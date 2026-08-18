package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.common.FakerUtils;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.cps.fct.e2e.utils.playwright.PlaywrightNetworkUtils.enableApiTrafficLogging;
import static com.cps.fct.e2e.utils.playwright.PlaywrightNetworkUtils.waitForResponseTriggeredBy;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
public class ChargeDecisionAnalysisPage extends BasePage {

    private static final String CASE_HEADLINE_LABEL = "Case headline";
    private static final String EVIDENTIAL_ANALYSIS_LABEL = "Evidential analysis";
    private static final String WHAT_ADVICE_IS_SOUGHT_LABEL = "What advice is sought?";
    private static final String MATERIALS_AND_INFORMATION_CONSIDERED_LABEL =
            "Materials and information considered";
    private static final String YOUR_ADVICE_LABEL = "Your advice";
    private static final String PUBLIC_INTEREST_ASSESSMENT_LABEL = "Public interest assessment";
    private static final String DISCLOSURE_MANAGEMENT_LABEL = "Disclosure management";
    private static final String ALLOCATION_LABEL = "Allocation";
    private static final String VICTIM_AND_WITNESS_NEEDS_LABEL = "Victim and witness needs";
    private static final String TRIAL_AND_SENTENCING_PREPARATION_LABEL = "Trial and sentencing preparation";
    private static final String HUMAN_RIGHTS_LABEL = "Human Rights";
    private static final String ADVOCATE_AND_OPERATIONAL_DELIVERY_INSTRUCTIONS_LABEL =
            "Advocate and operational delivery instructions";
    private static final String MONITORING_CODES_LABEL = "Monitoring codes";
    private static final String MONITORING_CODES_HEADER = "Monitoring Codes";
    private static final String GLOBAL_MONITORING_CODES_LABEL = "Global monitoring codes";
    private static final String LOCAL_MONITORING_CODES_LABEL = "Local monitoring codes";
    private static final String SUSPECT_VICTIM_RELATIONSHIP_LABEL = "Suspect-victim relationship";
    private static final String PRE_CHARGE_DECISION_LABEL = "Pre-charge decision";
    private static final String NGAP_QUESTION_TEXT = "Has the file been submitted as NGAP?";
    private static final String FIELDSET_LEGEND_SELECTOR = "legend.cps-fieldset__legend";
    private static final String HUMAN_RIGHTS_QUESTION_TEXT = "Are there human rights factors that may affect the case?";
    private static final String DG_COMPLAINT_HEADER = "DG file quality assessment";
    private static final String DG_COMPLAINT_SUBHEADER = "Is the submitted file compliant in accordance with the Directors Guidance (DG6)?";
    private static final String SAVE_AND_CONTINUE_BUTTON_TEXT = "Save and continue";
    private static final String ACTION_SAVE_ANALYSIS_ENDPOINT = "ActionSaveAnalysis";
    private static final String ACTION_SAVE_THRESHOLD_CONDITION_ENDPOINT = "ActionSaveCondition_StreamlinedThreshold";
    private static final String ACTION_REPLACE_THRESHOLD_WORKFLOW_ENDPOINT = "ActionReplaceWorkflow";
    private static final String ACTION_SAVE_DG_ASSESSMENT_ENDPOINT = "ActionCheckSubTypeAndSaveDG";
    // Charging-decision save endpoints. Match on the suspect-count-agnostic prefix
    // (ActionSaveChargeDecision covers both ...MultiSuspect and ...SingleSuspect) so the waits
    // hold across single- and multi-defendant cases rather than being tied to one test's data.
    private static final String ACTION_CHARGE_DECISION_CLEANUP_ENDPOINT = "ActionChargeDecisionCleanUp";
    private static final String ACTION_APPLY_PROPOSED_CHARGE_ENDPOINT = "ActionUpdateProposedChargingDecision";
    private static final String ACTION_SAVE_CHARGE_DECISION_ENDPOINT = "ActionSaveChargeDecision";
    private static final String ACTION_SAVE_CONSENT_DECISIONS_ENDPOINT = "ActionSaveConsentDecisions";
    private static final String SAVE_ANALYSIS_METHOD = "POST";
    private static final int SUCCESS_STATUS = 200;
    private static final String PREVIEW_HEADER = "Preview";
    private static final String CONTINUE_BUTTON_TEXT = "Continue";
    private static final String ADD_RELATIONSHIP_LINK_SELECTOR = "a[id$='Addrelationship']";
    private static final String RELATIONSHIP_QUESTION_PREFIX = "What was the relationship between";
    private static final String PROGRESS_WIZARD_SELECTOR = "[id$='PreChargeAnalysis4']";
    private static final String PROGRESS_WIZARD_SUB_ITEM_SELECTOR = "[data-block='CPS_Component.ProgressWizardSubItem']";
    private static final String COMPLETED_SECTION_ICON_SELECTOR = ".icon.completed.fa.fa-check.fa-2x";
    private static final String SELECTED_SECTION_ICON_SELECTOR = ".icon.selected.fa.fa-circle.fa-4x";
    private static final String RADIO_ROLE_YES = "role=radio[name='Yes']";
    private static final String RADIO_ROLE_NO = "role=radio[name='No']";
    private static final String RADIO_ROLE_NOT_AT_THIS_TIME = "role=radio[name='Not at this time']";
    private static final String DECISION_HEADER = "Charging decision";
    private static final String DECISION_TYPE_QUESTION = "What is your decision for";
    private static final String DECISION_CODE_QUESTION = "Select a decision code";
    private static final String NFA_REASON_QUESTION = "What is your reason for no further action?";
    private static final String OUTCOME_REASON_QUESTION = "Was undermining, unused material a key factor in the outcome of the case?";
    private static final String DECISION_TYPE_FIELD = "decision type";
    private static final String DECISION_CODE_FIELD = "decision code";
    private static final String OFFENCE_CATEGORY_FIELD = "offence category";
    private static final String DECISION_TYPE_NO_FURTHER_ACTION = "No further action";
    private static final String DECISION_TYPE_FURTHER_EVIDENCE_REQUIRED = "Further evidence required";
    private static final String DECISION_TYPE_CHARGE = "Charge";
    private static final String DECISION_TYPE_NON_CONVICTION_DISPOSAL = "Non-conviction disposal";
    private static final String PRINCIPAL_OFFENCE_CATEGORY_LABEL = "Principal offence category";
    private static final String CHARGE_CODE_DECISION_FIELD = "charge code decision";
    private static final String AG_CONSENT_FIELD = "AG consent";
    private static final String EARLY_ADVICE_POC_DROPDOWN_SELECTOR = "[id$='POC_Dropdown']";
    private static final String APPLY_TO_ALL_SUSPECTS_BUTTON_TEXT = "Apply to all suspects";
    private static final String APPLY_TO_ALL_SUSPECTS_BUTTON_SELECTOR =
            "button.btn.govuk-button.govuk-button--secondary:visible";
    private static final String CHARGE_CODE_SELECTOR = "[id$='ChargeCode']";
    private static final String CHECK_ALL_CHARGE_CODES_CHECKBOX_SELECTOR =
            "input[type='checkbox'][id$='CheckboxIsAllChecked']";
    private static final String CHARGE_CODE_BODY_SELECTOR = "[id$='ChargeCodeBody']";
    private static final String CHARGE_CODE_BODY_CHECKBOX_SELECTOR =
            CHARGE_CODE_BODY_SELECTOR + " input[type='checkbox']";
    private static final String CHARGE_DECISION_DROPDOWN_SELECTOR = "select[id*='DecisionDropdown']";
    private static final String CHARGE_APPLY_BUTTON_SELECTOR =
            "button[id*='ApplyButton']";
    private static final String CHARGE_DESCRIPTION_STATUS_BODY_SELECTOR = "[id$='DescriptionAndStatusBody']";
    private static final String AG_CONSENT_TEXT_AREA_SELECTOR = "textarea[id$='TextArea_Content']";
    private static final String CASE_ACTION_PLAN_LABEL = "Case action plan";
    // Charging-decision summary card on the offence-category page. Match on the stable OutSystems
    // widget-name suffixes (no numeric prefixes) so the selectors hold across case types.
    private static final String CHARGING_SUMMARY_CARD_SELECTOR = "[id$='ChargingSummaryGDS']";
    // Row ids carry an opaque generated prefix (e.g. b10-b14-b3-l1-577_0-b4-), so match on the
    // stable "…List_row_<name>" suffix.
    private static final String SUMMARY_ROW_DECISION_SELECTOR = "[id$='List_row_Decision']";
    private static final String SUMMARY_ROW_DECISION_CODE_SELECTOR = "[id$='List_row_DecisionCode']";
    private static final String SUMMARY_ROW_REASON_SELECTOR = "[id$='List_row_Reason']";
    private static final String SUMMARY_ROW_FOCUS_SELECTOR = "[id$='List_row_Focus']";
    private static final String CHARGING_DECISION_HEADER_SELECTOR = "span.govuk-heading-l";
    private static final String CHARGING_DECISION_SUSPECT_COUNT_SELECTOR = "h2.govuk-heading-m";
    private static final String ONE_DEFENDANT_SELECTOR = "[id$='OneDefendant']";
    private static final String DEFENDANTS_SELECTION_LIST_SELECTOR = "div.list-group[id*='DefendantsNoDecisionList']";
    private static final String DEFENDANT_NO_DECISION_ITEM_SELECTOR = ".govuk-checkboxes__item";
    private static final String PREVIEW_SCROLL_SELECTOR = ".previewScroll";
    private static final String PREVIEW_ANALYSIS_STEPS_SELECTOR =
            PREVIEW_SCROLL_SELECTOR + " [id$='AnalysisSteps']";
    private static final String HUMAN_RIGHTS_PREVIEW_TEXT =
            "Human rights factors are not an issue in this case at this time";
    private static final String NONE_SELECTED_TEXT = "None selected";
    private static final String SUSPECT_AWAITING_CHARGING_DECISION_TEXT = "There is 1 suspect awaiting a charging decision.";
    private static final int DEFAULT_WAIT_TIMEOUT_MS = 200;
    private static final int OFFENCE_CATEGORY_WAIT_TIMEOUT_MS = 700;
    private static final int UI_SETTLE_TIMEOUT_MILLIS = 30_000;
    private static final int ADD_RELATIONSHIP_CLICK_ATTEMPTS = 5;
    private static final String SAVING_INDICATOR_TEXT = "Saving...";
    private static final String LOADING_INDICATOR_TEXT = "Loading...";
    private static final Map<String, String> NEXT_SECTION_BY_SECTION = Map.ofEntries(
            Map.entry(CASE_HEADLINE_LABEL, EVIDENTIAL_ANALYSIS_LABEL),
            Map.entry(EVIDENTIAL_ANALYSIS_LABEL, PUBLIC_INTEREST_ASSESSMENT_LABEL),
            Map.entry(PUBLIC_INTEREST_ASSESSMENT_LABEL, DISCLOSURE_MANAGEMENT_LABEL),
            Map.entry(DISCLOSURE_MANAGEMENT_LABEL, ALLOCATION_LABEL),
            Map.entry(ALLOCATION_LABEL, VICTIM_AND_WITNESS_NEEDS_LABEL),
            Map.entry(VICTIM_AND_WITNESS_NEEDS_LABEL, TRIAL_AND_SENTENCING_PREPARATION_LABEL),
            Map.entry(TRIAL_AND_SENTENCING_PREPARATION_LABEL, HUMAN_RIGHTS_LABEL),
            Map.entry(HUMAN_RIGHTS_LABEL, ADVOCATE_AND_OPERATIONAL_DELIVERY_INSTRUCTIONS_LABEL),
            Map.entry(ADVOCATE_AND_OPERATIONAL_DELIVERY_INSTRUCTIONS_LABEL, MONITORING_CODES_LABEL),
            Map.entry(WHAT_ADVICE_IS_SOUGHT_LABEL, MATERIALS_AND_INFORMATION_CONSIDERED_LABEL),
            Map.entry(MATERIALS_AND_INFORMATION_CONSIDERED_LABEL, YOUR_ADVICE_LABEL),
            Map.entry(YOUR_ADVICE_LABEL, PREVIEW_HEADER)
    );
    private final Map<String, String> enteredAnalysisTextBySection = new LinkedHashMap<>();
    private final List<String> selectedGlobalMonitoringCodes = new ArrayList<>();
    private final List<String> selectedLocalMonitoringCodes = new ArrayList<>();
    private String selectedSuspectVictimRelationship;
    private boolean isPreChargeAnalysisNetworkLoggingEnabled;
    private boolean isChargingDecisionNetworkLoggingEnabled;

