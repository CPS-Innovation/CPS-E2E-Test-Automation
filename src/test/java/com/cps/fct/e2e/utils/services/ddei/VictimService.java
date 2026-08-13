package com.cps.fct.e2e.utils.services.ddei;

import com.cps.fct.e2e.model.victimCaseApp.VictimCmsDetails;
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
import static com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimCaseAppPayloadBuilder.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class VictimService extends BaseService {
    private Response response;

    public HttpResponseWrapper victimWitnessList(String caseId) {
        return service.sendRequest(getVictimWitnessListForCMSRequestParams(caseId));
    }

    private HttpClientBuilder getVictimWitnessListForCMSRequestParams(String caseId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/witnesses", caseId))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("victimWitnessListFromCMS")
                .build();
    }

    public void victimWitnessIds(HttpResponseWrapper response, ScenarioContext context) {
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

        Map<String, List<String>> victimMapIds = new HashMap<>();
        Map<String, List<String>> witnessMapIds = new HashMap<>();
        witnessMapIds.put("witnessId", witnessId);
        witnessMapIds.put("witnessChildId", witnessChildId);
        witnessMapIds.put("witnessExpertId", witnessExpertId);
        witnessMapIds.put("witnessPrisonerId", witnessPrisonerId);
        witnessMapIds.put("witnessInterpreterId", witnessInterpreterId);
        witnessMapIds.put("witnessVulnerableId", witnessVulnerableId);
        witnessMapIds.put("witnessPoliceId", witnessPoliceId);
        witnessMapIds.put("witnessProfessionalId", witnessProfessionalId);
        witnessMapIds.put("witnessIntimidatedId", witnessIntimidatedId);
        witnessMapIds.put("witnessSpecialId", witnessSpecialId);

        victimMapIds.put("victimId", victimId);
        victimMapIds.put("victimChildId", victimChildId);
        victimMapIds.put("victimExpertId", victimExpertId);
        victimMapIds.put("victimPrisonerId", victimPrisonerId);
        victimMapIds.put("victimInterpreterId", victimInterpreterId);
        victimMapIds.put("victimVulnerableId", victimVulnerableId);
        victimMapIds.put("victimPoliceId", victimPoliceId);
        victimMapIds.put("victimProfessionalId", victimProfessionalId);
        victimMapIds.put("victimIntimidatedId", victimIntimidatedId);
        victimMapIds.put("victimSpecialId", victimSpecialId);

        victimMapIds.put("pureVictimId", pureVictimId);
        victimMapIds.put("pureVictimVulnerableId", pureVictimVulnerableId);
        victimMapIds.put("pureVictimIntimidatedId", pureVictimIntimidatedId);

        context.set("victimMapIds", victimMapIds);
        context.set("witnessMapIds", witnessMapIds);
        
    }

    public String caseVictimGuid(String caseUrn, String caseId, String victimId ){
        HttpResponseWrapper responseWrapper = service.sendRequest(
                addCaseVictimToVCARequestParams(caseUrn, caseId, victimId));
        String victimCaseInfoGuid = JsonPath.read(responseWrapper.getBody(), "$.value.CaseInfoGuid");
        assertThat(victimCaseInfoGuid)
                .withFailMessage("CaseInfoGuid was not returned from the API response")
                .isNotNull();
        return victimCaseInfoGuid;
    }

    private HttpClientBuilder addCaseVictimToVCARequestParams(String caseUrn, String caseId, String victimId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/urns/%s/cases/%s/parties/%s", caseUrn, caseId, victimId))
                .addHeaders(ddeiHeaders())
                .method("POST")
                .body(postCaseVictimGuid(caseUrn))
                .resourceName("addVictimWitnessToVCA")
                .build();
    }




//    public String victimWitnessGuid(String caseUrn, String caseId, String WitnessVictimId) {
//        HttpResponseWrapper responseWrapper = service.sendRequest(
//                addVictimOrWitnessRequestParams(caseUrn, caseId, WitnessVictimId));
//        String victimCaseInfoGuid = JsonPath.read(responseWrapper.getBody(), "$.value.victimCaseInfoGuid");
//        assertThat(victimCaseInfoGuid)
//                .withFailMessage("victimCaseInfoGuid")
//                .isNotNull();
//        return victimCaseInfoGuid;
//    }
//








    public void addVictimWitnessCMSPersonalDetails(VictimCmsDetails details, String caseId, String witnessId) {
        service.sendRequest(addWitnessDetailsWitnessIdRequestParams(details, caseId, witnessId));
    }

    public void updateVictimWitnessCMSPersonalDetails(VictimCmsDetails details, String caseId, String witnessId) {
        service.sendRequest(updateWitnessDetailsWitnessIdRequestParams(details, caseId, witnessId));
    }

    public void addVictimWitnessCategoryDetails(VictimCmsDetails details, String caseId, String witnessId) {
        service.sendRequest(addCategoryWitnessIdRequestParams(details, caseId, witnessId));
    }

    public void updateVictimWitnessCategoryDetails(VictimCmsDetails details, String caseId, String witnessId) {
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



    public HttpResponseWrapper listVictimWitnessCMSContact(String caseId) {
        return service.sendRequest(getListVictimWitnessCMSContact(caseId));
    }

    public HttpResponseWrapper listVictimContactTypeDetails(String guid) {
        return service.sendRequest(getVictimContactTypeDetailsForRequestParams(guid));
    }

    public void addWitnessVictimDetailsToVCA(String guid, String requestBody) {
        service.sendRequest(addWitnessVictimVCADetailsRequestParams(guid, requestBody));
    }


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

    public void victimMeetingStatus(String guid, String requestBody) {
        service.sendRequest(victimMeetingStatusRequestParams(guid, requestBody));
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

    public HttpResponseWrapper listVictimMeetingDetails(String guid, Integer meetingTypeCode) {
        return service.sendRequest(getVictimMeetingDetailsForRequestParams(guid,meetingTypeCode ));
    }

    public HttpResponseWrapper listMeetingStatusDetails(String guid, Integer meetingTypeCode, Integer meetingAttempt) {
        return service.sendRequest(getMeetingStatusDetailsForRequestParams(guid,meetingTypeCode,meetingAttempt ));
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

    private HttpClientBuilder addWitnessDetailsWitnessIdRequestParams(VictimCmsDetails victimDetails,
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

    private HttpClientBuilder updateWitnessDetailsWitnessIdRequestParams(VictimCmsDetails victimDetails,
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

    private HttpClientBuilder addCategoryWitnessIdRequestParams(VictimCmsDetails victimDetails,
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

    private HttpClientBuilder updateCategoryVictimIdRequestParams(VictimCmsDetails victimDetails,
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

    private HttpClientBuilder victimMeetingStatusRequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/meeting-offers", guid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("victimMeetingStatusDetails")
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

    private HttpClientBuilder getMeetingStatusDetailsForRequestParams(String guid, Integer meetingTypeCode, Integer meetingAttempt) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/meeting-offers/%s/%s", guid,meetingTypeCode,meetingAttempt))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("getMeetingStatusDetails")
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
