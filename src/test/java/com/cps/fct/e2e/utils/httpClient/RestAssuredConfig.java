package com.cps.fct.e2e.utils.httpClient;

import com.cps.fct.e2e.utils.common.EnvConfig;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class RestAssuredConfig {
    public static void configure() {
        if (EnvConfig.isDebugMode()) {
            RestAssured.filters(
                    new RequestLoggingFilter(),
                    new ResponseLoggingFilter()
            );
        }
    }
}

