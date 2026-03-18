package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.enums.PreferredMethodOfContact;
import com.cps.fct.e2e.model.VictimWitnessDetails;
import com.cps.fct.e2e.model.WitnessVictim;
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

    @When("the witness's personal details are entered")
    public void theWitnessSPersonalDetailsAreEntered() {

        WitnessVictim witnessVictimIds = context.getCastClazz("witnessVictimIds", WitnessVictim.class);
        String caseId = context.get("caseId");

        //NeedToLook - Murali
        Map<String, VictimWitnessDetails> victimWitnessDataMap = witnessVictimIds.witnessId().stream()
                .collect(Collectors.toMap(
                        id -> id,
                        id -> {
                            VictimWitnessDetails details = getVictimWitnessDetails();
                            witnessService.addVictimWitnessCMSPersonalDetails(details, caseId, id);
                            return details;
                        }
                ));
        context.set("victimWitnessDetailsWrapper", new VictimWitnessDetailsMapWrapper(victimWitnessDataMap));

    }

    @And("witness and victim are added to VCA")
    public void witnessAndVictimDetailsAreAddedToVCA() {
        WitnessVictim witnessVictimIds = context.getCastClazz("witnessVictimIds", WitnessVictim.class);
        String caseUrn = context.get("caseUrn");
        String caseId = context.get("caseId");

        List<String> persistedWitnessId = mapIdsToGuids(witnessVictimIds.witnessId(), caseUrn, caseId );
        List<String> persistedWitnessChildId = mapIdsToGuids(witnessVictimIds.witnessChildId(), caseUrn, caseId);
        List<String> persistedWitnessExpertId = mapIdsToGuids(witnessVictimIds.witnessExpertId(), caseUrn, caseId);
        List<String> persistedWitnessPrisonerId = mapIdsToGuids(witnessVictimIds.witnessPrisonerId(), caseUrn, caseId);
        List<String> persistedWitnessInterpreterId = mapIdsToGuids(witnessVictimIds.witnessInterpreterId(), caseUrn, caseId);
        List<String> persistedWitnessVulnerableId = mapIdsToGuids(witnessVictimIds.witnessVulnerableId(), caseUrn, caseId);
        List<String> persistedWitnessPoliceId = mapIdsToGuids(witnessVictimIds.witnessPoliceId(), caseUrn, caseId);
        List<String> persistedWitnessProfessionalId = mapIdsToGuids(witnessVictimIds.witnessProfessionalId(), caseUrn, caseId);
        List<String> persistedWitnessIntimidatedId = mapIdsToGuids(witnessVictimIds.witnessIntimidatedId(), caseUrn, caseId);
        List<String> persistedVictimId = mapIdsToGuids(witnessVictimIds.victimId(), caseUrn, caseId);
        List<String> persistedVictimChildId = mapIdsToGuids(witnessVictimIds.victimChildId(), caseUrn, caseId);
        List<String> persistedVictimExpertId = mapIdsToGuids(witnessVictimIds.victimExpertId(), caseUrn, caseId);
        List<String> persistedVictimPrisonerId = mapIdsToGuids(witnessVictimIds.victimPrisonerId(), caseUrn, caseId);
        List<String> persistedVictimInterpreterId = mapIdsToGuids(witnessVictimIds.victimInterpreterId(), caseUrn, caseId);
        List<String> persistedVictimVulnerableId = mapIdsToGuids(witnessVictimIds.victimVulnerableId(), caseUrn, caseId);
        List<String> persistedVictimPoliceId = mapIdsToGuids(witnessVictimIds.victimPoliceId(), caseUrn, caseId);
        List<String> persistedVictimProfessionalId = mapIdsToGuids(witnessVictimIds.victimProfessionalId(), caseUrn, caseId);
        List<String> persistedVictimIntimidatedId = mapIdsToGuids(witnessVictimIds.victimIntimidatedId(), caseUrn, caseId);


        context.set("witnessVictimGuids", new WitnessVictim(persistedWitnessId, persistedWitnessChildId, persistedWitnessExpertId, persistedWitnessPrisonerId,
                persistedWitnessInterpreterId, persistedWitnessVulnerableId, persistedWitnessPoliceId, persistedWitnessProfessionalId, persistedWitnessIntimidatedId,
                persistedVictimId, persistedVictimChildId, persistedVictimExpertId, persistedVictimPrisonerId, persistedVictimInterpreterId,
                persistedVictimVulnerableId, persistedVictimPoliceId, persistedVictimProfessionalId, persistedVictimIntimidatedId ));
    }

//    public List<String> getPersistedIds( List<String> witnessOrVictimIds, String caseUrn, String caseId){
//        if(!witnessOrVictimIds.isEmpty()) {
//            return mapIdsToGuids(witnessOrVictimIds, caseUrn, caseId);
//        }
//    }


    @Then("the witness personal details are sent to VCA")
    public void theWitnessAndVictimPersonalDetailsAreSentToCMS() {

        WitnessVictim witnessVictimGuids = context.getCastClazz("witnessVictimGuids", WitnessVictim.class);
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

        WitnessVictim witnessVictimIds = context.getCastClazz("witnessVictimIds", WitnessVictim.class);
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
        if(ids.isEmpty()){
            return null;
        } else {
            return ids.stream()
                    .map(id -> witnessService.victimWitnessGuid(caseUrn, caseId, id))
                    .toList();
        }
    }
}