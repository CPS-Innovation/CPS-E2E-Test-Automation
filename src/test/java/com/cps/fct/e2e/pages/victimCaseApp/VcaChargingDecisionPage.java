package com.cps.fct.e2e.pages.victimCaseApp;

import com.cps.fct.e2e.pages.BasePage;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.options.AriaRole;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

import static com.cps.fct.e2e.utils.common.DateTimeUtils.utcHourNow;
import static com.cps.fct.e2e.utils.common.DateTimeUtils.utcMinuteNow;
import static com.cps.fct.e2e.utils.common.FakerUtils.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VcaChargingDecisionPage extends BasePage {

    private final ScenarioContext scenarioContext;

    public VcaChargingDecisionPage(PlaywrightContext context, ScenarioContext scenarioContext) {
        super(context);
        this.scenarioContext = scenarioContext;
    }

    private static final Logger logger =
            LoggerFactory.getLogger(VcaChargingDecisionPage.class);

    public String getJourneyType(String journeyType) {
        switch (journeyType.trim().toLowerCase()) {
            case "decision to charge":
                journeyType = " a decision to charge";
                break;
            case "no further action decision":
                journeyType = " no further action decision";
                break;
            default:
                System.out.println("Specified journey type does not exist");
        }
        return journeyType;
    }

    public void selectChargingDecisionType(String personFirstLastName, String journeyType) {

        waitUntilSpinnersAreGone("Loading, please wait");
        waitForElement(page, AriaRole.TAB, "Charging decision");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Charging decision");
        waitForText("Communications");
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText(personFirstLastName + " has not yet been informed by the CPS about the charging decision.");
        waitForButtonToAppear("Log a communication");
        clickButton("Log a communication");
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText("What do you need to inform ");
        if ("Decision to charge".equalsIgnoreCase(journeyType)) {
            getRadioButtonById("Decision_DtC").click();
        } else if ("No further action decision".equalsIgnoreCase(journeyType)) {
            getRadioButtonById("Decision_NFA").click();
        } else {
            System.out.println("Specified Charging Decision journey type does not exist");
        }
        waitForButtonToAppear("Create task");
        clickButton("Create task");
        waitForText("How did you contact " + personFirstLastName + "?");
    }

    public void selectChargingDecisionMethod(String personFirstLastName, String contactMethod) {
        waitForText("How did you contact " + personFirstLastName + "?");

        switch (contactMethod.trim().toLowerCase()) {
            case "telephone call":
                getRadioButtonById("NFACommunicationType_Telephone").click();
                break;
            case "email":
                getRadioButtonById("NFACommunicationType_Email").click();
                break;
            case "post":
                getRadioButtonById("NFACommunicationType_Post").click();
                break;
            case "victim not contacted":
                getRadioButtonById("NFACommunicationType_NotContacted").click();
                break;
            default:
                System.out.println("Specified charging decision contact method does not exist");
        }
        waitForButtonToAppear("Continue");
        clickButton("Continue");
    }

    public void logFirstTelephoneCall(String personFirstLastName, String callDirection, String informVictim, String notes) {

        waitForText("Telephone call 1");
        enterTextByLocator(page.locator("#b7-Input_Date"), todayMinusNoOfDays(4));
        enterTextByLocator(page.locator("#Input_Call_Hour"), utcHourNow());
        enterTextByLocator(page.locator("#Input_Call_Minutes"), utcMinuteNow());

        if ("Outbound".equalsIgnoreCase(callDirection)) {
            getRadioButtonById("RadioButton_Outbound").click();
        } else if ("Inbound".equalsIgnoreCase(callDirection)) {
            getRadioButtonById("RadioButton_Inbound").click();
        } else {
            System.out.println("Specified telephone call direction does not exist");
        }

        if ("Yes".equalsIgnoreCase(informVictim)) {
            getRadioButtonById("RadioButton_Yes").click();
        } else if ("No".equalsIgnoreCase(informVictim)) {
            getRadioButtonById("RadioButton_No").click();
        } else {
            System.out.println("Specified answer for inform victim does not exist");
        }

        enterTextById("TextArea_Notes", notes);
        clickButton("Save & continue");

        if ("Yes".equalsIgnoreCase(informVictim)) {
            waitForText("How does " + personFirstLastName + " want to receive the letter?");
            clickBackLink();
        } else if ("No".equalsIgnoreCase(informVictim)) {
            waitForText("Was a text message sent to " + personFirstLastName + "?");
            clickBackLink();
        } else {
            System.out.println("Specified answer for inform victim does not exist");
        }

        assertThat(page.getByText(
                Pattern.compile("Telephone call 1 logged.")))
                .isVisible();

    }

    public void confirmFirstTelephoneCall(String journeyType){
        journeyType = getJourneyType(journeyType);
        assertThat(readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Inform of " + journeyType,
                "div.govuk-summary-list__row", "Telephone call 1", "dd.govuk-summary-list__value"))
                .isVisible();
    }

    public void verifyFirstTelephoneCallSuccessful(String journeyType, String contactMethod,String callDirection, String informVictim, String notes){

        journeyType = getJourneyType(journeyType);

        String actualFirstCallDate = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Inform of " + journeyType,
                "div.govuk-summary-list__row", contactMethod+" 1", "dd.govuk-summary-list__value")
              .innerText();
        assertContains(convertDayDateMonthYear(todayMinusNoOfDays(4)), actualFirstCallDate);

        String actualFirstCallDirection = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Inform of " + journeyType,
                "div.govuk-summary-list__row", "Call direction", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(callDirection, actualFirstCallDirection);

        String actualVictimInformed = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Inform of " + journeyType,
                "div.govuk-summary-list__row", "Victim Informed", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(informVictim, actualVictimInformed);

        String actualNotes = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Inform of " + journeyType,
                "div.govuk-summary-list__row", "Notes", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(notes, actualNotes);

    }

    public void followUpFirstCallSuccess(String personFirstLastName, String followUpMethod, String journeyType, String notes){
        journeyType = getJourneyType(journeyType);
        assertThat(readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Inform of " + journeyType,
                "div.govuk-summary-list__row", "Telephone call 1", "dd.govuk-summary-list__value"))
                .isVisible();

        selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title", "Inform of " + journeyType,
                "button.govuk-button", "Select letter dispatch method")
                .click();

        waitForText("How does " +personFirstLastName+ " want to receive the letter?");

        if("email".equalsIgnoreCase(followUpMethod)){
            getRadioButtonById("RadioButton_NFAReceiveLetterEmail").click();
        } else if("post".equalsIgnoreCase(followUpMethod)){
            getRadioButtonById("RadioButton_NFAReceiveLetterEmail2").click();
        } else if("they do not want a letter".equalsIgnoreCase(followUpMethod)){
            getRadioButtonById("RadioButton_NFAReceiveLetterNotWanted").click();
        } else {
            System.out.println("Specified follow up method does not exist");
        }



    }

}