package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Address {
    private String AddressLine1;
    private String AddressLine2;
    private String AddressLine3;
    private String AddressLine4;
    private String AddressLine5;
    private String Postcode;
    private String City;
    private String Country;
}
