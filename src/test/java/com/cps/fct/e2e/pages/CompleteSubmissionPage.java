package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.common.FakerUtils;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CompleteSubmissionPage extends BasePage {

    private static final String EMPTY_EMAIL_ERROR_MESSAGE = "Enter prosecutor email";
    private static final String INVALID_EMAIL_ERROR_MESSAGE =
            "Enter prosecutor email in the correct format, like firstname.lastname@cps.gov.uk";
    private static final String EMPTY_PHONE_NUMBER_ERROR_MESSAGE = "Enter work phone number";
    private static final String INVALID_PHONE_NUMBER_ERROR_MESSAGE = "Enter work phone number in the correct format";
    private static final String MG3_SUBMISSION_SUCCESS_STATUS = "Success";
    private static final String MG3_SUBMISSION_SUCCESS_MESSAGE =
            "Your review and MG3 document have been successfully submitted to CMS.";
    private static final String MG3_CREATION_ERROR_MESSAGE = "We could not create the MG3 document.";
    private static final String MG3_CREATION_RETRY_LINK_TEXT = "You can try again now";
    private static final int MG3_CREATION_RETRY_ATTEMPTS = 3;
    private static final int MG3_CREATION_RETRY_DELAY_MILLIS = 20_000;
    private static final String INVALID_EMAIL = "invalid-email";
    private static final String INCOMPLETE_PHONE_NUMBER = "01234";


    private Locator pageTitle() {
        return page.locator("h1");
    }

    private Locator investigativeStageDropdown() {
        return page.locator("select[id*='InvestigativeStageDropdown']");
    }

    private Locator methodDropdown() {
        return page.locator("select[id*='MethodDropdown']");
    }

    private Locator emailField() {
        return page.locator("span.input-email input");
    }

    private Locator phoneNumberField() {
        return page.locator("span.input-tel input");
    }

    private Locator createMg3DocumentCheckbox() {
        return page.locator("[id$='Checkbox_ShowCreateMG3']");
    }

    private Locator emailErrorMessage() {
        return page.locator("[id$='ErrorMessage5']");
    }

    private Locator phoneNumberErrorMessage() {
        return page.locator("[id$='ErrorMessage6']");
    }

    private Locator mg3SubmissionSuccessAlert() {
        return page.locator("[role='alert'], .govuk-notification-banner, .alert, [id$='Alert']")
                .filter(new Locator.FilterOptions().setHasText(MG3_SUBMISSION_SUCCESS_STATUS))
                .first();
    }

    private Locator mg3CreationErrorAlert() {
        return page.locator("[role='alert'], .govuk-notification-banner, .alert, [id$='Alert']")
                .filter(new Locator.FilterOptions().setHasText(MG3_CREATION_ERROR_MESSAGE))
                .first();
    }

    private Locator mg3CreationRetryLink() {
        return page.getByText(MG3_CREATION_RETRY_LINK_TEXT).first();
    }

    private Locator submitReviewButton() {
        return submitReviewButton("Submit review");
    }

    private Locator submitReviewWithMg3DocumentButton() {
        return submitReviewButton("Submit review with MG3 document");
    }

    private Locator submitReviewButton(String buttonText) {
        return page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(buttonText).setExact(true)
        );
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


    public void completeReviewSubmission(Map<String, String> submissionData, boolean createMg3Document) {
        waitForPageToLoad();

        selectFromList(investigativeStageDropdown(), submissionData.get("Investigative stage"));
        selectFromList(methodDropdown(), submissionData.get("Method"));
        setCreateMg3Document(createMg3Document);

        submitWithoutContactDetailsAndAssertErrors(createMg3Document);
        submitWithInvalidContactDetailsAndAssertErrors(createMg3Document);
        enterContactDetails();

        clickSubmitReviewButton(createMg3Document);
        waitUntilSpinnersAreGone("Submitting review...", "Loading...");
        waitForLoginPageToLoadCompletely();
    }

    public void verifyReviewSubmission(String reviewType, boolean createMg3Document) {
        page.waitForTimeout(20000); // To-Do: Replace with proper wait - use wait for submit http request to complete
        assertReviewSubmissionSuccess(reviewType, createMg3Document);
    }

    private void assertReviewSubmissionSuccess(String reviewType, boolean createMg3Document) {
        if (createMg3Document) {
            assertMg3SubmissionSuccessAlert();
            return;
        }

        assertThat(taskCompletedMessage()).containsText(String.format("%s sent", reviewType));
        assertThat(statusMessage()).containsText("Success");
    }

    private void waitForPageToLoad() {
        assertThat(pageTitle()).containsText("Complete submission details");
    }

    private void selectFromList(Locator dropdown, String value) {
        dropdown.selectOption(value);
    }

    private void enterContactDetails() {
        enterContactDetails(FakerUtils.cpsEmail(), FakerUtils.homePhone());
    }

    private void enterContactDetails(String email, String phoneNumber) {
        emailField().fill(email);
        phoneNumberField().fill(phoneNumber);
    }

    private void submitWithoutContactDetailsAndAssertErrors(boolean createMg3Document) {
        enterContactDetails("", "");
        clickSubmitReviewButton(createMg3Document);
        assertThat(emailErrorMessage()).containsText(EMPTY_EMAIL_ERROR_MESSAGE);
        assertThat(phoneNumberErrorMessage()).containsText(EMPTY_PHONE_NUMBER_ERROR_MESSAGE);
    }

    private void submitWithInvalidContactDetailsAndAssertErrors(boolean createMg3Document) {
        enterContactDetails(INVALID_EMAIL, INCOMPLETE_PHONE_NUMBER);
        clickSubmitReviewButton(createMg3Document);
        assertThat(emailErrorMessage()).containsText(INVALID_EMAIL_ERROR_MESSAGE);
        assertThat(phoneNumberErrorMessage()).containsText(INVALID_PHONE_NUMBER_ERROR_MESSAGE);
    }

    private void clickSubmitReviewButton(boolean createMg3Document) {
        if (createMg3Document) {
            submitReviewWithMg3DocumentButton().click();
        } else {
            submitReviewButton().click();
        }
    }

    private void assertMg3SubmissionSuccessAlert() {
        for (int retryAttempt = 0; retryAttempt <= MG3_CREATION_RETRY_ATTEMPTS; retryAttempt++) {
            if (isMg3SubmissionSuccessVisible()) {
                return;
            }

            if (retryAttempt == MG3_CREATION_RETRY_ATTEMPTS || !isMg3CreationRetryAvailable()) {
                break;
            }

            retryMg3Creation();
            page.waitForTimeout(MG3_CREATION_RETRY_DELAY_MILLIS);
        }

        assertThat(mg3SubmissionSuccessAlert()).containsText(MG3_SUBMISSION_SUCCESS_STATUS);
        assertThat(mg3SubmissionSuccessAlert()).containsText(MG3_SUBMISSION_SUCCESS_MESSAGE);
    }

    private boolean isMg3SubmissionSuccessVisible() {
        try {
            String alertText = mg3SubmissionSuccessAlert()
                    .innerText(new Locator.InnerTextOptions().setTimeout(1000));
            return alertText.contains(MG3_SUBMISSION_SUCCESS_STATUS)
                    && alertText.contains(MG3_SUBMISSION_SUCCESS_MESSAGE);
        } catch (Exception ignored) {
            return false;
        }
    }

    private boolean isMg3CreationRetryAvailable() {
        try {
            String alertText = mg3CreationErrorAlert()
                    .innerText(new Locator.InnerTextOptions().setTimeout(1000));
            return alertText.contains(MG3_CREATION_ERROR_MESSAGE)
                    && mg3CreationRetryLink().isVisible();
        } catch (Exception ignored) {
            return false;
        }
    }

    private void retryMg3Creation() {
        Locator retryLink = mg3CreationRetryLink();
        retryLink.scrollIntoViewIfNeeded();
        retryLink.click();
        waitForLoginPageToLoadCompletely();
    }

    private void setCreateMg3Document(boolean createMg3Document) {
        Locator checkbox = createMg3DocumentCheckbox();
        if (createMg3Document && !checkbox.isChecked()) {
            checkbox.check(new Locator.CheckOptions().setForce(true));
        } else if (!createMg3Document && checkbox.isChecked()) {
            checkbox.uncheck(new Locator.UncheckOptions().setForce(true));
        }
    }
}
