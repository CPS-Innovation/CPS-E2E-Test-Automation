package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;

import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CompleteSubmissionPage extends BasePage {


    private Locator pageTitle() {
        return page.locator("h1");
    }

    private Locator investigativeStageDropdown() {
        return page.locator("select[id*='InvestigativeStageDropdown']");
    }

    private Locator methodDropdown() {
        return page.locator("select[id*='MethodDropdown']");
    }

    private Locator submitReviewButton() {
        return page.locator("button:has-text('Submit review')");
    }

    private Locator taskCompletedMessage() {
        return page.locator("#TaskCompleted2");
    }

    private Locator statusMessage() {
        return page.locator("[role='status']");
    }

    public CompleteSubmissionPage(PlaywrightContext context) {
        super(context);
    }


    public void completeReviewSubmission(Map<String, String> submissionData) {
        waitForPageToLoad();

        selectFromList(investigativeStageDropdown(), submissionData.get("Investigative stage"));
        selectFromList(methodDropdown(), submissionData.get("Method"));

        submitReviewButton().click();
        waitUntilSpinnersAreGone("Submitting review...", "Loading...");
        waitForLoginPageToLoadCompletely();
    }

    public void verifyReviewSubmission(String reviewType) {
        page.waitForTimeout(20000); // To-Do: Replace with proper wait - use wait for submit http request to complete
        assertThat(taskCompletedMessage()).containsText(String.format("%s sent", reviewType));
        assertThat(statusMessage()).containsText("Success");
    }

    private void waitForPageToLoad() {
        assertThat(pageTitle()).containsText("Complete submission details");
    }

    private void selectFromList(Locator dropdown, String value) {
        dropdown.selectOption(value);
    }
}