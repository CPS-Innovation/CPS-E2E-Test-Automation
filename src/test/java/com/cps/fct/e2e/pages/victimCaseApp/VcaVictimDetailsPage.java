package com.cps.fct.e2e.pages.victimCaseApp;

import com.cps.fct.e2e.pages.AppsLoginPage;
import com.cps.fct.e2e.pages.BasePage;
import com.cps.fct.e2e.pages.PageObjects;
import com.cps.fct.e2e.pages.caseReviewApp.LoginPage;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VcaVictimDetailsPage extends BasePage {

    private ScenarioContext context;

    public VcaVictimDetailsPage(PlaywrightContext context) {
        super(context);
    }

    private static final Logger logger =
            LoggerFactory.getLogger(VcaHomePage.class);

    public void navigateVictimDetailsPage(){

        waitUntilSpinnersAreGone("Loading, please wait");
        page.pause();


    }








}
