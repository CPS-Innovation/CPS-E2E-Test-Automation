package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import com.cps.fct.e2e.enums.PreferredMethodOfContact;
import com.cps.fct.e2e.model.VictimWitnessDetails;
import com.cps.fct.e2e.utils.common.FakerUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.FakerUtils.*;
import static com.cps.fct.e2e.utils.common.JsonUtils.toJsonString;

public class VictimWitnessPayloadBuilder {


    public static VictimWitnessDetails getVictimWitnessDetails() {
        return VictimWitnessDetails.builder()
                .title("Mr")
                .firstName(FakerUtils.firstName())
                .surname(FakerUtils.lastName())
                .dateOfBirth("02-02-1985")
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

    public static String payLoadForAddWitnessDetailToVca(VcaPersonalDetails payload) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PreferredMethodOfContact.class, new PreferredMethodOfContactAdapter())
                .setPrettyPrinting()
                .create();
        return gson.toJson(payload) ;
    }

    @NotNull
    public static VcaPersonalDetails getPersonDetailsVCAPayload(PreferredMethodOfContact method) {
        return VcaPersonalDetails.builder()
                .PreferredName("E2E Automation")
                .preferredMethodOfContact(method)
                .IsYouth(Boolean.FALSE)
                .SuitableContactTimes("anytime between 9am to 6pm weekdays")
                .SpecialConsiderationNeeds("Wheelchair access")
                .LastModifiedBy("CPS USER")
                .build();
    }

    public static String payLoadForAddVictimWitnessToVCA(String caseUrn) {
        Map<String, String> payload = new HashMap<>();
        payload.put("Urn", caseUrn);
        payload.put("CreatedBy", "E2ETestAutomation");
        return toJsonString(payload);
    }

    public static String payLoadForAddWitnessDetailsWitnessId(VictimWitnessDetails victimDetails ) {
        UpdateWitnessDetailsWitnessIdJsonBuilder builder = new UpdateWitnessDetailsWitnessIdJsonBuilder();
        List<Map<String, Object>> patchPayload = builder
                .add("/contactDetails/title", victimDetails.getContactDetailsTitle())
                .add("/contactDetails/gender", victimDetails.getContactDetailsGender())
                .add("/dateOfBirth", victimDetails.getDateOfBirth())
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
        return  gson.toJson(patchPayload);
    }
}
