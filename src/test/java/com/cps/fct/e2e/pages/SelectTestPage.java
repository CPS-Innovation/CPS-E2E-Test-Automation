package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.options.AriaRole;

import static com.cps.fct.e2e.utils.playwright.PlaywrightNetworkUtils.waitForResponseTriggeredBy;

public class SelectTestPage extends BasePage {
    private static final String SAVE_TEST_TYPE_ENDPOINT = "ActionSaveCaseIncludingTestType";
    private static final String SAVE_TEST_TYPE_METHOD = "POST";
    private static final int SUCCESS_STATUS = 200;

    public SelectTestPage(PlaywrightContext context) {
        super(context);
    }

    @Override
    public SelectTestPage waitForLoginPageToLoadCompletely() {
        waitForElement(page, AriaRole.HEADING, "Reviews");
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
        waitForLoginPageToLoadCompletely()
            .chooseTestType(typeOfTest)
            .clickSaveAndContinueAndWaitForSaveTestTypeResponse()
            .waitUntilLoadingIndicatorIsGone();
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

}
