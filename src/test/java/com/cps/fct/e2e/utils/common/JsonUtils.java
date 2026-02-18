package com.cps.fct.e2e.utils.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Gson gson = new Gson();


    public static JsonNode parse(String json) {
        try {
            return mapper.readTree(json);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    // Update JSON using POJO fields
    public static String updateJsonFromPojo(String json, Object pojo) {
        try {
            JsonNode root = mapper.readTree(json);
            Map<String, Object> updates = mapper.convertValue(pojo, Map.class);

            if (root instanceof ObjectNode objectNode) {
                for (Map.Entry<String, Object> entry : updates.entrySet()) {
                    objectNode.putPOJO(entry.getKey(), entry.getValue());
                }
            }

            return mapper.writeValueAsString(root);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update JSON from POJO", e);
        }
    }


    public static String getValue(JsonNode node, String path) {
        String[] keys = path.split("\\.");
        for (String key : keys) {
            if (node==null) return null;
            node = node.get(key);
        }
        return node!=null ? node.asText():null;
    }


    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON to List<" + clazz.getSimpleName() + ">", e);
        }
    }


    public static List<String> extractFromJsonToList(String jsonBody, String jsonPathExpression) {
        JSONArray rawArray = JsonPath.read(jsonBody, jsonPathExpression);
        return rawArray.stream()
                .map(Object::toString)
                .collect(Collectors.toList());

    }

    public static String toJsonString(Map<String, String> payload) {
        return gson.toJson(payload);
    }


        @SuppressWarnings("unchecked")
        public static <T> T readJsonPath(String jsonBody, String jsonPathExpression, Class<T> type) {
            Object result = JsonPath.read(jsonBody, jsonPathExpression);

            if (result instanceof JSONArray array) {
                if (array.isEmpty()) return null;
                result = array.getFirst();
            }

            if (result == null) return null;

            if (type.isInstance(result)) {
                return (T) result;
            }

            try {
                if (type == String.class) {
                    return (T) result.toString();
                } else if (type == Integer.class) {
                    return (T) Integer.valueOf(result.toString());
                } else if (type == Boolean.class) {
                    return (T) Boolean.valueOf(result.toString());
                }
            } catch (Exception e) {
                return null;
            }

            return null;
        }



    public static <T> String toJson(T object) throws JsonProcessingException {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(object);
    }

}









