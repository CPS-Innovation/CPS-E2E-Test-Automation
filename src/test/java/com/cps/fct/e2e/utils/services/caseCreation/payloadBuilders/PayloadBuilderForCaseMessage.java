package com.cps.fct.e2e.utils.services.caseCreation.payloadBuilders;

import com.cps.fct.e2e.utils.common.JsonReplacer;
import com.cps.fct.e2e.utils.common.ScenarioContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.cps.fct.e2e.utils.common.FakerUtils.generateUppercaseAlphaNumeric;

public class PayloadBuilderForCaseMessage extends JsonReplacer {

    private static final Pattern UNRESOLVED_PLACEHOLDER = Pattern.compile("\\{\\{[^}]+}}");

    public String generatePayloadWithValues(String payload, String messageType, ScenarioContext context)
            throws IOException {
        Map<String, String> replacements = new HashMap<>();

        putAll(replacements, context.getAsMap("CM01ModifiedValues"));
        putAll(replacements, context.getAsMap("LM04ModifiedValues"));

        replacements.put("{{DCF_CaseId}}", context.getAsString("caseId"));
        replacements.put("{{LM05_itemId}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{LM05_MaterialId}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{LM06_Unused_itemId}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{LM06_Unused_MaterialId}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{LM07_itemId}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{LM07_MaterialId}}", generateUppercaseAlphaNumeric(12));
        replacements.put("{{CJ05_itemId}}", generateUppercaseAlphaNumeric(12));

        String modifiedJson = applyReplacements(payload, replacements);
        assertNoUnresolvedPlaceholders(messageType, modifiedJson);
        context.set(messageType.toUpperCase() + "ModifiedValues", removeCurlyBracesFromKeys(replacements));
        return modifiedJson;
    }

    private void putAll(Map<String, String> replacements, Map<String, String> values) {
        if (values == null) {
            return;
        }
        values.forEach((key, value) -> replacements.put("{{" + key + "}}", value));
    }

    private void assertNoUnresolvedPlaceholders(String messageType, String modifiedJson) {
        String unresolvedPlaceholders = UNRESOLVED_PLACEHOLDER.matcher(modifiedJson)
                .results()
                .map(MatchResult::group)
                .distinct()
                .collect(Collectors.joining(", "));

        if (!unresolvedPlaceholders.isBlank()) {
            throw new IllegalStateException(
                    messageType + " payload contains unresolved placeholders: " + unresolvedPlaceholders);
        }
    }
}
