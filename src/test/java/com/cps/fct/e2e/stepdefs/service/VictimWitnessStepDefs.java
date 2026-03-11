package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.model.VictimWitnessDetails;
import com.cps.fct.e2e.model.Witness;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.ddei.CaseService;
import com.cps.fct.e2e.utils.services.ddei.WitnessService;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VcaPersonalDetails;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VcaPersonalDetailsMapWrapper;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimWitnessPayloadBuilder;
import com.cps.fct.e2e.utils.services.ddei.responseAssertions.VictimWitnessAssertions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.picocontainer.annotations.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimWitnessPayloadBuilder.*;

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
    }

    @When("the {string} personal details are added to CMS")
    public void thePersonalDetailsAreAddedToCMS(String witnessVictimType) throws InterruptedException {
        VictimWitnessDetails victimWitnessDetails;
        String caseId = context.get("caseId");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VictimWitnessDetails> victimWitnessDetailsToCMS = new HashMap<>();
        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            victimWitnessDetails = getVictimWitnessDetails();
            witnessService.addVictimWitnessCMSPersonalDetails(victimWitnessDetails, caseId, id);
            System.out.println(id);
            victimWitnessDetailsToCMS.put(id, victimWitnessDetails);
            context.set("victimWitnessDetailsToCMS",victimWitnessDetailsToCMS);
            Thread.sleep(2000);
        }
    }

    @Then("the {string} is onboarded and personal details are added to VCA")
    public void thePersonalDetailsAreAddedToVCA(String witnessVictimType) throws InterruptedException {
        VcaPersonalDetails vcaPersonalDetails;
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        Map<String, VcaPersonalDetails> victimWitnessDetailsToVCA = new HashMap<>();
        Map<String, String> idGuidMap = new HashMap<>();

        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            //Onboard process - creates GUID
            String guid =  witnessService.victimWitnessGuid(context.get("caseUrn"), context.get("caseId"), id);
            vcaPersonalDetails = VictimWitnessPayloadBuilder.getVcaPersonalDetails();
            String requestPayload = VictimWitnessPayloadBuilder.convertObjectToString(vcaPersonalDetails);
            witnessService.UpdateWitnessVictimDetailsToVCA(guid, requestPayload);
            victimWitnessDetailsToVCA.put(guid, vcaPersonalDetails);
            idGuidMap.put(id,guid);
            System.out.println(idGuidMap);
            context.set("victimWitnessDetailsToVCA",victimWitnessDetailsToVCA);
            context.set("idGuidMap",idGuidMap);
            Thread.sleep(1000);
        }
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

            //Step2: Validate VCA data -Get input details from the Post request to VCA
            VcaPersonalDetails vcaPersonalDetails = victimWitnessDetailsToVCA.get(idGuidMap.get(id));
            // Get output details from the Get request from VCA
            response = witnessService.witnessesDetailsFromVCA(context.get("caseUrn"), context.get("caseId"), id);
            VictimWitnessAssertions.assertVCAPersonalDetails(idGuidMap.get(id), vcaPersonalDetails, response);
            Thread.sleep(1000);
        }
    }
}