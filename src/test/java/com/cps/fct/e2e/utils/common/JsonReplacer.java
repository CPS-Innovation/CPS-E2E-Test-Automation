package com.cps.fct.e2e.utils.common;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonReplacer {

    protected String applyReplacements(String jsonString, Map<String, String> replacements) throws IOException {

        String modifiedContent = jsonString;

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            if (modifiedContent.contains(entry.getKey())) {
            modifiedContent = modifiedContent.replace(entry.getKey(), entry.getValue());
            }
        }
        return modifiedContent;
    }

    protected Map<String, String> removeCurlyBracesFromKeys(Map<String, String> cleanedReplacements) {
        return cleanedReplacements.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey()
                                .replaceAll("^\\{\\{", "")
                                .replaceAll("\\}\\}$", ""),
                        Map.Entry::getValue
                ));
    }

    private String toJsonString(String fileName) throws IOException {
        InputStream inputStream = JsonReplacer.class.getClassLoader()
                .getResourceAsStream(fileName);
        if (inputStream==null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }

}

