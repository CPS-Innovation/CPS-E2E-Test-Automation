package com.cps.fct.e2e.enums.caseReviewApp;

/**
 * The triage decision outcome recorded against a pre-charge case (the {@code decision} field of the
 * pre-charge decision payload). This is a separate axis from {@code decisionToBeMade} (the
 * triage route, e.g. Priority / 5Day / 28Day).
 */
public enum PreChargeTriageDecision {

    NFS_COMPLIANT("NFS Compliant"),
    NFS_NON_COMPLIANT("NFS Non-Compliant", "NFS Non Compliant", "Non-NFS Compliant", "Non NFS Compliant");

    private final String wireValue;
    private final String[] aliases;

    PreChargeTriageDecision(String wireValue, String... aliases) {
        this.wireValue = wireValue;
        this.aliases = aliases;
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
            for (String alias : decision.aliases) {
                if (alias.equalsIgnoreCase(normalized)) {
                    return decision;
                }
            }
        }
        throw new IllegalArgumentException("Unsupported pre-charge triage decision: '" + value
                + "'. Supported values: 'NFS Compliant', 'NFS Non-Compliant'.");
    }
}
