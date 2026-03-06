package com.cps.fct.e2e.utils.services.ddei;

import com.cps.fct.e2e.model.VictimWitnessDetails;
import com.cps.fct.e2e.model.WitnessVictim;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpClientBuilder;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.BaseService;
import com.jayway.jsonpath.JsonPath;

import java.util.List;

import static com.cps.fct.e2e.utils.common.JsonUtils.extractFromJsonToList;
import static com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimWitnessPayloadBuilder.payLoadForAddVictimWitnessToVCA;
import static com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimWitnessPayloadBuilder.payLoadForAddWitnessDetailsWitnessId;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class WitnessService extends BaseService {

     public void addVictimWitnessCMSPersonalDetails(VictimWitnessDetails details, String caseId, String witnessId) {
       service.sendRequest(UpdateWitnessDetailsWitnessIdRequestParams(details, caseId, witnessId));
     }

     public String victimWitnessGuid(String caseUrn, String caseId, String WitnessVictimId){
        HttpResponseWrapper responseWrapper = service.sendRequest(
                addVictimOrWitnessRequestParams(caseUrn, caseId, WitnessVictimId));
        String victimCaseInfoGuid = JsonPath.read(responseWrapper.getBody(), "$.value.victimCaseInfoGuid");
        assertThat(victimCaseInfoGuid)
                .withFailMessage("victimCaseInfoGuid")
                .isNotNull();
        return victimCaseInfoGuid;
    }

    public HttpResponseWrapper listWitnessVictimDetails(String caseId) {
       return service.sendRequest(witnessesDetailsRequestParams(caseId));
    }

    public void UpdateWitnessVictimDetailsToVCA(String witnessGuid, String requestBody ) {
        service.sendRequest(UpdateWitnessVictimVCADetailsRequestParams(witnessGuid,requestBody));
    }

    public void witnessesDetailsFromCMS( String caseUrn, String caseId, String witnessId) {
        service.sendRequest(witnessesDetailsFromCMSRequestParams(caseUrn,caseId, witnessId));
    }

    public void persistVictimWitnessDetails(HttpResponseWrapper response, ScenarioContext context) {
        String body = response.getBody();
        List<String> witnessId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isKeyWitness=='Yes')].witnessId");
        List<String> witnessChildId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isChild==true)].witnessId");
        List<String> witnessExpertId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isExpert==true)].witnessId");
        List<String> witnessPrisonerId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isPrisoner==true)].witnessId");
        List<String> witnessInterpreterId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isInterpreter==true)].witnessId");
        List<String> witnessVulnerableId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isVulnerable==true)].witnessId");
        List<String> witnessPoliceId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isPolice==true)].witnessId");
        List<String> witnessProfessionalId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isProfessional==true)].witnessId");
        List<String> witnessIntimidatedId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isIntimidated==true)].witnessId");
        List<String> victimId =  extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isKeyWitness=='Yes')].witnessId");
        List<String> victimChildId =  extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isChild==true)].witnessId");
        List<String> victimExpertId =  extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isExpert==true)].witnessId");
        List<String> victimPrisonerId =  extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isPrisoner==true)].witnessId");
        List<String> victimInterpreterId =  extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isInterpreter==true)].witnessId");
        List<String> victimVulnerableId =  extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isVulnerable==true)].witnessId");
        List<String> victimPoliceId =  extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isPolice==true)].witnessId");
        List<String> victimProfessionalId =  extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isProfessional==true)].witnessId");
        List<String> victimIntimidatedId =  extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isIntimidated==true)].witnessId");
        assertIdsArePresent(context, witnessId);
        assertIdsArePresent(context, witnessChildId);
        assertIdsArePresent(context, witnessExpertId);
        assertIdsArePresent(context, witnessPrisonerId);
        assertIdsArePresent(context, witnessInterpreterId);
        assertIdsArePresent(context, witnessVulnerableId);
        assertIdsArePresent(context, witnessPoliceId);
        assertIdsArePresent(context, witnessProfessionalId);
        assertIdsArePresent(context, witnessIntimidatedId);
        assertIdsArePresent(context, victimId);
        assertIdsArePresent(context, victimChildId);
        assertIdsArePresent(context, victimExpertId);
        assertIdsArePresent(context, victimPrisonerId);
        assertIdsArePresent(context, victimInterpreterId);
        assertIdsArePresent(context, victimVulnerableId);
        assertIdsArePresent(context, victimPoliceId);
        assertIdsArePresent(context, victimProfessionalId);
        assertIdsArePresent(context, victimIntimidatedId);
        context.set("witnessVictimIds", new WitnessVictim(witnessId, witnessChildId, witnessExpertId, witnessPrisonerId, witnessInterpreterId, witnessVulnerableId, witnessPoliceId, witnessProfessionalId, witnessIntimidatedId, victimId, victimChildId, victimExpertId, victimPrisonerId, victimInterpreterId, victimVulnerableId, victimPoliceId, victimProfessionalId, victimIntimidatedId));
    }

    private HttpClientBuilder witnessesDetailsRequestParams(String caseId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/witnesses", caseId))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("witnessDetails")
                .build();
    }

    private HttpClientBuilder witnessesDetailsFromCMSRequestParams(
            String caseUrn, String caseId, String witnessId) {

        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/urns/%s/cases/%s/parties/%s", caseUrn, caseId, witnessId))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("witnessDetailsFromCms")
                .build();
    }

    private HttpClientBuilder addVictimOrWitnessRequestParams(
            String caseUrn, String caseId, String WitnessVictimId) {

        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/urns/%s/cases/%s/parties/%s", caseUrn, caseId, WitnessVictimId))
                .addHeaders(ddeiHeaders())
                .method("POST")
                .body(payLoadForAddVictimWitnessToVCA(caseUrn))
                .resourceName("addVictimWitnessToVCA")
                .build();
    }

    private HttpClientBuilder UpdateWitnessVictimVCADetailsRequestParams(
            String WitnessGuid,String requestBody) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/victims/case-info/%s", WitnessGuid))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(requestBody)
                .resourceName("addVictimWitnessPersonalDetails")
                .build();
    }



    private HttpClientBuilder UpdateWitnessDetailsWitnessIdRequestParams(VictimWitnessDetails victimDetails,
                                                                         String caseId, String WitnessId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/witnesses/%s", caseId, WitnessId))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(payLoadForAddWitnessDetailsWitnessId(victimDetails))
                .resourceName("addVictimWitnessPersonalDetails")
                .build();
    }


    private static void assertIdsArePresent(ScenarioContext context, List<String> victimIds) {
        assertThat(victimIds)
                .withFailMessage("id's are should not be null " + context.get("caseId"))
                .isNotNull()
                .withFailMessage("id should contain at least one items " + context.get("caseId"))
                .isNotEmpty();
    }

}
