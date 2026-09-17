package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import com.cps.fct.e2e.model.caseReviewApp.PreChargeDecision;
import com.cps.fct.e2e.enums.caseReviewApp.PreChargeTriageDecision;
import com.cps.fct.e2e.utils.common.DateTimeUtils;
import com.cps.fct.e2e.utils.common.JsonUtils;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
//import io.cucumber.messages.ndjson.internal.com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;


public class PreChargeCaseBuilder {

    private static final String ACCEPTED_DECISION = "Accepted";
    private static final String PRIORITY_DECISION_TO_BE_MADE = "Priority";
    private static final int ACTION_PLAN_DUE_DAYS = 28;
    private static final int NFS_NON_COMPLIANT_ACTION_PLAN_DUE_MONTHS = 2;

    public static String constructPreChargeTriageAccepted(
            String caseType, String decisionToBeMade, String partyId) throws JsonProcessingException {
        return constructPreChargeTriagePayloadBuilder(
                ACCEPTED_DECISION,
                caseType,
                decisionToBeMade,
                acceptedDecision(partyId),
                datedRejectedDecision());
    }

    // RED (Priority) triage. NFS-compliant decisions are accepted; non-compliant decisions are rejected.
    public static String constructPreChargeTriagePriority(
            String caseType, String decision, String partyId) throws JsonProcessingException {
        PreChargeTriageDecision triageDecision = PreChargeTriageDecision.from(decision);

        if (triageDecision == PreChargeTriageDecision.NFS_NON_COMPLIANT) {
            return constructPreChargeTriagePayloadBuilder(
                    triageDecision.wireValue(),
                    caseType,
                    PRIORITY_DECISION_TO_BE_MADE,
                    null,
                    nfsNonCompliantRejectedDecision());
        }

        return constructPreChargeTriagePayloadBuilder(
                triageDecision.wireValue(),
                caseType,
                PRIORITY_DECISION_TO_BE_MADE,
                acceptedDecision(partyId),
                null);
    }

    private static String constructPreChargeTriagePayloadBuilder(
            String decision,
            String caseType,
            String decisionToBeMade,
            PreChargeDecision.AcceptedDecision acceptedDecision,
            PreChargeDecision.RejectedDecision rejectedDecision) throws JsonProcessingException {

        PreChargeDecision preChargeDecisionPayload = PreChargeDecision.builder()
                .decision(decision)
                .caseType(caseType)
                .decisionToBeMade(decisionToBeMade)
                .acceptedDecision(acceptedDecision)
                .rejectedDecision(rejectedDecision)
                .build();

        return JsonUtils.toJson(preChargeDecisionPayload);
    }

    private static PreChargeDecision.AcceptedDecision acceptedDecision(String partyId) {
        return PreChargeDecision.AcceptedDecision.builder()
                .caseRecieved(DateTimeUtils.UTCDateTimeNow())
                .partyId(Integer.valueOf(partyId))
                .build();
    }

    private static PreChargeDecision.RejectedDecision datedRejectedDecision() {
        return PreChargeDecision.RejectedDecision.builder()
                .actionPlanDue(DateTimeUtils.UTCDateTimeInFutureDayBy(ACTION_PLAN_DUE_DAYS)) // 28 days later
                .chaseTaskDue(DateTimeUtils.UTCDateTimeInFutureDayBy(ACTION_PLAN_DUE_DAYS))
                .build();
    }

    private static PreChargeDecision.RejectedDecision nfsNonCompliantRejectedDecision() {
        return PreChargeDecision.RejectedDecision.builder()
                .rejectionReasons(List.of(
                        rejectionReason("TRR13", "No/missing Disclosure Schedule", "test missing disclosure"),
                        rejectionReason("TRR02", "No MG3", "test mg3 "),
                        rejectionReason("TRR04", "No DV Checklist", "test dv check")))
                .actionPlanDue(DateTimeUtils.UTCDateTimeInFutureMonthBy(NFS_NON_COMPLIANT_ACTION_PLAN_DUE_MONTHS))
                .build();
    }

    private static PreChargeDecision.RejectionReason rejectionReason(
            String reasonId, String reasonTitle, String additionalComments) {
        return PreChargeDecision.RejectionReason.builder()
                .reasonId(reasonId)
                .reasonTitle(reasonTitle)
                .additionalComments(additionalComments)
                .build();
    }
}


