package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

import static org.assertj.core.api.Assertions.assertThat;

public class CaseReviewPage extends BasePage {


    public CaseReviewPage(PlaywrightContext context) {
        super(context);
    }

    private Locator reviewTypeText() {
        return page.locator("text=day PCD Review");
    }

    private Locator startReviewButton() {
        return page.locator("button:has-text('Start review')");
    }


    @Override
    public CaseReviewPage waitForLoginPageToLoadCompletely() {
        waitForElement(page, AriaRole.HEADING, "Reviews");
        waitForElement(page, AriaRole.HEADING, "Select test");
        return this;
    }

    public CaseReviewPage assertPageLoadSuccessful(String caseId, String typeOfReview) {
        String urlPattern = String.format("/LandingPage?IsSubmitted=false&CMSCaseId=%s", caseId);
        assertUrlContains(page, urlPattern);
        assertThat(reviewTypeText().innerText()).isEqualTo(typeOfReview);
        return this;
    }

    public CaseReviewPage clickReviewButton() {
        startReviewButton().click();
        return this;
    }

    public void startReview(String caseId, String typeOfReview) {
        waitForLoginPageToLoadCompletely().
                assertPageLoadSuccessful(caseId, typeOfReview)
                .clickReviewButton()
                .waitUntilLoadingIndicatorIsGone();
    }

    @Override
    public void waitUntilLoadingIndicatorIsGone() {
        waitUntilLoadingIndicatorIsGone("Starting review...");
    }
}