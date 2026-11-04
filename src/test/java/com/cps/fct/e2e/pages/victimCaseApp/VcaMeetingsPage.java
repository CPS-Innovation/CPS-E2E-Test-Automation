package com.cps.fct.e2e.pages.victimCaseApp;

import com.cps.fct.e2e.model.victimCaseApp.MeetingAttendees;
import com.cps.fct.e2e.pages.BasePage;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cps.fct.e2e.pages.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.cps.fct.e2e.utils.common.FakerUtils.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VcaMeetingsPage extends BasePage {

    private final ScenarioContext scenarioContext;


    public VcaMeetingsPage(PlaywrightContext context, ScenarioContext scenarioContext) {
        super(context);
        this.scenarioContext = scenarioContext;
    }

    private static final Logger logger =
            LoggerFactory.getLogger(VcaMeetingsPage.class);

    public void selectAcceptedMeetingType(String meetingType) {
        switch (meetingType.trim().toLowerCase()) {
            case "cps pre-trial meeting":
                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                        "CPS pre-trial meeting accepted",
                        "button.govuk-button", "Confirm arranged meetings")
                        .click();
                break;
            case "inform victim about charging decision":
                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                        "Charging decision meeting accepted",
                        "button.govuk-button", "Confirm arranged meetings")
                        .click();
                break;
            case "other":
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
        switch (meetingSource.trim().toLowerCase()) {
            case "victim requested":
                clickSelectOnAriaRole(page, AriaRole.RADIO, personFirstLastName + " requested it");
                break;
            case "cps offered":
                clickSelectOnAriaRole(page, AriaRole.RADIO, "CPS offered and " + personFirstLastName + " accepted");
                break;
            case "third party requested":
                clickSelectOnAriaRole(page, AriaRole.RADIO, "Requested by a third party");
                break;
            default:
                System.out.println("Specified meeting source does not exist");
        }

    }

    public void selectMeetingMethod(String meetingMethod, String locationType, String locationName) {
        switch (meetingMethod.trim().toLowerCase()) {
            case "in person":
                getRadioButtonById("MeetingMethod_InPerson").click();
                getButtonByName("Continue").click();
                waitForText("Where will the meeting take place?");
                selectLocation(locationType, locationName);
                page.pause();
                break;
            case "virtual call":
                getRadioButtonById("MeetingMethod_VirtualCall").click();
                getButtonByName("Continue").click();
                break;
            case "hybrid":
                getRadioButtonById("MeetingMethod_Hybrid").click();
                getButtonByName("Continue").click();
                break;
            default:
                System.out.println("Specified meeting method does not exist");
        }

    }

    public void selectLocation(String locationType, String locationName) {
        waitForText("Where will the meeting take place?");
        switch (locationType.trim().toLowerCase()){
            case "cps location":
                getRadioButtonById("CPSLocation").click();
                waitForText("Which CPS location will the meeting be held?");
                getElement("#b16-Input_SearchText").click();
                page.pause();
                selectFromDropDownList("[role='listbox']",locationName );
                page.pause();
                break;
            case "magistrates' court":
                clickSelectOnAriaRole(page, AriaRole.RADIO, locationType);
                waitForText("Which Magistrates' court will the meeting be held?");
                selectDropdownByValue("div.cps_dropdown",locationName);

                break;
            case "crown court":
                break;
            case "police station":
                break;
            case "other":
                break;
            default:
                System.out.println("Specified location type does not exist");

        }

    }

    public void selectMeetingAttendees(List<String> meetingAttendeesRoles){
        for (String role : meetingAttendeesRoles) {
            System.out.println(role);
        }
    }

    public void selectChairPerson (String chairPerson){


    }






}
