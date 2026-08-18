package com.cps.fct.e2e.enums.vicitmCaseApp;

import lombok.Getter;

@Getter
public enum OnboardService {
    UNIVERSAL(1),
    ENHANCED(2),
    RASSO(3),
    NO_SERVICE(4);

    private final int value;

    OnboardService(int value) {
        this.value = value;
    }

//    public static VictimOnboardService fromValue(int value) {
//        for (VictimOnboardService method : VictimOnboardService.values()) {
//            if (method.getValue()==value) {
//                return method;
//            }
//        }
//        throw new IllegalArgumentException("Incorrect Victim Meeting type : " + value);
//    }

    public static OnboardService fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "universal" -> UNIVERSAL;
            case "enhanced" -> ENHANCED;
            case "rasso" -> RASSO;
            case "not aligned" -> NO_SERVICE;
            default -> throw new IllegalArgumentException( "Unknown Service type: " + value );
        };
    }

}
