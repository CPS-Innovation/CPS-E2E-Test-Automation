package com.cps.fct.e2e.enums.vicitmCaseApp;

import lombok.Getter;

@Getter
public enum MeetingContactResearch {
    YES(1),
    NO(2),
    NOT_ASKED(3);

    private final int value;

    MeetingContactResearch(int value) {
        this.value = value;
    }

    public static MeetingContactResearch fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "yes" -> YES ;
            case "no" -> NO;
            case "question not asked" -> NOT_ASKED;
            default -> throw new IllegalArgumentException("Unknown Meeting Contact for Research: " + value);
        };
    }

}