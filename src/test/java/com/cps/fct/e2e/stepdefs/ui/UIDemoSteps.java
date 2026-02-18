package com.cps.fct.e2e.stepdefs.ui;

import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.FakerUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.common.SecurePassCode;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.picocontainer.annotations.Inject;

import java.util.List;
import java.util.Map;


public class UIDemoSteps {

    @Inject private PageObjects pages;

    @Inject ScenarioContext context;

    public UIDemoSteps() {
    }

    @Given("login to case review app")
    public void loginToCaseReviewApp() throws InterruptedException {
        String username = EnvConfig.get("CPS_USER") + context.getAsString("envSuffix");
        String password = SecurePassCode.decode(EnvConfig.get("PASSWORD"));
        pages.loginPage.loginIntoCaseReview(username,password);
    }

    @And("Search the case")
    public void searchTheCaseAndStartReview() {
        pages.caseIdSearchPage.searchCase(context.get("caseId"));
    }

    @When("I start {string}")
    public void iStartDaysPCDReview(String typeOfReview) {
        pages.caseReviewPage.startReview(context.get("caseId"),typeOfReview);
        context.set("typeOfReview", typeOfReview);
    }

    @And("select test as {string}")
    public void selectTestAs(String testType) {
        pages.selectTestPage.chooseReviewType(testType);
        context.set("reviewType", testType);
    }

    @And("the case headline is entered")
    public void enterTheCaseHeadlineText() {
        String randomWords = FakerUtils.populateSentences();
        pages.decisionAnalysisPage.enterCaseHeadLine(context.get("reviewType"), randomWords);
        context.set("caseHeadlineText", randomWords);

    }

    @And("the evidential analysis is entered")
    public void enterTheEvidentialAnalysisText() {
        String randomWords = FakerUtils.populateSentences();
        pages.decisionAnalysisPage.enterEvidentialAnalysisData(randomWords);
        context.set("evidentialAnalysisText", randomWords);
    }

    @And("I write the {string}")
    public void iWriteTheTextInEditor(String sectionName) {
        String randomWords = FakerUtils.populateSentences();
        pages.decisionAnalysisPage.enterSectionData(sectionName,randomWords);
        context.set(sectionName, randomWords);
    }


    @And("I write the {string} and choose NGAP option has Yes")
    public void iWriteTheAndChooseNGAPOptionHas(String sectionName) {
        String randomWords = FakerUtils.populateSentences();
        pages.decisionAnalysisPage.enterTextInAllocationSectionAndChooseNGAPAsYes(sectionName,randomWords);
        context.set(sectionName, randomWords);
    }

    @And("I choose {string} in Human Rights")
    public void iChooseNotAtThisTimeInHumanRights(String unused ) {
        pages.decisionAnalysisPage.humanRightsOptionHasNotAtThisTime();
    }


    @And("I choose Global monitoring codes as")
    public void iChooseGlobalMonitoringCodesAs(List<String> monitoringCodes) {
        pages.decisionAnalysisPage.selectGlobalMonitoringCodesAndSaveContinue(monitoringCodes);

    }

    @And("I preview pre charge analysis")
    public void iPreviewPreChargeAnalysis() {
        pages.decisionAnalysisPage.checkPreviewChargeAnalysis();
    }

    @And("I choose DG compliant as Yes")
    public void iChooseDGComplaintAsYes() {
        pages.decisionAnalysisPage.checkDGComplaintAsYes();
    }


    @When("I make charging decision as following:")
    public void iMakeChargingDecisionAsFollowing(DataTable dataTable) {
        Map<String, String> decisionChargingData =
                dataTable.asMaps(String.class, String.class).getFirst();
        pages.decisionAnalysisPage.applyDecisionChargeForNFA(decisionChargingData);
    }

    @And("I continue without action plan")
    public void iContinueWithoutActionPlan() {
        pages.actionPlanPage.continueWithOutActionPlan();
    }

    @And("I submit the charging decision as following")
    public void iSubmitTheChargingDecisionAsFollowing(DataTable dataTable) {

        Map<String, String> submitReviewData =
                dataTable.asMaps(String.class, String.class).getFirst();

        pages.completeSubmissionPage.completeReviewSubmission(submitReviewData);

    }

    @Then("review is submitted successfully")
    public void reviewIsSubmittedSuccessfully() {
        pages.completeSubmissionPage.verifyReviewSubmission(context.get("typeOfReview"));
    }


}
