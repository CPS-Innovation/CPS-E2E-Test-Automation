package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import com.cps.fct.e2e.enums.vicitmCaseApp.*;
import com.cps.fct.e2e.model.victimCaseApp.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.DateTimeUtils.*;
import static com.cps.fct.e2e.utils.common.FakerUtils.*;

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

    public static VictimLiaisonOfficer assignVictimLiaisonOfficer(int userPartyId) {
        return VictimLiaisonOfficer.builder()
                .VLOPartyId(userPartyId)
                .LastModifiedBy("Assign VLO")
                .build();
    }

    public static VictimCmsDetails addVictimPersonalDetailsToCMS() {
        return VictimCmsDetails.builder()
//                .title(FakerUtils.title())
                .title("Mr")
                .firstName(firstName())
                .surname(lastName())
                .dateOfBirth(dateOfBirthAdult().toString())
                .gender(gender())
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
        AddUpdateContactDetails builder = new AddUpdateContactDetails();
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
                .firstName(firstName())
                .surname(lastName())
                .dateOfBirth(dateOfBirthAdult().toString())
                .gender(gender())
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
        AddUpdateContactDetails builder = new AddUpdateContactDetails();
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
                    .ContactName(fullName())
                    .ContactTelephone(homePhone())
                    .ContactEmail(email())
                    .ContactType(contactTypeCode)
                    .CreatedBy("Add CPS Contact")
                    .Address(Address.builder().build())
                    .build();
        } else {
            return CpsContacts.builder()
                    .ContactName(fullName())
                    .ContactTelephone(homePhone())
                    .ContactEmail(email())
                    .ContactType(contactTypeCode)
                    .CreatedBy("Add CPS Contact")
                    .Address(Address.builder()
                            .AddressLine1(buildingNumber())
                            .AddressLine2(streetName())
                            .AddressLine3(streetName())
                            .AddressLine4(streetName())
                            .AddressLine5(streetAddress())
                            .Postcode(ukPostCode())
                            .City(cityName())
                            .Country(countyName())
                            .build())
                    .build();
        }
    }

    public static CpsContacts updateCpsContact(int contactTypeCode) {
        if (contactTypeCode == 2) {
            return CpsContacts.builder()
                    .ContactName(fullName())
                    .ContactTelephone(homePhone())
                    .ContactEmail(email())
                    .ContactType(contactTypeCode)
                    .LastModifiedBy("Update CPS Contact")
                    .Address(Address.builder().build())
                    .build();
        } else {
            return CpsContacts.builder()
                    .ContactName(fullName())
                    .ContactTelephone(homePhone())
                    .ContactEmail(email())
                    .ContactType(contactTypeCode)
                    .LastModifiedBy("Update CPS Contact")
                    .Address(Address.builder()
                            .AddressLine1(buildingNumber())
                            .AddressLine2(streetName())
                            .AddressLine3(streetName())
                            .AddressLine4(streetName())
                            .AddressLine5(streetAddress())
                            .Postcode(ukPostCode())
                            .City(cityName())
                            .Country(countyName())
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
        AddUpdateContactDetails builder = new AddUpdateContactDetails();
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
                .MeetingContextGuid(uuid())
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
                .MeetingContextGuid(uuid())
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

    public static Meetings meetingArrange(int meetingType, String meetingContextGuid, int meetingSource,
                                          int meetingMethod, int locationType, String LocationName) {
        return Meetings.builder()
                .MeetingType(meetingType)
                .MeetingContextGuid(meetingContextGuid)
                .MeetingMethod(meetingMethod)
                .MeetingDateTime(UTCDateTimeInPastBy(3))
                .MeetingSource(meetingSource)
                .LocationType(locationType)
                .LocationName(LocationName)
                .SpecialNeeds(false)
                .NatureOfNeeds("Test")
                .RequiresInterpretor(true)
                .RequiresSupportAttendance(true)
                .CreatedBy("ArrangeMeetingUser")
                .build();
    }

    public static String meetingAttendeesRequestBody(String chairPerson, List<String> meetingAttendeesRoles) {
        List<MeetingAttendees> requestBody = createAttendeesRequestBody(chairPerson, meetingAttendeesRoles);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(requestBody);
    }

    public static List<MeetingAttendees> createAttendeesRequestBody(String chairPerson, List<String> meetingAttendeesRoles) {
        List<MeetingAttendees> attendees = new ArrayList<>();
        for (String role : meetingAttendeesRoles) {
            MeetingAttendees.MeetingAttendeesBuilder builder = MeetingAttendees.builder()
                    .CreatedBy("AutomationUser")
                    .AttendeeRole(role)
                    .ChairPerson(role.equalsIgnoreCase(chairPerson));
            if ("Defence Solicitor".equalsIgnoreCase(role) || "Officer in Charge".equalsIgnoreCase(role)) {
                builder.AttendeeName(surnameFirstname());
            }
            attendees.add(builder.build());
        }
        return attendees;
    }

    //    @SneakyThrows
    public static MeetingCancel meetingCancel(int meetingTypeCode, String meetingContextGuid, String cancelReason, Meetings meetingsInputDetails) {
        return MeetingCancel.builder()
                .MeetingType(meetingTypeCode)
                .MeetingContextGuid(meetingContextGuid)
                .MeetingMethod(meetingsInputDetails.getMeetingMethod())
                .MeetingSource(meetingsInputDetails.getMeetingSource())
                .MeetingDateTime(meetingsInputDetails.getMeetingDateTime())
                .LocationType(meetingsInputDetails.getLocationType())
                .LocationName(meetingsInputDetails.getLocationName())
                .SpecialNeeds(false)
                .RequiresInterpretor(true)
                .RequiresSupportAttendance(true)
                .NatureOfNeeds(meetingsInputDetails.getNatureOfNeeds())
                .OtherTypeDescription(meetingsInputDetails.getOtherTypeDescription())
                .Cancelled(true)
                .CancellationReason(cancelReason)
                .CancellationDate(UTCDateTimeInPastBy(1))
                .LastModifiedBy("CancelUSer")
                .build();
    }

    public static MeetingLogged meetingLog(int meetingTypeCode, String meetingContextGuid, String meetingDuration,
                                           int agreedToResearch, String noteToOci, String noteToVictim, String proposedAction,
                                           Meetings meetingsInputDetails) {
        return MeetingLogged.builder()
                .MeetingType(meetingTypeCode)
                .MeetingContextGuid(meetingContextGuid)
                .MeetingMethod(meetingsInputDetails.getMeetingMethod())
                .MeetingSource(meetingsInputDetails.getMeetingSource())
                .MeetingDateTime(meetingsInputDetails.getMeetingDateTime())
                .LocationType(meetingsInputDetails.getLocationType())
                .LocationName(meetingsInputDetails.getLocationName())
                .SpecialNeeds(false)
                .RequiresInterpretor(true)
                .RequiresSupportAttendance(true)
                .NatureOfNeeds(meetingsInputDetails.getNatureOfNeeds())
                .OtherTypeDescription(meetingsInputDetails.getOtherTypeDescription())
                .MeetingConducted(true)
                .Cancelled(false)
                .ReasonNotConducted("Unselected")
                .MeetingDuration(meetingDuration)
                .ActionAgreed(
                        proposedAction != null
                                && !proposedAction.isEmpty()
                                && !"No".equalsIgnoreCase(proposedAction)
                )
                .ProposedActions("No".equals(proposedAction) ? "" : proposedAction)
                .ContactForResearch(agreedToResearch)
                .NotesSentToOic("Yes".equals(noteToOci))
                .NotesSentToVictim("Yes".equals(noteToVictim))
                .LastModifiedBy("LogMeetingUSer")
                .build();
    }

    public static String logAttendeesRequestBody(Map<String, String> meetingAttendeeGuids, String chairPerson, List<String> meetingAttendeesRoles) {
        List<MeetingAttendees> requestBody = logMeetingAttendeesRequestBody(meetingAttendeeGuids, chairPerson, meetingAttendeesRoles);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(requestBody);
    }

    public static List<MeetingAttendees> logMeetingAttendeesRequestBody(Map<String, String> meetingAttendeeGuids, String chairPerson, List<String> logAttendeesRoles) {
        List<MeetingAttendees> attendees = new ArrayList<>();
        for (String role : logAttendeesRoles) {
            String meetingAttendeeGuid = meetingAttendeeGuids.get(role);
            MeetingAttendees.MeetingAttendeesBuilder builder = MeetingAttendees.builder()
                    .LastModifiedBy("AutomationUser")
                    .AttendedMeeting(true)
                    .MeetingAttendeeGuid(meetingAttendeeGuid)
                    .AttendeeRole(role)
                    .ChairPerson(role.equalsIgnoreCase(chairPerson));
            attendees.add(builder.build());
        }
        return attendees;
    }

    public static Communication logOtherComms(int journeyTypeCode, int communicationType, int direction, String personContacted, String purpose) {
        return Communication.builder()
                .JourneyType(journeyTypeCode)
                .CommunicationType(communicationType)
                .Direction(direction)
                .DateOfCommunication(UTCDateInPast(3))
                .TimeOfCommunication(UTCTimeNow())
                .PurposeOfCommunication(purpose)
                .PersonRole(personContacted)
                .PersonName(firstnameSurname())
                .CreatedBy("adhocCommunicationUser")
                .OtherCommunicationType(communicationType == 6 ? "Other Contact method" : "")
                .build();
    }

    public static Decision createVictimNotContactDecision(int decision) {
        if (decision == 3) {
            return Decision.builder()
                    .DecisionToChargeContextGuid(uuid())
                    .DateOfHearing("1900-01-01")
                    .LocationOfHearing("")
                    .NoFurtherAttempts(false)
                    .VictimNotContacted(true)
                    .VictimNotContactedReason("victim not contacted reason")
                    .CreatedBy("decisionUser")
                    .build();

        } else if (decision == 4) {
            return Decision.builder()
                    .NoFurtherActionContextGuid(uuid())
                    .VictimWithdrawn(true)
                    .VictimNotContacted(true)
                    .VictimNotContactedReason("victim not contacted reason")
                    .CreatedBy("decisionUser")
                    .build();
        }
        return null;
    }

    public static Task createLogCommsTask(int task, int userPartyId) {
        return Task.builder()
                .Assignee(userPartyId)
                .DueDate(UTCDateTimeInFutureDayBy(1))
                .Task(task)
                .OrderNumber(1)
                .CreatedBy("createLogCommsTask")
                .build();
    }

    public static VictimVcaDetails addChargeTypeToVCA(int chargeType, int userPartyId) {
        return VictimVcaDetails.builder()
                .PreferredName("")
                .preferredMethodOfContact(PreferredMethodOfContact.Default)
                .IsYouth(Boolean.FALSE)
                .SuitableContactTimes("")
                .SpecialConsiderationNeeds("")
                .VLOPartyId(userPartyId)
                .ChargeType(chargeType)
                .LastModifiedBy("addChargeType")
                .build();
    }








}













































