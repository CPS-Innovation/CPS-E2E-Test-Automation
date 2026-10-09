package com.cps.fct.e2e.utils.services.caseCreation;

import com.cps.fct.e2e.model.caseCreation.CaseResponse;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.JsonUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpClientBuilder;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.caseCreation.payloadBuilders.PayloadBuilderForCM01;
import com.cps.fct.e2e.utils.services.caseCreation.payloadBuilders.PayloadBuilderForLM04;
import com.cps.fct.e2e.utils.services.BaseService;
import org.picocontainer.annotations.Inject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.JsonUtils.extractFromJson;
import static com.cps.fct.e2e.utils.common.JsonUtils.extractFromJsonToList;
import static java.lang.String.format;

public class CaseCreateService extends BaseService {

    private static final int TRANSIENT_REQUEST_RETRIES = 2;
    private static final int TRANSIENT_REQUEST_RETRY_DELAY_MILLIS = 10_000;

    @Inject
    PayloadBuilderForCM01 CM01;

    @Inject
    PayloadBuilderForLM04 LM04;

    public HttpResponseWrapper cm01WithCaseDetails(File caseFile, String messageType, ScenarioContext context) throws IOException {
        String payloadForDefendantAndCharge = Files.readString(caseFile.toPath());
        String modifiedRequestJson = CM01.generateCM01PayloadWithValues(payloadForDefendantAndCharge, context);
        context.set("modifiedCM01RequestPayload", modifiedRequestJson);
        return sendCM01(modifiedRequestJson, messageType);
    }

    private HttpResponseWrapper sendCM01(String payload, String messageType) {
        return service.sendRequest(createCase(payload, messageType));
    }

