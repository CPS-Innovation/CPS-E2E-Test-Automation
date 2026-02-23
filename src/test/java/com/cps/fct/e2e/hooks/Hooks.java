package com.cps.fct.e2e.hooks;

import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.RestAssuredConfig;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.cps.fct.e2e.utils.playwright.PlaywrightManager;
import com.cps.fct.e2e.utils.services.ddei.CommonService;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import lombok.Getter;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;

    private boolean isUIScenario = false;


    private static final String CIN3_SUFFIX = ".CIN3";
    private static final String CIN5_SUFFIX = ".CIN5";


    @Getter
    @Inject
    private PlaywrightManager playwrightManager;
    @Inject
    private PlaywrightContext playwrightContext;

    @Inject
    private ScenarioContext context;

    @Inject
    private CommonService service;

    @Before(order = 0)
    public void loadEnvironmentConfig() {
        String env = System.getProperty("env", "qa"); // default to QA
        EnvConfig.load(env);
        logger.info("Environment loaded: {} ", env);
    }

    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {
        RestAssuredConfig.configure();
        setSuffixBasedOnTag(scenario);
        if (service.isDDEIHealthy()) {
            service.createCmsAuthToken(context);
        }

        isUIScenario = scenario.getSourceTagNames().contains("@ui");
        if (isUIScenario) {
            playwrightManager.setUpBrowser(scenario);

        }

    }

    @After
    public void afterScenario(Scenario scenario) {
        attachReport(scenario);
        if (isUIScenario && playwrightManager != null) {
            playwrightManager.tearDownBrowser(scenario);
        }

    }

    private void attachReport(Scenario scenario) {
        Response failedResponse = context.get("failedResponse");
        if (failedResponse!=null) {
            scenario.attach(failedResponse.toString().getBytes(), "text/plain", "Failed Response");
        }
    }

    public void setSuffixBasedOnTag(Scenario scenario) {
        String suffix = "";
        String caseType = "";
        for (String tag : scenario.getSourceTagNames()) {
            String tagName = tag.toUpperCase();
            if (tagName.contains("@DCF")) {
                suffix = CIN5_SUFFIX;
                caseType = tagName;
                break;
            } else if (tagName.contains("@TWIF")) {
                suffix = CIN3_SUFFIX;
                caseType = tagName;
                break;
            }
        }
        context.set("caseType", caseType.replace("@", ""));
        context.set("envSuffix", suffix);
    }
}
















