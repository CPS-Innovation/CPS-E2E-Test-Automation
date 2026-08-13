package com.cps.fct.e2e.utils.common;

import java.util.Map;

public final class DataTableUtils {

    private DataTableUtils() {
    }

    public static boolean booleanValue(Map<String, String> data, String columnName) {
        String value = data.get(columnName);
        return value != null && Boolean.parseBoolean(value.trim());
    }
}
