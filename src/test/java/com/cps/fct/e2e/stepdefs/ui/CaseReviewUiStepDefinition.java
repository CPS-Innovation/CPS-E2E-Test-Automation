package com.cps.fct.e2e.stepdefs.ui;

import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.FakerUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.common.SecurePassCode;
import com.jayway.jsonpath.JsonPath;
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

public class CaseReviewUiStepDefinition {

    private static final String CREATE_MG3_DOCUMENT_COLUMN = "Create MG3 document";
    private static final String CREATE_MG3_DOCUMENT_CONTEXT_KEY = "createMg3Document";
    private static final String DEFENDANT_COUNT_CONTEXT_KEY = "defendantCount";
    private static final String CASE_DATA_FILE_NAME_CONTEXT_KEY = "caseDataFileName";
    private static final String MODIFIED_CM01_REQUEST_PAYLOAD_CONTEXT_KEY = "modifiedCM01RequestPayload";
    private static final String CHARGING_DECISION_TYPES_CONTEXT_KEY = "chargingDecisionTypes";
    private static final String DECISION_TYPE_FIELD = "decision type";
    private static final String CPS_USER_KEY = "CPS_USER";
    private static final String PASSWORD_KEY = "PASSWORD";
    private static final String AUTO_GENERATED_VALUE = "Auto-generated";
    private static final String DEFAULT_REVIEW_TYPE = "Full Code Test";
    private static final String THRESHOLD_TEST_REVIEW_TYPE = "Threshold Test";
    private static final String EARLY_ADVICE_REVIEW_TYPE = "Early Advice";
    private static final String CASE_HEADLINE_FIELD = "Case headline";
    private static final String EVIDENTIAL_ANALYSIS_FIELD = "Evidential analysis";
    private static final String WHAT_ADVICE_IS_SOUGHT_FIELD = "What advice is sought";
    private static final String WHAT_ADVICE_IS_SOUGHT_SECTION = "What advice is sought?";
    private static final String MATERIALS_AND_INFORMATION_CONSIDERED_FIELD =
            "Materials and information considered";
    private static final String YOUR_ADVICE_FIELD = "Your advice";
    private static final String PREVIEW_SECTION = "Preview";
    private static final String REASONABLE_GROUNDS_TO_SUSPECT_FIELD = "Reasonable grounds to suspect";
    private static final String REASONABLE_GROUNDS_TO_SUSPECT_MET_FIELD = "Reasonable grounds to suspect met";
    private static final String FURTHER_EVIDENCE_OBTAINABLE_FIELD = "Further evidence obtainable";
    private static final String FURTHER_EVIDENCE_OBTAINABLE_MET_FIELD = "Further evidence obtainable met";
    private static final String SERIOUSNESS_JUSTIFIES_IMMEDIATE_CHARGE_FIELD =
            "Seriousness justifies immediate charge";
    private static final String SERIOUSNESS_JUSTIFIES_IMMEDIATE_CHARGE_MET_FIELD =
            "Seriousness justifies immediate charge met";
    private static final String GROUNDS_TO_OBJECT_BAIL_FIELD = "Grounds to object bail";
    private static final String GROUNDS_TO_OBJECT_BAIL_MET_FIELD = "Grounds to object bail met";
    private static final String PUBLIC_INTEREST_ASSESSMENT_FIELD = "Public interest assessment";
    private static final String PUBLIC_INTEREST_ASSESSMENT_MET_FIELD = "Public interest assessment met";
    private static final String ADDITIONAL_ANALYSIS_FIELD = "Additional analysis";
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
    private static final String PRINCIPAL_OFFENCE_CATEGORY_FIELD = "Principal offence category";
    private static final String DATE_REQUIRED_BY_FIELD = "Date required by";
    private static final String CHASER_TASK_FIELD = "Chaser task";
    private static final String CHASER_TASK_DATE_FIELD = "Chaser task date";
    private static final String RELATED_TO_SUSPECT_FIELD = "Related to suspect";
    private static final String ACTION_FIELD = "Action";
    private static final String DEFAULT_RELATED_SUSPECT = "All";
    private static final String CHASER_ONE_DAY_BEFORE_VALUE = "1 day before date required";
    private static final String NO_VALUE = "No";
    private static final String YES_VALUE = "Yes";
    private static final String NOT_AT_THIS_TIME_VALUE = "Not at this time";

