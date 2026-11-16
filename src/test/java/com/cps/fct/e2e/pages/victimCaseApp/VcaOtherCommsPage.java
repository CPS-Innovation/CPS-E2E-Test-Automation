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

public class VcaOtherCommsPage extends BasePage {

    private final ScenarioContext scenarioContext;

    public VcaOtherCommsPage(PlaywrightContext context, ScenarioContext scenarioContext) {
        super(context);
        this.scenarioContext = scenarioContext;
    }

    private static final Logger logger =
            LoggerFactory.getLogger(VcaOtherCommsPage.class);

    public String getOtherCommsType(@NotNull String communicationType) {
        switch (communicationType.trim().toLowerCase()) {
            case "telephone call":
                communicationType = "Call";
                break;
            case "email":
                communicationType = "Email";
                break;
            case "post":
                communicationType = "Letter";
                break;
            case "text message":
                communicationType = "Text message";
                break;
            case "in person":
                communicationType = "In person conversation";
                break;
            case "other":
                communicationType = "Other Communication";
                break;
            default:
                System.out.println("Specified communication type does not exist");
        }
        return communicationType;
    }


    public String getRequiredCommsType(String communicationType) {

        if ("Call".equalsIgnoreCase(communicationType)) {
            communicationType = "Telephone call";
        } else if ("Letter".equalsIgnoreCase(communicationType)) {
            communicationType = "Post";
        } else if ("In person conversation".equalsIgnoreCase(communicationType)) {
            communicationType = "In person";
        } else if ("Other Communication".equalsIgnoreCase(communicationType)) {
            communicationType = "Other";
        } else {
            communicationType = communicationType;
        }
        return communicationType;
    }

    public void logOtherCommunicationDetails(String communicationType, String personFirstLastName, String direction,
                                             String personContacted, String purpose) {

        if ("Other".equalsIgnoreCase(communicationType)) {
            clickSelectOnAriaRole(page, AriaRole.RADIO, communicationType);
            waitForTextToAppear("Contact method");
            enterTextById("b7-Input_TextEntry", "Other Communication");
            getButtonByName("Continue").click();
        } else {
            clickSelectOnAriaRole(page, AriaRole.RADIO, communicationType);
            getButtonByName("Continue").click();
        }

        if ("In person".equalsIgnoreCase(communicationType)) {
            waitForText("Log an in-person conversation");
        } else if ("Other".equalsIgnoreCase(communicationType)) {
            waitForText("Log other communication");
        } else if ("Email".equalsIgnoreCase(communicationType)) {
            waitForText("Log an " + communicationType.toLowerCase());
        } else if ("Post".equalsIgnoreCase(communicationType)) {
            waitForText("Log a letter");
        } else if ("Text message".equalsIgnoreCase(communicationType)) {
            waitForText("Log a text message");
        } else {
            waitForText("Log a " + communicationType.toLowerCase());
        }

        enterTextInLocator("#b8-b4-Input_Date", todayMinusNoOfDays(1));
        enterTextInLocator("#b8-Input_Call_Hour", utcHourNow());
        enterTextInLocator("#b8-Input_Call_Minutes", utcMinuteNow());
        if (!"In Person".equalsIgnoreCase(communicationType)) {
            if ("Outbound".equalsIgnoreCase(direction)) {
                getRadioButtonById("b8-RadioButton_Outbound-input").click();
            } else if ("Inbound".equalsIgnoreCase(direction)) {
                getRadioButtonById("b8-RadioButton_Inbound-input").click();
            }
        }
        if ("Victim".equalsIgnoreCase(personContacted)) {
            getRadioButtonById("b8-RadioButton_Victim-input").click();
        } else if ("Other".equalsIgnoreCase(personContacted)) {
            getRadioButtonById("b8-RadioButton_Other-input").click();
            waitForTextToAppear("Name");
            enterTextById("b8-b9-InputName_TextEntry", firstnameSurname());
            enterTextById("b8-b9-InputRole_TextEntry", "Other Role Text");
        }
        enterTextById("b8-TextArea_Purpose", purpose);
        getButtonByName("Save & continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");

        if ("Post".equalsIgnoreCase(communicationType)) {
            communicationType = "Letter";
        }

        if ("In Person".equalsIgnoreCase(communicationType)) {
            communicationType = "In-person conversation";
        }

        if ("Other".equalsIgnoreCase(communicationType)) {
            communicationType = "Communication";
        }

        assertThat(page.getByText(
                Pattern.compile(communicationType + " logged")))
                .isVisible();

        waitForTextToAppear("The " + communicationType.toLowerCase() + " was successfully logged for the victim " + personFirstLastName + ".");
        getButtonByName("Continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
    }

    public void confirmOtherCommunicationDetails(String communicationType) {

        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText("Communications");
        waitForElement(page, AriaRole.TAB, "Other communication");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Other communication");

        communicationType = getOtherCommsType(communicationType);
        assertThat(readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationType + " logged",
                "div.govuk-summary-list__row", "Person contacted", "dd.govuk-summary-list__value"))
                .isVisible();
    }

