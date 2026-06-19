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

import static com.cps.fct.e2e.utils.common.DataTableUtils.booleanValue;


public class UIDemoSteps {

    private static final String CREATE_MG3_DOCUMENT_COLUMN = "Create MG3 document";
    private static final String CREATE_MG3_DOCUMENT_CONTEXT_KEY = "createMg3Document";
    private static final String CPS_USER_KEY = "CPS_USER";
    private static final String PASSWORD_KEY = "PASSWORD";

    @Inject private PageObjects pages;

    @Inject ScenarioContext context;

    public UIDemoSteps() {
    }

    @Given("I login to case review app")
    public void loginToCaseReviewApp() throws InterruptedException {
        pages.loginPage.loginIntoCaseReview(caseReviewUsername(), caseReviewPassword());
    }

    @Given("login to case review app")
    public void loginToCaseReviewAppWithPlainWording() throws InterruptedException {
        loginToCaseReviewApp();
    }

    @Given("login to case review app using {string} and {string}")
    public void loginToCaseReviewUsingLogin(String userName, String password) throws InterruptedException {
        pages.loginPage.loginIntoCaseReview(
                isBlank(userName) ? caseReviewUsername() : userName,
                caseReviewPassword()
        );
    }

    @And("Search the case")
    public void searchTheCaseAndStartReview() {
        pages.caseIdSearchPage.searchCase(context.get("caseId"));
    }

    @And("Search the case {string}")
    public void searchTheCaseUsingContextValue(String searchType) {
        String normalizedSearchType = searchType.trim().toLowerCase();

        switch (normalizedSearchType) {
            case "urn":
            case "caseurn":
            case "case urn":
                String urn = requiredContextValue("caseUrn");
                String caseId = pages.caseIdSearchPage.searchCaseUrn(urn);
                context.set("caseId", caseId);
                break;
            case "caseid":
            case "case id":
                pages.caseIdSearchPage.searchCase(requiredContextValue("caseId"));
                break;
            default:
                throw new IllegalArgumentException("Unsupported case search type: " + searchType
                        + ". Use URN or caseId.");
        }
    }

    @And("Search the case urn {string}")
    public void searchTheCaseAndStartReviewUsingUrn (String urn) {
        String caseId = pages.caseIdSearchPage.searchCaseUrn(urn);
        context.set("caseUrn", urn);
        context.set("caseId", caseId);
    }

    @When("I start {string}")
    public void iStartDaysPCDReview(String typeOfReview) {
        pages.caseReviewPage.startReview(context.get("caseId"),typeOfReview);
        context.set("typeOfReview", typeOfReview);
    }

    @When("I resume already existing  {string}")
    public void iResumeAlreadyExistingReview(String typeOfReview) {
        pages.caseReviewPage.resumeReview(context.get("caseId"), typeOfReview);
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
        pages.decisionAnalysisPage.enterCaseHeadLine("Full Code Test", randomWords);
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


    @And("I choose {string} monitoring codes as")
    public void iChooseMonitoringCodesAs(String monitoringCodeType, List<String> monitoringCodes) {
        pages.decisionAnalysisPage.selectMonitoringCodesAndSaveContinue(monitoringCodeType, monitoringCodes);

    }

    @And("I add suspect victim relationship as {string}")
    public void iAddSuspectVictimRelationshipAs(String relationshipType) {
        pages.decisionAnalysisPage.addSuspectVictimRelationship(relationshipType);
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

        boolean createMg3Document = booleanValue(submitReviewData, CREATE_MG3_DOCUMENT_COLUMN);
        context.set(CREATE_MG3_DOCUMENT_CONTEXT_KEY, createMg3Document);

        pages.completeSubmissionPage.completeReviewSubmission(
                submitReviewData,
                createMg3Document
        );

    }

    @Then("review is submitted successfully")
    public void reviewIsSubmittedSuccessfully() {
        pages.completeSubmissionPage.verifyReviewSubmission(
                context.get("typeOfReview"),
                Boolean.TRUE.equals(context.get(CREATE_MG3_DOCUMENT_CONTEXT_KEY))
        );
    }

    private String requiredContextValue(String key) {
        String value = context.getAsString(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("No value found in scenario context for key: " + key);
        }
        return value;
    }

    private String caseReviewUsername() {
        String envSuffix = requiredContextValue("envSuffix");
        return requiredEnvValue(CPS_USER_KEY) + envSuffix;
    }

    private String caseReviewPassword() {
        return SecurePassCode.decode(requiredEnvValue(PASSWORD_KEY));
    }

    private String requiredEnvValue(String key) {
        String value = EnvConfig.getEnv(key);
        if (isBlank(value)) {
            throw new IllegalStateException("No value found in environment for key: " + key);
        }
        return value;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

}
