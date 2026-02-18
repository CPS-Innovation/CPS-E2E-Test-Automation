package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;


public class LoginPage extends BasePage {

    public LoginPage(PlaywrightContext context) {
        super(context);
    }

    public void loginIntoCaseReview(
            String username, String password) throws InterruptedException {

         navigateToLoginPage().
                 waitForLoginPageToLoadCompletely()
                .enterUsername(username)
                .enterPassword(password)
                .clickSignInButton()
                .verifyNoLoginErrorsPresent();
    }

    private LoginPage navigateToLoginPage() throws InterruptedException {
        page.navigate(EnvConfig.get("CASE_REVIEW_URL"));
        return this;
    }

    private LoginPage enterUsername(String username) {
        enterText("Username", username);
        return this;
    }

    private LoginPage enterPassword(String password) {
        enterText("Password", password);
        return this;
    }

    private void verifyNoLoginErrorsPresent() {
        assertTextIsNotVisible("Unauthorized");
    }


    private LoginPage clickSignInButton() {
        clickButton("Sign in");
        return this;
    }

    @Override
    public LoginPage waitForLoginPageToLoadCompletely() {
        super.waitForLoginPageToLoadCompletely();
        return this;
    }




}



