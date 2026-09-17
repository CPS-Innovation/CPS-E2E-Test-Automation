package com.cps.fct.e2e.utils.services.caseCreation.payloadBuilders;

import com.cps.fct.e2e.utils.common.JsonReplacer;
import com.cps.fct.e2e.utils.common.ScenarioContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.FakerUtils.*;

public class PayloadBuilderForLM04 extends JsonReplacer {

    public String generateLM04PayloadWithValues(
            String type, String payloadFileName, ScenarioContext context) throws IOException {

        Map<String, String> cm01Map = context.getAsMap("CM01ModifiedValues");

        Map<String, String> initialLM04Map = new HashMap<>();
        initialLM04Map.put("{{DCF_Force}}", cm01Map.get("DCF_Force"));
        initialLM04Map.put("{{DCF_Unit}}", cm01Map.get("DCF_Unit"));
        initialLM04Map.put("{{DCF_Year}}", cm01Map.get("DCF_Year"));
        initialLM04Map.put("{{DCF_TopLevel}}", cm01Map.get("DCF_TopLevel"));
        initialLM04Map.put("{{DCF_SecondLevel}}", cm01Map.get("DCF_SecondLevel"));
        initialLM04Map.put("{{DCF_ThirdLevel}}", cm01Map.get("DCF_ThirdLevel"));
        initialLM04Map.put("{{DCF_BottomLevel}}", cm01Map.get("DCF_BottomLevel"));

        initialLM04Map.put("{{TWIF_Force}}", cm01Map.get("TWIF_Force"));
        initialLM04Map.put("{{TWIF_Unit}}", cm01Map.get("TWIF_Unit"));
        initialLM04Map.put("{{TWIF_Year}}", cm01Map.get("TWIF_Year"));
        initialLM04Map.put("{{TWIF_TopLevel}}", cm01Map.get("TWIF_TopLevel"));
        initialLM04Map.put("{{TWIF_SecondLevel}}", cm01Map.get("TWIF_SecondLevel"));
        initialLM04Map.put("{{TWIF_ThirdLevel}}", cm01Map.get("TWIF_ThirdLevel"));
        initialLM04Map.put("{{TWIF_BottomLevel}}", cm01Map.get("TWIF_BottomLevel"));

        initialLM04Map.put("{{LM04_itemId}}", generateUppercaseAlphaNumeric(12));
        initialLM04Map.put("{{LM04_PTIURN_Number}}", cm01Map.get("CM01_PTIURN_Number"));
        initialLM04Map.put("{{FirstName}}", firstName());
        initialLM04Map.put("{{MiddleName}}", middleName());
        initialLM04Map.put("{{Surname}}", lastName());
        initialLM04Map.put("{{AddressLin1}}", buildingNumber());
        initialLM04Map.put("{{AddressLin2}}", streetAddress());
        initialLM04Map.put("{{AddressLin3}}", cityName());
        initialLM04Map.put("{{LM04_CaseOffenceId}}", cm01Map.get("CM01_CaseOffenceId"));
        initialLM04Map.put("{{LM04_Con_ShoulderNo}}", cm01Map.get("CM01_Con_ShoulderNo"));

        initialLM04Map.put("{{LM04_Victim_Witness_Ref}}", generateUppercaseAlphaNumeric(12));

        return applyReplacements(payloadFileName, initialLM04Map);

    }



}



