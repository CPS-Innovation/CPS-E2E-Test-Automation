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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.DataTableUtils.booleanValue;


public class UIDemoSteps {

    private static final String CREATE_MG3_DOCUMENT_COLUMN = "Create MG3 document";
    private static final String CREATE_MG3_DOCUMENT_CONTEXT_KEY = "createMg3Document";
    private static final String CPS_USER_KEY = "CPS_USER";
    private static final String PASSWORD_KEY = "PASSWORD";
    private static final String AUTO_GENERATED_VALUE = "Auto-generated";
    private static final String DEFAULT_REVIEW_TYPE = "Full Code Test";
    private static final String CASE_HEADLINE_FIELD = "Case headline";
    private static final String EVIDENTIAL_ANALYSIS_FIELD = "Evidential analysis";
    private static final String PUBLIC_INTEREST_ASSESSMENT_FIELD = "Public interest assessment";
    private static final String DISCLOSURE_MANAGEMENT_FIELD = "Disclosure management";
    private static final String ALLOCATION_FIELD = "Allocation";
    private static final String ALLOCATION_NGAP_FIELD = "Allocation NGAP";
    private static final String VICTIM_AND_WITNESS_NEEDS_FIELD = "Victim and witness needs";
    private static final String TRIAL_AND_SENTENCING_PREPARATION_FIELD = "Trial and sentencing preparation";
    private static final String HUMAN_RIGHTS_FIELD = "Human Rights";
    private static final String ADVOCATE_AND_OPERATIONAL_DELIVERY_INSTRUCTIONS_FIELD =
            "Advocate and operational delivery instructions";
    private static final String SUSPECT_VICTIM_RELATIONSHIP_FIELD = "Suspect-victim relationship";
    private static final String GLOBAL_MONITORING_CODES_FIELD = "Global monitoring codes";
    private static final String LOCAL_MONITORING_CODES_FIELD = "Local monitoring codes";
    private static final String YES_VALUE = "Yes";
    private static final String NOT_AT_THIS_TIME_VALUE = "Not at this time";

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

    @And("I complete the pre-charge analysis details:")
    public void iCompletePreChargeAnalysisDetails(DataTable dataTable) {
        completePreChargeAnalysisDetails(dataTable);
    }

    @And("I complete the pre-charge analysis details with:")
    public void iCompletePreChargeAnalysisDetailsWithOverrides(DataTable dataTable) {
        completePreChargeAnalysisDetails(dataTable);
    }

