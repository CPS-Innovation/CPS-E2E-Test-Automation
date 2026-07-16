package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.regex.Pattern;


public class LoginPage extends BasePage {

    private static final int LOGIN_TIMEOUT_MILLIS = 10_000;
    private static final int SHORT_TIMEOUT_MILLIS = 2_000;
    private static final int MICROSOFT_PRIMARY_CLICK_TIMEOUT_MILLIS = 15_000;
    private static final int MICROSOFT_POST_EMAIL_ROUTE_TIMEOUT_MILLIS = 10_000;
    private static final int MICROSOFT_PASSWORD_TYPING_TIMEOUT_MILLIS = 15_000;
    private static final int MICROSOFT_PASSWORD_TYPING_DELAY_MILLIS = 20;
    private static final int LOGIN_ROUTE_READY_TIMEOUT_MILLIS = 15_000;
    private static final int LOGIN_ROUTE_URL_STABLE_MILLIS = 750;
    private static final int INITIAL_LOGIN_REDIRECT_WAIT_MILLIS = 3_000;
    private static final String AAD_USER_KEY = "AAD_USER";
    private static final String AAD_PASSWORD_KEY = "AAD_PASSWORD";
    private static final String AAD_PASSWORD_BASE64_KEY = "AAD_PASSWORD_BASE64";
    private static final String BASE64_PREFIX = "base64:";
    private static final String SIGN_IN_ANOTHER_WAY_TEXT = "Sign in another way";
    private static final String USE_MY_PASSWORD_TEXT = "Use my password";
    private static final String CHOOSE_WAY_TO_SIGN_IN_TEXT = "Choose a way to sign in";
    private static final String MICROSOFT_PASSKEY_ERROR_TEXT = "Something went wrong";
    private static final String MICROSOFT_PASSWORD_FIELD =
            "input[name='passwd']:not([aria-hidden='true']):not(.moveOffScreen), "
                    + "#i0118:not([aria-hidden='true']):not(.moveOffScreen), "
                    + "input[type='password']:not([aria-hidden='true']):not(.moveOffScreen)";
    private static final String MICROSOFT_PRIMARY_BUTTON =
            "#idSIButton9:visible, input[type='submit']:visible, button[type='submit']:visible, input[value='Next']:visible, button:has-text('Next'):visible";

    private final PlaywrightContext context;

    public LoginPage(PlaywrightContext context) {
        super(context);
        this.context = context;
    }

    public void loginIntoCaseReview(
            String username, String password) throws InterruptedException {

        navigateToLoginPage().
                waitForLoginPageToLoadCompletely()
                .completeLogin(username, password)
                .waitForCaseReviewHomePage()
                .verifyNoLoginErrorsPresent();
    }


    private LoginPage navigateToLoginPage() throws InterruptedException {
        page.navigate(EnvConfig.get("CASE_REVIEW_URL"));
        return this;
    }

    private void verifyNoLoginErrorsPresent() {
        assertTextIsNotVisible("Unauthorized");
    }


    private void clickSignInButton() {
        if (clickVisibleSignInButton()) {
            return;
        }

        getTextboxByName("Password").press("Enter");
    }

    private LoginPage completeLogin(String username, String password) {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForTimeout(INITIAL_LOGIN_REDIRECT_WAIT_MILLIS);

        if (isCaseReviewHomeVisible()
                || isUnauthorizedVisible()) {
            return this;
        }

        if (isMicrosoftUsernameVisible()) {
            enterMicrosoftUsername(getAadUsernameConfig());
            choosePasswordSignInIfPresent();
            enterMicrosoftPassword(getAadPasswordConfig());
            answerStaySignedInPromptIfPresent();
        }

        waitForCaseReviewLoginOrTerminalPage();
        enterCaseReviewCredentialsIfPresent(username, password);
        return this;
    }

    private boolean enterCaseReviewCredentialsIfPresent(String username, String password) {
        Locator usernameField = caseReviewUsernameField();
        Locator passwordField = caseReviewPasswordField();

        if (!isVisible(usernameField, SHORT_TIMEOUT_MILLIS)
                || !isLocatorVisible(passwordField)) {
            return false;
        }

        usernameField.fill(username, new Locator.FillOptions().setTimeout(SHORT_TIMEOUT_MILLIS));
        passwordField.fill(password, new Locator.FillOptions().setTimeout(SHORT_TIMEOUT_MILLIS));
        clickSignInButton();
        return true;
    }

