package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private static final String PREVIEW_HEADER = "Preview";
    private static final String CONTINUE_BUTTON_TEXT = "Continue";
    private static final String ADD_RELATIONSHIP_LINK_SELECTOR = "[id$='Addrelationship']";
    private static final String RELATIONSHIP_QUESTION_TEXT =
            "What was the relationship between suspect and victims at the time of the offence?";
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
    private static final String DECISION_TYPE_NO_FURTHER_ACTION = "No further action";
    private static final String DECISION_TYPE_FURTHER_EVIDENCE_REQUIRED = "Further evidence required";
    private static final String DECISION_TYPE_CHARGE = "Charge";
    private static final String DECISION_TYPE_NON_CONVICTION_DISPOSAL = "Non-conviction disposal";
    private static final String PRINCIPAL_OFFENCE_CATEGORY_LABEL = "Principal offence category";
    private static final String EARLY_ADVICE_POC_DROPDOWN_SELECTOR = "#b16-b4-POC_Dropdown";
    private static final String CASE_ACTION_PLAN_LABEL = "Case action plan";
    private static final String OFFENCE_CATEGORY_LABEL = "PCD principal offence category";
    // Charging-decision summary card on the offence-category page. Match on the stable OutSystems
    // widget-name suffixes (no numeric prefixes) so the selectors hold across case types.
    private static final String CHARGING_SUMMARY_CARD_SELECTOR = "[id$='ChargingSummaryGDS']";
    private static final String SUMMARY_ROW_DECISION_SELECTOR = "[id$='List_row_Decision']";
    private static final String SUMMARY_ROW_DECISION_CODE_SELECTOR = "[id$='List_row_DecisionCode']";
    private static final String SUMMARY_ROW_FOCUS_SELECTOR = "[id$='List_row_Focus']";
    private static final String CHARGING_DECISION_HEADER_SELECTOR = "span.govuk-heading-l";
    private static final String CHARGING_DECISION_SUSPECT_COUNT_SELECTOR = "h2.govuk-heading-m";
    private static final String ONE_DEFENDANT_SELECTOR = "[id$='OneDefendant']";
    private static final String PREVIEW_SCROLL_SELECTOR = ".previewScroll";
    private static final String PREVIEW_ANALYSIS_STEPS_SELECTOR =
            PREVIEW_SCROLL_SELECTOR + " [id$='AnalysisSteps']";
    private static final String HUMAN_RIGHTS_PREVIEW_TEXT =
            "Human rights factors are not an issue in this case at this time";
    private static final String NONE_SELECTED_TEXT = "None selected";
    private static final String SUSPECT_AWAITING_CHARGING_DECISION_TEXT = "There is 1 suspect awaiting a charging decision.";
    private static final int DEFAULT_WAIT_TIMEOUT_MS = 200;
    private static final int UI_SETTLE_TIMEOUT_MILLIS = 30_000;
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

    private void clickSaveAndContinueAndAssertSectionCompleted(String completedSectionName) {
        clickSaveAndContinue();
        assertSectionCompleted(completedSectionName);
    }

    public ChargeDecisionAnalysisPage enterTextInRichEditor(String randomWords) {
        fillRichTextEditor("role=textbox[name*='Editor editing area']", randomWords);
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
                .clickSaveAndContinueAndAssertSectionProgress(sectionName);
    }

    public void enterCaseHeadLine(String typeOfReview, String randomWords) {
        enteredAnalysisTextBySection.put(CASE_HEADLINE_LABEL, randomWords);
        assertCaseHeadlineSection(typeOfReview)
                .enterTextInRichEditor(randomWords)
                .clickSaveAndContinueAndAssertSectionProgress(CASE_HEADLINE_LABEL);
    }

    public void enterThresholdCaseHeadLine(String typeOfReview, String randomWords) {
        enteredAnalysisTextBySection.put(CASE_HEADLINE_LABEL, randomWords);
        assertCaseHeadlineSection(typeOfReview)
                .enterTextInRichEditor(randomWords)
                .clickSaveAndContinueAndAssertSectionCompleted(CASE_HEADLINE_LABEL);
    }

    public void enterSectionText(String headerLabel, String randomWords) {
        enteredAnalysisTextBySection.put(headerLabel, randomWords);
        assertSection(headerLabel).enterTextInRichEditor(randomWords).clickSaveAndContinueAndAssertSectionProgress(headerLabel);
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
                .clickSaveAndContinueAndAssertSectionCompleted(headerLabel);
    }

    public void skipThresholdAdditionalAnalysis(String headerLabel) {
        assertSection(headerLabel)
                .clickSaveAndContinueAndAssertSectionCompleted(headerLabel);
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
        assertPageHeaderContains(PRINCIPAL_OFFENCE_CATEGORY_LABEL);
        selectEarlyAdvicePocDropdownOption(offenceCategory);
        clickSaveAndContinue();
    }

    public void selectEarlyAdvicePrincipalOffenceCategoryAndContinue(String offenceCategory) {
        assertPageHeaderContains(PRINCIPAL_OFFENCE_CATEGORY_LABEL);
        selectEarlyAdvicePocDropdownOption(offenceCategory);
        clickSaveAndContinue();
        assertPageHeaderContains(CASE_ACTION_PLAN_LABEL);
    }

    private void selectEarlyAdvicePocDropdownOption(String optionText) {
        Locator dropdown = page.locator(EARLY_ADVICE_POC_DROPDOWN_SELECTOR);
        assertThat(dropdown).isVisible();
        dropdown.scrollIntoViewIfNeeded();

        if (isNativeSelect(dropdown)) {
            selectNativeDropdownOption(dropdown, optionText, PRINCIPAL_OFFENCE_CATEGORY_LABEL);
            return;
        }

        selectTypeAheadDropdownOption(dropdown, optionText, PRINCIPAL_OFFENCE_CATEGORY_LABEL);
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

    private void selectTypeAheadDropdownOption(Locator dropdown, String optionText, String fieldName) {
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

        throw new IllegalArgumentException(fieldName + " option was not found: " + optionText);
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

        label.scrollIntoViewIfNeeded();
        label.click();
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

    private Locator checkboxForMonitoringCodeLabel(Locator label) {
        String checkboxId = label.getAttribute("for");
        if (checkboxId != null && !checkboxId.isBlank()) {
            Locator checkbox = page.locator("input[type='checkbox'][id=" + cssString(checkboxId) + "]");
            assertThat(checkbox).isVisible();
            return checkbox;
        }

        Locator checkbox = label.locator(
                "xpath=ancestor::*[contains(concat(' ', normalize-space(@class), ' '), "
                        + "' govuk-checkboxes__item ')][1]//input[@type='checkbox']"
        ).first();
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
        Locator addRelationshipLink = addRelationshipLink();
        addRelationshipLink.click();
        assertThat(relationshipQuestion()).isVisible();

        Locator relationshipCheckbox = page.getByRole(
                AriaRole.CHECKBOX,
                new Page.GetByRoleOptions().setName(relationshipType).setExact(true)
        );
        assertThat(relationshipCheckbox).isVisible();
        relationshipCheckbox.check();
        selectedSuspectVictimRelationship = relationshipType;

        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(CONTINUE_BUTTON_TEXT).setExact(true)
        ).click();
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
        assertThat(page.getByText(DG_COMPLAINT_SUBHEADER, new Page.GetByTextOptions().setExact(true))).isVisible();
        checkCheckbox(RADIO_ROLE_YES);
        clickSaveAndContinue();
    }

    public void applyChargingDecision(Map<String, String> decisionChargingData) {
        String decisionType = decisionChargingData.get("decision type");

        waitForTextInLocator("h1", DECISION_HEADER);
        clickSaveAndContinue();
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);

        waitForTextInLocator("h1", DECISION_TYPE_QUESTION);
        checkRadioByName(decisionType);
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);

        waitForTextInLocator("h2", DECISION_CODE_QUESTION);
        checkRadioByName(decisionChargingData.get("decision code"));
        clickSaveAndContinue();
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);

        if (DECISION_TYPE_NO_FURTHER_ACTION.equalsIgnoreCase(decisionType)) {
            applyNoFurtherActionDecision(decisionChargingData);
        } else if (DECISION_TYPE_FURTHER_EVIDENCE_REQUIRED.equalsIgnoreCase(decisionType)) {
            applyFurtherEvidenceRequiredDecision(decisionChargingData);
        } else if (DECISION_TYPE_CHARGE.equalsIgnoreCase(decisionType)) {
            applyChargeDecision(decisionChargingData);
        } else if (DECISION_TYPE_NON_CONVICTION_DISPOSAL.equalsIgnoreCase(decisionType)) {
            applyNonConvictionDisposalDecision(decisionChargingData);
        } else {
            throw new IllegalArgumentException("Unsupported decision type: " + decisionType);
        }
    }

    private void applyNoFurtherActionDecision(Map<String, String> decisionChargingData) {
        applyDecisionCode(NFA_REASON_QUESTION, decisionChargingData.get("reason"));
        applyDecisionCode(OUTCOME_REASON_QUESTION, decisionChargingData.get("out come of case"));
        assertChargingDecisionSelections(decisionChargingData);
        selectOffenceCategoryAndFinish(decisionChargingData.get("offence category"));
    }

    private void applyFurtherEvidenceRequiredDecision(Map<String, String> decisionChargingData) {
        selectOffenceCategoryAndFinish(decisionChargingData.get("offence category"));
    }

    private void applyChargeDecision(Map<String, String> decisionChargingData) {
        // TODO: implement Charge decision flow
    }

    private void applyNonConvictionDisposalDecision(Map<String, String> decisionChargingData) {
        applyDecisionCode(OUTCOME_REASON_QUESTION, decisionChargingData.get("out come of case"));
        assertChargingDecisionSelections(decisionChargingData);
        selectOffenceCategoryAndFinish(decisionChargingData.get("offence category"));
    }

    private void selectOffenceCategoryAndFinish(String offenceCategory) {
        assertThat(page.getByText(OFFENCE_CATEGORY_LABEL)).isVisible();
        selectComboBoxByVisibleText(offenceCategory);
        clickSaveAndContinue();
        waitUntilLoadingIndicatorIsGone(LOADING_INDICATOR_TEXT);
        waitForLoginPageToLoadCompletely();
        clickSaveAndContinue();
    }

    // Verifies the choices captured on the charging-decision summary card (shown on the offence
    // category page) match what the scenario selected. Each value is asserted within its own
    // summary row; reason/outcome are only shown for some decision types, so they are asserted
    // only when supplied.
    private void assertChargingDecisionSelections(Map<String, String> decisionChargingData) {
        assertThat(page.locator(CHARGING_SUMMARY_CARD_SELECTOR)).isVisible();

        assertSummaryRowContains(SUMMARY_ROW_DECISION_SELECTOR, decisionChargingData.get("decision type"));
        assertSummaryRowContains(SUMMARY_ROW_DECISION_CODE_SELECTOR, decisionChargingData.get("decision code"));
        assertSummaryRowContains(SUMMARY_ROW_DECISION_CODE_SELECTOR, decisionChargingData.get("reason"));
        assertSummaryRowContains(SUMMARY_ROW_FOCUS_SELECTOR, decisionChargingData.get("out come of case"));
    }

    private void assertSummaryRowContains(String rowSelector, String expectedValue) {
        if (expectedValue == null || expectedValue.isBlank()) {
            return;
        }
        assertThat(page.locator(rowSelector)).containsText(expectedValue);
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

    private Locator addRelationshipLink() {
        Locator addRelationshipLink = page.locator(ADD_RELATIONSHIP_LINK_SELECTOR).first();
        assertThat(addRelationshipLink).isVisible();
        addRelationshipLink.scrollIntoViewIfNeeded();
        return addRelationshipLink;
    }

    private Locator relationshipQuestion() {
        return page.getByText(RELATIONSHIP_QUESTION_TEXT, new Page.GetByTextOptions().setExact(true));
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
