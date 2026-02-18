package com.cps.fct.e2e.utils.httpClient;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceResponseStore {

    private static final ConcurrentHashMap<String, HttpResponseWrapper> latestResponseStore = new ConcurrentHashMap<>();

    public static void add(String resourceName, HttpResponseWrapper response) {
        latestResponseStore.remove(resourceName);
        latestResponseStore.put(resourceName, response);
    }

    public static HttpResponseWrapper getLatestResponse(String resourceName) {
        return latestResponseStore.get(resourceName);
    }
    public static Set<String> getResourceNames() {
        return latestResponseStore.keySet();
    }

    public static Map<String, HttpResponseWrapper> getAll() {
        return latestResponseStore;
    }

    public static void clear() {
        latestResponseStore.clear();
    }
}