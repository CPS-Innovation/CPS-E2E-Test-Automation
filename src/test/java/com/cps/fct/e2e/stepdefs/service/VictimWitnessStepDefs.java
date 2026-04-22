package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.model.VictimWitnessCMSContact;
import com.cps.fct.e2e.model.VictimWitnessDetails;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.ddei.CaseService;
import com.cps.fct.e2e.utils.services.ddei.WitnessService;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VcaPersonalDetails;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimContactDetails;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimWitnessPayloadBuilder;
import com.cps.fct.e2e.utils.services.ddei.responseAssertions.VictimWitnessAssertions;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.picocontainer.annotations.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.JsonUtils.*;
import static com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimWitnessPayloadBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;


public class VictimWitnessStepDefs {
    @Inject
    private CaseService caseService;

    @Inject
    private WitnessService witnessService;

    @Inject
    private ScenarioContext context;

    public VictimWitnessStepDefs() {
    }

    @Then("Case details are correct")
    public void caseDetailsAreCorrect() {
        caseService.assertCaseDetails(context.get("caseDetails"));
    }

    @And("witness details are available")
    public void witnessDetailsAreAvailable() {
        HttpResponseWrapper response = witnessService.listWitnessVictimDetails(context.get("caseId"));
        witnessService.persistVictimWitnessDetails(response, context);
    }

    @Given("witness and victim details are available")
    public void witnessAndVictimDetailsAreAvailable() {
        HttpResponseWrapper response = witnessService.listWitnessVictimDetails(context.get("caseId"));
        witnessService.persistVictimWitnessDetails(response, context);

        Map<String, VictimWitnessDetails> victimWitnessDetailsToCMS = new HashMap<>();
        context.set("victimWitnessDetailsToCMS",victimWitnessDetailsToCMS);

        Map<String, VcaPersonalDetails> victimWitnessDetailsToVCA = new HashMap<>();
        context.set("victimWitnessDetailsToVCA",victimWitnessDetailsToVCA);

        Map<String, String> idGuidMap = new HashMap<>();
        context.set("idGuidMap",idGuidMap);

        Map<String, String> categoryMap = new HashMap<>();
        context.set("categoryMap",categoryMap);

        Map<Integer, VictimContactDetails> victimContactDetailsMap = new HashMap<>();
        context.set("victimContactDetailsMap",victimContactDetailsMap);

        Map<String, Integer> victimContactTypeMap = new HashMap<>();
        context.set("victimContactTypeMap",victimContactTypeMap);

        Map<String, VictimWitnessCMSContact> victimWitnessCMSContactMap = new HashMap<>();
        context.set("victimWitnessCMSContactMap",victimWitnessCMSContactMap);
    }

