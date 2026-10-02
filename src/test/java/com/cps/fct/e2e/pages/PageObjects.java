package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.pages.caseReviewApp.*;
import com.cps.fct.e2e.pages.victimCaseApp.VcaHomePage;
import com.cps.fct.e2e.pages.victimCaseApp.VcaLoginPage;
import com.cps.fct.e2e.pages.AppsLoginPage;
import com.cps.fct.e2e.pages.victimCaseApp.VcaVictimDetailsPage;
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

    @Inject
    public AppsLoginPage appsLoginPage;

    @Inject
    public VcaLoginPage vcaLoginPage;

    @Inject
    public VcaHomePage vcaHomePage;

    @Inject
    public VcaVictimDetailsPage vcaVictimDetailsPage;


}
