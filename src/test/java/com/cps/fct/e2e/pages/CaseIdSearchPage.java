package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import org.assertj.core.api.SoftAssertions;

public class CaseIdSearchPage extends BasePage {

    public CaseIdSearchPage(PlaywrightContext context) {
        super(context);
    }

    public CaseIdSearchPage assertPageLoadSuccessful() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(page.getByText("View a Case").isVisible()).isTrue();
        softly.assertAll();
        return this;
    }

    private CaseIdSearchPage inputCaseId(String caseId) {
        enterText("Case Id", caseId);
        return this;
    }


    private CaseIdSearchPage clickOnViewCaseButton() {
        clickButton("View Case");
        return this;
    }

    public void searchCase(String caseId) {
        waitForLoginPageToLoadCompletely()
                 .assertPageLoadSuccessful()
                 .inputCaseId(caseId)
                 .clickOnViewCaseButton()
                 .waitUntilLoadingIndicatorIsGone();
    }

    @Override
    public void waitUntilLoadingIndicatorIsGone() {
        waitUntilLoadingIndicatorIsGone("Searching for Case...");
        waitUntilLoadingIndicatorIsGone("Loading...");
    }

    @Override
    public CaseIdSearchPage waitForLoginPageToLoadCompletely() {
        waitForTextToAppear("View a Case");
        return this;
    }


}