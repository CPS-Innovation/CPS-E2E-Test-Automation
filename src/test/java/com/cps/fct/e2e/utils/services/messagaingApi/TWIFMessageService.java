package com.cps.fct.e2e.utils.services.messagaingApi;

import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpClientBuilder;
import com.cps.fct.e2e.utils.payloadBuilders.twif.TWIFPayloadBuilderForCM01;
import com.cps.fct.e2e.utils.services.BaseService;
import org.picocontainer.annotations.Inject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.lang.String.format;

public class TWIFMessageService extends BaseService {

    @Inject
    TWIFPayloadBuilderForCM01 forCM01;

    public void cm01WithADefendantCharge(File caseFile, String messageType, ScenarioContext context) throws IOException {
        String payloadForDefendantAndCharge = Files.readString(caseFile.toPath());
         send(forCM01.generatePayloadWithValues(payloadForDefendantAndCharge, context), messageType);
    }

    private  void send(String payload, String messageType) {
           service.sendRequest(requestParams(payload, messageType));
    }

    private HttpClientBuilder requestParams(String payloadInString, String messageType) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("TWIF_MESSAGING_API"))
                .endpoint(format("/twif/%s", messageType.toLowerCase()))
                .addHeader("Content-Type","application/json")
                .addHeader("Accept","*/*")
                .body(payloadInString)
                .method("POST")
                .resourceName(messageType.toLowerCase())
                .build();
    }

}
