package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

import static com.cps.fct.e2e.utils.playwright.PlaywrightNetworkUtils.waitForResponseTriggeredBy;
import static org.assertj.core.api.Assertions.assertThat;

public class CaseReviewPage extends BasePage {

    private static final String ACTION_START_REVIEW_ENDPOINT = "ActionStartNewReview";
    private static final String POST_METHOD = "POST";
    private static final int SUCCESS_STATUS = 200;

    public CaseReviewPage(PlaywrightContext context) {
        super(context);
    }

    // Matches the shared "PCD Review" suffix so both numeric reviews ("5 day PCD Review",
    // "28 day PCD Review") and word-based reviews ("Priority PCD Review") resolve. The exact
    // review type is still verified via the innerText assertion in assertPageLoadSuccessful.
    private Locator reviewTypeText() {
        return page.locator("text=PCD Review");
    }

    private Locator startReviewButton() {
        return page.locator("button:has-text('Start review')");
    }

    private Locator resumeReviewButton() {
        return page.locator("button:has-text('Resume')");
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

    public CaseReviewPage clickResumeButton() {
        resumeReviewButton().click();
        return this;
    }

    public void startReview(String caseId, String typeOfReview) {
        waitForLoginPageToLoadCompletely()
                .assertPageLoadSuccessful(caseId, typeOfReview);
        waitForResponseTriggeredBy(
                page,
                "Start review",
                ACTION_START_REVIEW_ENDPOINT,
                POST_METHOD,
                SUCCESS_STATUS,
                this::clickReviewButton
        );
        waitUntilLoadingIndicatorIsGone();
    }

    public void resumeReview(String caseId, String typeOfReview) {
        waitForLoginPageToLoadCompletely().
                assertPageLoadSuccessful(caseId, typeOfReview)
                .clickResumeButton()
                .waitUntilLoadingIndicatorIsGone();
    }

    @Override
    public void waitUntilLoadingIndicatorIsGone() {
        waitUntilLoadingIndicatorIsGone("Starting review...");
    }
}
