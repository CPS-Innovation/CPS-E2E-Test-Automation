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

import static com.cps.fct.e2e.utils.common.DateTimeUtils.utcHourNow;
import static com.cps.fct.e2e.utils.common.DateTimeUtils.utcMinuteNow;
import static com.cps.fct.e2e.utils.common.FakerUtils.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
                break;
            case "virtual call":
                getRadioButtonById("MeetingMethod_VirtualCall").click();
                break;
            case "hybrid":
                getRadioButtonById("MeetingMethod_Hybrid").click();
                getButtonByName("Continue").click();
                waitForText("Where will the meeting take place?");
                selectLocation(locationType, locationName);
                break;
            default:
                System.out.println("Specified meeting method does not exist");
        }

    }

    public void selectLocation(String locationType, String locationName) {
        waitForText("Where will the meeting take place?");
        switch (locationType.trim().toLowerCase()) {
            case "cps location":
                getRadioButtonById("CPSLocation").click();
                waitForText("Which CPS location will the meeting be held?");
                getElement("#b16-Input_SearchText").click();
                selectFromDropDownList("[role='listbox']", locationName);
                break;
            case "magistrates court":
                clickSelectOnAriaRole(page, AriaRole.RADIO, "Magistrates' court");
                waitForText("Which Magistrates' court will the meeting be held?");
                getElementById("b18-b1-Input_SearchText").click();
                selectFromDropDownList("[role='listbox']", locationName);
                break;
            case "crown court":
                clickSelectOnAriaRole(page, AriaRole.RADIO, "Crown court");
                waitForText("Which Crown court will the meeting be held?");
                getElementById("b20-b1-Input_SearchText").click();
                selectFromDropDownList("[role='listbox']", locationName);
                break;
            case "police station":
                clickSelectOnAriaRole(page, AriaRole.RADIO, "Police station");
                waitForText("Which Crown court will the meeting be held?");
                getElementById("b22-b1-Input_SearchText").click();
                selectFromDropDownList("[role='listbox']", locationName);
                break;
            case "other":
                clickSelectOnAriaRole(page, AriaRole.RADIO, "Other");
                waitForText("Where will the meeting be held?");
                enterTextById("b24-TextArea_TextEntry", locationName);
                break;
            default:
                System.out.println("Specified location type does not exist");
        }
    }

    public void selectMeetingAttendees(List<String> meetingAttendeesRoles) {
        for (String role : meetingAttendeesRoles) {
            switch (role.trim().toLowerCase()) {
                case "defence firm":
                    selectCheckboxContainText("Defence firm");
                case "defence solicitor":
                    selectCheckboxContainText("Defence Solicitor");
                case "officer in charge":
                    selectCheckboxContainText("Officer in Charge");
                case "victim liaison officer":
                    selectCheckboxContainText("Victim Liaison Officer");
                case "counsel":
                    selectCheckboxContainText("Counsel");
            }
        }
        getButtonByName("Continue").click();
        waitForText("Who will lead the meeting?");

    }

    public void verifyArrangedMeeting(String meetingType) {
        switch (meetingType.trim().toLowerCase()) {
            case "cps pre-trial meeting":
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting arranged",
                        "div.govuk-summary-list__row", "How was meeting arranged", "dd.govuk-summary-list__value"))
                        .isVisible();
                break;
            case "inform victim about charging decision":
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting arranged",
                        "div.govuk-summary-list__row", "How was meeting arranged", "dd.govuk-summary-list__value"))
                        .isVisible();
                break;
            case "other":
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting arranged",
                        "div.govuk-summary-list__row", "How was meeting arranged", "dd.govuk-summary-list__value"))
                        .isVisible();
                break;
            default:
                System.out.println("Specified meeting type is not arranged");
        }

    }

    public void cancelMeeting(String meetingType) {
        waitForText("Communications");
        waitForElement(page, AriaRole.TAB, "Meetings");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Meetings");
        switch (meetingType.trim().toLowerCase()) {
            case "cps pre-trial meeting":
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting arranged",
                        "div.govuk-summary-list__row", "How was meeting arranged", "dd.govuk-summary-list__value"))
                        .isVisible();
                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                        "CPS pre-trial meeting arranged",
                        "button.govuk-button", "Cancel meeting")
                        .click();
                waitForText("When was the meeting cancelled?");
                break;
            case "inform victim about charging decision":
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting arranged",
                        "div.govuk-summary-list__row", "How was meeting arranged", "dd.govuk-summary-list__value"))
                        .isVisible();
                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                        "Charging decision meeting arranged",
                        "button.govuk-button", "Cancel meeting")
                        .click();
                waitForText("When was the meeting cancelled?");
                break;
            case "other":
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting arranged",
                        "div.govuk-summary-list__row", "How was meeting arranged", "dd.govuk-summary-list__value"))
                        .isVisible();
                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                        "Other CPS meeting arranged",
                        "button.govuk-button", "Cancel meeting")
                        .click();
                waitForText("When was the meeting cancelled?");
                break;
            default:
                System.out.println("Specified meeting type is not arranged");
        }
    }

    public void selectCancellationReason(String personFirstLastName, String cancellationReason) {
        switch (cancellationReason.trim().toLowerCase()) {
            case "victim withdrew":
                waitForText(personFirstLastName + " withdrew");
                getRadioButtonById("Withdrew").click();
                break;
            case "meeting created in error":
                waitForText("Meeting created in error");
                getRadioButtonById("Error").click();
                break;
            case "change of court date":
                waitForText("Change of court date");
                getRadioButtonById("DateChange").click();
                break;
            case "no longer required":
                waitForText("No longer required");
                getRadioButtonById("NoLongerRequired").click();
                break;
            case "other":
                waitForText("Other");
                getRadioButtonById("Other").click();
                waitForText("Provide details");
                enterTextById("b10-TextArea_TextEntry", "Other Reason");
                break;
            default:
                System.out.println("Specified cancellation reason type does not exist");
        }
    }

    public void verifyCancelledMeeting(String meetingType) {
        switch (meetingType.trim().toLowerCase()) {
            case "cps pre-trial meeting":
                waitUntilSpinnersAreGone("Loading, please wait");
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting cancelled",
                        "div.govuk-summary-list__row", "Date and time meeting was cancelled", "dd.govuk-summary-list__value"))
                        .isVisible();
                break;
            case "inform victim about charging decision":
                waitUntilSpinnersAreGone("Loading, please wait");
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting cancelled",
                        "div.govuk-summary-list__row", "Date and time meeting was cancelled", "dd.govuk-summary-list__value"))
                        .isVisible();
                break;
            case "other":
                waitUntilSpinnersAreGone("Loading, please wait");
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting cancelled",
                        "div.govuk-summary-list__row", "Date and time meeting was cancelled", "dd.govuk-summary-list__value"))
                        .isVisible();
                break;
            default:
                System.out.println("Specified meeting type is not arranged");
        }

    }

    public void verifyCancelledMeetingDetails(String personFirstLastName, String meetingType, String cancellationReason) {

        if ("Victim withdrew".equals(cancellationReason)) {
            cancellationReason = personFirstLastName + " withdrew";
        }
        if ("Other".equals(cancellationReason)) {
            cancellationReason = cancellationReason + " Reason";
        }
        switch (meetingType.trim().toLowerCase()) {
            case "cps pre-trial meeting":
                waitUntilSpinnersAreGone("Loading, please wait");
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting cancelled",
                        "div.govuk-summary-list__row", "Date and time meeting was cancelled", "dd.govuk-summary-list__value"))
                        .isVisible();
                String preTrialMeetingCancelDateTime = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting cancelled",
                        "div.govuk-summary-list__row", "Date and time meeting was cancelled", "dd.govuk-summary-list__value")
                        .innerText();
                String preTrialMeetingCancelDate = preTrialMeetingCancelDateTime.split(" at ")[0];
                assertContains(convertDateMonthYear(todayMinusNoOfDays(1)), preTrialMeetingCancelDate);
                String preTrialMeetingCancelReason = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting cancelled",
                        "div.govuk-summary-list__row", "Reason", "dd.govuk-summary-list__value")
                        .innerText();
                assertEquals(cancellationReason, preTrialMeetingCancelReason);
                break;
            case "inform victim about charging decision":
                waitUntilSpinnersAreGone("Loading, please wait");
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting cancelled",
                        "div.govuk-summary-list__row", "Date and time meeting was cancelled", "dd.govuk-summary-list__value"))
                        .isVisible();
                String chargeDecisionMeetingCancelDateTime = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting cancelled",
                        "div.govuk-summary-list__row", "Date and time meeting was cancelled", "dd.govuk-summary-list__value")
                        .innerText();
                String chargeDecisionMeetingCancelDate = chargeDecisionMeetingCancelDateTime.split(" at ")[0];
                assertContains(convertDateMonthYear(todayMinusNoOfDays(1)), chargeDecisionMeetingCancelDate);
                String chargeDecisionMeetingCancelReason = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting cancelled",
                        "div.govuk-summary-list__row", "Reason", "dd.govuk-summary-list__value")
                        .innerText();
                assertEquals(cancellationReason, chargeDecisionMeetingCancelReason);
                break;
            case "other":
                waitUntilSpinnersAreGone("Loading, please wait");
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting cancelled",
                        "div.govuk-summary-list__row", "Date and time meeting was cancelled", "dd.govuk-summary-list__value"))
                        .isVisible();
                String otherMeetingCancelDateTime = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting cancelled",
                        "div.govuk-summary-list__row", "Date and time meeting was cancelled", "dd.govuk-summary-list__value")
                        .innerText();
                String otherMeetingCancelDate = otherMeetingCancelDateTime.split(" at ")[0];
                assertContains(convertDateMonthYear(todayMinusNoOfDays(1)), otherMeetingCancelDate);
                String otherMeetingCancelReason = readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting cancelled",
                        "div.govuk-summary-list__row", "Reason", "dd.govuk-summary-list__value")
                        .innerText();
                assertEquals(cancellationReason, otherMeetingCancelReason);
                break;
            default:
                System.out.println("Specified meeting type does not exist");
        }
    }

    public void verifyArrangedMeetingDetails(String personFirstLastName, String meetingType, String meetingRequest, String meetingMethod, String locationName, String chairPerson) {

        String meetingRequestType = null;
        if ("CPS Offered".equals(meetingRequest)) {
            meetingRequestType = "CPS offered and " + personFirstLastName + " accepted";
        } else if ("Victim Requested".equals(meetingRequest)) {
            meetingRequestType = personFirstLastName + " requested it";
        } else if ("Third party requested".equals(meetingRequest)) {
            meetingRequestType = "Requested by a third party";
        }

        chairPerson = "(Meeting lead) " + chairPerson;
        waitUntilSpinnersAreGone("Loading, please wait");

        switch (meetingType.trim().toLowerCase()) {
            case "cps pre-trial meeting":
                meetingType = "CPS pre-trial meeting";
                assertValues(meetingType, meetingRequestType, meetingMethod, locationName, chairPerson);
                break;
            case "inform victim about charging decision":
                meetingType = "Charging decision meeting";
                assertValues(meetingType, meetingRequestType, meetingMethod, locationName, chairPerson);
                break;
            case "other":
                meetingType = "Other CPS meeting";
                assertValues(meetingType, meetingRequestType, meetingMethod, locationName, chairPerson);
                break;
            default:
                System.out.println("Specified meeting type does not exist");
        }

    }

    public void assertValues(String meetingType, String meetingRequestType, String meetingMethod, String locationName, String chairPerson) {
        assertThat(readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " arranged",
                "div.govuk-summary-list__row", "Date and time", "dd.govuk-summary-list__value"))
                .isVisible();

        String meetingsArrangedDateTime = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " arranged",
                "div.govuk-summary-list__row", "Date and time", "dd.govuk-summary-list__value")
                .innerText();
        String meetingArrangedDate = meetingsArrangedDateTime.split(" at ")[0];
        assertContains(convertDateMonthYear(todayMinusThreeDays()), meetingArrangedDate);

        String meetingsRequestBy = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " arranged",
                "div.govuk-summary-list__row", "How was meeting arranged", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(meetingRequestType, meetingsRequestBy);

        String meetingsMethod = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " arranged",
                "div.govuk-summary-list__row", "How meeting will take place", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(meetingMethod, meetingsMethod);

        if (!"Virtual call".equalsIgnoreCase(locationName)) {
            String meetingsLocation = readTableRowCell(page,
                    "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " arranged",
                    "div.govuk-summary-list__row", "Location", "dd.govuk-summary-list__value")
                    .innerText();
            assertEquals(locationName, meetingsLocation);
        }

