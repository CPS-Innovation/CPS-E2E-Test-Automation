package com.cps.fct.e2e.enums;

import lombok.Getter;


public enum VictimContacts {
    FLO(2),
    ISVA(3),
    IDVA(4);

    public final int value;

    VictimContacts(int value){
        this.value = value;
    }

    public static VictimContacts fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "family liaison officer" -> FLO;
            case "independent sexual violence adviser" -> ISVA;
            case "independent domestic violence adviser" -> IDVA;
            default -> throw new IllegalArgumentException( "Unknown Victim Contact type: " + value );
        };
    }






}
