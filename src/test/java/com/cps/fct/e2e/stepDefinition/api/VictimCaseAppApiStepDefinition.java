package com.cps.fct.e2e.stepDefinition.api;

import com.cps.fct.e2e.enums.vicitmCaseApp.*;
import com.cps.fct.e2e.model.victimCaseApp.*;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.ddei.CommonService;
import com.cps.fct.e2e.utils.services.ddei.CaseReviewService;
import com.cps.fct.e2e.utils.services.ddei.VictimService;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimCaseAppPayloadBuilder;
import com.cps.fct.e2e.utils.services.ddei.responseAssertions.VictimCaseAppAssertions;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.picocontainer.annotations.Inject;

import java.util.*;
import java.util.stream.Collectors;

import static com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimCaseAppPayloadBuilder.*;


public class VictimCaseAppApiStepDefinition {
    @Inject
    private CaseReviewService caseReviewService;

    @Inject
    private VictimService victimService;

    @Inject
    private ScenarioContext context;

    @Inject
    private CommonService service;

    public VictimCaseAppApiStepDefinition() {
    }

    @Given("victim details are available in VCA")
    public void victimDetailsInVCA() {
        service.createCmsAuthToken(context);

        Map<String, String> idGuidMap = new HashMap<>();
        context.set("idGuidMap", idGuidMap);

        HttpResponseWrapper responseVictimWitnessIds = victimService.victimWitnessList(context.get("caseId"));
        victimService.victimWitnessIds(responseVictimWitnessIds, context);

        Map<String, VictimCmsDetails> victimDetailsToCmsMap = new HashMap<>();
        context.set("victimDetailsToCmsMap", victimDetailsToCmsMap);

        Map<String, VictimVcaDetails> victimDetailsToVcaMap = new HashMap<>();
        context.set("victimDetailsToVcaMap", victimDetailsToVcaMap);

    }

    @When("the {string} is onboarded as {string} service lead in VCA")
    public void victimOnboardForService(String victimType, String serviceType) {

        CaseInfo victimCaseInfo;
        VictimVcaDetails victimVcaDetails;

        OnboardService serviceTypeCode = OnboardService.fromString(serviceType);
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");


        for (String id : victimMapIds.get(victimType)) {
            victimCaseInfo = onboardVictim(context.get("caseUrn"));
            String caseVictimGuid = victimService.caseVictimGuid(context.get("caseUrn"), context.get("caseId"), id, convertObjectToString(victimCaseInfo));

            victimVcaDetails = addVictimServiceLead(serviceTypeCode.getValue());
            victimService.addVictimServiceLead(caseVictimGuid, convertObjectToString(victimVcaDetails));
            idGuidMap.put(id, caseVictimGuid);
        }
        context.set("idGuidMap", idGuidMap);
    }

    @When("the Victim liaison officer is assigned to {string} in VCA")
    public void victimLiaisonOfficerAssigned(String victimType) {

        VictimLiaisonOfficer victimLiaisonOfficer;
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, String> idGuidMap = context.get("idGuidMap");

        for (String id : victimMapIds.get(victimType)) {
            victimLiaisonOfficer = assignVictimLiaisonOfficer(victimService.getUserPartyId());
            victimService.assignVictimLiaisonOfficer(idGuidMap.get(id), convertObjectToString(victimLiaisonOfficer));
        }

    }

    @When("the {string} personal details are added to CMS")
    public void victimPersonalDetailsToCMS(String victimType) throws InterruptedException {
        VictimCmsDetails victimCmsDetails;
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, VictimCmsDetails> victimDetailsToCmsMap = context.get("victimDetailsToCmsMap");

        for (String id : victimMapIds.get(victimType)) {
            victimCmsDetails = addVictimPersonalDetailsToCMS();
            victimService.addVictimPersonalDetailsToCMS(victimCmsDetails, context.get("caseId"), id);
            victimDetailsToCmsMap.put(id, victimCmsDetails);
            Thread.sleep(2000);
        }
        context.set("victimDetailsToCmsMap", victimDetailsToCmsMap);
    }

    @When("the {string} personal details are added to VCA")
    public void victimPersonalDetailsToVCA(String victimType) throws InterruptedException {
        VictimVcaDetails victimVcaDetails;
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, VictimVcaDetails> victimDetailsToVcaMap = context.get("victimDetailsToVcaMap");

        for (String id : victimMapIds.get(victimType)) {
            victimVcaDetails = addVictimPersonalDetailsToVCA();
            victimService.addVictimPersonalDetailsToVCA(idGuidMap.get(id), convertObjectToString(victimVcaDetails));
            victimDetailsToVcaMap.put(idGuidMap.get(id), victimVcaDetails);
            Thread.sleep(2000);
        }
        context.set("victimDetailsToVcaMap", victimDetailsToVcaMap);
    }

