package com.cps.fct.e2e.utils.services.ddei.responseAssertions;

import com.cps.fct.e2e.model.VictimWitnessDetails;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.httpClient.ResourceResponseStore;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VcaPersonalDetails;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import java.util.List;
import java.util.Map;
import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.JsonUtils.extractFromJsonToList;
import static com.cps.fct.e2e.utils.common.JsonUtils.readJsonPath;
import static org.assertj.core.api.Assertions.assertThat;

public class VictimWitnessAssertions {

    public static void assertCMSPersonalDetails(String id,VictimWitnessDetails inputDetails,
                                                 HttpResponseWrapper responsePayload)
    {
        SoftAssertions softly = new SoftAssertions();
        String body = responsePayload.getBody();
        List<String> expectedEmail = extractFromJsonToList(body, "$[?(@.witnessId=="+id+")].contactDetails.email");
        List<String> expectedMobileNumber = extractFromJsonToList(body, "$[?(@.witnessId=="+id+")].contactDetails.mobileNumber");
        List<String> expectedPhoneNumber = extractFromJsonToList(body, "$[?(@.witnessId=="+id+")].contactDetails.phoneNumber");
        List<String> expectedWorkPhoneNumber = extractFromJsonToList(body, "$[?(@.witnessId=="+id+")].contactDetails.workPhoneNumber");

        //assertions
        assertThat(expectedEmail.getFirst()).isEqualTo(inputDetails.getContactDetailsEmail());
        assertThat(expectedMobileNumber.getFirst()).isEqualTo(inputDetails.getContactDetailsMobileNumber());
        assertThat(expectedPhoneNumber.getFirst()).isEqualTo(inputDetails.getContactDetailsPhoneNumber());
        assertThat(expectedWorkPhoneNumber.getFirst()).isEqualTo(inputDetails.getContactDetailsWorkPhoneNumber());
        softly.assertAll();
    }

    private static String getContactDetail(String responseBody, int id, String fieldName) {
        String jsonPath = String.format("$[?(@.witnessId == %d)].contactDetails.%s", id, fieldName);
        return readJsonPath(responseBody, jsonPath, String.class);
    }


    public static void assertWitnessPersonalDetailsFromCMS(Map<String, VcaPersonalDetails> vcaPersonalDetailsMap) {
        HttpResponseWrapper response = ResourceResponseStore.
                getLatestResponse("witnessDetailsFromCms");
        String responseBody = response.getBody();

        vcaPersonalDetailsMap.forEach((victimGuidId, vcaPersonalDetails) -> {
            SoftAssertions softly = new SoftAssertions();
            String preferredName = readJsonPath(responseBody, "$.value.preferredName", String.class);
            Boolean isYouth = readJsonPath(responseBody, "$.value.isYouth", Boolean.class);
//            String preferredMethodOfContact = readJsonPath(responseBody, "$.value.preferredMethodOfContact");
            String suitableContactTime = readJsonPath(responseBody, "$.value.suitableContactTimes",String.class);
            String specialConsiderationNeeds = readJsonPath(responseBody, "$.value.specialConsiderationNeeds",String.class);
            String victimCaseInfoGuid = readJsonPath(responseBody, "$.value.victimCaseInfoGuid",String.class);
            String lastModifiedBy = readJsonPath(responseBody, "$.value.lastModifiedBy",String.class);


            assertThat(vcaPersonalDetails.getPreferredName()).isEqualTo(preferredName);
            assertThat(vcaPersonalDetails.isIsYouth()).isEqualTo(isYouth);
//            assertThat(vcaPersonalDetails.getPreferredMethodOfContact().getValue()).isEqualTo(Integer.parseInt(preferredMethodOfContact));
            assertThat(vcaPersonalDetails.getSpecialConsiderationNeeds()).isEqualTo(specialConsiderationNeeds);
            assertThat(vcaPersonalDetails.getSuitableContactTimes()).isEqualTo(suitableContactTime);
            assertThat(victimGuidId).isEqualTo(victimCaseInfoGuid);
            assertThat(vcaPersonalDetails.getLastModifiedBy()).isEqualTo(lastModifiedBy);
            softly.assertAll();
        });

    }
}
