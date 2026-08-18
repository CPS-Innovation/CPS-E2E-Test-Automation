package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.options.AriaRole;

import static com.cps.fct.e2e.utils.playwright.PlaywrightNetworkUtils.waitForResponseTriggeredBy;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SelectTestPage extends BasePage {
    private static final String SAVE_TEST_TYPE_ENDPOINT = "ActionSaveCaseIncludingTestType";
    private static final String SAVE_TEST_TYPE_METHOD = "POST";
    private static final int SUCCESS_STATUS = 200;
    private static final String EARLY_ADVICE_TEST_TYPE = "Early Advice";
    private static final String EARLY_ADVICE_FIRST_PAGE_HEADING = "What advice is sought?";
    private static final String DEFAULT_FIRST_PAGE_HEADING = "Case headline";

    public SelectTestPage(PlaywrightContext context) {
        super(context);
    }

    public SelectTestPage waitForSelectTestPageToLoad() {
        waitForElement(page, AriaRole.HEADING, "Select test");
        return this;
    }


    private SelectTestPage chooseTestType(String type)
    {
         checkRadioByName(type);
         return this;
    }

    private SelectTestPage clickSaveAndContinue()
    {
        click("button[type='button']:has-text('Save and continue')");
        return this;
    }

    public void chooseReviewType(String typeOfTest)
    {
        waitForSelectTestPageToLoad()
            .chooseTestType(typeOfTest)
            .clickSaveAndContinueAndWaitForSaveTestTypeResponse();
        waitUntilLoadingIndicatorIsGone();
        assertSelectedTestTypePageReady(typeOfTest);
    }

    @Override
    public void waitUntilLoadingIndicatorIsGone() {
        waitUntilSpinnersAreGone("Saving...", "Loading...");
    }

    private SelectTestPage clickSaveAndContinueAndWaitForSaveTestTypeResponse() {
        waitForResponseTriggeredBy(
                page,
                "Save selected test type",
                SAVE_TEST_TYPE_ENDPOINT,
                SAVE_TEST_TYPE_METHOD,
                SUCCESS_STATUS,
                this::clickSaveAndContinue
        );

        return this;
    }

    private SelectTestPage assertSelectedTestTypePageReady(String typeOfTest) {
        waitForSelectedTestTypeText(typeOfTest);
        assertThat(page.locator("h1")).containsText(firstPageHeading(typeOfTest));
        return this;
    }

    private void waitForSelectedTestTypeText(String typeOfTest) {
        page.waitForCondition(() -> Boolean.TRUE.equals(page.evaluate("""
                typeOfTest => {
                    const text = (document.body.innerText || document.body.textContent || '')
                        .replace(/\\s+/g, ' ')
                        .trim();
                    return text.includes('selected ' + typeOfTest);
                }
                """, typeOfTest)));
    }

    private String firstPageHeading(String typeOfTest) {
        if (EARLY_ADVICE_TEST_TYPE.equalsIgnoreCase(typeOfTest)) {
            return EARLY_ADVICE_FIRST_PAGE_HEADING;
        }

        return DEFAULT_FIRST_PAGE_HEADING;
    }

}
