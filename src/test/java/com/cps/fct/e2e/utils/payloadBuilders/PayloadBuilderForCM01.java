package com.cps.fct.e2e.utils.payloadBuilders;

import com.cps.fct.e2e.utils.common.JsonReplacer;
import com.cps.fct.e2e.utils.common.JsonUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.cps.fct.e2e.utils.common.FakerUtils.*;

public class PayloadBuilderForCM01 extends JsonReplacer {

    private static final Pattern UNRESOLVED_PLACEHOLDER = Pattern.compile("\\{\\{[^}]+}}");

    JsonUtils jsonUtils = new JsonUtils();
    private final Map<String, String> caseWithChargeMap = caseWithChargeMap();

    // Keep this as explicit puts rather than Map.ofEntries: this map has enough entries that a
    // single large generic vararg call slows IDE/compiler analysis and triggers inspection warnings.
    private Map<String, String> caseWithChargeMap() throws IOException {
        Map<String, String> replacements = new LinkedHashMap<>();

        replacements.put("{{DCF_Force}}", jsonUtils.getMetaDataKeyValue("dcf", "Force"));
        replacements.put("{{DCF_Unit}}", jsonUtils.getMetaDataKeyValue("dcf", "Unit"));
        replacements.put("{{DCF_Year}}", jsonUtils.getMetaDataKeyValue("dcf", "Year"));
        replacements.put("{{DCF_TopLevel}}", jsonUtils.getMetaDataKeyValue("dcf", "TopLevel"));
        replacements.put("{{DCF_SecondLevel}}", jsonUtils.getMetaDataKeyValue("dcf", "SecondLevel"));
        replacements.put("{{DCF_ThirdLevel}}", jsonUtils.getMetaDataKeyValue("dcf", "ThirdLevel"));
        replacements.put("{{DCF_BottomLevel}}", jsonUtils.getMetaDataKeyValue("dcf", "BottomLevel"));

        replacements.put("{{TWIF_Force}}", jsonUtils.getMetaDataKeyValue("twif", "Force"));
        replacements.put("{{TWIF_Unit}}", jsonUtils.getMetaDataKeyValue("twif", "Unit"));
        replacements.put("{{TWIF_Year}}", jsonUtils.getMetaDataKeyValue("twif", "Year"));
        replacements.put("{{TWIF_TopLevel}}", jsonUtils.getMetaDataKeyValue("twif", "TopLevel"));
        replacements.put("{{TWIF_SecondLevel}}", jsonUtils.getMetaDataKeyValue("twif", "SecondLevel"));
        replacements.put("{{TWIF_ThirdLevel}}", jsonUtils.getMetaDataKeyValue("twif", "ThirdLevel"));
        replacements.put("{{TWIF_BottomLevel}}", jsonUtils.getMetaDataKeyValue("twif", "BottomLevel"));

        replacements.put("{{TWIF_Force_Priority}}", jsonUtils.getMetaDataKeyValue("twif", "ForcePriority"));
        replacements.put("{{TWIF_Unit_Priority}}", jsonUtils.getMetaDataKeyValue("twif", "UnitPriority"));
        replacements.put("{{TWIF_Year_Priority}}", jsonUtils.getMetaDataKeyValue("twif", "YearPriority"));
        replacements.put("{{TWIF_TopLevel_Priority}}", jsonUtils.getMetaDataKeyValue("twif", "TopLevelPriority"));
        replacements.put("{{TWIF_SecondLevel_Priority}}", jsonUtils.getMetaDataKeyValue("twif", "SecondLevelPriority"));
        replacements.put("{{TWIF_ThirdLevel_Priority}}", jsonUtils.getMetaDataKeyValue("twif", "ThirdLevelPriority"));
        replacements.put("{{TWIF_BottomLevel_Priority}}", jsonUtils.getMetaDataKeyValue("twif", "BottomLevelPriority"));

        replacements.put("{{CM01_itemId}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_PTIURN_Number}}", fiveDigitNumber());
        replacements.put("{{CM01_ASN_Number}}", elevenDigitNumber());
        replacements.put("{{CM01_Def_PersonId}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_Def_PersonId_2}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_Def_PersonId_3}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_Def_PersonId_4}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_CaseOffenceId}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_CaseOffenceId_2}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_CaseOffenceId_3}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_CaseOffenceId_4}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_CaseOffenceId_5}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_CaseOffenceId_6}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_CaseOffenceId_7}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_CaseOffenceId_8}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_SolicitorId}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_SolicitorId_2}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_Ser_ShoulderNo}}", generateUppercaseAlphaNumeric(6));
        replacements.put("{{CM01_Ser_PersonId}}", generateUppercaseAlphaNumeric(10));
        replacements.put("{{CM01_Con_ShoulderNo}}", generateUppercaseAlphaNumeric(6));
        replacements.put("{{CM01_Con_PersonId}}", generateUppercaseAlphaNumeric(10));

        replacements.put("{{CM01_WitnessRef_1}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_WitnessRef_2}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_WitnessDetailsRef_1}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_WitnessRef_Child}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_WitnessRef_Police}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_WitnessRef_Vulnerable}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_WitnessRef_Professional}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_WitnessRef_Expert}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_WitnessRef_Prisoner}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_WitnessRef_Interpreter}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_WitnessRef_Intimidated}}", generateUppercaseAlphaNumeric(12));

        replacements.put("{{CM01_VictimRef_1}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_VictimRef_2}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_VictimDetailsRef_1}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_VictimRef_Child}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_VictimRef_Vulnerable}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_VictimRef_Intimidated}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_VictimRef_Professional}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_VictimRef_Expert}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_VictimRef_Prisoner}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_VictimRef_Interpreter}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CM01_VictimRef_Police}}", generateUppercaseAlphaNumeric(12));

        replacements.put("{{PoliceOfficer_GivenName}}", firstName());
        replacements.put("{{PoliceOfficer_SurName}}", lastName());
        replacements.put("{{PoliceOfficer2_GivenName}}", firstName());
        replacements.put("{{PoliceOfficer2_Surname}}", lastName());
        replacements.put("{{Solicitor_FirstName}}", firstName());
        replacements.put("{{Solicitor_lastName}}", lastName());
        replacements.put("{{Solicitor_email}}", email());
        replacements.put("{{Solicitor_2_FirstName}}", firstName());
        replacements.put("{{Solicitor_2_lastName}}", lastName());
        replacements.put("{{Solicitor_2_email}}", email());
        replacements.put("{{Defender_Solicitor_Company}}", companyName());
        replacements.put("{{DEF_FirstName}}", firstName());
        replacements.put("{{DEF_Surname}}", lastName());
        replacements.put("{{DEF_1_FirstName}}", firstName());
        replacements.put("{{DEF_1_Surname}}", lastName());
        replacements.put("{{DEF_2_FirstName}}", firstName());
        replacements.put("{{DEF_2_Surname}}", lastName());
        replacements.put("{{DEF_3_FirstName}}", firstName());
        replacements.put("{{DEF_3_Surname}}", lastName());
        replacements.put("{{DEF_4_FirstName}}", firstName());
        replacements.put("{{DEF_4_Surname}}", lastName());
        replacements.put("{{DEF_Building_number}}", buildingNumber());
        replacements.put("{{DEF_Street_Address}}", streetAddress());
        replacements.put("{{DEF_City}}", cityName());
        replacements.put("{{DEF_1_Street_Address}}", streetAddress());
        replacements.put("{{DEF_1_City}}", cityName());
        replacements.put("{{DEF_1_Postcode}}", ukPostCode());
        replacements.put("{{DEF_2_Street_Address}}", streetAddress());
        replacements.put("{{DEF_2_City}}", cityName());
        replacements.put("{{DEF_2_Postcode}}", ukPostCode());
        replacements.put("{{DEF_3_Street_Address}}", streetAddress());
        replacements.put("{{DEF_3_City}}", cityName());
        replacements.put("{{DEF_3_Postcode}}", ukPostCode());
        replacements.put("{{DEF_4_Street_Address}}", streetAddress());
        replacements.put("{{DEF_4_City}}", cityName());
        replacements.put("{{DEF_4_Postcode}}", ukPostCode());
        replacements.put("{{DEF_Mobile}}", mobilePhone());
        replacements.put("{{DEF_HomePhone}}", homePhone());
        replacements.put("{{DEF_Email}}", email());
        replacements.put("{{Soli_Building_number}}", buildingNumber());
        replacements.put("{{Soli_Street_Address}}", streetAddress());
        replacements.put("{{Soli_HomePhone}}", homePhone());
        replacements.put("{{Soli_City}}", cityName());
        replacements.put("{{Police_Building_number}}", buildingNumber());
        replacements.put("{{Police_Street_Address}}", streetAddress());
        replacements.put("{{Police_HomePhone}}", homePhone());
        replacements.put("{{Police_City}}", cityName());

        return replacements;
    }

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
