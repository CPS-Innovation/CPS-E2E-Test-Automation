package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Address {
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String postcode;
    private String city;
    private String county;
}
