package com.cps.fct.e2e.utils.services.ddei.responseAssertions;

import com.cps.fct.e2e.model.victimCaseApp.*;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import net.minidev.json.JSONArray;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.SoftAssertions;
import com.google.gson.JsonArray;

import java.sql.SQLOutput;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.JsonUtils.*;
import static org.assertj.core.api.Assertions.assertThat;


public class VictimCaseAppAssertions {

    public static void assertCMSPersonalDetails(String id, VictimCmsDetails inputDetails,
                                                HttpResponseWrapper responsePayload) {
        SoftAssertions softly = new SoftAssertions();
        String responseBody = responsePayload.getBody();
        List<String> email = extractFromJsonToList(responseBody, "$[?(@.witnessId==" + id + ")].contactDetails.email");
        List<String> mobileNumber = extractFromJsonToList(responseBody, "$[?(@.witnessId==" + id + ")].contactDetails.mobileNumber");
        List<String> phoneNumber = extractFromJsonToList(responseBody, "$[?(@.witnessId==" + id + ")].contactDetails.phoneNumber");
        List<String> workPhoneNumber = extractFromJsonToList(responseBody, "$[?(@.witnessId==" + id + ")].contactDetails.workPhoneNumber");

        //assertions
        assertThat(email.getFirst()).isEqualTo(inputDetails.getContactDetailsEmail());
        assertThat(mobileNumber.getFirst()).isEqualTo(inputDetails.getContactDetailsMobileNumber());
        assertThat(phoneNumber.getFirst()).isEqualTo(inputDetails.getContactDetailsPhoneNumber());
        assertThat(workPhoneNumber.getFirst()).isEqualTo(inputDetails.getContactDetailsWorkPhoneNumber());
        softly.assertAll();
    }

    public static void assertVCAPersonalDetails(String guid, VictimVcaDetails inputDetails,
                                                HttpResponseWrapper responsePayload) {
        SoftAssertions softly = new SoftAssertions();
        String responseBody = responsePayload.getBody();

        String preferredName = readJsonPath(responseBody, "$.value.preferredName", String.class);
        Boolean iSYouth = readJsonPath(responseBody, "$.value.isYouth", Boolean.class);
        String suitableContactTime = readJsonPath(responseBody, "$.value.suitableContactTimes", String.class);
        String specialConsiderationNeeds = readJsonPath(responseBody, "$.value.specialConsiderationNeeds", String.class);
        String victimCaseInfoGuid = readJsonPath(responseBody, "$.value.victimCaseInfoGuid", String.class);
        String lastModifiedBy = readJsonPath(responseBody, "$.value.lastModifiedBy", String.class);

        //assertions
        assertThat(preferredName).isEqualTo(inputDetails.getPreferredName());
        assertThat(iSYouth).isEqualTo(inputDetails.isIsYouth());
        assertThat(suitableContactTime).isEqualTo(inputDetails.getSuitableContactTimes());
        assertThat(specialConsiderationNeeds).isEqualTo(inputDetails.getSpecialConsiderationNeeds());
        assertThat(victimCaseInfoGuid).isEqualTo(guid);
        assertThat(lastModifiedBy).isEqualTo(inputDetails.getLastModifiedBy());
        softly.assertAll();
    }

    public static void assertCpsContacts(Integer contactTypeCode, CpsContacts inputDetails,
                                         HttpResponseWrapper responsePayload) {
        SoftAssertions softly = new SoftAssertions();
        String filter = "value.find {it.contactType==" + contactTypeCode + "}";

        LinkedHashMap<String, Object> result = new JsonPath(responsePayload.getBody()).get(filter);

        assertThat(result.get("name")).isEqualTo(inputDetails.getContactName());
        assertThat(result.get("email")).isEqualTo(inputDetails.getContactEmail());
        assertThat(result.get("telephone")).isEqualTo(inputDetails.getContactTelephone());
        assertThat(result.get("contactType")).isEqualTo(inputDetails.getContactType());
        if (contactTypeCode != 2) {
            assertThat((String) ((LinkedHashMap<?, ?>) result.get("addressFields")).get("addressLine1")).isEqualTo(inputDetails.getAddress().getAddressLine1());
            assertThat((String) ((LinkedHashMap<?, ?>) result.get("addressFields")).get("addressLine2")).isEqualTo(inputDetails.getAddress().getAddressLine2());
            assertThat((String) ((LinkedHashMap<?, ?>) result.get("addressFields")).get("city")).isEqualTo(inputDetails.getAddress().getCity());
            assertThat((String) ((LinkedHashMap<?, ?>) result.get("addressFields")).get("postcode")).isEqualTo(inputDetails.getAddress().getPostcode());
        }
        softly.assertAll();
    }

