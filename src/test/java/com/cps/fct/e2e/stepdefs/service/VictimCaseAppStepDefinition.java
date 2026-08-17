package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.enums.CpsContactsType;
import com.cps.fct.e2e.enums.MeetingType;
import com.cps.fct.e2e.enums.OnboardService;
import com.cps.fct.e2e.enums.CategoryType;
import com.cps.fct.e2e.model.victimCaseApp.*;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.ddei.CommonService;
import com.cps.fct.e2e.utils.services.ddei.CaseService;
import com.cps.fct.e2e.utils.services.ddei.VictimService;
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
        HttpResponseWrapper response;
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, VictimCmsDetails> victimDetailsToCmsMap = context.get("victimDetailsToCmsMap");
        Map<String, VictimVcaDetails> victimDetailsToVcaMap = context.get("victimDetailsToVcaMap");

        for (String id : victimMapIds.get(victimType)) {
            //Get input details from the cms mapping
            VictimCmsDetails victimCmsDetails = victimDetailsToCmsMap.get(id);
            //Get details from Cms
            response = victimService.getVictimDetailsFromCMS(context.get("caseId"));
            //assert for input = output
            VictimCaseAppAssertions.assertCMSPersonalDetails(id, victimCmsDetails, response);
            Thread.sleep(2000);

            //Get input details from the VCA mapping
            VictimVcaDetails victimVcaDetails = victimDetailsToVcaMap.get(idGuidMap.get(id));
            //Get output details from the Get request from VCA
            response = victimService.getVictimDetailsFromVca(idGuidMap.get(id));
            VictimCaseAppAssertions.assertVCAPersonalDetails(idGuidMap.get(id), victimVcaDetails, response);
            Thread.sleep(2000);
        }
    }

    @When("the {string} personal details are updated to CMS")
    public void updateVictimPersonalDetailToCMS(String victimType) throws InterruptedException {
        VictimCmsDetails victimCmsDetails;

        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, VictimCmsDetails> victimDetailsToCmsMap = context.get("victimDetailsToCmsMap");

        for (String id : victimMapIds.get(victimType)) {
            victimCmsDetails = updateVictimPersonalDetailsToCMS();
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
        CpsContacts cpsContacts;

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");

        Map<Integer, CpsContacts> cpsContactMap = new HashMap<>();
        context.set("cpsContactMap", cpsContactMap);

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
                CpsContactsType cpsContactTypeCode = CpsContactsType.fromString(row.get("cpsContactType"));
                cpsContacts = addCpsContact(cpsContactTypeCode.getValue());
                String requestBody = convertObjectToString(cpsContacts);
                victimService.addCpsContacts(idGuidMap.get(id), requestBody);
                cpsContactMap.put(cpsContactTypeCode.getValue(), cpsContacts);
            }
            context.set("cpsContactMap", cpsContactMap);
        }
    }

    @Then("the added cps-contacts for {string} are verified")
    public void verifyAddedCpsContact(String victimType) {
        HttpResponseWrapper response;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, CpsContacts> cpsContactMap = context.get("cpsContactMap");

        for (String id : victimMapIds.get(victimType)) {
            for (Integer key : cpsContactMap.keySet()) {
                CpsContacts cpsContacts = cpsContactMap.get(key);
                response = victimService.listCpsContactDetails(idGuidMap.get(id));
                VictimCaseAppAssertions.assertCpsContacts(key, cpsContacts, response);
            }
        }
    }

    @When("the following cps-contacts are updated to {string} in VCA")
    public void updateCpsContacts(String victimType, DataTable dataTable) {
        CpsContacts cpsContacts;

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");

        Map<Integer, CpsContacts> cpsContactUpdateMap = new HashMap<>();
        context.set("cpsContactUpdateMap", cpsContactUpdateMap);

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
                CpsContactsType cpsContactsTypeCode = CpsContactsType.fromString(row.get("cpsContactType"));
                cpsContacts = updateCpsContact(cpsContactsTypeCode.getValue());
                String requestBody = convertObjectToString(cpsContacts);
//                victimService.addVictimContacts(idGuidMap.get(id), requestBody);
                victimService.updateVictimContacts(idGuidMap.get(id), requestBody);
                cpsContactUpdateMap.put(cpsContactsTypeCode.getValue(), cpsContacts);
            }
            context.set("cpsContactUpdateMap", cpsContactUpdateMap);
        }
    }

    @Then("the updated cps-contacts for {string} are verified")
    public void verifyUpdatedCpsContact(String victimType) {
        HttpResponseWrapper response;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, CpsContacts> cpsContactUpdateMap = context.get("cpsContactUpdateMap");

        for (String id : victimMapIds.get(victimType)) {

            for (Integer key : cpsContactUpdateMap.keySet()) {
                CpsContacts victimCpsContacts = cpsContactUpdateMap.get(key);
                response = victimService.listCpsContactDetails(idGuidMap.get(id));
                VictimCaseAppAssertions.assertCpsContacts(key, victimCpsContacts, response);
            }
        }
    }

    @Then("the case cms contact is verified in VCA")
    public void verifyCaseCmsContactsInVCA() {
        HttpResponseWrapper response;
        String caseId = context.get("caseId");
        response = victimService.caseCmsContactList(caseId);
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
        System.out.println(allCategoryValues);
        for (String id : victimMapIds.get(victimType)) {

            victimCmsDetails = addCategoryToVictimInVca(allCategoryValues);
            victimService.addVictimCategoryInVca(victimCmsDetails, context.get("caseId"), id);
            victimDetailsToCmsMap.put(id, victimCmsDetails);
        }
        context.set("victimDetailsToCmsMap", victimDetailsToCmsMap);
    }

    @Then("the added categories to {string} are verified")
    public void categoryAreVerified(String victimType) {
        HttpResponseWrapper response;
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, VictimCmsDetails> victimDetailsToCmsMap = context.get("victimDetailsToCmsMap");
        for (String id : victimMapIds.get(victimType)) {
            //Get input details from the cms mapping
            VictimCmsDetails victimCmsDetails = victimDetailsToCmsMap.get(id);
            //Get details from Cms
            response = victimService.getVictimDetailsFromCMS(context.get("caseId"));
            //assert for input = output
            VictimCaseAppAssertions.assertCategoryList(id, victimCmsDetails, response);
        }
    }

    @When("the following meetings are not offered to {string} in VCA")
    public void meetingAreNotOffered(String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        Map<Integer, Meetings> meetingOfferMap = new HashMap<>();
        context.set("meetingOfferMap", meetingOfferMap);

        for (String id : victimMapIds.get(victimType)) {
            for (Map<String, String> row : rows) {
                String meetingType = row.get("meetingType");
                String reason = row.get("notOfferedReason");
                MeetingType meetingTypeCode = MeetingType.fromString(meetingType);//Enum

                Meetings meetingNotOffered = meetingNotOffered(meetingTypeCode.getValue(), reason);//Class
                victimService.addMeetingsNotOffered(idGuidMap.get(id), convertObjectToString(meetingNotOffered));
                meetingOfferMap.put(meetingTypeCode.getValue(), meetingNotOffered);
            }
            context.set("meetingOfferMap", meetingOfferMap);
        }
    }

    @Then("meetings not offered to {string} are verified")
    public void meetingNotOfferedVerified(String victimType) {
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<Integer, Meetings> meetingOfferMap = context.get("meetingOfferMap");

        for (String id : witnessVictimMapIds.get(victimType)) {



        }

    }
































































































    @Then("the {string} and {string} category is verified in CMS")
    public void categoryVerifiedInCMS(String witnessType, String victimType) throws InterruptedException {
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, VictimCmsDetails> victimWitnessDetailsToCMS = context.get("victimWitnessDetailsToCMS");

        assertCategoryDetails(witnessType, victimMapIds, victimWitnessDetailsToCMS);
        assertCategoryDetails(victimType, victimMapIds, victimWitnessDetailsToCMS);
    }

    private void assertCategoryDetails(String victimType, Map<String, List<String>> victimMapIds,
                                       Map<String, VictimCmsDetails> victimWitnessDetailsToCMS) {
//        for (String id : victimMapIds.get(victimType)) {
//            //Step1: Validate CMS data -Get input details from the Post request to CMS
//            VictimCmsDetails victimCmsDetails = victimWitnessDetailsToCMS.get(id);
//            // Get output details from the Get request from CMS
//            HttpResponseWrapper response = victimService.listWitnessVictimDetails(context.get("caseId"));
//            VictimWitnessAssertions.assertCategoryDetails(id, victimCmsDetails, response);
//        }
    }


