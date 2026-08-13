package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import com.cps.fct.e2e.enums.PreferredMethodOfContact;
import com.cps.fct.e2e.model.victimCaseApp.*;
import com.cps.fct.e2e.utils.common.FakerUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.FakerUtils.*;
import static com.cps.fct.e2e.utils.common.JsonUtils.toJsonString;

public class VictimCaseAppPayloadBuilder {

    public static VictimCaseInfo postCaseVictimGuid(String caseUrn){
        return VictimCaseInfo.builder()
                .Urn(caseUrn)
                .CreatedBy("Automation User")
                .build();
    }

    public static VictimCmsDetails getVictimWitnessDetails() {
        return VictimCmsDetails.builder()
//                .title(FakerUtils.title())
                .title("Mr")
                .firstName(FakerUtils.firstName())
                .surname(FakerUtils.lastName())
                .dateOfBirth(FakerUtils.dateOfBirth().toString())
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

    public static VictimCmsDetails getVictimWitnessCategory(String categoryCode) {
        return VictimCmsDetails.builder()
                .category(categoryCode)
                .justification("JUSTIFICATION TEXT")
                .build();
    }

    public static String convertObjectToString(Object object) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PreferredMethodOfContact.class, new PreferredMethodOfContactAdapter())
                .setPrettyPrinting()
                .create();
        return gson.toJson(object);
    }

    public static VictimVcaDetails addVcaPersonalDetails() {
        return VictimVcaDetails.builder()
                .PreferredName("Add E2E Automation")
                .preferredMethodOfContact(PreferredMethodOfContact.EMAIL)
                .IsYouth(Boolean.FALSE)
                .SuitableContactTimes("anytime between 9am to 6pm weekdays")
                .SpecialConsiderationNeeds("Wheelchair access")
                .Service(1)
                .Onboarded(Boolean.TRUE)
                .LastModifiedBy("Add CPS user")
                .build();
    }

    public static VictimVcaDetails updateVcaPersonalDetails() {
        return VictimVcaDetails.builder()
                .PreferredName("Update E2E Automation")
                .preferredMethodOfContact(PreferredMethodOfContact.HOME_PHONE)
                .IsYouth(Boolean.TRUE)
                .SuitableContactTimes("Evening times only")
                .SpecialConsiderationNeeds("Chair Support")
                .Service(1)
                .Onboarded(Boolean.TRUE)
                .LastModifiedBy("Update CPS user")
                .build();
    }

    public static VictimLiaisonOfficerDetails addVictimLiaisonOfficer(Integer userPartyId) {
        return VictimLiaisonOfficerDetails.builder()
                .Service(1)
                .Onboarded(Boolean.TRUE)
                .VLOPartyId(userPartyId)
                .LastModifiedBy("Test Automation User")
                .build();
    }


    public static VictimContacts payLoadForAddVictimContactDetails(int contactTypeCode) {

        if (contactTypeCode == 2) {
            return VictimContacts.builder()
                    .ContactName(FakerUtils.fullName())
                    .ContactTelephone(FakerUtils.homePhone())
                    .ContactEmail(FakerUtils.email())
                    .ContactType(contactTypeCode)
                    .CreatedBy("E2E TestAutomation")
                    .Address(Address.builder().build())
                    .build();
        } else {
            return VictimContacts.builder()
                    .ContactName(FakerUtils.fullName())
                    .ContactTelephone(FakerUtils.homePhone())
                    .ContactEmail(FakerUtils.email())
                    .ContactType(contactTypeCode)
                    .CreatedBy("E2E TestAutomation")
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

    public static VictimContacts payLoadForUpdateVictimContactDetails(int contactTypeCode) {

        if (contactTypeCode == 2) {
            return VictimContacts.builder()
                    .ContactName("NEWSURENAME Update")
                    .ContactTelephone("07777777777")
                    .ContactEmail("new_email_address@gov.gov")
                    .ContactType(contactTypeCode)
                    .LastModifiedBy("Modified by E2E TestAutomation")
                    .Address(Address.builder().build())
                    .build();
        } else {
            return VictimContacts.builder()
                    .ContactName("NEWSURENAME Update")
                    .ContactTelephone("07777777777")
                    .ContactEmail("new_email_address@gov.gov")
                    .ContactType(contactTypeCode)
                    .LastModifiedBy("Modified by E2E TestAutomation")
                    .Address(Address.builder()
                            .AddressLine1("New Address Line1")
                            .AddressLine2("New Address Line2")
                            .AddressLine3("New Address Line3")
                            .AddressLine4("New Address Line4")
                            .AddressLine5("New Address Line5")
                            .Postcode("NW011NE")
                            .City("New City")
                            .Country("New County")
                            .build())
                    .build();
        }
    }

    // This method used for Onboarding(first time)
    public static String payLoadForAddVictimWitnessToVCA(String caseUrn) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("Urn", caseUrn);
        payload.put("CreatedBy", "Onboard CPS user");
        payload.put("Service", 1);
        payload.put("Onboarded", false);
        return toJsonString(payload);
    }

    public static String payLoadForAddWitnessDetailsWitnessId(VictimCmsDetails victimDetails) {
        UpdateWitnessDetailsWitnessIdJsonBuilder builder = new UpdateWitnessDetailsWitnessIdJsonBuilder();
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

    public static String payLoadForUpdateWitnessDetailsWitnessId(VictimCmsDetails victimDetails) {
        UpdateWitnessDetailsWitnessIdJsonBuilder builder = new UpdateWitnessDetailsWitnessIdJsonBuilder();
        List<Map<String, Object>> patchPayload = builder
                .replace("/contactDetails/title", "Dr")
                .replace("/contactDetails/gender", "Unknown")
                .replace("/dateOfBirth", "1990-02-01")
                .replace("/contactDetails/ethnicity", "British")
                .replace("/contactDetails/disability", "Yes")
                .replace("/previousConvictions", "True")
                .replace("/contactDetails/phoneNumber", victimDetails.getContactDetailsPhoneNumber())
                .replace("/contactDetails/mobileNumber", victimDetails.getContactDetailsMobileNumber())
                .replace("/contactDetails/workPhoneNumber", victimDetails.getContactDetailsWorkPhoneNumber())
                .replace("/contactDetails/email", victimDetails.getContactDetailsEmail())
                .replace("/contactDetails/postalAddress/addressLine1", victimDetails.getContactDetailsPostalAddressAddressLine1())
                .replace("/contactDetails/postalAddress/addressLine2", victimDetails.getContactDetailsPostalAddressAddressLine2())
                .replace("/contactDetails/postalAddress/addressLine3", victimDetails.getContactDetailsPostalAddressAddressLine3())
                .replace("/contactDetails/postalAddress/addressLine4", victimDetails.getContactDetailsPostalAddressAddressLine4())
                .replace("/contactDetails/postalAddress/addressLine5", victimDetails.getContactDetailsPostalAddressAddressLine5())
                .replace("/contactDetails/postalAddress/postcode", victimDetails.getContactDetailsPostalAddressPostcode())
                .add("/justification", victimDetails.getJustification())
                .build();

        //TODO : existing defect on address line
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(patchPayload);
    }

    public static String payLoadForAddOrUpdateCategory(VictimCmsDetails victimDetails) {
        UpdateWitnessDetailsWitnessIdJsonBuilder builder = new UpdateWitnessDetailsWitnessIdJsonBuilder();
        List<Map<String, Object>> patchPayload = builder
                .replace("/types", victimDetails.getCategory())
                .add("/justification", victimDetails.getJustification())
                .build();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(patchPayload);
    }

    public static VictimMeetings payLoadForAddVictimMeetingDetails(int meetingTypeCode, String reason) {

        return VictimMeetings.builder()
                .MeetingType(meetingTypeCode)
                .MeetingContextGuid(FakerUtils.uuid())
                .MeetingOffered(false)
                .MeetingRequested(false)
                .MethodOfOffer(10)
                .DateOfOffer(defaultDate())
                .ReasonForNoOffer(reason)
                .CreatedBy("automationUser")
                .build();
    }

    public static VictimMeetings payLoadForAddVictimMeetingMethodDetails(int meetingTypeCode, int methodTypeCode) {

        return VictimMeetings.builder()
                .MeetingType(meetingTypeCode)
                .MeetingContextGuid(FakerUtils.uuid())
                .MeetingOffered(true)
                .MeetingRequested(false)
                .MethodOfOffer(methodTypeCode)
                .DateOfOffer(todayMinusFiveDays())
                .CreatedBy("automationUser")
                .build();
    }

    public static VictimMeetings payLoadForMeetingStatusDetails(int meetingTypeCode, int methodTypeCode, String meetingStatus) {

        return VictimMeetings.builder()
                .MeetingType(meetingTypeCode)
                .MethodOfOffer(methodTypeCode)
                .DateOfOffer(todayMinusFiveDays())
                .MeetingOfferAttempt(1)
                .MeetingContextGuid(FakerUtils.uuid())
                .MeetingOffered(true)
                .MeetingRequested(false)
                .VictimResponse(meetingStatus)
                .MethodOfResponse(methodTypeCode)
                .VictimResponseDate(todayMinusFourDays())
                .LastModifiedBy("automationUser")
                .build();
    }

    public static VictimMeetings payLoadForNoResponseMeetingDetails(int meetingTypeCode, int methodTypeCode) {

        return VictimMeetings.builder()
                .MeetingType(meetingTypeCode)
                .MethodOfOffer(methodTypeCode)
                .DateOfOffer(todayMinusFiveDays())
                .MeetingOfferAttempt(1)
                .MeetingContextGuid(FakerUtils.uuid())
                .MeetingOffered(true)
                .MeetingRequested(false)
                .VictimResponse("No Response")
                .MethodOfResponse(10)
                .VictimResponseDate(todayMinusFourDays())
                .LastModifiedBy("automationUser")
                .build();
    }





}