    private void waitForCaseReviewLoginOrTerminalPage() {
        long deadline = System.currentTimeMillis() + LOGIN_TIMEOUT_MILLIS;

        while (System.currentTimeMillis() < deadline) {
            answerStaySignedInPromptIfPresent();

            if (isCaseReviewLoginVisible()
                    || isCaseReviewHomeVisible()
                    || isUnauthorizedVisible()) {
                return;
            }

            page.waitForTimeout(250);
        }

        throw new IllegalStateException("Case Review login page did not appear. Current URL is: " + page.url());
    }

    private void prepareVirtualAuthenticatorForMicrosoftChallenge() {
        context.attachVirtualWebAuthnAuthenticator(page);
    }

    private void enterMicrosoftUsername(String aadUsername) {
        Locator usernameField = microsoftUsernameField();
        if (!isVisible(usernameField, LOGIN_TIMEOUT_MILLIS)) {
            throw new IllegalStateException("Microsoft AD username page did not appear. Current URL is: " + page.url());
        }

        usernameField.fill(aadUsername);
        waitForInputValue(usernameField, aadUsername);
        prepareVirtualAuthenticatorForMicrosoftChallenge();
        if (isLocatorVisible(microsoftPrimaryButton())) {
            clickMicrosoftPrimaryButton();
        }
    }

    private void enterMicrosoftPassword(String aadPassword) {
        if (aadPassword == null || aadPassword.isBlank()) {
            throw new IllegalStateException(AAD_PASSWORD_KEY + " resolved to an empty value for automated Microsoft AD login.");
        }

        if (!waitForMicrosoftPasswordPageReady()) {
            throw new IllegalStateException("Microsoft AD password page did not appear. Check AAD_USER and conditional access settings.");
        }

        Locator passwordField = microsoftPasswordField();
        passwordField.click();
        passwordField.fill("");
        passwordField.pressSequentially(
                aadPassword,
                new Locator.PressSequentiallyOptions()
                        .setDelay(MICROSOFT_PASSWORD_TYPING_DELAY_MILLIS)
                        .setTimeout(MICROSOFT_PASSWORD_TYPING_TIMEOUT_MILLIS)
        );
        waitForInputValue(passwordField, aadPassword);
        dispatchPasswordInputEvents(passwordField);
        waitForMicrosoftPrimaryButtonToBeEnabled();
        clickMicrosoftPrimaryButton();
    }

    private void choosePasswordSignInIfPresent() {
        long deadline = System.currentTimeMillis() + LOGIN_TIMEOUT_MILLIS;
        while (System.currentTimeMillis() < deadline) {
            if (isMicrosoftPasswordVisible()) {
                return;
            }

            if (isLocatorVisible(useMyPasswordOption())) {
                try {
                    clickUseMyPassword(useMyPasswordOption());
                } catch (PlaywrightException exception) {
                    if (!isMicrosoftPasswordVisible()) {
                        throw exception;
                    }
                }
                page.waitForLoadState(LoadState.DOMCONTENTLOADED);
                waitForMicrosoftPasswordPageReady();
                return;
            }

            if (isLocatorVisible(signInAnotherWayLink())
                    && isLocatorVisible(microsoftPasskeyErrorText())) {
                clickSignInAnotherWay(signInAnotherWayLink());
                page.waitForLoadState(LoadState.DOMCONTENTLOADED);
                waitForMicrosoftPasskeyErrorToDisappear();
                waitForUseMyPasswordOrPasswordField();
                return;
            }

            page.waitForTimeout(100);
        }
    }

    private void waitForUseMyPasswordOrPasswordField() {
        long deadline = System.currentTimeMillis() + MICROSOFT_POST_EMAIL_ROUTE_TIMEOUT_MILLIS;

        while (System.currentTimeMillis() < deadline) {
            if (isMicrosoftPasswordVisible()) {
                return;
            }

            if (clickUseMyPasswordIfPresent(250)) {
                waitForMicrosoftPasswordPageReady();
                return;
            }

            page.waitForTimeout(100);
        }
    }

