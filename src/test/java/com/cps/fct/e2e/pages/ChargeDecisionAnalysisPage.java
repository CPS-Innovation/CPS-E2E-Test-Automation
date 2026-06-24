package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
public class ChargeDecisionAnalysisPage extends BasePage {

    private static final String CASE_HEADLINE_LABEL = "Case headline";
    private static final String EVIDENTIAL_ANALYSIS_LABEL = "Evidential analysis";
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
    private static final String GLOBAL_MONITORING_CODES_SELECTOR = "[id$='GlobalMonitoringFlags']";
    private static final String LOCAL_MONITORING_CODES_LABEL = "Local monitoring codes";
    private static final String MONITORING_CODE_LABEL_SELECTOR = ".govuk-checkboxes__label";
    private static final String CASE_HEADLINE_CONTAINER_SELECTOR = "[id$='Step2_A_CaseHeadline']";
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
    private static final String OFFENCE_CATEGORY_LABEL = "PCD principal offence category";
    private static final String CHARGING_DECISION_HEADER_SELECTOR = "span.govuk-heading-l";
    private static final String CHARGING_DECISION_SUSPECT_COUNT_SELECTOR = "h2.govuk-heading-m";
    private static final String ONE_DEFENDANT_SELECTOR = "[id$='OneDefendant']";
    private static final String PREVIEW_SCROLL_SELECTOR = ".previewScroll";
    private static final String PREVIEW_ANALYSIS_STEPS_SELECTOR =
            PREVIEW_SCROLL_SELECTOR + " [id$='AnalysisSteps']";
    private static final String PREVIEW_GLOBAL_MONITORING_CODES_SELECTOR =
            "#b10-b12-b3-GlobalMonitoringCodesSection";
    private static final String PREVIEW_SUSPECT_RELATIONSHIP_SELECTOR =
            "[data-block='MonitoringBlocks.DefendantRelationships']";
    private static final String PREVIEW_LOCAL_MONITORING_CODES_SELECTOR = "#b10-b12-b3-b6-Column2";
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
            Map.entry(ADVOCATE_AND_OPERATIONAL_DELIVERY_INSTRUCTIONS_LABEL, MONITORING_CODES_LABEL)
    );
    private final Map<String, String> enteredAnalysisTextBySection = new LinkedHashMap<>();
    private final List<String> selectedGlobalMonitoringCodes = new ArrayList<>();
    private final List<String> selectedLocalMonitoringCodes = new ArrayList<>();
    private String selectedSuspectVictimRelationship;

    public ChargeDecisionAnalysisPage(PlaywrightContext context) {
        super(context);
    }

    public ChargeDecisionAnalysisPage assertCaseHeadlineSection(String typeOfReview) {
        assertThat(page.locator(CASE_HEADLINE_CONTAINER_SELECTOR)).containsText(typeOfReview);
        clickSectionIfNotVisible(CASE_HEADLINE_LABEL);
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

    public void enterSectionText(String headerLabel, String randomWords) {
        enteredAnalysisTextBySection.put(headerLabel, randomWords);
        assertSection(headerLabel).enterTextInRichEditor(randomWords).clickSaveAndContinueAndAssertSectionProgress(headerLabel);
    }

    public ChargeDecisionAnalysisPage enterSectionTextOnly(String headerLabel, String randomWords) {
        enteredAnalysisTextBySection.put(headerLabel, randomWords);
        assertSection(headerLabel).enterTextInRichEditor(randomWords);
        return this;
    }

    public void humanRightsOptionHasNotAtThisTime() {
        assertThat(page.locator(FIELDSET_LEGEND_SELECTOR)).containsText(HUMAN_RIGHTS_QUESTION_TEXT);
        checkCheckbox(RADIO_ROLE_NOT_AT_THIS_TIME);
        enteredAnalysisTextBySection.put(HUMAN_RIGHTS_LABEL, HUMAN_RIGHTS_PREVIEW_TEXT);
        clickSaveAndContinueAndAssertSectionProgress(HUMAN_RIGHTS_LABEL);
    }

    public void selectMonitoringCodesAndSaveContinue(String monitoringCodeType, List<String> monitoringCodes) {
        assertThat(page.getByText(MONITORING_CODES_HEADER, new Page.GetByTextOptions().setExact(true))).isVisible();
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
        assertThat(page.getByText(MONITORING_CODES_HEADER, new Page.GetByTextOptions().setExact(true))).isVisible();
        selectMonitoringCodes("Global", globalMonitoringCodes);
        selectMonitoringCodes("Local", localMonitoringCodes);
        clickSaveAndContinueAndAssertSectionProgress(MONITORING_CODES_LABEL);
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

    private void scrollToMonitoringCodeSection(String monitoringCodeType) {
        switch (normalizedMonitoringCodeType(monitoringCodeType)) {
            case "global":
                Locator globalMonitoringCodes = page.locator(GLOBAL_MONITORING_CODES_SELECTOR);
                assertThat(globalMonitoringCodes).containsText(GLOBAL_MONITORING_CODES_LABEL);
                globalMonitoringCodes.scrollIntoViewIfNeeded();
                break;
            case "local":
                Locator localMonitoringCodes = page.getByText(
                        LOCAL_MONITORING_CODES_LABEL,
                        new Page.GetByTextOptions().setExact(true)
                ).first();
                assertThat(localMonitoringCodes).isVisible();
                localMonitoringCodes.scrollIntoViewIfNeeded();
                break;
            default:
                throw new IllegalArgumentException("Unsupported monitoring code type: " + monitoringCodeType
                        + ". Use Global or Local.");
        }
    }

    private void checkMonitoringCode(String monitoringCodeType, String monitoringCode) {
        String normalizedMonitoringCode = monitoringCode.trim();
        String labelSelector = monitoringCodeLabelSelector(monitoringCodeType);

        for (int attempt = 1; attempt <= 3; attempt++) {
            boolean checked = Boolean.TRUE.equals(page.evaluate("""
                    ([labelSelector, monitoringCode]) => {
                        const normalize = value => value.replace(/\\s+/g, ' ').trim().toLowerCase();
                        const expectedText = normalize(monitoringCode);
                        const isVisible = element => {
                            const style = window.getComputedStyle(element);
                            const rect = element.getBoundingClientRect();
                            return style.display !== 'none'
                                && style.visibility !== 'hidden'
                                && rect.width > 0
                                && rect.height > 0;
                        };
                        const labels = Array.from(document.querySelectorAll(labelSelector));
                        const visibleLabels = labels.filter(isVisible);
                        const exactMatch = item => normalize(item.innerText) === expectedText;
                        const containsMatch = item => normalize(item.innerText).includes(expectedText);
                        const label = visibleLabels.find(exactMatch)
                            || visibleLabels.find(containsMatch)
                            || labels.find(exactMatch)
                            || labels.find(containsMatch);

                        if (!label) {
                            return false;
                        }

                        label.scrollIntoView({ block: 'center', inline: 'nearest' });

                        const checkboxItem = label.closest('.govuk-checkboxes__item');
                        const checkbox = checkboxItem?.querySelector('input[type="checkbox"]');
                        if (!checkbox) {
                            return false;
                        }

                        checkbox.scrollIntoView({ block: 'center', inline: 'nearest' });
                        if (!checkbox.checked) {
                            checkbox.click();
                        }

                        return checkbox.checked;
                    }
                    """, List.of(labelSelector, normalizedMonitoringCode)));

            if (checked) {
                return;
            }

            page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
        }

        throw new IllegalStateException("Monitoring code checkbox was not found or checked: "
                + monitoringCodeType + " - " + normalizedMonitoringCode);
    }

    private String monitoringCodeLabelSelector(String monitoringCodeType) {
        return switch (normalizedMonitoringCodeType(monitoringCodeType)) {
            case "global", "local" -> MONITORING_CODE_LABEL_SELECTOR;
            default -> throw new IllegalArgumentException("Unsupported monitoring code type: " + monitoringCodeType
                    + ". Use Global or Local.");
        };
    }

    private String normalizedMonitoringCodeType(String monitoringCodeType) {
        return monitoringCodeType.trim().toLowerCase();
    }

    private void selectMonitoringCodes(String monitoringCodeType, List<String> monitoringCodes) {
        rememberSelectedMonitoringCodes(monitoringCodeType, monitoringCodes);

        if (monitoringCodes == null || monitoringCodes.isEmpty()) {
            return;
        }

        scrollToMonitoringCodeSection(monitoringCodeType);
        for (String monitoringCode : monitoringCodes) {
            checkMonitoringCode(monitoringCodeType, monitoringCode);
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
                page.locator(PREVIEW_GLOBAL_MONITORING_CODES_SELECTOR),
                selectedGlobalMonitoringCodes
        );
    }

    private void assertPreviewLocalMonitoringCodes() {
        assertPreviewMonitoringCodes(
                page.locator(PREVIEW_LOCAL_MONITORING_CODES_SELECTOR),
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
        Locator suspectRelationshipSection = page.locator(PREVIEW_SUSPECT_RELATIONSHIP_SELECTOR);
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

    public void checkDGComplaintAsYes() {
        assertThat(page.locator("h1")).containsText(DG_COMPLAINT_HEADER);
        assertThat(page.getByText(DG_COMPLAINT_SUBHEADER, new Page.GetByTextOptions().setExact(true))).isVisible();
        checkCheckbox(RADIO_ROLE_YES);
        clickSaveAndContinue();
    }

    public void applyDecisionChargeForNFA(Map<String, String> decisionChargingData) {
        waitForTextInLocator("h1", DECISION_HEADER);
        clickSaveAndContinue();
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);

        waitForTextInLocator("h1", DECISION_TYPE_QUESTION);
        checkRadioByName(decisionChargingData.get("decision type"));
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);

        waitForTextInLocator("h2", DECISION_CODE_QUESTION);
        checkRadioByName(decisionChargingData.get("decision code"));
        clickSaveAndContinue();
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);

        applyDecisionCode(NFA_REASON_QUESTION, decisionChargingData.get("reason"));
        applyDecisionCode(OUTCOME_REASON_QUESTION, decisionChargingData.get("out come of case"));

        assertThat(page.getByText(OFFENCE_CATEGORY_LABEL)).isVisible();
        selectComboBoxByVisibleText(decisionChargingData.get("offence category"));
        clickSaveAndContinue();
        waitUntilLoadingIndicatorIsGone(LOADING_INDICATOR_TEXT);
        waitForLoginPageToLoadCompletely();
        clickSaveAndContinue();
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
