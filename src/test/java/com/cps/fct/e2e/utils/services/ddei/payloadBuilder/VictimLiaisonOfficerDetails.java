package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import com.cps.fct.e2e.enums.PreferredMethodOfContact;
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


