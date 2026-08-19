package com.cps.fct.e2e.enums.vicitmCaseApp;
import lombok.Getter;

@Getter
public enum OfferMethod {
    POST(1),
    EMAIL(2),
    POLICE(3),
    ISVA(4),
    PHONE(5),
    NO_RESPONSE(10);

    private final int value;

    OfferMethod(int value) {
        this.value = value;
    }

    public static OfferMethod fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "letter by post" -> POST;
            case "letter by email" -> EMAIL;
            case "letter by police" -> POLICE;
            case "letter by isva" -> ISVA;
            case "by telephone" -> PHONE;
            case "no response" -> NO_RESPONSE;
             default -> throw new IllegalArgumentException( "Unknown Meeting offer method: " + value );
        };
    }


}
