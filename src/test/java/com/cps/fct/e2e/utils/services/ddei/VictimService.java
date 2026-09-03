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

    public void addVictimServiceLead(String caseVictimGuid, String requestBody) {
        service.sendRequest(addVictimServiceLeadRequestParams(caseVictimGuid, requestBody));
    }

    private HttpClientBuilder addVictimServiceLeadRequestParams(String caseVictimGuid, String requestBody) {
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

    private HttpClientBuilder assignVictimLiaisonOfficerRequestParams(String guid, String requestBody) {
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

    public HttpResponseWrapper getVictimDetailsFromCMS(String caseId) {
        return service.sendRequest(getVictimDetailsFromCMSRequestParams(caseId));
    }

    private HttpClientBuilder getVictimDetailsFromCMSRequestParams(String caseId) {
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

    public void addMeetingsNotOffered(String guid, String requestBody) {
        service.sendRequest(addMeetingsNotOfferedRequestParams(guid, requestBody));
    }

    private HttpClientBuilder addMeetingsNotOfferedRequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/meeting-offers", guid))
                .addHeaders(ddeiHeaders())
                .method("POST")
                .body(requestBody)
                .resourceName("addMeetingNotOffered")
                .build();
    }

    public HttpResponseWrapper listMeetingNotOfferedDetails(String guid, Integer meetingTypeCode) {
        return service.sendRequest(getMeetingNotOfferDetailsRequestParams(guid, meetingTypeCode));
    }

    private HttpClientBuilder getMeetingNotOfferDetailsRequestParams(String guid, Integer meetingTypeCode) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/meeting-offers/%s", guid, meetingTypeCode))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("getMeetingNotOffered")
                .build();
    }

    public String addMeetingOfferedMethod(String guid, String requestBody) {
        HttpResponseWrapper responseWrapper = service.sendRequest(addMeetingOfferedMethodRequestParams(guid, requestBody));
        String meetingContextGuid = JsonPath.read(responseWrapper.getBody(), "$.value.meetingContextGuid");
        assertThat(meetingContextGuid)
                .withFailMessage("Meeting Context Guid was not returned from the API response")
                .isNotNull();
        return meetingContextGuid;
    }

    private HttpClientBuilder addMeetingOfferedMethodRequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/meeting-offers", guid))
                .addHeaders(ddeiHeaders())
                .method("POST")
                .body(requestBody)
                .resourceName("addMeetingOfferMethod")
                .build();
    }

    public HttpResponseWrapper listMeetingOffered(String guid, Integer meetingTypeCode) {
        return service.sendRequest(getMeetingOfferRequestParams(guid, meetingTypeCode));
    }

    private HttpClientBuilder getMeetingOfferRequestParams(String guid, Integer meetingTypeCode) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/meeting-offers/%s", guid, meetingTypeCode))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("getMeeting")
                .build();
    }

    public void addMeetingOfferedResponse(String guid, String requestBody) {
        service.sendRequest(addMeetingOfferedResponseRequestParams(guid, requestBody));
    }

    private HttpClientBuilder addMeetingOfferedResponseRequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/meeting-offers", guid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("addMeetingResponse")
                .build();
    }

    public String arrangeMeeting(String guid, String requestBody) {
        HttpResponseWrapper responseWrapper = service.sendRequest(arrangeMeetingRequestParams(guid, requestBody));
        String meetingDetailsGuid = JsonPath.read(responseWrapper.getBody(), "$.value.victimMeetingDetailsGuid");
        assertThat(meetingDetailsGuid)
                .withFailMessage("Victim Meeting Details Guid was not returned from the API response")
                .isNotNull();
        return meetingDetailsGuid;
    }

    private HttpClientBuilder arrangeMeetingRequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/meetings", guid))
                .addHeaders(ddeiHeaders())
                .method("POST")
                .body(requestBody)
                .resourceName("arrangeMeeting")
                .build();
    }

    public Map<String, String> addMeetingAttendees(String meetingVictimGuid, String requestBody) {
        HttpResponseWrapper responseWrapper = service.sendRequest(addMeetingAttendeesRequestParams(meetingVictimGuid, requestBody));
//        String meetingAttendeesGuid = JsonPath.read(responseWrapper.getBody(), "$.value[0].meetingAttendeeGuid");
//        assertThat(meetingAttendeesGuid)
//                .withFailMessage("Victim Meeting Attendees Guid was not returned from the API response")
//                .isNotNull();
//        return meetingAttendeesGuid;
        List<Map<String, Object>> attendees =
                JsonPath.read(responseWrapper.getBody(), "$.value");
        Map<String, String> attendeeGuidByRole = new HashMap<>();
        for (Map<String, Object> attendee : attendees) {
            String role = (String) attendee.get("attendeeRole");
            String guid = (String) attendee.get("meetingAttendeeGuid");
            attendeeGuidByRole.put(role, guid);
        }
        return attendeeGuidByRole;
    }

    private HttpClientBuilder addMeetingAttendeesRequestParams(String meetingVictimGuid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/meetings/%s/attendees", meetingVictimGuid))
                .addHeaders(ddeiHeaders())
                .method("POST")
                .body(requestBody)
                .resourceName("addMeetingAttendees")
                .build();
    }

    public HttpResponseWrapper getMeetingArranged(String meetingDetailsGuid) {
        return service.sendRequest( getMeetingArrangedRequestParams(meetingDetailsGuid));
    }

    private HttpClientBuilder getMeetingArrangedRequestParams(String meetingDetailsGuid) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/meetings/%s", meetingDetailsGuid))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("getArrangedMeetingDetails")
                .build();
    }

    public void cancelMeeting(String meetingVictimGuid, String requestBody) {
        service.sendRequest(cancelMeetingRequestParams(meetingVictimGuid, requestBody));
    }

    private HttpClientBuilder cancelMeetingRequestParams(String meetingVictimGuid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/meetings/%s", meetingVictimGuid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("cancelMeeting")
                .build();
    }

    public void loggedMeeting(String meetingVictimGuid, String requestBody) {
        service.sendRequest(loggedMeetingRequestParams(meetingVictimGuid, requestBody));
    }

    private HttpClientBuilder loggedMeetingRequestParams(String meetingVictimGuid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/meetings/%s", meetingVictimGuid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("loggedMeeting")
                .build();
    }


    public void logMeetingAttendees(String meetingVictimGuid, String requestBody) {
       service.sendRequest(logMeetingAttendeesRequestParams(meetingVictimGuid, requestBody));
    }

    private HttpClientBuilder logMeetingAttendeesRequestParams(String meetingVictimGuid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/meetings/%s/attendees", meetingVictimGuid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("logMeetingAttendees")
                .build();
    }

    public void addOtherCommunication(String guid, String requestBody) {
        service.sendRequest(addOtherCommunicationRequestParams(guid, requestBody));
    }

    private HttpClientBuilder addOtherCommunicationRequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/%s/adhoc-journey/victim-communication", guid))
                .addHeaders(ddeiHeaders())
                .method("POST")
                .body(requestBody)
                .resourceName("addAdhocCommunication")
                .build();
    }


    public void informDecision(int decision, String guid, String requestBody) {
        service.sendRequest(informDecisionRequestParams(decision, guid, requestBody));
    }

    private HttpClientBuilder informDecisionRequestParams(int decision ,String guid, String requestBody) {
        String endpoint = switch (decision) {
            case 3 -> format("/api/victims/%s/decision-to-charge", guid);
            case 4 -> format("/api/victims/%s/no-further-action", guid);
            default -> throw new IllegalArgumentException("Invalid decision: " + decision);
        };

        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(endpoint)
                .addHeaders(ddeiHeaders())
                .method("POST")
                .body(requestBody)
                .resourceName("informDecisionCommunication")
                .build();
    }

    public void logCommunicationTask(String guid, String requestBody) {
        service.sendRequest(logCommunicationTaskRequestParams(guid, requestBody));
    }

    private HttpClientBuilder logCommunicationTaskRequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/VictimCaseInfos/%s/ActiveTasks?adhocTask=false", guid))
                .addHeaders(ddeiHeaders())
                .method("POST")
                .body(requestBody)
                .resourceName("addAdhocCommunication")
                .build();
    }

    public void addCaseChargeTypeToVCA(String guid, String requestBody) {
        service.sendRequest(addCaseChargeTypeToVCARequestParams(guid, requestBody));
    }

    private HttpClientBuilder addCaseChargeTypeToVCARequestParams(String guid, String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/case-info/%s", guid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("addCaseChargeTypeToVca")
                .build();
    }


}
