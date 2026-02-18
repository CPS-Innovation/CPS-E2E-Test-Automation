package com.cps.fct.e2e.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PreChargeDecision {

    private String decision;
    private AcceptedDecision acceptedDecision;
    private RejectedDecision rejectedDecision;
    private String caseType;
    private String decisionToBeMade;


    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AcceptedDecision {
        private String caseRecieved;
        private Integer partyId;
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RejectedDecision {
        private String actionPlanDue;
        private String chaseTaskDue;
    }

}

