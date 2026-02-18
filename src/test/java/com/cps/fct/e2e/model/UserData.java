package com.cps.fct.e2e.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ignores any unknown JSON fields
public class UserData {
    private String userSurname;
    private List<Unit> allocatedUnits;

    public String getUnitIdByName(String unitName) {
        if (allocatedUnits==null) return null;
        return allocatedUnits.stream()
                .filter(u -> unitName.equalsIgnoreCase(u.getUnit()))
                .map(u -> String.valueOf(u.getUnitId()))
                .findFirst()
                .orElse(null);
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Unit {
        private int unitId;
        private String unit;
    }

}