    public ChargeDecisionAnalysisPage(PlaywrightContext context) {
        super(context);
    }

    public ChargeDecisionAnalysisPage assertCaseHeadlineSection(String typeOfReview) {
        assertThat(page.getByText("You've selected " + typeOfReview, new Page.GetByTextOptions().setExact(true)))
                .isVisible();
        clickSectionIfNotVisible(CASE_HEADLINE_LABEL);
        assertThat(page.locator("h1")).containsText(CASE_HEADLINE_LABEL);
        return this;
    }

    public ChargeDecisionAnalysisPage assertSection(String headerText) {
        clickSectionIfNotVisible(headerText);
        return this;
    }

    public void enablePreChargeAnalysisNetworkLogging() {
        if (isPreChargeAnalysisNetworkLoggingEnabled) {
            return;
        }

        enableApiTrafficLogging(page, "Pre-charge analysis");
        isPreChargeAnalysisNetworkLoggingEnabled = true;
    }

    private void clickSectionIfNotVisible(String sectionName) {
        if (!page.getByLabel(PRE_CHARGE_DECISION_LABEL).getByText(sectionName).isVisible()) {
            Locator sectionLabel = page.getByText(sectionName, new Page.GetByTextOptions().setExact(true)).first();
            sectionLabel.hover();
            sectionLabel.click();
        }
    }

    public void clickSaveAndContinue() {
        waitUntilBusyIndicatorsAreGone();
        clickSaveAndContinueButton();
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
        waitUntilBusyIndicatorsAreGone();
    }

    private void clickSaveAndContinueAndAssertSectionProgress(String completedSectionName) {
        clickSaveAndContinue();
        assertSectionCompleted(completedSectionName);
        assertNextSectionSelectedIfKnown(completedSectionName);
    }

    private void clickSaveAnalysisAndAssertSectionProgress(String completedSectionName) {
        clickSaveAnalysis(completedSectionName);
        assertSectionCompleted(completedSectionName);
        assertNextSectionSelectedIfKnown(completedSectionName);
    }

    private void clickSaveAndContinueAndAssertSectionCompleted(String completedSectionName) {
        clickSaveAndContinue();
        assertSectionCompleted(completedSectionName);
    }

    private void clickSaveAnalysisAndAssertSectionCompleted(String completedSectionName) {
        clickSaveAnalysis(completedSectionName);
        assertSectionCompleted(completedSectionName);
    }

    private void clickSaveThresholdConditionAndAssertSectionCompleted(String completedSectionName) {
        waitUntilBusyIndicatorsAreGone();
        waitForResponseTriggeredBy(
                page,
                "Save Threshold Test condition section: " + completedSectionName,
                ACTION_SAVE_THRESHOLD_CONDITION_ENDPOINT,
                SAVE_ANALYSIS_METHOD,
                SUCCESS_STATUS,
                this::clickSaveAndContinueButton
        );
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
        waitUntilBusyIndicatorsAreGone();
        assertSectionCompleted(completedSectionName);
    }

    private void clickSaveThresholdAdditionalAnalysisAndAssertSectionCompleted(String completedSectionName) {
        waitUntilBusyIndicatorsAreGone();
        waitForResponseTriggeredBy(
                page,
                "Save Threshold Test additional analysis section: " + completedSectionName,
                ACTION_REPLACE_THRESHOLD_WORKFLOW_ENDPOINT,
                SAVE_ANALYSIS_METHOD,
                SUCCESS_STATUS,
                this::clickSaveAndContinueButton
        );
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
        waitUntilBusyIndicatorsAreGone();
        assertSectionCompleted(completedSectionName);
    }

    private void clickSaveAnalysis(String sectionName) {
        waitUntilBusyIndicatorsAreGone();
        waitForResponseTriggeredBy(
                page,
                "Save pre-charge analysis section: " + sectionName,
                ACTION_SAVE_ANALYSIS_ENDPOINT,
                SAVE_ANALYSIS_METHOD,
                SUCCESS_STATUS,
                this::clickSaveAndContinueButton
        );
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
        waitUntilBusyIndicatorsAreGone();
    }

    public ChargeDecisionAnalysisPage enterTextInRichEditor(String randomWords) {
        // Target the editable region by role+contenteditable rather than its accessible-name text,
        // which varies between review types (e.g. Priority PCD Review) and editor versions.
        // Scope to the visible editor: editors from previously-completed sections can linger in the
        // DOM, so an unscoped selector matches multiple and trips Playwright strict mode.
        fillRichTextEditor("[role='textbox'][contenteditable='true']:visible", randomWords);
        return this;
    }

    public ChargeDecisionAnalysisPage checkNGAPOptionAsYes() {
        assertThat(page.locator(FIELDSET_LEGEND_SELECTOR)).containsText(NGAP_QUESTION_TEXT);
        checkCheckbox(RADIO_ROLE_YES);
        return this;
    }

    public ChargeDecisionAnalysisPage checkNGAPOptionAsNo() {
        assertThat(page.locator(FIELDSET_LEGEND_SELECTOR)).containsText(NGAP_QUESTION_TEXT);
        assertElementTextPresent("role=heading[name='Has the file been submitted']");
        checkCheckbox(RADIO_ROLE_NO);
        return this;
    }

    public void enterEvidentialAnalysisData(String randomWords) {
        enterSectionText("Evidential analysis", randomWords);
    }


    public void enterSectionData(String sectionData, String randomWords) {
        enterSectionText(sectionData, randomWords);
    }

    public void enterTextInAllocationSectionAndChooseNGAPAsYes(String sectionName, String randomWords) {
        enterSectionTextOnly(sectionName, randomWords)
                .checkNGAPOptionAsYes()
                .clickSaveAnalysisAndAssertSectionProgress(sectionName);
    }

    // Priority PCD reviews omit the "Has the file been submitted as NGAP?" question from the
    // Allocation section, so only answer it when it is actually rendered. The allocation form
    // fields render with the section (already awaited via the rich-text editor), so a plain
    // presence check is reliable here.
    public void enterTextInAllocationSectionAndChooseNGAPAsYesIfPresent(String sectionName, String randomWords) {
        ChargeDecisionAnalysisPage sectionPage = enterSectionTextOnly(sectionName, randomWords);
        if (isNgapQuestionPresent()) {
            sectionPage.checkNGAPOptionAsYes();
        }
        sectionPage.clickSaveAnalysisAndAssertSectionProgress(sectionName);
    }

    private boolean isNgapQuestionPresent() {
        return page.locator(FIELDSET_LEGEND_SELECTOR)
                .filter(new Locator.FilterOptions().setHasText(NGAP_QUESTION_TEXT))
                .count() > 0;
    }

