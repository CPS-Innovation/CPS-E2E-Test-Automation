package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.model.victimCaseApp.*;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.ddei.CommonService;
import com.cps.fct.e2e.utils.services.ddei.CaseService;
import com.cps.fct.e2e.utils.services.ddei.VictimService;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.*;
import com.cps.fct.e2e.utils.services.ddei.responseAssertions.VictimWitnessAssertions;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.picocontainer.annotations.Inject;

import java.util.*;

import static com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimWitnessPayloadBuilder.*;
import static com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimWitnessPayloadBuilder.convertObjectToString;
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
        HttpResponseWrapper response = victimService.victimWitnessList(context.get("caseId"));
        victimService.victimWitnessIds(response, context);

//        Map<String, VictimCmsDetails> victimDetailsToCMS = new HashMap<>();
//        context.set("victimDetailsToCMS", victimDetailsToCMS);
//
//        Map<String, VictimVcaDetails> victimWitnessDetailsToVCA = new HashMap<>();
//        context.set("victimWitnessDetailsToVCA", victimWitnessDetailsToVCA);
//
//        Map<String, String> idGuidMap = new HashMap<>();
//        context.set("idGuidMap", idGuidMap);
//
//        Map<String, String> categoryMap = new HashMap<>();
//        context.set("categoryMap", categoryMap);
//
//        Map<Integer, VictimContacts> victimContactDetailsMap = new HashMap<>();
//        context.set("victimContactDetailsMap", victimContactDetailsMap);
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

    @When("the {string} is onboarded as {string} service lead to VCA")
    public void victimOnboardForService(String witnessVictimType, String service) {

//        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
//        Map<String, String> idGuidMap = context.get("idGuidMap");
//
//        VictimOnboardService serviceTypeCode = VictimOnboardService.fromString(service);
//
//        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
//            String guid = witnessService.victimWitnessGuid(context.get("caseUrn"), context.get("caseId"), id, serviceTypeCode.getValue());
//            idGuidMap.put(id, guid);
//        }
//        context.set("idGuidMap", idGuidMap);
    }

    @When("the {string} is onboarded to VCA")
    public void onboardedToVCA(String witnessVictimType) throws InterruptedException {
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, String> idGuidMap = context.get("idGuidMap");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            String guid = victimService.victimWitnessGuid(context.get("caseUrn"), context.get("caseId"), id);
            idGuidMap.put(id, guid);
        }
        context.set("idGuidMap", idGuidMap);
    }

    @When("the {string} personal details are added to CMS")
    public void thePersonalDetailsAreAddedToCMS(String witnessVictimType) throws InterruptedException {
        VictimCmsDetails victimCmsDetails;
        String caseId = context.get("caseId");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VictimCmsDetails> victimWitnessDetailsToCMS = context.get("victimWitnessDetailsToCMS");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            victimCmsDetails = getVictimWitnessDetails();
            victimService.addVictimWitnessCMSPersonalDetails(victimCmsDetails, caseId, id);
            victimWitnessDetailsToCMS.put(id, victimCmsDetails);
            Thread.sleep(2000);
        }
        context.set("victimWitnessDetailsToCMS", victimWitnessDetailsToCMS);
    }

    @When("the {string} personal details are updated to CMS")
    public void thePersonalDetailsAreUpdatedToCMS(String witnessVictimType) throws InterruptedException {
        VictimCmsDetails victimCmsDetails;
        String caseId = context.get("caseId");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VictimCmsDetails> victimWitnessDetailsToCMS = context.get("victimWitnessDetailsToCMS");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            victimCmsDetails = getVictimWitnessDetails();
            victimService.updateVictimWitnessCMSPersonalDetails(victimCmsDetails, caseId, id);
            victimWitnessDetailsToCMS.put(id, victimCmsDetails);
            Thread.sleep(2000);
        }
        context.set("victimWitnessDetailsToCMS", victimWitnessDetailsToCMS);
    }

    @When("the category {string} is added to {string} in VCA")
    public void addCategoryToVictimAndWitness(String category, String witnessVictimType) throws InterruptedException {
        String categoryCode = null;
        VictimCmsDetails victimCmsDetails;
        String caseId = context.get("caseId");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VictimCmsDetails> victimWitnessDetailsToCMS = context.get("victimWitnessDetailsToCMS");
        Map<String, String> categoryMap = context.get("categoryMap");

        categoryCode = switch (category) {
            case "Victim" -> "V";
            case "PoliceOfficer" -> "P";
            case "Child" -> "C";
            case "Professional" -> "F";
            case "Expert" -> "X";
            case "Vulnerable" -> "L";
            case "Intimidated" -> "T";
            case "ServingPrisoner" -> "H";
            case "Interpreter" -> "I";
            default -> categoryCode;
        };

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            victimCmsDetails = getVictimWitnessCategory(categoryCode);
            victimService.addVictimWitnessCategoryDetails(victimCmsDetails, caseId, id);
            victimWitnessDetailsToCMS.put(id, victimCmsDetails);
            categoryMap.put(id, categoryCode);
        }
        context.set("victimWitnessDetailsToCMS", victimWitnessDetailsToCMS);
        context.set("categoryMap", categoryMap);
    }

    @When("the category {string} is update to {string} in VCA")
    public void updateCategoryToVictimAndWitness(String category, String witnessVictimType) throws InterruptedException {
        String categoryCode = null;
        VictimCmsDetails victimCmsDetails;
        String caseId = context.get("caseId");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VictimCmsDetails> victimWitnessDetailsToCMS = context.get("victimWitnessDetailsToCMS");
        Map<String, String> categoryMap = context.get("categoryMap");

        categoryCode = switch (category) {
            case "PoliceOfficer" -> "P,V";
            case "Child" -> "C,V";
            case "Professional" -> "F,V";
            case "Vulnerable" -> "L,V";
            case "Intimidated" -> "T,V,";
            case "ServingPrisoner" -> "H,V";
            default -> categoryCode;
        };

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            categoryMap.put(id, categoryCode);
            victimCmsDetails = getVictimWitnessCategory(categoryCode);
            victimService.updateVictimWitnessCategoryDetails(victimCmsDetails, caseId, id);
            victimWitnessDetailsToCMS.put(id, victimCmsDetails);
        }

        context.set("victimWitnessDetailsToCMS", victimWitnessDetailsToCMS);
        context.set("categoryMap", categoryMap);
    }

    @Then("the {string} personal details are added to VCA")
    public void personalDetailsAreAddedToVCA(String witnessVictimType) throws InterruptedException {
        VictimVcaDetails victimVcaDetails;
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VictimVcaDetails> victimWitnessDetailsToVCA = context.get("victimWitnessDetailsToVCA");
        Map<String, String> idGuidMap = context.get("idGuidMap");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            victimVcaDetails = addVcaPersonalDetails();
            victimService.addWitnessVictimDetailsToVCA(idGuidMap.get(id), convertObjectToString(victimVcaDetails));
            victimWitnessDetailsToVCA.put(idGuidMap.get(id), victimVcaDetails);
            Thread.sleep(2000);
        }
        context.set("victimWitnessDetailsToVCA", victimWitnessDetailsToVCA);
    }

    @Then("the {string} personal details are update to VCA")
    public void personalDetailsAreUpdateToVCA(String witnessVictimType) throws InterruptedException {

        VictimVcaDetails victimVcaDetails;
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VictimVcaDetails> victimWitnessDetailsToVCA = context.get("victimWitnessDetailsToVCA");
        Map<String, String> idGuidMap = context.get("idGuidMap");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            victimVcaDetails = updateVcaPersonalDetails();
            String requestPayload = convertObjectToString(victimVcaDetails);
            victimService.updateWitnessVictimDetailsToVCA(idGuidMap.get(id), requestPayload);
            victimWitnessDetailsToVCA.put(idGuidMap.get(id), victimVcaDetails);
        }
        context.set("victimWitnessDetailsToVCA", victimWitnessDetailsToVCA);
    }

    @Then("the {string} personal details are verified in CMS and VCA")
    public void personalDetailsAreVerifiedInCMSAndVCA(String witnessVictimType) throws InterruptedException {
        HttpResponseWrapper response;
//        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
//        Map<String, VictimCmsDetails> victimWitnessDetailsToCMS = context.get("victimWitnessDetailsToCMS");
//        Map<String, VictimVcaDetails> victimWitnessDetailsToVCA = context.get("victimWitnessDetailsToVCA");
//        Map<String, String> idGuidMap = context.get("idGuidMap");
//
//        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
//            //Step1: Validate CMS data -Get input details from the Post request to CMS
//            VictimCmsDetails victimCmsDetails = victimWitnessDetailsToCMS.get(id);
//            // Get output details from the Get request from CMS
//            response = victimService.listWitnessVictimDetails(context.get("caseId"));
//            VictimWitnessAssertions.assertCMSPersonalDetails(id, victimCmsDetails, response);
//            Thread.sleep(2000);
//
//            //Step2: Validate VCA data -Get input details from the Post request to VCA
//            VictimVcaDetails victimVcaDetails = victimWitnessDetailsToVCA.get(idGuidMap.get(id));
//            // Get output details from the Get request from VCA
//            response = victimService.witnessesDetailsFromVCA(idGuidMap.get(id));
//            VictimWitnessAssertions.assertVCAPersonalDetails(idGuidMap.get(id), victimVcaDetails, response);
//            Thread.sleep(2000);
//        }
    }

    @Then("the {string} and {string} category is verified in CMS")
    public void categoryVerifiedInCMS(String witnessType, String victimType) throws InterruptedException {
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VictimCmsDetails> victimWitnessDetailsToCMS = context.get("victimWitnessDetailsToCMS");

        assertCategoryDetails(witnessType, witnessVictimMapIds, victimWitnessDetailsToCMS);
        assertCategoryDetails(victimType, witnessVictimMapIds, victimWitnessDetailsToCMS);
    }

    private void assertCategoryDetails(String witnessVictimType, Map<String, List<String>> witnessVictimMapIds,
                                       Map<String, VictimCmsDetails> victimWitnessDetailsToCMS) {
//        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
//            //Step1: Validate CMS data -Get input details from the Post request to CMS
//            VictimCmsDetails victimCmsDetails = victimWitnessDetailsToCMS.get(id);
//            // Get output details from the Get request from CMS
//            HttpResponseWrapper response = victimService.listWitnessVictimDetails(context.get("caseId"));
//            VictimWitnessAssertions.assertCategoryDetails(id, victimCmsDetails, response);
//        }
    }

    @When("the {string} is added to {string} in VCA")
    public void theVictimContactIsAddedInVCA(String contactType, String witnessVictimType) {
        int contactTypeCode = 0;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<Integer, VictimContacts> victimContactDetailsMap = context.get("victimContactDetailsMap");

        contactTypeCode = switch (contactType) {
            case "Victim Liaison Officer" -> 1;
            case "Family Liaison Officer" -> 2;
            case "Independent Sexual Violence Adviser" -> 3;
            case "Independent Domestic Violence Adviser" -> 4;
            default -> contactTypeCode;
        };

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            VictimContacts victimContacts = VictimWitnessPayloadBuilder.payLoadForAddVictimContactDetails(contactTypeCode);
            victimService.addVictimContactDetailsToVCA(idGuidMap.get(id), convertObjectToString(victimContacts));
            victimContactDetailsMap.put(contactTypeCode, victimContacts);
        }
        context.set("victimContactDetailsMap", victimContactDetailsMap);
    }

    @Then("the {string} for {string} is verified in VCA")
    public void addDetailsIsVerifiedInVCA(String contactType, String witnessVictimType) {
        HttpResponseWrapper response;
        int contactTypeCode = 0;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<Integer, VictimContacts> victimContactDetailsMap = context.get("victimContactDetailsMap");

        contactTypeCode = switch (contactType) {
            case "Victim Liaison Officer" -> 1;
            case "Family Liaison Officer" -> 2;
            case "Independent Sexual Violence Adviser" -> 3;
            case "Independent Domestic Violence Adviser" -> 4;
            default -> contactTypeCode;
        };

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            VictimContacts victimContacts = victimContactDetailsMap.get(contactTypeCode);
            response = victimService.listVictimContactTypeDetails(idGuidMap.get(id));
            VictimWitnessAssertions.assertContactTypeDetails(contactTypeCode, victimContacts, (Response) response);
        }
    }

    @When("the {string} details are changed for {string} in VCA")
    public void theVictimContactIsUpdatedInVCA(String contactType, String witnessVictimType) {
        int contactTypeCode = 0;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<Integer, VictimContacts> victimContactDetailsMap = context.get("victimContactDetailsMap");

        contactTypeCode = switch (contactType) {
            case "Victim Liaison Officer" -> 1;
            case "Family Liaison Officer" -> 2;
            case "Independent Sexual Violence Adviser" -> 3;
            case "Independent Domestic Violence Adviser" -> 4;
            default -> contactTypeCode;
        };

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            VictimContacts victimContacts = VictimWitnessPayloadBuilder.payLoadForUpdateVictimContactDetails(contactTypeCode);
            victimService.updateVictimContactDetailsToVCA(idGuidMap.get(id), convertObjectToString(victimContacts));
            victimContactDetailsMap.put(contactTypeCode, victimContacts);
        }
        context.set("victimContactDetailsMap", victimContactDetailsMap);
    }

    @Then("the {string} changes for {string} are verified in VCA")
    public void updateDetailsAreVerifiedInVCA(String contactType, String witnessVictimType) {
        HttpResponseWrapper response;
        int contactTypeCode = 0;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<Integer, VictimContacts> victimContactDetailsMap = context.get("victimContactDetailsMap");

        contactTypeCode = switch (contactType) {
            case "Victim Liaison Officer" -> 1;
            case "Family Liaison Officer" -> 2;
            case "Independent Sexual Violence Adviser" -> 3;
            case "Independent Domestic Violence Adviser" -> 4;
            default -> contactTypeCode;
        };

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            VictimContacts victimContacts = victimContactDetailsMap.get(contactTypeCode);
            response = victimService.listVictimContactTypeDetails(idGuidMap.get(id));
            VictimWitnessAssertions.assertContactTypeDetails(contactTypeCode, victimContacts, (Response) response);
        }
    }


    @Then("the cms case details should be equal as in cms classic")
    public void assertCaseContactDetailsInVCA() {
        String caseId = context.get("caseId");
        String modifiedRequestJson = context.get("modifiedCM01RequestPayload");
//        System.out.println(modifiedRequestJson);
        HttpResponseWrapper response = victimService.listVictimWitnessCMSContact(caseId);

        VictimWitnessCMSContact expectedOfficerInCaseContact =
                buildExpectedOfficerInCaseContact(modifiedRequestJson);
        context.set("expectedOfficerInCaseContact", expectedOfficerInCaseContact);

        VictimWitnessCMSContact expectedDefenceFirmContact =
                buildExpectedDefenceFirmContact(modifiedRequestJson);
        context.set("expectedDefenceFirmContact", expectedDefenceFirmContact);

        VictimWitnessCMSContact expectedDefenceSolicitorContact =
                buildExpectedDefenceSolicitorContact(modifiedRequestJson);
        context.set("expectedDefenceSolicitorContact", expectedDefenceSolicitorContact);

        //OFFICER_IN_CASE
        List<Map<String, Object>> officerInCaseList = JsonPath.read(
                response.getBody(),
                "$[?(@.contactType=='OFFICER_IN_CASE')]"
        );
        assertThat(officerInCaseList)
                .as("OFFICER_IN_CASE contact should exist in CMS contacts response")
                .isNotEmpty();
        Map<String, Object> officerInCase = officerInCaseList.getFirst();

        VictimWitnessCMSContact actualOfficerInCaseContact = VictimWitnessCMSContact.builder()
                .contactType((String) officerInCase.get("contactType"))
                .name((String) officerInCase.get("name"))
                .phone((String) officerInCase.get("phone"))
                .email((String) officerInCase.get("email"))
                .build();
        assertThat(actualOfficerInCaseContact.getContactType())
                .isEqualTo(expectedOfficerInCaseContact.getContactType());
        assertThat(actualOfficerInCaseContact.getName())
                .isEqualTo(expectedOfficerInCaseContact.getName());
        assertThat(actualOfficerInCaseContact.getPhone())
                .isEqualTo(expectedOfficerInCaseContact.getPhone());
        assertThat(actualOfficerInCaseContact.getEmail())
                .isEqualTo(expectedOfficerInCaseContact.getEmail());

        //DEFENCE_FIRM
        List<Map<String, Object>> defenceFirmList = JsonPath.read(
                response.getBody(),
                "$[?(@.contactType=='DEFENCE_FIRM')]"
        );
        assertThat(defenceFirmList)
                .as("DEFENCE_FIRM contact should exist in CMS contacts response")
                .isNotEmpty();
        Map<String, Object> defenceFirm = defenceFirmList.getFirst();

        VictimWitnessCMSContact actualDefenceFirmContact = VictimWitnessCMSContact.builder()
                .contactType((String) defenceFirm.get("contactType"))
                .name((String) defenceFirm.get("name"))
                .phone((String) defenceFirm.get("phone"))
                .email((String) defenceFirm.get("email"))
                .build();

        assertThat(actualDefenceFirmContact.getContactType())
                .isEqualTo(expectedDefenceFirmContact.getContactType());
        assertThat(actualDefenceFirmContact.getName())
                .isEqualTo(expectedDefenceFirmContact.getName());
        assertThat(actualDefenceFirmContact.getPhone())
                .isEqualTo(expectedDefenceFirmContact.getPhone());
        assertThat(actualDefenceFirmContact.getEmail())
                .isEqualTo(expectedDefenceFirmContact.getEmail());

        //DEFENCE_SOLICITOR
        List<Map<String, Object>> defenceSolicitormList = JsonPath.read(
                response.getBody(),
                "$[?(@.contactType=='DEFENCE_SOLICITOR')]"
        );
        assertThat(defenceSolicitormList)
                .as("DEFENCE_SOLICITOR contact should exist in CMS contacts response")
                .isNotEmpty();
        Map<String, Object> defenceSolicitor = defenceSolicitormList.getFirst();

        VictimWitnessCMSContact actualDefenceSolicitorContact = VictimWitnessCMSContact.builder()
                .contactType((String) defenceSolicitor.get("contactType"))
                .name((String) defenceSolicitor.get("name"))
                .build();

        assertThat(actualDefenceSolicitorContact.getContactType())
                .isEqualTo(expectedDefenceSolicitorContact.getContactType());
        assertThat(actualDefenceSolicitorContact.getName())
                .isEqualTo(expectedDefenceSolicitorContact.getName());
    }

    private VictimWitnessCMSContact buildExpectedOfficerInCaseContact(String requestJson) {
        List<String> givenName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.PCDPoliceContactDetails.OfficerCompleting.Name.GivenName");

        List<String> familyName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.PCDPoliceContactDetails.OfficerCompleting.Name.FamilyName");

        List<String> phone = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.PCDPoliceContactDetails.OfficerCompleting.ContactDetails.ContactNumber[0].Number.TelNationalNumber");

        List<String> email = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.PCDPoliceContactDetails.OfficerCompleting.ContactDetails.Email");

        return VictimWitnessCMSContact.builder()
                .contactType("OFFICER_IN_CASE")
                .name(familyName.getFirst() + ", " + givenName.getFirst())
                .phone(phone.getFirst())
                .email(email.getFirst())
                .build();
    }

    private VictimWitnessCMSContact buildExpectedDefenceFirmContact(String requestJson) {
        String firmName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.Firm");

        String email = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.ContactDetails.Email");

        String phone = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.ContactDetails.ContactNumber[0].Number.TelNationalNumber");

        return VictimWitnessCMSContact.builder()
                .contactType("DEFENCE_FIRM")
                .name(firmName)
                .phone(phone)
                .email(email)
                .build();
    }

    private VictimWitnessCMSContact buildExpectedDefenceSolicitorContact(String requestJson) {
        String givenName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.Name.GivenName");

        String familyName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.Name.FamilyName");
        return VictimWitnessCMSContact.builder()
                .contactType("DEFENCE_SOLICITOR")
                .name(familyName + ", " + givenName)
                .build();
    }

    @When("the Victim liaison officer is assigned to {string} in VCA")
    public void victimLiaisonOfficerAssigned(String witnessVictimType) {

        VictimLiaisonOfficerDetails vcaVictimLiaisonOfficerDetails;
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Integer userPartyId = victimService.getUserPartyId();
        Map<String, VictimLiaisonOfficerDetails> victimWitnessVLODetails = context.get("victimWitnessVLODetails");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {

            vcaVictimLiaisonOfficerDetails = addVictimLiaisonOfficer(userPartyId);
            String requestPayload = convertObjectToString(vcaVictimLiaisonOfficerDetails);
            victimService.addVictimLiaisonOfficer(idGuidMap.get(id), requestPayload);
            victimWitnessVLODetails.put(idGuidMap.get(id), vcaVictimLiaisonOfficerDetails);
        }
        context.set("victimWitnessVLODetails", victimWitnessVLODetails);
    }

    @Then("assigned Victim liaison officer for {string} is verified")
    public void assignedVloIsVerified(String witnessVictimType) throws InterruptedException {

        HttpResponseWrapper response;
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, VictimLiaisonOfficerDetails> victimWitnessVLODetails = context.get("victimWitnessVLODetails");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            VictimLiaisonOfficerDetails victimLiaisonOfficerDetails = victimWitnessVLODetails.get(idGuidMap.get(id));

            response = victimService.victimLiaisonOfficerFromVCA(idGuidMap.get(id));
            VictimWitnessAssertions.assertVictimLiaisonOfficerDetails(idGuidMap.get(id), victimLiaisonOfficerDetails, response);
            Thread.sleep(1000);
        }
    }

    @When("the following meetings is not offered to {string} in VCA")
    public void meetingIsNotOffered(String witnessVictimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        Map<Integer, VictimMeetings> victimMeetingDetailsMap = new HashMap<>();
        context.set("victimMeetingDetailsMap", victimMeetingDetailsMap);

        List<Integer> meetingTypeCodeList = new ArrayList<>();

        for (Map<String, String> row : rows) {
            String meeting = row.get("meeting");
            int meetingTypeCode = Integer.parseInt(row.get("meetingTypeCode"));
            String reason = row.get("reason");
            meetingTypeCodeList.add(meetingTypeCode);

            for (String id : witnessVictimMapIds.get(witnessVictimType)) {
                VictimMeetings victimMeetings = VictimWitnessPayloadBuilder.payLoadForAddVictimMeetingDetails(meetingTypeCode, reason);
                victimService.addVictimMeetingDetailsToVCA(idGuidMap.get(id), convertObjectToString(victimMeetings));
                victimMeetingDetailsMap.put(meetingTypeCode, victimMeetings);
            }
            context.set("victimMeetingDetailsMap", victimMeetingDetailsMap);
        }
        context.set("meetingTypeCodeList", meetingTypeCodeList);
    }

    @Then("the offered meeting details of {string} is verified in VCA")
    public void meetingDetailsVerified(String witnessVictimType) {
        HttpResponseWrapper response;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<Integer, VictimMeetings> victimMeetingDetailsMap = context.get("victimMeetingDetailsMap");

        List<Integer> meetingTypeCodeList = context.get("meetingTypeCodeList");

        for (Integer meetingTypeCode : meetingTypeCodeList) {

            for (String id : witnessVictimMapIds.get(witnessVictimType)) {
                VictimMeetings victimMeetings = victimMeetingDetailsMap.get(meetingTypeCode);
                response = victimService.listVictimMeetingDetails(idGuidMap.get(id), meetingTypeCode);
                VictimWitnessAssertions.assertMeetingTypeDetails(meetingTypeCode, victimMeetings, (Response) response);
            }
        }
    }

    @When("the following meetings via meeting method is offered to {string} in VCA")
    public void meetingOfferedMethod(String witnessVictimType, DataTable dataTable) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<Integer, VictimMeetings> victimMeetingDetailsMap = new HashMap<>();
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

            for (String id : witnessVictimMapIds.get(witnessVictimType)) {
                VictimMeetings victimMeetings = VictimWitnessPayloadBuilder.payLoadForAddVictimMeetingMethodDetails(meetingTypeCode, methodTypeCode);
                HttpResponseWrapper response = victimService.addVictimMeetingDetailsToVCA(idGuidMap.get(id), convertObjectToString(victimMeetings));
//                String responseBody = response.getBody();
//                String meetingContextGuid = JsonPath.read(responseBody, "$.value.meetingContextGuid");
                victimMeetingDetailsMap.put(meetingTypeCode, victimMeetings);
//                meetingContextGuidList.add(meetingContextGuid);
            }

        }
        context.set("victimMeetingDetailsMap", victimMeetingDetailsMap);
        context.set("meetingTypeCodeList", meetingTypeCodeList);
        context.set("methodTypeCodeList", methodTypeCodeList);
