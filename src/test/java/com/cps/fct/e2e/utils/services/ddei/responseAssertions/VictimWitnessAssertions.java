package com.cps.fct.e2e.utils.services.ddei.responseAssertions;

import com.cps.fct.e2e.model.VictimWitnessDetails;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VcaPersonalDetails;
import com.cps.fct.e2e.utils.services.ddei.payloadBuilder.VictimContactDetails;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import java.util.LinkedHashMap;
import java.util.List;

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

    public static void assertContactTypeDetails(int contactTypeCode, VictimContactDetails inputDetails,
                                                Response responsePayload)
    {
        SoftAssertions softly = new SoftAssertions();
        //"value.find {it.contactType==1}"
        String filter = "value.find {it.contactType=="+ contactTypeCode +"}";
        LinkedHashMap<String, Object> result = responsePayload.getBody().jsonPath().get(filter);
        System.out.println(result.get("contactType").toString());
        softly.assertAll();
    }
}
