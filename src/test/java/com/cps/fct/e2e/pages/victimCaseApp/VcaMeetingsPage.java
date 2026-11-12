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

    public String getMeetingType(String meetingType) {
        switch (meetingType.trim().toLowerCase()) {
            case "cps pre-trial meeting":
                meetingType = "CPS pre-trial meeting";
                break;
            case "inform victim about charging decision":
                meetingType = "Charging decision meeting";
                break;
            case "other":
                meetingType = "Other CPS meeting";
                break;
            default:
                System.out.println("Specified meeting type does not exist");
        }
        return meetingType;
    }

    public void assertMeetingNotOffered(String meetingType, String notOfferedReason) {
        String actualMeetingOffered = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType,
                "div.govuk-summary-list__row", "Has a meeting been offered", "dd.govuk-summary-list__value")
                .innerText();
        String actualMeetingOfferedReason = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType,
                "div.govuk-summary-list__row", "Why was a meeting not offered", "dd.govuk-summary-list__value")
                .innerText();

        assertEquals("No", actualMeetingOffered);
        assertEquals(notOfferedReason, actualMeetingOfferedReason);
    }

    public void selectMeetingOfferedResponse(String meetingType, String meetingResponse) {
        waitUntilSpinnersAreGone("Loading, please wait");
        selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                meetingType + " offered",
                "button.govuk-button", meetingResponse)
                .click();
    }

    public void verifyOfferedAcceptanceResponse(String meetingType) {
        assertThat(readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " accepted",
                "div.govuk-summary-list__row", "How was meeting accepted", "dd.govuk-summary-list__value"))
                .isVisible();
    }

    public void verifyOfferedDeclinedResponse(String meetingType) {
        assertThat(readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " declined",
                "div.govuk-summary-list__row", "How was meeting declined", "dd.govuk-summary-list__value"))
                .isVisible();
    }

    public void verifyOfferedNoResponse(String meetingType) {
        assertThat(readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to " + meetingType + " offer",
                "div.govuk-summary-list__row", "Response", "dd.govuk-summary-list__value"))
                .isVisible();
    }

    public void assertOfferAcceptResponse(String meetingType, String meetingResponseMethod, String offerMethod) {
        String actualMeetingAcceptResponseMethod = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " accepted",
                "div.govuk-summary-list__row", "How was meeting accepted", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(meetingResponseMethod, actualMeetingAcceptResponseMethod);
        String actualMeetingAcceptResponseDate = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " accepted",
                "div.govuk-summary-list__row", "Date meeting was accepted", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(convertDateAs0DMonthYear(todayMinusNoOfDays(4)), actualMeetingAcceptResponseDate);
        Locator meetingOffered = selectTableLink(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " accepted",
                "summary.govuk-details__summary",
                "Offered meetings"
        );
        meetingOffered.click();
        assertOfferDetails(meetingType, offerMethod);
    }

    public void assertOfferDeclineResponse(String meetingType, String meetingResponseMethod, String offerMethod) {
        String actualMeetingDeclineMethod = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " declined",
                "div.govuk-summary-list__row", "How was meeting declined", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(meetingResponseMethod, actualMeetingDeclineMethod);
        String actualMeetingDeclineDate = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " declined",
                "div.govuk-summary-list__row", "Date meeting was declined", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(convertDateAs0DMonthYear(todayMinusFourDays()), actualMeetingDeclineDate);
        Locator meetingOffered = selectTableLink(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " declined",
                "summary.govuk-details__summary",
                "Offered meetings"
        );
        meetingOffered.click();
        assertOfferDetails(meetingType, offerMethod);
    }

    public void assertOfferNoResponse(String meetingType, String meetingResponseMethod, String offerMethod) {
        String actualMeetingNoResponse = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to "+ meetingType + " offer",
                "div.govuk-summary-list__row", "Response", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals("No", actualMeetingNoResponse);

        Locator meetingOffered = selectTableLink(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", "No response to "+ meetingType + " offer",
                "summary.govuk-details__summary",
                "Offered meetings"
        );
        meetingOffered.click();
        assertOfferDetails(meetingType, offerMethod);
    }

    public void assertOfferDetails(String meetingType, String offerMethod) {
        assertThat(readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " offered",
                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value"))
                .isVisible();
        String actualMeetingOfferedMethod = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " offered",
                "div.govuk-summary-list__row", "How was meeting offered", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(offerMethod, actualMeetingOfferedMethod);
        String actualMeetingOfferedDate = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " offered",
                "div.govuk-summary-list__row", "Date meeting was offered", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(convertDateAs0DMonthYear(todayMinusFiveDays()), actualMeetingOfferedDate);
    }

    public void selectAcceptedMeetingType(String meetingType) {
        selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                meetingType+" accepted",
                "button.govuk-button", "Confirm arranged meetings")
                .click();
    }

    public void selectMeetingSource(String personFirstLastName, String meetingSource) {
        waitForText("How was the meeting arranged?");
        switch (meetingSource.trim().toLowerCase()) {
            case "victim requested":
                clickSelectOnAriaRole(page, AriaRole.RADIO, personFirstLastName + " requested it");
                break;
            case "cps offered":
                clickSelectOnAriaRole(page, AriaRole.RADIO, "CPS offered and " + personFirstLastName + " accepted");
                break;
            case "third party requested":
                getRadioButtonById("MeetingSource_ThirdPartyRequested").click();
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

    public void selectMeetingChairPerson(String chairPerson){
        switch (chairPerson.trim().toLowerCase()) {
            case "defence firm":
                selectRadioContainName("Defence firm (");
                break;
            case "defence solicitor":
                selectRadioContainName("Defence Solicitor (");
                  break;
            case "officer in charge":
                selectRadioContainName("Officer in Charge (");
                break;
            case "victim liaison officer":
                selectRadioContainName("Victim Liaison Officer");
                break;
            case "counsel":
                selectRadioContainName("Counsel");
                break;
            case "victim":
                selectRadioContainName("Victim (");
                break;
            default:
                System.out.println("Meeting attendees role does not exist");
        }
    }

    public void verifyArrangedMeeting(String meetingType) {
                        assertThat(readTableRowCell(page,
                        "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType+" arranged",
                        "div.govuk-summary-list__row", "How was meeting arranged", "dd.govuk-summary-list__value"))
                        .isVisible();
    }

    public void cancelMeeting(String meetingType) {
        waitForText("Communications");
        waitForElement(page, AriaRole.TAB, "Meetings");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Meetings");

        assertThat(readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType+" arranged",
                "div.govuk-summary-list__row", "How was meeting arranged", "dd.govuk-summary-list__value"))
                .isVisible();
        selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                meetingType+" arranged",
                "button.govuk-button", "Cancel meeting")
                .click();
        waitForText("When was the meeting cancelled?");

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
        waitUntilSpinnersAreGone("Loading, please wait");
        assertThat(readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType+" cancelled",
                "div.govuk-summary-list__row", "Date and time meeting was cancelled", "dd.govuk-summary-list__value"))
                .isVisible();
    }

    public void verifyCancelledMeetingDetails(String personFirstLastName, String meetingType, String cancellationReason) {

        if ("Victim withdrew".equals(cancellationReason)) {
            cancellationReason = personFirstLastName + " withdrew";
        }
        if ("Other".equals(cancellationReason)) {
            cancellationReason = cancellationReason + " Reason";
        }

        waitUntilSpinnersAreGone("Loading, please wait");
        assertThat(readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType+" cancelled",
                "div.govuk-summary-list__row", "Date and time meeting was cancelled", "dd.govuk-summary-list__value"))
                .isVisible();
        String actualMeetingCancelDateTime = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType+" cancelled",
                "div.govuk-summary-list__row", "Date and time meeting was cancelled", "dd.govuk-summary-list__value")
                .innerText();
        String actualMeetingCancelDate = actualMeetingCancelDateTime.split(" at ")[0];
        assertContains(convertDateMonthYear(todayMinusNoOfDays(1)), actualMeetingCancelDate);

        String actualMeetingCancelReason = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType+" cancelled",
                "div.govuk-summary-list__row", "Reason", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(cancellationReason, actualMeetingCancelReason);

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

        assertArrangedMeetingValues(meetingType, meetingRequestType, meetingMethod, locationName, chairPerson);

    }

    public void assertArrangedMeetingValues(String meetingType, String meetingRequestType, String meetingMethod, String locationName, String chairPerson) {
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

        selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
                meetingType+" arranged",
                "button.govuk-button", "Log meeting outcome")
                .click();