    private HttpClientBuilder createCase(String payloadInString, String messageType) {
        String caseType = context.get("caseType");
        String caseTypeLowerCase = caseType.toLowerCase();
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("CASE_CREATE_API"))
                .endpoint(format("/api/%s/cm01", caseTypeLowerCase))
                .addHeaders(caseCreateHeaders())
                .body(payloadInString)
                .method("POST")
                .retry(TRANSIENT_REQUEST_RETRIES)
                .retryDelay(TRANSIENT_REQUEST_RETRY_DELAY_MILLIS)
                .resourceName(messageType)
                .build();
    }

    public void getCM01RequestId(HttpResponseWrapper response, ScenarioContext context) {
        CaseResponse caseResponse = JsonUtils.fromJson(response.getBody(), CaseResponse.class);
        context.set("case", caseResponse);
        context.set("cm01Success", caseResponse.isSuccess());
        context.set("cm01RequestId", caseResponse.getRequestId());
    }

    public HttpResponseWrapper caseDetails(String caseCreateRequestId, ScenarioContext context) throws InterruptedException {
        return service.sendRequest(getCaseDetails(caseCreateRequestId));
    }

    private HttpClientBuilder getCaseDetails(String caseCreateRequestId) throws InterruptedException {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("CASE_CREATE_API"))
                .endpoint(format("/api/request/%s", caseCreateRequestId))
                .addHeaders(caseCreateHeaders())
                .method("GET")
                .resourceName("CaseDetails")
                .build();
    }

    public void persistCaseDetails(HttpResponseWrapper response, ScenarioContext context) {
        CaseResponse caseResponse = JsonUtils.fromJson(response.getBody(), CaseResponse.class);
        context.set("caseData", caseResponse);
        context.set("caseId", caseResponse.getCaseId());
        context.set("caseUrn", caseResponse.getCaseUrn());
    }

    public HttpResponseWrapper lm04AddVictimWitness(File victimWitness, String messageType, ScenarioContext context) throws IOException {
        String payloadForNewVictimWitness = Files.readString(victimWitness.toPath());
        String modifiedRequestJson = LM04.generateLM04PayloadWithValues(messageType, payloadForNewVictimWitness, context);
        context.set("modifiedLM04RequestPayload", modifiedRequestJson);
        return sendLM04(modifiedRequestJson, messageType);

    }

    private HttpResponseWrapper sendLM04(String payload, String messageType) {
        return service.sendRequest(createVictimWitness(payload, messageType));
    }

    private HttpClientBuilder createVictimWitness(String payloadInString, String messageType) {
        String caseType = context.get("caseType");
        String caseTypeLowerCase = caseType.toLowerCase();
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("CASE_CREATE_API"))
                .endpoint("/api/dcf/lm04")
                .endpoint(format("/api/%s/lm04", caseTypeLowerCase))
                .addHeaders(caseCreateHeaders())
                .body(payloadInString)
                .method("POST")
                .retry(TRANSIENT_REQUEST_RETRIES)
                .retryDelay(TRANSIENT_REQUEST_RETRY_DELAY_MILLIS)
                .resourceName(messageType)
                .build();
    }

    public void getLM04RequestId(HttpResponseWrapper response, ScenarioContext context) {
        CaseResponse caseResponse = JsonUtils.fromJson(response.getBody(), CaseResponse.class);
        context.set("case", caseResponse);
        context.set("lm04Success", caseResponse.isSuccess());
        context.set("lm04RequestId", caseResponse.getRequestId());
    }


    public HttpResponseWrapper victimWitnessDetailsList(String caseId) {
        return service.sendRequest(getVictimWitnessDetailsListFromCmsRequestParams(caseId));
    }

    private HttpClientBuilder getVictimWitnessDetailsListFromCmsRequestParams(String caseId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/witnesses", caseId))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("victimWitnessListFromCMS")
                .build();
    }

    public void victimWitnessDetails(HttpResponseWrapper response, ScenarioContext context) {
        String body = response.getBody();
        List<String> witnessId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isKeyWitness=='Yes')].witnessId");
        List<String> witnessName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isKeyWitness=='Yes')].witnessFullName");

        List<String> witnessChildId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isChild==true)].witnessId");
        List<String> witnessChildName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isChild==true)].witnessFullName");

        List<String> witnessExpertId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isExpert==true)].witnessId");
        List<String> witnessExpertName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isExpert==true)].witnessFullName");

        List<String> witnessPrisonerId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isPrisoner==true)].witnessId");
        List<String> witnessPrisonerName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isPrisoner==true)].witnessFullName");

        List<String> witnessInterpreterId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isInterpreter==true)].witnessId");
        List<String> witnessInterpreterName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isInterpreter==true)].witnessFullName");

        List<String> witnessVulnerableId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isVulnerable==true)].witnessId");
        List<String> witnessVulnerableName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isVulnerable==true)].witnessFullName");

        List<String> witnessPoliceId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isPolice==true)].witnessId");
        List<String> witnessPoliceName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isPolice==true)].witnessFullName");

        List<String> witnessProfessionalId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isProfessional==true)].witnessId");
        List<String> witnessProfessionalName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isProfessional==true)].witnessFullName");

        List<String> witnessIntimidatedId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isIntimidated==true)].witnessId");
        List<String> witnessIntimidatedName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isIntimidated==true)].witnessFullName");

        List<String> witnessSpecialId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isSpecialNeeds==true)].witnessId");
        List<String> witnessSpecialName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==false && @.isSpecialNeeds==true)].witnessFullName");

        List<String> victimWitnessId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isKeyWitness=='Yes')].witnessId");
        List<String> victimWitnessName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isKeyWitness=='Yes')].witnessFullName");

        List<String> victimWitnessChildId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isChild==true)].witnessId");
        List<String> victimWitnessChildName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isChild==true)].witnessFullName");

        List<String> victimWitnessExpertId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isExpert==true)].witnessId");
        List<String> victimWitnessExpertName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isExpert==true)].witnessFullName");

        List<String> victimWitnessPrisonerId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isPrisoner==true)].witnessId");
        List<String> victimWitnessPrisonerName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isPrisoner==true)].witnessFullName");

        List<String> victimWitnessInterpreterId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isInterpreter==true)].witnessId");
        List<String> victimWitnessInterpreterName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isInterpreter==true)].witnessFullName");

        List<String> victimWitnessVulnerableId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isVulnerable==true)].witnessId");
        List<String> victimWitnessVulnerableName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isVulnerable==true)].witnessFullName");

        List<String> victimWitnessPoliceId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isPolice==true)].witnessId");
        List<String> victimWitnessPoliceName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isPolice==true)].witnessFullName");

        List<String> victimWitnessProfessionalId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isProfessional==true)].witnessId");
        List<String> victimWitnessProfessionalName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isProfessional==true)].witnessFullName");

        List<String> victimWitnessIntimidatedId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isIntimidated==true)].witnessId");
        List<String> victimWitnessIntimidatedName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isIntimidated==true)].witnessFullName");

        List<String> victimWitnessSpecialId = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isSpecialNeeds==true)].witnessId");
        List<String> victimWitnessSpecialName = extractFromJsonToList(body,
                "$[?(@.isWitnessAndVictim==true && @.isSpecialNeeds==true)].witnessFullName");

        List<String> victimId = extractFromJsonToList(body,
                "$[?(@.isPureVictim==true && @.isWitnessAndVictim==false && @.isVulnerable==false && @.isIntimidated==false)].witnessId");
        List<String> victimName = extractFromJsonToList(body,
                "$[?(@.isPureVictim==true && @.isWitnessAndVictim==false && @.isVulnerable==false && @.isIntimidated==false)].witnessFullName");

        List<String> victimVulnerableId = extractFromJsonToList(body,
                "$[?(@.isPureVictim==true && @.isWitnessAndVictim==false && @.isVulnerable==true && @.isIntimidated==false)].witnessId");
        List<String> victimVulnerableName = extractFromJsonToList(body,
                "$[?(@.isPureVictim==true && @.isWitnessAndVictim==false && @.isVulnerable==true && @.isIntimidated==false)].witnessFullName");

        List<String> victimIntimidatedId = extractFromJsonToList(body,
                "$[?(@.isPureVictim==true && @.isWitnessAndVictim==false && @.isVulnerable==false && @.isIntimidated==true)].witnessId");
        List<String> victimIntimidatedName = extractFromJsonToList(body,
                "$[?(@.isPureVictim==true && @.isWitnessAndVictim==false && @.isVulnerable==false && @.isIntimidated==true)].witnessFullName");


        Map<String, List<String>> witnessMapIds = new HashMap<>();
        Map<String, List<String>> idWitnessNameMap = new HashMap<>();

        Map<String, List<String>> victimWitnessMapIds = new HashMap<>();
        Map<String, List<String>> idVictimWitnessNameMap = new HashMap<>();


        witnessMapIds.put("witness", witnessId);
        witnessMapIds.put("witnessChild", witnessChildId);
        witnessMapIds.put("witnessExpert", witnessExpertId);
        witnessMapIds.put("witnessPrisoner", witnessPrisonerId);
        witnessMapIds.put("witnessInterpreter", witnessInterpreterId);
        witnessMapIds.put("witnessVulnerable", witnessVulnerableId);
        witnessMapIds.put("witnessPolice", witnessPoliceId);
        witnessMapIds.put("witnessProfessional", witnessProfessionalId);
        witnessMapIds.put("witnessIntimidated", witnessIntimidatedId);
        witnessMapIds.put("witnessSpecial", witnessSpecialId);

        victimWitnessMapIds.put("victimWitness", victimWitnessId);
        victimWitnessMapIds.put("victimWitnessChild", victimWitnessChildId);
        victimWitnessMapIds.put("victimWitnessExpert", victimWitnessExpertId);
        victimWitnessMapIds.put("victimWitnessPrisoner", victimWitnessPrisonerId);
        victimWitnessMapIds.put("victimWitnessInterpreter", victimWitnessInterpreterId);
        victimWitnessMapIds.put("victimWitnessVulnerable", victimWitnessVulnerableId);
        victimWitnessMapIds.put("victimWitnessPolice", victimWitnessPoliceId);
        victimWitnessMapIds.put("victimWitnessProfessional", victimWitnessProfessionalId);
        victimWitnessMapIds.put("victimWitnessIntimidated", victimWitnessIntimidatedId);
        victimWitnessMapIds.put("victimWitnessSpecial", victimWitnessSpecialId);

        victimWitnessMapIds.put("victim", victimId);
        victimWitnessMapIds.put("victimVulnerable", victimVulnerableId);
        victimWitnessMapIds.put("victimIntimidated", victimIntimidatedId);

        context.set("witnessMapIds", witnessMapIds);
        context.set("victimWitnessMapIds", victimWitnessMapIds);

        idWitnessNameMap.put(String.valueOf(witnessId), witnessName);
        idWitnessNameMap.put(String.valueOf(witnessChildId), witnessChildName);
        idWitnessNameMap.put(String.valueOf(witnessExpertId), witnessExpertName);
        idWitnessNameMap.put(String.valueOf(witnessInterpreterId), witnessInterpreterName);
        idWitnessNameMap.put(String.valueOf(witnessIntimidatedId), witnessIntimidatedName);
        idWitnessNameMap.put(String.valueOf(witnessPoliceId), witnessPoliceName);
        idWitnessNameMap.put(String.valueOf(witnessPrisonerId), witnessPrisonerName);
        idWitnessNameMap.put(String.valueOf(witnessProfessionalId), witnessProfessionalName);
        idWitnessNameMap.put(String.valueOf(witnessVulnerableId), witnessVulnerableName);
        idWitnessNameMap.put(String.valueOf(witnessSpecialId),witnessSpecialName);

        idVictimWitnessNameMap.put(String.valueOf(victimWitnessId), victimWitnessName);
        idVictimWitnessNameMap.put(String.valueOf(victimWitnessChildId), victimWitnessChildName);
        idVictimWitnessNameMap.put(String.valueOf(victimWitnessExpertId), victimWitnessExpertName);
        idVictimWitnessNameMap.put(String.valueOf(victimWitnessIntimidatedId), victimWitnessIntimidatedName);
        idVictimWitnessNameMap.put(String.valueOf(victimWitnessInterpreterId), victimWitnessInterpreterName);
        idVictimWitnessNameMap.put(String.valueOf(victimWitnessPoliceId), victimWitnessPoliceName);
        idVictimWitnessNameMap.put(String.valueOf(victimWitnessPrisonerId), victimWitnessPrisonerName);
        idVictimWitnessNameMap.put(String.valueOf(victimWitnessPrisonerId), victimWitnessProfessionalName);
        idVictimWitnessNameMap.put(String.valueOf(victimWitnessVulnerableId), victimWitnessVulnerableName);
        idVictimWitnessNameMap.put(String.valueOf(victimWitnessSpecialId), victimWitnessSpecialName);

        idVictimWitnessNameMap.put(String.valueOf(victimId), victimName);
        idVictimWitnessNameMap.put(String.valueOf(victimIntimidatedId), victimIntimidatedName);
        idVictimWitnessNameMap.put(String.valueOf(victimVulnerableId), victimWitnessVulnerableName);

        context.set("idVictimWitnessNameMap", idVictimWitnessNameMap);
        context.set("idWitnessNameMap", idWitnessNameMap);

    }

    public HttpResponseWrapper victimWitnessFirstnameSurname(String caseId, String victimWitnessId) {
        return service.sendRequest(getVictimWitnessFirstnameSurnameRequestParams(caseId, victimWitnessId));
    }

    private HttpClientBuilder getVictimWitnessFirstnameSurnameRequestParams(String caseId, String victimWitnessId) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint(format("/api/cases/%s/victim-witnesses/%s", caseId,victimWitnessId))
                .addHeaders(ddeiHeaders())
                .method("GET")
                .resourceName("victimWitnessFirstnameSurname")
                .build();
    }

    public String victimWitnessFirstnameAndSurname(HttpResponseWrapper response){
        String body = response.getBody();
        String firstname = extractFromJson(body,"$.firstNames");
        String surname = extractFromJson(body,"$.firstNames");
        return firstname + " " + surname;
    }












}




















