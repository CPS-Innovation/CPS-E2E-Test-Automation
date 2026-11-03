package com.cps.fct.e2e.pages.victimCaseApp;

import com.cps.fct.e2e.pages.BasePage;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cps.fct.e2e.pages.*;

import java.util.Objects;
import java.util.regex.Pattern;

import static com.cps.fct.e2e.utils.common.FakerUtils.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VcaMeetingsPage extends BasePage {

    private final ScenarioContext scenarioContext;

    @Inject
    private PageObjects pages;

    public VcaMeetingsPage(PlaywrightContext context, ScenarioContext scenarioContext) {
        super(context);
        this.scenarioContext = scenarioContext;
    }

    private static final Logger logger =
            LoggerFactory.getLogger(VcaMeetingsPage.class);

    public void selectAcceptedMeetingType(String meetingType) {
        switch (meetingType) {
            case "CPS pre-trial meeting":
                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                        "CPS pre-trial meeting accepted",
                        "button.govuk-button", "Confirm arranged meetings")
                        .click();
                break;
            case "Inform victim about charging decision":
                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                        "Charging decision meeting accepted",
                        "button.govuk-button", "Confirm arranged meetings")
                        .click();
                break;
            case "Other":
                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                        "Other CPS meeting accepted",
                        "button.govuk-button", "Confirm arranged meetings")
                        .click();
                break;
            default:
                System.out.println("Specified meeting type does not exist");
        }


    }

    public void selectMeetingSource(String personFirstLastName, String meetingSource) {
        switch (meetingSource) {
            case "Victim Requested":
                clickSelectOnAriaRole(page, AriaRole.RADIO, personFirstLastName + " requested it");
                break;
            case "CPS offered":
                clickSelectOnAriaRole(page, AriaRole.RADIO, "CPS offered and " + personFirstLastName + " accepted");
                break;
            case "Third party Requested":
                clickSelectOnAriaRole(page, AriaRole.RADIO, "Requested by a third party");
                break;
            default:
                System.out.println("Specified meeting source does not exist");
        }

    }

    public void selectMeetingMethod(String meetingMethod, String locationType, String locationName) {
        switch (meetingMethod) {
            case "In person":
                clickSelectOnAriaRole(page, AriaRole.RADIO, meetingMethod);
                getButtonByName("Continue").click();
                waitForText("Where will the meeting take place?");
                selectLocation(locationType, locationName);
                break;
            case "Virtual call":
                clickSelectOnAriaRole(page, AriaRole.RADIO, meetingMethod);
                break;
            case "Hybrid":
                clickSelectOnAriaRole(page, AriaRole.RADIO, meetingMethod);
                break;
            default:
                System.out.println("Specified meeting method does not exist");
        }

    }

    public void selectLocation(String locationType, String locationName) {
        waitForText("Where will the meeting take place?");
        switch (locationType){
            case "CPS location":
                clickSelectOnAriaRole(page, AriaRole.RADIO, locationType);
                waitForText("Which CPS location will the meeting be held?");
                selectDropdownByValue("div.cps_dropdown",locationName);
                break;
            case "Magistrates' court":
                clickSelectOnAriaRole(page, AriaRole.RADIO, locationType);
                waitForText("Which Magistrates' court will the meeting be held?");
                selectDropdownByValue("div.cps_dropdown",locationName);

                break;
            case "Crown court":
                break;
            case "Police station":
                break;
            case "Other":
                break;
            default:
                System.out.println("Specified location type does not exist");

        }

    }


}
