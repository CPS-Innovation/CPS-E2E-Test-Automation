package com.cps.fct.e2e.utils.services.ddei.responseAssertions;

import com.cps.fct.e2e.model.victimCaseApp.*;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import com.google.gson.JsonArray;

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
//        String expectedPreferredMethodOfContact = readJsonPath(responseBody, "$.value.preferredMethodOfContact");
        String suitableContactTime = readJsonPath(responseBody, "$.value.suitableContactTimes", String.class);
        String specialConsiderationNeeds = readJsonPath(responseBody, "$.value.specialConsiderationNeeds", String.class);
        String victimCaseInfoGuid = readJsonPath(responseBody, "$.value.victimCaseInfoGuid", String.class);
        String lastModifiedBy = readJsonPath(responseBody, "$.value.lastModifiedBy", String.class);

        //assertions
        assertThat(preferredName).isEqualTo(inputDetails.getPreferredName());
        assertThat(iSYouth).isEqualTo(inputDetails.isIsYouth());
//        assertThat(expectedPreferredMethodOfContact).isEqualTo(Integer.parseInt(inputDetails.getPreferredMethodOfContact().getValue()));
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
        CaseCMSContact caseCMSContact;
        SoftAssertions softly = new SoftAssertions();
        JsonArray context;

//        List<Map<String, Object>> officerInCaseList =
//                new JsonPath(responsePayload.getBody())
//                        .get("find { it.contactType == 'OFFICER_IN_CASE' }");
//        List<Map<String, Object>> defenceFirmResult =
//                new JsonPath(responsePayload.getBody())
//                        .get("find { it.contactType == 'OFFICER_IN_CASE' }");
//
//        List<Map<String, Object>> defenceSolicitorResult =
//                new JsonPath(responsePayload.getBody())
//                        .get("find { it.contactType == 'OFFICER_IN_CASE' }");
//
//        assertThat(officerInCaseList)
//                .as("OFFICER_IN_CASE should exist in response")
//                .isNotEmpty();
//        Map<String, Object> officerInCase = officerInCaseList.getFirst();
//        CaseCMSContact actualOfficerInCaseContact = CaseCMSContact.builder()
//                .contactType((String) officerInCase.get("contactType"))
//                .name((String) officerInCase.get("name"))
//                .phone((String) officerInCase.get("phone"))
//                .email((String) officerInCase.get("email"))
//                .build();
//        assertThat(actualOfficerInCaseContact.getContactType())
//                .isEqualTo(.getContactType());
//        assertThat(actualOfficerInCaseContact.getName())
//                .isEqualTo(expectedOfficerInCaseContact.getName());
//        assertThat(actualOfficerInCaseContact.getPhone())
//                .isEqualTo(expectedOfficerInCaseContact.getPhone());
//        assertThat(actualOfficerInCaseContact.getEmail())
//                .isEqualTo(expectedOfficerInCaseContact.getEmail());

    }

    public static void assertCategoryList(String id, VictimCmsDetails inputDetails,
                                          HttpResponseWrapper responsePayload) {
        SoftAssertions softly = new SoftAssertions();
//        String responseBody = responsePayload.getBody();
//        List<String> categoryList = extractFromJsonToList(responseBody, "$?(@.isWitnessAndVictim==true).types");

        //assertions
//        assertThat(categoryList.getFirst()).isEqualTo(inputDetails.getCategory());
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

    public static void assertMeetingResponse(Meetings inputDetails,
                                                HttpResponseWrapper responsePayload){
        SoftAssertions softly = new SoftAssertions();

        JsonPath result = new JsonPath(responsePayload.getBody());

        assertThat(result.getInt("value[0].meetingType")).isEqualTo(inputDetails.getMeetingType());
        assertThat(result.getInt("value[0].methodOfOffer")).isEqualTo(inputDetails.getMethodOfOffer());
        assertThat(result.getString("value[0].meetingContextGuid")).isEqualTo(inputDetails.getMeetingContextGuid());
//        assertThat(result.getString("value[0].victimResponse")).isEqualTo(inputDetails.getVictimResponse());
//        assertThat(result.getString("value[0].methodOfResponse")).isEqualTo(inputDetails.getMethodOfResponse());
//        assertThat(result.getString("value[0].victimResponseDate")).isEqualTo(inputDetails.getVictimResponseDate());
        softly.assertAll();
    }

    public static void assertArrangedMeeting(Meetings inputDetails,
                                             HttpResponseWrapper responsePayload){
        SoftAssertions softly = new SoftAssertions();

        JsonPath result = new JsonPath(responsePayload.getBody());

//        assertThat(result.getInt("value[0].meetingType")).isEqualTo(inputDetails.getMeetingType());
//        assertThat(result.getString("value[0].meetingContextGuid")).isEqualTo(inputDetails.getMeetingContextGuid());
//        assertThat(result.getString("value[0].victimResponse")).isEqualTo(inputDetails.getVictimResponse());
//        assertThat(result.getString("value[0].methodOfResponse")).isEqualTo(inputDetails.getMethodOfResponse());
//        assertThat(result.getString("value[0].victimResponseDate")).isEqualTo(inputDetails.getVictimResponseDate());
        softly.assertAll();
    }

    public static void assertCancelArrangedMeeting(MeetingCancel inputDetails,
                                             HttpResponseWrapper responsePayload){
        SoftAssertions softly = new SoftAssertions();

        JsonPath result = new JsonPath(responsePayload.getBody());

        assertThat(result.getInt("value[0].meetingType")).isEqualTo(inputDetails.getMeetingType());
        assertThat(result.getString("value[0].meetingContextGuid")).isEqualTo(inputDetails.getMeetingContextGuid());

        softly.assertAll();
    }

}
