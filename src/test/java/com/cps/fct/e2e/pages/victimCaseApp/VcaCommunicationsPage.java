package com.cps.fct.e2e.pages.victimCaseApp;

import com.cps.fct.e2e.pages.BasePage;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.options.AriaRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cps.fct.e2e.pages.*;

import java.util.*;
import java.util.regex.Pattern;

import static com.cps.fct.e2e.utils.common.DateTimeUtils.*;
import static com.cps.fct.e2e.utils.common.FakerUtils.*;
import static com.cps.fct.e2e.utils.common.FakerUtils.todayMinusThreeDays;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class VcaCommunicationsPage extends BasePage {

    private final ScenarioContext scenarioContext;

    private final VcaMeetingsPage vcaMeetingsPage;

    public VcaCommunicationsPage(PlaywrightContext context, ScenarioContext scenarioContext, VcaMeetingsPage vcaMeetingsPage) {
        super(context);
        this.scenarioContext = scenarioContext;
        this.vcaMeetingsPage = vcaMeetingsPage;
    }

    private static final int WAIT_FOR_MILLIS = 10_000;
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
        meetingType = vcaMeetingsPage.getMeetingType(meetingType);
        vcaMeetingsPage.assertMeetingNotOffered(meetingType, notOfferedReason);
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
        meetingType = vcaMeetingsPage.getMeetingType(meetingType);
        vcaMeetingsPage.selectMeetingOfferedResponse(meetingType,meetingResponse);
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
                vcaMeetingsPage.verifyOfferedAcceptanceResponse(meetingType);
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
                vcaMeetingsPage.verifyOfferedDeclinedResponse(meetingType);
                break;
            case "Log no response":
                waitUntilSpinnersAreGone("Loading, please wait");
                waitForText("Communications");
                waitForElement(page, AriaRole.TAB, "Meetings");
                vcaMeetingsPage.verifyOfferedNoResponse(meetingType);
                break;
            default:
                System.out.println("Specified meeting response does not exist");
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

        meetingType = vcaMeetingsPage.getMeetingType(meetingType);

        switch (meetingResponse) {
            case "Log meeting acceptance":
                waitUntilSpinnersAreGone("Loading, please wait");
                waitForText("Communications");
                waitForElement(page, AriaRole.TAB, "Meetings");
                vcaMeetingsPage.assertOfferAcceptResponse(meetingType, meetingResponseMethod, offerMethod);
                break;
            case "Log meeting declined":
                waitUntilSpinnersAreGone("Loading, please wait");
                waitForText("Communications");
                waitForElement(page, AriaRole.TAB, "Meetings");
                vcaMeetingsPage.assertOfferDeclineResponse(meetingType, meetingResponseMethod, offerMethod);
                break;
            case "Log no response":
                waitUntilSpinnersAreGone("Loading, please wait");
                waitForText("Communications");
                waitForElement(page, AriaRole.TAB, "Meetings");
                vcaMeetingsPage.assertOfferNoResponse(meetingType, offerMethod);
                break;
            default:
                System.out.println("Specified response type does not exist");
        }
    }

    public void arrangeMeetings(String personFirstLastName, String meetingType, String meetingSource,
                                String meetingMethod, String locationType, String locationName) {
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText("Communications");
        waitForElement(page, AriaRole.TAB, "Meetings");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Meetings");
        meetingType = vcaMeetingsPage.getMeetingType(meetingType);
        vcaMeetingsPage.selectAcceptedMeetingType(meetingType);
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText("How was the meeting arranged?");
        vcaMeetingsPage.selectMeetingSource(personFirstLastName, meetingSource);
        getButtonByName("Continue").click();
        waitForText("When will the meeting take place?");
        enterTextByLocator(page.locator("#b10-Input_Date"), todayMinusThreeDays());
        enterTextByLocator(page.locator("#Input_Hour"), utcHourNow());
        enterTextByLocator(page.locator("#Input_Minutes"), utcMinuteNow());
        getButtonByName("Continue").click();
        waitForText("How will the meeting take place?");
        vcaMeetingsPage.selectMeetingMethod(meetingMethod, locationType, locationName);
        getButtonByName("Continue").click();
        waitForText("Who should attend the meeting?");
    }

    public void selectAttendeesAndChairperson(List<String> meetingAttendeesRoles, String chairPerson) {
        waitForText("Who should attend the meeting?");
        vcaMeetingsPage.selectMeetingAttendees(meetingAttendeesRoles);
        waitForText("Who will lead the meeting?");
        vcaMeetingsPage.selectMeetingChairPerson(chairPerson);
        getButtonByName("Continue").click();
        waitForText("Support needs");
    }

    public void selectSupportNeeds(List<String> meetingSupportNeeds, String personFirstLastName) {
        waitForText("Support needs");
        boolean needsInterpreter = meetingSupportNeeds != null &&
                meetingSupportNeeds.stream()
                        .anyMatch(support -> support.equalsIgnoreCase("Interpreter"));
        boolean needsSomeoneToAttend = meetingSupportNeeds != null &&
                meetingSupportNeeds.stream()
                        .anyMatch(support -> support.equalsIgnoreCase("Someone to attend"));

        waitForText("Will " + personFirstLastName + " need an interpreter?");
        if (needsInterpreter) {
            getRadioButtonById("RadioButtonYes-input").click();
        } else {
            getRadioButtonById("RadioButtonNo-input").click();
        }

        waitForText("Will " + personFirstLastName + " need someone to attend for support?");
        if (needsSomeoneToAttend) {
            getRadioButtonById("RadioButtonYes2-input").click();
        } else {
            getRadioButtonById("RadioButtonNo2-input").click();
        }

        enterText(
                "Disability support or adjustment needs (optional)",
                "Disability support Text" );
        getButtonByName("Continue").click();
        waitForText("Check your answers before confirming details");
    }


    public void confirmArrangedMeeting(String meetingType) {
        waitForText("Check your answers before confirming details");
        getButtonByName("Confirm details").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText("Communications");
        waitForElement(page, AriaRole.TAB, "Meetings");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Meetings");
        meetingType = vcaMeetingsPage.getMeetingType(meetingType);
        vcaMeetingsPage.verifyArrangedMeeting(meetingType);
    }

    public void cancelArrangedMeeting(String personFirstLastName, String meetingType, String cancellationReason){
        waitUntilSpinnersAreGone("Loading, please wait");
        meetingType = vcaMeetingsPage.getMeetingType(meetingType);
        vcaMeetingsPage.cancelMeeting(meetingType);
        enterTextInLocator("#b6-Input_Date",todayMinusNoOfDays(1));
        enterTextInLocator("#Input_Hour",utcHourNow());
        enterTextInLocator("#Input_Minutes",utcMinuteNow());
        getButtonByName("Continue").click();
        waitForText("What is the reason for cancelling the meeting?");
        vcaMeetingsPage.selectCancellationReason(personFirstLastName,cancellationReason);
        getButtonByName("Continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        waitUntilTabAppear("Communications");
        waitUntilSpinnersAreGone("Loading, please wait");
    }

    public void confirmCancelledMeeting(String meetingType){
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForElement(page, AriaRole.TAB, "Meetings");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Meetings");
        navigateMeetings();
        meetingType = vcaMeetingsPage.getMeetingType(meetingType);
        vcaMeetingsPage.verifyCancelledMeeting(meetingType);
    }

    public void verifyCancelledMeetingDetails(String personFirstLastName, String meetingType, String cancellationReason){
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForElement(page, AriaRole.TAB, "Meetings");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Meetings");
        meetingType = vcaMeetingsPage.getMeetingType(meetingType);
        vcaMeetingsPage.verifyCancelledMeetingDetails(personFirstLastName, meetingType,cancellationReason);

    }

    public void verifyArrangedMeetingDetails(String personFirstLastName, String meetingType, String meetingRequest, String meetingMethod, String locationName, String chairPerson){
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForElement(page, AriaRole.TAB, "Meetings");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Meetings");
        meetingType = vcaMeetingsPage.getMeetingType(meetingType);
        vcaMeetingsPage.verifyArrangedMeetingDetails(personFirstLastName, meetingType, meetingRequest, meetingMethod, locationName, chairPerson);
    }

    public void conductArrangedMeeting(String personFullName, String personFirstLastName, String meetingType, String meetingTookPlace,
                                       List<String> meetingAttendeesRoles, String meetingLedBy, String agreeToResearch, String meetingNotes,
                                       String meetingNotesSent, String actionsAgreed){

        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText("Communications");
        waitForElement(page, AriaRole.TAB, "Meetings");
        clickSelectOnAriaRole(page, AriaRole.TAB, "Meetings");
        meetingType = vcaMeetingsPage.getMeetingType(meetingType);
        vcaMeetingsPage.selectArrangedMeetingType(meetingType);
        vcaMeetingsPage.logArrangedMeetingTime(meetingTookPlace);
        vcaMeetingsPage.logArrangedMeetingAttendeesLedBy(personFullName,personFirstLastName,meetingAttendeesRoles, meetingLedBy);
        vcaMeetingsPage.logArrangedMeetingAction(personFirstLastName, agreeToResearch, meetingNotes, meetingNotesSent, actionsAgreed);
        vcaMeetingsPage.confirmLogArrangedMeetingDetails();
        vcaMeetingsPage.verifyMeetingOutcome(meetingType);
    }


    public void verifyLoggedMeeting(String meetingType, String agreeToResearch, String meetingNotes,
                                    String meetingNotesSent, String actionsAgreed){
        meetingType = vcaMeetingsPage.getMeetingType(meetingType);
        vcaMeetingsPage.verifyLoggedMeetingDetails(meetingType, agreeToResearch, meetingNotes, meetingNotesSent, actionsAgreed);
    }

}
















