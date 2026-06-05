package com.cps.fct.e2e.utils.payloadBuilders.twif;

import com.cps.fct.e2e.utils.common.JsonReplacer;
import com.cps.fct.e2e.utils.common.JsonUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import net.masterthought.cucumber.generators.StepsOverviewPage;

import java.io.IOException;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.DateTimeUtils.pcdDecisionByDate;
import static com.cps.fct.e2e.utils.common.DateTimeUtils.pcdRequestDate;
import static com.cps.fct.e2e.utils.common.FakerUtils.*;

public class TWIFPayloadBuilderForCM01 extends JsonReplacer {
    JsonUtils jsonUtils = new JsonUtils();
    private final Map<String, String> caseWithChargeMap = Map.ofEntries(
            Map.entry("{{TWIF_Force}}", jsonUtils.getMetaDataKeyValue("twif", "Force")),
            Map.entry("{{TWIF_Unit}}", jsonUtils.getMetaDataKeyValue("twif", "Unit")),
            Map.entry("{{TWIF_Year}}", jsonUtils.getMetaDataKeyValue("twif", "Year")),

            Map.entry("{{TWIF_TopLevel}}", jsonUtils.getMetaDataKeyValue("twif", "TopLevel")),
            Map.entry("{{TWIF_SecondLevel}}", jsonUtils.getMetaDataKeyValue("twif", "SecondLevel")),
            Map.entry("{{TWIF_ThirdLevel}}", jsonUtils.getMetaDataKeyValue("twif", "ThirdLevel")),
            Map.entry("{{TWIF_BottomLevel}}", jsonUtils.getMetaDataKeyValue("twif", "BottomLevel")),

            Map.entry("{{CM01_itemId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_PTIURN_Number}}", fiveDigitNumber()),
            Map.entry("{{CM01_ASN_Number}}", elevenDigitNumber()),
            Map.entry("{{CM01_Suspect_UniqueId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_CaseOffenceId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_Officer_UniqueId}}", generateUppercaseAlphaNumeric(12)),
            Map.entry("{{CM01_Supervising_UniqueId}}", generateUppercaseAlphaNumeric(6)),
            Map.entry("{{CM01_Officer1_FirstName}}", firstName()),
            Map.entry("{{CM01_Officer1_SurName}}", lastName()),
            Map.entry("{{CM01_Officer2_FirstName}}", firstName()),
            Map.entry("{{CM01_Officer2_SurName}}", lastName()),
            Map.entry("{{DEF_FirstName}}", firstName()),
            Map.entry("{{DEF_Surname}}",lastName()),
            Map.entry("{{CM01_PCD_Request_Date}}", pcdRequestDate()),
            Map.entry("{{CM01_PCD_Decision_By_Date}}", pcdDecisionByDate()),
            Map.entry("{{DEF_Building_number}}", buildingNumber()),
            Map.entry("{{DEF_Street_Address}}", streetAddress()),
            Map.entry("{{DEF_City}}", cityName()),
            Map.entry("{{DEF_Mobile}}", mobilePhone()),
            Map.entry("{{DEF_HomePhone}}", homePhone()),
            Map.entry("{{DEF_Email}}", email()),
            Map.entry("{{CM01_Ser_ShoulderNo}}", generateUppercaseAlphaNumeric(6)),
            Map.entry("{{CM01_Ser_PersonId}}", generateUppercaseAlphaNumeric(10)),
            Map.entry("{{CM01_Con_ShoulderNo}}", generateUppercaseAlphaNumeric(6)),
            Map.entry("{{CM01_Con_PersonId}}", generateUppercaseAlphaNumeric(10))
    );

    public TWIFPayloadBuilderForCM01() throws IOException {
    }

    public String generatePayloadWithValues(String payloadFileName, ScenarioContext context) throws IOException {
        String modifiedJson = applyReplacements(payloadFileName, caseWithChargeMap);
        extractCaseUrnAndSave(context);
        return modifiedJson;
    }

    private void extractCaseUrnAndSave(ScenarioContext context) {
        Map<String, String> caseDataMap = removeCurlyBracesFromKeys(caseWithChargeMap);
        String casePtr = caseDataMap.get("CM01_PTIURN_Number");
        String force =  caseDataMap.get("TWIF_Force");
        String unit =  caseDataMap.get("TWIF_Unit");
        String year =  caseDataMap.get("TWIF_Year");
        String casePtrUrn = force+unit+casePtr+year;
        context.set("caseUrn", String.format(casePtrUrn));
    }
}
