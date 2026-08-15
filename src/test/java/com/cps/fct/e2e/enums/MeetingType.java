package com.cps.fct.e2e.enums;
import lombok.Getter;

@Getter
public enum MeetingType {
    CPS_PTM(1),
    CD(2),
    VRR(3),
    VCL(4),
    COMPLAINTS(5),
    OTHERS(99);

    private final int value;

    MeetingType(int value) {
        this.value = value;
    }


    public static MeetingType fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "cps pre-trial meeting" -> CPS_PTM;
            case "inform victim about charging decision" -> CD;
            case "stopped or substantially altered charge" -> VCL;
            case "victims right to review" -> VRR;
            case "victim complaint" -> COMPLAINTS;
            case "other cps meeting" -> OTHERS;
            default -> throw new IllegalArgumentException( "Unknown Meeting type: " + value );
        };
    }






}