    public static void assertCaseCmsContact(String cm01RequestPayload, HttpResponseWrapper responsePayload) {

        SoftAssertions softly = new SoftAssertions();
        String responseBody = responsePayload.getBody();

        String rank = "Constable";
        String expectedOfficerInCaseGivenName = readJsonPath(cm01RequestPayload,
                "$.PreChargeDecisionRequest.CaseContacts[?(@.Officer.PoliceOfficerRank == '" + rank + "')].Name.GivenName",
                String.class);
        String expectedOfficerInCaseFamilyName = readJsonPath(cm01RequestPayload,
                "$.PreChargeDecisionRequest.CaseContacts[?(@.Officer.PoliceOfficerRank == '" + rank + "')].Name.FamilyName",
                String.class);
        String expectedOfficerInCaseName = expectedOfficerInCaseFamilyName + ", " + expectedOfficerInCaseGivenName;
        String expectedOfficerInCaseEmail = readJsonPath(cm01RequestPayload,
                "$.PreChargeDecisionRequest.CaseContacts[?(@.Officer.PoliceOfficerRank == '" + rank + "')].ContactDetails.Email",
                String.class);
        String expectedOfficerInCasePhone = readJsonPath(cm01RequestPayload,
                "$.PreChargeDecisionRequest.CaseContacts[?(@.Officer.PoliceOfficerRank == '" + rank + "')].ContactDetails.ContactNumber[0].Number.TelNationalNumber",
                String.class);

        String expectedSolicitorFirmName = readJsonPath(cm01RequestPayload,
                "$.PreChargeDecisionRequest.Suspect[*].DefenceSolicitor.Firm", String.class);

        String expectedSolicitorEmail = readJsonPath(cm01RequestPayload,
                "$.PreChargeDecisionRequest.Suspect[*].DefenceSolicitor.ContactDetails.Email", String.class);

        String expectedSolicitorPhone = readJsonPath(cm01RequestPayload,
                "$.PreChargeDecisionRequest.Suspect[*].DefenceSolicitor.ContactDetails.ContactNumber[0].Number.TelNationalNumber", String.class);

        String expectedSolicitorGivenName = readJsonPath(cm01RequestPayload,
                "$.PreChargeDecisionRequest.Suspect[*].DefenceSolicitor.Name.GivenName", String.class);

        String expectedSolicitorFamilyName = readJsonPath(cm01RequestPayload,
                "$.PreChargeDecisionRequest.Suspect[*].DefenceSolicitor.Name.FamilyName", String.class);

        String expectedSolicitorName = expectedSolicitorFamilyName + ", " + expectedSolicitorGivenName;

        String contactType = "OFFICER_IN_CASE";
        String actualOfficerInCaseName = readJsonPath(responseBody,"$[?(@.contactType == '" + contactType + "')].name",String.class);
        String actualOfficerInCaseEmail = readJsonPath(responseBody,"$[?(@.contactType == '" + contactType + "')].email",String.class);
        String actualOfficerInCasePhone = readJsonPath(responseBody,"$[?(@.contactType == '" + contactType + "')].phone",String.class);

        String solicitorFirm = "DEFENCE_FIRM";
        String actualSolicitorFirmName = readJsonPath(responseBody,"$[?(@.contactType == '" + solicitorFirm + "')].name",String.class);
        String actualSolicitorFirmEmail = readJsonPath(responseBody,"$[?(@.contactType == '" + solicitorFirm + "')].email",String.class);
        String actualSolicitorFirmPhone = readJsonPath(responseBody,"$[?(@.contactType == '" + solicitorFirm + "')].phone",String.class);

        String solicitor = "DEFENCE_SOLICITOR";
        String actualSolicitorName = readJsonPath(responseBody,"$[?(@.contactType == '" + solicitor + "')].name",String.class);

        assertThat(actualOfficerInCaseName).isEqualTo(expectedOfficerInCaseName);
        assertThat(actualOfficerInCaseEmail).isEqualTo(expectedOfficerInCaseEmail);
        assertThat(actualOfficerInCasePhone).isEqualTo(expectedOfficerInCasePhone);

        assertThat(actualSolicitorFirmName).isEqualTo(expectedSolicitorFirmName);
        assertThat(actualSolicitorName).isEqualTo(expectedSolicitorName);
        assertThat(actualSolicitorFirmEmail).isEqualTo(expectedSolicitorEmail);
        assertThat(actualSolicitorFirmPhone).isEqualTo(expectedSolicitorPhone);
        softly.assertAll();
    }

