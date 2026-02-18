package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
public class ChargeDecisionAnalysisPage extends BasePage {

    private static final String CASE_HEADLINE_LABEL = "Case headline";
    private static final String PRE_CHARGE_DECISION_LABEL = "Pre-charge decision";
    private static final String NGAP_QUESTION_TEXT = "Has the file been submitted as NGAP?";
    private static final String HUMAN_RIGHTS_QUESTION_TEXT = "Are there human rights factors that may affect the case?";
    private static final String DG_COMPLAINT_HEADER = "DG file quality assessment";
    private static final String DG_COMPLAINT_SUBHEADER = "Is the submitted file compliant in accordance with the Directors Guidance (DG6)";
    private static final String SAVE_AND_CONTINUE_BUTTON = "button[title='Save and Continue']";
    private static final String RADIO_ROLE_YES = "role=radio[name='Yes']";
    private static final String RADIO_ROLE_NO = "role=radio[name='No']";
    private static final String RADIO_ROLE_NOT_AT_THIS_TIME = "role=radio[name='Not at this time']";
    private static final String DECISION_HEADER = "Charging decision";
    private static final String DECISION_TYPE_QUESTION = "What is your decision for";
    private static final String DECISION_CODE_QUESTION = "Select a decision code";
    private static final String NFA_REASON_QUESTION = "What is your reason for no further action?";
    private static final String OUTCOME_REASON_QUESTION = "Was undermining, unused material a key factor in the outcome of the case?";
    private static final String OFFENCE_CATEGORY_LABEL = "PCD principal offence category";
    private static final int DEFAULT_WAIT_TIMEOUT_MS = 200;
    private static final int SAVE_BUTTON_WAIT_TIMEOUT_MS = 300;
    private static final String LOADING_INDICATOR_TEXT = "Loading...";

    public ChargeDecisionAnalysisPage(PlaywrightContext context) {
        super(context);
    }

    public ChargeDecisionAnalysisPage assertCaseHeadlineSection(String typeOfReview) {
        assertThat(page.locator("#b9-Step2_A_CaseHeadline")).containsText(typeOfReview);
        clickSectionIfNotVisible(CASE_HEADLINE_LABEL);
        return this;
    }

    public ChargeDecisionAnalysisPage assertSection(String headerText) {
        clickSectionIfNotVisible(headerText);
        return this;
    }

    private void clickSectionIfNotVisible(String sectionName) {
        Locator link = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(sectionName).setExact(true));
        if (!page.getByLabel(PRE_CHARGE_DECISION_LABEL).getByText(sectionName).isVisible()) {
            link.hover();
            link.click();
        }
    }

    public void clickSaveAndContinue() {
        Locator saveButton = page.locator(SAVE_AND_CONTINUE_BUTTON);
        page.waitForTimeout(SAVE_BUTTON_WAIT_TIMEOUT_MS);
        saveButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        saveButton.scrollIntoViewIfNeeded();
        saveButton.click(new Locator.ClickOptions().setForce(true));
    }

    public ChargeDecisionAnalysisPage enterTextInRichEditor(String randomWords) {
        fillRichTextEditor("role=textbox[name*='Editor editing area']", randomWords);
        return this;
    }

    public ChargeDecisionAnalysisPage checkNGAPOptionAsYes() {
        assertThat(page.locator("h6")).containsText(NGAP_QUESTION_TEXT);
        checkCheckbox(RADIO_ROLE_YES);
        return this;
    }

    public ChargeDecisionAnalysisPage checkNGAPOptionAsNo() {
        assertThat(page.locator("h6")).containsText(NGAP_QUESTION_TEXT);
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
                .clickSaveAndContinue();
    }

    public void enterCaseHeadLine(String typeOfReview, String randomWords) {
        assertCaseHeadlineSection(typeOfReview)
                .enterTextInRichEditor(randomWords)
                .clickSaveAndContinue();
    }

    public void enterSectionText(String headerLabel, String randomWords) {
        assertSection(headerLabel).enterTextInRichEditor(randomWords).clickSaveAndContinue();
    }

    public ChargeDecisionAnalysisPage enterSectionTextOnly(String headerLabel, String randomWords) {
        assertSection(headerLabel).enterTextInRichEditor(randomWords);
        return this;
    }

    public void humanRightsOptionHasNotAtThisTime() {
        Assertions.assertThat(hasText("h5", HUMAN_RIGHTS_QUESTION_TEXT)).isTrue();
        checkCheckbox(RADIO_ROLE_NOT_AT_THIS_TIME);
        clickSaveAndContinue();
    }

    public void selectGlobalMonitoringCodesAndSaveContinue(List<String> monitoringCodes) {
        assertElementTextPresent("text=Codes");
        assertElementTextPresent("text=monitoring codes");
        for (String monitoringCode : monitoringCodes) {
            page.locator("role=checkbox[name='" + monitoringCode + "']").check();
        }
        clickSaveAndContinue();
    }

    public void checkPreviewChargeAnalysis() {
        clickSaveAndContinue(); // TO-DO: proper asserts for all sections texts
    }

    public void checkDGComplaintAsYes() {
        Assertions.assertThat(hasText("h1", DG_COMPLAINT_HEADER)).isTrue();
        Assertions.assertThat(hasText("h6", DG_COMPLAINT_SUBHEADER)).isTrue();
        checkCheckbox(RADIO_ROLE_YES);
        clickSaveAndContinue();
        System.out.println("hello world");
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
    }

    private void applyDecisionCode(String questionHeader, String radioValue) {
        waitForTextInLocator("h1", questionHeader);
        String radioLocator = String.format("input[type='radio'][value='%s']", radioValue);
        page.locator(radioLocator).scrollIntoViewIfNeeded();
        page.locator(radioLocator).click();
        clickSaveAndContinue();
        page.waitForTimeout(DEFAULT_WAIT_TIMEOUT_MS);
    }
}