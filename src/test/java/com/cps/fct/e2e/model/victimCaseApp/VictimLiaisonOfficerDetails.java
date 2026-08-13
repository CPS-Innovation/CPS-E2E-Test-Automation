package com.cps.fct.e2e.model.victimCaseApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VictimLiaisonOfficerDetails {
    private String LastModifiedBy;
    private int Service;
    private boolean Onboarded;
    private int VLOPartyId;
}


