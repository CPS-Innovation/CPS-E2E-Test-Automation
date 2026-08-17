package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CompleteSubmissionPage extends BasePage {

    private static final Pattern INVESTIGATIVE_STAGE_ERROR_MESSAGE =
            Pattern.compile("investigative stage", Pattern.CASE_INSENSITIVE);
    private static final Pattern METHOD_ERROR_MESSAGE =
            Pattern.compile("method", Pattern.CASE_INSENSITIVE);
    private static final String CONTACT_DETAILS_NOT_PREPOPULATED_MESSAGE =
            "Prosecutor contact details are not prepopulated from CMS. "
                    + "Update the CMS contact preference page with prosecutor email and work phone number.";
    private static final String MG3_SUBMISSION_SUCCESS_STATUS = "Success";
    private static final Pattern MG3_SUBMISSION_SUCCESS_MESSAGE =
            Pattern.compile(
                    "Your\\s+review\\s+and\\s+MG3(/S)?\\s+document\\s+have\\s+been\\s+successfully\\s+submitted\\s+to\\s+CMS\\.",
                    Pattern.CASE_INSENSITIVE
            );
    private static final String MG3_CREATION_ERROR_MESSAGE = "We could not create the MG3 document.";
    private static final String MG3_CREATION_RETRY_LINK_TEXT = "You can try again now";
    private static final Pattern SUBMISSION_BUTTON_TEXT =
            Pattern.compile("Submit", Pattern.CASE_INSENSITIVE);
    private static final String FULL_CODE_TEST_REVIEW_TYPE = "Full Code Test";
    private static final String THRESHOLD_TEST_REVIEW_TYPE = "Threshold Test";
    private static final String EARLY_ADVICE_REVIEW_TYPE = "Early Advice";
    private static final String DECISION_TYPE_CHARGE = "Charge";
    private static final int MG3_CREATION_RETRY_ATTEMPTS = 3;
    private static final int MG3_CREATION_RETRY_DELAY_MILLIS = 20_000;
    private static final int MG3_SUBMISSION_SUCCESS_TIMEOUT_MILLIS = 65_000;
    private static final int MG3_SUBMISSION_POLL_INTERVAL_MILLIS = 1_000;
    private static final int REVIEW_SUBMISSION_SETTLE_DELAY_MILLIS = 20_000;
    private static final String SUBMITTING_REVIEW_TEXT = "Submitting ";
    private static final String MG3_CREATION_PROGRESS_TEXT = "document is being created";
    private static final String LOADING_TEXT = "Loading...";
    private static final String SUBMIT_WITH_MG3_BUTTON_SELECTOR = "#b18-b4-b3-SubmitWithMG3";
    private static final String VALIDATION_ERROR_SELECTOR =
            ".govuk-error-summary, .govuk-error-message, [id*='ErrorMessage']";


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
        return page.locator(".govuk-button")
                .filter(new Locator.FilterOptions().setHasText(SUBMISSION_BUTTON_TEXT))
                .first();
    }

    private Locator submitReviewWithMg3DocumentButton() {
        return page.locator(".govuk-button")
                .filter(new Locator.FilterOptions().setHasText(SUBMISSION_BUTTON_TEXT))
                .first();
    }

    private Locator taskCompletedMessage() {
        return page.locator("#TaskCompleted2");
    }

    private Locator statusMessage() {
        return page.locator("[role='status'], [role='alert'], .govuk-notification-banner, .alert, [id$='Alert']")
                .filter(new Locator.FilterOptions().setHasText(MG3_SUBMISSION_SUCCESS_STATUS))
                .first();
    }

    public CompleteSubmissionPage(PlaywrightContext context) {
        super(context);
    }


    public boolean completeReviewSubmission(
            Map<String, String> submissionData,
            Boolean createMg3Document,
            String reviewType,
            List<String> decisionTypes
    ) {
        waitForPageToLoad();
        assertChargeDecisionMatchesReviewType(reviewType, decisionTypes);

        boolean isMg3SubmissionRequired = isMg3SubmissionRequired(createMg3Document, decisionTypes);
        if (createMg3Document != null && !hasChargeDecision(decisionTypes)) {
            setCreateMg3Document(createMg3Document);
        }

        assertMethodDataMatchesReviewType(submissionData, reviewType);
        assertContactDetailsArePrepopulated();
        submitWithoutInvestigativeStageAndMethodAndAssertErrors(isMg3SubmissionRequired, reviewType);
        selectFromList(investigativeStageDropdown(), submissionData.get("Investigative stage"));
        selectMethodForReviewType(submissionData.get("Method"), reviewType);

        // Deliberately no response wait on the submission POST here: the "Submitting" spinner is only
        // visible while that request is in flight, so blocking on the response would consume the whole
        // window and the appear/disappear gate below would miss it. Gate on the spinner + text instead.
        clickSubmitReviewButton(isMg3SubmissionRequired);
        waitForSubmitReviewProgressToFinish(isMg3SubmissionRequired);
        waitForLoginPageToLoadCompletely();
        return isMg3SubmissionRequired;
    }

    public void verifyReviewSubmission(String reviewType, boolean createMg3Document) {
        assertReviewSubmissionSuccess(reviewType, createMg3Document);
    }

    private void assertReviewSubmissionSuccess(String reviewType, boolean createMg3Document) {
        if (createMg3Document) {
            assertMg3SubmissionSuccessAlert();
            return;
        }

        assertThat(statusMessage()).containsText(
                MG3_SUBMISSION_SUCCESS_STATUS,
                new com.microsoft.playwright.assertions.LocatorAssertions.ContainsTextOptions()
                        .setTimeout(REVIEW_SUBMISSION_SETTLE_DELAY_MILLIS)
        );
    }

    private void waitForPageToLoad() {
        assertThat(pageTitle()).containsText("Complete submission details");
    }

    private void selectFromList(Locator dropdown, String value) {
        dropdown.selectOption(value);
    }

    private void selectMethodForReviewType(String method, String reviewType) {
        if (isEarlyAdviceReview(reviewType)) {
            if (!isBlank(method)) {
                throw new IllegalArgumentException("Method must not be supplied for "
                        + EARLY_ADVICE_REVIEW_TYPE
                        + ". Remove the Method column/value from the feature file.");
            }
            return;
        }

        if (!isMethodRequiredForReviewType(reviewType)) {
            if (!isBlank(method)) {
                throw new IllegalArgumentException("Method is only supported for "
                        + THRESHOLD_TEST_REVIEW_TYPE + " and " + FULL_CODE_TEST_REVIEW_TYPE
                        + ". Actual review type: " + reviewType);
            }
            return;
        }

        if (isBlank(method)) {
            throw new IllegalArgumentException("Method is required for "
                    + reviewType
                    + ". Add the Method column/value to the feature file.");
        }

        if (methodDropdown().count() == 0) {
            throw new IllegalStateException("Method dropdown is not shown for "
                    + reviewType
                    + ". Check the Complete submission details page.");
        }

        selectFromList(methodDropdown(), method);
    }

    private void assertMethodDataMatchesReviewType(Map<String, String> submissionData, String reviewType) {
        String method = submissionData.get("Method");

        if (isEarlyAdviceReview(reviewType) && !isBlank(method)) {
            throw new IllegalArgumentException("Method must not be supplied for "
                    + EARLY_ADVICE_REVIEW_TYPE
                    + ". Remove the Method column/value from the feature file.");
        }

        if (isMethodRequiredForReviewType(reviewType) && isBlank(method)) {
            throw new IllegalArgumentException("Method is required for "
                    + reviewType
                    + ". Add the Method column/value to the feature file.");
        }

        if (!isMethodRequiredForReviewType(reviewType) && !isBlank(method)) {
            throw new IllegalArgumentException("Method is only supported for "
                    + THRESHOLD_TEST_REVIEW_TYPE + " and " + FULL_CODE_TEST_REVIEW_TYPE
                    + ". Actual review type: " + reviewType);
        }
    }

    private void assertContactDetailsArePrepopulated() {
        String email = emailField().inputValue();
        String phoneNumber = phoneNumberField().inputValue();

        if (isBlank(email) || isBlank(phoneNumber)) {
            throw new IllegalStateException(CONTACT_DETAILS_NOT_PREPOPULATED_MESSAGE);
        }
    }

    private void submitWithoutInvestigativeStageAndMethodAndAssertErrors(
            boolean createMg3Document,
            String reviewType
    ) {
        clickSubmitReviewButton(createMg3Document);
        assertValidationErrorMessage(INVESTIGATIVE_STAGE_ERROR_MESSAGE);
        if (isMethodRequiredForReviewType(reviewType)) {
            assertValidationErrorMessage(METHOD_ERROR_MESSAGE);
        }
    }

    private void assertValidationErrorMessage(Pattern expectedMessage) {
        Locator validationError = page.locator(VALIDATION_ERROR_SELECTOR)
                .filter(new Locator.FilterOptions().setHasText(expectedMessage))
                .first();
        assertThat(validationError).isVisible();
    }

    private void clickSubmitReviewButton(boolean createMg3Document) {
        if (createMg3Document) {
            Locator submitWithMg3Button = submitReviewWithMg3DocumentButton();
            assertThat(submitWithMg3Button)
                    .isVisible(new com.microsoft.playwright.assertions.LocatorAssertions.IsVisibleOptions()
                            .setTimeout(25_000));
            submitWithMg3Button.scrollIntoViewIfNeeded();
            submitWithMg3Button.click();
        } else {
            Locator submitButton = submitReviewButton();
            assertThat(submitButton)
                    .isVisible(new com.microsoft.playwright.assertions.LocatorAssertions.IsVisibleOptions()
                            .setTimeout(25_000));
            submitButton.scrollIntoViewIfNeeded();
            submitButton.click();
        }
    }

    private void waitForSubmitReviewProgressToFinish(boolean createMg3Document) {
        if (createMg3Document) {
            waitForMg3SubmitReviewProgressToFinish();
            return;
        }

        waitForStandardSubmitReviewProgressToFinish();
    }

    private void waitForStandardSubmitReviewProgressToFinish() {
        waitForVisibleTextToAppear(SUBMITTING_REVIEW_TEXT, MG3_SUBMISSION_SUCCESS_TIMEOUT_MILLIS);
        waitUntilVisibleTextIsGone(SUBMITTING_REVIEW_TEXT, MG3_SUBMISSION_SUCCESS_TIMEOUT_MILLIS);
        waitUntilVisibleTextIsGone(LOADING_TEXT, MG3_SUBMISSION_SUCCESS_TIMEOUT_MILLIS);
    }

    private void waitForMg3SubmitReviewProgressToFinish() {
        if (!waitForMg3CreationProgressSuccessOrRetryAvailable()) {
            assertMg3SubmissionSuccessAlert();
            return;
        }

        waitUntilVisibleTextIsGone(MG3_CREATION_PROGRESS_TEXT, MG3_SUBMISSION_SUCCESS_TIMEOUT_MILLIS);
        waitUntilVisibleTextIsGone(LOADING_TEXT, MG3_SUBMISSION_SUCCESS_TIMEOUT_MILLIS);
        assertMg3SubmissionSuccessAlert();
    }

    private boolean waitForMg3CreationProgressSuccessOrRetryAvailable() {
        long deadline = System.currentTimeMillis() + MG3_SUBMISSION_SUCCESS_TIMEOUT_MILLIS;

        while (System.currentTimeMillis() < deadline) {
            if (isVisibleTextPresent(MG3_CREATION_PROGRESS_TEXT)) {
                return true;
            }

            if (isMg3SubmissionSuccessVisible() || isMg3CreationRetryAvailable()) {
                return false;
            }

            page.waitForTimeout(MG3_SUBMISSION_POLL_INTERVAL_MILLIS);
        }

        return false;
    }

    private void assertMg3SubmissionSuccessAlert() {
        if (waitForMg3SubmissionSuccessOrRetryAvailable()) {
            return;
        }

        int retryAttempt = 0;
        while (retryAttempt < MG3_CREATION_RETRY_ATTEMPTS && isMg3CreationRetryAvailable()) {
            retryAttempt++;
            retryMg3Creation();
            page.waitForTimeout(MG3_CREATION_RETRY_DELAY_MILLIS);

            if (waitForMg3SubmissionSuccessOrRetryAvailable()) {
                return;
            }
        }

        com.microsoft.playwright.assertions.LocatorAssertions.ContainsTextOptions textTimeout =
                new com.microsoft.playwright.assertions.LocatorAssertions.ContainsTextOptions().setTimeout(25_000);
        assertThat(mg3SubmissionSuccessAlert()).containsText(MG3_SUBMISSION_SUCCESS_STATUS, textTimeout);
        assertThat(mg3SubmissionSuccessAlert()).containsText(MG3_SUBMISSION_SUCCESS_MESSAGE, textTimeout);
    }

    private boolean waitForMg3SubmissionSuccessOrRetryAvailable() {
        long deadline = System.currentTimeMillis() + MG3_SUBMISSION_SUCCESS_TIMEOUT_MILLIS;

        while (System.currentTimeMillis() < deadline) {
            if (isMg3SubmissionSuccessVisible()) {
                return true;
            }

            if (isMg3CreationRetryAvailable()) {
                return false;
            }

            page.waitForTimeout(MG3_SUBMISSION_POLL_INTERVAL_MILLIS);
        }

        return false;
    }

    private void waitUntilVisibleTextIsGone(String text, int timeoutMillis) {
        long deadline = System.currentTimeMillis() + timeoutMillis;

        while (System.currentTimeMillis() < deadline) {
            if (!isVisibleTextPresent(text)) {
                return;
            }

            page.waitForTimeout(MG3_SUBMISSION_POLL_INTERVAL_MILLIS);
        }

        throw new IllegalStateException("Visible text did not disappear within "
                + timeoutMillis + "ms: " + text);
    }

    private void waitForVisibleTextToAppear(String text, int timeoutMillis) {
        long deadline = System.currentTimeMillis() + timeoutMillis;

        while (System.currentTimeMillis() < deadline) {
            if (isVisibleTextPresent(text)) {
                return;
            }

            page.waitForTimeout(MG3_SUBMISSION_POLL_INTERVAL_MILLIS);
        }

        throw new IllegalStateException("Visible text did not appear within "
                + timeoutMillis + "ms: " + text);
    }

    private boolean isVisibleTextPresent(String text) {
        return Boolean.TRUE.equals(page.evaluate("""
                expectedText => {
                    const isVisible = element => {
                        const style = window.getComputedStyle(element);
                        const rect = element.getBoundingClientRect();
                        return style.display !== 'none'
                            && style.visibility !== 'hidden'
                            && rect.width > 0
                            && rect.height > 0;
                    };

                    return Array.from(document.querySelectorAll('body *')).some(element =>
                        isVisible(element)
                            && (element.innerText || element.textContent || '').includes(expectedText)
                    );
                }
                """, text));
    }

    private boolean isMg3SubmissionSuccessVisible() {
        try {
            String alertText = mg3SubmissionSuccessAlert()
                    .innerText(new Locator.InnerTextOptions().setTimeout(1000));
            return alertText.contains(MG3_SUBMISSION_SUCCESS_STATUS)
                    && MG3_SUBMISSION_SUCCESS_MESSAGE.matcher(alertText).find();
        } catch (PlaywrightException e) {
            if (isGenuineLocatorError(e)) {
                throw e;
            }
            return false;
        }
    }

    private boolean isMg3CreationRetryAvailable() {
        try {
            String alertText = mg3CreationErrorAlert()
                    .innerText(new Locator.InnerTextOptions().setTimeout(1000));
            return alertText.contains(MG3_CREATION_ERROR_MESSAGE)
                    && mg3CreationRetryLink().isVisible();
        } catch (PlaywrightException e) {
            if (isGenuineLocatorError(e)) {
                throw e;
            }
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

    private void assertChargeDecisionMatchesReviewType(String reviewType, List<String> decisionTypes) {
        if (hasChargeDecision(decisionTypes) && !isMethodRequiredForReviewType(reviewType)) {
            throw new IllegalArgumentException(DECISION_TYPE_CHARGE
                    + " decision type is only supported for "
                    + THRESHOLD_TEST_REVIEW_TYPE + " and " + FULL_CODE_TEST_REVIEW_TYPE
                    + ". Actual review type: " + reviewType);
        }
    }

    private boolean isMg3SubmissionRequired(Boolean createMg3Document, List<String> decisionTypes) {
        return hasChargeDecision(decisionTypes) || Boolean.TRUE.equals(createMg3Document);
    }

    private boolean hasChargeDecision(List<String> decisionTypes) {
        return decisionTypes != null
                && decisionTypes.stream()
                .map(this::normalizeText)
                .anyMatch(DECISION_TYPE_CHARGE::equalsIgnoreCase);
    }

    private boolean isMethodRequiredForReviewType(String reviewType) {
        String normalizedReviewType = normalizeText(reviewType);
        return FULL_CODE_TEST_REVIEW_TYPE.equalsIgnoreCase(normalizedReviewType)
                || THRESHOLD_TEST_REVIEW_TYPE.equalsIgnoreCase(normalizedReviewType);
    }

    private boolean isEarlyAdviceReview(String reviewType) {
        return EARLY_ADVICE_REVIEW_TYPE.equalsIgnoreCase(normalizeText(reviewType));
    }

    private String normalizeText(String value) {
        return value == null ? "" : value.replaceAll("\\s+", " ").trim();
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
