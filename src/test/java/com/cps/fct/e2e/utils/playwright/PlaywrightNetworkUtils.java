package com.cps.fct.e2e.utils.playwright;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Request;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.TimeoutError;

public final class PlaywrightNetworkUtils {

    private PlaywrightNetworkUtils() {
    }

    /**
     * Usage while discovering flaky page transitions:
     *
     * <pre>
     * PlaywrightNetworkUtils.enableApiTrafficLogging(page, "Select test");
     * </pre>
     *
     * This prints XHR/fetch/API request and response URLs to the console. Use it temporarily to
     * identify the endpoint that should later be waited on with {@link #waitForResponseTriggeredBy}.
     */
    public static void enableApiTrafficLogging(Page page, String logPrefix) {
        page.onRequest(request -> {
            if (isApiTraffic(request)) {
                System.out.printf(
                        "[%s API request] %s %s (%s)%n",
                        logPrefix,
                        request.method(),
                        request.url(),
                        request.resourceType()
                );
            }
        });

        page.onResponse(response -> {
            Request request = response.request();
            if (isApiTraffic(request)) {
                System.out.printf(
                        "[%s API response] %s %s %s (%s)%n",
                        logPrefix,
                        response.status(),
                        request.method(),
                        response.url(),
                        request.resourceType()
                );
            }
        });
    }

    /**
     * Usage for a stable action wait:
     *
     * <pre>
     * PlaywrightNetworkUtils.waitForResponseTriggeredBy(
     *     page,
     *     "Save selected test type",
     *     "ActionSaveCaseIncludingTestType",
     *     "POST",
     *     200,
     *     this::clickSaveAndContinue
     * );
     * </pre>
     *
     * The helper starts listening before the action runs, so it cannot miss fast responses.
     * The action description is included in timeout/status failure messages.
     */
    public static Response waitForResponseTriggeredBy(
            Page page,
            String actionDescription,
            String endpointContains,
            String method,
            int expectedStatus,
            Runnable action
    ) {
        try {
            Response response = page.waitForResponse(
                    candidate -> isMatchingResponse(candidate, endpointContains, method),
                    action
            );

            if (response.status() != expectedStatus) {
                throw new IllegalStateException(String.format(
                        "%s failed. Expected %s %s to return HTTP %s, but got HTTP %s from %s. Response body: %s",
                        actionDescription,
                        method,
                        endpointContains,
                        expectedStatus,
                        response.status(),
                        response.url(),
                        safeResponseBody(response)
                ));
            }

            return response;
        } catch (TimeoutError error) {
            throw new IllegalStateException(String.format(
                    "%s timed out waiting for %s %s to return HTTP %s.",
                    actionDescription,
                    method,
                    endpointContains,
                    expectedStatus
            ), error);
        }
    }

    private static boolean isMatchingResponse(Response response, String endpointContains, String method) {
        return response.url().contains(endpointContains)
                && method.equalsIgnoreCase(response.request().method());
    }

    private static boolean isApiTraffic(Request request) {
        String url = request.url();
        return url.startsWith("http")
                && ("xhr".equals(request.resourceType())
                || "fetch".equals(request.resourceType())
                || url.toLowerCase().contains("/api/"));
    }

    private static String safeResponseBody(Response response) {
        try {
            return response.text();
        } catch (RuntimeException error) {
            return "<response body unavailable: " + error.getMessage() + ">";
        }
    }
}
