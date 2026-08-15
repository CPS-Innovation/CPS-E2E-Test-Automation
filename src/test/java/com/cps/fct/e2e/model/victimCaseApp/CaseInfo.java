package com.cps.fct.e2e.model.victimCaseApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CaseInfo {

    private String Urn;
    private int Service;
    private boolean Onboarded;
    private String CreatedBy;


}