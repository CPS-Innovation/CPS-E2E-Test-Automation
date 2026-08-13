package com.cps.fct.e2e.utils.services.messagaingApi;

import com.cps.fct.e2e.model.CaseResponse;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.JsonUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpClientBuilder;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.payloadBuilders.PayloadBuilderForCM01;
import com.cps.fct.e2e.utils.payloadBuilders.PayloadBuilderForLM04;
import com.cps.fct.e2e.utils.services.BaseService;
import org.picocontainer.annotations.Inject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.lang.String.format;

public class CaseCreateService extends BaseService{

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
        private HttpResponseWrapper sendCM01 (String payload, String messageType) {
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
        context.set("case",caseResponse);
        context.set("cm01Success", caseResponse.isSuccess());
        context.set("cm01RequestId", caseResponse.getRequestId());
    }

    public HttpResponseWrapper caseDetails(String caseCreateRequestId, ScenarioContext context) throws InterruptedException {
        return service.sendRequest(getCaseDetails(caseCreateRequestId));
    }

    private HttpClientBuilder getCaseDetails(String caseCreateRequestId) throws InterruptedException {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("CASE_CREATE_API"))
                .endpoint(format("/api/request/%s",caseCreateRequestId))
                .addHeaders(caseCreateHeaders())
                .method("GET")
                .resourceName("CaseDetails")
                .build();
    }

    public void persistCaseDetails(HttpResponseWrapper response, ScenarioContext context) {
        CaseResponse caseResponse = JsonUtils.fromJson(response.getBody(), CaseResponse.class);
        context.set("caseData",caseResponse);
        context.set("caseId", caseResponse.getCaseId());
        context.set("caseUrn", caseResponse.getCaseUrn());
    }

    public HttpResponseWrapper lm04AddVictimWitness(File victimWitness, String messageType, ScenarioContext context) throws IOException {
        String payloadForNewVictimWitness = Files.readString(victimWitness.toPath());
        String modifiedRequestJson = LM04.generateLM04PayloadWithValues(messageType, payloadForNewVictimWitness, context);
        context.set("modifiedLM04RequestPayload", modifiedRequestJson);
        return sendLM04(modifiedRequestJson, messageType);

    }

    private HttpResponseWrapper sendLM04 (String payload, String messageType) {
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
        context.set("case",caseResponse);
        context.set("lm04Success", caseResponse.isSuccess());
        context.set("lm04RequestId", caseResponse.getRequestId());
    }

}




















