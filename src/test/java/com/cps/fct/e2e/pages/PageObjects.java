package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.pages.caseReviewApp.*;
import com.cps.fct.e2e.pages.victimCaseApp.*;
import com.cps.fct.e2e.pages.AppsLoginPage;
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

    @Inject
    public VcaCaseContactPage vcaCaseContactPage;

    @Inject
    public VcaCommunicationsPage vcaCommunicationsPage;

    public void useCurrentPage() {
        loginPage.useCurrentPage();
        caseIdSearchPage.useCurrentPage();
        caseReviewPage.useCurrentPage();
        selectTestPage.useCurrentPage();
        decisionAnalysisPage.useCurrentPage();
        actionPlanPage.useCurrentPage();
        completeSubmissionPage.useCurrentPage();
//        appsLoginPage.useCurrentPage();
        vcaLoginPage.useCurrentPage();
        vcaHomePage.useCurrentPage();
        vcaVictimDetailsPage.useCurrentPage();
        vcaCaseContactPage.useCurrentPage();
        vcaCommunicationsPage.useCurrentPage();


    }

}
