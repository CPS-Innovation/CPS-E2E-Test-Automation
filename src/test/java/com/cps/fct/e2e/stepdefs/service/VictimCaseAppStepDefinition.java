package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.enums.*;
import com.cps.fct.e2e.model.victimCaseApp.*;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.ddei.CommonService;
import com.cps.fct.e2e.utils.services.ddei.CaseService;
import com.cps.fct.e2e.utils.services.ddei.VictimService;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimCaseAppPayloadBuilder;
import com.cps.fct.e2e.utils.services.ddei.responseAssertions.VictimCaseAppAssertions;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.picocontainer.annotations.Inject;

import java.util.*;

import static com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimCaseAppPayloadBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;


public class VictimCaseAppStepDefinition {
    @Inject
    private CaseService caseService;

    @Inject
    private VictimService victimService;

    @Inject
    private ScenarioContext context;

    @Inject
    private CommonService service;

    public VictimCaseAppStepDefinition() {
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


//
//        Map<String, String> categoryMap = new HashMap<>();
//        context.set("categoryMap", categoryMap);
//

//        Map<String, Integer> victimContactTypeMap = new HashMap<>();
//        context.set("victimContactTypeMap", victimContactTypeMap);
//
//        Map<String, VictimWitnessCMSContact> victimWitnessCMSContactMap = new HashMap<>();
//        context.set("victimWitnessCMSContactMap", victimWitnessCMSContactMap);
//
//        Map<String, VictimLiaisonOfficerDetails> victimWitnessVLODetails = new HashMap<>();
//        context.set("victimWitnessVLODetails", victimWitnessVLODetails);

    }

    @When("the {string} is onboarded as {string} service lead in VCA")
    public void victimOnboardForService(String victimType, String service) {

        CaseInfo victimCaseInfo;
        VictimVcaDetails victimVcaDetails;

        OnboardService serviceTypeCode = OnboardService.fromString(service);
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
                String meetingType = row.get("meetingType");
                String reason = row.get("notOfferedReason");
                MeetingType meetingTypeCode = MeetingType.fromString(meetingType);//Enum
                Meetings meetingNotOffered = meetingNotOffered(meetingTypeCode.getValue(), reason);//Class
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
                String meetingType = row.get("meetingType");
                String offerMethod = row.get("offerMethod");
                MeetingType meetingTypeCode = MeetingType.fromString(meetingType); //Enum
                MeetingMethod meetingMethodCode = MeetingMethod.fromString(offerMethod); //Enum
                Meetings meetingOfferedMethod = meetingOfferMethod(meetingTypeCode.getValue(), meetingMethodCode.getValue());//Class
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
    public void meetingAcceptDeclineNoResponse(String victimType,DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<Integer, Meetings> meetingDetailsMap = context.get("meetingDetailsMap");
        Map<Integer, String> meetingContextGuidMap = context.get("meetingContextGuidMap");

        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
                String meetingType = row.get("meetingType");
                String meetingOfferResponseMethod = row.get("offerResponseMethod");
                String meetingOfferResponse = row.get("offerResponse");
                MeetingType meetingTypeCode = MeetingType.fromString(meetingType); //Enum
                MeetingMethod meetingResponseMethodCode = MeetingMethod.fromString(meetingOfferResponseMethod); //Enum
                String meetingContextGuid = meetingContextGuidMap.get(meetingTypeCode.getValue());
                Meetings meetingResponse = meetingOfferResponse(meetingTypeCode.getValue(), meetingResponseMethodCode.getValue(), meetingContextGuid, meetingOfferResponse);
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
            }
        }
    }









}