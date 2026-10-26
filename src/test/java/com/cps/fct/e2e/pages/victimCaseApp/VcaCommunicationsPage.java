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
                Locator meetingOtherTable = page.locator("div.govuk-summary-card")
                        .filter(new Locator.FilterOptions()
                                .setHas(page.locator("h2.govuk-summary-card__title")
                                        .getByText("Other CPS meeting",
                                                new Locator.GetByTextOptions().setExact(true))));
                Locator  meetingOtherRow = meetingOtherTable.locator("div.govuk-summary-list__row")
                        .filter(new Locator.FilterOptions().setHasText("Has a meeting been offered"));
                String otherMeetingOffered = meetingOtherRow.locator("dd.govuk-summary-list__value").innerText();

                Locator  meetingOtherReason = meetingOtherTable.locator("div.govuk-summary-list__row")
                        .filter(new Locator.FilterOptions().setHasText("Why was a meeting not offered"));
                String otherMeetingReason = meetingOtherReason.locator("dd.govuk-summary-list__value").innerText();

                assertEquals("No", otherMeetingOffered);
                assertEquals(notOfferedReason, otherMeetingReason);
                break;

            case "Inform victim about charging decision":
                Locator meetingInformVictimTable = page.locator("div.govuk-summary-card")
                        .filter(new Locator.FilterOptions()
                                .setHas(page.locator("h2.govuk-summary-card__title")
                                        .getByText("Charging decision meeting",
                                                new Locator.GetByTextOptions().setExact(true))));
                Locator  meetingInformVictimRow = meetingInformVictimTable.locator("div.govuk-summary-list__row")
                        .filter(new Locator.FilterOptions().setHasText("Has a meeting been offered"));
                String informVictimRowOffered = meetingInformVictimRow.locator("dd.govuk-summary-list__value").innerText();

                Locator  meetingInformVictimReason = meetingInformVictimTable.locator("div.govuk-summary-list__row")
                        .filter(new Locator.FilterOptions().setHasText("Why was a meeting not offered"));
                String informVictimReason = meetingInformVictimReason.locator("dd.govuk-summary-list__value").innerText();
                assertEquals("No", informVictimRowOffered);
                assertEquals(notOfferedReason, informVictimReason);
                break;

            case "CPS pre-trial meeting":
                Locator meetingPreTrialTable = page.locator("div.govuk-summary-card")
                        .filter(new Locator.FilterOptions()
                                .setHas(page.locator("h2.govuk-summary-card__title")
                                                .getByText("CPS pre-trial meeting",
                                                        new Locator.GetByTextOptions().setExact(true))));
                Locator  meetingPreTrialRow = meetingPreTrialTable.locator("div.govuk-summary-list__row")
                        .filter(new Locator.FilterOptions().setHasText("Has a meeting been offered"));
                String preTrialOffered = meetingPreTrialRow.locator("dd.govuk-summary-list__value").innerText();

                Locator  meetingPreTrialReason = meetingPreTrialTable.locator("div.govuk-summary-list__row")
                        .filter(new Locator.FilterOptions().setHasText("Why was a meeting not offered"));
                String preTrialReason = meetingPreTrialReason.locator("dd.govuk-summary-list__value").innerText();
                assertEquals("No", preTrialOffered);
                assertEquals(notOfferedReason, preTrialReason);
                break;

            default:
                System.out.println("Specified meeting type does not exist");
        }
    }






}
