package com.cps.fct.e2e.utils.jsonMerge;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class JsonMergeUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Merge overrides into base JSON using dotted paths + [index] syntax,
     * in STRICT mode:
     *
     * - All intermediate segments (objects/arrays) MUST already exist
     * - Only the final field / array element is allowed to be created/overwritten
     *
     * Example override keys:
     *  "PreChargeDecisionRequest.PTIURN.Year": "26"
     *  "PreChargeDecisionRequest.Suspect[0].Person.DateOfBirth": "1990-01-01"
     */

    public static File mergeToTempFile(File baseFile, File overridesFile) throws IOException {

        System.out.println(">> mergeToTempFile baseFile      = " + baseFile.getAbsolutePath());
        System.out.println(">> mergeToTempFile overridesFile = " + overridesFile.getAbsolutePath());

        // Read base JSON (LM04 witness/victim template)
        ObjectNode baseJson = (ObjectNode) MAPPER.readTree(baseFile);
        System.out.println(">> baseJson root field names:");
        baseJson.fieldNames().forEachRemaining(System.out::println);

        // Read overrides JSON (flat map of paths → values)
        ObjectNode overridesRoot = (ObjectNode) MAPPER.readTree(overridesFile);
        System.out.println(">> overrides root field names:");
        overridesRoot.fieldNames().forEachRemaining(System.out::println);

        // Apply each override path strictly onto baseJson
        Iterator<Map.Entry<String, JsonNode>> fields = overridesRoot.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String path = entry.getKey();      // e.g. "WitnessOrVictim.PTIURN.Force"
            JsonNode value = entry.getValue(); // e.g. "35"

            setDeepStrict(baseJson, path, value);
        }

        // Write merged JSON to temp file
        String mergedJsonString = MAPPER.writeValueAsString(baseJson);

        Path tempFilePath = Files.createTempFile("merged-", ".json");
        Files.writeString(tempFilePath, mergedJsonString, StandardCharsets.UTF_8);

        return tempFilePath.toFile();
    }

    /**
     * Strict path application:
     * - All intermediate segments MUST exist (no auto-creation of objects/arrays)
     * - Final field/element may be created or overwritten
     */
    private static void setDeepStrict(ObjectNode root, String path, JsonNode value) {
        String[] parts = path.split("\\.");
        JsonNode current = root;

        // Traverse all but the last segment
        for (int i = 0; i < parts.length - 1; i++) {
            PathToken token = parseToken(parts[i]);

            if (!(current instanceof ObjectNode)) {
                throw new IllegalArgumentException(
                        "Segment '" + parts[i] + "' expects an object but found: " + current.getNodeType());
            }

            ObjectNode currentObject = (ObjectNode) current;
            JsonNode child = currentObject.get(token.field);

            if (child == null) {
                throw new IllegalArgumentException(
                        "Segment '" + token.field + "' does not exist while traversing '" + path + "'");
            }

            if (token.index == null) {
                // Expecting object segment
                if (!(child instanceof ObjectNode)) {
                    throw new IllegalArgumentException(
                            "Segment '" + token.field + "' is not an object in path '" + path + "'");
                }
                current = child;
            } else {
                // Expecting array segment: field must be an array and index must exist
                if (!(child instanceof ArrayNode)) {
                    throw new IllegalArgumentException(
                            "Segment '" + token.field + "' is not an array in path '" + path + "'");
                }
                ArrayNode arrayNode = (ArrayNode) child;
                int idx = token.index;
                if (idx < 0 || idx >= arrayNode.size()) {
                    throw new IllegalArgumentException(
                            "Index " + idx + " out of bounds for array '" + token.field
                                    + "' (size=" + arrayNode.size() + ") in path '" + path + "'");
                }
                JsonNode element = arrayNode.get(idx);
                if (element == null) {
                    throw new IllegalArgumentException(
                            "Null element at index " + idx + " in array '" + token.field + "' for path '" + path + "'");
                }
                current = element;
            }
        }

        // Handle last segment: we allow creating/overwriting the final field/element
        PathToken lastToken = parseToken(parts[parts.length - 1]);

        if (!(current instanceof ObjectNode)) {
            throw new IllegalArgumentException(
                    "Final segment '" + lastToken.field + "' expects an object parent but found: " + current.getNodeType());
        }

        ObjectNode parentObject = (ObjectNode) current;

        if (lastToken.index == null) {
            // Final is a simple field on an object – allow create/overwrite
            parentObject.set(lastToken.field, value);
        } else {
            // Final is an array element: parentObject must already have an ArrayNode at field
            JsonNode node = parentObject.get(lastToken.field);
            if (!(node instanceof ArrayNode)) {
                throw new IllegalArgumentException(
                        "Final segment '" + lastToken.field + "' is not an array in path '" + path + "'");
            }
            ArrayNode arrayNode = (ArrayNode) node;
            int idx = lastToken.index;
            if (idx < 0 || idx >= arrayNode.size()) {
                throw new IllegalArgumentException(
                        "Final index " + idx + " out of bounds for array '" + lastToken.field
                                + "' (size=" + arrayNode.size() + ") in path '" + path + "'");
            }
            arrayNode.set(idx, value);
        }
    }

    /**
     * Token representation: "Suspect" or "Suspect[0]"
     */
    private static class PathToken {
        final String field;
        final Integer index; // null if not an array segment

        PathToken(String field, Integer index) {
            this.field = field;
            this.index = index;
        }
    }

    /**
     * Parse a segment like:
     *  "Suspect"     -> field="Suspect", index=null
     *  "Suspect[0]"  -> field="Suspect", index=0
     */
    private static PathToken parseToken(String part) {
        int bracketStart = part.indexOf('[');
        if (bracketStart == -1) {
            return new PathToken(part, null);
        }

        String fieldName = part.substring(0, bracketStart);
        int bracketEnd = part.indexOf(']', bracketStart);
        if (bracketEnd == -1) {
            throw new IllegalArgumentException("Invalid array token (missing ']'): '" + part + "'");
        }

        String indexStr = part.substring(bracketStart + 1, bracketEnd);
        int index;
        try {
            index = Integer.parseInt(indexStr);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid array index in segment '" + part + "'", ex);
        }

        return new PathToken(fieldName, index);
    }

    public static String resolveDcfMetaDataType(String caseDataType) {
        String lower = caseDataType.toLowerCase(Locale.ROOT).trim();

        if (lower.startsWith("witness")) {
            return "dcf witness meta data";
        }
        if (lower.startsWith("victim")) {
            return "dcf victim meta data";
        }

        throw new IllegalArgumentException(
                "Unsupported LM04 caseDataType: '" + caseDataType +
                        "'. Expected string starting with 'witness' or 'victim'.");
    }
}