    private void completePreChargeAnalysisDetails(DataTable dataTable) {
        Map<String, String> analysisDetails = defaultPreChargeAnalysisDetails();
        analysisDetails.putAll(analysisDetails(dataTable));

        String caseHeadline = resolvedAnalysisText(analysisDetails, CASE_HEADLINE_FIELD);
        pages.decisionAnalysisPage.enterCaseHeadLine(selectedReviewType(), caseHeadline);
        context.set("caseHeadlineText", caseHeadline);

        String evidentialAnalysis = resolvedAnalysisText(analysisDetails, EVIDENTIAL_ANALYSIS_FIELD);
        pages.decisionAnalysisPage.enterEvidentialAnalysisData(evidentialAnalysis);
        context.set("evidentialAnalysisText", evidentialAnalysis);

        enterAnalysisSection(analysisDetails, PUBLIC_INTEREST_ASSESSMENT_FIELD);
        enterAnalysisSection(analysisDetails, DISCLOSURE_MANAGEMENT_FIELD);

        String allocationText = resolvedAnalysisText(analysisDetails, ALLOCATION_FIELD);
        String allocationNgap = requiredAnalysisValue(analysisDetails, ALLOCATION_NGAP_FIELD);
        if (!YES_VALUE.equalsIgnoreCase(allocationNgap)) {
            throw new IllegalArgumentException("Unsupported " + ALLOCATION_NGAP_FIELD + " value: " + allocationNgap
                    + ". Only Yes is currently supported.");
        }
        pages.decisionAnalysisPage.enterTextInAllocationSectionAndChooseNGAPAsYes(ALLOCATION_FIELD, allocationText);
        context.set(ALLOCATION_FIELD, allocationText);

        enterAnalysisSection(analysisDetails, VICTIM_AND_WITNESS_NEEDS_FIELD);
        enterAnalysisSection(analysisDetails, TRIAL_AND_SENTENCING_PREPARATION_FIELD);

        String humanRights = requiredAnalysisValue(analysisDetails, HUMAN_RIGHTS_FIELD);
        if (!NOT_AT_THIS_TIME_VALUE.equalsIgnoreCase(humanRights)) {
            throw new IllegalArgumentException("Unsupported " + HUMAN_RIGHTS_FIELD + " value: " + humanRights
                    + ". Only Not at this time is currently supported.");
        }
        pages.decisionAnalysisPage.humanRightsOptionHasNotAtThisTime();
        context.set(HUMAN_RIGHTS_FIELD, humanRights);

        enterAnalysisSection(analysisDetails, ADVOCATE_AND_OPERATIONAL_DELIVERY_INSTRUCTIONS_FIELD);

        String relationship = optionalAnalysisValue(analysisDetails, SUSPECT_VICTIM_RELATIONSHIP_FIELD);
        if (!isBlank(relationship)) {
            pages.decisionAnalysisPage.addSuspectVictimRelationship(relationship);
            context.set(SUSPECT_VICTIM_RELATIONSHIP_FIELD, relationship);
        }

        List<String> globalMonitoringCodes = monitoringCodes(analysisDetails, GLOBAL_MONITORING_CODES_FIELD);
        List<String> localMonitoringCodes = monitoringCodes(analysisDetails, LOCAL_MONITORING_CODES_FIELD);
        pages.decisionAnalysisPage.selectMonitoringCodesAndSaveContinue(globalMonitoringCodes, localMonitoringCodes);
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

    private void enterAnalysisSection(Map<String, String> analysisDetails, String sectionName) {
        String sectionText = resolvedAnalysisText(analysisDetails, sectionName);
        pages.decisionAnalysisPage.enterSectionData(sectionName, sectionText);
        context.set(sectionName, sectionText);
    }

    private String selectedReviewType() {
        String reviewType = context.getAsString("reviewType");
        return isBlank(reviewType) ? DEFAULT_REVIEW_TYPE : reviewType;
    }

    private Map<String, String> defaultPreChargeAnalysisDetails() {
        Map<String, String> defaults = new LinkedHashMap<>();
        defaults.put(normalizedField(CASE_HEADLINE_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(EVIDENTIAL_ANALYSIS_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(PUBLIC_INTEREST_ASSESSMENT_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(DISCLOSURE_MANAGEMENT_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(ALLOCATION_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(ALLOCATION_NGAP_FIELD), YES_VALUE);
        defaults.put(normalizedField(VICTIM_AND_WITNESS_NEEDS_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(TRIAL_AND_SENTENCING_PREPARATION_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(HUMAN_RIGHTS_FIELD), NOT_AT_THIS_TIME_VALUE);
        defaults.put(normalizedField(ADVOCATE_AND_OPERATIONAL_DELIVERY_INSTRUCTIONS_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(SUSPECT_VICTIM_RELATIONSHIP_FIELD), "");
        defaults.put(normalizedField(GLOBAL_MONITORING_CODES_FIELD), "");
        defaults.put(normalizedField(LOCAL_MONITORING_CODES_FIELD), "");
        return defaults;
    }

    private Map<String, String> analysisDetails(DataTable dataTable) {
        Map<String, String> analysisDetails = new LinkedHashMap<>();

        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            String field = tableColumnValue(row, "field");
            String value = tableColumnValue(row, "value");

            if (isBlank(field)) {
                throw new IllegalArgumentException("Pre-charge analysis detail row is missing a field name.");
            }

            analysisDetails.put(normalizedField(field), value == null ? "" : value.trim());
        }

        return analysisDetails;
    }

    private String tableColumnValue(Map<String, String> row, String columnName) {
        return row.entrySet().stream()
                .filter(entry -> entry.getKey() != null && entry.getKey().trim().equalsIgnoreCase(columnName))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Pre-charge analysis data table must contain a '" + columnName + "' column."
                ));
    }

    private String resolvedAnalysisText(Map<String, String> analysisDetails, String fieldName) {
        String value = requiredAnalysisValue(analysisDetails, fieldName);

        if (AUTO_GENERATED_VALUE.equalsIgnoreCase(value)) {
            return FakerUtils.populateSentences();
        }

        if (isBlank(value)) {
            throw new IllegalArgumentException(fieldName + " must contain text or " + AUTO_GENERATED_VALUE + ".");
        }

        return value;
    }

    private String requiredAnalysisValue(Map<String, String> analysisDetails, String fieldName) {
        String value = optionalAnalysisValue(analysisDetails, fieldName);
        if (value == null) {
            throw new IllegalArgumentException("Pre-charge analysis data table is missing field: " + fieldName);
        }

        return value;
    }

    private String optionalAnalysisValue(Map<String, String> analysisDetails, String fieldName) {
        return analysisDetails.get(normalizedField(fieldName));
    }

    private List<String> monitoringCodes(Map<String, String> analysisDetails, String fieldName) {
        String value = optionalAnalysisValue(analysisDetails, fieldName);
        List<String> codes = new ArrayList<>();

        if (isBlank(value)) {
            return codes;
        }

        if (AUTO_GENERATED_VALUE.equalsIgnoreCase(value)) {
            throw new IllegalArgumentException(fieldName + " must list exact monitoring codes separated by ';'.");
        }

        for (String code : value.split(";")) {
            String normalizedCode = code.trim();
            if (!normalizedCode.isBlank()) {
                codes.add(normalizedCode);
            }
        }

        return codes;
    }

    private String normalizedField(String fieldName) {
        return fieldName.replaceAll("\\s+", " ").trim().toLowerCase(Locale.ROOT);
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
