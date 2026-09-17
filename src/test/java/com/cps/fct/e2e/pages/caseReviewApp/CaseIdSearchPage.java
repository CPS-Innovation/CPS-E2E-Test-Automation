package com.cps.fct.e2e.pages.caseReviewApp;

import com.cps.fct.e2e.pages.BasePage;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.assertj.core.api.SoftAssertions;

import java.net.URI;

import static com.cps.fct.e2e.utils.playwright.PlaywrightNetworkUtils.waitForResponseTriggeredBy;

public class CaseIdSearchPage extends BasePage {

    private static final int LANDING_PAGE_TIMEOUT_MILLIS = 30_000;
    private static final int SEARCH_PAGE_READY_TIMEOUT_MILLIS = 20_000;
    private static final int MAX_SEARCH_PAGE_READY_ATTEMPTS = 3;
    private static final int SEARCH_POLL_INTERVAL_MILLIS = 250;
    private static final int MAX_SEARCH_ATTEMPTS = 5;
    private static final int CASE_NOT_FOUND_BACKOFF_MILLIS = 3_000;
    private static final String CASE_NOT_FOUND_TEXT = "No Case Found";
    private static final String CASE_ID_RADIO_SELECTOR = "#Radio_CaseID-input";
    private static final String CASE_ID_INPUT_SELECTOR = "#Input_CaseID2";
    private static final String URN_RADIO_SELECTOR = "#Radio_URN-input";
    private static final String URN_INPUT_SELECTOR = "#Input_URN";
    // The View Case click posts a case-existence check before navigating to the landing page. It is a
    // stable action name (not tied to review type or environment), so wait on it to confirm the search
    // request resolved before the URL-outcome polling below.
    private static final String CASE_EXISTS_CHECK_ENDPOINT = "DataActionCheckIfCaseExists";
    private static final String POST_METHOD = "POST";
    private static final int SUCCESS_STATUS = 200;

    private enum SearchOutcome { LANDED, CASE_NOT_FOUND, TIMED_OUT }

    public CaseIdSearchPage(PlaywrightContext context) {
        super(context);
    }

    public void assertPageLoadSuccessful() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(page.getByText("View a Case").isVisible()).isTrue();
        softly.assertAll();
    }

    private CaseIdSearchPage inputCaseId(String caseId) {
        selectSearchTypeAndWaitForInput(CASE_ID_RADIO_SELECTOR, CASE_ID_INPUT_SELECTOR);
        page.locator(CASE_ID_INPUT_SELECTOR).fill(caseId,
                new Locator.FillOptions().setTimeout(SEARCH_PAGE_READY_TIMEOUT_MILLIS));
        return this;
    }

    private CaseIdSearchPage inputUrn(String urn) {
        selectSearchTypeAndWaitForInput(URN_RADIO_SELECTOR, URN_INPUT_SELECTOR);
        page.locator(URN_INPUT_SELECTOR).fill(urn,
                new Locator.FillOptions().setTimeout(SEARCH_PAGE_READY_TIMEOUT_MILLIS));
        return this;
    }

    private void selectSearchTypeAndWaitForInput(String radioSelector, String inputSelector) {
        for (int attempt = 1; attempt <= MAX_SEARCH_PAGE_READY_ATTEMPTS; attempt++) {
            try {
                waitForSearchPageControl(radioSelector);
                selectSearchType(radioSelector);
                waitForSearchPageControl(inputSelector);
                return;
            } catch (PlaywrightException e) {
                if (isGenuineLocatorError(e) || attempt == MAX_SEARCH_PAGE_READY_ATTEMPTS) {
                    throw e;
                }
                page.reload();
                waitForCaseSearchPageToLoad();
                waitUntilLoadingIndicatorIsGone();
            }
        }
    }

    private void waitForSearchPageControl(String selector) {
        page.locator(selector).waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(SEARCH_PAGE_READY_TIMEOUT_MILLIS));
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


    private void clickOnViewCaseButton() {
        clickButton("View Case");
    }

    public void searchCase(String caseId) {
        waitForCaseSearchPageToLoad().assertPageLoadSuccessful();
        submitSearchWithRetries(() -> inputCaseId(caseId).clickOnViewCaseButton(), "Case ID " + caseId);
    }

    public String searchCaseUrn(String urn) {
        waitForCaseSearchPageToLoad().assertPageLoadSuccessful();
        submitSearchWithRetries(() -> inputUrn(urn).clickOnViewCaseButton(), "URN " + urn);
        return cmsCaseIdFromCurrentUrl();
    }

    private void submitSearchWithRetries(Runnable submitSearch, String searchDescription) {
        for (int attempt = 1; attempt <= MAX_SEARCH_ATTEMPTS; attempt++) {
            waitForResponseTriggeredBy(
                    page,
                    "Search case (" + searchDescription + ")",
                    CASE_EXISTS_CHECK_ENDPOINT,
                    POST_METHOD,
                    SUCCESS_STATUS,
                    submitSearch
            );
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
        } catch (PlaywrightException e) {
            if (isGenuineLocatorError(e)) {
                throw e;
            }
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

    public CaseIdSearchPage waitForCaseSearchPageToLoad() {
        waitForTextToAppear("View a Case");
        return this;
    }


}
