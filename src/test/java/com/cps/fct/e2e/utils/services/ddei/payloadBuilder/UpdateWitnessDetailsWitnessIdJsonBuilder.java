package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UpdateWitnessDetailsWitnessIdJsonBuilder {

    private final List<Map<String, Object>> operations;

    public UpdateWitnessDetailsWitnessIdJsonBuilder() {
        this.operations = new ArrayList<>();
    }

    public UpdateWitnessDetailsWitnessIdJsonBuilder replace(String path, Object value) {
        Map<String, Object> operation = new HashMap<>();
        operation.put("op", "replace");
        operation.put("path", path);
        operation.put("value", value);
        operations.add(operation);
        return this;
    }

    public UpdateWitnessDetailsWitnessIdJsonBuilder add(String path, Object value) {
        Map<String, Object> operation = new HashMap<>();
        operation.put("op", "add");
        operation.put("path", path);
        operation.put("value", value);
        operations.add(operation);
        return this;
    }

    public List<Map<String, Object>> build() {
        return operations;
    }

}