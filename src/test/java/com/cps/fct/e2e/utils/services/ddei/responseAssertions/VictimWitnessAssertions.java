package com.cps.fct.e2e.utils.services.ddei.responseAssertions;

import com.cps.fct.e2e.model.VictimWitnessDetails;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.httpClient.ResourceResponseStore;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VcaPersonalDetails;
import org.assertj.core.api.SoftAssertions;

import java.util.Map;

import static com.cps.fct.e2e.utils.common.JsonUtils.readJsonPath;
import static org.assertj.core.api.Assertions.assertThat;

public class VictimWitnessAssertions {

    public static void  assertVictimWitnessPersonalDetails(Map<String, VictimWitnessDetails> detailsMap)
    {
       HttpResponseWrapper response = ResourceResponseStore.
               getLatestResponse("witnessDetails");
        String responseBody = response.getBody();
        detailsMap.forEach((id, victimWitnessDetails) -> {
            SoftAssertions softly = new SoftAssertions();
            int witnessId = Integer.parseInt(id);

            String expectedEmail = getContactDetail(responseBody, witnessId, "email");
            String expectedMobileNumber = getContactDetail(responseBody, witnessId, "mobileNumber");
            String expectedPhoneNumber = getContactDetail(responseBody, witnessId, "phoneNumber");
            String expectedWorkPhoneNumber = getContactDetail(responseBody, witnessId, "workPhoneNumber");

            //assertions
            assertThat(expectedEmail).isEqualTo(victimWitnessDetails.getContactDetailsEmail());
            assertThat(expectedMobileNumber).isEqualTo(victimWitnessDetails.getContactDetailsMobileNumber());
            assertThat(expectedPhoneNumber).isEqualTo(victimWitnessDetails.getContactDetailsPhoneNumber());
            assertThat(expectedWorkPhoneNumber).isEqualTo(victimWitnessDetails.getContactDetailsWorkPhoneNumber());
            softly.assertAll();
        });
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
