package com.cps.fct.e2e.pages.victimCaseApp;

import com.cps.fct.e2e.pages.BasePage;
import com.cps.fct.e2e.pages.caseReviewApp.LoginPage;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.regex.Pattern;

public class VcaLoginPage extends BasePage {
    private static final int LOGIN_TIMEOUT_MILLIS = 10_000;
    private static final int SHORT_TIMEOUT_MILLIS = 2_000;
    private static final int MICROSOFT_PRIMARY_CLICK_TIMEOUT_MILLIS = 15_000;
    private static final int MICROSOFT_POST_EMAIL_ROUTE_TIMEOUT_MILLIS = 10_000;
    private static final int MICROSOFT_CREDENTIAL_TYPING_TIMEOUT_MILLIS = 15_000;
    private static final int MICROSOFT_CREDENTIAL_TYPING_DELAY_MILLIS = 20;
    private static final int LOGIN_ROUTE_READY_TIMEOUT_MILLIS = 15_000;
    private static final int LOGIN_ROUTE_URL_STABLE_MILLIS = 750;
    private static final int INITIAL_LOGIN_REDIRECT_WAIT_MILLIS = 3_000;

    private final PlaywrightContext context;

    public VcaLoginPage(PlaywrightContext context) {
        super(context);
        this.context = context;
    }

    public void loginIntoVca(String username, String password) throws InterruptedException {
        navigateToVcaLoginPage().
                waitForLoginPageToLoad()
                .completeLogin(username, password)
                .waitForCaseReviewHomePage();
//                .verifyNoLoginErrorsPresent();
    }


    private VcaLoginPage navigateToVcaLoginPage() throws InterruptedException {
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
        long urlStableSince = System.currentTimeMillis();

        while (System.currentTimeMillis() < deadline) {
            String currentUrl = page.url();
            if (!currentUrl.equals(lastUrl)) {
                lastUrl = currentUrl;
                urlStableSince = System.currentTimeMillis();
            }

//            if (isKnownLoginRouteVisible()
//                    && System.currentTimeMillis() - urlStableSince >= LOGIN_ROUTE_URL_STABLE_MILLIS) {
//                return;
//            }

            page.waitForTimeout(100);
        }
    }

    private VcaLoginPage completeLogin(String username, String password) {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForTimeout(INITIAL_LOGIN_REDIRECT_WAIT_MILLIS);

//        if (isCaseReviewHomeVisible()
//                || isUnauthorizedVisible()) {
//            return this;
//        }
//
//        if (isMicrosoftUsernameVisible()) {
//            enterMicrosoftUsername(getAadUsernameConfig());
//            choosePasswordSignInIfPresent();
//            enterMicrosoftCredential(getAadCredentialConfig());
//            answerStaySignedInPromptIfPresent();
//        }
//
//        waitForCaseReviewLoginOrTerminalPage();
        enterVictimCaseAppCredentials(username, password);
        return this;
    }

    private boolean enterVictimCaseAppCredentials(String username, String password) {
        Locator usernameField = victimCaseAppUsernameField();
        Locator passwordField = victimCaseAppPasswordField();

//        if (!isVisible(usernameField, SHORT_TIMEOUT_MILLIS)
//                || !isLocatorVisible(passwordField)) {
//            return false;
//        }

        usernameField.fill(username, new Locator.FillOptions().setTimeout(SHORT_TIMEOUT_MILLIS));
        passwordField.fill(password, new Locator.FillOptions().setTimeout(SHORT_TIMEOUT_MILLIS));
        clickSignInButton();
        return true;
    }

    private Locator victimCaseAppUsernameField() {
        return getTextboxById("Input_Username");
    }

    private Locator victimCaseAppPasswordField() {
        return getTextboxById("Input_Password");
    }

    private void clickSignInButton() {
//        if (clickVisibleSignInButton()) {
//            return;
//        }

        clickButton("Sign in");
    }


    private VcaLoginPage waitForCaseReviewHomePage() {
        page.waitForSelector("text='Victims'",
                new Page.WaitForSelectorOptions().setTimeout(LOGIN_TIMEOUT_MILLIS));
        return this;
    }






}
