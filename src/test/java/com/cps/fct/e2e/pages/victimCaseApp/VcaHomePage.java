package com.cps.fct.e2e.pages.victimCaseApp;

import com.cps.fct.e2e.pages.AppsLoginPage;
import com.cps.fct.e2e.pages.BasePage;
import com.cps.fct.e2e.pages.PageObjects;
import com.cps.fct.e2e.pages.caseReviewApp.LoginPage;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class VcaHomePage extends BasePage {

    private ScenarioContext context;

    public VcaHomePage(PlaywrightContext context) {
        super(context);
    }

    private static final Logger logger =
            LoggerFactory.getLogger(VcaHomePage.class);

    private static final int LOGIN_TIMEOUT_MILLIS = 25_000;

    //        page.pause();

    public void searchVictimCase(String caseUrn) {
        getRadioButtonById("RadioButtonCaseRef-input").click();
        enterText("Case reference", caseUrn);
        getButtonByName("Search").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForTextToAppear(caseUrn);
    }


    public String getVictimFullName(String caseUrn, String victimWitnessName) {
        waitForTextToAppear(caseUrn);
        String victimWitnessFullName = victimWitnessName.substring(1, victimWitnessName.indexOf(",")).toUpperCase()
                + victimWitnessName.substring(victimWitnessName.indexOf(","), victimWitnessName.length() - 1);
        waitForTextToAppear(victimWitnessFullName);
        System.out.println(victimWitnessFullName);
        return victimWitnessFullName;
    }

    public String getVictimSurname(String victimWitnessFullName) {
        String victimWitnessSurname = victimWitnessFullName.substring(0, victimWitnessFullName.indexOf(",")).trim();
        victimWitnessSurname = victimWitnessSurname.substring(0, 1).toUpperCase()
                + victimWitnessSurname.substring(1).toLowerCase();
        return victimWitnessSurname;
    }

    public String getVictimFirstname(String victimWitnessFullName) {

        String victimWitnessFirstName = victimWitnessFullName.substring(victimWitnessFullName.indexOf(",")+1).trim();
        victimWitnessFirstName = victimWitnessFirstName.substring(0, 1).toUpperCase()
                + victimWitnessFirstName.substring(1).toLowerCase();
        return victimWitnessFirstName;
    }



    public void onboardVictim(String PersonFullName, String service) {
        onboardPage(PersonFullName);
        selectServiceLead(service);
        clickButton("Save and continue");
        verifyOnboard(PersonFullName);
        clickButton("Confirm details");
        logger.info("Victim VCA");
    }

    private void onboardPage(String PersonFullName) {
        String victimSurname = PersonFullName.substring(0, PersonFullName.indexOf(",")).trim();
        Locator onboardLink = page.getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Onboard onboard " + victimSurname + ",")
        );
        onboardLink.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
        onboardLink.click();
    }

    private void selectServiceLead(String serviceLead) {
        if (Objects.equals(serviceLead, "Not aligned")) {
            serviceLead = "Not aligned to a service";
        }
        Locator selectServiceLead = page.getByRole(
                AriaRole.RADIO,
                new Page.GetByRoleOptions().setName(serviceLead));
        selectServiceLead.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
        selectServiceLead.click();

    }

    private void verifyOnboard(String PersonFullName) {
        String victimFirstname = PersonFullName.substring(PersonFullName.indexOf(",") + 1).trim();
        assertThat(page.getByText(
                Pattern.compile("Success\\s*You onboarded " + victimFirstname)))
                .isVisible();
    }

    public void selectNextTaskForVictim(String taskType) {
        waitForText("What is the next task for");
        Locator selectTask = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName(taskType));
        selectTask.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        selectTask.click();
        clickButton("Continue");
        waitForText("Check the task details");
        waitForButtonToAppear("Confirm and create").click();

    }

    public void verifyVictimOnboard(String victimFullName) {
        System.out.println(victimFullName);
        waitForTextToAppear("Victim onboarded");
        waitForButtonToAppear("Continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        assertThat(page.getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions().setName(victimFullName))).isVisible();
    }

    public void assignVloToVictim(String vloName, String personFullName) {
        assertThat(page.getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions().setName(personFullName))).isVisible();
        clickOnChangeForVLO();
        selectVloFromList(vloName);
        waitForButtonToAppear("Save and continue").click();
        assertThat(page.getByText(
                Pattern.compile("Success\\s*You changed the victim")))
                .isVisible();
    }

    public void verifyAssignedVlo(String vloName) {
        Locator vlOfficerRow = page.locator(".govuk-summary-list__row")
                .filter(new Locator.FilterOptions().setHas(page.locator("dt.govuk-summary-list__key")
                                .filter(new Locator.FilterOptions().setHasText("Victim liaison officer"))));

        assertThat(vlOfficerRow).containsText(vloName);
    }


    public void clickOnChangeForVLO() {

        Locator officerRow = page.locator(".govuk-summary-list__row")
                .filter(new Locator.FilterOptions().setHas(page.locator("dt.govuk-summary-list__key")
                                .filter(new Locator.FilterOptions().setHasText("Victim liaison officer")))
                        .setHasText("Unassigned"));

        assertThat(officerRow).containsText("Unassigned");
        officerRow.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Change"))
                .click();
    }

    public void selectVloFromList(String vloName) {
        Locator searchInput = page.locator("#b7-Input_SearchText");
        searchInput.clear();
        searchInput.fill(vloName);
        Locator option = page.getByRole(AriaRole.OPTION,
                new Page.GetByRoleOptions().setName(Pattern.compile(".*, "+vloName+".*"))
        );
        assertThat(option).isVisible();
        option.click();
    }


    public void getVictimWitnessFirstLastName( String victimWitnessFullName){


        String victimWitnessSurname = victimWitnessFullName.substring(0, victimWitnessFullName.indexOf(",")).trim();
        String victimWitnessFirstName = victimWitnessFullName.substring(victimWitnessFullName.indexOf(",")+1).trim();

        victimWitnessSurname = victimWitnessSurname.substring(0, 1).toUpperCase()
                + victimWitnessSurname.substring(1).toLowerCase();

        victimWitnessFirstName = victimWitnessFirstName.substring(0, 1).toUpperCase()
                + victimWitnessFirstName.substring(1).toLowerCase();

    }





    public void verifyAssignVloSuccessMessage(String vloName, String victimFullName ){
        Locator message = page.locator("#b4-MessageText");
        assertThat(message).containsText("victim liaison officer");
        assertThat(message).containsText(vloName);
        assertThat(message).containsText(victimFullName);

    }

}