//        switch (meetingType.trim().toLowerCase()) {
//            case "cps pre-trial meeting":
//                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
//                        "CPS pre-trial meeting arranged",
//                        "button.govuk-button", "Log meeting outcome")
//                        .click();
//                break;
//            case "inform victim about charging decision":
//                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
//                        "Charging decision meeting arranged",
//                        "button.govuk-button", "Log meeting outcome")
//                        .click();
//                break;
//            case "other":
//                selectTableButton(page, "div.govuk-summary-card", "h2.govuk-summary-card__title",
//                        "Other CPS meeting arranged",
//                        "button.govuk-button", "Log meeting outcome")
//                        .click();
//                break;
//            default:
//                System.out.println("Specified meeting type does not exist");
//        }
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

    public void logArrangedMeetingAttendeesLedBy(String personFullName, String personFirstLastName, List<String> meetingAttendeesRoles, String meetingLedBy) {
        waitForText("Who attended the meeting?");
        System.out.println(meetingAttendeesRoles);
        for (String role : meetingAttendeesRoles) {
            switch (role.trim().toLowerCase()) {
                case "defence firm":
                    selectCheckboxContainText("Defence firm (");
                    break;
                case "defence solicitor":
                    selectCheckboxContainText("Defence Solicitor (");
                    break;
                case "officer in charge":
                    selectCheckboxContainText("Officer in Charge (");
                    break;
                case "victim liaison officer":
                    selectCheckboxContainText("Victim Liaison Officer");
                    break;
                case "counsel":
                    selectCheckboxContainText("Counsel");
                    break;
                case "victim":
                    selectCheckboxContainText("Victim (");
                    break;
                default:
                    System.out.println("Meeting attendees role does not exist");
            }
        }
        getButtonByName("Continue").click();
        waitForText("Who led the meeting?");
        if ("Victim".equalsIgnoreCase(meetingLedBy)) {
            selectRadioContainName("Victim (");
        } else if ("Victim Liaison Officer".equalsIgnoreCase(meetingLedBy)) {
            selectRadioContainName("Victim Liaison Officer");
        } else if ("Officer in Charge".equalsIgnoreCase(meetingLedBy)) {
            selectRadioContainName("Officer in Charge (");
        } else {
            selectRadioContainName(meetingLedBy);
        }
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

        if ("Yes".equalsIgnoreCase(meetingNotesSent)) {
            getRadioButtonById("NotesToVictim_Yes").click();
        } else if ("No".equalsIgnoreCase(meetingNotesSent)) {
            getRadioButtonById("NotesToVictim_No").click();
        }
        getButtonByName("Continue").click();
        waitForText("Were any actions agreed?");

        if ("Yes".equalsIgnoreCase(actionsAgreed)) {
            getRadioButtonById("Yes").click();
            waitForText("What actions were agreed?");
            enterTextById("b38-TextArea_TextEntry", "Action Agreed Data Entered");
        } else if ("No".equalsIgnoreCase(actionsAgreed)) {
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

        assertThat(readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType+" outcome logged",
                "div.govuk-summary-list__row", "Duration of the meeting", "dd.govuk-summary-list__value"))
                .isVisible();

//        switch (meetingType.trim().toLowerCase()) {
//            case "cps pre-trial meeting":
//                assertThat(readTableRowCell(page,
//                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "CPS pre-trial meeting outcome logged",
//                        "div.govuk-summary-list__row", "Duration of the meeting", "dd.govuk-summary-list__value"))
//                        .isVisible();
//                break;
//            case "inform victim about charging decision":
//                assertThat(readTableRowCell(page,
//                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Charging decision meeting outcome logged",
//                        "div.govuk-summary-list__row", "Duration of the meeting", "dd.govuk-summary-list__value"))
//                        .isVisible();
//                break;
//            case "other":
//                assertThat(readTableRowCell(page,
//                        "div.govuk-summary-card", "h2.govuk-summary-card__title", "Other CPS meeting outcome logged",
//                        "div.govuk-summary-list__row", "Duration of the meeting", "dd.govuk-summary-list__value"))
//                        .isVisible();
//                break;
//            default:
//                System.out.println("Specified meeting type is not arranged");
//        }
    }

    public void verifyLoggedMeetingDetails(String meetingType, String agreeToResearch, String meetingNotes,
                                           String meetingNotesSent, String actionsAgreed) {

        if ("Question Not Asked".equalsIgnoreCase(agreeToResearch)) {
            agreeToResearch = "Did not ask";
        }
        if ("Yes".equalsIgnoreCase(actionsAgreed)) {
            actionsAgreed = "Action Agreed Data Entered";
        }

        assertThat(readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " outcome logged",
                "div.govuk-summary-list__row", "Duration of the meeting", "dd.govuk-summary-list__value"))
                .isVisible();

        String actualAgreeToResearch = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " outcome logged",
                "div.govuk-summary-list__row", "Agreed to research", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(agreeToResearch, actualAgreeToResearch);

        String actualMeetingNotes = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " outcome logged",
                "div.govuk-summary-list__row", "Meeting notes to OIC", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(meetingNotes, actualMeetingNotes);

        String actualMeetingNotesSent = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " outcome logged",
                "div.govuk-summary-list__row", "Meeting notes to victim", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(meetingNotesSent, actualMeetingNotesSent);

        String actualActionsAgreed = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " outcome logged",
                "div.govuk-summary-list__row", "Actions agreed", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(actionsAgreed, actualActionsAgreed);

