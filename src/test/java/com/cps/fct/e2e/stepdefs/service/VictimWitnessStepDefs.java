package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.enums.PreferredMethodOfContact;
import com.cps.fct.e2e.model.Victim;
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

    @And("the {string} personal details are entered")
    public void thePersonalDetailsAreEntered(String witnessVictimType){
        String caseId = context.get("caseId");
        Witness witnessIds = null;
        Victim victimIds = null;

        if (witnessVictimType.equalsIgnoreCase("witness")) {
            witnessIds = context.getCastClazz("witnessIds", Witness.class);
            Map<String, VictimWitnessDetails> witnessDataMap = witnessIds.witnessId().stream()
                    .collect(Collectors.toMap(
                            id -> id,
                            id -> {
                                VictimWitnessDetails details = getVictimWitnessDetails();
                                witnessService.addVictimWitnessCMSPersonalDetails(details, caseId, id);
                                return details;
                            }
                    ));
            context.set("victimWitnessDetailsWrapper", new VictimWitnessDetailsMapWrapper(witnessDataMap));

        } else{
            victimIds = context.getCastClazz("victimIds", Victim.class);
            Map<String, VictimWitnessDetails> witnessDataMap = victimIds.victimId().stream()
                    .collect(Collectors.toMap(
                            id -> id,
                            id -> {
                                VictimWitnessDetails details = getVictimWitnessDetails();
                                witnessService.addVictimWitnessCMSPersonalDetails(details, caseId, id);
                                return details;
                            }
                    ));
            context.set("victimWitnessDetailsWrapper", new VictimWitnessDetailsMapWrapper(witnessDataMap));
        }






    }







    @When("the witness's personal details are entered")
    public void theWitnessSPersonalDetailsAreEntered() {

        Witness witnessVictimIds = context.getCastClazz("witnessVictimIds", Witness.class);
        String caseId = context.get("caseId");

        //NeedToLook - Murali
//        Map<String, VictimWitnessDetails> victimWitnessDataMap = witnessVictimIds.witnessId().stream()
//                .collect(Collectors.toMap(
//                        id -> id,
//                        id -> {
//                            VictimWitnessDetails details = getVictimWitnessDetails();
//                            witnessService.addVictimWitnessCMSPersonalDetails(details, caseId, id);
//                            return details;
//                        }
//                ));
//        context.set("victimWitnessDetailsWrapper", new VictimWitnessDetailsMapWrapper(victimWitnessDataMap));

    }

    @And("witness and victim are added to VCA")
    public void witnessAndVictimDetailsAreAddedToVCA() {
        Witness witnessIds = context.getCastClazz("witnessIds", Witness.class);
        Victim victimIds = context.getCastClazz("victimIds", Victim.class);
        String caseUrn = context.get("caseUrn");
        String caseId = context.get("caseId");

        List<String> persistedWitnessGuid = mapIdsToGuids(witnessIds.witnessId(), caseUrn, caseId );
        List<String> persistedWitnessChildGuid = mapIdsToGuids(witnessIds.witnessChildId(), caseUrn, caseId);
        List<String> persistedWitnessExpertGuid = mapIdsToGuids(witnessIds.witnessExpertId(), caseUrn, caseId);
        List<String> persistedWitnessPrisonerGuid = mapIdsToGuids(witnessIds.witnessPrisonerId(), caseUrn, caseId);
        List<String> persistedWitnessInterpreterGuid = mapIdsToGuids(witnessIds.witnessInterpreterId(), caseUrn, caseId);
        List<String> persistedWitnessVulnerableGuid = mapIdsToGuids(witnessIds.witnessVulnerableId(), caseUrn, caseId);
        List<String> persistedWitnessPoliceGuid = mapIdsToGuids(witnessIds.witnessPoliceId(), caseUrn, caseId);
        List<String> persistedWitnessProfessionalGuid = mapIdsToGuids(witnessIds.witnessProfessionalId(), caseUrn, caseId);
        List<String> persistedWitnessIntimidatedGuid = mapIdsToGuids(witnessIds.witnessIntimidatedId(), caseUrn, caseId);
        List<String> persistedVictimGuid = mapIdsToGuids(victimIds.victimId(), caseUrn, caseId);
        List<String> persistedVictimChildGuid = mapIdsToGuids(victimIds.victimChildId(), caseUrn, caseId);
        List<String> persistedVictimExpertGuid = mapIdsToGuids(victimIds.victimExpertId(), caseUrn, caseId);
        List<String> persistedVictimPrisonerGuid = mapIdsToGuids(victimIds.victimPrisonerId(), caseUrn, caseId);
        List<String> persistedVictimInterpreterGuid = mapIdsToGuids(victimIds.victimInterpreterId(), caseUrn, caseId);
        List<String> persistedVictimVulnerableGuid = mapIdsToGuids(victimIds.victimVulnerableId(), caseUrn, caseId);
        List<String> persistedVictimPoliceGuid = mapIdsToGuids(victimIds.victimPoliceId(), caseUrn, caseId);
        List<String> persistedVictimProfessionalGuid = mapIdsToGuids(victimIds.victimProfessionalId(), caseUrn, caseId);
        List<String> persistedVictimIntimidatedGuid = mapIdsToGuids(victimIds.victimIntimidatedId(), caseUrn, caseId);

        context.set("witnessGuids", new Witness(persistedWitnessGuid, persistedWitnessChildGuid, persistedWitnessExpertGuid, persistedWitnessPrisonerGuid,
                persistedWitnessInterpreterGuid, persistedWitnessVulnerableGuid, persistedWitnessPoliceGuid, persistedWitnessProfessionalGuid, persistedWitnessIntimidatedGuid ));

        context.set("victimGuids", new Victim(persistedVictimGuid, persistedVictimChildGuid, persistedVictimExpertGuid, persistedVictimPrisonerGuid, persistedVictimInterpreterGuid,
                persistedVictimVulnerableGuid, persistedVictimPoliceGuid, persistedVictimProfessionalGuid, persistedVictimIntimidatedGuid ));
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
        if(ids.isEmpty()){
            return null;
        } else {
            return ids.stream()
                    .map(id -> witnessService.victimWitnessGuid(caseUrn, caseId, id))
                    .toList();
        }
    }
}