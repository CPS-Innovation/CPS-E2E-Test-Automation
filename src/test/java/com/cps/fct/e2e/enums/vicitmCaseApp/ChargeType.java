package com.cps.fct.e2e.enums.vicitmCaseApp;

import lombok.Getter;

@Getter
public enum ChargeType {
    RED(1),
    GREEN(2),
    POLICE(3);

    private final int value;

    ChargeType(int value) {
        this.value = value;
    }

    public static ChargeType fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "red" -> RED;
            case "green" -> GREEN;
            case "police charged" -> POLICE;
            default -> throw new IllegalArgumentException("Unknown ChargeType: " + value);
        };
    }


}
