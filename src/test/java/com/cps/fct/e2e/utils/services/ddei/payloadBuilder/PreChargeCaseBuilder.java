package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import com.cps.fct.e2e.model.PreChargeDecision;
import com.cps.fct.e2e.model.PreChargeTriageDecision;
import com.cps.fct.e2e.utils.common.DateTimeUtils;
import com.cps.fct.e2e.utils.common.JsonUtils;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
//import io.cucumber.messages.ndjson.internal.com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.JsonProcessingException;


public class PreChargeCaseBuilder {

    private static final String ACCEPTED_DECISION = "Accepted";
    private static final String PRIORITY_DECISION_TO_BE_MADE = "Priority";
    private static final int ACTION_PLAN_DUE_DAYS = 28;

    public static String constructPreChargeTriageAccepted(
            String caseType, String decisionToBeMade, String partyId) throws JsonProcessingException {
        return constructPreChargeTriagePayloadBuilder(
                ACCEPTED_DECISION, caseType, decisionToBeMade, partyId, datedRejectedDecision());
    }

    // RED (Priority) triage. Priority cases are accepted rather than rejected, so rejectedDecision
    // stays null/null. The decision is validated/normalised through PreChargeTriageDecision.
    public static String constructPreChargeTriagePriority(
            String caseType, String decision, String partyId) throws JsonProcessingException {
        String wireDecision = PreChargeTriageDecision.from(decision).wireValue();
        return constructPreChargeTriagePayloadBuilder(
                wireDecision, caseType, PRIORITY_DECISION_TO_BE_MADE, partyId, emptyRejectedDecision());
    }

    private static String constructPreChargeTriagePayloadBuilder(
            String decision,
            String caseType,
            String decisionToBeMade,
            String partyId,
            PreChargeDecision.RejectedDecision rejectedDecision) throws JsonProcessingException {

        PreChargeDecision preChargeDecisionPayload = PreChargeDecision.builder()
                .decision(decision)
                .caseType(caseType)
                .decisionToBeMade(decisionToBeMade)
                .acceptedDecision(PreChargeDecision.AcceptedDecision.builder()
                        .caseRecieved(DateTimeUtils.UTCDateTimeNow())
                        .partyId(Integer.valueOf(partyId))
                        .build())
                .rejectedDecision(rejectedDecision)
                .build();

        return JsonUtils.toJson(preChargeDecisionPayload);
    }

    private static PreChargeDecision.RejectedDecision datedRejectedDecision() {
        return PreChargeDecision.RejectedDecision.builder()
                .actionPlanDue(DateTimeUtils.UTCDateTimeInFutureDayBy(ACTION_PLAN_DUE_DAYS)) // 28 days later
                .chaseTaskDue(DateTimeUtils.UTCDateTimeInFutureDayBy(ACTION_PLAN_DUE_DAYS))
                .build();
    }

    private static PreChargeDecision.RejectedDecision emptyRejectedDecision() {
        return PreChargeDecision.RejectedDecision.builder()
                .actionPlanDue(null)
                .chaseTaskDue(null)
                .build();
    }
}