    @When("the {string} is onboarded to VCA")
    public void onboardedToVCA(String witnessVictimType) throws InterruptedException {
        VcaPersonalDetails vcaPersonalDetails;
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, String> idGuidMap = context.get("idGuidMap");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            //Onboard process - creates GUID,with service as 1-Universal, Onboard as 'false'
            String guid =  witnessService.victimWitnessGuid(context.get("caseUrn"), context.get("caseId"), id);
            idGuidMap.put(id,guid);
        }
        context.set("idGuidMap",idGuidMap);
    }

    @When("the {string} personal details are added to CMS")
    public void thePersonalDetailsAreAddedToCMS(String witnessVictimType) throws InterruptedException {
        VictimWitnessDetails victimWitnessDetails;
        String caseId = context.get("caseId");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VictimWitnessDetails> victimWitnessDetailsToCMS = context.get("victimWitnessDetailsToCMS");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            victimWitnessDetails = getVictimWitnessDetails();
            witnessService.addVictimWitnessCMSPersonalDetails(victimWitnessDetails, caseId, id);
            victimWitnessDetailsToCMS.put(id, victimWitnessDetails);
            Thread.sleep(2000);
        }
        context.set("victimWitnessDetailsToCMS",victimWitnessDetailsToCMS);
    }

    @When("the {string} personal details are updated to CMS")
    public void thePersonalDetailsAreUpdatedToCMS(String witnessVictimType) throws InterruptedException {
        VictimWitnessDetails victimWitnessDetails;
        String caseId = context.get("caseId");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VictimWitnessDetails> victimWitnessDetailsToCMS = context.get("victimWitnessDetailsToCMS");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            victimWitnessDetails = getVictimWitnessDetails();
            witnessService.updateVictimWitnessCMSPersonalDetails(victimWitnessDetails, caseId, id);
            victimWitnessDetailsToCMS.put(id, victimWitnessDetails);
            Thread.sleep(2000);
        }
        context.set("victimWitnessDetailsToCMS",victimWitnessDetailsToCMS);
    }

    @When("the category {string} is added to {string} in VCA")
    public void addCategoryToVictimAndWitness(String category, String witnessVictimType) throws InterruptedException {
        String categoryCode = null;
        VictimWitnessDetails victimWitnessDetails;
        String caseId = context.get("caseId");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VictimWitnessDetails> victimWitnessDetailsToCMS = context.get("victimWitnessDetailsToCMS");
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
            victimWitnessDetails = getVictimWitnessCategory(categoryCode);
            witnessService.addVictimWitnessCategoryDetails(victimWitnessDetails, caseId, id);
            victimWitnessDetailsToCMS.put(id, victimWitnessDetails);
            categoryMap.put(id, categoryCode);
        }
        context.set("victimWitnessDetailsToCMS",victimWitnessDetailsToCMS);
        context.set("categoryMap", categoryMap);
    }

    @When("the category {string} is update to {string} in VCA")
    public void updateCategoryToVictimAndWitness(String category, String witnessVictimType) throws InterruptedException {
        String categoryCode = null;
        VictimWitnessDetails victimWitnessDetails;
        String caseId = context.get("caseId");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VictimWitnessDetails> victimWitnessDetailsToCMS = context.get("victimWitnessDetailsToCMS");
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
            victimWitnessDetails = getVictimWitnessCategory(categoryCode);
            witnessService.updateVictimWitnessCategoryDetails(victimWitnessDetails, caseId, id);
            victimWitnessDetailsToCMS.put(id, victimWitnessDetails);
        }

        context.set("victimWitnessDetailsToCMS",victimWitnessDetailsToCMS);
        context.set("categoryMap", categoryMap);
    }

    @Then("the {string} personal details are added to VCA")
    public void personalDetailsAreAddedToVCA(String witnessVictimType) throws InterruptedException {
        VcaPersonalDetails vcaPersonalDetails;
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VcaPersonalDetails> victimWitnessDetailsToVCA = context.get("victimWitnessDetailsToVCA");
        Map<String, String> idGuidMap = context.get("idGuidMap");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            vcaPersonalDetails = addVcaPersonalDetails();
            witnessService.addWitnessVictimDetailsToVCA(idGuidMap.get(id), convertObjectToString(vcaPersonalDetails));
            victimWitnessDetailsToVCA.put(idGuidMap.get(id), vcaPersonalDetails);
            Thread.sleep(2000);
        }
        context.set("victimWitnessDetailsToVCA",victimWitnessDetailsToVCA);
    }

    @Then("the {string} personal details are update to VCA")
    public void personalDetailsAreUpdateToVCA(String witnessVictimType) throws InterruptedException {
        VcaPersonalDetails vcaPersonalDetails;
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VcaPersonalDetails> victimWitnessDetailsToVCA = context.get("victimWitnessDetailsToVCA");
        Map<String, String> idGuidMap = context.get("idGuidMap");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            vcaPersonalDetails = updateVcaPersonalDetails();
            String requestPayload = convertObjectToString(vcaPersonalDetails);
            witnessService.updateWitnessVictimDetailsToVCA(idGuidMap.get(id), requestPayload);
            victimWitnessDetailsToVCA.put(idGuidMap.get(id), vcaPersonalDetails);
        }
        context.set("victimWitnessDetailsToVCA",victimWitnessDetailsToVCA);
    }

    @Then("the {string} personal details are verified in CMS and VCA")
    public void personalDetailsAreVerifiedInCMSAndVCA(String witnessVictimType) throws InterruptedException {
        HttpResponseWrapper response;
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VictimWitnessDetails> victimWitnessDetailsToCMS = context.get("victimWitnessDetailsToCMS");
        Map<String, VcaPersonalDetails> victimWitnessDetailsToVCA = context.get("victimWitnessDetailsToVCA");
        Map<String, String> idGuidMap =  context.get("idGuidMap");

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            //Step1: Validate CMS data -Get input details from the Post request to CMS
            VictimWitnessDetails victimWitnessDetails = victimWitnessDetailsToCMS.get(id);
            // Get output details from the Get request from CMS
            response = witnessService.listWitnessVictimDetails(context.get("caseId"));
            VictimWitnessAssertions.assertCMSPersonalDetails(id, victimWitnessDetails, response);
            Thread.sleep(2000);

            //Step2: Validate VCA data -Get input details from the Post request to VCA
            VcaPersonalDetails vcaPersonalDetails = victimWitnessDetailsToVCA.get(idGuidMap.get(id));
            // Get output details from the Get request from VCA
            response = witnessService.witnessesDetailsFromVCA(idGuidMap.get(id));
            VictimWitnessAssertions.assertVCAPersonalDetails(idGuidMap.get(id), vcaPersonalDetails, response);
            Thread.sleep(2000);
        }
    }

    @Then("the {string} and {string} category is verified in CMS")
    public void categoryVerifiedInCMS(String witnessType, String victimType) throws InterruptedException {
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VictimWitnessDetails> victimWitnessDetailsToCMS = context.get("victimWitnessDetailsToCMS");

        assertCategoryDetails(witnessType, witnessVictimMapIds, victimWitnessDetailsToCMS);
        assertCategoryDetails(victimType, witnessVictimMapIds, victimWitnessDetailsToCMS);
    }

    private void assertCategoryDetails(String witnessVictimType,  Map<String, List<String>> witnessVictimMapIds,
                                       Map<String, VictimWitnessDetails> victimWitnessDetailsToCMS){
        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            //Step1: Validate CMS data -Get input details from the Post request to CMS
            VictimWitnessDetails victimWitnessDetails = victimWitnessDetailsToCMS.get(id);
            // Get output details from the Get request from CMS
            HttpResponseWrapper response = witnessService.listWitnessVictimDetails(context.get("caseId"));
            VictimWitnessAssertions.assertCategoryDetails(id, victimWitnessDetails, response);
        }
    }

    @When("the {string} is added to {string} in VCA")
    public void theVictimContactIsAddedInVCA(String contactType, String witnessVictimType) {
        int contactTypeCode = 0;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<Integer, VictimContactDetails> victimContactDetailsMap = context.get("victimContactDetailsMap");

        contactTypeCode = switch (contactType) {
            case "Victim Liaison Officer" -> 1;
            case "Family Liaison Officer" -> 2;
            case "Independent Sexual Violence Adviser" -> 3;
            case "Independent Domestic Violence Adviser" -> 4;
            default -> contactTypeCode;
        };

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            VictimContactDetails victimContactDetails = VictimWitnessPayloadBuilder.payLoadForAddVictimContactDetails(contactTypeCode);
            witnessService.addVictimContactDetailsToVCA(idGuidMap.get(id), convertObjectToString(victimContactDetails));
            victimContactDetailsMap.put(contactTypeCode, victimContactDetails);
        }
        context.set("victimContactDetailsMap",victimContactDetailsMap);
    }

    @Then("the {string} for {string} is verified in VCA")
    public void addDetailsIsVerifiedInVCA(String contactType , String witnessVictimType) {
        int contactTypeCode = 0;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<Integer, VictimContactDetails> victimContactDetailsMap = context.get("victimContactDetailsMap");

        contactTypeCode = switch (contactType) {
            case "Victim Liaison Officer" -> 1;
            case "Family Liaison Officer" -> 2;
            case "Independent Sexual Violence Adviser" -> 3;
            case "Independent Domestic Violence Adviser" -> 4;
            default -> contactTypeCode;
        };

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            VictimContactDetails victimContactDetails = victimContactDetailsMap.get(contactTypeCode);
            Response response = witnessService.listVictimContactTypeDetails(idGuidMap.get(id));
            VictimWitnessAssertions.assertContactTypeDetails(contactTypeCode, victimContactDetails, response);
        }
    }

    @When("the {string} details are changed for {string} in VCA")
    public void theVictimContactIsUpdatedInVCA(String contactType, String witnessVictimType) {
        int contactTypeCode = 0;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<Integer, VictimContactDetails> victimContactDetailsMap = context.get("victimContactDetailsMap");

        contactTypeCode = switch (contactType) {
            case "Victim Liaison Officer" -> 1;
            case "Family Liaison Officer" -> 2;
            case "Independent Sexual Violence Adviser" -> 3;
            case "Independent Domestic Violence Adviser" -> 4;
            default -> contactTypeCode;
        };

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            VictimContactDetails victimContactDetails = VictimWitnessPayloadBuilder.payLoadForUpdateVictimContactDetails(contactTypeCode);
            witnessService.updateVictimContactDetailsToVCA(idGuidMap.get(id), convertObjectToString(victimContactDetails));
            victimContactDetailsMap.put(contactTypeCode, victimContactDetails);
        }
        context.set("victimContactDetailsMap",victimContactDetailsMap);
    }

    @Then("the {string} changes for {string} are verified in VCA")
    public void updateDetailsAreVerifiedInVCA(String contactType , String witnessVictimType) {
        int contactTypeCode = 0;
        Map<String, String> idGuidMap = context.get("idGuidMap");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<Integer, VictimContactDetails> victimContactDetailsMap = context.get("victimContactDetailsMap");

        contactTypeCode = switch (contactType) {
            case "Victim Liaison Officer" -> 1;
            case "Family Liaison Officer" -> 2;
            case "Independent Sexual Violence Adviser" -> 3;
            case "Independent Domestic Violence Adviser" -> 4;
            default -> contactTypeCode;
        };

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            VictimContactDetails victimContactDetails = victimContactDetailsMap.get(contactTypeCode);
            Response response = witnessService.listVictimContactTypeDetails(idGuidMap.get(id));
            VictimWitnessAssertions.assertContactTypeDetails(contactTypeCode, victimContactDetails, response);
        }
    }


    @Then("the cms case details should be equal as in cms classic")
    public void assertCaseContactDetailsInVCA() {
        String caseId = context.get("caseId");
        String modifiedRequestJson = context.get("modifiedRequestPayload");
        HttpResponseWrapper response = witnessService.listVictimWitnessCMSContact(caseId);

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
                "$.PreChargeDecisionRequest.CaseContacts[?(@.Officer.PoliceOfficerRank == 'PoliceUnit')].Name.GivenName[0].Value");

        List<String> familyName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.CaseContacts[?(@.Officer.PoliceOfficerRank == 'PoliceUnit')].Name.FamilyName.Value");

        List<String> phone = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.CaseContacts[?(@.Officer.PoliceOfficerRank == 'PoliceUnit')].ContactDetails.ContactNumber[0].Number.TelNationalNumber");

        List<String> email = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.CaseContacts[?(@.Officer.PoliceOfficerRank == 'PoliceUnit')].ContactDetails.Email");

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
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.Name.GivenName[0].Value");

        String familyName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.Name.FamilyName.Value");
        return VictimWitnessCMSContact.builder()
                .contactType("DEFENCE_SOLICITOR")
                .name(familyName + ", " + givenName)
                .build();
    }
}