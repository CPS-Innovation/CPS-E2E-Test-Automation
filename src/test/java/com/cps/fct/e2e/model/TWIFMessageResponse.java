package com.cps.fct.e2e.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TWIFMessageResponse {

    @JsonProperty("success")
    private boolean success;

//    @JsonProperty("messageType")
//    private String messageType;

    @JsonProperty("validationResult")
    private ValidationResult validationResult;

    @JsonProperty("soapResponse")
    private SOAPResponse soapResponse;

    @JsonProperty("errors")
    private List<String> errors;

    public boolean isFullyValid() {
        return success
                && validationResult != null && validationResult.isValid
                && soapResponse != null && soapResponse.success
                && (errors == null || errors.isEmpty());
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ValidationResult {
        @JsonProperty("isValid")
        private boolean isValid;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SOAPResponse {
        @JsonProperty("success")
        private boolean success;
    }
}
