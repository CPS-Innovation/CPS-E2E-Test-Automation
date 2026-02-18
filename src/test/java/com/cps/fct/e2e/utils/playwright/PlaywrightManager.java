package com.cps.fct.e2e.utils.playwright;

import com.microsoft.playwright.*;
import io.cucumber.java.Scenario;
import lombok.Getter;
import org.picocontainer.annotations.Inject;

import java.nio.file.Path;
import java.util.List;

public class PlaywrightManager {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;


    @Getter
    private Page page;

    @Inject PlaywrightContext playwrightContext;

    public void setUpBrowser(Scenario scenario) {

        playwright = Playwright.create();


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
                browser = Playwright.create().chromium().launch(launchOptions);
                break;

            case "firefox":
                browser = Playwright.create().firefox().launch(launchOptions);
                break;

            case "webkit":
                browser = Playwright.create().webkit().launch(launchOptions);
                break;

            default:
                throw new RuntimeException("Unsupported browser: " + browserName);
        }

        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setIgnoreHTTPSErrors(true)
                .setViewportSize(null); // viewport size to 1280*720

        if (enableVideo) {
            contextOptions.setRecordVideoDir(Path.of("videos"));
        }

        browserContext = browser.newContext(contextOptions);
        browserContext.setDefaultTimeout(5000);


        if (enableTracing) {
            browserContext.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
        }

       playwrightContext.setBrowserContext(browserContext);
    }

    public void tearDownBrowser(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
                scenario.attach(screenshot, "image/png", "Failure Screenshot");

                StringBuilder errorLog = new StringBuilder();
                page.onConsoleMessage(msg -> {
                    if ("error".equals(msg.type())) {
                        errorLog.append(msg.text()).append("\n");
                    }
                });

                if (!errorLog.isEmpty()) {
                    scenario.attach(errorLog.toString().getBytes(), "text/plain", "Browser Console Errors");
                }
            }

            if (Boolean.parseBoolean(System.getProperty("tracing", "false"))) {
                browserContext.tracing().stop(new Tracing.StopOptions().setPath(Path.of("trace.zip")));
                byte[] traceData = java.nio.file.Files.readAllBytes(Path.of("trace.zip"));
                scenario.attach(traceData, "application/zip", "Playwright Trace");
            }

        } catch (Exception e) {
            System.err.println("Error during teardown: " + e.getMessage());
        } finally {
            if (browserContext != null) browserContext.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }

}
