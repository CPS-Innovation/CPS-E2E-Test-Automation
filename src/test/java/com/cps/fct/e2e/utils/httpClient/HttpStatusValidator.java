package com.cps.fct.e2e.utils.httpClient;

import com.cps.fct.e2e.utils.common.ScenarioContext;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.picocontainer.annotations.Inject;

import java.util.List;
import java.util.Map;

public class HttpStatusValidator {

    private final Map<String, List<Integer>> allowedStatusesByMethod = Map.of(
            "GET", List.of(HttpStatus.SC_OK),
            "POST", List.of(HttpStatus.SC_OK, HttpStatus.SC_CREATED),
            "PUT", List.of(HttpStatus.SC_NO_CONTENT),
            "DELETE", List.of(HttpStatus.SC_NO_CONTENT),
            "PATCH", List.of(HttpStatus.SC_OK, HttpStatus.SC_NO_CONTENT)
    );
    @Inject
    private ScenarioContext context;

    public void validateOrThrow(Response response, String method) {
        List<Integer> expectedStatuses = allowedStatusesByMethod.getOrDefault(
                method.toUpperCase(), List.of(HttpStatus.SC_OK)
        );

        int actualStatus = response.getStatusCode();

        if (!expectedStatuses.contains(actualStatus)) {
            context.set("failedResponse", response.getBody());
            throw new AssertionError("❌ Unexpected status for " + method + ": " + actualStatus +
                    " (expected one of: " + expectedStatuses + ")");
        }
    }
}