    @Inject private PageObjects pages;

    @Inject ScenarioContext context;

    public CaseReviewUiStepDefinition() {
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
        pages.decisionAnalysisPage.enterCaseHeadLine(selectedReviewType(), randomWords);
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
        pages.decisionAnalysisPage.addSuspectVictimRelationship(relationshipType, expectedDefendantCount());
    }

    @And("I select PCD principal offence category as {string}")
    public void iSelectPcdPrincipalOffenceCategoryAs(String offenceCategory) {
        pages.decisionAnalysisPage.selectEarlyAdvicePrincipalOffenceCategoryAndContinue(
                offenceCategory,
                expectedDefendantCount()
        );
        context.set(PRINCIPAL_OFFENCE_CATEGORY_FIELD, offenceCategory);
    }

    @And("I complete the pre-charge analysis details:")
    public void iCompletePreChargeAnalysisDetails(DataTable dataTable) {
        completePreChargeAnalysisDetails(dataTable);
    }

    @And("I complete the pre-charge analysis details with:")
    public void iCompletePreChargeAnalysisDetailsWithOverrides(DataTable dataTable) {
        completePreChargeAnalysisDetails(dataTable);
    }

    @And("I complete the Threshold Test pre-charge analysis details with:")
    public void iCompleteThresholdTestPreChargeAnalysisDetailsWithOverrides(DataTable dataTable) {
        completeThresholdTestPreChargeAnalysisDetails(dataTable);
    }

    @And("^I complete the Early Advice pre[- ]charge analysis details with\\s?:$")
    public void iCompleteEarlyAdvicePreChargeAnalysisDetailsWithOverrides(DataTable dataTable) {
        completeEarlyAdvicePreChargeAnalysisDetails(dataTable);
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
        pages.decisionAnalysisPage.enterTextInAllocationSectionAndChooseNGAPAsYesIfPresent(ALLOCATION_FIELD, allocationText);
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
            pages.decisionAnalysisPage.addSuspectVictimRelationship(relationship, expectedDefendantCount());
            context.set(SUSPECT_VICTIM_RELATIONSHIP_FIELD, relationship);
        }

