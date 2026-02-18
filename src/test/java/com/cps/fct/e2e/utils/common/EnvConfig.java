package com.cps.fct.e2e.utils.common;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class EnvConfig {

    private static final Logger logger = LoggerFactory.getLogger(EnvConfig.class);
    private static Dotenv dotenv = null;

    public static void load(String environment) {
        dotenv = Dotenv.configure()
                .directory(".")
                .filename(String.format(".env.%s", environment))
                .ignoreIfMissing()
                .ignoreIfMalformed()
                .load();
    }

    public static String get(String key) {
        String value = dotenv.get(key);
        if (value==null || value.isBlank()) {
            logger.warn("Environment variable '{}' is missing or empty", key);
        }
        return value;
    }


    public static String getEnv(String key) {
        String value = dotenv.get(key);
        if (value == null) {
            value = System.getProperty(key);
        }
        return value;
    }


    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(Objects.requireNonNull(dotenv.get(key)));
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public static boolean isDebugMode() {
        return Boolean.parseBoolean(dotenv.get("DEBUG", "false"));
    }

}
