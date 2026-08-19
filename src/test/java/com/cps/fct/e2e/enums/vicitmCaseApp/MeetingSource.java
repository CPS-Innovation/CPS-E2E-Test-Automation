package com.cps.fct.e2e.enums.vicitmCaseApp;

import lombok.Getter;

@Getter
public enum MeetingSource {
    CPS(1),
    VICTIM(2),
    THIRD_PARTY(3);

    private final int value;

    MeetingSource(int value) {
        this.value = value;
    }

    public static MeetingSource fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "victim requested" -> VICTIM ;
            case "cps offered" -> CPS;
            case "requested by a third party" -> THIRD_PARTY ;
            default -> throw new IllegalArgumentException("Unknown Meeting source: " + value);
        };
    }

}