package com.cps.fct.e2e.utils.services.messagaingApi.assertions;

import com.cps.fct.e2e.model.TWIFMessageResponse;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.httpClient.ResourceResponseStore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

public class TWIFAssertions {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void assertTWIFRequest(String messageType) throws JsonProcessingException {
        HttpResponseWrapper responseStore =
                ResourceResponseStore.getLatestResponse(messageType.toLowerCase());
        String jsonString = responseStore.getBody();
        TWIFMessageResponse response = mapper.readValue(jsonString, TWIFMessageResponse.class);
        assertThat(response.isFullyValid()).isTrue();
    }
}