    public void enterCaseHeadLine(String typeOfReview, String randomWords) {
        enteredAnalysisTextBySection.put(CASE_HEADLINE_LABEL, randomWords);
        assertCaseHeadlineSection(typeOfReview)
                .enterTextInRichEditor(randomWords)
                .clickSaveAnalysisAndAssertSectionProgress(CASE_HEADLINE_LABEL);
    }

    public void enterThresholdCaseHeadLine(String typeOfReview, String randomWords) {
        enteredAnalysisTextBySection.put(CASE_HEADLINE_LABEL, randomWords);
        assertCaseHeadlineSection(typeOfReview)
                .enterTextInRichEditor(randomWords)
                .clickSaveAnalysisAndAssertSectionCompleted(CASE_HEADLINE_LABEL);
    }

    public void enterSectionText(String headerLabel, String randomWords) {
        enteredAnalysisTextBySection.put(headerLabel, randomWords);
        assertSection(headerLabel).enterTextInRichEditor(randomWords).clickSaveAnalysisAndAssertSectionProgress(headerLabel);
    }

    public void enterEarlyAdviceSectionText(String headerLabel, String randomWords, String expectedNextHeader) {
        enteredAnalysisTextBySection.put(headerLabel, randomWords);
        assertSection(headerLabel)
                .enterTextInRichEditor(randomWords)
                .clickSaveAndContinue();
        assertPageHeaderContains(expectedNextHeader);
    }

    public void enterThresholdConditionSection(String headerLabel, String randomWords, String answer) {
        enteredAnalysisTextBySection.put(headerLabel, randomWords);
        assertSection(headerLabel)
                .enterTextInRichEditor(randomWords)
                .chooseVisibleRadioAnswer(answer)
                .clickSaveThresholdConditionAndAssertSectionCompleted(headerLabel);
    }

    public void skipThresholdAdditionalAnalysis(String headerLabel) {
        assertSection(headerLabel)
                .clickSaveThresholdAdditionalAnalysisAndAssertSectionCompleted(headerLabel);
        assertMonitoringCodesPageVisible();
    }

    public ChargeDecisionAnalysisPage enterSectionTextOnly(String headerLabel, String randomWords) {
        enteredAnalysisTextBySection.put(headerLabel, randomWords);
        assertSection(headerLabel).enterTextInRichEditor(randomWords);
        return this;
    }

    private ChargeDecisionAnalysisPage chooseVisibleRadioAnswer(String answer) {
        Locator radio = page.getByRole(
                AriaRole.RADIO,
                new Page.GetByRoleOptions().setName(answer).setExact(true)
        ).first();
        radio.scrollIntoViewIfNeeded();
        assertThat(radio).isVisible();
        radio.check(new Locator.CheckOptions().setForce(true));
        assertThat(radio).isChecked();
        return this;
    }

    public void humanRightsOptionHasNotAtThisTime() {
        assertThat(page.locator(FIELDSET_LEGEND_SELECTOR)).containsText(HUMAN_RIGHTS_QUESTION_TEXT);
        checkCheckbox(RADIO_ROLE_NOT_AT_THIS_TIME);
        enteredAnalysisTextBySection.put(HUMAN_RIGHTS_LABEL, HUMAN_RIGHTS_PREVIEW_TEXT);
        clickSaveAndContinueAndAssertSectionProgress(HUMAN_RIGHTS_LABEL);
    }

    public void selectMonitoringCodesAndSaveContinue(String monitoringCodeType, List<String> monitoringCodes) {
        assertMonitoringCodesPageVisible();
        selectMonitoringCodes(monitoringCodeType, monitoringCodes);
        clickSaveAndContinueAndAssertSectionProgress(MONITORING_CODES_LABEL);
    }

    public void selectGlobalMonitoringCodesAndSaveContinue(List<String> monitoringCodes) {
        selectMonitoringCodesAndSaveContinue("Global", monitoringCodes);
    }

    public void selectMonitoringCodesAndSaveContinue(
            List<String> globalMonitoringCodes,
            List<String> localMonitoringCodes
    ) {
        assertMonitoringCodesPageVisible();
        selectMonitoringCodes("Global", globalMonitoringCodes);
        selectMonitoringCodes("Local", localMonitoringCodes);
        clickSaveAndContinueAndAssertSectionProgress(MONITORING_CODES_LABEL);
    }

    public void selectEarlyAdviceMonitoringCodesAndSaveContinue(
            List<String> globalMonitoringCodes,
            List<String> localMonitoringCodes
    ) {
        assertMonitoringCodesPageVisible();
        selectMonitoringCodes("Global", globalMonitoringCodes);
        selectMonitoringCodes("Local", localMonitoringCodes);
        clickSaveAndContinue();
        assertPageHeaderContains(PRINCIPAL_OFFENCE_CATEGORY_LABEL);
    }

    public void selectPrincipalOffenceCategory(String offenceCategory) {
        selectPrincipalOffenceCategory(offenceCategory, 1);
    }

    public void selectPrincipalOffenceCategory(String offenceCategory, int defendantCount) {
        assertPageHeaderContains(PRINCIPAL_OFFENCE_CATEGORY_LABEL);
        selectEarlyAdvicePocDropdownOption(offenceCategory, defendantCount);
        clickSaveAndContinue();
    }

    public void selectEarlyAdvicePrincipalOffenceCategoryAndContinue(String offenceCategory) {
        selectEarlyAdvicePrincipalOffenceCategoryAndContinue(offenceCategory, 1);
    }

    public void selectEarlyAdvicePrincipalOffenceCategoryAndContinue(String offenceCategory, int defendantCount) {
        assertPageHeaderContains(PRINCIPAL_OFFENCE_CATEGORY_LABEL);
        selectEarlyAdvicePocDropdownOption(offenceCategory, defendantCount);
        clickSaveAndContinue();
        assertPageHeaderContains(CASE_ACTION_PLAN_LABEL);
    }

    private void selectEarlyAdvicePocDropdownOption(String optionText, int defendantCount) {
        Locator dropdown = page.locator(EARLY_ADVICE_POC_DROPDOWN_SELECTOR).first();
        assertThat(dropdown).isVisible();
        dropdown.scrollIntoViewIfNeeded();

        if (isNativeSelect(dropdown)) {
            selectNativeDropdownOption(dropdown, optionText, PRINCIPAL_OFFENCE_CATEGORY_LABEL);
        } else {
            selectTypeAheadDropdownOption(dropdown, optionText);
        }

        applyPrincipalOffenceCategoryToAllSuspectsIfNeeded(defendantCount);
    }

    private void applyPrincipalOffenceCategoryToAllSuspectsIfNeeded(int defendantCount) {
        if (defendantCount <= 1) {
            return;
        }

        Locator applyToAllSuspectsButton = page.locator(APPLY_TO_ALL_SUSPECTS_BUTTON_SELECTOR)
                .filter(new Locator.FilterOptions().setHasText(APPLY_TO_ALL_SUSPECTS_BUTTON_TEXT));

        assertThat(applyToAllSuspectsButton).isVisible();
        applyToAllSuspectsButton.scrollIntoViewIfNeeded();
        applyToAllSuspectsButton.click();
        waitUntilBusyIndicatorsAreGone();
    }

    private boolean isNativeSelect(Locator locator) {
        return Boolean.TRUE.equals(locator.evaluate("element => element.tagName.toLowerCase() === 'select'"));
    }

    private void selectNativeDropdownOption(Locator dropdown, String optionText, String fieldName) {
        Locator options = dropdown.locator("option");
        for (int optionIndex = 0; optionIndex < options.count(); optionIndex++) {
            String visibleText = options.nth(optionIndex).innerText().trim();
            if (optionMatches(visibleText, optionText)) {
                dropdown.selectOption(new SelectOption().setIndex(optionIndex));
                return;
            }
        }

        throw new IllegalArgumentException(fieldName + " option was not found: " + optionText);
    }

    private void selectTypeAheadDropdownOption(Locator dropdown, String optionText) {
        dropdown.click();
        Locator textInput = dropdown.locator("input").first();
        if (textInput.count() == 0) {
            textInput = page.locator(EARLY_ADVICE_POC_DROPDOWN_SELECTOR + " input").first();
        }

        if (textInput.count() > 0 && textInput.isVisible()) {
            textInput.fill(optionText);
        } else {
            dropdown.pressSequentially(optionText);
        }

        if (selectVisibleTypeAheadOption(optionText)) {
            return;
        }

        throw new IllegalArgumentException(ChargeDecisionAnalysisPage.PRINCIPAL_OFFENCE_CATEGORY_LABEL + " option was not found: " + optionText);
    }

    private boolean selectVisibleTypeAheadOption(String optionText) {
        return Boolean.TRUE.equals(page.evaluate("""
                optionText => {
                    const normalize = value => (value || '').replace(/\\s+/g, ' ').trim().toLowerCase();
                    const expected = normalize(optionText);
                    const isVisible = element => {
                        const style = window.getComputedStyle(element);
                        const rect = element.getBoundingClientRect();
                        return style.display !== 'none'
                            && style.visibility !== 'hidden'
                            && rect.width > 0
                            && rect.height > 0;
                    };
                    const optionSelectors = [
                        '[role="option"]',
                        '.select2-results__option',
                        '.choices__item--choice',
                        '.dropdown-item',
                        'li',
                        'option'
                    ];
                    const candidates = Array.from(document.querySelectorAll(optionSelectors.join(',')))
                        .filter(isVisible);
                    const option = candidates.find(element => normalize(element.innerText || element.textContent) === expected)
                        || candidates.find(element => normalize(element.innerText || element.textContent).includes(expected));

                    if (!option) {
                        return false;
                    }

                    option.scrollIntoView({ block: 'center', inline: 'nearest' });
                    option.click();
                    return true;
                }
                """, optionText));
    }

