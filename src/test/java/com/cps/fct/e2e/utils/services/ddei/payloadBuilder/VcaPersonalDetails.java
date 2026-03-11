package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import com.cps.fct.e2e.enums.PreferredMethodOfContact;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VcaPersonalDetails {
    private String PreferredName;
    private boolean IsYouth;
    private PreferredMethodOfContact preferredMethodOfContact;
    private String SuitableContactTimes;
    private String SpecialConsiderationNeeds;
    private String LastModifiedBy;
    private int Service;
    private boolean Onboarded;
}


