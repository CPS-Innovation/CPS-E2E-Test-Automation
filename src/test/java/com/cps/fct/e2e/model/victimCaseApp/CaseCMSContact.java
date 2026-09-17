package com.cps.fct.e2e.model.victimCaseApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CaseCMSContact {

    private String contactType;
    private String email;
    private String name;
    private String phone;

}
