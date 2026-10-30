package com.cps.fct.e2e.pages.victimCaseApp;

import com.cps.fct.e2e.pages.BasePage;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

import static com.cps.fct.e2e.utils.common.FakerUtils.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VcaCommunicationsPage extends BasePage {

    private final ScenarioContext scenarioContext;

    public VcaCommunicationsPage(PlaywrightContext context, ScenarioContext scenarioContext) {
        super(context);
        this.scenarioContext = scenarioContext;
    }

    private static final Logger logger =
            LoggerFactory.getLogger(VcaCommunicationsPage.class);

    public void navigateMeetings() {
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText("Communications");
        waitForElement(page, AriaRole.TAB, "Meetings");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Meetings");
    }

    public void logMeetingNotOffered(String victimWitnessFirstSureName, String meetingType, String notOfferedReason) {
        clickSelectOnAriaRole(page, AriaRole.BUTTON, "Log new meeting details");
        waitForText("What do you want to log?");
        clickSelectOnAriaRole(page, AriaRole.RADIO, "A meeting offer");
        getButtonByName("Continue").click();
        waitForText("What is the purpose of the meeting?");
        clickSelectOnAriaRole(page, AriaRole.RADIO, meetingType);

        if (meetingType.equals("Other")) {
            page.getByRole(AriaRole.TEXTBOX).click();
            page.getByRole(AriaRole.TEXTBOX).fill("Cps Other Meeting Type");
        }

        getButtonByName("Continue").click();
        waitForText("Has " + victimWitnessFirstSureName + " been offered a meeting?");
        clickSelectOnAriaRole(page, AriaRole.RADIO, "No");
        getButtonByName("Continue").click();
        waitForText("Why was a meeting not offered?");
        enterText("Why was a meeting not offered?", notOfferedReason);
        getButtonByName("Continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");

        if ("Inform victim about charging decision".equals(meetingType) ||
                "Other".equals(meetingType)) {
            assertThat(page.getByText(
                    Pattern.compile("Success\\s*" + meetingType + " meeting offer has been recorded")))
                    .isVisible();
        } else {
            assertThat(page.getByText(
                    Pattern.compile("Success\\s*" + meetingType + " offer has been recorded")))
                    .isVisible();
        }
    }

    public void verifyMeetingNotOffered(String meetingType, String notOfferedReason) {
        waitUntilSpinnersAreGone("Loading, please wait");
        switch (meetingType) {
            case "Other":
                String otherMeetingOffered = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting",
                        "div.govuk-summary-list__row", "Has a meeting been offered", "dd.govuk-summary-list__value")
                        .innerText();
                String otherMeetingReason = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting",
                        "div.govuk-summary-list__row", "Why was a meeting not offered", "dd.govuk-summary-list__value")
                        .innerText();

                assertEquals("No", otherMeetingOffered);
                assertEquals(notOfferedReason, otherMeetingReason);
                break;
            case "Inform victim about charging decision":
                String informVictimRowOffered = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting",
                        "div.govuk-summary-list__row", "Has a meeting been offered", "dd.govuk-summary-list__value")
                        .innerText();

                String informVictimReason = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting",
                        "div.govuk-summary-list__row", "Why was a meeting not offered", "dd.govuk-summary-list__value")
                        .innerText();

                assertEquals("No", informVictimRowOffered);
                assertEquals(notOfferedReason, informVictimReason);
                break;
            case "CPS pre-trial meeting":
                String preTrialOffered = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting",
                        "div.govuk-summary-list__row", "Has a meeting been offered", "dd.govuk-summary-list__value")
                        .innerText();

                String preTrialReason = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting",
                        "div.govuk-summary-list__row", "Why was a meeting not offered", "dd.govuk-summary-list__value")
                        .innerText();

                assertEquals("No", preTrialOffered);
                assertEquals(notOfferedReason, preTrialReason);
                break;
            default:
                System.out.println("Specified meeting type does not exist");
        }
    }

    public void logMeetingOffered(String personFirstLastName, String meetingType, String meetingMethod) {

        clickSelectOnAriaRole(page, AriaRole.BUTTON, "Log new meeting details");
        waitForText("What do you want to log?");
        clickSelectOnAriaRole(page, AriaRole.RADIO, "A meeting offer");
        getButtonByName("Continue").click();
        waitForText("What is the purpose of the meeting?");
        clickSelectOnAriaRole(page, AriaRole.RADIO, meetingType);

        if (meetingType.equals("Other")) {
            page.getByRole(AriaRole.TEXTBOX).click();
            page.getByRole(AriaRole.TEXTBOX).fill("Cps Other Meeting Type");
        }

        getButtonByName("Continue").click();
        waitForText("Has " + personFirstLastName + " been offered a meeting?");
        clickSelectOnAriaRole(page, AriaRole.RADIO, "Yes");
        getButtonByName("Continue").click();
        waitForText("Details of the meeting offered");
        clickSelectOnAriaRole(page, AriaRole.RADIO, meetingMethod);
        page.getByRole(AriaRole.TEXTBOX).click();
        page.getByRole(AriaRole.TEXTBOX).fill(todayMinusFiveDays());
        getButtonByName("Continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        if ("Inform victim about charging decision".equals(meetingType) ||
                "Other".equals(meetingType)) {
            assertThat(page.getByText(
                    Pattern.compile("Success\\s*" + meetingType + " meeting offer has been recorded")))
                    .isVisible();
        } else {
            assertThat(page.getByText(
                    Pattern.compile("Success\\s*" + meetingType + " offer has been recorded")))
                    .isVisible();
        }
    }

    public void logMeetingResponse(String meetingType, String meetingResponse, String responseMethod) {
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText("Communications");
        waitForElement(page, AriaRole.TAB, "Meetings");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Meetings");

        switch (meetingType) {
            case "Other":
                waitUntilSpinnersAreGone("Loading, please wait");
                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                        "Other CPS meeting offered",
                        "button.govuk-button", meetingResponse)
                        .click();
                break;
            case "Inform victim about charging decision":
                waitUntilSpinnersAreGone("Loading, please wait");
                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                        "Charging decision meeting offered",
                        "button.govuk-button", meetingResponse)
                        .click();
                break;
            case "CPS pre-trial meeting":
                waitUntilSpinnersAreGone("Loading, please wait");
                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                        "CPS pre-trial meeting offered",
                        "button.govuk-button", meetingResponse)
                        .click();
                break;
            default:
                System.out.println("Specified meeting type does not exist");
        }
        waitUntilSpinnersAreGone("Loading, please wait");

        switch (meetingResponse) {
            case "Log meeting acceptance":
                waitForText("Details of the meeting acceptance");
                waitForText("How was it accepted?");
                clickSelectOnAriaRole(page, AriaRole.RADIO, responseMethod);
                page.getByRole(AriaRole.TEXTBOX).click();
                page.getByRole(AriaRole.TEXTBOX).fill(todayMinusFourDays());
                getButtonByName("Continue").click();
                waitUntilSpinnersAreGone("Loading, please wait");
                waitForText("Communications");
                waitForElement(page, AriaRole.TAB, "Meetings");
                switch (meetingType) {
                    case "CPS pre-trial meeting":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting accepted",
                                "div.govuk-summary-list__row", "How was meeting accepted", "dd.govuk-summary-list__value"))
                                .isVisible();
                        break;
                    case "Inform victim about charging decision":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting accepted",
                                "div.govuk-summary-list__row", "How was meeting accepted", "dd.govuk-summary-list__value"))
                                .isVisible();
                        break;
                    case "Other":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting accepted",
                                "div.govuk-summary-list__row", "How was meeting accepted", "dd.govuk-summary-list__value"))
                                .isVisible();
                        break;
                    default:
                        System.out.println("Specified meeting type does not exist");
                }
                break;
            case "Log meeting declined":
                waitForText("Details of the meeting declined");
                waitForText("How was it declined?");
                clickSelectOnAriaRole(page, AriaRole.RADIO, responseMethod);
                page.getByRole(AriaRole.TEXTBOX).click();
                page.getByRole(AriaRole.TEXTBOX).fill(todayMinusFourDays());
                getButtonByName("Continue").click();
                waitUntilSpinnersAreGone("Loading, please wait");
                waitForText("Communications");
                waitForElement(page, AriaRole.TAB, "Meetings");
                switch (meetingType) {
                    case "CPS pre-trial meeting":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting declined",
                                "div.govuk-summary-list__row", "How was meeting declined", "dd.govuk-summary-list__value"))
                                .isVisible();
                        break;
                    case "Inform victim about charging decision":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting declined",
                                "div.govuk-summary-list__row", "How was meeting declined", "dd.govuk-summary-list__value"))
                                .isVisible();
                        break;
                    case "Other":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting declined",
                                "div.govuk-summary-list__row", "How was meeting declined", "dd.govuk-summary-list__value"))
                                .isVisible();
                        break;
                    default:
                        System.out.println("Specified meeting type does not exist");
                }
                break;
            case "Log no response":
                waitUntilSpinnersAreGone("Loading, please wait");
                waitForText("Communications");
                waitForElement(page, AriaRole.TAB, "Meetings");
                switch (meetingType) {
                    case "CPS pre-trial meeting":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to CPS pre-trial meeting offer",
                                "div.govuk-summary-list__row", "Response", "dd.govuk-summary-list__value"))
                                .isVisible();
                        break;
                    case "Inform victim about charging decision":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to Charging decision meeting offer",
                                "div.govuk-summary-list__row", "Response", "dd.govuk-summary-list__value"))
                                .isVisible();
                        break;
                    case "Other":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to Other CPS meeting offer",
                                "div.govuk-summary-list__row", "Response", "dd.govuk-summary-list__value"))
                                .isVisible();
                        break;
                    default:
                        System.out.println("Specified response type does not exist");
                }
                break;
            default:
                System.out.println("Specified meeting type does not exist");
        }

    }

    public void verifyMeetingOfferResponse(String meetingType, String offerMethod, String meetingResponse, String meetingResponseMethod) {
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText("Communications");
        waitForElement(page, AriaRole.TAB, "Meetings");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Meetings");

        if (Objects.equals(offerMethod, "By telephone")) {
            offerMethod = "Telephone";
        }
        if (Objects.equals(meetingResponseMethod, "By telephone")) {
            meetingResponseMethod = "Telephone";
        }

        switch (meetingResponse) {
            case "Log meeting acceptance":
                waitUntilSpinnersAreGone("Loading, please wait");
                waitForText("Communications");
                waitForElement(page, AriaRole.TAB, "Meetings");
                switch (meetingType) {
                    case "CPS pre-trial meeting":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting accepted",
                                "div.govuk-summary-list__row", "How was meeting accepted", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String preTrialMeetingAcceptedMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting accepted",
                                "div.govuk-summary-list__row", "How was meeting accepted", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(meetingResponseMethod, preTrialMeetingAcceptedMethod);
                        String preTrialMeetingAcceptedDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting accepted",
                                "div.govuk-summary-list__row", "Date meeting was accepted", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(preTrialMeetingAcceptedDate, convertDateMonthYear(todayMinusFourDays()));

                        Locator preTrialMeetingOffered = selectTableLink(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting accepted",
                                "summary.govuk-details__summary",
                                "Offered meetings"
                        );
                        preTrialMeetingOffered.click();

                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String preTrialMeetingOfferedMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(preTrialMeetingOfferedMethod, offerMethod);
                        String preTrialMeetingOfferedDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting offered",
                                "div.govuk-summary-list__row", "Date meeting was offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(preTrialMeetingOfferedDate, convertDateMonthYear(todayMinusFiveDays()));
                        break;
                    case "Inform victim about charging decision":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting accepted",
                                "div.govuk-summary-list__row", "How was meeting accepted", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String chargingDecisionMeetingAcceptedMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting accepted",
                                "div.govuk-summary-list__row", "How was meeting accepted", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(meetingResponseMethod, chargingDecisionMeetingAcceptedMethod);
                        String chargingDecisionMeetingAcceptedDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting accepted",
                                "div.govuk-summary-list__row", "Date meeting was accepted", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(chargingDecisionMeetingAcceptedDate, convertDateMonthYear(todayMinusFourDays()));

                        Locator chargingDecisionMeetingOffered = selectTableLink(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting accepted",
                                "summary.govuk-details__summary",
                                "Offered meetings"
                        );
                        chargingDecisionMeetingOffered.click();

                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String chargingDecisionMeetingOfferedMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(chargingDecisionMeetingOfferedMethod, offerMethod);
                        String chargingDecisionMeetingOfferedDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting offered",
                                "div.govuk-summary-list__row", "Date meeting was offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(chargingDecisionMeetingOfferedDate, convertDateMonthYear(todayMinusFiveDays()));
                        break;
                    case "Other":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting accepted",
                                "div.govuk-summary-list__row", "How was meeting accepted", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String otherMeetingAcceptedMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting accepted",
                                "div.govuk-summary-list__row", "How was meeting accepted", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(meetingResponseMethod, otherMeetingAcceptedMethod);
                        String otherMeetingAcceptedDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting accepted",
                                "div.govuk-summary-list__row", "Date meeting was accepted", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(otherMeetingAcceptedDate, convertDateMonthYear(todayMinusFourDays()));

                        Locator otherMeetingOffered = selectTableLink(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting accepted",
                                "summary.govuk-details__summary",
                                "Offered meetings"
                        );
                        otherMeetingOffered.click();

                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String otherMeetingOfferedMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(otherMeetingOfferedMethod, offerMethod);
                        String otherMeetingOfferedDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting offered",
                                "div.govuk-summary-list__row", "Date meeting was offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(otherMeetingOfferedDate, convertDateMonthYear(todayMinusFiveDays()));
                        break;
                    default:
                        System.out.println("Specified meeting type does not exist");
                }
                break;
            case "Log meeting declined":
                waitUntilSpinnersAreGone("Loading, please wait");
                waitForText("Communications");
                waitForElement(page, AriaRole.TAB, "Meetings");
                switch (meetingType) {
                    case "CPS pre-trial meeting":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting declined",
                                "div.govuk-summary-list__row", "How was meeting declined", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String preTrialMeetingDeclineMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting declined",
                                "div.govuk-summary-list__row", "How was meeting declined", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(meetingResponseMethod, preTrialMeetingDeclineMethod);
                        String preTrialMeetingDeclineDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting declined",
                                "div.govuk-summary-list__row", "Date meeting was declined", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(preTrialMeetingDeclineDate, convertDateMonthYear(todayMinusFourDays()));

                        Locator preTrialMeetingOffered = selectTableLink(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting declined",
                                "summary.govuk-details__summary",
                                "Offered meetings"
                        );
                        preTrialMeetingOffered.click();

                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String preTrialMeetingOfferedMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(preTrialMeetingOfferedMethod, offerMethod);
                        String preTrialMeetingOfferedDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting offered",
                                "div.govuk-summary-list__row", "Date meeting was offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(preTrialMeetingOfferedDate, convertDateMonthYear(todayMinusFiveDays()));
                        break;
                    case "Inform victim about charging decision":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting declined",
                                "div.govuk-summary-list__row", "How was meeting declined", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String chargingDecisionMeetingDeclinedMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting declined",
                                "div.govuk-summary-list__row", "How was meeting declined", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(meetingResponseMethod, chargingDecisionMeetingDeclinedMethod);
                        String chargingDecisionMeetingDeclinedDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting declined",
                                "div.govuk-summary-list__row", "Date meeting was declined", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(chargingDecisionMeetingDeclinedDate, convertDateMonthYear(todayMinusFourDays()));

                        Locator chargingDecisionMeetingOffered = selectTableLink(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting declined",
                                "summary.govuk-details__summary",
                                "Offered meetings"
                        );
                        chargingDecisionMeetingOffered.click();

                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String chargingDecisionMeetingOfferedMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(chargingDecisionMeetingOfferedMethod, offerMethod);
                        String chargingDecisionMeetingOfferedDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting offered",
                                "div.govuk-summary-list__row", "Date meeting was offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(chargingDecisionMeetingOfferedDate, convertDateMonthYear(todayMinusFiveDays()));
                        break;
                    case "Other":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting declined",
                                "div.govuk-summary-list__row", "How was meeting declined", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String otherMeetingDeclinedMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting declined",
                                "div.govuk-summary-list__row", "How was meeting declined", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(meetingResponseMethod, otherMeetingDeclinedMethod);
                        String otherMeetingDeclinedDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting declined",
                                "div.govuk-summary-list__row", "Date meeting was declined", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(otherMeetingDeclinedDate, convertDateMonthYear(todayMinusFourDays()));

                        Locator otherMeetingOffered = selectTableLink(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting declined",
                                "summary.govuk-details__summary",
                                "Offered meetings"
                        );
                        otherMeetingOffered.click();

                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String otherMeetingOfferedMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(otherMeetingOfferedMethod, offerMethod);
                        String otherMeetingOfferedDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting offered",
                                "div.govuk-summary-list__row", "Date meeting was offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(otherMeetingOfferedDate, convertDateMonthYear(todayMinusFiveDays()));
                        break;
                    default:
                        System.out.println("Specified meeting type does not exist");
                }
                break;
            case "Log no response":
                waitUntilSpinnersAreGone("Loading, please wait");
                waitForText("Communications");
                waitForElement(page, AriaRole.TAB, "Meetings");
                switch (meetingType) {
                    case "CPS pre-trial meeting":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to CPS pre-trial meeting offer",
                                "div.govuk-summary-list__row", "Response", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String preTrialMeetingNoResponse = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to CPS pre-trial meeting offer",
                                "div.govuk-summary-list__row", "Response", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals("No", preTrialMeetingNoResponse);

                        Locator preTrialMeetingOffered = selectTableLink(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to CPS pre-trial meeting offer",
                                "summary.govuk-details__summary",
                                "Offered meetings"
                        );
                        preTrialMeetingOffered.click();

                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String preTrialMeetingOfferedMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(preTrialMeetingOfferedMethod, offerMethod);
                        String preTrialMeetingOfferedDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting offered",
                                "div.govuk-summary-list__row", "Date meeting was offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(preTrialMeetingOfferedDate, convertDateMonthYear(todayMinusFiveDays()));
                        break;
                    case "Inform victim about charging decision":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to Charging decision meeting offer",
                                "div.govuk-summary-list__row", "Response", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String chargingDecisionMeetingNoResponse = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to Charging decision meeting offer",
                                "div.govuk-summary-list__row", "Response", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals("No", chargingDecisionMeetingNoResponse);
                        Locator chargingDecisionMeetingOffered = selectTableLink(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to Charging decision meeting offer",
                                "summary.govuk-details__summary",
                                "Offered meetings"
                        );
                        chargingDecisionMeetingOffered.click();

                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String chargingDecisionMeetingOfferedMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(chargingDecisionMeetingOfferedMethod, offerMethod);
                        String chargingDecisionMeetingOfferedDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting offered",
                                "div.govuk-summary-list__row", "Date meeting was offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(chargingDecisionMeetingOfferedDate, convertDateMonthYear(todayMinusFiveDays()));
                        break;
                    case "Other":
                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to Other CPS meeting offer",
                                "div.govuk-summary-list__row", "Response", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String otherMeetingResponse = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to Other CPS meeting offer",
                                "div.govuk-summary-list__row", "Response", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals("No", otherMeetingResponse);

                        Locator otherMeetingOffered = selectTableLink(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to Other CPS meeting offer",
                                "summary.govuk-details__summary",
                                "Offered meetings"
                        );
                        otherMeetingOffered.click();

                        assertThat(readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value"))
                                .isVisible();
                        String otherMeetingOfferedMethod = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting offered",
                                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(otherMeetingOfferedMethod, offerMethod);
                        String otherMeetingOfferedDate = readTableRowCell(page,
                                "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting offered",
                                "div.govuk-summary-list__row", "Date meeting was offered", "dd.govuk-summary-list__value")
                                .innerText();
                        assertEquals(otherMeetingOfferedDate, convertDateMonthYear(todayMinusFiveDays()));
                        break;
                    default:
                        System.out.println("Specified response type does not exist");
                }
                break;
            default:
                System.out.println("Specified response type does not exist");
        }
    }


}
