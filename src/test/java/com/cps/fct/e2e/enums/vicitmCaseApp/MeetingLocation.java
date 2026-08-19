package com.cps.fct.e2e.enums.vicitmCaseApp;

import lombok.Getter;

@Getter
public enum MeetingLocation {
    CPS(1),
    MC(2),
    CC(3),
    PS(4),
    VIRTUAL(5),
    OTHER(3);

    private final int value;

    MeetingLocation(int value) {
        this.value = value;
    }

    public static MeetingLocation fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "cps location " -> CPS ;
            case "magistrates court" -> MC;
            case "crown court" -> CC ;
            case "police station" -> PS;
            case "virtual" -> VIRTUAL ;
            case "other" -> OTHER ;
            default -> throw new IllegalArgumentException("Unknown Meeting location: " + value);
        };
    }

}