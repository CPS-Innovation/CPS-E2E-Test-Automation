package com.cps.fct.e2e.enums;
import lombok.Getter;

@Getter
public enum CategoryType {
    POLICE("P,V"),
    CHILD("C,V"),
    PROFESSIONAL("F,V"),
    EXPERT("X,V"),
    VULNERABLE("L,V"),
    INTIMIDATED("T,V"),
    PRISONER("H,V"),
    INTERPRETER("I,V");

    private final String value;

    CategoryType(String value) {
        this.value = value;
    }


    public static CategoryType fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "serving police officer" -> POLICE;
            case "child under 18 years" -> CHILD;
            case "professional" -> PROFESSIONAL;
            case "expert witness" -> EXPERT;
            case "vulnerable" -> VULNERABLE;
            case "intimidated" -> INTIMIDATED;
            case "serving prisoner or on remand" -> PRISONER;
            case "interpreter instructed by prosecution" -> INTERPRETER;
            default -> throw new IllegalArgumentException("Unknown Victim Category type: " + value);
        };
    }

}

