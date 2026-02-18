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

public class DCFMessageService extends BaseService {

    @Inject
    DCFPayloadBuilderForCM01 forCM01;

    @Inject
    DCFPayloadBuilderForLM04 forLM04;

    public HttpResponseWrapper cm01WithADefendantCharge(File caseFile, String messageType, ScenarioContext context) throws IOException {
        String payloadForDefendantAndCharge = Files.readString(caseFile.toPath());
         return send(forCM01.generatePayloadWithValues(payloadForDefendantAndCharge, context), messageType);
    }


    public void lmO4AddVictimWitness(File victimWitness, String messageType, ScenarioContext context) throws IOException {
        String payloadForNewVictimWitness = Files.readString(victimWitness.toPath());
        send(forLM04.generatePayloadWithValues(messageType, payloadForNewVictimWitness, context), messageType);
    }


    private HttpResponseWrapper send(String payload, String messageType) {
        return  service.sendRequest(createDCFMessage(payload, messageType));
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
