package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import com.cps.fct.e2e.enums.vicitmCaseApp.PreferredMethodOfContact;
import com.cps.fct.e2e.model.victimCaseApp.*;
import com.cps.fct.e2e.utils.common.FakerUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.FakerUtils.*;
import static com.cps.fct.e2e.utils.common.FakerUtils.streetAddress;

public class VictimCaseAppPayloadBuilder {

    public static String convertObjectToString(Object object) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PreferredMethodOfContact.class, new PreferredMethodOfContactAdapter())
                .setPrettyPrinting()
                .create();
        return gson.toJson(object);
    }



    public static CaseInfo onboardVictim(String caseUrn) {
        return CaseInfo.builder()
                .Urn(caseUrn)
                .CreatedBy("Automation User")
                .build();
    }

    public static VictimVcaDetails addVictimServiceLead(Integer serviceTypeCode) {
        return VictimVcaDetails.builder()
                .PreferredName("")
                .IsYouth(Boolean.FALSE)
                .preferredMethodOfContact(PreferredMethodOfContact.Default)
                .SuitableContactTimes("")
                .SpecialConsiderationNeeds("")
                .Service(serviceTypeCode)
                .LastModifiedBy("Onboard Victim User")
                .build();
    }

    public static VictimLiaisonOfficer assignVictimLiaisonOfficer(Integer userPartyId) {
        return VictimLiaisonOfficer.builder()
                .VLOPartyId(userPartyId)
                .LastModifiedBy("Assign VLO")
                .build();
    }

    public static VictimCmsDetails addVictimPersonalDetailsToCMS() {
        return VictimCmsDetails.builder()
//                .title(FakerUtils.title())
                .title("Mr")
                .firstName(FakerUtils.firstName())
                .surname(FakerUtils.lastName())
                .dateOfBirth(FakerUtils.dateOfBirthAdult().toString())
                .gender(FakerUtils.gender())
                .contactDetailsEmail(email())
                .contactDetailsMobileNumber(mobilePhone())
                .contactDetailsPhoneNumber(homePhone())
                .contactDetailsWorkPhoneNumber(homePhone())
                .contactDetailsPostalAddressAddressLine1(buildingNumber())
                .contactDetailsPostalAddressAddressLine2(streetName())
                .contactDetailsPostalAddressAddressLine3(StringUtils.EMPTY)
                .contactDetailsPostalAddressAddressLine4(StringUtils.EMPTY)
                .contactDetailsPostalAddressAddressLine5(cityName())
                .contactDetailsPostalAddressPostcode(ukPostCode())
                .justification("JUSTIFICATION TEXT")
                .build();
    }

    public static String payLoadAddVictimPersonalDetailsToCMS(VictimCmsDetails victimDetails) {
        AddUpdateVictimDetails builder = new AddUpdateVictimDetails();
        List<Map<String, Object>> patchPayload = builder
                .add("/contactDetails/title", victimDetails.getTitle())
                .add("/contactDetails/gender", victimDetails.getGender())
                .add("/dateOfBirth", victimDetails.getDateOfBirth())
//                .add("/contactDetails/ethnicity", victimDetails.getEthnicity())
//                .add("/contactDetails/disability", victimDetails.getDisability())
//                .add("/previousConvictions", victimDetails.getPreviousConvictions())
                .add("/contactDetails/phoneNumber", victimDetails.getContactDetailsPhoneNumber())
                .add("/contactDetails/mobileNumber", victimDetails.getContactDetailsMobileNumber())
                .add("/contactDetails/workPhoneNumber", victimDetails.getContactDetailsWorkPhoneNumber())
                .add("/contactDetails/email", victimDetails.getContactDetailsEmail())
//                .add("/contactDetails/postalAddress/addressLine1", victimDetails.getContactDetailsPostalAddressAddressLine1())
//                .add("/contactDetails/postalAddress/addressLine2", victimDetails.getContactDetailsPostalAddressAddressLine2())
//                .add("/contactDetails/postalAddress/addressLine3", victimDetails.getContactDetailsPostalAddressAddressLine3())
//                .add("/contactDetails/postalAddress/addressLine4", victimDetails.getContactDetailsPostalAddressAddressLine4())
//                .add("/contactDetails/postalAddress/addressLine5", victimDetails.getContactDetailsPostalAddressAddressLine5())
//                .add("/contactDetails/postalAddress/postcode", victimDetails.getContactDetailsPostalAddressPostcode())
                .add("/justification", victimDetails.getJustification())
                .build();

        //TODO : existing defect on address line
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(patchPayload);
    }

    public static VictimVcaDetails addVictimPersonalDetailsToVCA() {
        return VictimVcaDetails.builder()
                .PreferredName("Add E2E Automation")
                .preferredMethodOfContact(PreferredMethodOfContact.EMAIL)
                .IsYouth(Boolean.FALSE)
                .SuitableContactTimes("anytime between 9am to 6pm weekdays")
                .SpecialConsiderationNeeds("")
                .LastModifiedBy("Add Victim Details To VCA")
                .build();
    }

    public static VictimCmsDetails updateVictimPersonalDetailsToCMS() {
        return VictimCmsDetails.builder()
                .title("Mrs")
                .firstName(FakerUtils.firstName())
                .surname(FakerUtils.lastName())
                .dateOfBirth(FakerUtils.dateOfBirthAdult().toString())
                .gender(FakerUtils.gender())
                .contactDetailsEmail(email())
                .contactDetailsMobileNumber(mobilePhone())
                .contactDetailsPhoneNumber(homePhone())
                .contactDetailsWorkPhoneNumber(homePhone())
                .contactDetailsPostalAddressAddressLine1(streetName())
                .contactDetailsPostalAddressAddressLine2(streetName())
                .contactDetailsPostalAddressAddressLine3(streetAddress())
                .contactDetailsPostalAddressAddressLine4(streetAddress())
                .contactDetailsPostalAddressAddressLine5(cityName())
                .contactDetailsPostalAddressPostcode(ukPostCode())
                .justification("UPDATE DETAILS TEXT")
                .build();
    }

    public static String payLoadUpdateVictimPersonalDetailsToCMS(VictimCmsDetails victimDetails) {
        AddUpdateVictimDetails builder = new AddUpdateVictimDetails();
        List<Map<String, Object>> patchPayload = builder
                .replace("/contactDetails/title", victimDetails.getTitle())
                .replace("/contactDetails/gender", victimDetails.getGender())
                .replace("/dateOfBirth", victimDetails.getDateOfBirth())
                .replace("/contactDetails/phoneNumber", victimDetails.getContactDetailsPhoneNumber())
                .replace("/contactDetails/mobileNumber", victimDetails.getContactDetailsMobileNumber())
                .replace("/contactDetails/workPhoneNumber", victimDetails.getContactDetailsWorkPhoneNumber())
                .replace("/contactDetails/postalAddress/addressLine1", victimDetails.getContactDetailsPostalAddressAddressLine1())
                .replace("/contactDetails/postalAddress/addressLine2", victimDetails.getContactDetailsPostalAddressAddressLine2())
                .replace("/contactDetails/postalAddress/addressLine3", victimDetails.getContactDetailsPostalAddressAddressLine3())
                .replace("/contactDetails/postalAddress/addressLine4", victimDetails.getContactDetailsPostalAddressAddressLine4())
                .replace("/contactDetails/postalAddress/addressLine5", victimDetails.getContactDetailsPostalAddressAddressLine5())
                .replace("/contactDetails/postalAddress/postcode", victimDetails.getContactDetailsPostalAddressPostcode())
                .replace("/contactDetails/email", victimDetails.getContactDetailsEmail())
                .add("/justification", victimDetails.getJustification())
                .build();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(patchPayload);
    }

    public static VictimVcaDetails updateVictimPersonalDetailsInVca() {
        return VictimVcaDetails.builder()
                .PreferredName("Update E2E Automation")
                .preferredMethodOfContact(PreferredMethodOfContact.HOME_PHONE)
                .IsYouth(Boolean.TRUE)
                .SuitableContactTimes("Updated New times only")
                .SpecialConsiderationNeeds("Not Needed anymore")
                .LastModifiedBy("Update Personal Details in VCA")
                .build();
    }

    public static CpsContacts addCpsContact(int contactTypeCode) {
        if (contactTypeCode == 2) {
            return CpsContacts.builder()
                    .ContactName(FakerUtils.fullName())
                    .ContactTelephone(FakerUtils.homePhone())
                    .ContactEmail(FakerUtils.email())
                    .ContactType(contactTypeCode)
                    .CreatedBy("Add CPS Contact")
                    .Address(Address.builder().build())
                    .build();
        } else {
            return CpsContacts.builder()
                    .ContactName(FakerUtils.fullName())
                    .ContactTelephone(FakerUtils.homePhone())
                    .ContactEmail(FakerUtils.email())
                    .ContactType(contactTypeCode)
                    .CreatedBy("Add CPS Contact")
                    .Address(Address.builder()
                            .AddressLine1(FakerUtils.buildingNumber())
                            .AddressLine2(FakerUtils.streetName())
                            .AddressLine3(FakerUtils.streetName())
                            .AddressLine4(FakerUtils.streetName())
                            .AddressLine5(FakerUtils.streetAddress())
                            .Postcode(FakerUtils.ukPostCode())
                            .City(FakerUtils.cityName())
                            .Country(FakerUtils.countyName())
                            .build())
                    .build();
        }
    }

    public static CpsContacts updateCpsContact(int contactTypeCode) {
        if (contactTypeCode == 2) {
            return CpsContacts.builder()
                    .ContactName(FakerUtils.fullName())
                    .ContactTelephone(FakerUtils.homePhone())
                    .ContactEmail(FakerUtils.email())
                    .ContactType(contactTypeCode)
                    .LastModifiedBy("Update CPS Contact")
                    .Address(Address.builder().build())
                    .build();
        } else {
            return CpsContacts.builder()
                    .ContactName(FakerUtils.fullName())
                    .ContactTelephone(FakerUtils.homePhone())
                    .ContactEmail(FakerUtils.email())
                    .ContactType(contactTypeCode)
                    .LastModifiedBy("Update CPS Contact")
                    .Address(Address.builder()
                            .AddressLine1(FakerUtils.buildingNumber())
                            .AddressLine2(FakerUtils.streetName())
                            .AddressLine3(FakerUtils.streetName())
                            .AddressLine4(FakerUtils.streetName())
                            .AddressLine5(FakerUtils.streetAddress())
                            .Postcode(FakerUtils.ukPostCode())
                            .City(FakerUtils.cityName())
                            .Country(FakerUtils.countyName())
                            .build())
                    .build();
        }
    }

    public static VictimCmsDetails addCategoryToVictimInVca(String categoryCode) {
        return VictimCmsDetails.builder()
                .category(categoryCode)
                .justification("Added Category")
                .build();
    }

    public static String payLoadForAddOrUpdateCategory(VictimCmsDetails victimDetails) {
        AddUpdateVictimDetails builder = new AddUpdateVictimDetails();
        List<Map<String, Object>> patchPayload = builder
                .replace("/types", victimDetails.getCategory())
                .add("/justification", victimDetails.getJustification())
                .build();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(patchPayload);
    }

    public static Meetings meetingNotOffered(int meetingTypeCode, String reason) {
        return Meetings.builder()
                .MeetingType(meetingTypeCode)
                .MeetingContextGuid(FakerUtils.uuid())
                .MeetingOffered(false)
                .MeetingRequested(false)
                .MethodOfOffer(10)
                .DateOfOffer(defaultDate())
                .ReasonForNoOffer(reason)
                .CreatedBy("meetingNotOffered")
                .build();
    }

    public static Meetings meetingOfferMethod(int meetingTypeCode, int meetingMethod) {
        return Meetings.builder()
                .MeetingType(meetingTypeCode)
                .MeetingContextGuid(FakerUtils.uuid())
                .MeetingOffered(true)
                .MeetingRequested(false)
                .MethodOfOffer(meetingMethod)
                .DateOfOffer(todayMinusFiveDays())
                .CreatedBy("meetingOfferedUsingMethod")
                .build();
    }

    public static Meetings meetingOfferResponse(int meetingTypeCode, int offerResponseMethod, String meetingContextGuid, String meetingOfferResponse) {
        return Meetings.builder()
                .MeetingType(meetingTypeCode)
                .MeetingOfferAttempt(1)
                .MeetingContextGuid(meetingContextGuid)
                .MeetingOffered(true)
                .MeetingRequested(false)
                .VictimResponse(meetingOfferResponse)
                .MethodOfResponse(offerResponseMethod)
                .VictimResponseDate("No Response".equals(meetingOfferResponse)
                                ? todayDate()
                                : todayMinusFourDays())
                .LastModifiedBy("meetingResponseUser")
                .build();
    }


















}