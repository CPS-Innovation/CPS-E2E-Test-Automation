package com.cps.fct.e2e.model.victimCaseApp;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class CpsContacts {
    private String ContactName;
    private String ContactTelephone;
    private String ContactEmail;
    private int ContactType;
    private String CreatedBy;
    private String LastModifiedBy;
    private Address Address;
    private String AddressLine1;
    private String AddressLine2;
    private String AddressLine3;
    private String AddressLine4;
    private String AddressLine5;
    private String Postcode;
    private String City;
    private String Country;
}