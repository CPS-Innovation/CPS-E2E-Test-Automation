package com.cps.fct.e2e.utils.FileMapping;

public class FileMapperFactory {
    public static FileMapper getMapper(String category) {
        return switch (category.toUpperCase()) {
            case "TWIF" -> new TWIFMapper();
            case "DCF" -> new DCFMapper();
            default -> throw new IllegalArgumentException("Unknown category: " + category);
        };
    }
}

