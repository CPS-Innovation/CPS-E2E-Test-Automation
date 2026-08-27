package com.cps.fct.e2e.enums.vicitmCaseApp;

import lombok.Getter;

@Getter
public enum JourneyType {
    DC(1),
    NFA(2),
    VCL(3),
    NIVCL(4),
    NIDC(5),
    NINFA(6),
    OTHER(7);

    private final int value;

    JourneyType(int value) {
        this.value = value;
    }

    public static JourneyType fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "decision to charge" -> DC;
            case "no further action" -> NFA;
            case "vcl" -> VCL;
            case "non integrated vcl" -> NIVCL;
            case "non integrated decision to charge" -> NIDC;
            case "non integrated no further action" -> NINFA;
            case "other" -> OTHER;
            default -> throw new IllegalArgumentException("Unknown CommunicationType: " + value);
        };
    }


}
