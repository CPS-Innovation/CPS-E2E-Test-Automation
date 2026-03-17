package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import com.cps.fct.e2e.enums.PreferredMethodOfContact;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class VictimContactDetails {
    private String ContactName;
    private String ContactTelephone;
    private String ContactEmail;
    private int ContactType;
    private String CreatedBy;
    private Address Address;
}