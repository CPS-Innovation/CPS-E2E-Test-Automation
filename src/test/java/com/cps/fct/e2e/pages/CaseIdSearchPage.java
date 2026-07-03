package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import org.assertj.core.api.SoftAssertions;

import java.net.URI;

public class CaseIdSearchPage extends BasePage {

    private static final int LANDING_PAGE_TIMEOUT_MILLIS = 30_000;
    private static final int SEARCH_POLL_INTERVAL_MILLIS = 250;
    private static final int MAX_SEARCH_ATTEMPTS = 5;
    private static final int CASE_NOT_FOUND_BACKOFF_MILLIS = 3_000;
    private static final String CASE_NOT_FOUND_TEXT = "No Case Found";
    private static final String CASE_ID_RADIO_SELECTOR = "#Radio_CaseID-input";
    private static final String CASE_ID_INPUT_SELECTOR = "#Input_CaseID2";
    private static final String URN_RADIO_SELECTOR = "#Radio_URN-input";
    private static final String URN_INPUT_SELECTOR = "#Input_URN";

    private enum SearchOutcome { LANDED, CASE_NOT_FOUND, TIMED_OUT }

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
        boolean selected = Boolean.TRUE.equals(page.evaluate("""
                selector => {
                    const radio = document.querySelector(selector);
                    if (!radio) {
                        return false;
                    }

                    const label = radio.id
                        ? document.querySelector(`label[for="${radio.id}"]`)
                        : null;
                    const clickable = label || radio;

                    clickable.scrollIntoView({ block: 'center', inline: 'nearest' });
                    clickable.click();

                    if (!radio.checked) {
                        radio.checked = true;
                        radio.dispatchEvent(new Event('input', { bubbles: true }));
                        radio.dispatchEvent(new Event('change', { bubbles: true }));
                    }

                    return radio.checked;
                }
                """, radioSelector));

        if (!selected) {
            throw new IllegalStateException("Search type radio was not selected: " + radioSelector);
        }
    }


    private CaseIdSearchPage clickOnViewCaseButton() {
        clickButton("View Case");
        return this;
    }

    public void searchCase(String caseId) {
        waitForLoginPageToLoadCompletely().assertPageLoadSuccessful();
        submitSearchWithRetries(() -> inputCaseId(caseId).clickOnViewCaseButton(), "Case ID " + caseId);
    }

    public String searchCaseUrn(String urn) {
        waitForLoginPageToLoadCompletely().assertPageLoadSuccessful();
        submitSearchWithRetries(() -> inputUrn(urn).clickOnViewCaseButton(), "URN " + urn);
        return cmsCaseIdFromCurrentUrl();
    }

    private void submitSearchWithRetries(Runnable submitSearch, String searchDescription) {
        for (int attempt = 1; attempt <= MAX_SEARCH_ATTEMPTS; attempt++) {
            submitSearch.run();
            waitUntilLoadingIndicatorIsGone();
            SearchOutcome outcome = waitForSearchOutcome();

            if (outcome == SearchOutcome.LANDED) {
                return;
            }
            if (outcome == SearchOutcome.CASE_NOT_FOUND) {
                if (attempt == MAX_SEARCH_ATTEMPTS) {
                    throw new IllegalStateException("Search for " + searchDescription
                            + " returned '" + CASE_NOT_FOUND_TEXT + "' after " + attempt + " attempts.");
                }
                page.waitForTimeout(CASE_NOT_FOUND_BACKOFF_MILLIS);
                continue;
            }
            throw new IllegalStateException("Landing page was not reached after searching for "
                    + searchDescription + ". Current URL: " + page.url());
        }
    }

    private SearchOutcome waitForSearchOutcome() {
        long deadline = System.currentTimeMillis() + LANDING_PAGE_TIMEOUT_MILLIS;
        while (System.currentTimeMillis() < deadline) {
            String url = page.url();
            if (isOnLandingPage(url)) {
                return SearchOutcome.LANDED;
            }
            if (!hasLeftSearchPage(url) && isCaseNotFoundVisible()) {
                return SearchOutcome.CASE_NOT_FOUND;
            }
            page.waitForTimeout(SEARCH_POLL_INTERVAL_MILLIS);
        }
        return SearchOutcome.TIMED_OUT;
    }

    private boolean isOnLandingPage(String url) {
        return url.contains("/LandingPage") && url.contains("CMSCaseId=");
    }

    private boolean hasLeftSearchPage(String url) {
        return url.contains("/LandingPage");
    }

    private boolean isCaseNotFoundVisible() {
        try {
            return page.getByText(CASE_NOT_FOUND_TEXT).first().isVisible();
        } catch (RuntimeException ignored) {
            return false;
        }
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
