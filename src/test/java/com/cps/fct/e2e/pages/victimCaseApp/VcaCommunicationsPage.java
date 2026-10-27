package com.cps.fct.e2e.pages.victimCaseApp;

import com.cps.fct.e2e.pages.BasePage;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.cps.fct.e2e.utils.common.FakerUtils.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.restassured.RestAssured.expect;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class VcaCommunicationsPage extends BasePage {

//    @Inject
//    private ScenarioContext context;

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

    public void logMeetingNotOffered(String victimWitnessFirstSureName,String meetingType, String notOfferedReason){

        clickSelectOnAriaRole(page, AriaRole.BUTTON, "Log new meeting details");
        waitForText("What do you want to log?");
        clickSelectOnAriaRole(page, AriaRole.RADIO, "A meeting offer");
        getButtonByName("Continue").click();
        waitForText("What is the purpose of the meeting?");
        clickSelectOnAriaRole(page, AriaRole.RADIO, meetingType);

        if(meetingType.equals("Other")){
            page.getByRole(AriaRole.TEXTBOX).click();
            page.getByRole(AriaRole.TEXTBOX).fill("Cps Other Meeting Type");
        }

        getButtonByName("Continue").click();
        waitForText("Has " +victimWitnessFirstSureName+ " been offered a meeting?");
        clickSelectOnAriaRole(page, AriaRole.RADIO, "No");
        getButtonByName("Continue").click();
        waitForText("Why was a meeting not offered?");
        enterText("Why was a meeting not offered?",notOfferedReason);
        getButtonByName("Continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");

        if("Inform victim about charging decision".equals (meetingType) ||
                "Other".equals (meetingType)) {
            assertThat(page.getByText(
                    Pattern.compile("Success\\s*" + meetingType + " meeting offer has been recorded")))
                    .isVisible();
        }
        else {
            assertThat(page.getByText(
                    Pattern.compile("Success\\s*" + meetingType + " offer has been recorded")))
                    .isVisible();
        }

    }

    public void verifyMeetingNotOffered(String meetingType,String notOfferedReason){

        waitUntilSpinnersAreGone("Loading, please wait");

        switch (meetingType) {
            case "Other":
                String otherMeetingOffered = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title","Other CPS meeting",
                        "div.govuk-summary-list__row","Has a meeting been offered","dd.govuk-summary-list__value")
                        .innerText();

                String otherMeetingReason = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title","Other CPS meeting",
                        "div.govuk-summary-list__row","Why was a meeting not offered","dd.govuk-summary-list__value")
                        .innerText();

                assertEquals("No", otherMeetingOffered);
                assertEquals(notOfferedReason, otherMeetingReason);
                break;

            case "Inform victim about charging decision":
                String informVictimRowOffered = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title","Charging decision meeting",
                        "div.govuk-summary-list__row","Has a meeting been offered","dd.govuk-summary-list__value")
                        .innerText();

                String informVictimReason = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title","Charging decision meeting",
                        "div.govuk-summary-list__row","Why was a meeting not offered","dd.govuk-summary-list__value")
                        .innerText();

                assertEquals("No", informVictimRowOffered);
                assertEquals(notOfferedReason, informVictimReason);
                break;

            case "CPS pre-trial meeting":
                String preTrialOffered = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title","CPS pre-trial meeting",
                        "div.govuk-summary-list__row","Has a meeting been offered","dd.govuk-summary-list__value")
                        .innerText();

                String preTrialReason = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title","CPS pre-trial meeting",
                        "div.govuk-summary-list__row","Why was a meeting not offered","dd.govuk-summary-list__value")
                        .innerText();

                assertEquals("No", preTrialOffered);
                assertEquals(notOfferedReason, preTrialReason);
                break;

            default:
                System.out.println("Specified meeting type does not exist");
        }
    }






}
