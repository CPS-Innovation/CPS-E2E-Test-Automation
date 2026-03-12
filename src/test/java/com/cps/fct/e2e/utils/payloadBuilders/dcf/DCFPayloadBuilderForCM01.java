package com.cps.fct.e2e.utils.payloadBuilders.dcf;

import com.cps.fct.e2e.utils.common.JsonReplacer;
import com.cps.fct.e2e.utils.common.JsonUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.FakerUtils.*;

public class DCFPayloadBuilderForCM01 extends JsonReplacer {

    JsonUtils jsonUtils = new JsonUtils();
    private final Map<String, String> caseWithChargeMap = Map.ofEntries(
            Map.entry("{{DCF_Force}}", jsonUtils.getMetaDataKeyValue("dcf", "Force")),
            Map.entry("{{DCF_Unit}}", jsonUtils.getMetaDataKeyValue("dcf", "Unit")),
            Map.entry("{{DCF_Year}}", jsonUtils.getMetaDataKeyValue("dcf", "Year")),

            Map.entry("{{DCF_TopLevel}}", jsonUtils.getMetaDataKeyValue("dcf", "TopLevel")),
            Map.entry("{{DCF_SecondLevel}}", jsonUtils.getMetaDataKeyValue("dcf", "SecondLevel")),
            Map.entry("{{DCF_ThirdLevel}}", jsonUtils.getMetaDataKeyValue("dcf", "ThirdLevel")),
            Map.entry("{{DCF_BottomLevel}}", jsonUtils.getMetaDataKeyValue("dcf", "BottomLevel")),

            Map.entry("{{CM01_itemId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_PTIURN_Number}}", fiveDigitNumber()),
            Map.entry("{{CM01_Def_PersonId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_Def_PersonId_2}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_Def_PersonId_3}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_CaseOffenceId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_CaseOffenceId_2}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_CaseOffenceId_3}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_SolicitorId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_SolicitorId_2}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_Ser_ShoulderNo}}", generateUppercaseAlphaNumeric(6)),
            Map.entry("{{CM01_Ser_PersonId}}", generateUppercaseAlphaNumeric(10)),
            Map.entry("{{CM01_Con_ShoulderNo}}", generateUppercaseAlphaNumeric(6)),
            Map.entry("{{CM01_Con_PersonId}}", generateUppercaseAlphaNumeric(10)),

            Map.entry("{{CM01_WitnessRef_1}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_2}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessDetailsRef_1}}", generateUppercaseAlphaNumeric(12)),
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
            Map.entry("{{CM01_VictimDetailsRef_1}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Child}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Vulnerable}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Intimidated}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Professional}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Expert}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Prisoner}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Interpreter}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Police}}", generateUppercaseAlphaNumeric(12)),

            Map.entry("{{PoliceOfficer_GivenName}}", firstName()),
            Map.entry("{{PoliceOfficer_SurName}}", lastName()),
            Map.entry("{{PoliceOfficer2_GivenName}}", firstName()),
            Map.entry("{{PoliceOfficer2_Surname}}", lastName()),
            Map.entry("{{Solicitor_FirstName}}", firstName()),
            Map.entry("{{Solicitor_lastName}}", lastName()),
            Map.entry("{{Solicitor_email}}", email()),
            Map.entry("{{Solicitor_2_FirstName}}", firstName()),
            Map.entry("{{Solicitor_2_lastName}}", lastName()),
            Map.entry("{{Solicitor_2_email}}", email()),
            Map.entry("{{Defender_Solicitor_Company}}", companyName()),
            Map.entry("{{DEF_FirstName}}", firstName()),
            Map.entry("{{DEF_Surname}}", lastName()),
            Map.entry("{{DEF_2_FirstName}}", firstName()),
            Map.entry("{{DEF_2_Surname}}", lastName()),
            Map.entry("{{DEF_3_FirstName}}", firstName()),
            Map.entry("{{DEF_3_Surname}}", lastName()),
            Map.entry("{{DEF_Building_number}}", buildingNumber()),
            Map.entry("{{DEF_Street_Address}}", streetAddress()),
            Map.entry("{{DEF_City}}", cityName()),
            Map.entry("{{DEF_Mobile}}", mobilePhone()),
            Map.entry("{{DEF_HomePhone}}", homePhone()),
            Map.entry("{{DEF_Email}}", email())
    );

    public DCFPayloadBuilderForCM01() throws IOException {
    }

    public String generatePayloadWithValues(String payloadFileName, ScenarioContext context) throws IOException {
        String modifiedJson = applyReplacements(payloadFileName, caseWithChargeMap);
        context.set("CM01ModifiedValues", removeCurlyBracesFromKeys(caseWithChargeMap));
        return modifiedJson;
    }
}
