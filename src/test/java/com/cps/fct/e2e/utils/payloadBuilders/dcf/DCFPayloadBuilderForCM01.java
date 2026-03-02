package com.cps.fct.e2e.utils.payloadBuilders.dcf;

import com.cps.fct.e2e.utils.common.JsonReplacer;
import com.cps.fct.e2e.utils.common.ScenarioContext;

import java.io.IOException;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.FakerUtils.*;

public class DCFPayloadBuilderForCM01 extends JsonReplacer {


    private static final Map<String, String> caseWithChargeMap = Map.ofEntries(
            Map.entry("{{CM01_itemId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_PTIURN_Number}}", fiveDigitNumber()),
            Map.entry("{{CM01_Def_PersonId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_CaseOffenceId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_SolicitorId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_Ser_ShoulderNo}}", generateUppercaseAlphaNumeric(6)),
            Map.entry("{{CM01_Ser_PersonId}}", generateUppercaseAlphaNumeric(10)),
            Map.entry("{{CM01_Con_ShoulderNo}}", generateUppercaseAlphaNumeric(6)),
            Map.entry("{{CM01_Con_PersonId}}", generateUppercaseAlphaNumeric(10)),
            Map.entry("{{CM01_WitnessRef_1}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_Child}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_Police}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_Vulnerable}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_WitnessRef_Intimidated}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_1}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Child}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Vulnerable}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_VictimRef_Intimidated}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{PoliceOfficer_GivenName}}", firstName()),
            Map.entry("{{PoliceOfficer_SurName}}", lastName()),
            Map.entry("{{PoliceOfficer2_GivenName}}", firstName()),
            Map.entry("{{PoliceOfficer2_Surname}}", lastName()),
            Map.entry("{{Solicitor_FirstName}}", firstName()),
            Map.entry("{{Solicitor_lastName}}", lastName()),
            Map.entry("{{Solicitor_email}}", email()),
            Map.entry("{{Defender_Solicitor_Company}}", companyName()),
            Map.entry("{{DEF_FirstName}}", firstName()),
            Map.entry("{{DEF_Surname}}", lastName()),
            Map.entry("{{DEF_Building_number}}", buildingNumber()),
            Map.entry("{{DEF_Street_Address}}", streetAddress())

    );

    public String generatePayloadWithValues(String payloadFileName, ScenarioContext context) throws IOException {
        String modifiedJson = applyReplacements(payloadFileName, caseWithChargeMap);
        context.set("CM01ModifiedValues", removeCurlyBracesFromKeys(caseWithChargeMap));
        return modifiedJson;
    }
}
