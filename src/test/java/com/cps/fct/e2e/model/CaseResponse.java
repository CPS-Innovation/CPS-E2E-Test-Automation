package com.cps.fct.e2e.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CaseResponse {

    // Getters and Setters
    private String message;
        private String caseId;
        private String urn;
        private Response response;
        private String result;

        // Inner class for response
        @Setter
        @Getter
        public static class Response {
            private int code;
            private String text;

        }

}