    private boolean optionMatches(String actualText, String expectedText) {
        String actual = normalizeText(actualText);
        String expected = normalizeText(expectedText);
        return actual.equals(expected) || actual.contains(expected);
    }

    private String normalizeText(String text) {
        return text == null ? "" : text.replaceAll("\\s+", " ").trim().toLowerCase();
    }

    private void assertPageHeaderContains(String expectedText) {
        page.waitForCondition(() -> Boolean.TRUE.equals(page.evaluate("""
                expectedText => {
                    const normalize = value => (value || '').replace(/\\s+/g, ' ').trim().toLowerCase();
                    const expected = normalize(expectedText);
                    const isVisible = element => {
                        const style = window.getComputedStyle(element);
                        const rect = element.getBoundingClientRect();
                        return style.display !== 'none'
                            && style.visibility !== 'hidden'
                            && rect.width > 0
                            && rect.height > 0;
                    };

                    return Array.from(document.querySelectorAll('h1, span.govuk-heading-l'))
                        .some(element =>
                            isVisible(element)
                                && normalize(element.innerText || element.textContent).includes(expected)
                        );
                }
                """, expectedText)));
    }

    private void assertMonitoringCodesPageVisible() {
        Locator pageHeading = page.locator(CHARGING_DECISION_HEADER_SELECTOR)
                .filter(new Locator.FilterOptions().setHasText(MONITORING_CODES_HEADER))
                .first();
        assertThat(pageHeading).isVisible();
    }

    private void clickSaveAndContinueButton() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            boolean clicked = Boolean.TRUE.equals(page.evaluate("""
                    buttonText => {
                        const normalize = value => (value || '').replace(/\\s+/g, ' ').trim().toLowerCase();
                        const expectedText = normalize(buttonText);
                        const isVisible = element => {
                            const style = window.getComputedStyle(element);
                            const rect = element.getBoundingClientRect();
                            return style.display !== 'none'
                                && style.visibility !== 'hidden'
                                && rect.width > 0
                                && rect.height > 0;
                        };
                        const textFor = element => normalize(
                            element.innerText
                                || element.textContent
                                || element.value
                                || element.getAttribute('aria-label')
                        );
                        const button = Array.from(
                            document.querySelectorAll('button, input[type="submit"], input[type="button"], a')
                        ).find(element =>
                            textFor(element) === expectedText
                                && isVisible(element)
                                && !element.disabled
                                && element.getAttribute('aria-disabled') !== 'true'
                        );

                        if (!button) {
                            return false;
                        }

                        button.scrollIntoView({ block: 'center', inline: 'nearest' });
                        button.click();
                        return true;
                    }
                    """, SAVE_AND_CONTINUE_BUTTON_TEXT));

            if (clicked) {
                return;
            }