    public void verifyOtherCommunicationDetails(String personFullName, String communicationType, String direction,
                                                String personContacted, String purpose) {

        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText("Communications");
        waitForElement(page, AriaRole.TAB, "Other communication");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Other communication");
        String communicationTableType = getOtherCommsType(communicationType);

        if ("Victim".equalsIgnoreCase(personContacted)) {
            personContacted = personFullName + " (victim)";
        } else if ("Other".equalsIgnoreCase(personContacted)) {
            personContacted = " (Other Role Text)";
        }

        if ("Telephone call".equalsIgnoreCase(communicationType)) {
            String actualDateOfComms = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                    "div.govuk-summary-list__row", "Date of telephone "+communicationTableType.toLowerCase(), "dd.govuk-summary-list__value")
                    .innerText();
            assertContains(convertDayDateMonthYear(todayMinusNoOfDays(1)), actualDateOfComms);

            String actualPurposeOfComms = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                    "div.govuk-summary-list__row", "Purpose of "+communicationTableType.toLowerCase(), "dd.govuk-summary-list__value")
                    .innerText();
            assertEquals(purpose, actualPurposeOfComms);

            String actualDirectionOfComms = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                    "div.govuk-summary-list__row", "Direction of "+communicationTableType.toLowerCase(), "dd.govuk-summary-list__value")
                    .innerText();
            assertEquals(direction, actualDirectionOfComms);

        } else if ("In Person".equalsIgnoreCase(communicationType)) {
            String actualDateOfComms = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                    "div.govuk-summary-list__row", "Date of conversation", "dd.govuk-summary-list__value")
                    .innerText();
            assertContains(convertDayDateMonthYear(todayMinusNoOfDays(1)), actualDateOfComms);

            String actualPurposeOfComms = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                    "div.govuk-summary-list__row", "Purpose of conversation", "dd.govuk-summary-list__value")
                    .innerText();
            assertEquals(purpose, actualPurposeOfComms);

        } else if ("Other".equalsIgnoreCase(communicationType)) {
            String actualDateOfComms = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                    "div.govuk-summary-list__row", "Date of communication", "dd.govuk-summary-list__value")
                    .innerText();
            assertContains(convertDayDateMonthYear(todayMinusNoOfDays(1)), actualDateOfComms);

            String actualPurposeOfComms = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                    "div.govuk-summary-list__row", "Purpose of communication", "dd.govuk-summary-list__value")
                    .innerText();
            assertEquals(purpose, actualPurposeOfComms);

            String actualDirectionOfComms = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                    "div.govuk-summary-list__row", "Direction of communication", "dd.govuk-summary-list__value")
                    .innerText();
            assertEquals(direction, actualDirectionOfComms);

        }else if ("Post".equalsIgnoreCase(communicationType)) {
            String actualDateOfComms = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                    "div.govuk-summary-list__row", "Date of "+communicationTableType.toLowerCase(), "dd.govuk-summary-list__value")
                    .innerText();
            assertContains(convertDayDateMonthYear(todayMinusNoOfDays(1)), actualDateOfComms);

            String actualPurposeOfComms = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                    "div.govuk-summary-list__row", "Purpose of "+communicationTableType.toLowerCase(), "dd.govuk-summary-list__value")
                    .innerText();
            assertEquals(purpose, actualPurposeOfComms);

            String actualDirectionOfComms = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                    "div.govuk-summary-list__row", "Direction of "+communicationTableType.toLowerCase(), "dd.govuk-summary-list__value")
                    .innerText();
            assertEquals(direction, actualDirectionOfComms);
        }
        else {
            String actualDateOfComms = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                    "div.govuk-summary-list__row", "Date of " + communicationType.toLowerCase(), "dd.govuk-summary-list__value")
                    .innerText();
            assertContains(convertDayDateMonthYear(todayMinusNoOfDays(1)), actualDateOfComms);

            String actualPurposeOfComms = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                    "div.govuk-summary-list__row", "Purpose of " + communicationTableType.toLowerCase(), "dd.govuk-summary-list__value")
                    .innerText();
            assertEquals(purpose, actualPurposeOfComms);

            String actualDirectionOfComms = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                    "div.govuk-summary-list__row", "Direction of " + communicationTableType.toLowerCase(), "dd.govuk-summary-list__value")
                    .innerText();
            assertEquals(direction, actualDirectionOfComms);
        }

        String actualPersonContacted = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", communicationTableType + " logged",
                "div.govuk-summary-list__row", "Person contacted", "dd.govuk-summary-list__value")
                .innerText();
        assertContains(personContacted, actualPersonContacted);
    }


}