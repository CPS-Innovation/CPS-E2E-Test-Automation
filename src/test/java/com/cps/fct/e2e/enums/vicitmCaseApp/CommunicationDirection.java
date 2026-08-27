package com.cps.fct.e2e.enums.vicitmCaseApp;

import lombok.Getter;

@Getter
public enum CommunicationDirection {
    IN(2),
    OUT(1);

    private final int value;

    CommunicationDirection(int value) {
        this.value = value;
    }

    public static CommunicationDirection fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "inbound" -> IN;
            case "outbound" -> OUT;
            default -> throw new IllegalArgumentException("Unknown CommunicationDirection: " + value);
        };
    }


}
