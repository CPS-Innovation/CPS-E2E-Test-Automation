package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.playwright.PlaywrightContext;

public class ActionPlanPage extends BasePage {

    // Constants
    private static final String CASE_ACTION_PLAN_HEADER = "Case action plan";
    private static final String CONTINUE_WITHOUT_ACTION_PLAN_BUTTON = "Continue without action plan";
    private static final String LOADING_INDICATOR_TEXT = "Loading...";

    public ActionPlanPage(PlaywrightContext context) {
        super(context);
    }

    public void continueWithOutActionPlan() {
        waitForTextInLocator("h1", CASE_ACTION_PLAN_HEADER);
        clickButton(CONTINUE_WITHOUT_ACTION_PLAN_BUTTON);
        waitUntilLoadingIndicatorIsGone(LOADING_INDICATOR_TEXT);
    }
}