    @Then("the {string} personal details are verified in CMS and VCA")
    public void victimPersonalDetailsAreVerifiedInCMSAndVCA(String victimType) throws InterruptedException {

        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, VictimCmsDetails> victimDetailsToCmsMap = context.get("victimDetailsToCmsMap");
        Map<String, VictimVcaDetails> victimDetailsToVcaMap = context.get("victimDetailsToVcaMap");

        for (String id : victimMapIds.get(victimType)) {
            //Get input details from the cms mapping
            VictimCmsDetails victimCmsDetails = victimDetailsToCmsMap.get(id);
            //Get details from Cms
            HttpResponseWrapper responseCms = victimService.getVictimDetailsFromCMS(context.get("caseId"));
            //assert for input = output
            VictimCaseAppAssertions.assertCMSPersonalDetails(id, victimCmsDetails, responseCms);
            Thread.sleep(2000);

            //Get input details from the VCA mapping
            VictimVcaDetails victimVcaDetails = victimDetailsToVcaMap.get(idGuidMap.get(id));
            //Get output details from the Get request from VCA
            HttpResponseWrapper responseVca = victimService.getVictimDetailsFromVca(idGuidMap.get(id));
            VictimCaseAppAssertions.assertVCAPersonalDetails(idGuidMap.get(id), victimVcaDetails, responseVca);
            Thread.sleep(2000);
        }
    }

    @When("the {string} personal details are updated to CMS")
    public void updateVictimPersonalDetailToCMS(String victimType) throws InterruptedException {

        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, VictimCmsDetails> victimDetailsToCmsMap = context.get("victimDetailsToCmsMap");

        for (String id : victimMapIds.get(victimType)) {
            VictimCmsDetails victimCmsDetails = updateVictimPersonalDetailsToCMS();
            victimService.updateVictimPersonalDetailsToCMS(victimCmsDetails, context.get("caseId"), id);
            victimDetailsToCmsMap.put(id, victimCmsDetails);
            Thread.sleep(2000);
        }
        context.set("victimDetailsToCmsMap", victimDetailsToCmsMap);
    }

    @Then("the {string} personal details are update to VCA")
    public void updatePersonalDetailsToVCA(String victimType) {

        VictimVcaDetails victimVcaDetails;
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, VictimVcaDetails> victimDetailsToVcaMap = context.get("victimDetailsToVcaMap");

        for (String id : victimMapIds.get(victimType)) {
            victimVcaDetails = updateVictimPersonalDetailsInVca();
            victimService.updateVictimPersonalDetailsInVCA(idGuidMap.get(id), convertObjectToString(victimVcaDetails));
            victimDetailsToVcaMap.put(idGuidMap.get(id), victimVcaDetails);
        }
        context.set("victimDetailsToVcaMap", victimDetailsToVcaMap);
    }

