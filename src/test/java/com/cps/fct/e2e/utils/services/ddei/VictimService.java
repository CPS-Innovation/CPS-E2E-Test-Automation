package com.cps.fct.e2e.utils.services.ddei;

import com.cps.fct.e2e.model.victimCaseApp.VictimCmsDetails;
import com.cps.fct.e2e.model.victimCaseApp.VictimVcaDetails;
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
        return service.sendRequest(getVictimWitnessListFromCmsRequestParams(caseId));
    }

    private HttpClientBuilder getVictimWitnessListFromCmsRequestParams(String caseId) {
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
                "$[?(@.isPureVictim==true && @.isWitnessAndVictim==false && @.isVulnerable==false && @.isIntimidated==false)].witnessId");
        List<String> pureVictimVulnerableId = extractFromJsonToList(body,
                "$[?(@.isPureVictim==true && @.isWitnessAndVictim==false && @.isVulnerable==true && @.isIntimidated==false)].witnessId");
        List<String> pureVictimIntimidatedId = extractFromJsonToList(body,
                "$[?(@.isPureVictim==true && @.isWitnessAndVictim==false && @.isVulnerable==false && @.isIntimidated==true)].witnessId");

        Map<String, List<String>> victimMapIds = new HashMap<>();
        Map<String, List<String>> witnessMapIds = new HashMap<>();
        witnessMapIds.put("witness", witnessId);
        witnessMapIds.put("witnessChild", witnessChildId);
        witnessMapIds.put("witnessExpert", witnessExpertId);
        witnessMapIds.put("witnessPrisoner", witnessPrisonerId);
        witnessMapIds.put("witnessInterpreter", witnessInterpreterId);
        witnessMapIds.put("witnessVulnerable", witnessVulnerableId);
        witnessMapIds.put("witnessPolice", witnessPoliceId);
        witnessMapIds.put("witnessProfessional", witnessProfessionalId);
        witnessMapIds.put("witnessIntimidated", witnessIntimidatedId);
        witnessMapIds.put("witnessSpecial", witnessSpecialId);

        victimMapIds.put("victimWitness", victimId);
        victimMapIds.put("victimWitnessChild", victimChildId);
        victimMapIds.put("victimWitnessExpert", victimExpertId);
        victimMapIds.put("victimWitnessPrisoner", victimPrisonerId);
        victimMapIds.put("victimWitnessInterpreter", victimInterpreterId);
        victimMapIds.put("victimWitnessVulnerable", victimVulnerableId);
        victimMapIds.put("victimWitnessPolice", victimPoliceId);
        victimMapIds.put("victimWitnessProfessional", victimProfessionalId);
        victimMapIds.put("victimWitnessIntimidated", victimIntimidatedId);
        victimMapIds.put("victimWitnessSpecial", victimSpecialId);

        victimMapIds.put("victim", pureVictimId);
        victimMapIds.put("victimVulnerable", pureVictimVulnerableId);
        victimMapIds.put("victimIntimidated", pureVictimIntimidatedId);

        context.set("victimMapIds", victimMapIds);
        context.set("witnessMapIds", witnessMapIds);

    }

    public String caseVictimGuid(String caseUrn, String caseId, String victimId, String requestBody) {
        HttpResponseWrapper responseWrapper = service.sendRequest(
                addCaseVictimToVCARequestParams(caseUrn, caseId, victimId, requestBody));
        String victimCaseInfoGuid = JsonPath.read(responseWrapper.getBody(), "$.value.victimCaseInfoGuid");
        assertThat(victimCaseInfoGuid)
                .withFailMessage("CaseInfoGuid was not returned from the API response")
                .isNotNull();
        return victimCaseInfoGuid;
    }

    private HttpClientBuilder addCaseVictimToVCARequestParams(String caseUrn, String caseId, String victimId, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/urns/%s/cases/%s/parties/%s", caseUrn, caseId, victimId))
                .addHeaders(ddeiHeaders())
                .method("POST")
                .body(requestBody)
                .resourceName("addVictimWitnessToVCA")
                .build();
    }

    public void addVictimServiceLead(String caseVictimGuid, String requestBody){
        service.sendRequest(addVictimServiceLeadRequestParams(caseVictimGuid,requestBody));
    }

    private HttpClientBuilder addVictimServiceLeadRequestParams (String caseVictimGuid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/case-info/%s", caseVictimGuid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("addVictimServiceLead")
                .build();
    }

    public Integer getUserPartyId() {
        HttpResponseWrapper responseWrapper = service.sendRequest(getUserPartyIdParams());
        return JsonPath.read(responseWrapper.getBody(), "$.partyId");
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

    public void assignVictimLiaisonOfficer(String guid, String requestBody) {
        service.sendRequest(assignVictimLiaisonOfficerRequestParams(guid, requestBody));
    }

    private HttpClientBuilder assignVictimLiaisonOfficerRequestParams( String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/case-info/%s", guid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("assignVictimLiaisonOfficer")
                .build();
    }

    public void addVictimPersonalDetailsToCMS(VictimCmsDetails details, String caseId, String victimId) {
        service.sendRequest(addVictimPersonalDetailsToCMSRequestParams(details, caseId, victimId));
    }

    private HttpClientBuilder addVictimPersonalDetailsToCMSRequestParams(VictimCmsDetails victimDetails,
                                                                         String caseId, String victimId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/witnesses/%s", caseId, victimId))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(payLoadAddVictimPersonalDetailsToCMS(victimDetails))
                .resourceName("addVictimPersonalDetailsToCms")
                .build();
    }

    public void addVictimPersonalDetailsToVCA(String guid, String requestBody) {
        service.sendRequest(addVictimPersonalDetailsToVCARequestParams(guid, requestBody));
    }

    private HttpClientBuilder addVictimPersonalDetailsToVCARequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/case-info/%s", guid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("addVictimPersonalDetailsToVca")
                .build();
    }

    public HttpResponseWrapper getVictimDetailsFromCMS(String caseId){
        return service.sendRequest(getVictimDetailsFromCMSRequestParams(caseId));
    }

    private HttpClientBuilder getVictimDetailsFromCMSRequestParams(String caseId){
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("api/cases/%s/witnesses", caseId))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("getVictimPersonalDetailsFromCms")
                .build();

    }

    public HttpResponseWrapper getVictimDetailsFromVca(String guid) {
        return service.sendRequest(getVictimDetailsFromVcaRequestParams(guid));
    }

    private HttpClientBuilder getVictimDetailsFromVcaRequestParams(String guid) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/case-info/%s", guid))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("getVictimPersonalDetailsFromVca")
                .build();
    }

    public void updateVictimPersonalDetailsToCMS(VictimCmsDetails details, String caseId, String victimId) {
        service.sendRequest(updateVictimPersonalDetailsToCMSRequestParams(details, caseId, victimId));
    }

    private HttpClientBuilder updateVictimPersonalDetailsToCMSRequestParams(VictimCmsDetails victimDetails,
                                                                         String caseId, String victimId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/witnesses/%s", caseId, victimId))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(payLoadUpdateVictimPersonalDetailsToCMS(victimDetails))
                .resourceName("updateVictimPersonalDetailsToCms")
                .build();
    }

    public void updateVictimPersonalDetailsInVCA(String guid, String requestBody) {
        service.sendRequest(updateVictimDetailsInVCARequestParams(guid, requestBody));
    }

    private HttpClientBuilder updateVictimDetailsInVCARequestParams(
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

    public void addCpsContacts(String guid, String requestBody) {
        service.sendRequest(addCpsContactsRequestParams(guid, requestBody));
    }

    private HttpClientBuilder addCpsContactsRequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/cps-contacts", guid))
                .addHeaders(ddeiHeaders())
                .method("POST")
                .body(requestBody)
                .resourceName("addCpsContactDetails")
                .build();
    }

    public HttpResponseWrapper listCpsContactDetails(String guid) {
        return service.sendRequest(listCpsContactRequestParams(guid));
    }

    private HttpClientBuilder listCpsContactRequestParams(String guid) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/cps-contacts", guid))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("getCpsContactDetails")
                .build();
    }

    public void updateVictimContacts(String guid, String requestBody) {
        service.sendRequest(updateVictimContactsRequestParams(guid, requestBody));
    }

    private HttpClientBuilder updateVictimContactsRequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/cps-contacts", guid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("updateCpsContactDetails")
                .build();
    }

    public HttpResponseWrapper caseCmsContactList(String caseId) {
        return service.sendRequest(getCaseCmsContactList(caseId));
    }

    private HttpClientBuilder getCaseCmsContactList(String caseId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/contacts", caseId))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("getCaseCmsContactList")
                .build();
    }

    public void addVictimCategoryInVca(VictimCmsDetails details, String caseId, String victimId) {
        service.sendRequest(addVictimCategoryInVcaRequestParams(details, caseId, victimId));
    }

    private HttpClientBuilder addVictimCategoryInVcaRequestParams(VictimCmsDetails victimDetails,
                                                                String caseId, String victimId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/witnesses/%s", caseId, victimId))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(payLoadForAddOrUpdateCategory(victimDetails))
                .resourceName("addWitnessCategoryDetails")
                .build();
    }





