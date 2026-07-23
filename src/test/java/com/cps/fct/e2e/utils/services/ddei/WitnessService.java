package com.cps.fct.e2e.utils.services.ddei;

import com.cps.fct.e2e.model.VictimWitnessDetails;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpClientBuilder;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.BaseService;
import com.jayway.jsonpath.JsonPath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static com.cps.fct.e2e.utils.common.JsonUtils.extractFromJsonToList;
import static com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimWitnessPayloadBuilder.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class WitnessService extends BaseService {
    private Response response;
    private String baseURI;

    public void addVictimWitnessCMSPersonalDetails(VictimWitnessDetails details, String caseId, String witnessId) {
        service.sendRequest(addWitnessDetailsWitnessIdRequestParams(details, caseId, witnessId));
    }

    public void updateVictimWitnessCMSPersonalDetails(VictimWitnessDetails details, String caseId, String witnessId) {
        service.sendRequest(updateWitnessDetailsWitnessIdRequestParams(details, caseId, witnessId));
    }

    public void addVictimWitnessCategoryDetails(VictimWitnessDetails details, String caseId, String witnessId) {
        service.sendRequest(addCategoryWitnessIdRequestParams(details, caseId, witnessId));
    }

    public void updateVictimWitnessCategoryDetails(VictimWitnessDetails details, String caseId, String witnessId) {
        service.sendRequest(updateCategoryVictimIdRequestParams(details, caseId, witnessId));
    }

    public String victimWitnessGuid(String caseUrn, String caseId, String WitnessVictimId) {
        HttpResponseWrapper responseWrapper = service.sendRequest(
                addVictimOrWitnessRequestParams(caseUrn, caseId, WitnessVictimId));
        String victimCaseInfoGuid = JsonPath.read(responseWrapper.getBody(), "$.value.victimCaseInfoGuid");
        assertThat(victimCaseInfoGuid)
                .withFailMessage("victimCaseInfoGuid")
                .isNotNull();
        return victimCaseInfoGuid;
    }

    public HttpResponseWrapper listWitnessVictimDetails(String caseId) {
        return service.sendRequest(getWitnessesDetailsForCMSRequestParams(caseId));
    }

    public HttpResponseWrapper listVictimWitnessCMSContact(String caseId) {
        return service.sendRequest(getListVictimWitnessCMSContact(caseId));
    }

    public Response listVictimContactTypeDetails(String guid) {
        return service.restAssuredRequest(getVictimContactTypeDetailsForRequestParams(guid));
    }

    public void addWitnessVictimDetailsToVCA(String guid, String requestBody) {
        service.sendRequest(addWitnessVictimVCADetailsRequestParams(guid, requestBody));
    }

//    public void getUserPartyId() {
//        service.sendRequest(getUserPartyIdParams());
//    }

    public Integer getUserPartyId() {
        HttpResponseWrapper responseWrapper = service.sendRequest(getUserPartyIdParams());
        Integer userPartyId;
        userPartyId = JsonPath.read(responseWrapper.getBody(), "$.partyId");
        return userPartyId;
    }


    public void assignVictimLiaisonOfficer(String guid, String requestBody) {
        service.sendRequest(assignVictimLiaisonOfficerRequestParams(guid, requestBody));
    }


    public void addVictimContactDetailsToVCA(String guid, String requestBody) {
        service.sendRequest(addVictimContactDetailsRequestParams(guid, requestBody));
    }

    public void updateVictimContactDetailsToVCA(String guid, String requestBody) {
        service.sendRequest(updateVictimContactDetailsRequestParams(guid, requestBody));
    }

    public void updateWitnessVictimDetailsToVCA(String guid, String requestBody) {
        service.sendRequest(updateWitnessVictimVCADetailsRequestParams(guid, requestBody));
    }

    public HttpResponseWrapper witnessesDetailsFromVCA(String guid) {
        return service.sendRequest(getWitnessesDetailsFromVCARequestParams(guid));
    }

    public HttpResponseWrapper addVictimMeetingDetailsToVCA(String guid, String requestBody) {
        return service.sendRequest(addVictimMeetingDetailsRequestParams(guid, requestBody));
    }

    public void declineVictimMeeting(String guid, String requestBody) {
        service.sendRequest(declineVictimMeetingRequestParams(guid, requestBody));
    }

    public void noResponseVictimMeeting(String guid, String requestBody) {
         service.sendRequest(noResponseVictimMeetingRequestParams(guid, requestBody));
    }

    public void addVictimLiaisonOfficer(String guid, String requestBody) {
        service.sendRequest(assignVictimLiaisonOfficerRequestParams(guid, requestBody));
    }

    public HttpResponseWrapper victimLiaisonOfficerFromVCA(String guid) {
        return service.sendRequest(getvictimLiaisonOfficerFromVCARequestParams(guid));
    }

    public Response listVictimMeetingDetails(String guid, Integer meetingTypeCode) {
        return service.restAssuredRequest(getVictimMeetingDetailsForRequestParams(guid,meetingTypeCode ));
    }

    public Response listDelineMeetingDetails(String guid, Integer meetingTypeCode, Integer meetingAttempt) {
        return service.restAssuredRequest(getDelineMeetingDetailsForRequestParams(guid,meetingTypeCode,meetingAttempt ));
    }

//    public Response noResponseMeetingDetails(String guid, Integer meetingTypeCode, Integer meetingAttempt) {
//        return service.restAssuredRequest(getDelineMeetingDetailsForRequestParams(guid,meetingTypeCode,meetingAttempt ));
//    }

    public void persistVictimWitnessDetails(HttpResponseWrapper response, ScenarioContext context) {
        String body = response.getBody();
        List<String> witnessId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isKeyWitness=='Yes')].witnessId");
        List<String> witnessChildId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isChild==true)].witnessId");
        List<String> witnessExpertId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isExpert==true)].witnessId");
        List<String> witnessPrisonerId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isPrisoner==true)].witnessId");
        List<String> witnessInterpreterId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isInterpreter==true)].witnessId");
        List<String> witnessVulnerableId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isVulnerable==true)].witnessId");
        List<String> witnessPoliceId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isPolice==true)].witnessId");
        List<String> witnessProfessionalId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isProfessional==true)].witnessId");
        List<String> witnessIntimidatedId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isIntimidated==true)].witnessId");
        List<String> witnessSpecialId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isSpecialNeeds==true)].witnessId");

        List<String> victimId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isKeyWitness=='Yes')].witnessId");
        List<String> victimChildId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isChild==true)].witnessId");
        List<String> victimExpertId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isExpert==true)].witnessId");
        List<String> victimPrisonerId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isPrisoner==true)].witnessId");
        List<String> victimInterpreterId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isInterpreter==true)].witnessId");
        List<String> victimVulnerableId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isVulnerable==true)].witnessId");
        List<String> victimPoliceId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isPolice==true)].witnessId");
        List<String> victimProfessionalId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isProfessional==true)].witnessId");
        List<String> victimIntimidatedId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isIntimidated==true)].witnessId");
        List<String> victimSpecialId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isSpecialNeeds==true)].witnessId");

        List<String> pureVictimId = extractFromJsonToList(body,
                "$[?(@.isPureVictim==true && @.isWitnessAndVictim==false)].witnessId");
        List<String> pureVictimVulnerableId = extractFromJsonToList(body,
                "$[?(@.isPureVictim==true && @.isVulnerable==true)].witnessId");
        List<String> pureVictimIntimidatedId = extractFromJsonToList(body,
                "$[?(@.isPureVictim==true && @.isIntimidated==true)].witnessId");

        Map<String, List<String>> witnessVictimMapIds = new HashMap<>();
        witnessVictimMapIds.put("witnessId", witnessId);
        witnessVictimMapIds.put("witnessChildId", witnessChildId);
        witnessVictimMapIds.put("witnessExpertId", witnessExpertId);
        witnessVictimMapIds.put("witnessPrisonerId", witnessPrisonerId);
        witnessVictimMapIds.put("witnessInterpreterId", witnessInterpreterId);
        witnessVictimMapIds.put("witnessVulnerableId", witnessVulnerableId);
        witnessVictimMapIds.put("witnessPoliceId", witnessPoliceId);
        witnessVictimMapIds.put("witnessProfessionalId", witnessProfessionalId);
        witnessVictimMapIds.put("witnessIntimidatedId", witnessIntimidatedId);
        witnessVictimMapIds.put("witnessSpecialId", witnessSpecialId);

        witnessVictimMapIds.put("victimId", victimId);
        witnessVictimMapIds.put("victimChildId", victimChildId);
        witnessVictimMapIds.put("victimExpertId", victimExpertId);
        witnessVictimMapIds.put("victimPrisonerId", victimPrisonerId);
        witnessVictimMapIds.put("victimInterpreterId", victimInterpreterId);
        witnessVictimMapIds.put("victimVulnerableId", victimVulnerableId);
        witnessVictimMapIds.put("victimPoliceId", victimPoliceId);
        witnessVictimMapIds.put("victimProfessionalId", victimProfessionalId);
        witnessVictimMapIds.put("victimIntimidatedId", victimIntimidatedId);
        witnessVictimMapIds.put("victimSpecialId", victimSpecialId);

        witnessVictimMapIds.put("pureVictimId", pureVictimId);
        witnessVictimMapIds.put("pureVictimVulnerableId", pureVictimVulnerableId);
        witnessVictimMapIds.put("pureVictimIntimidatedId", pureVictimIntimidatedId);

        context.set("witnessVictimMapIds", witnessVictimMapIds);
    }

    private HttpClientBuilder getWitnessesDetailsForCMSRequestParams(String caseId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/witnesses", caseId))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("witnessDetailsFromCMS")
                .build();
    }

    private HttpClientBuilder getListVictimWitnessCMSContact(String caseId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/contacts", caseId))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("victimWitnessCMSContact")
                .build();
    }

    private HttpClientBuilder getWitnessesDetailsFromVCARequestParams(String guid) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/case-info/%s", guid))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("witnessDetailsFromVCA")
                .build();
    }

    private HttpClientBuilder getVictimContactTypeDetailsForRequestParams(String guid) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/cps-contacts", guid))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("getVictimContactTypeDetails")
                .build();
    }

    private HttpClientBuilder addVictimOrWitnessRequestParams(
            String caseUrn, String caseId, String WitnessVictimId) {

        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/urns/%s/cases/%s/parties/%s", caseUrn, caseId, WitnessVictimId))
                .addHeaders(ddeiHeaders())
                .method("POST")
                .body(payLoadForAddVictimWitnessToVCA(caseUrn))
                .resourceName("addVictimWitnessToVCA")
                .build();
    }

    private HttpClientBuilder addWitnessVictimVCADetailsRequestParams(
            String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/case-info/%s", guid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("addVictimWitnessPersonalDetails")
                .build();
    }

    private HttpClientBuilder updateWitnessVictimVCADetailsRequestParams(
            String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/case-info/%s", guid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("addVictimWitnessPersonalDetails")
                .build();
    }

    private HttpClientBuilder assignVictimLiaisonOfficerRequestParams(
            String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/case-info/%s", guid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("assignVictimLiaisonOfficer")
                .build();
    }


    private HttpClientBuilder getvictimLiaisonOfficerFromVCARequestParams(String guid) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/case-info/%s", guid))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("witnessDetailsFromVCA")
                .build();
    }

    private HttpClientBuilder addWitnessDetailsWitnessIdRequestParams(VictimWitnessDetails victimDetails,
                                                                      String caseId, String WitnessId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/witnesses/%s", caseId, WitnessId))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(payLoadForAddWitnessDetailsWitnessId(victimDetails))
                .resourceName("addVictimWitnessPersonalDetails")
                .build();
    }

    private HttpClientBuilder updateWitnessDetailsWitnessIdRequestParams(VictimWitnessDetails victimDetails,
                                                                        String caseId, String WitnessId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/witnesses/%s", caseId, WitnessId))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(payLoadForUpdateWitnessDetailsWitnessId(victimDetails))
                .resourceName("addVictimWitnessPersonalDetails")
                .build();
    }

    private HttpClientBuilder addCategoryWitnessIdRequestParams(VictimWitnessDetails victimDetails,
                                                                String caseId, String WitnessId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/witnesses/%s", caseId, WitnessId))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(payLoadForAddOrUpdateCategory(victimDetails))
                .resourceName("addWitnessCategoryDetails")
                .build();
    }

    private HttpClientBuilder updateCategoryVictimIdRequestParams(VictimWitnessDetails victimDetails,
                                                                  String caseId, String WitnessId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/witnesses/%s", caseId, WitnessId))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(payLoadForAddOrUpdateCategory(victimDetails))
                .resourceName("updateVictimCategoryDetails")
                .build();
    }

    private HttpClientBuilder addVictimContactDetailsRequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/cps-contacts", guid))
                .addHeaders(ddeiHeaders())
                .method("POST")
                .body(requestBody)
                .resourceName("addVictimContactDetails")
                .build();
    }
    private HttpClientBuilder updateVictimContactDetailsRequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/cps-contacts", guid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("updateVictimContactDetails")
                .build();
    }

    private HttpClientBuilder addVictimMeetingDetailsRequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/meeting-offers", guid))
                .addHeaders(ddeiHeaders())
                .method("POST")
                .body(requestBody)
                .resourceName("addVictimMeetingDetails")
                .build();
    }

    private HttpClientBuilder declineVictimMeetingRequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/meeting-offers", guid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("declineVictimMeetingDetails")
                .build();
    }

    private HttpClientBuilder noResponseVictimMeetingRequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/meeting-offers", guid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("noResponseVictimMeetingDetails")
                .build();
    }

    private HttpClientBuilder getVictimMeetingDetailsForRequestParams(String guid, Integer meetingTypeCode) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/meeting-offers/%s", guid,meetingTypeCode))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("getVictimMeetingTypeDetails")
                .build();
    }

    private HttpClientBuilder getDelineMeetingDetailsForRequestParams(String guid, Integer meetingTypeCode, Integer meetingAttempt) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/meeting-offers/%s/%s", guid,meetingTypeCode,meetingAttempt))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("getMeetingTypeDetails")
                .build();
    }

    private HttpClientBuilder getUserPartyIdParams() {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint("/api/users/party")
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("getUserPartyId")
                .build();
    }



    private static void assertIdsArePresent(ScenarioContext context, List<String> victimIds) {
        assertThat(victimIds)
                .withFailMessage("id's are should not be null " + context.get("caseId"))
                .isNotNull()
                .withFailMessage("id should contain at least one items " + context.get("caseId"))
                .isNotEmpty();
    }

    private Response restAssuredSample(String caseId) {
        response = RestAssured
                .given()
                .log()
                .all()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .basePath("/api/cases/{caseId}/witnesses")
                .pathParam("caseId", caseId)
                .headers(ddeiHeaders())
                .when()
                .get()
                .then()
                .log()
                .all().extract().response();

        return response;
    }

}
