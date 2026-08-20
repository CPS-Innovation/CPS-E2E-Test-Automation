package com.cps.fct.e2e.model.caseReviewApp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PreChargeDecision {

    private String decision;
    private AcceptedDecision acceptedDecision;
    private RejectedDecision rejectedDecision;
    private String caseType;
    private String decisionToBeMade;


    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AcceptedDecision {
        private String caseRecieved;
        private Integer partyId;
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RejectedDecision {
        private List<RejectionReason> rejectionReasons;
        private String actionPlanDue;
        private String chaseTaskDue;
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RejectionReason {
        private String reasonId;
        private String reasonTitle;
        private String additionalComments;
    }
}

