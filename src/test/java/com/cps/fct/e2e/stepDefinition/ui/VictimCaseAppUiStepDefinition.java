package com.cps.fct.e2e.stepDefinition.ui;

import com.cps.fct.e2e.pages.PageObjects;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.common.SecurePassCode;
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
    public void caseReferenceSearch(String victimType) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> victimMapIds = context.get("victimMapIds");
        Map<String, String> victimTypeVictimNameMap = new HashMap<>();
        context.set("victimTypeVictimNameMap", victimTypeVictimNameMap);
//        String caseUrn = "10AE6487726";
//        String victimName = "[Goodwin, Jonathon]";

        for (String id : victimMapIds.get(victimType)) {
//            String victimId = String.valueOf(victimMapIds.get(victimType));
            Map<String, List<String>> idVictimNameMap = context.get("idVictimWitnessNameMap");
            String victimName = String.valueOf(idVictimNameMap.get(String.valueOf(victimMapIds.get(victimType))));
            pages.vcaHomePage.searchVictimCase(caseUrn);
            String victimFullName = pages.vcaHomePage.getVictimFullName(caseUrn, victimName);
            victimTypeVictimNameMap.put(victimType, victimFullName);
        }
        context.set("victimTypeVictimNameMap", victimTypeVictimNameMap);
    }

    @When("the {string} is onboarded to {string} service lead")
    public void onboardVictim(String victimType, String ServiceType) {
        Map<String, String> victimTypeVictimNameMap = context.get("victimTypeVictimNameMap");
        String victimFullName = victimTypeVictimNameMap.get(victimType);
        pages.vcaHomePage.onboardVictim(victimFullName, ServiceType);
    }

    @When("select {string} for next task for the {string}")
    public void selectNextTask(String taskType, String victimType) {
        pages.vcaHomePage.selectNextTaskForVictim(taskType);
    }

    @When("verify that {string} is onboarded")
    public void verifyOnboard(String victimType) {
        Map<String, String> victimTypeVictimNameMap = context.get("victimTypeVictimNameMap");
        String victimFullName = victimTypeVictimNameMap.get(victimType);
        pages.vcaHomePage.verifyVictimOnboard(victimFullName);
    }


    @When("the {string} is assigned as VLO to {string}")
    public void assignVLO(String vloName, String victimType) {
//        String caseUrn = context.get("caseUrn");
        String caseUrn = "10AE5354926";
        Map<String, String> victimTypeVictimNameMap = context.get("victimTypeVictimNameMap");
//        String victimFullName = victimTypeVictimNameMap.get(victimType);
        String victimFullName = "KING, Craig";
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.assignVloToVictim(vloName, victimFullName);
    }

    @When("verify that Vlo {string} is assigned to {string}")
    public void verifyAssignedVlo(String vloName, String victimType){
        //        String caseUrn = context.get("caseUrn");
        String caseUrn = "10AE5354926";
        Map<String, String> victimTypeVictimNameMap = context.get("victimTypeVictimNameMap");
//        String victimFullName = victimTypeVictimNameMap.get(victimType);
        String victimFullName = "KING, Craig";
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.verifyAssignedVlo(vloName);

    }





}