            page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
        }

        throw new IllegalStateException("Save and continue button was not found or clicked.");
    }

    private void waitUntilBusyIndicatorsAreGone() {
        long deadline = System.currentTimeMillis() + UI_SETTLE_TIMEOUT_MILLIS;
        while (System.currentTimeMillis() < deadline) {
            boolean busyIndicatorVisible = Boolean.TRUE.equals(page.evaluate("""
                    () => {
                        const busyTexts = ['Loading...', 'Saving...'];
                        const isVisible = element => {
                            const style = window.getComputedStyle(element);
                            const rect = element.getBoundingClientRect();
                            return style.display !== 'none'
                                && style.visibility !== 'hidden'
                                && rect.width > 0
                                && rect.height > 0;
                        };
                        return Array.from(document.querySelectorAll('body *')).some(element =>
                            busyTexts.includes((element.innerText || element.textContent || '').trim())
                                && isVisible(element)
                        );
                    }
                    """));

            if (!busyIndicatorVisible) {
                return;
            }

            page.waitForTimeout(250);
        }

        throw new IllegalStateException("Loading or Saving indicator did not disappear within "
                + UI_SETTLE_TIMEOUT_MILLIS + "ms.");
    }

    private void checkMonitoringCode(String monitoringCodeType, String monitoringCode) {
        String normalizedMonitoringCode = monitoringCode.trim();
        Locator label = monitoringCodeLabel(monitoringCodeType, normalizedMonitoringCode);
        Locator checkbox = checkboxForMonitoringCodeLabel(label);

        if (checkbox.isChecked()) {
            return;
        }

        // Toggle the checkbox itself (by its stable id) rather than the JS-marked label: OutSystems
        // re-renders from its own state and drops our injected marker attribute, detaching the label
        // mid-click. Force bypasses the custom-control overlay that intercepts pointer events.
        checkbox.scrollIntoViewIfNeeded();
        checkbox.check(new Locator.CheckOptions().setForce(true));
        assertThat(checkbox).isChecked();
    }

    private void assertMonitoringCodeChecked(String monitoringCodeType, String monitoringCode) {
        Locator checkbox = checkboxForMonitoringCodeLabel(
                monitoringCodeLabel(monitoringCodeType, monitoringCode.trim())
        );
        assertThat(checkbox).isChecked();
    }

    private static final String MONITORING_CODE_LABEL_MARKER = "data-e2e-monitoring-code-label";

    private Locator monitoringCodeLabel(String monitoringCodeType, String monitoringCode) {
        String sectionHeading = switch (normalizedMonitoringCodeType(monitoringCodeType)) {
            case "global" -> GLOBAL_MONITORING_CODES_LABEL;
            case "local" -> LOCAL_MONITORING_CODES_LABEL;
            default -> throw new IllegalArgumentException("Unsupported monitoring code type: " + monitoringCodeType
                    + ". Use Global or Local.");
        };

        long deadline = System.currentTimeMillis() + UI_SETTLE_TIMEOUT_MILLIS;
        while (System.currentTimeMillis() < deadline) {
            scrollMonitoringCodeSectionIntoView(sectionHeading);
            if (markMonitoringCodeLabel(sectionHeading, monitoringCode)) {
                Locator label = page.locator("[" + MONITORING_CODE_LABEL_MARKER + "='true']");
                assertThat(label).isVisible();
                return label;
            }
            page.waitForTimeout(250);
        }

        throw new IllegalStateException("Monitoring code label not found within "
                + UI_SETTLE_TIMEOUT_MILLIS + "ms: section='" + sectionHeading
                + "', code='" + monitoringCode + "'.");
    }

    private void scrollMonitoringCodeSectionIntoView(String sectionHeading) {
        Boolean.TRUE.equals(page.evaluate("""
                sectionHeading => {
                    const normalize = value => (value || '').replace(/\\s+/g, ' ').trim().toLowerCase();
                    const targetHeading = normalize(sectionHeading);
                    const isVisible = element => {
                        const style = window.getComputedStyle(element);
                        const rect = element.getBoundingClientRect();
                        return style.display !== 'none'
                            && style.visibility !== 'hidden'
                            && rect.width > 0
                            && rect.height > 0;
                    };
                    const textOf = element => normalize(element.innerText || element.textContent);
                    const matchesHeading = element => {
                        const text = textOf(element);
                        return text === targetHeading || text.startsWith(targetHeading) || text.includes(targetHeading);
                    };

                    const heading = Array.from(document.querySelectorAll('h1, h2, h3, h4, h5, h6, legend, span, label, p, div, strong, b'))
                        .filter(element => isVisible(element) && matchesHeading(element))
                        .sort((a, b) => textOf(a).length - textOf(b).length)[0];

                    if (!heading) {
                        return false;
                    }

                    heading.scrollIntoView({ block: 'center', inline: 'nearest' });
                    return true;
                }
                """, sectionHeading));
        page.waitForTimeout(100);
    }

    private boolean markMonitoringCodeLabel(String sectionHeading, String monitoringCode) {
        return Boolean.TRUE.equals(page.evaluate("""
                ([sectionHeading, codeLabel, markerAttr, sectionHeadings]) => {
                    const normalize = value => (value || '').replace(/\\s+/g, ' ').trim().toLowerCase();
                    const targetHeading = normalize(sectionHeading);
                    const targetLabel = normalize(codeLabel);
                    const knownHeadings = sectionHeadings.map(normalize);
                    const isVisible = element => {
                        const style = window.getComputedStyle(element);
                        const rect = element.getBoundingClientRect();
                        return style.display !== 'none'
                            && style.visibility !== 'hidden'
                            && rect.width > 0
                            && rect.height > 0;
                    };

                    const headingSelector = 'h1, h2, h3, h4, h5, h6, legend, span, label, p, div, strong, b';
                    const allElements = Array.from(document.querySelectorAll(headingSelector));
                    const textOf = el => normalize(el.innerText || el.textContent);
                    const matchesHeading = (el, target) => {
                        const text = textOf(el);
                        return text === target || text.startsWith(target) || text.includes(target);
                    };

                    const headingCandidates = allElements
                        .filter(el => isVisible(el) && matchesHeading(el, targetHeading))
                        .sort((a, b) => textOf(a).length - textOf(b).length);

                    if (headingCandidates.length === 0) {
                        return false;
                    }

                    const boundaryCandidates = allElements
                        .filter(el => isVisible(el)
                            && knownHeadings.some(known => known !== targetHeading && matchesHeading(el, known)));

                    const allVisibleLabels = Array.from(document.querySelectorAll('label')).filter(isVisible);

                    for (const heading of headingCandidates) {
                        const followingBoundary = boundaryCandidates.find(el =>
                            heading.compareDocumentPosition(el) & Node.DOCUMENT_POSITION_FOLLOWING
                        );

                        const match = allVisibleLabels.find(l => {
                            if (normalize(l.innerText || l.textContent) !== targetLabel) {
                                return false;
                            }
                            const afterHeading = Boolean(
                                heading.compareDocumentPosition(l) & Node.DOCUMENT_POSITION_FOLLOWING
                            );
                            if (!afterHeading) {
                                return false;
                            }
                            if (!followingBoundary) {
                                return true;
                            }
                            return Boolean(
                                followingBoundary.compareDocumentPosition(l) & Node.DOCUMENT_POSITION_PRECEDING
                            );
                        });

                        if (match) {
                            document.querySelectorAll('[' + markerAttr + '="true"]')
                                .forEach(el => el.removeAttribute(markerAttr));
                            match.setAttribute(markerAttr, 'true');
                            return true;
                        }
                    }
                    return false;
                }
                """, List.of(
                        sectionHeading,
                        monitoringCode.trim(),
                        MONITORING_CODE_LABEL_MARKER,
                        List.of(
                                GLOBAL_MONITORING_CODES_LABEL,
                                LOCAL_MONITORING_CODES_LABEL,
                                SUSPECT_VICTIM_RELATIONSHIP_LABEL
                        )
                )));
    }

    // OutSystems builds widget ids as a chain of auto-generated segments before the widget's own name:
    // container tokens (b10-b11-b3-...), list tokens (l1-) and per-row record keys (475_2-). Every one of
    // those is transient - container tokens are reassigned on re-render, and the record key changes with
    // the data between runs (470_2 -> 475_2). Scope to the label's checkbox row first, then match on the
    // stable widget family (GlobalCodeCheckbox covers both GlobalCodeCheckbox and GlobalCodeCheckbox2).
    private static final Pattern VOLATILE_ID_PREFIX = Pattern.compile("^([a-z]?\\d+(_\\d+)?-)+");
    private static final Pattern TRAILING_WIDGET_ORDINAL = Pattern.compile("\\d+$");

    private Locator checkboxForMonitoringCodeLabel(Locator label) {
        Locator checkboxItem = label.locator(
                "xpath=ancestor::*[contains(concat(' ', normalize-space(@class), ' '), "
                        + "' govuk-checkboxes__item ')][1]"
        );
        assertThat(checkboxItem).isVisible();

        String checkboxId = label.getAttribute("for");
        if (checkboxId != null && !checkboxId.isBlank()) {
            String stableIdSuffix = VOLATILE_ID_PREFIX.matcher(checkboxId).replaceFirst("");
            String stableIdFamily = TRAILING_WIDGET_ORDINAL.matcher(stableIdSuffix).replaceFirst("");
            Locator checkbox = checkboxItem.locator("input[type='checkbox'][id*=" + cssString(stableIdFamily) + "]");
            assertThat(checkbox).hasCount(1);
            assertThat(checkbox).isVisible();
            return checkbox;
        }

        Locator checkbox = checkboxItem.locator("input[type='checkbox']");
        assertThat(checkbox).hasCount(1);
        assertThat(checkbox).isVisible();
        return checkbox;
    }

    private String cssString(String value) {
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

    private String normalizedMonitoringCodeType(String monitoringCodeType) {
        return monitoringCodeType.trim().toLowerCase();
    }

    private void selectMonitoringCodes(String monitoringCodeType, List<String> monitoringCodes) {
        rememberSelectedMonitoringCodes(monitoringCodeType, monitoringCodes);

        if (monitoringCodes == null || monitoringCodes.isEmpty()) {
            return;
        }

        for (String monitoringCode : monitoringCodes) {
            checkMonitoringCode(monitoringCodeType, monitoringCode);
            page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
            waitUntilBusyIndicatorsAreGone();
        }
        for (String monitoringCode : monitoringCodes) {
            assertMonitoringCodeChecked(monitoringCodeType, monitoringCode);
        }
    }

    private void rememberSelectedMonitoringCodes(String monitoringCodeType, List<String> monitoringCodes) {
        List<String> targetCodes = switch (normalizedMonitoringCodeType(monitoringCodeType)) {
            case "global" -> selectedGlobalMonitoringCodes;
            case "local" -> selectedLocalMonitoringCodes;
            default -> throw new IllegalArgumentException("Unsupported monitoring code type: " + monitoringCodeType
                    + ". Use Global or Local.");
        };

        targetCodes.clear();
        if (monitoringCodes == null) {
            return;
        }

        monitoringCodes.stream()
                .map(String::trim)
                .filter(code -> !code.isBlank())
                .forEach(targetCodes::add);
    }

    private void assertPreviewAnalysisSteps() {
        Locator previewScroll = page.locator(PREVIEW_SCROLL_SELECTOR);
        previewScroll.scrollIntoViewIfNeeded();
        assertThat(previewScroll).isVisible();
        assertThat(page.locator(PREVIEW_ANALYSIS_STEPS_SELECTOR).first()).isVisible();

        enteredAnalysisTextBySection.values()
                .forEach(enteredText -> assertLocatorContainsNormalizedText(
                        previewScroll,
                        enteredText,
                        "Preview analysis text"
                ));
    }

    private void assertPreviewGlobalMonitoringCodes() {
        assertPreviewMonitoringCodes(
                previewRowByLabel(GLOBAL_MONITORING_CODES_LABEL),
                selectedGlobalMonitoringCodes
        );
    }

    private void assertPreviewLocalMonitoringCodes() {
        assertPreviewMonitoringCodes(
                previewRowByLabel(LOCAL_MONITORING_CODES_LABEL),
                selectedLocalMonitoringCodes
        );
    }

    private void assertPreviewMonitoringCodes(Locator previewSection, List<String> expectedCodes) {
        previewSection.scrollIntoViewIfNeeded();
        assertThat(previewSection).isVisible();

        if (expectedCodes.isEmpty()) {
            assertLocatorContainsNormalizedText(previewSection, NONE_SELECTED_TEXT, "Preview monitoring codes");
            return;
        }

        expectedCodes.forEach(code -> assertLocatorContainsNormalizedText(
                previewSection,
                code,
                "Preview monitoring codes"
        ));
    }

    private void assertPreviewSuspectVictimRelationship() {
        Locator suspectRelationshipSection = previewRowByLabel("Suspect-victim relationship");
        suspectRelationshipSection.scrollIntoViewIfNeeded();
        assertThat(suspectRelationshipSection).isVisible();

        if (selectedSuspectVictimRelationship != null && !selectedSuspectVictimRelationship.isBlank()) {
            assertLocatorContainsNormalizedText(
                    suspectRelationshipSection,
                    selectedSuspectVictimRelationship,
                    "Preview suspect-victim relationship"
            );
        }
    }

    private Locator previewRowByLabel(String label) {
        page.locator(PREVIEW_SCROLL_SELECTOR).scrollIntoViewIfNeeded();

        boolean rowMarked = Boolean.TRUE.equals(page.evaluate("""
                ([previewSelector, label]) => {
                    const normalize = value => (value || '').replace(/\\s+/g, ' ').trim().toLowerCase();
                    const expectedLabel = normalize(label);
                    const previewRoot = document.querySelector(previewSelector)?.parentElement || document.body;
                    const isVisible = element => {
                        const style = window.getComputedStyle(element);
                        const rect = element.getBoundingClientRect();
                        return style.display !== 'none'
                            && style.visibility !== 'hidden'
                            && rect.width > 0
                            && rect.height > 0;
                    };

                    const candidates = Array.from(previewRoot.querySelectorAll('div, section, article, tr'));
                    const matchingLabel = candidates.find(element =>
                        isVisible(element)
                            && normalize(element.innerText || element.textContent).startsWith(expectedLabel)
                    );

                    if (!matchingLabel) {
                        return false;
                    }

                    let row = matchingLabel;
                    for (let i = 0; row && i < 5; i++) {
                        const rowText = normalize(row.innerText || row.textContent);
                        if (rowText.includes(expectedLabel)
                                && (rowText.length > expectedLabel.length || row.querySelector('a, button'))) {
                            break;
                        }
                        row = row.parentElement;
                    }

                    if (!row) {
                        return false;
                    }

                    document.querySelectorAll("[data-e2e-preview-row='true']")
                        .forEach(element => element.removeAttribute('data-e2e-preview-row'));
                    row.setAttribute('data-e2e-preview-row', 'true');
                    row.scrollIntoView({ block: 'center', inline: 'nearest' });
                    return true;
                }
                """, List.of(PREVIEW_SCROLL_SELECTOR, label)));

        if (!rowMarked) {
            throw new IllegalStateException("Preview row was not found: " + label);
        }

        return page.locator("[data-e2e-preview-row='true']");
    }

    private void assertLocatorContainsNormalizedText(Locator locator, String expectedText, String description) {
        Assertions.assertThat(normalizePreviewText(locator.innerText()))
                .as(description)
                .contains(normalizePreviewText(expectedText));
    }

    private String normalizePreviewText(String text) {
        if (text == null) {
            return "";
        }

        return text
                .replace('\u00A0', ' ')
                .replace("\u2026", "...")
                .replace("\u2018", "'")
                .replace("\u2019", "'")
                .replace("\u201C", "\"")
                .replace("\u201D", "\"")
                .replace("\u2013", "-")
                .replace("\u2014", "-")
                .replaceAll("\\s+", " ")
                .trim();
    }

    public void addSuspectVictimRelationship(String relationshipType) {
        addSuspectVictimRelationship(relationshipType, 1);
    }

    public void addSuspectVictimRelationship(String relationshipType, int expectedRelationshipCount) {
        waitForAddRelationshipLinkCount(expectedRelationshipCount);

        for (int index = 0; index < expectedRelationshipCount; index++) {
            waitForAddRelationshipLinkCount(expectedRelationshipCount - index);
            clickNextAddRelationshipLink();
            completeVisibleSuspectVictimRelationship(relationshipType);
        }

        selectedSuspectVictimRelationship = relationshipType;
    }

    private void clickNextAddRelationshipLink() {
        for (int attempt = 1; attempt <= ADD_RELATIONSHIP_CLICK_ATTEMPTS; attempt++) {
            try {
                Locator relationshipLink = page.locator(ADD_RELATIONSHIP_LINK_SELECTOR).first();
                assertThat(relationshipLink).isVisible();
                relationshipLink.click();
                return;
            } catch (PlaywrightException exception) {
                if (!isDetachedFromDomError(exception) || attempt == ADD_RELATIONSHIP_CLICK_ATTEMPTS) {
                    throw exception;
                }
                page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
            }
        }
    }

    private boolean isDetachedFromDomError(PlaywrightException exception) {
        String message = exception.getMessage();
        return message != null && message.contains("Element is not attached to the DOM");
    }

    private void waitForAddRelationshipLinkCount(int expectedRelationshipCount) {
        Assertions.assertThat(expectedRelationshipCount)
                .as("Expected suspect-victim relationship add link count")
                .isGreaterThan(0);

        Locator relationshipLinks = page.locator(ADD_RELATIONSHIP_LINK_SELECTOR);
        page.waitForCondition(() -> relationshipLinks.count() == expectedRelationshipCount);
        Assertions.assertThat(relationshipLinks.count())
                .as("Suspect-victim relationship add link count")
                .isEqualTo(expectedRelationshipCount);
    }

    private void completeVisibleSuspectVictimRelationship(String relationshipType) {
        assertThat(relationshipQuestion()).isVisible();

        Locator relationshipCheckbox = page.getByRole(
                AriaRole.CHECKBOX,
                new Page.GetByRoleOptions().setName(relationshipType).setExact(true)
        );
        assertThat(relationshipCheckbox).isVisible();
        relationshipCheckbox.scrollIntoViewIfNeeded();
        relationshipCheckbox.check();

        Locator continueButton = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(CONTINUE_BUTTON_TEXT).setExact(true)
        );
        assertThat(continueButton).isVisible();
        continueButton.scrollIntoViewIfNeeded();
        continueButton.click();
        waitUntilSpinnersAreGone(SAVING_INDICATOR_TEXT, LOADING_INDICATOR_TEXT);
        assertThat(relationshipQuestion()).not().isVisible();
    }

    public void checkPreviewChargeAnalysis() {
        assertThat(page.locator(CHARGING_DECISION_HEADER_SELECTOR)).containsText(PREVIEW_HEADER);
        assertPreviewAnalysisSteps();
        assertPreviewGlobalMonitoringCodes();
        assertPreviewSuspectVictimRelationship();
        assertPreviewLocalMonitoringCodes();
        clickSaveAndContinue();
    }

    public void checkPreviewEarlyAdviceAnalysis() {
        assertPageHeaderContains(PREVIEW_HEADER);
        clickSaveAndContinue();
        assertPageHeaderContains(MONITORING_CODES_HEADER);
    }

    public void checkDGComplaintAsYes() {
        String headingText = page.locator("h1").first().innerText();
        if (normalizePreviewText(headingText).contains(DECISION_HEADER)) {
            return;
        }

        assertThat(page.locator("h1")).containsText(DG_COMPLAINT_HEADER);
        // Priority PCD reviews don't require a DG file quality assessment: the page shows an
        // informational message ("You do not need to complete a DG file quality assessment...")
        // and only a Save and continue button, with no compliance question to answer.
        if (isDgComplianceQuestionPresent()) {
            checkCheckbox(RADIO_ROLE_YES);
        }
        clickSaveDgAssessment();
    }

    private void clickSaveDgAssessment() {
        waitUntilBusyIndicatorsAreGone();
        waitForResponseTriggeredBy(
                page,
                "Save DG file quality assessment",
                ACTION_SAVE_DG_ASSESSMENT_ENDPOINT,
                SAVE_ANALYSIS_METHOD,
                SUCCESS_STATUS,
                this::clickSaveAndContinueButton
        );
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
        waitUntilBusyIndicatorsAreGone();
    }

    private boolean isDgComplianceQuestionPresent() {
        return page.getByText(DG_COMPLAINT_SUBHEADER, new Page.GetByTextOptions().setExact(true)).count() > 0;
    }

    // Runs a charging-decision click and blocks until its backing POST resolves, instead of racing
    // the next assertion behind a fixed timeout. Listening starts before the click, so a fast
    // response cannot be missed.
    private void clickAndWaitForChargingResponse(String actionDescription, String endpointContains, Runnable action) {
        waitUntilBusyIndicatorsAreGone();
        waitForResponseTriggeredBy(
                page,
                actionDescription,
                endpointContains,
                SAVE_ANALYSIS_METHOD,
                SUCCESS_STATUS,
                action
        );
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
        waitUntilBusyIndicatorsAreGone();
    }

    public void applyChargingDecision(Map<String, String> decisionChargingData) {
        applyChargingDecision(decisionChargingData, false);
    }

    public void applyChargingDecision(Map<String, String> decisionChargingData, boolean requiresConsent) {
        Map<String, String> normalizedDecisionChargingData =
                normalizedChargingDecisionData(decisionChargingData);

        startChargingDecisionForDefendant(null);
        completeChargingDecisionDetails(normalizedDecisionChargingData);
        selectOffenceCategory(normalizedDecisionChargingData);
        finishChargingDecisionAfterFinalDefendant();
        completeConsentIfRequired(normalizedDecisionChargingData, requiresConsent);
    }

    public void applyChargingDecisions(
            List<Map<String, String>> decisionChargingDataRows,
            List<String> defendantNames
    ) {
        applyChargingDecisions(decisionChargingDataRows, defendantNames, false);
    }

    public void applyChargingDecisions(
            List<Map<String, String>> decisionChargingDataRows,
            List<String> defendantNames,
            boolean requiresConsent
    ) {
        Assertions.assertThat(decisionChargingDataRows)
                .as("Multi-defendant charging decision rows")
                .isNotEmpty();
        Assertions.assertThat(defendantNames)
                .as("Multi-defendant charging decision names")
                .hasSize(decisionChargingDataRows.size());

        startChargingDecisionForDefendant(defendantNames.getFirst());

        for (int index = 0; index < decisionChargingDataRows.size(); index++) {
            Map<String, String> normalizedDecisionChargingData =
                    normalizedChargingDecisionData(decisionChargingDataRows.get(index));

            completeChargingDecisionDetails(normalizedDecisionChargingData);
            selectOffenceCategory(normalizedDecisionChargingData, index);
            continueWithNextDefendantOrFinish(
                    defendantNames,
                    index,
                    normalizedDecisionChargingData,
                    requiresConsent
            );
        }
    }

    private void startChargingDecisionForDefendant(String defendantName) {
        waitForTextInLocator("h1", DECISION_HEADER);

        if (defendantName != null && !defendantName.isBlank()) {
            selectDefendantForChargingDecision(defendantName);
        }

        clickAndWaitForChargingResponse(
                "Start charging decision",
                ACTION_CHARGE_DECISION_CLEANUP_ENDPOINT,
                this::clickSaveAndContinueButton
        );
    }

    private void completeChargingDecisionDetails(Map<String, String> decisionChargingData) {
        String decisionType = requiredChargingDecisionValue(decisionChargingData, DECISION_TYPE_FIELD);

        waitForDecisionTypeQuestion();
        checkRadioByName(decisionType);
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);

        waitForTextInLocator("h2", DECISION_CODE_QUESTION);
        checkRadioByName(requiredChargingDecisionValue(decisionChargingData, DECISION_CODE_FIELD));
        clickAndWaitForChargingResponse(
                "Save charge decision type and code",
                ACTION_SAVE_CHARGE_DECISION_ENDPOINT,
                this::clickSaveAndContinueButton
        );

        switch (normalizeText(decisionType)) {
            case "no further action" -> completeNoFurtherActionDecisionDetails(decisionChargingData);
            case "further evidence required" -> completeFurtherEvidenceRequiredDecisionDetails();
            case "charge" -> completeChargeDecisionDetails(decisionChargingData);
            case "non-conviction disposal" -> completeNonConvictionDisposalDecisionDetails(decisionChargingData);
            default -> throw new IllegalArgumentException("Unsupported decision type: " + decisionType);
        }
    }

    private void waitForDecisionTypeQuestion() {
        waitUntilBusyIndicatorsAreGone();
        waitForTextInLocator("h1", DECISION_TYPE_QUESTION);
    }

    private void selectDefendantForChargingDecision(String defendantName) {
        Locator defendantsNoDecisionList = page.locator(DEFENDANTS_SELECTION_LIST_SELECTOR);
        page.waitForCondition(() -> defendantsNoDecisionList.count() > 0);

        System.out.println("Selecting defendant for charging decision: " + defendantName);
        System.out.println("Visible defendants awaiting charging decision: " + visibleDefendantNames(defendantsNoDecisionList));

        Locator defendantItem = defendantsNoDecisionList
                .locator(DEFENDANT_NO_DECISION_ITEM_SELECTOR)
                .filter(new Locator.FilterOptions().setHasText(defendantName));
        assertThat(defendantItem).hasCount(1);
        defendantItem.scrollIntoViewIfNeeded();

        Locator checkboxSpan = defendantItem.locator("span").first();
        assertThat(checkboxSpan).isVisible();
        checkboxSpan.scrollIntoViewIfNeeded();
        checkboxSpan.click();
    }

    private List<String> visibleDefendantNames(Locator defendantsNoDecisionList) {
        Locator defendantItems = defendantsNoDecisionList.locator(DEFENDANT_NO_DECISION_ITEM_SELECTOR);
        List<String> defendantNames = new ArrayList<>();

        for (int index = 0; index < defendantItems.count(); index++) {
            Locator defendantItem = defendantItems.nth(index);
            if (defendantItem.isVisible()) {
                defendantNames.add(defendantItem.innerText().replaceAll("\\s+", " ").trim());
            }
        }

        return defendantNames;
    }

    private Map<String, String> normalizedChargingDecisionData(Map<String, String> decisionChargingData) {
        Map<String, String> normalizedDecisionChargingData = new LinkedHashMap<>(decisionChargingData);
        String decisionType = decisionChargingData.get(DECISION_TYPE_FIELD);

        if (decisionType != null && !decisionType.isBlank()) {
            normalizedDecisionChargingData.put(DECISION_TYPE_FIELD, canonicalDecisionType(decisionType));
        }

        return normalizedDecisionChargingData;
    }

    private String canonicalDecisionType(String decisionType) {
        return switch (normalizeText(decisionType)) {
            case "charge decision" -> DECISION_TYPE_CHARGE;
            case "further evidence is required" -> DECISION_TYPE_FURTHER_EVIDENCE_REQUIRED;
            default -> decisionType.trim();
        };
    }

    private void completeNoFurtherActionDecisionDetails(Map<String, String> decisionChargingData) {
        applyDecisionCode(NFA_REASON_QUESTION, decisionChargingData.get("reason"));
        applyDecisionCode(OUTCOME_REASON_QUESTION, decisionChargingData.get("out come of case"));
        assertChargingDecisionSelections(decisionChargingData);
    }

    private void completeFurtherEvidenceRequiredDecisionDetails() {
        // Further evidence required has no additional decision-specific page before offence category.
    }

    private void completeChargeDecisionDetails(Map<String, String> decisionChargingData) {
        String chargeCodeDecision = requiredChargingDecisionValue(decisionChargingData, CHARGE_CODE_DECISION_FIELD);

        checkAllChargeCodes();
        // TODO: Support selecting individual ChargeCodeBody rows by code when future scenarios need it.
        assertAllChargeCodeBodyCheckboxesChecked();
        selectChargeCodeDecision(chargeCodeDecision);
        clickAndWaitForChargingResponse(
                "Apply proposed charge decision",
                ACTION_APPLY_PROPOSED_CHARGE_ENDPOINT,
                this::clickApplyChargeDecision
        );
        assertChargeDescriptionStatus(chargeCodeDecision);
        clickAndWaitForChargingResponse(
                "Save charge decision",
                ACTION_SAVE_CHARGE_DECISION_ENDPOINT,
                this::clickContinue
        );
        assertChargeDecisionTypeSelections(decisionChargingData);
    }

    private void checkAllChargeCodes() {
        Locator chargeCode = page.locator(CHARGE_CODE_SELECTOR);
        assertThat(chargeCode).hasCount(1);
        assertThat(chargeCode).isVisible();

        Locator checkAllCheckbox = chargeCode.locator(CHECK_ALL_CHARGE_CODES_CHECKBOX_SELECTOR);
        assertThat(checkAllCheckbox).hasCount(1);
        assertThat(checkAllCheckbox).isVisible();
        checkAllCheckbox.scrollIntoViewIfNeeded();
        checkAllCheckbox.check(new Locator.CheckOptions().setForce(true));
        assertThat(checkAllCheckbox).isChecked();
    }

    private void assertAllChargeCodeBodyCheckboxesChecked() {
        Locator chargeCodeBodyCheckboxes = page.locator(CHARGE_CODE_BODY_CHECKBOX_SELECTOR);
        page.waitForCondition(() -> chargeCodeBodyCheckboxes.count() > 0);
        int checkboxCount = chargeCodeBodyCheckboxes.count();
        Assertions.assertThat(checkboxCount)
                .as("Charge code body checkbox count")
                .isGreaterThan(0);

        for (int index = 0; index < checkboxCount; index++) {
            assertThat(chargeCodeBodyCheckboxes.nth(index)).isChecked();
        }
    }

    private void selectChargeCodeDecision(String chargeCodeDecision) {
        Locator decisionDropdown = page.locator(CHARGE_DECISION_DROPDOWN_SELECTOR);
        assertThat(decisionDropdown).hasCount(1);
        assertThat(decisionDropdown).isVisible();
        decisionDropdown.scrollIntoViewIfNeeded();
        selectNativeDropdownOption(decisionDropdown, chargeCodeDecision, CHARGE_CODE_DECISION_FIELD);
    }

    private void clickApplyChargeDecision() {
        Locator applyButton = page.locator(CHARGE_APPLY_BUTTON_SELECTOR);
        assertThat(applyButton).hasCount(1);
        assertThat(applyButton).isVisible();
        applyButton.scrollIntoViewIfNeeded();
        applyButton.click();
    }

    private void assertChargeDescriptionStatus(String chargeCodeDecision) {
        String expectedStatus = expectedChargeDescriptionStatus(chargeCodeDecision);
        Locator descriptionStatusBodies = page.locator(CHARGE_DESCRIPTION_STATUS_BODY_SELECTOR);
        page.waitForCondition(() -> descriptionStatusBodies.count() > 0);
        int statusCount = descriptionStatusBodies.count();

        for (int index = 0; index < statusCount; index++) {
            assertThat(descriptionStatusBodies.nth(index)).containsText(expectedStatus);
        }
    }

    private String expectedChargeDescriptionStatus(String chargeCodeDecision) {
        return switch (normalizeText(chargeCodeDecision)) {
            case "accept" -> "Accepted";
            default -> throw new IllegalArgumentException("Unsupported charge code decision: " + chargeCodeDecision);
        };
    }

    private void clickContinue() {
        Locator continueButton = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(CONTINUE_BUTTON_TEXT)
        );
        assertThat(continueButton).isVisible();
        continueButton.scrollIntoViewIfNeeded();
        continueButton.click();
    }

    private void completeAgConsent(String agConsentLabelText) {
        Locator agConsentLabel = page.locator("label")
                .filter(new Locator.FilterOptions().setHasText(agConsentLabelText));
        assertThat(agConsentLabel).hasCount(1);
        assertThat(agConsentLabel).isVisible();
        agConsentLabel.scrollIntoViewIfNeeded();
        agConsentLabel.click();

        Locator textArea = page.locator(AG_CONSENT_TEXT_AREA_SELECTOR);
        assertThat(textArea).hasCount(1);
        assertThat(textArea).isVisible();
        textArea.scrollIntoViewIfNeeded();
        textArea.fill(FakerUtils.populateSentences());

        Locator saveAndContinueButton = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(SAVE_AND_CONTINUE_BUTTON_TEXT)
        );
        assertThat(saveAndContinueButton).isVisible();
        saveAndContinueButton.scrollIntoViewIfNeeded();
        clickAndWaitForChargingResponse(
                "Save AG consent decision",
                ACTION_SAVE_CONSENT_DECISIONS_ENDPOINT,
                saveAndContinueButton::click
        );
    }

    private String requiredChargingDecisionValue(Map<String, String> decisionChargingData, String fieldName) {
        String value = decisionChargingData.get(fieldName);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Charging decision data table is missing value for: " + fieldName);
        }
        return value.trim();
    }

    private void completeNonConvictionDisposalDecisionDetails(Map<String, String> decisionChargingData) {
        applyDecisionCode(OUTCOME_REASON_QUESTION, decisionChargingData.get("out come of case"));
        assertChargingDecisionSelections(decisionChargingData);
    }

    private void selectOffenceCategory(Map<String, String> decisionChargingData) {
        selectOffenceCategory(decisionChargingData, 0);
    }

    private void selectOffenceCategory(Map<String, String> decisionChargingData, int dropdownIndex) {
        String offenceCategory = requiredChargingDecisionValue(decisionChargingData, OFFENCE_CATEGORY_FIELD);
        waitUntilBusyIndicatorsAreGone();
        page.waitForTimeout(OFFENCE_CATEGORY_WAIT_TIMEOUT_MS);
        Locator offenceCategoryDropdown = offenceCategoryDropdown(offenceCategory, dropdownIndex);
        offenceCategoryDropdown.scrollIntoViewIfNeeded();
        selectNativeDropdownOption(offenceCategoryDropdown, offenceCategory, OFFENCE_CATEGORY_FIELD);
        assertNativeDropdownOptionSelected(offenceCategoryDropdown, offenceCategory, OFFENCE_CATEGORY_FIELD);
        commitNativeDropdownSelection(offenceCategoryDropdown);
        assertNativeDropdownOptionSelected(offenceCategoryDropdown, offenceCategory, OFFENCE_CATEGORY_FIELD);
    }

    private Locator offenceCategoryDropdown(String offenceCategory, int dropdownIndex) {
        Assertions.assertThat(dropdownIndex)
                .as("Offence category dropdown index")
                .isGreaterThanOrEqualTo(0);

        long deadline = System.currentTimeMillis() + UI_SETTLE_TIMEOUT_MILLIS;
        int lastMatchingDropdownCount = 0;

        while (System.currentTimeMillis() < deadline) {
            Locator comboBoxes = page.getByRole(AriaRole.COMBOBOX);
            int comboBoxCount = comboBoxes.count();
            int matchingDropdownIndex = 0;

            for (int index = 0; index < comboBoxCount; index++) {
                Locator comboBox = comboBoxes.nth(index);
                if (isVisibleComboBoxWithOption(comboBox, offenceCategory)) {
                    if (matchingDropdownIndex == dropdownIndex) {
                        return comboBox;
                    }
                    matchingDropdownIndex++;
                }
            }

            lastMatchingDropdownCount = matchingDropdownIndex;
            page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
        }

        throw new IllegalStateException("Offence category dropdown at index " + dropdownIndex
                + " was not found with option: " + offenceCategory
                + ". Matching dropdown count: " + lastMatchingDropdownCount);
    }

    private boolean isVisibleComboBoxWithOption(Locator comboBox, String optionText) {
        try {
            if (!comboBox.isVisible()) {
                return false;
            }

            Locator options = comboBox.locator("option");
            for (int optionIndex = 0; optionIndex < options.count(); optionIndex++) {
                if (optionMatches(options.nth(optionIndex).innerText(), optionText)) {
                    return true;
                }
            }

            return false;
        } catch (PlaywrightException exception) {
            if (isGenuineLocatorError(exception)) {
                throw exception;
            }
            return false;
        }
    }

    private void assertNativeDropdownOptionSelected(Locator dropdown, String expectedOptionText, String fieldName) {
        String selectedOptionText = String.valueOf(dropdown.evaluate("""
                select => {
                    const option = select.options[select.selectedIndex];
                    return option ? option.textContent : '';
                }
                """)).trim();

        Assertions.assertThat(optionMatches(selectedOptionText, expectedOptionText))
                .as(fieldName + " selected option. Expected: " + expectedOptionText
                + ", actual: " + selectedOptionText)
                .isTrue();
    }

    private void commitNativeDropdownSelection(Locator dropdown) {
        dropdown.evaluate("""
                select => {
                    select.dispatchEvent(new Event('input', { bubbles: true }));
                    select.dispatchEvent(new Event('change', { bubbles: true }));
                    select.blur();
                }
                """);
        page.waitForTimeout(OFFENCE_CATEGORY_WAIT_TIMEOUT_MS);
        waitUntilBusyIndicatorsAreGone();
    }

    private void continueChargingDecisionWithNextDefendant(String defendantName) {
        selectDefendantForChargingDecision(defendantName);
        clickSaveAndContinue();
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
        waitUntilSpinnersAreGone(SAVING_INDICATOR_TEXT, LOADING_INDICATOR_TEXT);
        waitForDecisionTypeQuestion();
    }

    private void continueWithNextDefendantOrFinish(
            List<String> defendantNames,
            int currentDefendantIndex,
            Map<String, String> decisionChargingData,
            boolean requiresConsent
    ) {
        if (isFinalDefendant(defendantNames, currentDefendantIndex)) {
            finishChargingDecisionAfterFinalDefendant();
            completeConsentIfRequired(decisionChargingData, requiresConsent);
            return;
        }

        if (hasOneRemainingDefendant(defendantNames, currentDefendantIndex)) {
            continueChargingDecisionWithOnlyRemainingDefendant();
            return;
        }

        if (hasNextDefendant(defendantNames, currentDefendantIndex)) {
            continueChargingDecisionWithNextDefendant(defendantNames.get(currentDefendantIndex + 1));
            return;
        }
    }

    private boolean hasNextDefendant(List<String> defendantNames, int currentDefendantIndex) {
        return currentDefendantIndex + 1 < defendantNames.size();
    }

    private boolean hasOneRemainingDefendant(List<String> defendantNames, int currentDefendantIndex) {
        return defendantNames.size() - currentDefendantIndex - 1 == 1;
    }

    private boolean isFinalDefendant(List<String> defendantNames, int currentDefendantIndex) {
        return currentDefendantIndex + 1 == defendantNames.size();
    }

    private void continueChargingDecisionWithOnlyRemainingDefendant() {
        clickSaveAndContinue();
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
        waitUntilSpinnersAreGone(SAVING_INDICATOR_TEXT, LOADING_INDICATOR_TEXT);
        waitForDecisionTypeQuestion();
    }

    private void finishChargingDecisionAfterFinalDefendant() {
        clickSaveAndContinue();
        waitUntilLoadingIndicatorIsGone(LOADING_INDICATOR_TEXT);
        waitForDomContentLoaded();
//        clickSaveAndContinue();
    }

    private void completeConsentIfRequired(Map<String, String> decisionChargingData, boolean requiresConsent) {
        if (!requiresConsent) {
            return;
        }

        String decisionType = requiredChargingDecisionValue(decisionChargingData, DECISION_TYPE_FIELD);

        if (DECISION_TYPE_CHARGE.equalsIgnoreCase(decisionType)) {
            completeAgConsent(requiredChargingDecisionValue(decisionChargingData, AG_CONSENT_FIELD));
        }
    }

    // Verifies the choices captured on the charging-decision summary card (shown on the offence
    // category page) match what the scenario selected. Each value is asserted within its own
    // summary row; reason/outcome are only shown for some decision types, so they are asserted
    // only when supplied.
    private void assertChargingDecisionSelections(Map<String, String> decisionChargingData) {
        Locator summaryCard = latestChargingSummaryCard();

        assertSummaryRowContains(summaryCard, SUMMARY_ROW_DECISION_SELECTOR, decisionChargingData.get("decision type"));
        assertSummaryRowContains(summaryCard, SUMMARY_ROW_DECISION_CODE_SELECTOR, decisionChargingData.get("decision code"));
        assertSummaryRowContains(summaryCard, SUMMARY_ROW_REASON_SELECTOR, decisionChargingData.get("reason"));
        assertSummaryRowContains(summaryCard, SUMMARY_ROW_FOCUS_SELECTOR, decisionChargingData.get("out come of case"));
    }

    private void assertChargeDecisionTypeSelections(Map<String, String> decisionChargingData) {
        Locator summaryCard = latestChargingSummaryCard();

        assertSummaryRowContains(summaryCard, SUMMARY_ROW_DECISION_SELECTOR, decisionChargingData.get("decision type"));
        assertSummaryRowContains(summaryCard, SUMMARY_ROW_DECISION_CODE_SELECTOR, decisionChargingData.get("decision code"));
    }

    private Locator latestChargingSummaryCard() {
        Locator summaryCards = page.locator(CHARGING_SUMMARY_CARD_SELECTOR);
        page.waitForCondition(() -> summaryCards.count() > 0);

        Locator latestSummaryCard = summaryCards.last();
        assertThat(latestSummaryCard).isVisible();
        return latestSummaryCard;
    }

    private void assertSummaryRowContains(Locator summaryCard, String rowSelector, String expectedValue) {
        if (expectedValue == null || expectedValue.isBlank()) {
            return;
        }
        // The row renders values in its own display casing (e.g. "No Further Action"), which can
        // differ from the scenario's casing ("No further action"). Match case-insensitively so
        // display casing doesn't fail a semantically-correct selection.
        assertThat(summaryCard.locator(rowSelector))
                .containsText(expectedValue, new LocatorAssertions.ContainsTextOptions().setIgnoreCase(true));
    }

    private void applyDecisionCode(String questionHeader, String radioValue) {
        waitForTextInLocator("h1", questionHeader);
        String radioLocator = String.format("input[type='radio'][value='%s']", radioValue);
        page.locator(radioLocator).scrollIntoViewIfNeeded();
        page.locator(radioLocator).click();
        clickSaveAndContinue();
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
    }

    private void assertChargingDecisionSummary() {
        assertThat(page.locator(CHARGING_DECISION_HEADER_SELECTOR)).containsText(DECISION_HEADER);
        assertThat(page.locator(CHARGING_DECISION_SUSPECT_COUNT_SELECTOR))
                .containsText(SUSPECT_AWAITING_CHARGING_DECISION_TEXT);
        Locator defendantName = page.locator(ONE_DEFENDANT_SELECTOR);
        assertThat(defendantName).isVisible();
        Assertions.assertThat(defendantName.innerText().trim())
                .as("Defendant name")
                .isNotBlank();
    }

    private Locator addRelationshipLink(int index) {
        Locator addRelationshipLink = page.locator(ADD_RELATIONSHIP_LINK_SELECTOR).nth(index);
        assertThat(addRelationshipLink).isVisible();
        addRelationshipLink.scrollIntoViewIfNeeded();
        return addRelationshipLink;
    }

    private Locator relationshipQuestion() {
        return page.getByText(RELATIONSHIP_QUESTION_PREFIX).first();
    }

    private void assertSectionCompleted(String sectionName) {
        assertProgressWizardSubItemHasIcon(sectionName, COMPLETED_SECTION_ICON_SELECTOR);
    }

    private void assertNextSectionSelectedIfKnown(String completedSectionName) {
        String nextSectionName = NEXT_SECTION_BY_SECTION.get(completedSectionName);
        if (nextSectionName == null) {
            return;
        }

        assertProgressWizardSubItemHasIcon(nextSectionName, SELECTED_SECTION_ICON_SELECTOR);
    }

    private void assertProgressWizardSubItemHasIcon(String sectionName, String iconSelector) {
        page.waitForCondition(() -> Boolean.TRUE.equals(page.evaluate("""
                ([sectionName, iconSelector]) => {
                    const normalize = value => value.replace(/\\s+/g, ' ').trim().toLowerCase();
                    const expectedSectionName = normalize(sectionName);
                    const wizard = document.querySelector("[id$='PreChargeAnalysis4']");
                    if (!wizard) {
                        return false;
                    }

                    const section = Array.from(
                        wizard.querySelectorAll("[data-block='CPS_Component.ProgressWizardSubItem']")
                    ).find(item => normalize(item.innerText).includes(expectedSectionName));

                    if (!section) {
                        return false;
                    }

                    section.scrollIntoView({ block: 'center', inline: 'nearest' });
                    return Boolean(section.querySelector(iconSelector));
                }
                """, List.of(sectionName, iconSelector))));
    }
}
