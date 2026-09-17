package com.cps.fct.e2e.model.victimCaseApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddUpdateContactDetails {

    private final List<Map<String, Object>> operations;

    public AddUpdateContactDetails() {
        this.operations = new ArrayList<>();
    }

    public AddUpdateContactDetails replace(String path, Object value) {
        Map<String, Object> operation = new HashMap<>();
        operation.put("op", "replace");
        operation.put("path", path);
        operation.put("value", value);
        operations.add(operation);
        return this;
    }

    public AddUpdateContactDetails add(String path, Object value) {
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