package com.cps.fct.e2e.utils.payloadBuilders.dcf;

import com.cps.fct.e2e.model.CaseResponse;
import com.cps.fct.e2e.utils.common.JsonReplacer;
import com.cps.fct.e2e.utils.common.ScenarioContext;

import java.io.IOException;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.FakerUtils.*;

public class DCFPayloadBuilderForLM04 extends JsonReplacer {

    public String generatePayloadWithValues(
            String type, String payloadFileName, ScenarioContext context) throws IOException {

        Map<String, String> cm01Map = context.getAsMap("CM01ModifiedValues");
        CaseResponse caseData = context.getCastClazz("caseDetails", CaseResponse.class);
        Map<String, String> initialLM04Map = new java.util.HashMap<>(Map.of(
                "{{LM04_CaseId}}", caseData.getCaseId(),
                "{{LM04_PTIURN_Number}}", cm01Map.get("CM01_PTIURN_Number"),
                "{{LM04_Force}}", cm01Map.get("DCF_Force"),
                "{{LM04_Unit}}", cm01Map.get("DCF_Unit"),
                "{{LM04_Year}}", cm01Map.get("DCF_Year"),
                "{{LM04_itemId}}",generateUppercaseAlphaNumeric(12),
                "{{FirstName}}",firstName(),
                "{{MiddleName}}",middleName(),
                "{{Surname}}",lastName(),
                "{{LM04_CaseOffenceId}}", cm01Map.get("CM01_CaseOffenceId")));


        switch (type.toLowerCase()) {
            case "witness":
                initialLM04Map.put("{{LM04_WitnessRef_1}}", cm01Map.get("CM01_WitnessRef_1"));
                break;
            case "witness intimidated":
                initialLM04Map.put("{{LM04_WitnessRef_intimidated}}", cm01Map.get("CM01_WitnessRef_Intimidated"));
                break;
            case "witness child":
                initialLM04Map.put("{{LM04_WitnessRef_child}}", cm01Map.get("CM01_WitnessRef_Child"));
                break;
            case "witness vulnerable":
                initialLM04Map.put("{{LM04_WitnessRef_vulnerable}}", cm01Map.get("CM01_WitnessRef_Vulnerable"));
                break;
            case "witness professional":
                initialLM04Map.put("{{LM04_WitnessRef_professional}}", cm01Map.get("CM01_WitnessRef_Professional"));
                break;
            case "witness expert":
                initialLM04Map.put("{{LM04_WitnessRef_expert}}", cm01Map.get("CM01_WitnessRef_Expert"));
                break;
            case "witness prisoner":
                initialLM04Map.put("{{LM04_WitnessRef_prisoner}}", cm01Map.get("CM01_WitnessRef_Prisoner"));
                break;
            case "witness interpreter":
                initialLM04Map.put("{{LM04_WitnessRef_interpreter}}", cm01Map.get("CM01_WitnessRef_Interpreter"));
                break;
            case "witness police":
                initialLM04Map.put("{{LM04_WitnessRef_police}}", cm01Map.get("CM01_WitnessRef_Police"));
                initialLM04Map.put("{{LM04_Con_ShoulderNo}}", cm01Map.get("CM01_Con_ShoulderNo"));
                break;

            case "victim":
                initialLM04Map.put("{{LM04_VictimRef_1}}", cm01Map.get("CM01_VictimRef_1"));
                break;
            case "victim witness":
                initialLM04Map.put("{{LM04_VictimWitnessRef_1}}", cm01Map.get("CM01_VictimWitnessRef_1"));
                break;
            default:
                break;
        }
        return applyReplacements(payloadFileName, initialLM04Map);
    }

}