//        String meetingsAttendees = readTableRowCell(page,
//                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " arranged",
//                "div.govuk-summary-list__row", "Attendees", "dd.govuk-summary-list__value")
//                .innerText();
//        System.out.println("Meeting Inner Text in Attendees = "+meetingsAttendees);
//        assertContains(chairPerson, meetingsAttendees);

    }

    public void selectArrangedMeetingType(String meetingType) {
        switch (meetingType.trim().toLowerCase()) {
            case "cps pre-trial meeting":
                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                        "CPS pre-trial meeting arranged",
                        "button.govuk-button", "Log meeting outcome")
                        .click();
                break;
            case "inform victim about charging decision":
                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                        "Charging decision meeting arranged",
                        "button.govuk-button", "Log meeting outcome")
                        .click();
                break;
            case "other":
                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                        "Other CPS meeting arranged",
                        "button.govuk-button", "Log meeting outcome")
                        .click();
                break;
            default:
                System.out.println("Specified meeting type does not exist");
        }
    }


    public void logArrangedMeetingTime(String meetingTookPlace) {
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText("Did the meeting take place?");

        if ("Yes".equalsIgnoreCase(meetingTookPlace)) {
            getRadioButtonById("RadioButton3-input").click();
        } else {
            System.out.println("Meeting took place is entered as NO ");
        }
        getButtonByName("Continue").click();
        waitForText("How long did the meeting last?");
        enterTextInLocator("#Input_Hour", utcHourNow());
        enterTextInLocator("#Input_Minutes", utcMinuteNow());
        getButtonByName("Continue").click();
        waitForText("Who attended the meeting?");
    }

    public void logArrangedMeetingAttendeesLedBy(String personFirstLastName, List<String> meetingAttendeesRoles, String meetingLedBy) {
        waitForText("Who attended the meeting?");

        for (String role : meetingAttendeesRoles) {
            switch (role.trim().toLowerCase()) {
                case "defence firm":
                    selectCheckboxContainText("Defence firm");
                case "defence solicitor":
                    selectCheckboxContainText("Defence Solicitor");
                case "officer in charge":
                    selectCheckboxContainText("Officer in Charge");
                case "victim liaison officer":
                    selectCheckboxContainText("Victim Liaison Officer");
                case "counsel":
                    selectCheckboxContainText("Counsel");
                case "victim":
                    selectCheckboxContainText("Victim (" + personFirstLastName + ")");
            }
        }
        getButtonByName("Continue").click();
        waitForText("Who led the meeting?");
        checkRadioContainName(meetingLedBy);
        getButtonByName("Continue").click();
        waitForText("Did " + personFirstLastName + " agree to be contacted for research?");
    }

    public void logArrangedMeetingAction(String personFirstLastName, String agreeToResearch, String meetingNotes,
                                         String meetingNotesSent, String actionsAgreed) {
        waitForText("Did " + personFirstLastName + " agree to be contacted for research?");

        if ("Yes".equalsIgnoreCase(agreeToResearch)) {
            getRadioButtonById("ContactForResearch_Yes").click();
        } else if ("No".equalsIgnoreCase(agreeToResearch)) {
            getRadioButtonById("ContactForResearch_No").click();
        } else if ("Question Not Asked".equalsIgnoreCase(agreeToResearch)) {
            getRadioButtonById("ContactForResearch_DidNotAsk").click();
        }
        getButtonByName("Continue").click();
        waitForText("Have the meeting notes been sent to the Officer in Charge (OIC)?");

        if ("Yes".equalsIgnoreCase(meetingNotes)) {
            getRadioButtonById("NotesToOIC_Yes").click();
        } else if ("No".equalsIgnoreCase(meetingNotes)) {
            getRadioButtonById("NotesToOIC_No").click();
        }
        getButtonByName("Continue").click();
        waitForText("Have the meeting notes been sent to " + personFirstLastName + "?");

        if ("Yes".equalsIgnoreCase(meetingNotes)) {
            getRadioButtonById("NotesToVictim_Yes").click();
        } else if ("No".equalsIgnoreCase(meetingNotes)) {
            getRadioButtonById("NotesToVictim_No").click();
        }
        getButtonByName("Continue").click();
        waitForText("Were any actions agreed?");

        if ("Yes".equalsIgnoreCase(meetingNotes)) {
            getRadioButtonById("Yes").click();
            waitForText("What actions were agreed?");
            enterTextById("b38-TextArea_TextEntry", "Action Agreed Data Entered");
        } else if ("No".equalsIgnoreCase(meetingNotes)) {
            getRadioButtonById("No").click();
        }
        getButtonByName("Continue").click();
        waitForText("Check your answers before confirming details");
    }

    public void confirmLogArrangedMeetingDetails() {
        waitForText("Check your answers before confirming details");
        getButtonByName("Log meeting outcome").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText("Communications");
        waitForElement(page, AriaRole.TAB, "Meetings");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Meetings");
    }

    public void verifyMeetingOutcome(String meetingType) {
        switch (meetingType.trim().toLowerCase()) {
            case "cps pre-trial meeting":
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting outcome logged",
                        "div.govuk-summary-list__row", "Duration of the meeting", "dd.govuk-summary-list__value"))
                        .isVisible();
                break;
            case "inform victim about charging decision":
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting outcome logged",
                        "div.govuk-summary-list__row", "Duration of the meeting", "dd.govuk-summary-list__value"))
                        .isVisible();
                break;
            case "other":
                assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting outcome logged",
                        "div.govuk-summary-list__row", "Duration of the meeting", "dd.govuk-summary-list__value"))
                        .isVisible();
                break;
            default:
                System.out.println("Specified meeting type is not arranged");
        }

    }


}