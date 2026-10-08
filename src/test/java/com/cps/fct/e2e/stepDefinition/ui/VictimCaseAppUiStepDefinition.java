package com.cps.fct.e2e.stepDefinition.ui;

import com.cps.fct.e2e.model.victimCaseApp.Meetings;
import com.cps.fct.e2e.pages.PageObjects;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.common.SecurePassCode;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.picocontainer.annotations.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VictimCaseAppUiStepDefinition {

    @Inject
    private PageObjects pages;
    @Inject
    private ScenarioContext context;

    public VictimCaseAppUiStepDefinition() {
    }

    @Given("VLO login to victim case application")
    public void newVloLogIntoVca() {

        pages.vcaLoginPage.loginIntoVca(
                pages.appsLoginPage.vcaUsername(), pages.appsLoginPage.vcaPassword()
        );

    }

    @When("{string} is searched using case reference")
    public void caseReferenceSearch(String personType) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> victimWitnessIds = context.get("victimWitnessMapIds");

        Map<String, String> personTypePersonNameMap = new HashMap<>();
        context.set("personTypePersonNameMap", personTypePersonNameMap);

        Map<String, String> personNameSurnameMap = new HashMap<>();
        context.set("personNameSurnameMap", personNameSurnameMap);

        Map<String, String> personNameFirstnameMap = new HashMap<>();
        context.set("personNameFirstnameMap", personNameFirstnameMap);

//        String caseUrn = "05AQ7242526";
//        String victimName = "[RAU, Ressie]";

        for (String id : victimWitnessIds.get(personType)) {
            String victimWitnessId = String.valueOf(victimWitnessIds.get(personType));
            Map<String, List<String>> idvictimWitnessNameMap = context.get("idVictimWitnessNameMap");
            String victimWitnessName = String.valueOf(idvictimWitnessNameMap.get(victimWitnessId));
            pages.vcaHomePage.searchVictimCase(caseUrn);
            String personFullName = pages.vcaHomePage.getVictimFullName(caseUrn, victimWitnessName);
            String personSurname = pages.vcaHomePage.getVictimSurname(personFullName);
            String personFirstname = pages.vcaHomePage.getVictimFirstname(personFullName);
            personTypePersonNameMap.put(personType, personFullName);
            personNameSurnameMap.put(personFullName, personSurname);
            personNameFirstnameMap.put(personFullName, personFirstname);
        }
        context.set("personTypePersonNameMap", personTypePersonNameMap);
        context.set("personNameSurnameMap", personNameSurnameMap);
        context.set("personNameFirstnameMap", personNameFirstnameMap);
    }

    @When("the {string} is onboarded to {string} service lead")
    public void onboardVictim(String personType, String ServiceType) {
        Map<String, List<String>> victimWitnessIds = context.get("victimWitnessMapIds");
        Map<String, String> personTypePersonNameMap = context.get("personTypePersonNameMap");
        Map<String, String> personNameSurnameMap = context.get ("personNameSurnameMap");
        Map<String, String> personNameFirstnameMap = context.get ("personNameFirstnameMap");
        String personFullName = personTypePersonNameMap.get(personType);
        String surName = personNameSurnameMap.get(personFullName);
        String firstName = personNameFirstnameMap.get(personFullName);
        System.out.println(personFullName);
        System.out.println(surName);
        System.out.println(firstName);


//        for (String id : victimWitnessIds.get(personType)) {
////            pages.vcaHomePage.onboardVictim(personFullName, ServiceType);
//            pages.vcaHomePage.onboardVictim(personFullName, ServiceType);
//        }

    }

    @When("select {string} for next task for the {string}")
    public void selectNextTask(String taskType, String personType) {
        Map<String, List<String>> victimWitnessIds = context.get("victimWitnessMapIds");
        for (String id : victimWitnessIds.get(personType)) {
            pages.vcaHomePage.selectNextTaskForVictim(taskType);
        }
    }

    @When("verify that {string} is onboarded")
    public void verifyOnboard(String personType) {
        Map<String, List<String>> victimWitnessIds = context.get("victimWitnessMapIds");
        Map<String, String> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String victimFullName = personTypePersonNameMap.get(personType);
        for (String id : victimWitnessIds.get(personType)) {
            pages.vcaHomePage.verifyVictimOnboard(victimFullName);
        }
    }


    @When("the {string} is assigned as VLO to {string}")
    public void assignVLO(String vloName, String personType) {
        String caseUrn = context.get("caseUrn");
//        String caseUrn = "05AQ7242526";
        Map<String, String> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = personTypePersonNameMap.get(personType);
//        String victimFullName = "KING, Craig";
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.assignVloToVictim(vloName, personFullName);
    }

    @When("verify that Vlo {string} is assigned to {string}")
    public void verifyAssignedVlo(String vloName, String personType){
                String caseUrn = context.get("caseUrn");
//        String caseUrn = "05AQ7242526";
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.verifyAssignedVlo(vloName);
    }

    @When("the following {string} personal details are added to CMS")
    public void addVictimCMSPersonDetails(String personType, DataTable dataTable){
        String caseUrn = context.get("caseUrn");

        Map<String, String> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String victimWitnessFullName = personTypePersonNameMap.get(personType);

        pages.vcaHomePage.searchVictimCase(caseUrn);
//        pages.vcaHomePage.getVictimWitnessFirstLastName(victimWitnessFullName);
        pages.vcaVictimDetailsPage.navigateVictimDetailsPage(caseUrn, victimWitnessFullName);
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            switch(row.get("cmsField")){
                case "Date of birth":
                    pages.vcaVictimDetailsPage.addDateOfBirth(row.get("justification"));
                    break;
                case "Address":
                    pages.vcaVictimDetailsPage.addAddress(row.get("justification"));
                    break;
                case "Telephone number":
                    pages.vcaVictimDetailsPage.addTelephone(row.get("justification"));
                    break;
                case "Email address":
                    pages.vcaVictimDetailsPage.addEmail(victimWitnessFullName, row.get("justification"));
                    break;
                default:
                    System.out.println("Specified cms field does not exist");
            }
        }
    }

    @When("the following {string} personal details are added in VCA")
    public void addVictimVCAPersonDetails(String personType, DataTable dataTable){
        String caseUrn = context.get("caseUrn");
        Map<String, String> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String victimWitnessFullName = personTypePersonNameMap.get(personType);
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            switch(row.get("vcaField")){
                case "Preferred name":
                    pages.vcaVictimDetailsPage.addPreferredName(victimWitnessFullName);
                    break;
                case "Preferred method of contact for CPS":
                    pages.vcaVictimDetailsPage.addPreferredCPSMoc();
                    break;
//                case "Preferred contact times":
//                    pages.vcaVictimDetailsPage.addPreferredContact();
//                    break;
//                case "Victim representative details":
//                    pages.vcaVictimDetailsPage.addVictimRepresentative();
//                    break;
//                case "Power of attorney details":
//                    pages.vcaVictimDetailsPage.addPowerOfAttorney();
//                    break;
                default:
                    System.out.println("Specified vca field does not exist");
            }
        }


    }



}
