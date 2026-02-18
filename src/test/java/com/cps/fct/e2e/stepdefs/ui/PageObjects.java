package com.cps.fct.e2e.stepdefs.ui;

import com.cps.fct.e2e.pages.*;
import org.picocontainer.annotations.Inject;

public class PageObjects {

    @Inject
    LoginPage loginPage;

    @Inject
    CaseIdSearchPage caseIdSearchPage;

    @Inject
    CaseReviewPage caseReviewPage;

    @Inject
    SelectTestPage selectTestPage;

    @Inject
    ChargeDecisionAnalysisPage decisionAnalysisPage;

    @Inject
    ActionPlanPage actionPlanPage;

    @Inject
    CompleteSubmissionPage completeSubmissionPage;
}