    public static void assertCategoryList(String id, VictimCmsDetails inputDetails,
                                          HttpResponseWrapper responsePayload) {
        SoftAssertions softly = new SoftAssertions();
        String responseBody = responsePayload.getBody();

//        List<String> types = readJsonPath(responseBody, "$[0].types", List.class);
//        System.out.println(types);
//        String result = String.join(",", types);
//        System.out.println(result);
//
//        assertThat(result).isEqualTo(inputDetails.getCategory());

        softly.assertAll();
    }

    public static void assertMeetingNotOfferedDetails(int meetingTypeCode, Meetings inputDetails,
                                                      HttpResponseWrapper responsePayload) {
        SoftAssertions softly = new SoftAssertions();

        JsonPath result = new JsonPath(responsePayload.getBody());

        assertThat(result.getInt("value[0].meetingType")).isEqualTo(inputDetails.getMeetingType());
        assertThat(result.getInt("value[0].methodOfOffer")).isEqualTo(inputDetails.getMethodOfOffer());
        assertThat(result.getString("value[0].reasonForNoOffer")).isEqualTo(inputDetails.getReasonForNoOffer());
        assertThat(result.getString("value[0].meetingContextGuid")).isEqualTo(inputDetails.getMeetingContextGuid());
        softly.assertAll();
    }

    public static void assertMeetingOfferMethod(int meetingTypeCode, Meetings inputDetails,
                                                HttpResponseWrapper responsePayload){
        SoftAssertions softly = new SoftAssertions();

        JsonPath result = new JsonPath(responsePayload.getBody());

        assertThat(result.getInt("value[0].meetingType")).isEqualTo(inputDetails.getMeetingType());
        assertThat(result.getInt("value[0].methodOfOffer")).isEqualTo(inputDetails.getMethodOfOffer());
        assertThat(result.getString("value[0].meetingContextGuid")).isEqualTo(inputDetails.getMeetingContextGuid());
        softly.assertAll();
    }

    public static void assertMeetingResponse(Meetings inputDetails, String responseValue,
                                                HttpResponseWrapper responsePayload){
        SoftAssertions softly = new SoftAssertions();
        JsonPath result = new JsonPath(responsePayload.getBody());

        assertThat(result.getInt("value[0].meetingType")).isEqualTo(inputDetails.getMeetingType());
        assertThat(result.getInt("value[0].methodOfOffer")).isEqualTo(inputDetails.getMethodOfOffer());
        assertThat(result.getString("value[0].meetingContextGuid")).isEqualTo(inputDetails.getMeetingContextGuid());
        assertThat(result.getString("value[0].victimResponse")).isEqualTo(responseValue);
        softly.assertAll();
    }

    public static void assertArrangedMeeting(Meetings inputDetails,
                                             HttpResponseWrapper responsePayload){
        SoftAssertions softly = new SoftAssertions();
        JsonPath result = new JsonPath(responsePayload.getBody());
        assertThat(result.getInt("value.meetingType")).isEqualTo(inputDetails.getMeetingType());
        assertThat(result.getString("value.meetingContextGuid")).isEqualTo(inputDetails.getMeetingContextGuid());
        assertThat(result.getInt("value.meetingMethod")).isEqualTo(inputDetails.getMeetingMethod());
        assertThat(result.getInt("value.meetingSource")).isEqualTo(inputDetails.getMeetingSource());
        assertThat(result.getInt("value.locationType")).isEqualTo(inputDetails.getLocationType());
        assertThat(result.getString("value.locationName")).isEqualTo(inputDetails.getLocationName());

        softly.assertAll();
    }

