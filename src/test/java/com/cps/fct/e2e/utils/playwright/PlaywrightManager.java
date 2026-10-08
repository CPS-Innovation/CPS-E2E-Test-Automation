package com.cps.fct.e2e.utils.playwright;

import com.cps.fct.e2e.utils.common.EnvConfig;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import io.cucumber.java.Scenario;
import lombok.Getter;
import org.picocontainer.annotations.Inject;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PlaywrightManager {

    private static final int DEFAULT_PLAYWRIGHT_TIMEOUT_MILLIS = 25_000;
    private static final Path TEST_ARTIFACTS_DIR = Path.of("test-artifacts");
    private static final Path SCREENSHOTS_DIR = TEST_ARTIFACTS_DIR.resolve("screenshots");
    private static final Path VIDEOS_DIR = TEST_ARTIFACTS_DIR.resolve("videos");
    private static final Path TRACES_DIR = TEST_ARTIFACTS_DIR.resolve("traces");
    private static final String CLOSE_BROWSER_PROPERTY = "closeBrowser";
    private static final String KEEP_BROWSER_OPEN_SECONDS_PROPERTY = "keepBrowserOpenSeconds";
    private static final String PAUSE_BEFORE_CLOSE_PROPERTY = "pauseBeforeClose";
    private static final int MAX_BROWSER_CONSOLE_ERRORS_TO_ATTACH = 20;
    private static final List<String> NOISY_BROWSER_CONSOLE_ERROR_FRAGMENTS = List.of(
            "fa-app-insights-proxy-staging.azurewebsites.net/v2/track",
            "violates the following Content Security Policy directive",
            "Fetch API cannot load https://fa-app-insights-proxy-staging.azurewebsites.net/v2/track",
            "https://graph.microsoft.com/v1.0/me?$select=department,jobTitle",
            "auth getMe failed",
            "initialiseUserData Unexpected error fetching user data",
            "Call to /api/global-components/user-data returned non-ok status code: 401"
    );

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private final List<String> browserConsoleErrors = new ArrayList<>();


    @Getter
    private Page page;

    @Inject PlaywrightContext playwrightContext;

    public void setUpBrowser(Scenario scenario) {
        if (isBrowserStarted()) {
            return;
        }

        playwright = Playwright.create();
        browserConsoleErrors.clear();


        String browserName = System.getProperty("browser", "edge").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        boolean enableTracing = Boolean.parseBoolean(System.getProperty("tracing", "false"));
        boolean enableVideo = Boolean.parseBoolean(System.getProperty("video", "false"));

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setArgs(List.of("--start-maximized"));

        switch (browserName) {
            case "edge":
            case "chrome":
                BrowserPathResolver.getBrowserExecutable(browserName)
                        .ifPresent(launchOptions::setExecutablePath);
                browser = playwright.chromium().launch(launchOptions);
                break;

            case "firefox":
                browser = playwright.firefox().launch(launchOptions);
                break;

            case "webkit":
                browser = playwright.webkit().launch(launchOptions);
                break;

            default:
                throw new RuntimeException("Unsupported browser: " + browserName);
        }

        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setIgnoreHTTPSErrors(true)
                .setViewportSize(null); // viewport size to 1280*720

        if (enableVideo) {
            contextOptions.setRecordVideoDir(VIDEOS_DIR);
        }

        browserContext = browser.newContext(contextOptions);
        browserContext.setDefaultTimeout(DEFAULT_PLAYWRIGHT_TIMEOUT_MILLIS);
        PlaywrightAssertions.setDefaultAssertionTimeout(DEFAULT_PLAYWRIGHT_TIMEOUT_MILLIS);
        grantLocalNetworkAccess(browserName);


        if (enableTracing) {
            browserContext.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
        }

       playwrightContext.setBrowserContext(browserContext);
       page = playwrightContext.getPage();
       page.onConsoleMessage(msg -> {
           if ("error".equals(msg.type())) {
               browserConsoleErrors.add(msg.text());
           }
       });
    }

    public boolean isBrowserStarted() {
        return page != null && !page.isClosed();
    }

    private boolean hasBrowserSession() {
        return playwright != null || browser != null || browserContext != null || page != null;
    }

    private void grantLocalNetworkAccess(String browserName) {
        if (!List.of("edge", "chrome").contains(browserName)) {
            return;
        }

        String caseReviewUrl = EnvConfig.getEnv("CASE_REVIEW_URL");
        if (caseReviewUrl == null || caseReviewUrl.isBlank()) {
            return;
        }

        URI uri = URI.create(caseReviewUrl);
        String origin = uri.getScheme() + "://" + uri.getHost();
        if (uri.getPort() != -1) {
            origin += ":" + uri.getPort();
        }

        browserContext.grantPermissions(
                List.of("local-network-access"),
                new BrowserContext.GrantPermissionsOptions().setOrigin(origin)
        );
    }

    public void tearDownBrowser(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                attachFailureScreenshot(scenario);
                attachBrowserConsoleErrors(scenario);
            }

            if (browserContext != null && Boolean.parseBoolean(System.getProperty("tracing", "false"))) {
                Files.createDirectories(TRACES_DIR);
                Path tracePath = TRACES_DIR.resolve("trace-" + System.currentTimeMillis() + ".zip");
                browserContext.tracing().stop(new Tracing.StopOptions().setPath(tracePath));
                byte[] traceData = Files.readAllBytes(tracePath);
                scenario.attach(traceData, "application/zip", "Playwright Trace");
                System.err.println("Playwright trace saved: " + tracePath.toAbsolutePath());
            }

        } catch (Exception e) {
            System.err.println("Error during teardown: " + e.getMessage());
        } finally {
            boolean shouldKeepBrowserOpen = false;
            if (hasBrowserSession()) {
                pauseBeforeCloseIfRequested();
                shouldKeepBrowserOpen = keepBrowserOpenIfRequested();
            }
            if (!shouldKeepBrowserOpen) {
                if (playwrightContext != null) playwrightContext.detachVirtualWebAuthnAuthenticator();
                if (browserContext != null) browserContext.close();
                if (browser != null) browser.close();
                if (playwright != null) playwright.close();
            }
        }
    }

    private void pauseBeforeCloseIfRequested() {
        if (!Boolean.parseBoolean(System.getProperty(PAUSE_BEFORE_CLOSE_PROPERTY, "false"))) {
            return;
        }
        if (page == null || page.isClosed()) {
            System.err.println("Cannot pause before close because the Playwright page is not available.");
            return;
        }
        System.out.println("Playwright pause requested. Resume from the Playwright inspector to continue teardown.");
        page.pause();
    }

    private boolean keepBrowserOpenIfRequested() {
        if (Boolean.parseBoolean(System.getProperty(CLOSE_BROWSER_PROPERTY, "true"))) {
            return false;
        }

        long keepOpenSeconds = keepBrowserOpenSeconds();
        if (keepOpenSeconds > 0) {
            System.out.printf("Keeping browser open for %d seconds because -D%s=false.%n",
                    keepOpenSeconds, CLOSE_BROWSER_PROPERTY);
            sleepForSeconds(keepOpenSeconds);
            return true;
        }

        System.out.printf("Keeping browser open because -D%s=false. Stop the test process manually when finished.%n",
                CLOSE_BROWSER_PROPERTY);
        sleepForever();
        return true;
    }

    private long keepBrowserOpenSeconds() {
        String value = System.getProperty(KEEP_BROWSER_OPEN_SECONDS_PROPERTY, "0");
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid value for -" + KEEP_BROWSER_OPEN_SECONDS_PROPERTY
                    + ": " + value + ". Use a whole number of seconds.");
        }
    }

    private void sleepForSeconds(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void sleepForever() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void attachFailureScreenshot(Scenario scenario) throws java.io.IOException {
        if (page == null || page.isClosed()) {
            System.err.println("Failure screenshot was not captured because the Playwright page is not available.");
            return;
        }

        Path screenshotPath = failureScreenshotPath(scenario);
        Files.createDirectories(screenshotPath.getParent());

        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                .setFullPage(true)
                .setPath(screenshotPath));

        scenario.attach(screenshot, "image/png", "Failure Screenshot");
        System.err.println("Failure screenshot saved: " + screenshotPath.toAbsolutePath());
        System.err.println("Failure page URL: " + page.url());
    }

    private Path failureScreenshotPath(Scenario scenario) {
        String scenarioName = scenario.getName()
                .replaceAll("[^A-Za-z0-9._-]+", "-")
                .replaceAll("^-+|-+$", "");
        if (scenarioName.isBlank()) {
            scenarioName = "failed-scenario";
        }
        return SCREENSHOTS_DIR.resolve(scenarioName + "-" + System.currentTimeMillis() + ".png");
    }

    private void attachBrowserConsoleErrors(Scenario scenario) {
        List<String> relevantConsoleErrors = browserConsoleErrors.stream()
                .filter(error -> !isNoisyBrowserConsoleError(error))
                .distinct()
                .limit(MAX_BROWSER_CONSOLE_ERRORS_TO_ATTACH)
                .toList();

        if (relevantConsoleErrors.isEmpty()) {
            if (!browserConsoleErrors.isEmpty()) {
                System.err.println("Browser console errors suppressed as known environment noise: "
                        + browserConsoleErrors.size());
            }
            return;
        }

        String errorLog = String.join(System.lineSeparator(), relevantConsoleErrors);
        scenario.attach(errorLog.getBytes(), "text/plain", "Browser Console Errors");
        System.err.println("Browser console errors attached: " + relevantConsoleErrors.size()
                + " relevant"
                + (browserConsoleErrors.size() > relevantConsoleErrors.size()
                ? ", " + (browserConsoleErrors.size() - relevantConsoleErrors.size()) + " suppressed"
                : ""));
    }

    private boolean isNoisyBrowserConsoleError(String error) {
        return NOISY_BROWSER_CONSOLE_ERROR_FRAGMENTS.stream().anyMatch(error::contains);
    }

}