    private void waitForMicrosoftPasskeyErrorToDisappear() {
        Locator passkeyError = microsoftPasskeyErrorText();
        passkeyError.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(LOGIN_TIMEOUT_MILLIS));
    }

    private boolean clickUseMyPasswordIfPresent(int timeoutMillis) {
        Locator useMyPassword = useMyPasswordOption();
        if (!isVisible(useMyPassword, timeoutMillis)) {
            return false;
        }

        try {
            clickUseMyPassword(useMyPassword);
        } catch (PlaywrightException exception) {
            if (!isMicrosoftPasswordVisible()) {
                throw exception;
            }
        }

        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        return true;
    }

    private void clickSignInAnotherWay(Locator signInAnotherWay) {
        signInAnotherWay.click(new Locator.ClickOptions()
                .setForce(true)
                .setTimeout(SHORT_TIMEOUT_MILLIS));
    }

    private void clickUseMyPassword(Locator useMyPassword) {
        useMyPassword.click(new Locator.ClickOptions()
                .setForce(true)
                .setTimeout(SHORT_TIMEOUT_MILLIS));
    }

    private void answerStaySignedInPromptIfPresent() {
        Locator noButton = page.locator("#idBtn_Back, input[value='No'], button:has-text('No')").first();
        if (isLocatorVisible(noButton)) {
            noButton.click();
        }
    }

    private LoginPage waitForCaseReviewHomePage() {
        page.waitForSelector("text='View a Case'",
                new Page.WaitForSelectorOptions().setTimeout(LOGIN_TIMEOUT_MILLIS));
        return this;
    }

    private boolean isMicrosoftUsernameVisible() {
        return isLocatorVisible(microsoftUsernameField());
    }

    private boolean isMicrosoftPasswordVisible() {
        return isLocatorVisible(microsoftPasswordField())
                && !isChooseWayToSignInVisible();
    }

    private boolean isMicrosoftAlternativeSignInVisible() {
        return isLocatorVisible(signInAnotherWayLink()) || isLocatorVisible(useMyPasswordOption());
    }

    private boolean waitForMicrosoftPasswordPageReady() {
        long deadline = System.currentTimeMillis() + LOGIN_TIMEOUT_MILLIS;

        while (System.currentTimeMillis() < deadline) {
            if (isMicrosoftPasswordVisible()) {
                return true;
            }

            page.waitForTimeout(100);
        }

        return false;
    }

    private boolean isChooseWayToSignInVisible() {
        return isLocatorVisible(chooseWayToSignInHeader());
    }

    private boolean isMicrosoftLoginFlowVisible() {
        return isMicrosoftUrl()
                || isMicrosoftUsernameVisible()
                || isMicrosoftAlternativeSignInVisible();
    }

    private boolean isMicrosoftUrl() {
        String currentUrl = page.url().toLowerCase();
        return currentUrl.contains("login.microsoftonline.com")
                || currentUrl.contains("login.microsoft.com");
    }

    private boolean isCaseReviewHomeVisible() {
        return isLocatorVisible(page.getByText("View a Case"));
    }

    private boolean isCaseReviewLoginVisible() {
        return isLocatorVisible(caseReviewUsernameField())
                && isLocatorVisible(caseReviewPasswordField());
    }

    private boolean isUnauthorizedVisible() {
        return isLocatorVisible(page.getByText("Unauthorized"));
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

            if (isKnownLoginRouteVisible()
                    && System.currentTimeMillis() - urlStableSince >= LOGIN_ROUTE_URL_STABLE_MILLIS) {
                return;
            }

            page.waitForTimeout(100);
        }
    }

    private boolean isKnownLoginRouteVisible() {
        return isCaseReviewHomeVisible()
                || isUnauthorizedVisible()
                || isCaseReviewLoginVisible()
                || isMicrosoftLoginFlowVisible();
    }

    private boolean isVisible(Locator locator, int timeoutMillis) {
        try {
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeoutMillis));
            return true;
        } catch (PlaywrightException e) {
            if (isGenuineLocatorError(e)) {
                throw e;
            }
            return false;
        }
    }

    private boolean isLocatorVisible(Locator locator) {
        try {
            return locator.isVisible();
        } catch (PlaywrightException e) {
            if (isGenuineLocatorError(e)) {
                throw e;
            }
            return false;
        }
    }

    private boolean isLocatorEditable(Locator locator) {
        try {
            return locator.isEditable();
        } catch (PlaywrightException e) {
            if (isGenuineLocatorError(e)) {
                throw e;
            }
            return false;
        }
    }

    private Locator microsoftUsernameField() {
        return page.locator("#i0116, input[name='loginfmt'], input[type='email']").first();
    }

    private Locator microsoftPasswordField() {
        return page.locator(MICROSOFT_PASSWORD_FIELD).first();
    }

    private Locator signInAnotherWayLink() {
        return page.getByText(SIGN_IN_ANOTHER_WAY_TEXT, new Page.GetByTextOptions().setExact(true)).first();
    }

    private Locator useMyPasswordOption() {
        return page.getByText(USE_MY_PASSWORD_TEXT, new Page.GetByTextOptions().setExact(true)).first();
    }

    private Locator chooseWayToSignInHeader() {
        return page.getByText(CHOOSE_WAY_TO_SIGN_IN_TEXT, new Page.GetByTextOptions().setExact(true)).first();
    }

    private Locator microsoftPasskeyErrorText() {
        return page.getByText(MICROSOFT_PASSKEY_ERROR_TEXT, new Page.GetByTextOptions().setExact(false)).first();
    }

    private Locator caseReviewUsernameField() {
        return getTextboxByName("Username");
    }

    private Locator caseReviewPasswordField() {
        return getTextboxByName("Password");
    }

    private boolean clickVisibleSignInButton() {
        Locator signInButton = caseReviewSignInButton();
        if (isVisible(signInButton, SHORT_TIMEOUT_MILLIS)) {
            signInButton.click();
            return true;
        }

        Locator submitButton = submitButton();
        if (isVisible(submitButton, SHORT_TIMEOUT_MILLIS)) {
            submitButton.click();
            return true;
        }

        return false;
    }

    private Locator caseReviewSignInButton() {
        return page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(
                        Pattern.compile("^(sign\\s*in|log\\s*in|login)$", Pattern.CASE_INSENSITIVE)
                )).first();
    }

    private Locator submitButton() {
        return page.locator("button[type='submit'], input[type='submit']").first();
    }

    private Locator microsoftPrimaryButton() {
        return page.locator(MICROSOFT_PRIMARY_BUTTON).first();
    }

    private void clickMicrosoftPrimaryButton() {
        microsoftPrimaryButton().click(new Locator.ClickOptions()
                .setTimeout(MICROSOFT_PRIMARY_CLICK_TIMEOUT_MILLIS));
    }

    private void waitForInputValue(Locator field, String expectedValue) {
        page.waitForCondition(() -> {
            try {
                return expectedValue.equals(field.inputValue());
            } catch (PlaywrightException e) {
                if (isGenuineLocatorError(e)) {
                    throw e;
                }
                return false;
            }
        });
    }

    private void dispatchPasswordInputEvents(Locator passwordField) {
        passwordField.evaluate("""
                element => {
                    element.dispatchEvent(new InputEvent('input', { bubbles: true, inputType: 'insertText' }));
                    element.dispatchEvent(new Event('change', { bubbles: true }));
                }
                """);
    }

    private void waitForMicrosoftPrimaryButtonToBeEnabled() {
        page.waitForCondition(() -> {
            try {
                Locator button = microsoftPrimaryButton();
                return button.isVisible() && button.isEnabled();
            } catch (PlaywrightException e) {
                if (isGenuineLocatorError(e)) {
                    throw e;
                }
                return false;
            }
        });
    }

    private String getAadUsernameConfig() {
        String value = EnvConfig.getEnv(AAD_USER_KEY);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(AAD_USER_KEY + " must be configured for automated Microsoft AD login.");
        }
        return value;
    }

    private String getAadPasswordConfig() {
        String value = EnvConfig.getEnv(AAD_PASSWORD_KEY);
        if (value != null && !value.isBlank()) {
            if (value.startsWith(BASE64_PREFIX)) {
                return decodePassword(AAD_PASSWORD_KEY, value.substring(BASE64_PREFIX.length()));
            }
            return requireNonBlankPassword(AAD_PASSWORD_KEY, value);
        }

        String encodedValue = EnvConfig.getEnv(AAD_PASSWORD_BASE64_KEY);
        if (encodedValue != null && !encodedValue.isBlank()) {
            return decodePassword(AAD_PASSWORD_BASE64_KEY, encodedValue);
        }

        throw new IllegalStateException(AAD_PASSWORD_KEY + " must be configured for automated Microsoft AD login.");
    }

    private String decodePassword(String key, String value) {
        try {
            return requireNonBlankPassword(key, new String(java.util.Base64.getDecoder().decode(value)));
        } catch (IllegalArgumentException exception) {
            throw new IllegalStateException(key + " must be plain text, prefixed with base64:, or supplied as AAD_PASSWORD_BASE64.", exception);
        }
    }

    private String requireNonBlankPassword(String key, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(key + " resolved to an empty value for automated Microsoft AD login.");
        }
        return value;
    }

    @Override
    public LoginPage waitForLoginPageToLoadCompletely() {
        super.waitForLoginPageToLoadCompletely();
        waitForLoginRouteToSettle();
        return this;
    }




}
