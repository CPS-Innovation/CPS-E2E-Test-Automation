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
import java.util.Map;

public class JsonMergeUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Merge overrides into base JSON using dotted paths, including array indices, e.g.:
     *
     *  "PreChargeDecisionRequest.PTIURN.Year": "26"
     *  "PreChargeDecisionRequest.Suspect[0].Person.DateOfBirth": "1990-01-01"
     */
    public static File mergeToTempFile(File baseFile, File overridesFile) throws IOException {
        ObjectNode baseJson = (ObjectNode) MAPPER.readTree(baseFile);
        ObjectNode overridesJson = (ObjectNode) MAPPER.readTree(overridesFile);

        Iterator<Map.Entry<String, JsonNode>> fields = overridesJson.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String path = entry.getKey();
            JsonNode value = entry.getValue();

            setDeep(baseJson, path, value);
        }


        String mergedJsonString = MAPPER.writeValueAsString(baseJson);

        Path tempFilePath = Files.createTempFile("merged-case-", ".json");
        Files.writeString(tempFilePath, mergedJsonString, StandardCharsets.UTF_8);

        return tempFilePath.toFile();
    }

    /**
     * Set a value into a JSON tree using a dotted path with optional [index] array notation.
     *
     * Examples:
     *  PreChargeDecisionRequest.PTIURN.Year
     *  PreChargeDecisionRequest.Suspect[0].Person.DateOfBirth
     */
    private static void setDeep(ObjectNode root, String path, JsonNode value) {
        String[] parts = path.split("\\.");
        JsonNode current = root;

        // Walk all but last part, ensuring structure exists
        for (int i = 0; i < parts.length - 1; i++) {
            PathToken token = parseToken(parts[i]);

            if (token.index == null) {
                // Object field access
                current = ensureObjectField((ObjectNode) current, token.field);
            } else {
                // Array field access
                current = ensureArrayElement((ObjectNode) current, token.field, token.index);
            }
        }

        // Handle last part: set the value
        PathToken lastToken = parseToken(parts[parts.length - 1]);

        if (lastToken.index == null) {
            // Final is an object field
            ((ObjectNode) current).set(lastToken.field, value);
        } else {
            // Final is an array element under an object field
            ArrayNode arrayNode = getOrCreateArray((ObjectNode) current, lastToken.field);
            ensureArraySize(arrayNode, lastToken.index);
            arrayNode.set(lastToken.index, value);
        }
    }

    /**
     * Ensure an object field exists and is an ObjectNode, return it.
     */
    private static ObjectNode ensureObjectField(ObjectNode parent, String fieldName) {
        JsonNode child = parent.get(fieldName);
        if (!(child instanceof ObjectNode)) {
            child = parent.putObject(fieldName);
        }
        return (ObjectNode) child;
    }

    /**
     * Ensure an array field exists and has an ObjectNode at given index, return that element.
     */
    private static ObjectNode ensureArrayElement(ObjectNode parent, String fieldName, int index) {
        ArrayNode arrayNode = getOrCreateArray(parent, fieldName);
        ensureArraySize(arrayNode, index);

        JsonNode element = arrayNode.get(index);
        if (!(element instanceof ObjectNode)) {
            element = MAPPER.createObjectNode();
            arrayNode.set(index, element);
        }
        return (ObjectNode) element;
    }

    /**
     * Get an ArrayNode field, creating it if necessary.
     */
    private static ArrayNode getOrCreateArray(ObjectNode parent, String fieldName) {
        JsonNode node = parent.get(fieldName);
        if (!(node instanceof ArrayNode)) {
            node = parent.putArray(fieldName);
        }
        return (ArrayNode) node;
    }

    /**
     * Ensure array has at least (index + 1) elements, filling with nulls if needed.
     */
    private static void ensureArraySize(ArrayNode array, int index) {
        while (array.size() <= index) {
            array.addNull();
        }
    }

    /**
     * Token representation: "Suspect" or "Suspect[0]"
     */
    private static class PathToken {
        final String field;
        final Integer index; // null if not an array

        PathToken(String field, Integer index) {
            this.field = field;
            this.index = index;
        }
    }

    /**
     * Parse a path segment into field + optional [index].
     *
     * "Suspect"     -> field="Suspect", index=null
     * "Suspect[0]"  -> field="Suspect", index=0
     */
    private static PathToken parseToken(String part) {
        int bracketStart = part.indexOf('[');
        if (bracketStart == -1) {
            return new PathToken(part, null);
        }

        String fieldName = part.substring(0, bracketStart);
        int bracketEnd = part.indexOf(']', bracketStart);
        if (bracketEnd == -1) {
            throw new IllegalArgumentException("Invalid array token (missing ']'): " + part);
        }

        String indexStr = part.substring(bracketStart + 1, bracketEnd);
        int index = Integer.parseInt(indexStr);

        return new PathToken(fieldName, index);
    }
}