//    public void updateVictimCategoryToCMS(String requestBody, String caseId, String victimId) {
//        service.sendRequest(updateVictimCategoryToCMSRequestParams(requestBody, caseId, victimId));
//    }
//
//    private HttpClientBuilder updateVictimCategoryToCMSRequestParams(String requestBody, String caseId, String victimId) {
//        return new HttpClientBuilder.Builder()
//                .baseUri(EnvConfig.get("DDEI_HOST"))
//                .endpoint(format("/api/cases/%s/witnesses/%s", caseId, victimId))
//                .addHeaders(ddeiHeaders())
//                .method("PATCH")
//                .body(payLoadUpdateVictimPersonalDetailsToCMS(victimDetails))
//                .body(payLoadUpdateVictimPersonalDetailsToCMS(victimDetails))
//                .resourceName("updateVictimPersonalDetailsToCms")
//                .build();
//    }

































































































































    public void updateVictimWitnessCMSPersonalDetails(VictimCmsDetails details, String caseId, String witnessId) {
        service.sendRequest(updateWitnessDetailsWitnessIdRequestParams(details, caseId, witnessId));
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







    public void updateVictimContactDetailsToVCA(String guid, String requestBody) {
        service.sendRequest(updateVictimContactDetailsRequestParams(guid, requestBody));
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



    public HttpResponseWrapper victimLiaisonOfficerFromVCA(String guid) {
        return service.sendRequest(getvictimLiaisonOfficerFromVCARequestParams(guid));
    }

    public HttpResponseWrapper listVictimMeetingDetails(String guid, Integer meetingTypeCode) {
        return service.sendRequest(getVictimMeetingDetailsForRequestParams(guid, meetingTypeCode));
    }

    public HttpResponseWrapper listMeetingStatusDetails(String guid, Integer meetingTypeCode, Integer meetingAttempt) {
        return service.sendRequest(getMeetingStatusDetailsForRequestParams(guid, meetingTypeCode, meetingAttempt));
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








    private HttpClientBuilder getvictimLiaisonOfficerFromVCARequestParams(String guid) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/case-info/%s", guid))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("witnessDetailsFromVCA")
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
                .endpoint(format("/api/victims/%s/meeting-offers/%s", guid, meetingTypeCode))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("getVictimMeetingTypeDetails")
                .build();
    }

    private HttpClientBuilder getMeetingStatusDetailsForRequestParams(String guid, Integer meetingTypeCode, Integer meetingAttempt) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/meeting-offers/%s/%s", guid, meetingTypeCode, meetingAttempt))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("getMeetingStatusDetails")
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