//        context.set("meetingContextGuidList", meetingContextGuidList);

    }

    @When("offered meetings is {string} by {string} in VCA")
    public void meetingOfferedStatus(String meetingStatus, String witnessVictimType) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<Integer, VictimMeetings> victimMeetingDetailsMap = context.get("victimMeetingDetailsMap");

        List<Integer> meetingTypeCodeList = context.get("meetingTypeCodeList");
        List<Integer> methodTypeCodeList = context.get("methodTypeCodeList");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            for (int i = 0; i < meetingTypeCodeList.size() && i < methodTypeCodeList.size(); i++) {
                Integer meetingTypeCode = meetingTypeCodeList.get(i);
                Integer methodTypeCode = methodTypeCodeList.get(i);
                VictimMeetings victimMeetings = VictimWitnessPayloadBuilder.payLoadForMeetingStatusDetails(meetingTypeCode, methodTypeCode, meetingStatus);
                victimService.victimMeetingStatus(idGuidMap.get(id), convertObjectToString(victimMeetings));
                victimMeetingDetailsMap.put(meetingTypeCode, victimMeetings);
            }
            context.set("victimMeetingDetailsMap", victimMeetingDetailsMap);
        }

    }

    @Then("the {string} meeting details of {string} is verified in VCA")
    public void meetingStatusDetailsVerified(String meetingStatus,String witnessVictimType) {
        HttpResponseWrapper response;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<Integer, VictimMeetings> victimMeetingDetailsMap = context.get("victimMeetingDetailsMap");

        List<Integer> meetingTypeCodeList = context.get("meetingTypeCodeList");
        List<Integer> methodTypeCodeList = context.get("methodTypeCodeList");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            for (int i = 0; i < meetingTypeCodeList.size() && i < methodTypeCodeList.size(); i++) {
                Integer meetingTypeCode = meetingTypeCodeList.get(i);
                Integer methodTypeCode = methodTypeCodeList.get(i);
                Integer meetingAttempt = 1;
                VictimMeetings victimMeetings = victimMeetingDetailsMap.get(meetingTypeCode);
                response = victimService.listMeetingStatusDetails(idGuidMap.get(id), meetingTypeCode, meetingAttempt);
                VictimWitnessAssertions.assertMeetingStatusDetails(meetingTypeCode, victimMeetings, (Response) response);
            }

        }

    }

    @When("No response is logged when there is no response by {string} in communication attempt")
    public void meetingOfferedNoResponse(String witnessVictimType) {

        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<Integer, VictimMeetings> victimMeetingDetailsMap = context.get("victimMeetingDetailsMap");

        List<Integer> meetingTypeCodeList = context.get("meetingTypeCodeList");
        List<Integer> methodTypeCodeList = context.get("methodTypeCodeList");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            for (int i = 0; i < meetingTypeCodeList.size() && i < methodTypeCodeList.size(); i++) {
                Integer meetingTypeCode = meetingTypeCodeList.get(i);
                Integer methodTypeCode = methodTypeCodeList.get(i);
                VictimMeetings victimMeetings = VictimWitnessPayloadBuilder.payLoadForNoResponseMeetingDetails(meetingTypeCode, methodTypeCode);
                victimService.noResponseVictimMeeting(idGuidMap.get(id), convertObjectToString(victimMeetings));
                victimMeetingDetailsMap.put(meetingTypeCode, victimMeetings);
            }
            context.set("victimMeetingDetailsMap", victimMeetingDetailsMap);
        }

    }

    @Then("Logged no response to meeting details by {string} is verified in VCA")
    public void noResponseDetailsVerified(String witnessVictimType) {
        HttpResponseWrapper response;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<Integer, VictimMeetings> victimMeetingDetailsMap = context.get("victimMeetingDetailsMap");

        List<Integer> meetingTypeCodeList = context.get("meetingTypeCodeList");
        List<Integer> methodTypeCodeList = context.get("methodTypeCodeList");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            for (int i = 0; i < meetingTypeCodeList.size() && i < methodTypeCodeList.size(); i++) {
                Integer meetingTypeCode = meetingTypeCodeList.get(i);
                Integer methodTypeCode = methodTypeCodeList.get(i);
                Integer meetingAttempt = 1;
                Integer noResponseMethodCode = 10;
                VictimMeetings victimMeetings = victimMeetingDetailsMap.get(meetingTypeCode);
                response = victimService.listMeetingStatusDetails(idGuidMap.get(id), meetingTypeCode, meetingAttempt);
                VictimWitnessAssertions.assertNoResponseMeetingDetails(meetingTypeCode, victimMeetings, (Response) response);
            }

        }

    }

}