package com.cps.fct.e2e.enums.vicitmCaseApp;

import lombok.Getter;

@Getter
public enum CpsContactsType {
    FLO(2),
    ISVA(3),
    IDVA(4);

    public final int value;

    CpsContactsType(int value){
        this.value = value;
    }

//    public static VictimContactsType fromValue(int value) {
//        for (VictimContactsType method : VictimContactsType.values()) {
//            if (method.getValue()==value) {
//                return method;
//            }
//        }
//        throw new IllegalArgumentException("Incorrect Victim Meeting type : " + value);
//    }


    public static CpsContactsType fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "family liaison officer" -> FLO;
            case "independent sexual violence adviser" -> ISVA;
            case "independent domestic violence adviser" -> IDVA;
            default -> throw new IllegalArgumentException( "Unknown Victim Contact type: " + value );
        };
    }






}
