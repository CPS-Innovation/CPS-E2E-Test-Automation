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

//        String caseUrn = "05AQ7242526";
//        String victimName = "[RAU, Ressie]";

        for (String id : victimWitnessIds.get(personType)) {
            String victimWitnessId = String.valueOf(victimWitnessIds.get(personType));
            Map<String, List<String>> idvictimWitnessNameMap = context.get("idVictimWitnessNameMap");
            String victimWitnessName = String.valueOf(idvictimWitnessNameMap.get(victimWitnessId));
            pages.vcaHomePage.searchVictimCase(caseUrn);
            String victimWitnessFullName = pages.vcaHomePage.getVictimFullName(caseUrn, victimWitnessName);
            personTypePersonNameMap.put(personType, victimWitnessFullName);
        }
        context.set("personTypePersonNameMap", personTypePersonNameMap);
    }

    @When("the {string} is onboarded to {string} service lead")
    public void onboardVictim(String victimType, String ServiceType) {
        Map<String, String> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String PersonFullName = personTypePersonNameMap.get(victimType);
        pages.vcaHomePage.onboardVictim(PersonFullName, ServiceType);
    }

    @When("select {string} for next task for the {string}")
    public void selectNextTask(String taskType, String personType) {
        pages.vcaHomePage.selectNextTaskForVictim(taskType);
    }

    @When("verify that {string} is onboarded")
    public void verifyOnboard(String personType) {
        Map<String, String> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String victimFullName = personTypePersonNameMap.get(personType);
        pages.vcaHomePage.verifyVictimOnboard(victimFullName);
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
//        Map<String, String> personTypePersonNameMap = context.get("personTypePersonNameMap");
//        String victimFullName = personTypePersonNameMap.get(personType);
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.verifyAssignedVlo(vloName);
    }

    @When("the following {string} personal details are added to CMS")
    public void addVictimCMSPersonDetails(String personType, DataTable dataTable){
        String caseUrn = context.get("caseUrn");
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaVictimDetailsPage.navigateVictimDetailsPage();


    }

    @When("the following {string} personal details are added in VCA")
    public void addVictimVCAPersonDetails(String victimType, DataTable dataTable){
        String caseUrn = context.get("caseUrn");
        pages.vcaHomePage.searchVictimCase(caseUrn);


    }



}
