package com.cps.fct.e2e.utils.services.caseCreation.payloadBuilders;

import com.cps.fct.e2e.utils.common.JsonReplacer;
import com.cps.fct.e2e.utils.common.JsonUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;

import java.io.IOException;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.cps.fct.e2e.utils.common.DateTimeUtils.*;
import static com.cps.fct.e2e.utils.common.FakerUtils.*;

public class PayloadBuilderForCM01 extends JsonReplacer {

    private static final Pattern UNRESOLVED_PLACEHOLDER = Pattern.compile("\\{\\{[^}]+}}");

    JsonUtils jsonUtils = new JsonUtils();
    private final Map<String, String> caseWithChargeMap = Map.ofEntries(
            Map.entry("{{DCF_Force}}", jsonUtils.getMetaDataKeyValue("dcf", "Force")),
            Map.entry("{{DCF_Unit}}", jsonUtils.getMetaDataKeyValue("dcf", "Unit")),
            Map.entry("{{DCF_Year}}", jsonUtils.getMetaDataKeyValue("dcf", "Year")),
            Map.entry("{{DCF_TopLevel}}", jsonUtils.getMetaDataKeyValue("dcf", "TopLevel")),
            Map.entry("{{DCF_SecondLevel}}", jsonUtils.getMetaDataKeyValue("dcf", "SecondLevel")),
            Map.entry("{{DCF_ThirdLevel}}", jsonUtils.getMetaDataKeyValue("dcf", "ThirdLevel")),
            Map.entry("{{DCF_BottomLevel}}", jsonUtils.getMetaDataKeyValue("dcf", "BottomLevel")),

            Map.entry("{{TWIF_Force}}", jsonUtils.getMetaDataKeyValue("twif", "Force")),
            Map.entry("{{TWIF_Unit}}", jsonUtils.getMetaDataKeyValue("twif", "Unit")),
            Map.entry("{{TWIF_Year}}", jsonUtils.getMetaDataKeyValue("twif", "Year")),
            Map.entry("{{TWIF_TopLevel}}", jsonUtils.getMetaDataKeyValue("twif", "TopLevel")),
            Map.entry("{{TWIF_SecondLevel}}", jsonUtils.getMetaDataKeyValue("twif", "SecondLevel")),
            Map.entry("{{TWIF_ThirdLevel}}", jsonUtils.getMetaDataKeyValue("twif", "ThirdLevel")),
            Map.entry("{{TWIF_BottomLevel}}", jsonUtils.getMetaDataKeyValue("twif", "BottomLevel")),

            Map.entry("{{TWIF_Force_Priority}}", jsonUtils.getMetaDataKeyValue("twif", "ForcePriority")),
            Map.entry("{{TWIF_Unit_Priority}}", jsonUtils.getMetaDataKeyValue("twif", "UnitPriority")),
            Map.entry("{{TWIF_Year_Priority}}", jsonUtils.getMetaDataKeyValue("twif", "YearPriority")),
            Map.entry("{{TWIF_TopLevel_Priority}}", jsonUtils.getMetaDataKeyValue("twif", "TopLevelPriority")),
            Map.entry("{{TWIF_SecondLevel_Priority}}", jsonUtils.getMetaDataKeyValue("twif", "SecondLevelPriority")),
            Map.entry("{{TWIF_ThirdLevel_Priority}}", jsonUtils.getMetaDataKeyValue("twif", "ThirdLevelPriority")),
            Map.entry("{{TWIF_BottomLevel_Priority}}", jsonUtils.getMetaDataKeyValue("twif", "BottomLevelPriority")),

            Map.entry("{{CM01_itemId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_PTIURN_Number}}", fiveDigitNumber()),
            Map.entry("{{CM01_ASN_Number}}", elevenDigitNumber()),

            Map.entry("{{CM01_Suspect1_Id}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_Suspect2_Id}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_Suspect3_Id}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_Suspect4_Id}}", generateUppercaseAlphaNumeric(12)),

            Map.entry("{{CM01_Def_PersonId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_Def_PersonId_2}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_Def_PersonId_3}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_Def_PersonId_4}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_CaseOffenceId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_CaseOffenceId_2}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_CaseOffenceId_3}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_CaseOffenceId_4}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_CaseOffenceId_5}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_CaseOffenceId_6}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_CaseOffenceId_7}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_CaseOffenceId_8}}", generateUppercaseAlphaNumeric(12)),

            Map.entry("{{CM01_SolicitorId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_SolicitorId_2}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_Ser_ShoulderNo}}", generateUppercaseAlphaNumeric(6)),
            Map.entry("{{CM01_Ser_PersonId}}", generateUppercaseAlphaNumeric(10)),
            Map.entry("{{CM01_Con_ShoulderNo}}", generateUppercaseAlphaNumeric(6)),
            Map.entry("{{CM01_Con_PersonId}}", generateUppercaseAlphaNumeric(10)),

            Map.entry("{{CM01_WitnessRef_1}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_2}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_3}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_Child}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_Police}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_Vulnerable}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_Professional}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_Expert}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_Prisoner}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_Interpreter}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_Intimidated}}", generateUppercaseAlphaNumeric(12)),

            Map.entry("{{CM01_VictimRef_1}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_2}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_3}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Child}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Vulnerable}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Intimidated}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Professional}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Expert}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Prisoner}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Interpreter}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Police}}", generateUppercaseAlphaNumeric(12)),

            Map.entry("{{CM01_VictimWitnessRef_1}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimWitnessRef_2}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimWitnessRef_3}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimWitnessRef_Child}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimWitnessRef_Vulnerable}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimWitnessRef_Intimidated}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimWitnessRef_Professional}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimWitnessRef_Expert}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimWitnessRef_Prisoner}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimWitnessRef_Interpreter}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimWitnessRef_Police}}", generateUppercaseAlphaNumeric(12)),

            Map.entry("{{Suspect1_GivenName}}", firstName()),
            Map.entry("{{Suspect2_GivenName}}", firstName()),
            Map.entry("{{Suspect3_GivenName}}", firstName()),
            Map.entry("{{Suspect4_GivenName}}", firstName()),

            Map.entry("{{Suspect1_SurName}}", lastName()),
            Map.entry("{{Suspect2_SurName}}", lastName()),
            Map.entry("{{Suspect3_SurName}}", lastName()),
            Map.entry("{{Suspect4_SurName}}", lastName()),

            Map.entry("{{Suspect1_Email}}", email()),
            Map.entry("{{Suspect2_Email}}", email()),
            Map.entry("{{Suspect3_Email}}", email()),
            Map.entry("{{Suspect4_Email}}", email()),

            Map.entry("{{Suspect1_Mobile}}", mobilePhone()),
            Map.entry("{{Suspect1_HomePhone}}", homePhone()),

            Map.entry("{{Suspect2_Mobile}}", mobilePhone()),
            Map.entry("{{Suspect2_HomePhone}}", homePhone()),

            Map.entry("{{Suspect3_Mobile}}", mobilePhone()),
            Map.entry("{{Suspect3_HomePhone}}", homePhone()),

            Map.entry("{{Suspect4_Mobile}}", mobilePhone()),
            Map.entry("{{Suspect4_HomePhone}}", homePhone()),

            Map.entry("{{Suspect1_BuildingNo}}", buildingNumber()),
            Map.entry("{{Suspect1_StreetAddress}}", streetAddress()),
            Map.entry("{{Suspect1_City}}", cityName()),
            Map.entry("{{Suspect1_PostCode}}", ukPostCode()),
            Map.entry("{{Suspect1_DoB}}", adultDateOfBirth()),

            Map.entry("{{Suspect2_BuildingNo}}", buildingNumber()),
            Map.entry("{{Suspect2_StreetAddress}}", streetAddress()),
            Map.entry("{{Suspect2_City}}", cityName()),
            Map.entry("{{Suspect2_PostCode}}", ukPostCode()),
            Map.entry("{{Suspect2_DoB}}", adultDateOfBirth()),

            Map.entry("{{Suspect3_BuildingNo}}", buildingNumber()),
            Map.entry("{{Suspect3_StreetAddress}}", streetAddress()),
            Map.entry("{{Suspect3_City}}", cityName()),
            Map.entry("{{Suspect3_PostCode}}", ukPostCode()),
            Map.entry("{{Suspect3_DoB}}", adultDateOfBirth()),

            Map.entry("{{Suspect4_BuildingNo}}", buildingNumber()),
            Map.entry("{{Suspect4_StreetAddress}}", streetAddress()),
            Map.entry("{{Suspect4_City}}", cityName()),
            Map.entry("{{Suspect4_PostCode}}", ukPostCode()),
            Map.entry("{{Suspect4_DoB}}", adultDateOfBirth()),

            Map.entry("{{Child1_DoB}}", childDateOfBirth()),
            Map.entry("{{Child2_DoB}}", childDateOfBirth()),
            Map.entry("{{Child3_DoB}}", childDateOfBirth()),

            Map.entry("{{AdviceRequiredByDate}}", UTCDateThreeMonthsFuture()),
            Map.entry("{{ProposedChargeFromDate}}", UTCDatePastThreeMonths()),
            Map.entry("{{PCDRequestDate}}", UTCDateToday()),
            Map.entry("{{PCDDecisionRequiredByDate}}", UTCDateSixMonthsFuture()),

            Map.entry("{{Offence1_BuildingNo}}", buildingNumber()),
            Map.entry("{{Offence1_StreetAddress}}", streetAddress()),
            Map.entry("{{Offence1_City}}", cityName()),
            Map.entry("{{Offence1_PostCode}}", ukPostCode()),

            Map.entry("{{Offence2_BuildingNo}}", buildingNumber()),
            Map.entry("{{Offence2_StreetAddress}}", streetAddress()),
            Map.entry("{{Offence2_City}}", cityName()),
            Map.entry("{{Offence2_PostCode}}", ukPostCode()),

            Map.entry("{{Offence3_BuildingNo}}", buildingNumber()),
            Map.entry("{{Offence3_StreetAddress}}", streetAddress()),
            Map.entry("{{Offence3_City}}", cityName()),
            Map.entry("{{Offence3_PostCode}}", ukPostCode()),

            Map.entry("{{Offence4_BuildingNo}}", buildingNumber()),
            Map.entry("{{Offence4_StreetAddress}}", streetAddress()),
            Map.entry("{{Offence4_City}}", cityName()),
            Map.entry("{{Offence4_PostCode}}", ukPostCode()),

            Map.entry("{{Solicitor_FirstName}}", firstName()),
            Map.entry("{{Solicitor_lastName}}", lastName()),
            Map.entry("{{Solicitor_email}}", email()),

            Map.entry("{{Solicitor_2_FirstName}}", firstName()),
            Map.entry("{{Solicitor_2_lastName}}", lastName()),
            Map.entry("{{Solicitor_2_email}}", email()),

            Map.entry("{{Defender_Solicitor_Company}}", companyName()),
            Map.entry("{{DEF_FirstName}}", firstName()),
            Map.entry("{{DEF_Surname}}", lastName()),
            Map.entry("{{DEF_1_FirstName}}", firstName()),
            Map.entry("{{DEF_1_Surname}}", lastName()),
            Map.entry("{{DEF_2_FirstName}}", firstName()),
            Map.entry("{{DEF_2_Surname}}", lastName()),
            Map.entry("{{DEF_3_FirstName}}", firstName()),
            Map.entry("{{DEF_3_Surname}}", lastName()),
            Map.entry("{{DEF_4_FirstName}}", firstName()),
            Map.entry("{{DEF_4_Surname}}", lastName()),
            Map.entry("{{DEF_Building_number}}", buildingNumber()),
            Map.entry("{{DEF_Street_Address}}", streetAddress()),
            Map.entry("{{DEF_City}}", cityName()),
            Map.entry("{{DEF_1_Street_Address}}", streetAddress()),
            Map.entry("{{DEF_1_City}}", cityName()),
            Map.entry("{{DEF_1_Postcode}}", ukPostCode()),
            Map.entry("{{DEF_2_Street_Address}}", streetAddress()),
            Map.entry("{{DEF_2_City}}", cityName()),
            Map.entry("{{DEF_2_Postcode}}", ukPostCode()),
            Map.entry("{{DEF_3_Street_Address}}", streetAddress()),
            Map.entry("{{DEF_3_City}}", cityName()),
            Map.entry("{{DEF_3_Postcode}}", ukPostCode()),
            Map.entry("{{DEF_4_Street_Address}}", streetAddress()),
            Map.entry("{{DEF_4_City}}", cityName()),
            Map.entry("{{DEF_4_Postcode}}", ukPostCode()),
            Map.entry("{{DEF_Mobile}}", mobilePhone()),
            Map.entry("{{DEF_HomePhone}}", homePhone()),
            Map.entry("{{DEF_Email}}", email()),
            Map.entry("{{Solicitor_Building_number}}", buildingNumber()),
            Map.entry("{{Solicitor_Street_Address}}", streetAddress()),
            Map.entry("{{Solicitor_HomePhone}}", homePhone()),
            Map.entry("{{Solicitor_City}}", cityName()),
            Map.entry("{{Solicitor_Postcode}}", ukPostCode()),

            Map.entry("{{PoliceOfficer_GivenName}}", firstName()),
            Map.entry("{{PoliceOfficer_SurName}}", lastName()),
            Map.entry("{{PoliceOfficer2_GivenName}}", firstName()),
            Map.entry("{{PoliceOfficer2_Surname}}", lastName()),


            Map.entry("{{Police_Building_number}}", buildingNumber()),
            Map.entry("{{Police_Street_Address}}", streetAddress()),
            Map.entry("{{Police_HomePhone}}", homePhone()),
            Map.entry("{{Police_City}}", cityName()),

            Map.entry("{{PoliceStation_Building_number}}", buildingNumber()),
            Map.entry("{{PoliceStation_Street_Address}}", streetAddress()),
            Map.entry("{{PoliceStation_City}}", cityName()),
            Map.entry("{{PoliceStation_Postcode}}", ukPostCode()),
            Map.entry("{{PoliceStation_PhoneNo}}", homePhone()),


            Map.entry("{{HomePhone}}", homePhone()),
            Map.entry("{{PostCode}}", ukPostCode())
    );

    public PayloadBuilderForCM01() throws IOException {
    }

    public String generateCM01PayloadWithValues(String payloadFileName, ScenarioContext context) throws IOException {
        String modifiedJson = applyReplacements(payloadFileName, caseWithChargeMap);
        assertNoUnresolvedPlaceholders(modifiedJson);
        context.set("CM01ModifiedValues", removeCurlyBracesFromKeys(caseWithChargeMap));
        return modifiedJson;
    }

    private void assertNoUnresolvedPlaceholders(String modifiedJson) {
        String unresolvedPlaceholders = UNRESOLVED_PLACEHOLDER.matcher(modifiedJson)
                .results()
                .map(MatchResult::group)
                .distinct()
                .collect(Collectors.joining(", "));

        if (!unresolvedPlaceholders.isBlank()) {
            throw new IllegalStateException("CM01 payload contains unresolved placeholders: " + unresolvedPlaceholders);
        }
    }
}