//        assertLoggedMeetingValues(meetingType, agreeToResearch, meetingNotes, meetingNotesSent, actionsAgreed);

//        switch (meetingType.trim().toLowerCase()) {
//            case "cps pre-trial meeting":
//                meetingType = "CPS pre-trial meeting";
//                assertLoggedMeetingValues(meetingType, agreeToResearch, meetingNotes, meetingNotesSent, actionsAgreed);
//                break;
//            case "inform victim about charging decision":
//                meetingType = "Charging decision meeting";
//                assertLoggedMeetingValues(meetingType, agreeToResearch, meetingNotes, meetingNotesSent, actionsAgreed);
//                break;
//            case "other":
//                meetingType = "Other CPS meeting";
//                assertLoggedMeetingValues(meetingType, agreeToResearch, meetingNotes, meetingNotesSent, actionsAgreed);
//                break;
//            default:
//                System.out.println("Specified meeting type does not exist");
//        }
    }

    public void assertLoggedMeetingValues(String meetingType, String agreeToResearch, String meetingNotes,
                                          String meetingNotesSent, String actionsAgreed) {
        if ("Question Not Asked".equalsIgnoreCase(agreeToResearch)) {
            agreeToResearch = "Did not ask";
        }
        if ("Yes".equalsIgnoreCase(actionsAgreed)) {
            actionsAgreed = "Action Agreed Data Entered";
        }

        assertThat(readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " outcome logged",
                "div.govuk-summary-list__row", "Duration of the meeting", "dd.govuk-summary-list__value"))
                .isVisible();

        String actualAgreeToResearch = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " outcome logged",
                "div.govuk-summary-list__row", "Agreed to research", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(agreeToResearch, actualAgreeToResearch);

        String actualMeetingNotes = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " outcome logged",
                "div.govuk-summary-list__row", "Meeting notes to OIC", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(meetingNotes, actualMeetingNotes);

        String actualMeetingNotesSent = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " outcome logged",
                "div.govuk-summary-list__row", "Meeting notes to victim", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(meetingNotesSent, actualMeetingNotesSent);

        String actualActionsAgreed = readTableRowCell(page,
                "div.govuk-summary-card", "h2.govuk-summary-card__title", meetingType + " outcome logged",
                "div.govuk-summary-list__row", "Actions agreed", "dd.govuk-summary-list__value")
                .innerText();
        assertEquals(actionsAgreed, actualActionsAgreed);
    }


}