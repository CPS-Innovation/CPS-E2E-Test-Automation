package com.cps.fct.e2e.utils.services.ddei.responseAssertions;

import com.cps.fct.e2e.model.VictimWitnessDetails;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.httpClient.ResourceResponseStore;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VcaPersonalDetails;
import org.assertj.core.api.SoftAssertions;

import java.util.List;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.JsonUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

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
        String expectedCategory = extractCategoryFromJson(responseBody, "$[?(@.witnessId=="+id+")].types");

        //assertions
        assertThat(expectedCategory).isEqualTo(inputDetails.getCategory());
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

//    public static void assertCMSPersonalDetails(Map<String, VcaPersonalDetails> vcaPersonalDetailsMap, HttpResponseWrapper responsePayload) {
//        SoftAssertions softly = new SoftAssertions();
//        String responseBody = responsePayload.getBody();
//
//        String preferredName = readJsonPath(responseBody, "$.value.preferredName", String.class);
//        Boolean isYouth = readJsonPath(responseBody, "$.value.isYouth", Boolean.class);
//        //String preferredMethodOfContact = readJsonPath(responseBody, "$.value.preferredMethodOfContact");
//        String suitableContactTime = readJsonPath(responseBody, "$.value.suitableContactTimes",String.class);
//        String specialConsiderationNeeds = readJsonPath(responseBody, "$.value.specialConsiderationNeeds",String.class);
//        String victimCaseInfoGuid = readJsonPath(responseBody, "$.value.victimCaseInfoGuid",String.class);
//        String lastModifiedBy = readJsonPath(responseBody, "$.value.lastModifiedBy",String.class);
//
//
//        assertThat(vcaPersonalDetails.getPreferredName()).isEqualTo(preferredName);
//        assertThat(vcaPersonalDetails.isIsYouth()).isEqualTo(isYouth);
//        //assertThat(vcaPersonalDetails.getPreferredMethodOfContact().getValue()).isEqualTo(Integer.parseInt(preferredMethodOfContact));
//        assertThat(vcaPersonalDetails.getSpecialConsiderationNeeds()).isEqualTo(specialConsiderationNeeds);
//        assertThat(vcaPersonalDetails.getSuitableContactTimes()).isEqualTo(suitableContactTime);
//        assertThat(victimGuidId).isEqualTo(victimCaseInfoGuid);
//        assertThat(vcaPersonalDetails.getLastModifiedBy()).isEqualTo(lastModifiedBy);
//        softly.assertAll();
//
//
//    }
//
//    private static String getContactDetail(String responseBody, int id, String fieldName) {
//        String jsonPath = String.format("$[?(@.witnessId == %d)].contactDetails.%s", id, fieldName);
//        return readJsonPath(responseBody, jsonPath, String.class);
//    }

}