//    @Then("the cms case details should be equal as in cms classic")
//    public void assertCaseContactDetailsInVCA() {
//        String caseId = context.get("caseId");
//        String modifiedRequestJson = context.get("modifiedCM01RequestPayload");

    /// /        System.out.println(modifiedRequestJson);
//        HttpResponseWrapper response = victimService.listVictimWitnessCMSContact(caseId);
//
//        VictimCMSContact expectedOfficerInCaseContact =
//                buildExpectedOfficerInCaseContact(modifiedRequestJson);
//        context.set("expectedOfficerInCaseContact", expectedOfficerInCaseContact);
//
//        VictimCMSContact expectedDefenceFirmContact =
//                buildExpectedDefenceFirmContact(modifiedRequestJson);
//        context.set("expectedDefenceFirmContact", expectedDefenceFirmContact);
//
//        VictimCMSContact expectedDefenceSolicitorContact =
//                buildExpectedDefenceSolicitorContact(modifiedRequestJson);
//        context.set("expectedDefenceSolicitorContact", expectedDefenceSolicitorContact);
//
//        //OFFICER_IN_CASE
//        List<Map<String, Object>> officerInCaseList = JsonPath.read(
//                response.getBody(),
//                "$[?(@.contactType=='OFFICER_IN_CASE')]"
//        );
//        assertThat(officerInCaseList)
//                .as("OFFICER_IN_CASE contact should exist in CMS contacts response")
//                .isNotEmpty();
//        Map<String, Object> officerInCase = officerInCaseList.getFirst();
//
//        VictimCMSContact actualOfficerInCaseContact = VictimCMSContact.builder()
//                .contactType((String) officerInCase.get("contactType"))
//                .name((String) officerInCase.get("name"))
//                .phone((String) officerInCase.get("phone"))
//                .email((String) officerInCase.get("email"))
//                .build();
//        assertThat(actualOfficerInCaseContact.getContactType())
//                .isEqualTo(expectedOfficerInCaseContact.getContactType());
//        assertThat(actualOfficerInCaseContact.getName())
//                .isEqualTo(expectedOfficerInCaseContact.getName());
//        assertThat(actualOfficerInCaseContact.getPhone())
//                .isEqualTo(expectedOfficerInCaseContact.getPhone());
//        assertThat(actualOfficerInCaseContact.getEmail())
//                .isEqualTo(expectedOfficerInCaseContact.getEmail());
//
//        //DEFENCE_FIRM
//        List<Map<String, Object>> defenceFirmList = JsonPath.read(
//                response.getBody(),
//                "$[?(@.contactType=='DEFENCE_FIRM')]"
//        );
//        assertThat(defenceFirmList)
//                .as("DEFENCE_FIRM contact should exist in CMS contacts response")
//                .isNotEmpty();
//        Map<String, Object> defenceFirm = defenceFirmList.getFirst();
//
//        VictimCMSContact actualDefenceFirmContact = VictimCMSContact.builder()
//                .contactType((String) defenceFirm.get("contactType"))
//                .name((String) defenceFirm.get("name"))
//                .phone((String) defenceFirm.get("phone"))
//                .email((String) defenceFirm.get("email"))
//                .build();
//
//        assertThat(actualDefenceFirmContact.getContactType())
//                .isEqualTo(expectedDefenceFirmContact.getContactType());
//        assertThat(actualDefenceFirmContact.getName())
//                .isEqualTo(expectedDefenceFirmContact.getName());
//        assertThat(actualDefenceFirmContact.getPhone())
//                .isEqualTo(expectedDefenceFirmContact.getPhone());
//        assertThat(actualDefenceFirmContact.getEmail())
//                .isEqualTo(expectedDefenceFirmContact.getEmail());
//
//        //DEFENCE_SOLICITOR
//        List<Map<String, Object>> defenceSolicitormList = JsonPath.read(
//                response.getBody(),
//                "$[?(@.contactType=='DEFENCE_SOLICITOR')]"
//        );
//        assertThat(defenceSolicitormList)
//                .as("DEFENCE_SOLICITOR contact should exist in CMS contacts response")
//                .isNotEmpty();
//        Map<String, Object> defenceSolicitor = defenceSolicitormList.getFirst();
//
//        VictimCMSContact actualDefenceSolicitorContact = VictimCMSContact.builder()
//                .contactType((String) defenceSolicitor.get("contactType"))
//                .name((String) defenceSolicitor.get("name"))
//                .build();
//
//        assertThat(actualDefenceSolicitorContact.getContactType())
//                .isEqualTo(expectedDefenceSolicitorContact.getContactType());
//        assertThat(actualDefenceSolicitorContact.getName())
//                .isEqualTo(expectedDefenceSolicitorContact.getName());
//    }
    private CaseCMSContact buildExpectedOfficerInCaseContact(String requestJson) {
        List<String> givenName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.PCDPoliceContactDetails.OfficerCompleting.Name.GivenName");

        List<String> familyName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.PCDPoliceContactDetails.OfficerCompleting.Name.FamilyName");

        List<String> phone = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.PCDPoliceContactDetails.OfficerCompleting.ContactDetails.ContactNumber[0].Number.TelNationalNumber");

        List<String> email = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.PCDPoliceContactDetails.OfficerCompleting.ContactDetails.Email");

        return CaseCMSContact.builder()
                .contactType("OFFICER_IN_CASE")
                .name(familyName.getFirst() + ", " + givenName.getFirst())
                .phone(phone.getFirst())
                .email(email.getFirst())
                .build();
    }

    private CaseCMSContact buildExpectedDefenceFirmContact(String requestJson) {
        String firmName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.Firm");

        String email = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.ContactDetails.Email");

        String phone = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.ContactDetails.ContactNumber[0].Number.TelNationalNumber");

        return CaseCMSContact.builder()
                .contactType("DEFENCE_FIRM")
                .name(firmName)
                .phone(phone)
                .email(email)
                .build();
    }

    private CaseCMSContact buildExpectedDefenceSolicitorContact(String requestJson) {
        String givenName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.Name.GivenName");

        String familyName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.Name.FamilyName");
        return CaseCMSContact.builder()
                .contactType("DEFENCE_SOLICITOR")
                .name(familyName + ", " + givenName)
                .build();
    }


    @Then("assigned Victim liaison officer for {string} is verified")
    public void assignedVloIsVerified(String victimType) throws InterruptedException {

        HttpResponseWrapper response;
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, VictimLiaisonOfficer> victimWitnessVLODetails = context.get("victimWitnessVLODetails");

        for (String id : victimMapIds.get(victimType)) {
            VictimLiaisonOfficer victimLiaisonOfficer = victimWitnessVLODetails.get(idGuidMap.get(id));

            response = victimService.victimLiaisonOfficerFromVCA(idGuidMap.get(id));
            VictimCaseAppAssertions.assertVictimLiaisonOfficerDetails(idGuidMap.get(id), victimLiaisonOfficer, response);
            Thread.sleep(1000);
        }
    }

    @When("the following meetings is not offered to {string} in VCA")
    public void meetingIsNotOffered(String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        Map<Integer, Meetings> victimMeetingDetailsMap = new HashMap<>();
        context.set("victimMeetingDetailsMap", victimMeetingDetailsMap);

        List<Integer> meetingTypeCodeList = new ArrayList<>();

        for (Map<String, String> row : rows) {
            String meeting = row.get("meeting");
            int meetingTypeCode = Integer.parseInt(row.get("meetingTypeCode"));
            String reason = row.get("reason");
            meetingTypeCodeList.add(meetingTypeCode);

            for (String id : victimMapIds.get(victimType)) {
                Meetings meetings = payLoadForAddVictimMeetingDetails(meetingTypeCode, reason);
                victimService.addVictimMeetingDetailsToVCA(idGuidMap.get(id), convertObjectToString(meetings));
                victimMeetingDetailsMap.put(meetingTypeCode, meetings);
            }
            context.set("victimMeetingDetailsMap", victimMeetingDetailsMap);
        }
        context.set("meetingTypeCodeList", meetingTypeCodeList);
    }

    @Then("the offered meeting details of {string} is verified in VCA")
    public void meetingDetailsVerified(String victimType) {
        HttpResponseWrapper response;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Meetings> victimMeetingDetailsMap = context.get("victimMeetingDetailsMap");

        List<Integer> meetingTypeCodeList = context.get("meetingTypeCodeList");

        for (Integer meetingTypeCode : meetingTypeCodeList) {

            for (String id : victimMapIds.get(victimType)) {
                Meetings meetings = victimMeetingDetailsMap.get(meetingTypeCode);
                response = victimService.listVictimMeetingDetails(idGuidMap.get(id), meetingTypeCode);
                VictimCaseAppAssertions.assertMeetingTypeDetails(meetingTypeCode, meetings, (Response) response);
            }
        }
    }

    @When("the following meetings via meeting method is offered to {string} in VCA")
    public void meetingOfferedMethod(String victimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<Integer, Meetings> victimMeetingDetailsMap = new HashMap<>();
        context.set("victimMeetingDetailsMap", victimMeetingDetailsMap);

        List<String> meetingContextGuidList = new ArrayList<>();
        List<Integer> meetingTypeCodeList = new ArrayList<>();
        List<Integer> methodTypeCodeList = new ArrayList<>();

        for (Map<String, String> row : rows) {
            String meeting = row.get("meeting");
            int meetingTypeCode = Integer.parseInt(row.get("meetingTypeCode"));
            meetingTypeCodeList.add(meetingTypeCode);

            int methodTypeCode = Integer.parseInt(row.get("methodTypeCode"));
            methodTypeCodeList.add(methodTypeCode);

            for (String id : victimMapIds.get(victimType)) {
                Meetings meetings = payLoadForAddVictimMeetingMethodDetails(meetingTypeCode, methodTypeCode);
                HttpResponseWrapper response = victimService.addVictimMeetingDetailsToVCA(idGuidMap.get(id), convertObjectToString(meetings));
//                String responseBody = response.getBody();
//                String meetingContextGuid = JsonPath.read(responseBody, "$.value.meetingContextGuid");
                victimMeetingDetailsMap.put(meetingTypeCode, meetings);
//                meetingContextGuidList.add(meetingContextGuid);
            }

        }
        context.set("victimMeetingDetailsMap", victimMeetingDetailsMap);
        context.set("meetingTypeCodeList", meetingTypeCodeList);
        context.set("methodTypeCodeList", methodTypeCodeList);
//        context.set("meetingContextGuidList", meetingContextGuidList);

    }

    @When("offered meetings is {string} by {string} in VCA")
    public void meetingOfferedStatus(String meetingStatus, String victimType) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Meetings> victimMeetingDetailsMap = context.get("victimMeetingDetailsMap");

        List<Integer> meetingTypeCodeList = context.get("meetingTypeCodeList");
        List<Integer> methodTypeCodeList = context.get("methodTypeCodeList");

        for (String id : victimMapIds.get(victimType)) {
            for (int i = 0; i < meetingTypeCodeList.size() && i < methodTypeCodeList.size(); i++) {
                Integer meetingTypeCode = meetingTypeCodeList.get(i);
                Integer methodTypeCode = methodTypeCodeList.get(i);
                Meetings meetings = payLoadForMeetingStatusDetails(meetingTypeCode, methodTypeCode, meetingStatus);
                victimService.victimMeetingStatus(idGuidMap.get(id), convertObjectToString(meetings));
                victimMeetingDetailsMap.put(meetingTypeCode, meetings);
            }
            context.set("victimMeetingDetailsMap", victimMeetingDetailsMap);
        }

    }

    @Then("the {string} meeting details of {string} is verified in VCA")
    public void meetingStatusDetailsVerified(String meetingStatus, String victimType) {
        HttpResponseWrapper response;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Meetings> victimMeetingDetailsMap = context.get("victimMeetingDetailsMap");

        List<Integer> meetingTypeCodeList = context.get("meetingTypeCodeList");
        List<Integer> methodTypeCodeList = context.get("methodTypeCodeList");

        for (String id : victimMapIds.get(victimType)) {
            for (int i = 0; i < meetingTypeCodeList.size() && i < methodTypeCodeList.size(); i++) {
                Integer meetingTypeCode = meetingTypeCodeList.get(i);
                Integer methodTypeCode = methodTypeCodeList.get(i);
                Integer meetingAttempt = 1;
                Meetings meetings = victimMeetingDetailsMap.get(meetingTypeCode);
                response = victimService.listMeetingStatusDetails(idGuidMap.get(id), meetingTypeCode, meetingAttempt);
                VictimCaseAppAssertions.assertMeetingStatusDetails(meetingTypeCode, meetings, (Response) response);
            }

        }

    }

    @When("No response is logged when there is no response by {string} in communication attempt")
    public void meetingOfferedNoResponse(String victimType) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Meetings> victimMeetingDetailsMap = context.get("victimMeetingDetailsMap");

        List<Integer> meetingTypeCodeList = context.get("meetingTypeCodeList");
        List<Integer> methodTypeCodeList = context.get("methodTypeCodeList");

        for (String id : victimMapIds.get(victimType)) {
            for (int i = 0; i < meetingTypeCodeList.size() && i < methodTypeCodeList.size(); i++) {
                Integer meetingTypeCode = meetingTypeCodeList.get(i);
                Integer methodTypeCode = methodTypeCodeList.get(i);
                Meetings meetings = payLoadForNoResponseMeetingDetails(meetingTypeCode, methodTypeCode);
                victimService.noResponseVictimMeeting(idGuidMap.get(id), convertObjectToString(meetings));
                victimMeetingDetailsMap.put(meetingTypeCode, meetings);
            }
            context.set("victimMeetingDetailsMap", victimMeetingDetailsMap);
        }

    }

    @Then("Logged no response to meeting details by {string} is verified in VCA")
    public void noResponseDetailsVerified(String victimType) {
        HttpResponseWrapper response;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<Integer, Meetings> victimMeetingDetailsMap = context.get("victimMeetingDetailsMap");

        List<Integer> meetingTypeCodeList = context.get("meetingTypeCodeList");
        List<Integer> methodTypeCodeList = context.get("methodTypeCodeList");

        for (String id : victimMapIds.get(victimType)) {
            for (int i = 0; i < meetingTypeCodeList.size() && i < methodTypeCodeList.size(); i++) {
                Integer meetingTypeCode = meetingTypeCodeList.get(i);
                Integer methodTypeCode = methodTypeCodeList.get(i);
                Integer meetingAttempt = 1;
                Integer noResponseMethodCode = 10;
                Meetings meetings = victimMeetingDetailsMap.get(meetingTypeCode);
                response = victimService.listMeetingStatusDetails(idGuidMap.get(id), meetingTypeCode, meetingAttempt);
                VictimCaseAppAssertions.assertNoResponseMeetingDetails(meetingTypeCode, meetings, (Response) response);
            }

        }

    }


}