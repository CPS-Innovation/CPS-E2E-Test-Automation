package com.cps.fct.e2e.enums;


public enum VictimContactsType {
    FLO(2),
    ISVA(3),
    IDVA(4);

    public final int value;

    VictimContactsType(int value){
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


    public static VictimContactsType fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "family liaison officer" -> FLO;
            case "independent sexual violence adviser" -> ISVA;
            case "independent domestic violence adviser" -> IDVA;
            default -> throw new IllegalArgumentException( "Unknown Victim Contact type: " + value );
        };
    }






}
