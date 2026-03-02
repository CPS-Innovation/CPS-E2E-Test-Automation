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

//    public static File mergeToTempFile(File baseFile, File overridesFile) throws IOException {
//        ObjectNode baseJson = (ObjectNode) MAPPER.readTree(baseFile);
//        ObjectNode overridesJson = (ObjectNode) MAPPER.readTree(overridesFile);
//
//        Iterator<Map.Entry<String, JsonNode>> fields = overridesJson.fields();
//        while (fields.hasNext()) {
//            Map.Entry<String, JsonNode> entry = fields.next();
//            String path = entry.getKey();
//            JsonNode value = entry.getValue();
//
//            try {
//                setDeepStrict(baseJson, path, value);
//            } catch (IllegalArgumentException ex) {
//                // Fail fast with clear message if structure does not match the path
//                throw new IllegalStateException(
//                        "Failed to apply override path '" + path + "': " + ex.getMessage(), ex);
//            }
//        }
//
//        // (Optional) debugging – remove or switch to logger when happy
//        JsonNode ptiurn = baseJson
//                .path("PreChargeDecisionRequest")
//                .path("PTIURN");
//        System.out.println("PTIURN after merge:\n" + ptiurn.toPrettyString());
//
//        JsonNode suspect0Person = baseJson
//                .path("PreChargeDecisionRequest")
//                .path("Suspect")
//                .path(0)
//                .path("Person");
//        System.out.println("Suspect[0].Person after merge:\n" + suspect0Person.toPrettyString());
//
//        String mergedJsonString = MAPPER.writeValueAsString(baseJson);
//
//        Path tempFilePath = Files.createTempFile("merged-case-", ".json");
//        Files.writeString(tempFilePath, mergedJsonString, StandardCharsets.UTF_8);
//
//        System.out.println("Merged JSON:\n" + mergedJsonString);
//
//        return tempFilePath.toFile();
//    }

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

//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.Iterator;
//import java.util.Map;
//
//public class JsonMergeUtil {
//
//    private static final ObjectMapper MAPPER = new ObjectMapper();
//
//    /**
//     * Merge overrides into base JSON using dotted paths, including array indices, e.g.:
//     *
//     *  "PreChargeDecisionRequest.PTIURN.Year": "26"
//     *  "PreChargeDecisionRequest.Suspect[0].Person.DateOfBirth": "1990-01-01"
//     */
//    public static File mergeToTempFile(File baseFile, File overridesFile) throws IOException {
//        ObjectNode baseJson = (ObjectNode) MAPPER.readTree(baseFile);
//        ObjectNode overridesJson = (ObjectNode) MAPPER.readTree(overridesFile);
//
//        Iterator<Map.Entry<String, JsonNode>> fields = overridesJson.fields();
//        while (fields.hasNext()) {
//            Map.Entry<String, JsonNode> entry = fields.next();
//            String path = entry.getKey();
//            JsonNode value = entry.getValue();
//
//            setDeep(baseJson, path, value);
//        }
//
//
//        String mergedJsonString = MAPPER.writeValueAsString(baseJson);
//
//        Path tempFilePath = Files.createTempFile("merged-case-", ".json");
//        Files.writeString(tempFilePath, mergedJsonString, StandardCharsets.UTF_8);
//
//        return tempFilePath.toFile();
//    }
//
//    /**
//     * Set a value into a JSON tree using a dotted path with optional [index] array notation.
//     *
//     * Examples:
//     *  PreChargeDecisionRequest.PTIURN.Year
//     *  PreChargeDecisionRequest.Suspect[0].Person.DateOfBirth
//     */
//    private static void setDeep(ObjectNode root, String path, JsonNode value) {
//        String[] parts = path.split("\\.");
//        JsonNode current = root;
//
//        // Walk all but last part, ensuring structure exists
//        for (int i = 0; i < parts.length - 1; i++) {
//            PathToken token = parseToken(parts[i]);
//
//            if (token.index == null) {
//                // Object field access
//                current = ensureObjectField((ObjectNode) current, token.field);
//            } else {
//                // Array field access
//                current = ensureArrayElement((ObjectNode) current, token.field, token.index);
//            }
//        }
//
//        // Handle last part: set the value
//        PathToken lastToken = parseToken(parts[parts.length - 1]);
//
//        if (lastToken.index == null) {
//            // Final is an object field
//            ((ObjectNode) current).set(lastToken.field, value);
//        } else {
//            // Final is an array element under an object field
//            ArrayNode arrayNode = getOrCreateArray((ObjectNode) current, lastToken.field);
//            ensureArraySize(arrayNode, lastToken.index);
//            arrayNode.set(lastToken.index, value);
//        }
//    }
//
//    /**
//     * Ensure an object field exists and is an ObjectNode, return it.
//     */
//    private static ObjectNode ensureObjectField(ObjectNode parent, String fieldName) {
//        JsonNode child = parent.get(fieldName);
//        if (!(child instanceof ObjectNode)) {
//            child = parent.putObject(fieldName);
//        }
//        return (ObjectNode) child;
//    }
//
//    /**
//     * Ensure an array field exists and has an ObjectNode at given index, return that element.
//     */
//    private static ObjectNode ensureArrayElement(ObjectNode parent, String fieldName, int index) {
//        ArrayNode arrayNode = getOrCreateArray(parent, fieldName);
//        ensureArraySize(arrayNode, index);
//
//        JsonNode element = arrayNode.get(index);
//        if (!(element instanceof ObjectNode)) {
//            element = MAPPER.createObjectNode();
//            arrayNode.set(index, element);
//        }
//        return (ObjectNode) element;
//    }
//
//    /**
//     * Get an ArrayNode field, creating it if necessary.
//     */
//    private static ArrayNode getOrCreateArray(ObjectNode parent, String fieldName) {
//        JsonNode node = parent.get(fieldName);
//        if (!(node instanceof ArrayNode)) {
//            node = parent.putArray(fieldName);
//        }
//        return (ArrayNode) node;
//    }
//
//    /**
//     * Ensure array has at least (index + 1) elements, filling with nulls if needed.
//     */
//    private static void ensureArraySize(ArrayNode array, int index) {
//        while (array.size() <= index) {
//            array.addNull();
//        }
//    }
//
//    /**
//     * Token representation: "Suspect" or "Suspect[0]"
//     */
//    private static class PathToken {
//        final String field;
//        final Integer index; // null if not an array
//
//        PathToken(String field, Integer index) {
//            this.field = field;
//            this.index = index;
//        }
//    }
//
//    /**
//     * Parse a path segment into field + optional [index].
//     *
//     * "Suspect"     -> field="Suspect", index=null
//     * "Suspect[0]"  -> field="Suspect", index=0
//     */
//    private static PathToken parseToken(String part) {
//        int bracketStart = part.indexOf('[');
//        if (bracketStart == -1) {
//            return new PathToken(part, null);
//        }
//
//        String fieldName = part.substring(0, bracketStart);
//        int bracketEnd = part.indexOf(']', bracketStart);
//        if (bracketEnd == -1) {
//            throw new IllegalArgumentException("Invalid array token (missing ']'): " + part);
//        }
//
//        String indexStr = part.substring(bracketStart + 1, bracketEnd);
//        int index = Integer.parseInt(indexStr);
//
//        return new PathToken(fieldName, index);
//    }
//}