    @When("the following cps-contacts are added to {string} in VCA")
    public void addCpsContacts(String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");

        Map<Integer, CpsContacts> cpsContactMap = new HashMap<>();
        context.set("cpsContactMap", cpsContactMap);

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
                CpsContactsType cpsContactTypeCode = CpsContactsType.fromString(row.get("cpsContactType"));
                CpsContacts cpsContacts = addCpsContact(cpsContactTypeCode.getValue());
                String requestBody = convertObjectToString(cpsContacts);
                victimService.addCpsContacts(idGuidMap.get(id), requestBody);
                cpsContactMap.put(cpsContactTypeCode.getValue(), cpsContacts);
            }
            context.set("cpsContactMap", cpsContactMap);
        }
    }

    @Then("the added cps-contacts for {string} are verified")
    public void verifyAddedCpsContact(String victimType) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, CpsContacts> cpsContactMap = context.get("cpsContactMap");

        for (String id : victimMapIds.get(victimType)) {
            for (Integer key : cpsContactMap.keySet()) {
                CpsContacts cpsContacts = cpsContactMap.get(key);
                HttpResponseWrapper response = victimService.listCpsContactDetails(idGuidMap.get(id));
                VictimCaseAppAssertions.assertCpsContacts(key, cpsContacts, response);
            }
        }
    }

    @When("the following cps-contacts are updated to {string} in VCA")
    public void updateCpsContacts(String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");

        Map<Integer, CpsContacts> cpsContactUpdateMap = new HashMap<>();
        context.set("cpsContactUpdateMap", cpsContactUpdateMap);

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
                CpsContactsType cpsContactsTypeCode = CpsContactsType.fromString(row.get("cpsContactType"));
                CpsContacts cpsContacts = updateCpsContact(cpsContactsTypeCode.getValue());
                String requestBody = convertObjectToString(cpsContacts);
                victimService.updateVictimContacts(idGuidMap.get(id), requestBody);
                cpsContactUpdateMap.put(cpsContactsTypeCode.getValue(), cpsContacts);
            }
            context.set("cpsContactUpdateMap", cpsContactUpdateMap);
        }
    }

    @Then("the updated cps-contacts for {string} are verified")
    public void verifyUpdatedCpsContact(String victimType) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, CpsContacts> cpsContactUpdateMap = context.get("cpsContactUpdateMap");

        for (String id : victimMapIds.get(victimType)) {
            for (Integer key : cpsContactUpdateMap.keySet()) {
                CpsContacts victimCpsContacts = cpsContactUpdateMap.get(key);
                HttpResponseWrapper response = victimService.listCpsContactDetails(idGuidMap.get(id));
                VictimCaseAppAssertions.assertCpsContacts(key, victimCpsContacts, response);
            }
        }
    }

    @Then("the case cms contact is verified in VCA")
    public void verifyCaseCmsContactsInVCA() {
        String caseId = context.get("caseId");
        HttpResponseWrapper response = victimService.caseCmsContactList(caseId);
        String cm01RequestPayload = context.get("modifiedCM01RequestPayload");
        VictimCaseAppAssertions.assertCaseCmsContact(cm01RequestPayload, response);
        /* TO-DO - Need to fix the assertions */
    }

    @When("the following category type is added to {string} in VCA")
    public void addCategoryToVictim(String victimType, DataTable dataTable) {
        VictimCmsDetails victimCmsDetails;
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, VictimCmsDetails> victimDetailsToCmsMap = context.get("victimDetailsToCmsMap");

        Set<String> categoryValues = new LinkedHashSet<>();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            CategoryType categoryTypeCode = CategoryType.fromString(row.get("categoryType"));
            categoryValues.addAll(Arrays.asList(categoryTypeCode.getValue().split(",")));
        }
        String allCategoryValues = String.join(",", categoryValues);

        for (String id : victimMapIds.get(victimType)) {
            victimCmsDetails = addCategoryToVictimInVca(allCategoryValues);
            victimService.addVictimCategoryInVca(victimCmsDetails, context.get("caseId"), id);
            victimDetailsToCmsMap.put(id, victimCmsDetails);
        }
        context.set("victimDetailsToCmsMap", victimDetailsToCmsMap);
    }

    @Then("the added categories to {string} are verified")
    public void categoryAreVerified(String victimType) {

        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, VictimCmsDetails> victimDetailsToCmsMap = context.get("victimDetailsToCmsMap");
        for (String id : victimMapIds.get(victimType)) {
            //Get input details from the cms mapping
            VictimCmsDetails victimCmsDetails = victimDetailsToCmsMap.get(id);
            //Get details from Cms
            HttpResponseWrapper response = victimService.getVictimDetailsFromCMS(context.get("caseId"));
            //assert for input = output
            VictimCaseAppAssertions.assertCategoryList(id, victimCmsDetails, response);
            /* TO-DO - Need to fix the assertions */
        }
    }

    @When("the following meetings are not offered to {string} in VCA")
    public void meetingAreNotOffered(String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        Map<Integer, Meetings> meetingDetailsMap = new HashMap<>();
        context.set("meetingDetailsMap", meetingDetailsMap);

        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
//                String meetingType = row.get("meetingType");
//                String reason = row.get("notOfferedReason");
                MeetingType meetingTypeCode = MeetingType.fromString(row.get("meetingType"));//Enum
                Meetings meetingNotOffered = meetingNotOffered(meetingTypeCode.getValue(), row.get("notOfferedReason"));//Class
                victimService.addMeetingsNotOffered(idGuidMap.get(id), convertObjectToString(meetingNotOffered));
                meetingDetailsMap.put(meetingTypeCode.getValue(), meetingNotOffered);
            }
            context.set("meetingNotOfferMap", meetingDetailsMap);
        }
    }

    @Then("meetings not offered to {string} are verified")
    public void meetingNotOfferedVerified(String victimType) {
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Meetings> meetingDetailsMap = context.get("meetingDetailsMap");

        for (String id : victimMapIds.get(victimType)) {
            for (Integer meetingTypeCode : meetingDetailsMap.keySet()) {
                Meetings meetingNotOffered = meetingDetailsMap.get(meetingTypeCode);
                HttpResponseWrapper response = victimService.listMeetingNotOfferedDetails(idGuidMap.get(id), meetingTypeCode);
                VictimCaseAppAssertions.assertMeetingNotOfferedDetails(meetingTypeCode, meetingNotOffered, response);
            }
        }
    }

    @When("the following meetings are offered using following methods to {string} in VCA")
    public void meetingOfferedMethod(String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        Map<Integer, Meetings> meetingDetailsMap = new HashMap<>();
        context.set("meetingDetailsMap", meetingDetailsMap);

        Map<Integer, String> meetingContextGuidMap = new HashMap<>();
        context.set("meetingContextGuidMap", meetingContextGuidMap);

        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
//                String meetingType = row.get("meetingType");
//                String offerMethod = row.get("offerMethod");
                MeetingType meetingTypeCode = MeetingType.fromString(row.get("meetingType")); //Enum
                OfferMethod offerMethodCode = OfferMethod.fromString(row.get("offerMethod")); //Enum
                Meetings meetingOfferedMethod = meetingOfferMethod(meetingTypeCode.getValue(), offerMethodCode.getValue());//Class
                String meetingContextGuid = victimService.addMeetingOfferedMethod(idGuidMap.get(id), convertObjectToString(meetingOfferedMethod));
                meetingDetailsMap.put(meetingTypeCode.getValue(), meetingOfferedMethod);
                meetingContextGuidMap.put(meetingTypeCode.getValue(), meetingContextGuid);
            }
            context.set("meetingDetailsMap", meetingDetailsMap);
            context.set("meetingContextGuidMap", meetingContextGuidMap);
        }
    }

    @Then("offered meeting type and method is verified for {string} in VCA")
    public void verifyOfferMeetingMethod(String victimType) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Meetings> meetingDetailsMap = context.get("meetingDetailsMap");

        for (String id : victimMapIds.get(victimType)) {
            for (Integer meetingTypeCode : meetingDetailsMap.keySet()) {
                Meetings meetingOfferMethod = meetingDetailsMap.get(meetingTypeCode);
                HttpResponseWrapper response = victimService.listMeetingOffered(idGuidMap.get(id), meetingTypeCode);
                VictimCaseAppAssertions.assertMeetingOfferMethod(meetingTypeCode, meetingOfferMethod, response);
            }
        }
    }

    @When("the following offered meeting response from {string} is recorded in VCA")
    public void meetingAcceptDeclineNoResponse(String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<Integer, Meetings> meetingDetailsMap = context.get("meetingDetailsMap");
        Map<Integer, String> meetingContextGuidMap = context.get("meetingContextGuidMap");

        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
//                String meetingType = row.get("meetingType");
//                String meetingOfferResponseMethod = row.get("offerResponseMethod");
//                String meetingOfferResponse = row.get("offerResponse");
                MeetingType meetingTypeCode = MeetingType.fromString(row.get("meetingType")); //Enum
                OfferMethod meetingResponseMethodCode = OfferMethod.fromString(row.get("offerResponseMethod")); //Enum
                String meetingContextGuid = meetingContextGuidMap.get(meetingTypeCode.getValue());
                Meetings meetingResponse = meetingOfferResponse(meetingTypeCode.getValue(), meetingResponseMethodCode.getValue(), meetingContextGuid, row.get("offerResponse"));
                victimService.addMeetingOfferedResponse(idGuidMap.get(id), convertObjectToString(meetingResponse));
            }
            context.set("meetingDetailsMap", meetingDetailsMap);
        }
    }

    @Then("meeting response is verified for {string} is recorded in VCA")
    public void verifyMeetingResponse(String victimType) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Meetings> meetingDetailsMap = context.get("meetingDetailsMap");

        for (String id : victimMapIds.get(victimType)) {
            for (Integer meetingTypeCode : meetingDetailsMap.keySet()) {
                Meetings meetingOfferMethod = meetingDetailsMap.get(meetingTypeCode);
                HttpResponseWrapper response = victimService.listMeetingOffered(idGuidMap.get(id), meetingTypeCode);
                VictimCaseAppAssertions.assertMeetingResponse(meetingOfferMethod, response);
                /* TO-DO - Need to fix the assertions */
            }
        }
    }

    @When("the accepted meeting is arranged using following for {string} in VCA")
    public void meetingArranged(String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<Integer, Meetings> meetingDetailsMap = context.get("meetingDetailsMap");
        Map<Integer, String> meetingContextGuidMap = context.get("meetingContextGuidMap");

        Map<Integer, String> meetingDetailsGuidMap = new HashMap<>();
        context.set("meetingDetailsGuidMap", meetingDetailsGuidMap);

        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
                MeetingType meetingTypeCode = MeetingType.fromString(row.get("meetingType")); //Enum
                Meetings meetingsArranged = meetingArrange(meetingTypeCode.getValue(), meetingContextGuidMap.get(meetingTypeCode.getValue()),
                        MeetingSource.fromString(row.get("meetingSource")).getValue(),
                        MeetingMethod.fromString(row.get("meetingMethod")).getValue(),
                        MeetingLocation.fromString(row.get("locationType")).getValue(),
                        row.get("locationName"));
                String meetingDetailsGuid = victimService.arrangeMeeting(idGuidMap.get(id), convertObjectToString(meetingsArranged));
                meetingDetailsMap.put(meetingTypeCode.getValue(), meetingsArranged);
                meetingDetailsGuidMap.put(meetingTypeCode.getValue(), meetingDetailsGuid);
            }
            context.set("meetingDetailsMap", meetingDetailsMap);
            context.set("meetingDetailsGuidMap", meetingDetailsGuidMap);
        }
    }

    @When("the meeting attendees are added and make {string} as chair person for {string} meeting")
    public void meetingAttendees(String chairPerson, String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Meetings> meetingDetailsMap = context.get("meetingDetailsMap");
        Map<Integer, String> meetingDetailsGuidMap = context.get("meetingDetailsGuidMap");

        Map<Integer, String> meetingAttendeesDetailsMap = new HashMap<>();
        context.set("meetingAttendeesDetailsMap", meetingAttendeesDetailsMap);

        Map<Integer, String> meetingAttendeesGuidsMap = new HashMap<>();
        context.set("meetingAttendeesGuidsMap", meetingAttendeesGuidsMap);

        Map<Integer, Map<String, String>> meetingTypeAttendeesGuidsMap = new HashMap<>();
        context.set("meetingTypeAttendeesGuidsMap", meetingTypeAttendeesGuidsMap);

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (String id : victimMapIds.get(victimType)) {
            for (Integer meetingTypeCodeKey : meetingDetailsMap.keySet()) {
                String meetingDetailsGuid = meetingDetailsGuidMap.get(meetingTypeCodeKey);

                List<String> meetingAttendeesRoles = rows.stream()
                        .map(row -> row.get("meetingAttendeesRoles"))
                        .collect(Collectors.toList());

                String payloadBody = meetingAttendeesRequestBody(chairPerson, meetingAttendeesRoles);
                Map<String, String> attendeeGuidList = victimService.addMeetingAttendees(meetingDetailsGuid, payloadBody);
                meetingAttendeesDetailsMap.put(meetingTypeCodeKey, payloadBody);
                meetingTypeAttendeesGuidsMap.put(meetingTypeCodeKey, attendeeGuidList);

            }
            context.set("meetingAttendeesDetailsMap", meetingAttendeesDetailsMap);
            context.set("meetingTypeAttendeesGuidsMap", meetingTypeAttendeesGuidsMap);
        }

    }

    @Then("the arranged meeting and attendees details for {string} in verified")
    public void verifyArrangedAttendeesMeetings(String victimType) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Meetings> meetingDetailsMap = context.get("meetingDetailsMap");
        Map<Integer, String> meetingContextGuidMap = context.get("meetingContextGuidMap");
        Map<Integer, String> meetingDetailsGuidMap = context.get("meetingDetailsGuidMap");
        Map<Integer, String> meetingAttendeesDetailsMap = context.get("meetingAttendeesDetailsMap");

        for (String id : victimMapIds.get(victimType)) {
            for (Integer meetingTypeCode : meetingDetailsMap.keySet()) {
                Meetings meetingDetails = meetingDetailsMap.get(meetingTypeCode);
                HttpResponseWrapper response = victimService.getMeetingArranged(meetingDetailsGuidMap.get(meetingTypeCode));
                VictimCaseAppAssertions.assertArrangedMeeting(meetingDetails, response);
                /* TO-DO - Need to fix the assertions */
            }
        }
    }

    @When("the arranged meeting is cancelled with a reason for {string} in VCA")
    public void cancelArrangedMeeting(String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Meetings> meetingDetailsMap = context.get("meetingDetailsMap");
        Map<Integer, String> meetingContextGuidMap = context.get("meetingContextGuidMap");
        Map<Integer, String> meetingDetailsGuidMap = context.get("meetingDetailsGuidMap");
        Map<Integer, MeetingCancel> meetingCancelDetailsMap = new HashMap<>();
        context.set("meetingCancelDetailsMap", meetingCancelDetailsMap);

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
                MeetingType meetingTypeCode = MeetingType.fromString(row.get("meetingType"));
                Meetings meetingsInputDetails = meetingDetailsMap.get(meetingTypeCode.getValue());
                MeetingCancel meetingsCancelled = meetingCancel(meetingTypeCode.getValue(), meetingContextGuidMap.get(meetingTypeCode.getValue()),
                        row.get("cancellationReason"), meetingsInputDetails);
                victimService.cancelMeeting(meetingDetailsGuidMap.get(meetingTypeCode.getValue()), convertObjectToString(meetingsCancelled));
                meetingCancelDetailsMap.put(meetingTypeCode.getValue(), meetingsCancelled);
            }
            context.set("meetingCancelDetailsMap", meetingCancelDetailsMap);
        }
    }

    @Then("the cancelled meeting details for {string} in verified")
    public void verifyCancelArrangedMeeting(String victimType) {
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Meetings> meetingDetailsMap = context.get("meetingDetailsMap");
        Map<Integer, String> meetingContextGuidMap = context.get("meetingContextGuidMap");
        Map<Integer, String> meetingDetailsGuidMap = context.get("meetingDetailsGuidMap");
        Map<Integer, MeetingCancel> meetingCancelDetailsMap = context.get("meetingCancelDetailsMap");

        for (String id : victimMapIds.get(victimType)) {
            for (Integer meetingTypeCode : meetingDetailsMap.keySet()) {
                Meetings meetingDetails = meetingDetailsMap.get(meetingTypeCode);
                MeetingCancel meetingCancelDetails = meetingCancelDetailsMap.get(meetingTypeCode);
                HttpResponseWrapper response = victimService.getMeetingArranged(meetingDetailsGuidMap.get(meetingTypeCode));
                VictimCaseAppAssertions.assertCancelArrangedMeeting(meetingCancelDetails, response);
                /* TO-DO - Need to fix the assertions */
            }

        }
    }

    @When("the arranged meeting is conducted with following details and logged for {string} in VCA")
    public void logConductedMeetingDetails(String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Meetings> meetingDetailsMap = context.get("meetingDetailsMap");
        Map<Integer, String> meetingContextGuidMap = context.get("meetingContextGuidMap");
        Map<Integer, String> meetingDetailsGuidMap = context.get("meetingDetailsGuidMap");
        Map<Integer, MeetingLogged> meetingLoggedDetailsMap = new HashMap<>();
        context.set("meetingLoggedDetailsMap", meetingLoggedDetailsMap);

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
                MeetingType meetingTypeCode = MeetingType.fromString(row.get("meetingType"));
                Meetings meetingsInputDetails = meetingDetailsMap.get(meetingTypeCode.getValue());

                String meetingDuration = row.get("meetingDuration");
                int agreedToResearch = MeetingContactResearch.fromString(row.get("agreedToResearch")).getValue();
                String noteToOci = row.get("noteToOci");
                String noteToVictim = row.get("noteToVictim");
                String proposedActions = row.get("proposedActions");

                MeetingLogged meetingLogged = meetingLog(meetingTypeCode.getValue(), meetingContextGuidMap.get(meetingTypeCode.getValue()),
                        meetingDuration, agreedToResearch, noteToOci, noteToVictim, proposedActions, meetingsInputDetails);

                victimService.loggedMeeting(meetingDetailsGuidMap.get(meetingTypeCode.getValue()), convertObjectToString(meetingLogged));
                meetingLoggedDetailsMap.put(meetingTypeCode.getValue(), meetingLogged);

            }
            context.set("meetingLoggedDetailsMap", meetingLoggedDetailsMap);
        }
    }

    @When("the following attendees attended the meeting lead by {string}")
    public void logMeetingAttendees(String chairPerson, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Meetings> meetingDetailsMap = context.get("meetingDetailsMap");
        Map<Integer, String> meetingDetailsGuidMap = context.get("meetingDetailsGuidMap");
        Map<Integer, MeetingLogged> meetingLoggedDetailsMap = context.get("meetingLoggedDetailsMap");
        Map<Integer, Map<String, String>> meetingTypeAttendeesGuidsMap = context.get("meetingTypeAttendeesGuidsMap");
        Map<Integer, String> meetingAttendeesDetailsMap = context.get("meetingAttendeesDetailsMap");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Integer meetingTypeCodeKey : meetingLoggedDetailsMap.keySet()) {
            String meetingDetailsGuid = meetingDetailsGuidMap.get(meetingTypeCodeKey);
            Map<String, String> meetingAttendeeGuids = meetingTypeAttendeesGuidsMap.get(meetingTypeCodeKey);
            List<String> logAttendeesRoles = rows.stream()
                    .map(row -> row.get("logAttendeesRoles"))
                    .collect(Collectors.toList());

            String payloadBody = logAttendeesRequestBody(meetingAttendeeGuids, chairPerson, logAttendeesRoles);
            victimService.logMeetingAttendees(meetingDetailsGuid, payloadBody);
            meetingAttendeesDetailsMap.put(meetingTypeCodeKey, payloadBody);

        }
        context.set("meetingAttendeesDetailsMap", meetingAttendeesDetailsMap);
    }

    @Then("the logged meeting details are verified for {string} in VCA")
    public void verifyLoggedMeetings(String victimType) {
        Map<Integer, String> meetingDetailsGuidMap = context.get("meetingDetailsGuidMap");
        Map<Integer, MeetingLogged> meetingLoggedDetailsMap = context.get("meetingLoggedDetailsMap");
        Map<Integer, Map<String, String>> meetingTypeAttendeesGuidsMap = context.get("meetingTypeAttendeesGuidsMap");
        Map<Integer, String> meetingAttendeesDetailsMap = context.get("meetingAttendeesDetailsMap");

        for (Integer meetingTypeCodeKey : meetingLoggedDetailsMap.keySet()) {
            String meetingDetailsGuid = meetingDetailsGuidMap.get(meetingTypeCodeKey);
            /* TO-DO - Need to add the verification steps */
        }

    }

    @When("the following {string} communication are logged to {string} in VCA")
    public void logOtherCommunication(String journeyType, String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        Map<Integer, Communication> otherCommunicationMap = new HashMap<>();
        context.set("otherCommunicationMap", otherCommunicationMap);

        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
                String personContacted = row.get("personContacted");
                String purpose = row.get("purpose");

                CommunicationType communicationType = CommunicationType.fromString(row.get("communicationType"));
                CommunicationDirection direction = CommunicationDirection.fromString(row.get("direction"));
                JourneyType journeyTypeCode = JourneyType.fromString(journeyType);
                Communication otherCommunication = logOtherComms(journeyTypeCode.getValue(), communicationType.getValue(), direction.getValue(), personContacted, purpose);
                victimService.addOtherCommunication(idGuidMap.get(id), convertObjectToString(otherCommunication));
                otherCommunicationMap.put(communicationType.getValue(), otherCommunication);
            }
            context.set("otherCommunicationMap", otherCommunicationMap);
        }
    }

    @Then("verify the logged other communication for the {string} in VCA")
    public void verifyOtherComms(String victimType) {
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Communication> otherCommunicationMap = context.get("otherCommunicationMap");

        for (String id : victimMapIds.get(victimType)) {
            for (Integer communicationType : otherCommunicationMap.keySet()) {
                int CommsType = communicationType;
                /* TO-DO - Need to add the verification steps */

            }
        }

    }

    @When("the following communication for victim not contacted is logged to {string} in VCA")
    public void victimNotContactedComms(String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");

        Map<Integer, Decision> decisionListMap = new HashMap<>();
        context.set("decisionListMap", decisionListMap);

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (String id : victimMapIds.get(victimType)) {

            for (Map<String, String> row : rows) {
                TaskList taskType = TaskList.fromString(row.get("decisionType"));
                Decision createDecision = createVictimNotContactDecision(taskType.getValue());
                victimService.informDecision(taskType.getValue(), idGuidMap.get(id), convertObjectToString(createDecision));
                decisionListMap.put(taskType.getValue(), createDecision);
            }

            context.set("decisionListMap", decisionListMap);
        }

    }

    @Then("verify the logged decision to charge communication for the {string} in VCA")
    public void verifyDecisionComm(String victimType) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Decision> decisionListMap = context.get("decisionListMap");

        for (String id : victimMapIds.get(victimType)) {
            for (Integer decisionType : decisionListMap.keySet()) {
                int decType = decisionType;
                /* TO-DO - Need to add the verification steps */

            }

        }

    }

    @When("the following tasks to log communication are created for {string} in VCA")
    public void createLogCommunicationTask(String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");

        Map<Integer, Task> createTaskListMap = new HashMap<>();
        context.set("createTaskListMap", createTaskListMap);

        Map<Integer, Integer> taskTypeTaskIdMap = new HashMap<>();
        context.set("taskTypeTaskIdMap", taskTypeTaskIdMap);

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (String id : victimMapIds.get(victimType)) {

            for (Map<String, String> row : rows) {
                TaskList taskType = TaskList.fromString(row.get("taskType"));
                Task createLogCommsTask = createLogCommsTask(taskType.getValue(), victimService.getUserPartyId());

                Integer taskTypeTaskId = victimService.logCommunicationTask(idGuidMap.get(id), convertObjectToString(createLogCommsTask));
                createTaskListMap.put(taskType.getValue(), createLogCommsTask);
                taskTypeTaskIdMap.put(taskType.getValue(), taskTypeTaskId);
            }
            context.set("createTaskListMap", createTaskListMap);
            context.set("taskTypeTaskIdMap", taskTypeTaskIdMap);
        }

    }

    @Then("verify the task to log communication for the {string} in VCA")
    public void verifyLogCommunicationTask(String victimType) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Task> createTaskListMap = context.get("createTaskListMap");

        for (String id : victimMapIds.get(victimType)) {
            for (Integer taskType : createTaskListMap.keySet()) {
                int taskTyp = taskType;
                /* TO-DO - Need to add the verification steps */

            }

        }

    }

    @When("the charging type {string} is added to case information to {string} in VCA")
    public void chargingType(String chargeType, String victimType) {

        VictimVcaDetails victimVcaDetails;
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, VictimVcaDetails> victimDetailsToVcaMap = context.get("victimDetailsToVcaMap");


        for (String id : victimMapIds.get(victimType)) {
            VictimVcaDetails caseChargeDetails = addChargeTypeToVCA(ChargeType.fromString(chargeType).getValue(), victimService.getUserPartyId());
            victimService.addCaseChargeTypeToVCA(idGuidMap.get(id), convertObjectToString(caseChargeDetails));
            victimDetailsToVcaMap.put(idGuidMap.get(id), caseChargeDetails);
        }
        context.set("victimDetailsToVcaMap", victimDetailsToVcaMap);

    }

    @Then("the case charging type is verified for {string} in VCA")
    public void verifyChargeType(String victimType) {
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, VictimVcaDetails> victimDetailsToVcaMap = context.get("victimDetailsToVcaMap");

        for (String id : victimMapIds.get(victimType)) {

            String ids = id;
            /* TO-DO - Need to add the verification steps */

        }

    }

    @When("the first telephone call attempt is successful to inform victim with following details for {string} in VCA")
    public void firstCallSuccessful(String victimType, DataTable dataTable) {
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");

        Map<Integer, TelephoneCommunication> teleCommsListMap = new HashMap<>();
        context.set("teleCommsListMap", teleCommsListMap);

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
                JourneyType journeyTypeCode = JourneyType.fromString(row.get("journeyType"));
                String informVictim = row.get("informVictim");
                CommunicationDirection callDirection = CommunicationDirection.fromString(row.get("callDirection"));
                String notes = row.get("notes");
                TelephoneCommunication firstCall = firstTeleCall(journeyTypeCode.getValue(), informVictim, callDirection.getValue(), notes);
                victimService.addFirstCallAttempt(idGuidMap.get(id), convertObjectToString(firstCall));
                teleCommsListMap.put(journeyTypeCode.getValue(), firstCall);
            }
            context.set("communicationListMap", teleCommsListMap);
        }
    }

    @When("follow up as below for {string} in VCA")
    public void followUp(String victimType, DataTable dataTable) {
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");

        Map<String, FollowUpCommunication> followUpCommsListMap = new HashMap<>();
        context.set("followUpCommsListMap", followUpCommsListMap);

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
                JourneyType journeyTypeCode = JourneyType.fromString(row.get("journeyType"));
                String followUpMethod = row.get("followUpMethod").toLowerCase(Locale.ROOT);
                String notes = row.get("notes");
                FollowUpCommunication finalFollow = finalFollowUp(journeyTypeCode.getValue(), notes, followUpMethod);
                victimService.addFollowUpComms(idGuidMap.get(id), followUpMethod, convertObjectToString(finalFollow));
                followUpCommsListMap.put(followUpMethod, finalFollow);
            }
            context.set("followUpCommsListMap", followUpCommsListMap);
        }
    }

    @When("verify the charge decision communication for {string} in VCA")
    public void verifyChargeDecision(String victimType) {
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, TelephoneCommunication> teleCommsListMap = context.get("teleCommsListMap");
        Map<String, FollowUpCommunication> followUpCommsListMap = context.get("followUpCommsListMap");

        for (String id : victimMapIds.get(victimType)) {
            String ids = id;
            /* TO-DO - Need to add the verification steps */

        }
    }

    @When("the first telephone call attempt is un-successful to inform victim with following details for {string} in VCA")
    public void firstCallUnSuccessful(String victimType, DataTable dataTable) {
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");

        Map<Integer, TelephoneCommunication> teleCommsListMap = new HashMap<>();
        context.set("teleCommsListMap", teleCommsListMap);

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
                JourneyType journeyTypeCode = JourneyType.fromString(row.get("journeyType"));
                String informVictim = row.get("informVictim");
                CommunicationDirection callDirection = CommunicationDirection.fromString(row.get("callDirection"));
                String notes = row.get("notes");
                TelephoneCommunication firstCall = firstTeleCall(journeyTypeCode.getValue(), informVictim, callDirection.getValue(), notes);
                victimService.addFirstCallAttempt(idGuidMap.get(id), convertObjectToString(firstCall));
                String smsSent = row.get("smsSent").toLowerCase(Locale.ROOT);
                TelephoneCommunication smsSentStatus = firstCallSmsStatus(journeyTypeCode.getValue(), informVictim, callDirection.getValue(), notes, smsSent);
                victimService.addFirstCallAttemptSms(idGuidMap.get(id), convertObjectToString(smsSentStatus));

                if ("yes".equalsIgnoreCase(smsSent)) {
                    SmsCommunication smsSend = firstCallSmsSend(journeyTypeCode.getValue());
                    victimService.addSmsSendDetails(idGuidMap.get(id), convertObjectToString(smsSend));

                }
//                teleCommsListMap.put(journeyTypeCode.getValue(),smsSentStatus);
                }
                context.set("teleCommsListMap", teleCommsListMap);
            }
        }


    }