package com.cps.fct.e2e.enums.caseReviewApp;

/**
 * The triage decision outcome recorded against a pre-charge case (the {@code decision} field of the
 * {@link PreChargeDecision} payload). This is a separate axis from {@code decisionToBeMade} (the
 * triage route, e.g. Priority / 5Day / 28Day).
 */
public enum PreChargeTriageDecision {

    NFS_COMPLIANT("NFS Compliant");
    // TODO: NON_NFS_COMPLIANT("Non-NFS Compliant") — a rejected decision, so its payload must
    //       populate rejectedDecision.actionPlanDue / chaseTaskDue instead of leaving them null.

    private final String wireValue;

    PreChargeTriageDecision(String wireValue) {
        this.wireValue = wireValue;
    }

    public String wireValue() {
        return wireValue;
    }

    public static PreChargeTriageDecision from(String value) {
        String normalized = value == null ? "" : value.trim();
        for (PreChargeTriageDecision decision : values()) {
            if (decision.wireValue.equalsIgnoreCase(normalized)) {
                return decision;
            }
        }
        throw new IllegalArgumentException("Unsupported pre-charge triage decision: '" + value
                + "'. Currently supported: 'NFS Compliant'. ('Non-NFS Compliant' is not yet implemented.)");
    }
}
