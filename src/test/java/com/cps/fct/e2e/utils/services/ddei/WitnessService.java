package com.cps.fct.e2e.utils.services.ddei;

import com.cps.fct.e2e.model.VictimWitnessDetails;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpClientBuilder;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.BaseService;
import com.jayway.jsonpath.JsonPath;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.restassured.response.Response;

import static com.cps.fct.e2e.utils.common.JsonUtils.extractFromJsonToList;
import static com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimWitnessPayloadBuilder.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.*;

public class WitnessService extends BaseService {
     private Response response;
     private String baseURI;

     public void addVictimWitnessCMSPersonalDetails(VictimWitnessDetails details, String caseId, String witnessId) {
       service.sendRequest(addWitnessDetailsWitnessIdRequestParams(details, caseId, witnessId));
     }

    public void updateVictimWitnessCMSPersonalDetails(VictimWitnessDetails details, String caseId, String witnessId) {
        service.sendRequest(updateWitnessDetailsWitnessIdRequestParams(details, caseId, witnessId));
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

    public void addWitnessVictimDetailsToVCA(String witnessGuid, String requestBody ) {
        service.sendRequest(addWitnessVictimVCADetailsRequestParams(witnessGuid,requestBody));
    }

    public void updateWitnessVictimDetailsToVCA(String witnessGuid, String requestBody ) {
        service.sendRequest(addWitnessVictimVCADetailsRequestParams(witnessGuid,requestBody));
    }

    public HttpResponseWrapper witnessesDetailsFromVCA( String caseUrn, String caseId, String witnessId) {
        return service.sendRequest(witnessesDetailsFromCMSRequestParams(caseUrn,caseId, witnessId));
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
//        assertIdsArePresent(context, witnessId);
//        assertIdsArePresent(context, witnessChildId);
//        assertIdsArePresent(context, witnessExpertId);
//        assertIdsArePresent(context, witnessPrisonerId);
//        assertIdsArePresent(context, witnessInterpreterId);
//        assertIdsArePresent(context, witnessVulnerableId);
//        assertIdsArePresent(context, witnessPoliceId);
//        assertIdsArePresent(context, witnessProfessionalId);
//        assertIdsArePresent(context, witnessIntimidatedId);
//        assertIdsArePresent(context, victimId);
//        assertIdsArePresent(context, victimChildId);
//        assertIdsArePresent(context, victimExpertId);
//        assertIdsArePresent(context, victimPrisonerId);
//        assertIdsArePresent(context, victimInterpreterId);
//        assertIdsArePresent(context, victimVulnerableId);
//        assertIdsArePresent(context, victimPoliceId);
//        assertIdsArePresent(context, victimProfessionalId);
//        assertIdsArePresent(context, victimIntimidatedId);

        Map<String, List<String>> witnessVictimMapIds = new HashMap<>();
        witnessVictimMapIds.put("witnessId", witnessId);
        witnessVictimMapIds.put("witnessChildId", witnessChildId);
        witnessVictimMapIds.put("witnessExpertId", witnessExpertId);
        witnessVictimMapIds.put("witnessPrisonerId", witnessPrisonerId);
        witnessVictimMapIds.put("witnessInterpreterId", witnessInterpreterId);
        witnessVictimMapIds.put("witnessVulnerableId", witnessVulnerableId);
        witnessVictimMapIds.put("witnessPoliceId", witnessPoliceId);
        witnessVictimMapIds.put("witnessProfessionalId", witnessProfessionalId);
        witnessVictimMapIds.put("witnessIntimidatedId", witnessIntimidatedId);

        witnessVictimMapIds.put("victimId", victimId );
        witnessVictimMapIds.put("victimChildId", victimChildId );
        witnessVictimMapIds.put("victimExpertId", victimExpertId );
        witnessVictimMapIds.put("victimPrisonerId", victimPrisonerId );
        witnessVictimMapIds.put("victimInterpreterId", victimInterpreterId);
        witnessVictimMapIds.put("victimVulnerableId", victimVulnerableId );
        witnessVictimMapIds.put("victimPoliceId", victimPoliceId);
        witnessVictimMapIds.put("victimProfessionalId", victimProfessionalId);
        witnessVictimMapIds.put("victimIntimidatedId", victimIntimidatedId );
        context.set("witnessVictimMapIds", witnessVictimMapIds);
     }

    public Response getCmsDetailsByWitnessOrVictimId(String caseId, String witnessVictimId ){
        baseURI = "https://fa-wm-app-ddei-staging.azurewebsites.net/api";
        String apiUrl = baseURI +"/cases/{caseId}/witnesses}";
        response =
                given()
                    .pathParam("caseId",caseId )
                    .headers(ddeiHeaders())
                .when()
                .get(apiUrl)
                .then()
                    .extract()
                    .response();
        return response;
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

    private HttpClientBuilder addWitnessVictimVCADetailsRequestParams(
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

    private HttpClientBuilder updateWitnessVictimVCADetailsRequestParams(
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

    private HttpClientBuilder addWitnessDetailsWitnessIdRequestParams(VictimWitnessDetails victimDetails,
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
    private HttpClientBuilder updateWitnessDetailsWitnessIdRequestParams(VictimWitnessDetails victimDetails,
                                                                      String caseId, String WitnessId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/witnesses/%s", caseId, WitnessId))
                .addHeaders(ddeiHeaders())
                .method("PATCH")
                .body(payLoadForUpdateWitnessDetailsWitnessId(victimDetails))
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
