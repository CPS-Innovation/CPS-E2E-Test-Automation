package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import org.assertj.core.api.SoftAssertions;

import java.net.URI;

public class CaseIdSearchPage extends BasePage {

    private static final int LANDING_PAGE_TIMEOUT_MILLIS = 30_000;
    private static final String CASE_ID_RADIO_SELECTOR = "#Radio_CaseID-input";
    private static final String CASE_ID_INPUT_SELECTOR = "#Input_CaseID2";
    private static final String URN_RADIO_SELECTOR = "#Radio_URN-input";
    private static final String URN_INPUT_SELECTOR = "#Input_URN";

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
        selectSearchType(CASE_ID_RADIO_SELECTOR);
        page.locator(CASE_ID_INPUT_SELECTOR).fill(caseId);
        return this;
    }

    private CaseIdSearchPage inputUrn(String urn) {
        selectSearchType(URN_RADIO_SELECTOR);
        page.locator(URN_INPUT_SELECTOR).fill(urn);
        return this;
    }

    private void selectSearchType(String radioSelector) {
        Locator radio = page.locator(radioSelector);
        if (!radio.isChecked()) {
            radio.check(new Locator.CheckOptions().setForce(true));
        }
    }


    private CaseIdSearchPage clickOnViewCaseButton() {
        clickButton("View Case");
        return this;
    }

    public void searchCase(String caseId) {
        waitForLoginPageToLoadCompletely()
                 .assertPageLoadSuccessful()
                 .inputCaseId(caseId)
                 .clickOnViewCaseButton();
        waitUntilLoadingIndicatorIsGone();
        waitForLandingPage();
    }

    public String searchCaseUrn(String urn) {
        waitForLoginPageToLoadCompletely()
                .assertPageLoadSuccessful()
                .inputUrn(urn)
                .clickOnViewCaseButton();
        waitUntilLoadingIndicatorIsGone();
        waitForLandingPage();
        return cmsCaseIdFromCurrentUrl();
    }

    private CaseIdSearchPage waitForLandingPage() {
        long deadline = System.currentTimeMillis() + LANDING_PAGE_TIMEOUT_MILLIS;
        while (System.currentTimeMillis() < deadline) {
            if (page.url().contains("/LandingPage") && page.url().contains("CMSCaseId=")) {
                return this;
            }
            page.waitForTimeout(250);
        }

        throw new IllegalStateException("Landing page was not reached after searching for a case. Current URL: " + page.url());
    }

    private String cmsCaseIdFromCurrentUrl() {
        String query = URI.create(page.url()).getQuery();
        if (query == null || query.isBlank()) {
            throw new IllegalStateException("CMSCaseId was not found because the current URL has no query string: " + page.url());
        }

        for (String queryParam : query.split("&")) {
            if (queryParam.startsWith("CMSCaseId=")) {
                return queryParam.substring("CMSCaseId=".length());
            }
        }

        throw new IllegalStateException("CMSCaseId was not found in current URL: " + page.url());
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
