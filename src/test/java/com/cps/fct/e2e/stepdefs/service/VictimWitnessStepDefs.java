package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.enums.PreferredMethodOfContact;
import com.cps.fct.e2e.model.VictimWitnessDetails;
import com.cps.fct.e2e.model.Witness;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.ddei.CaseService;
import com.cps.fct.e2e.utils.services.ddei.WitnessService;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VcaPersonalDetails;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VcaPersonalDetailsMapWrapper;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimWitnessDetailsMapWrapper;
import com.cps.fct.e2e.utils.services.ddei.responseAssertions.VictimWitnessAssertions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.picocontainer.annotations.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @When("the {string} personal details are entered")
    public void thePersonalDetailsAreEntered(String witnessVictimType) {
        String caseId = context.get("caseId");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");
        for (String id : witnessVictimMapIds.get(witnessVictimType)) {
            witnessService.addVictimWitnessCMSPersonalDetails(getVictimWitnessDetails(), caseId, id);
        }
    }

    @And("witness and victim are added to VCA")
    public void witnessAndVictimDetailsAreAddedToVCA() {
        String caseUrn = context.get("caseUrn");
        String caseId = context.get("caseId");
        Map<String, List<String>> witnessVictimMapIds = context.get("witnessVictimMapIds");

        Map<String, List<String>> witnessVictimMapGuids = new HashMap<>();
        witnessVictimMapGuids.put("witnessGuid", mapIdsToGuids(witnessVictimMapIds.get("witnessId"), caseUrn, caseId));
        witnessVictimMapGuids.put("witnessChildGuid", mapIdsToGuids(witnessVictimMapIds.get("witnessChildId"), caseUrn, caseId));
        witnessVictimMapGuids.put("witnessExpertGuid", mapIdsToGuids(witnessVictimMapIds.get("witnessExpertId"), caseUrn, caseId));
        witnessVictimMapGuids.put("witnessPrisonerGuid", mapIdsToGuids(witnessVictimMapIds.get("witnessPrisonerId"), caseUrn, caseId));
        witnessVictimMapGuids.put("witnessInterpreterGuid", mapIdsToGuids(witnessVictimMapIds.get("witnessInterpreterId"), caseUrn, caseId));
        witnessVictimMapGuids.put("witnessVulnerableGuid", mapIdsToGuids(witnessVictimMapIds.get("witnessVulnerableId"), caseUrn, caseId));
        witnessVictimMapGuids.put("witnessPoliceGuid", mapIdsToGuids(witnessVictimMapIds.get("witnessPoliceId"), caseUrn, caseId));
        witnessVictimMapGuids.put("witnessProfessionalGuid", mapIdsToGuids(witnessVictimMapIds.get("witnessProfessionalId"), caseUrn, caseId));
        witnessVictimMapGuids.put("witnessIntimidatedGuid", mapIdsToGuids(witnessVictimMapIds.get("witnessIntimidatedId"), caseUrn, caseId));
        witnessVictimMapGuids.put("victimGuid", mapIdsToGuids(witnessVictimMapIds.get("victimId"), caseUrn, caseId));
        witnessVictimMapGuids.put("victimChildGuid", mapIdsToGuids(witnessVictimMapIds.get("victimChildId"), caseUrn, caseId));
        witnessVictimMapGuids.put("victimExpertGuid", mapIdsToGuids(witnessVictimMapIds.get("victimExpertId"), caseUrn, caseId));
        witnessVictimMapGuids.put("victimPrisonerGuid", mapIdsToGuids(witnessVictimMapIds.get("victimPrisonerId"), caseUrn, caseId));
        witnessVictimMapGuids.put("victimInterpreterGuid", mapIdsToGuids(witnessVictimMapIds.get("victimInterpreterId"), caseUrn, caseId));
        witnessVictimMapGuids.put("victimVulnerableGuid", mapIdsToGuids(witnessVictimMapIds.get("victimVulnerableId"), caseUrn, caseId));
        witnessVictimMapGuids.put("victimPoliceGuid", mapIdsToGuids(witnessVictimMapIds.get("victimPoliceId"), caseUrn, caseId));
        witnessVictimMapGuids.put("victimProfessionalGuid", mapIdsToGuids(witnessVictimMapIds.get("victimProfessionalId"), caseUrn, caseId));
        witnessVictimMapGuids.put("victimIntimidatedGuid", mapIdsToGuids(witnessVictimMapIds.get("victimIntimidatedId"), caseUrn, caseId));
        context.set("witnessVictimMapGuids", witnessVictimMapGuids);
    }

    @Then("the witness personal details are sent to VCA")
    public void theWitnessAndVictimPersonalDetailsAreSentToCMS() {

        Witness witnessVictimGuids = context.getCastClazz("witnessVictimGuids", Witness.class);
        Map<String, VcaPersonalDetails> victimWitnessDataMap = witnessVictimGuids.witnessId().stream()
                .collect(Collectors.toMap(
                        witnessGuid -> witnessGuid,
                        witnessGuid -> {
                            VcaPersonalDetails details = getPersonDetailsVCAPayload(PreferredMethodOfContact.EMAIL);
                            String requestBody = payLoadForAddWitnessDetailToVca(details);
                            witnessService.UpdateWitnessVictimDetailsToVCA(witnessGuid, requestBody);
                            return details;
                        }
                ));
        context.set("vcaPersonaDetailsWrapper", new VcaPersonalDetailsMapWrapper(victimWitnessDataMap));
    }

    @Then("Witness personal details are updated correctly to CMS")
    public void witnessPersonalDetailsAreUpdatedCorrectly() {
        VictimWitnessDetailsMapWrapper wrapper = context.getCastClazz("victimWitnessDetailsWrapper",
                VictimWitnessDetailsMapWrapper.class);
        Map<String, VictimWitnessDetails> victimWitnessMap = wrapper.getMap();
        witnessService.listWitnessVictimDetails(context.get("caseId"));
        VictimWitnessAssertions.assertVictimWitnessPersonalDetails(victimWitnessMap);
    }

    @And("Witness personal details are updated correctly to VCA")
    public void witnessPersonalDetailsAreUpdatedCorrectlyToVCA() {

        Witness witnessVictimIds = context.getCastClazz("witnessVictimIds", Witness.class);
        VcaPersonalDetailsMapWrapper wrapper = context.getCastClazz("vcaPersonaDetailsWrapper", VcaPersonalDetailsMapWrapper.class);
        Map<String, VcaPersonalDetails> vcaPersonalDetailsMap = wrapper.getMap();

        String caseUrn = context.get("caseUrn");
        String caseId = context.get("caseId");

        witnessVictimIds.witnessId().forEach(witnessId -> {
            witnessService.witnessesDetailsFromCMS(caseUrn, caseId, witnessId);
            VictimWitnessAssertions.assertWitnessPersonalDetailsFromCMS(vcaPersonalDetailsMap);
        });
    }

    // Helper method
    private List<String> mapIdsToGuids(List<String> ids, String caseUrn, String caseId) {
        if (ids.isEmpty()) {
            return null;
        } else {
            return ids.stream()
                    .map(id -> witnessService.victimWitnessGuid(caseUrn, caseId, id))
                    .toList();
        }
    }
}