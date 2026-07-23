package com.cps.fct.e2e.utils.services.ddei.responseAssertions;

import com.cps.fct.e2e.model.VictimWitnessDetails;
import com.cps.fct.e2e.utils.common.JsonUtils;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VcaPersonalDetails;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimContactDetails;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimLiaisonOfficerDetails;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimMeetingDetails;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.JsonUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class VictimWitnessAssertions {

    public static void assertCMSPersonalDetails(String id,VictimWitnessDetails inputDetails,
                                                 HttpResponseWrapper responsePayload)
    {
        SoftAssertions softly = new SoftAssertions();
        String responseBody = responsePayload.getBody();
        List<String> expectedEmail = extractFromJsonToList(responseBody, "$[?(@.witnessId=="+id+")].contactDetails.email");
        List<String> expectedMobileNumber = extractFromJsonToList(responseBody, "$[?(@.witnessId=="+id+")].contactDetails.mobileNumber");
        List<String> expectedPhoneNumber = extractFromJsonToList(responseBody, "$[?(@.witnessId=="+id+")].contactDetails.phoneNumber");
        List<String> expectedWorkPhoneNumber = extractFromJsonToList(responseBody, "$[?(@.witnessId=="+id+")].contactDetails.workPhoneNumber");

        //assertions
        assertThat(expectedEmail.getFirst()).isEqualTo(inputDetails.getContactDetailsEmail());
        assertThat(expectedMobileNumber.getFirst()).isEqualTo(inputDetails.getContactDetailsMobileNumber());
        assertThat(expectedPhoneNumber.getFirst()).isEqualTo(inputDetails.getContactDetailsPhoneNumber());
        assertThat(expectedWorkPhoneNumber.getFirst()).isEqualTo(inputDetails.getContactDetailsWorkPhoneNumber());

        assertThat(expectedEmail.getFirst()).isEqualTo(inputDetails.getContactDetailsEmail());
        assertThat(expectedMobileNumber.getFirst()).isEqualTo(inputDetails.getContactDetailsMobileNumber());
        assertThat(expectedPhoneNumber.getFirst()).isEqualTo(inputDetails.getContactDetailsPhoneNumber());
        assertThat(expectedWorkPhoneNumber.getFirst()).isEqualTo(inputDetails.getContactDetailsWorkPhoneNumber());
        softly.assertAll();
    }

    public static void assertCategoryDetails(String id,VictimWitnessDetails inputDetails,
                                                HttpResponseWrapper responsePayload)
    {
        SoftAssertions softly = new SoftAssertions();
        String responseBody = responsePayload.getBody();
        String expectedCategory = extractFromJson(responseBody, "$[?(@.witnessId=="+id+")].types");
        //assertions
        if (inputDetails.getCategory().length()==1){
            assertThat(expectedCategory).contains(inputDetails.getCategory());
        }
        else{
            assertThat(expectedCategory).contains(inputDetails.getCategory().split(",")[0]);
            assertThat(expectedCategory).contains(inputDetails.getCategory().split(",")[1]);
        }
        softly.assertAll();
    }

    public static void assertVCAPersonalDetails(String guid,VcaPersonalDetails inputDetails,
                                                HttpResponseWrapper responsePayload)
    {
        SoftAssertions softly = new SoftAssertions();
        String responseBody = responsePayload.getBody();

        String expectedPreferredName = readJsonPath(responseBody, "$.value.preferredName", String.class);
        Boolean expectedIsYouth = readJsonPath(responseBody, "$.value.isYouth", Boolean.class);
//        String expectedPreferredMethodOfContact = readJsonPath(responseBody, "$.value.preferredMethodOfContact");
        String expectedSuitableContactTime = readJsonPath(responseBody, "$.value.suitableContactTimes",String.class);
        String expectedSpecialConsiderationNeeds = readJsonPath(responseBody, "$.value.specialConsiderationNeeds",String.class);
        String expectedVictimCaseInfoGuid = readJsonPath(responseBody, "$.value.victimCaseInfoGuid",String.class);
        String expectedLastModifiedBy = readJsonPath(responseBody, "$.value.lastModifiedBy",String.class);

        //assertions
        assertThat(expectedPreferredName).isEqualTo(inputDetails.getPreferredName());
        assertThat(expectedIsYouth).isEqualTo(inputDetails.isIsYouth());
//        assertThat(expectedPreferredMethodOfContact).isEqualTo(Integer.parseInt(inputDetails.getPreferredMethodOfContact().getValue()));
        assertThat(expectedSuitableContactTime).isEqualTo(inputDetails.getSuitableContactTimes());
        assertThat(expectedSpecialConsiderationNeeds).isEqualTo(inputDetails.getSpecialConsiderationNeeds());
        assertThat(expectedVictimCaseInfoGuid).isEqualTo(guid);
        assertThat(expectedLastModifiedBy).isEqualTo(inputDetails.getLastModifiedBy());
        softly.assertAll();
    }

    public static void assertVictimLiaisonOfficerDetails(String guid, VictimLiaisonOfficerDetails inputDetails,
                                                         HttpResponseWrapper responsePayload)
    {
        SoftAssertions softly = new SoftAssertions();
        String responseBody = responsePayload.getBody();

        Integer expectedVictimLiaisonOfficer = readJsonPath(responseBody, "$.value.vloPartyId",Integer.class);
        String expectedVictimCaseInfoGuid = readJsonPath(responseBody, "$.value.victimCaseInfoGuid",String.class);
        String expectedLastModifiedBy = readJsonPath(responseBody, "$.value.lastModifiedBy",String.class);

        //assertions
        assertThat(expectedVictimLiaisonOfficer).isEqualTo(inputDetails.getVLOPartyId());
        assertThat(expectedVictimCaseInfoGuid).isEqualTo(guid);
        assertThat(expectedLastModifiedBy).isEqualTo(inputDetails.getLastModifiedBy());
        softly.assertAll();

    }

    public static void assertContactTypeDetails(int contactTypeCode, VictimContactDetails inputDetails,
                                                Response responsePayload)
    {
        SoftAssertions softly = new SoftAssertions();
        //"value.find {it.contactType==1}"
        String filter = "value.find {it.contactType=="+ contactTypeCode +"}";
        LinkedHashMap<String, Object> result = responsePayload.getBody().jsonPath().get(filter);
        System.out.println(result.get("contactType").toString());

        assertThat(result.get("name")).isEqualTo(inputDetails.getContactName());
        assertThat(result.get("email")).isEqualTo(inputDetails.getContactEmail());
        assertThat(result.get("telephone")).isEqualTo(inputDetails.getContactTelephone());
        assertThat(result.get("contactType")).isEqualTo(inputDetails.getContactType());
        if(contactTypeCode != 2){
            assertThat((String) ((LinkedHashMap<?, ?>) result.get("addressFields")).get("addressLine1")).isEqualTo(inputDetails.getAddress().getAddressLine1());
            assertThat((String) ((LinkedHashMap<?, ?>) result.get("addressFields")).get("addressLine2")).isEqualTo(inputDetails.getAddress().getAddressLine2());
            assertThat((String) ((LinkedHashMap<?, ?>) result.get("addressFields")).get("city")).isEqualTo(inputDetails.getAddress().getCity());
            assertThat((String) ((LinkedHashMap<?, ?>) result.get("addressFields")).get("postcode")).isEqualTo(inputDetails.getAddress().getPostcode());
        }
        softly.assertAll();
    }

    public static void assertMeetingTypeDetails(int meetingTypeCode, VictimMeetingDetails inputDetails,
                                                Response responsePayload)
    {
//        SoftAssertions softly = new SoftAssertions();
        //"value.find {it.contactType==1}"
//        String filter = "value.find {it.meetingType=="+ meetingTypeCode +"}";
//        LinkedHashMap<String, Object> result = responsePayload.getBody().jsonPath().get(filter);
//        System.out.println(result.get("contactType").toString());

        SoftAssertions softly = new SoftAssertions();
        String result = String.valueOf(responsePayload.getBody());



    }

    public static void assertDeclineMeetingDetails(int meetingTypeCode, VictimMeetingDetails inputDetails,
                                                Response responsePayload)
    {

        SoftAssertions softly = new SoftAssertions();
        LinkedHashMap<String, Object> result = responsePayload.getBody().jsonPath().get();

        Map<String, Object> value = (Map<String, Object>) result.get("value");

        Integer expectedMeetingType = (Integer) value.get("meetingType");
        Integer expectedMethodOfOffer = (Integer) value.get("methodOfOffer");
        String expectedDateOfOffer = (String) value.get("dateOfOffer");
        String expectedVictimResponse = (String) value.get("victimResponse");
        Integer expectedMethodOfResponse = (Integer) value.get("methodOfResponse");
        String expectedVictimResponseDate = (String) value.get("victimResponseDate");
        String expectedLastModifiedBy = (String) value.get("lastModifiedBy");

        assertThat(expectedMeetingType).isEqualTo(inputDetails.getMeetingType());
        assertThat(expectedMethodOfOffer).isEqualTo(inputDetails.getMethodOfOffer());
        assertThat(expectedDateOfOffer).isEqualTo(inputDetails.getDateOfOffer());
        assertThat(expectedVictimResponse).isEqualTo(inputDetails.getVictimResponse());
        assertThat(expectedMethodOfResponse).isEqualTo(inputDetails.getMethodOfResponse());
        assertThat(expectedVictimResponseDate).isEqualTo(inputDetails.getVictimResponseDate());
        assertThat(expectedLastModifiedBy).isEqualTo(inputDetails.getLastModifiedBy());

    }

    public static void assertNoResponseMeetingDetails(int meetingTypeCode, VictimMeetingDetails inputDetails,
                                                   Response responsePayload)
    {

        SoftAssertions softly = new SoftAssertions();
        LinkedHashMap<String, Object> result = responsePayload.getBody().jsonPath().get();

        Map<String, Object> value = (Map<String, Object>) result.get("value");

        Integer expectedMeetingType = (Integer) value.get("meetingType");
        Integer expectedMethodOfOffer = (Integer) value.get("methodOfOffer");
        String expectedDateOfOffer = (String) value.get("dateOfOffer");
        String expectedVictimResponse = (String) value.get("victimResponse");
        Integer expectedMethodOfResponse = (Integer) value.get("methodOfResponse");
        String expectedVictimResponseDate = (String) value.get("victimResponseDate");
        String expectedLastModifiedBy = (String) value.get("lastModifiedBy");

        assertThat(expectedMeetingType).isEqualTo(inputDetails.getMeetingType());
        assertThat(expectedMethodOfOffer).isEqualTo(inputDetails.getMethodOfOffer());
        assertThat(expectedDateOfOffer).isEqualTo(inputDetails.getDateOfOffer());
        assertThat(expectedVictimResponse).isEqualTo(inputDetails.getVictimResponse());
        assertThat(expectedMethodOfResponse).isEqualTo(inputDetails.getMethodOfResponse());
        assertThat(expectedVictimResponseDate).isEqualTo(inputDetails.getVictimResponseDate());
        assertThat(expectedLastModifiedBy).isEqualTo(inputDetails.getLastModifiedBy());

    }



}
