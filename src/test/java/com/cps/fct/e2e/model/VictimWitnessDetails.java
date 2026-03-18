package com.cps.fct.e2e.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VictimWitnessDetails {
    private String title;
    private String firstName;
    private String surname;
    private String gender;
    private String dateOfBirth;
    private String contactDetailsTitle;
    private String contactDetailsGender;
    private String contactDetailsPhoneNumber;
    private String contactDetailsMobileNumber;
    private String contactDetailsWorkPhoneNumber;
    private String contactDetailsEmail;
    private String contactDetailsPostalAddressAddressLine1;
    private String contactDetailsPostalAddressAddressLine2;
    private String contactDetailsPostalAddressAddressLine3;
    private String contactDetailsPostalAddressAddressLine4;
    private String contactDetailsPostalAddressAddressLine5;
    private String contactDetailsPostalAddressPostcode;
    private String contactDetailsPreferredCorrespondenceLanguage;
    private String contactDetailsDisability;
    private String contactDetailsEthnicity;
    private String previousConvictions;
    private String justification;

}

