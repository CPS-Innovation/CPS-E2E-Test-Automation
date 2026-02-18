package com.cps.fct.e2e.utils.playwright;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class BrowserPathResolver {

    public static Optional<Path> getBrowserExecutable(String browserName) {
        return switch (browserName.toLowerCase()) {
            case "chrome" -> tryPaths(
                    "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                    "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"
            );
            case "edge" -> tryPaths(
                    "C:\\Program Files\\Microsoft\\Edge\\Application\\msedge.exe",
                    "C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe"
            );
            default -> Optional.empty();
        };
    }

    private static Optional<Path> tryPaths(String... paths) {
        for (String path : paths) {
            Path p = Paths.get(path);
            if (p.toFile().exists()) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
}