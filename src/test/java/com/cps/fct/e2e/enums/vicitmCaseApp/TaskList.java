package com.cps.fct.e2e.enums.vicitmCaseApp;

import lombok.Getter;

@Getter
public enum TaskList {
    ADHOC(1),
    PTM(2),
    DC(3),
    NFA(4);

    private final int value;

    TaskList(int value) {
        this.value = value;
    }

    public static TaskList fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "adhoc" -> ADHOC;
            case "pre trial meeting" -> PTM;
            case "decision to charge", "inform of a decision to charge" -> DC;
            case "no further action", "inform of a no further action decision" -> NFA;
            default -> throw new IllegalArgumentException("Unknown Task Type: " + value);
        };
    }


}
