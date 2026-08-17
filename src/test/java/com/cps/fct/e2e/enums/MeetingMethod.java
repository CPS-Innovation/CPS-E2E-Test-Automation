package com.cps.fct.e2e.enums;
import lombok.Getter;

@Getter
public enum MeetingMethod {
    POST(1),
    EMAIL(2),
    POLICE(3),
    ISVA(4),
    PHONE(5);

    private final int value;

    MeetingMethod(int value) {
        this.value = value;
    }

    public static MeetingMethod fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "letter by post" -> POST;
            case "letter by email" -> EMAIL;
            case "letter by police" -> POLICE;
            case "letter by isva" -> ISVA;
            case "by telephone" -> PHONE;
             default -> throw new IllegalArgumentException( "Unknown Meeting source: " + value );
        };
    }


}
