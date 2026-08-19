package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.pages.caseReviewApp.*;
import org.picocontainer.annotations.Inject;

public class PageObjects {

    @Inject
    public LoginPage loginPage;

    @Inject
    public CaseIdSearchPage caseIdSearchPage;

    @Inject
    public CaseReviewPage caseReviewPage;

    @Inject
    public SelectTestPage selectTestPage;

    @Inject
    public ChargeDecisionAnalysisPage decisionAnalysisPage;

    @Inject
    public ActionPlanPage actionPlanPage;

    @Inject
    public CompleteSubmissionPage completeSubmissionPage;



}
