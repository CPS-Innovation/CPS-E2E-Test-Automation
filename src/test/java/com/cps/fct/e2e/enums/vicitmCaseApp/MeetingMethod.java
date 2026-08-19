package com.cps.fct.e2e.enums.vicitmCaseApp;

import lombok.Getter;

@Getter
public enum MeetingMethod {
    IN_PERSON(1),
    VIRTUAL(2),
    HYBRID(3);

    private final int value;

    MeetingMethod(int value) {
        this.value = value;
    }

    public static MeetingMethod fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "in person" ->IN_PERSON ;
            case "virtual call" ->VIRTUAL ;
            case "hybrid" -> HYBRID;
            default -> throw new IllegalArgumentException("Unknown Meeting method: " + value);
        };
    }

}