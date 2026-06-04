package com.cps.fct.e2e.utils.services.messagaingApi;

import com.cps.fct.e2e.model.CaseResponse;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.JsonUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpClientBuilder;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.payloadBuilders.dcf.DCFPayloadBuilderForCM01;
import com.cps.fct.e2e.utils.payloadBuilders.dcf.DCFPayloadBuilderForLM04;
import com.cps.fct.e2e.utils.services.BaseService;
import org.picocontainer.annotations.Inject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import static java.lang.String.format;

public class DCFMessageService extends BaseService {

    @Inject
    DCFPayloadBuilderForCM01 forCM01;

    @Inject
    DCFPayloadBuilderForLM04 forLM04;

    public HttpResponseWrapper cm01WithADefendantCharge(File caseFile, String messageType, ScenarioContext context) throws IOException {
        String payloadForDefendantAndCharge = Files.readString(caseFile.toPath());
        String modifiedRequestJson = forCM01.generateCM01PayloadWithValues(payloadForDefendantAndCharge, context);
        context.set("modifiedRequestPayload", modifiedRequestJson);
        return sendCM01(modifiedRequestJson, messageType);
    }

    public HttpResponseWrapper cm01DcfCaseDetails(String caseCreateRequestId, ScenarioContext context) throws InterruptedException {
        return  service.sendRequest(getDcfCaseDetails(caseCreateRequestId));
    }



//    private HttpResponseWrapper send(String payload, String messageType) {
//        return  service.sendRequest(createDCFMessage(payload, messageType));
//    }

    private HttpResponseWrapper sendCM01 (String payload, String messageType) {
        return service.sendRequest(createDcfCase(payload, messageType));
    }

    private HttpResponseWrapper sendLM04 (String payload, String messageType) {
        return service.sendRequest(createDcfVictimWitness(payload, messageType));
    }


    private HttpClientBuilder createDcfCase(String payloadInString, String messageType) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("CASE_CREATE_API"))
                .endpoint("/api/dcf/cm01")
                .addHeaders(caseCreateHeaders())
                .body(payloadInString)
                .method("POST")
                .resourceName(messageType)
                .build();
    }

    private HttpClientBuilder getDcfCaseDetails(String caseCreateRequestId) throws InterruptedException {
//        Thread.sleep(90000);
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("CASE_CREATE_API"))
                .endpoint(format("/api/request/%s",caseCreateRequestId))
                .addHeaders(caseCreateHeaders())
                .method("GET")
                .resourceName("dcfCaseDetails")
                .build();
    }

    public void persistCM01RequestId(HttpResponseWrapper response, ScenarioContext context) {
        CaseResponse caseResponse = JsonUtils.fromJson(response.getBody(), CaseResponse.class);
        context.set("case",caseResponse);
        context.set("cm01Success", caseResponse.isSuccess());
        context.set("dcfCm01RequestId", caseResponse.getRequestId());
    }

    public void persistDcfCaseDetails(HttpResponseWrapper response, ScenarioContext context) {
        CaseResponse caseResponse = JsonUtils.fromJson(response.getBody(), CaseResponse.class);
        context.set("caseData",caseResponse);
        context.set("caseId", caseResponse.getCaseId());
        context.set("caseUrn", caseResponse.getCaseUrn());
    }

    private HttpClientBuilder createDcfVictimWitness(String payloadInString, String messageType) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("CASE_CREATE_API"))
                .endpoint("/api/dcf/lm04")
                .addHeaders(caseCreateHeaders())
                .body(payloadInString)
                .method("POST")
                .resourceName(messageType)
                .build();
    }

    public HttpResponseWrapper lm04DcfVictimWitnessDetails(String caseCreateRequestId, ScenarioContext context) throws InterruptedException {
        return  service.sendRequest(getDcfCaseDetails(caseCreateRequestId));
    }

    public void lmO4AddVictimWitness(File victimWitness, String messageType, ScenarioContext context) throws IOException {
        String payloadForNewVictimWitness = Files.readString(victimWitness.toPath());
        sendLM04(forLM04.generateLM04PayloadWithValues(messageType, payloadForNewVictimWitness, context), messageType);


    }

    private HttpClientBuilder getDcfLm04Details(String caseCreateRequestId) throws InterruptedException {
//        Thread.sleep(90000);
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("CASE_CREATE_API"))
                .endpoint(format("/api/request/%s",caseCreateRequestId))
                .addHeaders(caseCreateHeaders())
                .method("GET")
                .resourceName("dcfLm04Details")
                .build();
    }

    public void persistLM04RequestId(HttpResponseWrapper response, ScenarioContext context) {
        CaseResponse caseResponse = JsonUtils.fromJson(response.getBody(), CaseResponse.class);
        context.set("case",caseResponse);
        context.set("lm04Success", caseResponse.isSuccess());
        context.set("dcfLm04RequestId", caseResponse.getRequestId());
    }

    private Map<String, String> messageServiceHeaders() {
        return Map.of(
                "session-id", EnvConfig.get("SESSION_ID"),
                "Content-Type", "application/json",
                "transaction-id", EnvConfig.get("TRANSACTION_ID"));
    }

    private HttpClientBuilder createDCFMessage(String payloadInString, String messageType) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DCF_MESSAGING_API"))
                .endpoint("/inbound-json")
                .addHeaders(messageServiceHeaders())
                .body(payloadInString)
                .method("POST")
                .resourceName(messageType)
                .build();
    }

    public void persistCaseDetails(HttpResponseWrapper response, ScenarioContext context) {
        CaseResponse caseResponse = JsonUtils.fromJson(response.getBody(), CaseResponse.class);
        context.set("caseDetails",caseResponse);
        context.set("caseId", caseResponse.getCaseId());
        context.set("caseUrn", caseResponse.getUrn());
    }


}
