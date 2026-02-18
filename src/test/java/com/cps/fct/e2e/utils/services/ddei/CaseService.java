package com.cps.fct.e2e.utils.services.ddei;

import com.cps.fct.e2e.model.Case;
import com.cps.fct.e2e.model.UserData;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpClientBuilder;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.httpClient.ResourceResponseStore;
import com.cps.fct.e2e.utils.services.BaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.cps.fct.e2e.utils.common.JsonUtils.fromJson;
import static com.cps.fct.e2e.utils.common.JsonUtils.fromJsonToList;
import static com.cps.fct.e2e.utils.services.ddei.payloadBuilder.PreChargeCaseBuilder.constructPreChargeTriageFor28DaysMCAccepted;
import static java.lang.String.format;

public class CaseService extends BaseService {


    public List<Case> listCaseDetails(String caseURn) {
        HttpResponseWrapper responseWrapper = service.sendRequest(caseListRequestParams(caseURn));
        return fromJsonToList(responseWrapper.getBody(), Case.class);
    }


    private HttpClientBuilder caseListRequestParams(String caseURN) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/urns/%s/cases", caseURN))
                .addFormParams(Map.of())
                .addHeaders(ddeiHeaders())
                .method("GET")
                .retry(0)
                .resourceName("listCases")
                .build();
    }

    public Optional<String> getCaseIdFor(String caseUrn) {
        try {
            List<Case> cases = listCaseDetails(caseUrn);
            if (cases == null || cases.isEmpty()) return Optional.empty();

            JSONArray jsonArray = new JSONArray(cases);
            JSONObject obj = jsonArray.optJSONObject(0);
            if (obj == null || !obj.has("id")) return Optional.empty();

            return Optional.of(obj.getString("id"));
        } catch (JSONException | IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public void assertCaseDetails(List<Case> caseListDetails) {
        JSONArray jsonArray = new JSONArray(caseListDetails);
        JSONObject obj = jsonArray.getJSONObject(0);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(obj.getString("id")).as(" ID").isNotEmpty();
        softly.assertThat(obj.getString("urn")).as(" URN").isNotEmpty();
        softly.assertAll();

    }

    public void prechargeTheTirageCaseFor28DaysMCAccepted(String caseUrn, String caseId) throws JsonProcessingException {
        DataStoreForPartyAndTaskIds result = getPartyIdAndTaskId(caseUrn, caseId);
        String preChargePayload = constructPreChargeTriageFor28DaysMCAccepted(result.partyId());
         triagePreChargeCaseRequestParams(caseId, result.taskId(), preChargePayload);
    }

    @NotNull
    private DataStoreForPartyAndTaskIds getPartyIdAndTaskId(String caseUrn, String caseId) {
        UserData userData = retrieveUnitAndSurname();
        String partyId = extractPartyId(
                userData.getUnitIdByName(EnvConfig.get("UNIT_NAME")), userData.getUserSurname());
        releaseLockIfPresent(caseUrn, caseId);
        String taskId = taskIdForCaseId(caseId);
        return new DataStoreForPartyAndTaskIds(partyId, taskId);
    }


    private String taskIdForCaseId(String caseId) {
        service.sendRequest(new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(String.format("/api/cases/%s/tasks", caseId))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .retry(0)
                .resourceName("caseTaskId")
                .build());
        HttpResponseWrapper caseTaskIdResponse = ResourceResponseStore.getLatestResponse("caseTaskId");
        return JsonPath.read(caseTaskIdResponse.getBody(), "$[0].id").toString();
    }

    private void triagePreChargeCaseRequestParams(String caseId, String taskId, String payload) {
        service.sendRequest(new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(String.format("/api/cases/%s/triage/%s/preChargeDecision", caseId, taskId))
                .addHeaders(ddeiHeaders())
                .body(payload)
                .method("POST")
                .retry(0)
                .resourceName("triagePrechargeCase")
                .build());

    }

    private void releaseLockIfPresent(String caseUrn, String caseId) {
        service.sendRequest(new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(String.format("/api/urns/%s/cases/%s/unlock", caseUrn, caseId))
                .addHeaders(ddeiHeaders())
                .method("POST")
                .retry(0)
                .resourceName("caseLock")
                .build());

    }


    private String extractPartyId(String unitId, String surname) {
        service.sendRequest(new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint("/api/users")
                        .addQueryParam("unitId", unitId)
                        .addQueryParam("surname", surname)
                .addHeaders(ddeiHeaders())
                .method("GET")
                .retry(0)
                .resourceName("usersUnits")
                .build());
        HttpResponseWrapper usersUnitsResponse = ResourceResponseStore.getLatestResponse("usersUnits");
        return JsonPath.read(usersUnitsResponse.getBody(), "$[0].partyId").toString();
    }

    private UserData retrieveUnitAndSurname() {
       service.sendRequest(new HttpClientBuilder.Builder()
               .baseUri(EnvConfig.get("DDEI_HOST"))
               .endpoint("/api/user-data")
               .addHeaders(ddeiHeaders())
               .method("GET")
               .retry(0)
               .resourceName("userData")
               .build());
        HttpResponseWrapper userDataResponse = ResourceResponseStore.getLatestResponse("userData");
       return fromJson(userDataResponse.getBody(), UserData.class);
    }

    public void retryUntilCaseIsInCMS(ScenarioContext context) {
        Optional<String> optionalCaseUrn = Optional.empty();
        String caseUrn = context.get("caseUrn");
        int maxAttempts = 10;
        for (int i = 0; i < maxAttempts; i++) {
            optionalCaseUrn = getCaseIdFor(caseUrn);
            if (optionalCaseUrn.isPresent()) {
                context.set("caseId", optionalCaseUrn.get());
                return;
            }
        }
            Assertions.fail("Case ID not found for URN: " + caseUrn);
    }


    private record DataStoreForPartyAndTaskIds(String partyId, String taskId) {
    }
}






