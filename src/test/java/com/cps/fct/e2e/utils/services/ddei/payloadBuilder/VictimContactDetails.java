package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import com.cps.fct.e2e.enums.PreferredMethodOfContact;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VictimContactDetails {
    private String ContactFullName;
    private String ContactTelephone;
    private String ContactEmail;
    private String ContactType;
    private String AddressAddressLine1;
    private String AddressAddressLine2;
    private String AddressAddressLine3;
    private String AddressAddressLine4;
    private String AddressAddressLine5;
    private String AddressPostcode;
    private String AddressCity;
    private String AddressCounty;
    private String CreatedBy;

}


