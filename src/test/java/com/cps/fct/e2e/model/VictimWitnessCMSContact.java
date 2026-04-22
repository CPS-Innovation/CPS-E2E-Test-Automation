package com.cps.fct.e2e.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VictimWitnessCMSContact {
    private String contactType;
    private String email;
    private String name;
    private String phone;
}