    @SneakyThrows
    public static void assertMeetingAttendees(String inputDetails,
                                              HttpResponseWrapper responsePayload)  {
        SoftAssertions softly = new SoftAssertions();
        JsonPath result = new JsonPath(responsePayload.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
                List<MeetingAttendees> expected =
                objectMapper.readValue(inputDetails, new TypeReference<List<MeetingAttendees>>() {} );

        for (int i = 0; i < expected.size(); i++) {
            String expectedAttendeeRole = expected.get(i).getAttendeeRole();
            String actualAttendeeRole = result.getString("value[" + i + "].attendeeRole");

            Boolean expectedChairPerson = expected.get(i).isChairPerson();
            Boolean actualChairPerson = result.getBoolean("value[" + i + "].chairPerson");

            if (expectedAttendeeRole != null && !expectedAttendeeRole.isEmpty()
                    && actualAttendeeRole != null && !actualAttendeeRole.isEmpty()) {
                softly.assertThat(actualAttendeeRole)
                        .as("Attendee role at index " + i).isEqualTo(expectedAttendeeRole);
            }

            softly.assertThat(actualChairPerson)
                    .as("ChairPerson for attendee at index " + i + " (" + expectedAttendeeRole + ")")
                    .isEqualTo(expectedChairPerson);

            softly.assertAll();
        }

    }



    public static void assertCancelArrangedMeeting(MeetingCancel inputDetails,
                                             HttpResponseWrapper responsePayload){
        SoftAssertions softly = new SoftAssertions();
        JsonPath result = new JsonPath(responsePayload.getBody());
        assertThat(result.getInt("value.meetingType")).isEqualTo(inputDetails.getMeetingType());
        assertThat(result.getString("value.meetingContextGuid")).isEqualTo(inputDetails.getMeetingContextGuid());
        assertThat(result.getString("value.cancellationReason")).isEqualTo(inputDetails.getCancellationReason());
        softly.assertAll();
    }

    public static void assertMeetingOutcome(MeetingLogged inputDetails,
                                             HttpResponseWrapper responsePayload){
        SoftAssertions softly = new SoftAssertions();
        JsonPath result = new JsonPath(responsePayload.getBody());
        assertThat(result.getInt("value.meetingType")).isEqualTo(inputDetails.getMeetingType());
        assertThat(result.getInt("value.meetingMethod")).isEqualTo(inputDetails.getMeetingMethod());
        assertThat(result.getInt("value.meetingSource")).isEqualTo(inputDetails.getMeetingSource());
        assertThat(result.getString("value.meetingDuration")).isEqualTo(inputDetails.getMeetingDuration());
        assertThat(result.getInt("value.contactForResearch")).isEqualTo(inputDetails.getContactForResearch());
        assertThat(result.getBoolean("value.notesSentToOic")).isEqualTo(inputDetails.getNotesSentToOic());
        assertThat(result.getBoolean("value.notesSentToVictim")).isEqualTo(inputDetails.getNotesSentToVictim());
        assertThat(result.getString("value.proposedActions")).isEqualTo(inputDetails.getProposedActions());
        softly.assertAll();
    }

    public static void assertOtherComms(Communication inputDetails,
                                            HttpResponseWrapper responsePayload){
        SoftAssertions softly = new SoftAssertions();
        JsonPath result = new JsonPath(responsePayload.getBody());

        assertThat(result.getInt("value[0].communicationType")).isEqualTo(inputDetails.getCommunicationType());
        assertThat(result.getInt("value[0].attemptOrder")).isEqualTo(inputDetails.getCommunicationType());
        assertThat(result.getString("value[0].personRole")).isEqualTo(inputDetails.getPersonRole());
        assertThat(result.getString("value[0].purposeOfCommunication")).isEqualTo(inputDetails.getPurposeOfCommunication());

    }











}
