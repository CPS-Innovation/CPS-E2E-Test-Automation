package com.cps.fct.e2e.enums.vicitmCaseApp;

import lombok.Getter;

@Getter
public enum TaskOrder {
    IC(0),
    LOM(1),
    LOR(2),
    LAM(3),
    LMO(4),
    LC(1),
    LTX(2),
    LAC(3),
    LDP(4),
    LE(5),
    LL(6);

    private final int value;

    TaskOrder(int value) {
        this.value = value;
    }

    public static TaskOrder fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "is completed" -> IC;
            case "log offered meeting" -> LOM;
            case "log offer response" -> LOR;
            case "log arranged meeting" -> LAM;
            case "log meeting outcome" -> LMO;
            case "log communication" -> LC;
            case "log text message" -> LTX;
            case "log another communication" -> LAC;
            case "letter dispatch method" -> LDP;
            case "log email" -> LE;
            case "log letter" -> LL;


            default -> throw new IllegalArgumentException("Unknown TaskList: " + value);
        };
    }


}
