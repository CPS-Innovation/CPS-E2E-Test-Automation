package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.options.AriaRole;

public class SelectTestPage extends BasePage {
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
      click("button[title='Save and Continue']");
      return this;
    }

    public void chooseReviewType(String typeOfTest)
    {
        waitForLoginPageToLoadCompletely()
            .chooseTestType(typeOfTest)
            .clickSaveAndContinue()
            .waitUntilLoadingIndicatorIsGone();
    }

    @Override
    public void waitUntilLoadingIndicatorIsGone() {
        waitUntilSpinnersAreGone("Saving...", "Loading...");
    }

}
