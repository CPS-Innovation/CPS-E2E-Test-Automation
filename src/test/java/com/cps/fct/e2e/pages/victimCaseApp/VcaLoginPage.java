package com.cps.fct.e2e.pages.victimCaseApp;

import com.cps.fct.e2e.pages.BasePage;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VcaLoginPage extends BasePage {

    public VcaLoginPage(PlaywrightContext context) {
        super(context);
    }

    private static final Logger logger =
            LoggerFactory.getLogger(VcaLoginPage.class);

    private static final int LOGIN_TIMEOUT_MILLIS = 35_000;
    private static final int SHORT_TIMEOUT_MILLIS = 2_000;
    private static final int LOGIN_ROUTE_READY_TIMEOUT_MILLIS = 5_000;
    private static final int INITIAL_LOGIN_REDIRECT_WAIT_MILLIS = 1_000;

    public void loginIntoVca(String username, String password){
        navigateToVcaLoginPage(). waitForLoginPageToLoad().completeLogin(username, password)
                .waitForVictimCaseAppHomePage();

    }

    private VcaLoginPage navigateToVcaLoginPage()  {
        page.navigate(EnvConfig.get("VICTIM_CASE_APP_URL"));
        return this;
    }

    public VcaLoginPage waitForLoginPageToLoad() {
        super.waitForDomContentLoaded();
        waitForLoginRouteToSettle();
        return this;
    }

    private void waitForLoginRouteToSettle() {
        long deadline = System.currentTimeMillis() + LOGIN_ROUTE_READY_TIMEOUT_MILLIS;
        String lastUrl = page.url();

        while (System.currentTimeMillis() < deadline) {
            String currentUrl = page.url();
            if (!currentUrl.equals(lastUrl)) {
                lastUrl = currentUrl;
            }
            page.waitForTimeout(100);
        }
    }

    private VcaLoginPage completeLogin(String username, String password) {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForTimeout(INITIAL_LOGIN_REDIRECT_WAIT_MILLIS);
        enterVictimCaseAppCredentials(username, password);
        return this;
    }

    private boolean enterVictimCaseAppCredentials(String username, String password) {
        Locator usernameField = victimCaseAppUsernameField();
        Locator passwordField = victimCaseAppPasswordField();

        usernameField.fill(username, new Locator.FillOptions().setTimeout(SHORT_TIMEOUT_MILLIS));
        passwordField.fill(password, new Locator.FillOptions().setTimeout(SHORT_TIMEOUT_MILLIS));
        clickSignInButton();
        waitForTextToAppear("Victims");
        return true;
    }

    private Locator victimCaseAppUsernameField() {
        return getTextboxById("Input_Username");
    }

    private Locator victimCaseAppPasswordField() {
        return getTextboxById("Input_Password");
    }

    private void clickSignInButton() {
        clickButton("Sign in");
        logger.info("VLO Logged into VCA");
    }

    private void waitForVictimCaseAppHomePage(){
//        waitForElementVisible("#PaginationResults",LOGIN_TIMEOUT_MILLIS);
        waitUntilSpinnersAreGone();
        waitForElementVisible("#PaginationResults",LOGIN_TIMEOUT_MILLIS);

    }

}
