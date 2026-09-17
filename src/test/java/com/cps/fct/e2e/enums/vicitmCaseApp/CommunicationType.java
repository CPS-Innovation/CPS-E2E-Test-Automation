package com.cps.fct.e2e.enums.vicitmCaseApp;

import lombok.Getter;

@Getter
public enum CommunicationType {
    TELEPHONE(1),
    SMS(2),
    EMAIL(3),
    POST(4),
    INPERSON(5),
    OTHER(6);

    private final int value;

    CommunicationType(int value) {
        this.value = value;
    }


    public static CommunicationType fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "telephone call" -> TELEPHONE;
            case "email" -> SMS;
            case "post" -> EMAIL;
            case "text message" -> POST;
            case "in person" -> INPERSON;
            case "other" -> OTHER;
            default -> throw new IllegalArgumentException("Unknown CommunicationType: " + value);
        };
    }


}