        List<String> globalMonitoringCodes = monitoringCodes(analysisDetails, GLOBAL_MONITORING_CODES_FIELD);
        List<String> localMonitoringCodes = monitoringCodes(analysisDetails, LOCAL_MONITORING_CODES_FIELD);
        pages.decisionAnalysisPage.selectMonitoringCodesAndSaveContinue(globalMonitoringCodes, localMonitoringCodes);
    }

    private void completeThresholdTestPreChargeAnalysisDetails(DataTable dataTable) {
        if (!THRESHOLD_TEST_REVIEW_TYPE.equalsIgnoreCase(selectedReviewType())) {
            throw new IllegalStateException("Threshold pre-charge analysis step requires selected review type "
                    + THRESHOLD_TEST_REVIEW_TYPE + ". Actual: " + selectedReviewType());
        }

        Map<String, String> analysisDetails = defaultThresholdPreChargeAnalysisDetails();
        analysisDetails.putAll(analysisDetails(dataTable));

        String caseHeadline = resolvedAnalysisText(analysisDetails, CASE_HEADLINE_FIELD);
        pages.decisionAnalysisPage.enterThresholdCaseHeadLine(THRESHOLD_TEST_REVIEW_TYPE, caseHeadline);
        context.set("caseHeadlineText", caseHeadline);

        enterThresholdConditionSection(
                analysisDetails,
                REASONABLE_GROUNDS_TO_SUSPECT_FIELD,
                REASONABLE_GROUNDS_TO_SUSPECT_MET_FIELD
        );
        enterThresholdConditionSection(
                analysisDetails,
                FURTHER_EVIDENCE_OBTAINABLE_FIELD,
                FURTHER_EVIDENCE_OBTAINABLE_MET_FIELD
        );
        enterThresholdConditionSection(
                analysisDetails,
                SERIOUSNESS_JUSTIFIES_IMMEDIATE_CHARGE_FIELD,
                SERIOUSNESS_JUSTIFIES_IMMEDIATE_CHARGE_MET_FIELD
        );
        enterThresholdConditionSection(
                analysisDetails,
                GROUNDS_TO_OBJECT_BAIL_FIELD,
                GROUNDS_TO_OBJECT_BAIL_MET_FIELD
        );

        enterThresholdConditionSection(
                analysisDetails,
                PUBLIC_INTEREST_ASSESSMENT_FIELD,
                PUBLIC_INTEREST_ASSESSMENT_MET_FIELD
        );
        skipThresholdAdditionalAnalysis(analysisDetails);

        String relationship = optionalAnalysisValue(analysisDetails, SUSPECT_VICTIM_RELATIONSHIP_FIELD);
        if (!isBlank(relationship)) {
            pages.decisionAnalysisPage.addSuspectVictimRelationship(relationship, expectedDefendantCount());
            context.set(SUSPECT_VICTIM_RELATIONSHIP_FIELD, relationship);
        }

        List<String> globalMonitoringCodes = monitoringCodes(analysisDetails, GLOBAL_MONITORING_CODES_FIELD);
        List<String> localMonitoringCodes = monitoringCodes(analysisDetails, LOCAL_MONITORING_CODES_FIELD);
        pages.decisionAnalysisPage.selectMonitoringCodesAndSaveContinue(globalMonitoringCodes, localMonitoringCodes);
    }

    private void completeEarlyAdvicePreChargeAnalysisDetails(DataTable dataTable) {
        if (!EARLY_ADVICE_REVIEW_TYPE.equalsIgnoreCase(selectedReviewType())) {
            throw new IllegalStateException("Early Advice pre-charge analysis step requires selected review type "
                    + EARLY_ADVICE_REVIEW_TYPE + ". Actual: " + selectedReviewType());
        }

        Map<String, String> analysisDetails = defaultEarlyAdvicePreChargeAnalysisDetails();
        analysisDetails.putAll(analysisDetails(dataTable));

        enterEarlyAdviceAnalysisSection(
                analysisDetails,
                WHAT_ADVICE_IS_SOUGHT_FIELD,
                WHAT_ADVICE_IS_SOUGHT_SECTION,
                MATERIALS_AND_INFORMATION_CONSIDERED_FIELD
        );
        enterEarlyAdviceAnalysisSection(
                analysisDetails,
                MATERIALS_AND_INFORMATION_CONSIDERED_FIELD,
                MATERIALS_AND_INFORMATION_CONSIDERED_FIELD,
                YOUR_ADVICE_FIELD
        );
        enterEarlyAdviceAnalysisSection(
                analysisDetails,
                YOUR_ADVICE_FIELD,
                YOUR_ADVICE_FIELD,
                PREVIEW_SECTION
        );
        completeEarlyAdvicePostPreviewDetails(analysisDetails);
    }

    @And("I preview pre charge analysis")
    public void iPreviewPreChargeAnalysis() {
        if (EARLY_ADVICE_REVIEW_TYPE.equalsIgnoreCase(selectedReviewType())) {
            pages.decisionAnalysisPage.checkPreviewEarlyAdviceAnalysis();
            return;
        }

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
        context.set(CHARGING_DECISION_TYPES_CONTEXT_KEY, List.of(
                tableColumnValue(decisionChargingData, DECISION_TYPE_FIELD)
        ));
        pages.decisionAnalysisPage.applyChargingDecision(decisionChargingData, selectedCaseRequiresConsent());
    }

    @When("^I make charging decision for the multi defendants\\s+as following:$")
    public void iMakeChargingDecisionForTheMultiDefendantsAsFollowing(DataTable dataTable) {
        List<Map<String, String>> decisionChargingData = dataTable.asMaps(String.class, String.class);
        int expectedDefendantCount = expectedDefendantCount();
        context.set(CHARGING_DECISION_TYPES_CONTEXT_KEY, decisionChargingData.stream()
                .map(row -> tableColumnValue(row, DECISION_TYPE_FIELD))
                .toList());

        if (decisionChargingData.size() != expectedDefendantCount) {
            throw new IllegalArgumentException("Multi-defendant charging decision table must contain "
                    + expectedDefendantCount + " row(s), but contained " + decisionChargingData.size() + ".");
        }

        pages.decisionAnalysisPage.applyChargingDecisions(
                decisionChargingData,
                multiDefendantNamesInDecisionOrder(expectedDefendantCount),
                selectedCaseRequiresConsent()
        );
    }

    @And("I continue without action plan")
    public void iContinueWithoutActionPlan() {
        pages.actionPlanPage.continueWithOutActionPlan();
    }

    @And("I add an action point plan for {string} and {string}")
    public void iAddAnActionPointPlanForAnd(String daysToAdd, String actionPointOption) {
        pages.actionPlanPage.addActionPointPlan(daysToAdd, actionPointOption);
    }

    @And("I add an action point plan with:")
    public void iAddAnActionPointPlanWith(DataTable dataTable) {
        Map<String, String> actionPlan = analysisDetails(dataTable);

        String dateRequiredBy = requiredAnalysisValue(actionPlan, DATE_REQUIRED_BY_FIELD);
        String action = requiredAnalysisValue(actionPlan, ACTION_FIELD);

        String relatedSuspect = optionalAnalysisValue(actionPlan, RELATED_TO_SUSPECT_FIELD);
        if (isBlank(relatedSuspect)) {
            relatedSuspect = DEFAULT_RELATED_SUSPECT;
        }

        boolean addChaser = resolveChaserTaskOneDayBefore(actionPlan);

        pages.actionPlanPage.addActionPointPlan(dateRequiredBy, action, relatedSuspect, addChaser);
    }

    private boolean resolveChaserTaskOneDayBefore(Map<String, String> actionPlan) {
        String chaserTask = optionalAnalysisValue(actionPlan, CHASER_TASK_FIELD);
        String chaserTaskDate = optionalAnalysisValue(actionPlan, CHASER_TASK_DATE_FIELD);

        if (isBlank(chaserTask) || NO_VALUE.equalsIgnoreCase(chaserTask)) {
            if (!isBlank(chaserTaskDate)) {
                throw new IllegalArgumentException(CHASER_TASK_DATE_FIELD + " must be blank when "
                        + CHASER_TASK_FIELD + " is " + NO_VALUE + ".");
            }
            return false;
        }

        if (!YES_VALUE.equalsIgnoreCase(chaserTask)) {
            throw new IllegalArgumentException("Unsupported " + CHASER_TASK_FIELD + " value: " + chaserTask
                    + ". Use " + YES_VALUE + " or " + NO_VALUE + ".");
        }

        if (!CHASER_ONE_DAY_BEFORE_VALUE.equalsIgnoreCase(chaserTaskDate)) {
            throw new IllegalArgumentException(CHASER_TASK_DATE_FIELD + " only supports '"
                    + CHASER_ONE_DAY_BEFORE_VALUE + "' when " + CHASER_TASK_FIELD + " is " + YES_VALUE + ".");
        }

        return true;
    }

    @And("I submit the charging decision as following")
    public void iSubmitTheChargingDecisionAsFollowing(DataTable dataTable) {

        Map<String, String> submitReviewData =
                dataTable.asMaps(String.class, String.class).getFirst();

        Boolean createMg3Document = optionalBooleanValue(submitReviewData, CREATE_MG3_DOCUMENT_COLUMN);
        boolean effectiveCreateMg3Document = pages.completeSubmissionPage.completeReviewSubmission(
                submitReviewData,
                createMg3Document,
                selectedReviewType(),
                selectedChargingDecisionTypes()
        );
        context.set(CREATE_MG3_DOCUMENT_CONTEXT_KEY, effectiveCreateMg3Document);

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

    private void enterThresholdConditionSection(
            Map<String, String> analysisDetails,
            String sectionName,
            String answerFieldName
    ) {
        String sectionText = resolvedAnalysisText(analysisDetails, sectionName);
        String answer = requiredAnalysisValue(analysisDetails, answerFieldName);

        if (!YES_VALUE.equalsIgnoreCase(answer)) {
            throw new IllegalArgumentException("Unsupported " + answerFieldName + " value: " + answer
                    + ". Only Yes is currently supported.");
        }

        pages.decisionAnalysisPage.enterThresholdConditionSection(sectionName, sectionText, answer);
        context.set(sectionName, sectionText);
        context.set(answerFieldName, answer);
    }

    private void enterEarlyAdviceAnalysisSection(
            Map<String, String> analysisDetails,
            String fieldName,
            String sectionName,
            String expectedNextSectionName
    ) {
        String sectionText = resolvedAnalysisText(analysisDetails, fieldName);
        pages.decisionAnalysisPage.enterEarlyAdviceSectionText(
                sectionName,
                sectionText,
                expectedNextSectionName
        );
        context.set(fieldName, sectionText);
    }

    private void skipThresholdAdditionalAnalysis(Map<String, String> analysisDetails) {
        String additionalAnalysis = optionalAnalysisValue(analysisDetails, ADDITIONAL_ANALYSIS_FIELD);
        if (!isBlank(additionalAnalysis)) {
            throw new IllegalArgumentException(ADDITIONAL_ANALYSIS_FIELD
                    + " does not have a text editor. Omit it or leave the value blank.");
        }

        pages.decisionAnalysisPage.skipThresholdAdditionalAnalysis(ADDITIONAL_ANALYSIS_FIELD);
    }

    private void completeEarlyAdvicePostPreviewDetails(Map<String, String> analysisDetails) {
        if (!hasEarlyAdvicePostPreviewDetails(analysisDetails)) {
            return;
        }

        pages.decisionAnalysisPage.checkPreviewEarlyAdviceAnalysis();

        List<String> globalMonitoringCodes = monitoringCodes(analysisDetails, GLOBAL_MONITORING_CODES_FIELD);
        List<String> localMonitoringCodes = monitoringCodes(analysisDetails, LOCAL_MONITORING_CODES_FIELD);
        String relationship = requiredEarlyAdviceSuspectVictimRelationship(analysisDetails);
        pages.decisionAnalysisPage.addSuspectVictimRelationship(relationship, expectedDefendantCount());
        context.set(SUSPECT_VICTIM_RELATIONSHIP_FIELD, relationship);

        pages.decisionAnalysisPage.selectEarlyAdviceMonitoringCodesAndSaveContinue(
                globalMonitoringCodes,
                localMonitoringCodes
        );
    }

    private boolean hasEarlyAdvicePostPreviewDetails(Map<String, String> analysisDetails) {
        return !isBlank(optionalAnalysisValue(analysisDetails, GLOBAL_MONITORING_CODES_FIELD))
                || !isBlank(optionalAnalysisValue(analysisDetails, LOCAL_MONITORING_CODES_FIELD))
                || !isBlank(optionalAnalysisValue(analysisDetails, SUSPECT_VICTIM_RELATIONSHIP_FIELD));
    }

    private String requiredEarlyAdviceSuspectVictimRelationship(Map<String, String> analysisDetails) {
        String relationship = optionalAnalysisValue(analysisDetails, SUSPECT_VICTIM_RELATIONSHIP_FIELD);

        if (isBlank(relationship)) {
            throw new IllegalArgumentException("Early Advice post-preview details require "
                    + SUSPECT_VICTIM_RELATIONSHIP_FIELD + ".");
        }

        if (AUTO_GENERATED_VALUE.equalsIgnoreCase(relationship)) {
            throw new IllegalArgumentException(SUSPECT_VICTIM_RELATIONSHIP_FIELD
                    + " must be an exact selectable relationship, not " + AUTO_GENERATED_VALUE + ".");
        }

        return relationship;
    }

    private String selectedReviewType() {
        String reviewType = context.getAsString("reviewType");
        return isBlank(reviewType) ? DEFAULT_REVIEW_TYPE : reviewType;
    }

    private List<String> selectedChargingDecisionTypes() {
        List<String> decisionTypes = context.get(CHARGING_DECISION_TYPES_CONTEXT_KEY);
        return decisionTypes == null ? List.of() : decisionTypes;
    }

    private boolean selectedCaseRequiresConsent() {
        String caseDataFileName = context.getAsString(CASE_DATA_FILE_NAME_CONTEXT_KEY);
        if (isBlank(caseDataFileName)) {
            return false;
        }

        String normalizedFileName = caseDataFileName
                .replace('_', ' ')
                .replace('-', ' ')
                .toUpperCase(Locale.ROOT);

        return normalizedFileName.contains("AGO")
                || normalizedFileName.contains("DPP")
                || normalizedFileName.contains("COMBINED CONSENT");
    }

    private int expectedDefendantCount() {
        Integer defendantCount = context.getAsInt(DEFENDANT_COUNT_CONTEXT_KEY);
        return defendantCount == null ? 1 : defendantCount;
    }

    private List<String> multiDefendantNamesInDecisionOrder(int expectedDefendantCount) {
        String modifiedCm01Payload = context.getAsString(MODIFIED_CM01_REQUEST_PAYLOAD_CONTEXT_KEY);
        if (isBlank(modifiedCm01Payload)) {
            throw new IllegalStateException("No value found in scenario context for key: "
                    + MODIFIED_CM01_REQUEST_PAYLOAD_CONTEXT_KEY);
        }

        List<String> givenNames = JsonPath.read(
                modifiedCm01Payload,
                "$.PreChargeDecisionRequest.Suspect[*].AccusedPerson.Name.GivenName"
        );
        List<String> familyNames = JsonPath.read(
                modifiedCm01Payload,
                "$.PreChargeDecisionRequest.Suspect[*].AccusedPerson.Name.FamilyName"
        );

        if (givenNames.size() != expectedDefendantCount || familyNames.size() != expectedDefendantCount) {
            throw new IllegalStateException("Expected " + expectedDefendantCount
                    + " defendant name(s) in modified CM01 payload but found given names="
                    + givenNames.size() + ", family names=" + familyNames.size() + ".");
        }

        List<String> defendantNames = new ArrayList<>();
        for (int index = 0; index < expectedDefendantCount; index++) {
            String givenName = givenNames.get(index);
            String familyName = familyNames.get(index);

            if (isBlank(givenName) || isBlank(familyName)) {
                throw new IllegalStateException("Blank defendant name found in modified CM01 payload at index "
                        + index + ".");
            }

            defendantNames.add(defendantDisplayName(familyName, givenName));
        }

        System.out.println("Defendant names from modified CM01 payload: " + defendantNames);
        return defendantNames;
    }

    private String defendantDisplayName(String familyName, String givenName) {
        return familyName.trim().toUpperCase(Locale.ROOT) + ", " + givenName.trim();
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

    private Map<String, String> defaultThresholdPreChargeAnalysisDetails() {
        Map<String, String> defaults = new LinkedHashMap<>();
        defaults.put(normalizedField(CASE_HEADLINE_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(REASONABLE_GROUNDS_TO_SUSPECT_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(REASONABLE_GROUNDS_TO_SUSPECT_MET_FIELD), YES_VALUE);
        defaults.put(normalizedField(FURTHER_EVIDENCE_OBTAINABLE_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(FURTHER_EVIDENCE_OBTAINABLE_MET_FIELD), YES_VALUE);
        defaults.put(normalizedField(SERIOUSNESS_JUSTIFIES_IMMEDIATE_CHARGE_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(SERIOUSNESS_JUSTIFIES_IMMEDIATE_CHARGE_MET_FIELD), YES_VALUE);
        defaults.put(normalizedField(GROUNDS_TO_OBJECT_BAIL_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(GROUNDS_TO_OBJECT_BAIL_MET_FIELD), YES_VALUE);
        defaults.put(normalizedField(PUBLIC_INTEREST_ASSESSMENT_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(PUBLIC_INTEREST_ASSESSMENT_MET_FIELD), YES_VALUE);
        defaults.put(normalizedField(ADDITIONAL_ANALYSIS_FIELD), "");
        defaults.put(normalizedField(SUSPECT_VICTIM_RELATIONSHIP_FIELD), "");
        defaults.put(normalizedField(GLOBAL_MONITORING_CODES_FIELD), "");
        defaults.put(normalizedField(LOCAL_MONITORING_CODES_FIELD), "");
        return defaults;
    }

    private Map<String, String> defaultEarlyAdvicePreChargeAnalysisDetails() {
        Map<String, String> defaults = new LinkedHashMap<>();
        defaults.put(normalizedField(WHAT_ADVICE_IS_SOUGHT_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(MATERIALS_AND_INFORMATION_CONSIDERED_FIELD), AUTO_GENERATED_VALUE);
        defaults.put(normalizedField(YOUR_ADVICE_FIELD), AUTO_GENERATED_VALUE);
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

    private Boolean optionalBooleanValue(Map<String, String> row, String columnName) {
        return row.entrySet().stream()
                .filter(entry -> entry.getKey() != null
                        && normalizedField(entry.getKey()).equals(normalizedField(columnName)))
                .map(Map.Entry::getValue)
                .findFirst()
                .map(value -> Boolean.parseBoolean(value.trim()))
                .orElse(null);
    }

    private String firstNonBlankAnalysisValue(Map<String, String> analysisDetails, String... fieldNames) {
        for (String fieldName : fieldNames) {
            String value = optionalAnalysisValue(analysisDetails, fieldName);
            if (!isBlank(value)) {
                return value;
            }
        }

        return null;
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
