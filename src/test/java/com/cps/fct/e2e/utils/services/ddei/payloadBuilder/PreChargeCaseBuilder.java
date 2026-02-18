package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import com.cps.fct.e2e.model.PreChargeDecision;
import com.cps.fct.e2e.utils.common.DateTimeUtils;
import com.cps.fct.e2e.utils.common.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;


public class PreChargeCaseBuilder {

    public static String constructPreChargeTriageFor28DaysMCAccepted(String partyId) throws JsonProcessingException {
       return  PreChargeCaseBuilder.constructPreChargeTriagePayloadBuilder(
               "Accepted", "MC", "28Day", partyId);
    }

    public static String constructPreChargeTriageFor5DaysMCAccepted(String partyId) throws JsonProcessingException {
        return  PreChargeCaseBuilder.constructPreChargeTriagePayloadBuilder(
                "Accepted", "MC", "5Day", partyId);
    }

    private static String constructPreChargeTriagePayloadBuilder(
            String decision, String caseType, String decisionToBeMade, String partyId) throws JsonProcessingException {

        PreChargeDecision preChargeDecisionPayload = PreChargeDecision.builder()
                .decision(decision)
                .caseType(caseType)
                .decisionToBeMade(decisionToBeMade)
                .acceptedDecision(PreChargeDecision.AcceptedDecision.builder()
                        .caseRecieved(DateTimeUtils.UTCDateTimeNow())
                        .partyId(Integer.valueOf(partyId))
                        .build())
                .rejectedDecision(PreChargeDecision.RejectedDecision.builder()
                        .actionPlanDue(DateTimeUtils.UTCDateTimeInFutureDayBy(28)) // 28 days later
                        .chaseTaskDue(DateTimeUtils.UTCDateTimeInFutureDayBy(28))
                        .build())
                .build();

        return JsonUtils.toJson(preChargeDecisionPayload);
    }
}


