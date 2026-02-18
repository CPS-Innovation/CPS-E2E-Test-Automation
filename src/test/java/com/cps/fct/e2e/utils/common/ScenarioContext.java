package com.cps.fct.e2e.utils.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScenarioContext {

    private final Map<String, Object> context = new HashMap<>();

    public void set(String key, Object value) {
        context.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) context.get(key);
    }


    public <T> T getCastClazz(String key, Class<T> clazz) {
        return clazz.cast(context.get(key));
    }


    public String getAsString(String key) {
        Object value = get(key);
        return value instanceof String ? (String) value:null;
    }

    public Integer getAsInt(String key) {
        Object value = get(key);
        return value instanceof Integer ? (Integer) value:null;
    }

    @SuppressWarnings("unchecked")
    public List<Object> getAsList(String key) {
        Object value = get(key);
        return value instanceof List<?> ? (List<Object>) value:null;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getAsMap(String key) {
        Object value = get(key);
        return value instanceof Map<?, ?> ? (Map<String, String>) value:null;
    }

    public boolean contains(String key) {
        return context.containsKey(key);
    }

}


