package com.cps.fct.e2e.pages.victimCaseApp;

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

//    private ScenarioContext context;

    public VcaHomePage(PlaywrightContext context) {
        super(context);
    }

    private static final Logger logger =
            LoggerFactory.getLogger(VcaHomePage.class);

    private static final int LOGIN_TIMEOUT_MILLIS = 25_000;

    public void searchVictimCase(String caseUrn) {
        getRadioButtonById("RadioButtonCaseRef-input").click();
        enterText("Case reference", caseUrn);
        getButtonByName("Search").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForTableToAppear(page,"div.govuk-summary-list",caseUrn);
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
        String victimWitnessFirstName = victimWitnessFullName.substring(victimWitnessFullName.indexOf(",") + 1).trim();
        victimWitnessFirstName = victimWitnessFirstName.substring(0, 1).toUpperCase()
                + victimWitnessFirstName.substring(1).toLowerCase();
        return victimWitnessFirstName;
    }

    public void onboardVictim(String personFullName, String service) {
        waitUntilSpinnersAreGone("Loading, please wait");
        navigateToOnboardPage(personFullName);
        selectServiceLead(service);
        clickButton("Save and continue");
        verifyOnboard(personFullName);
        clickButton("Confirm details");
        logger.info("Victim VCA");
    }

    public void navigateToOnboardPage(String personFullName) {
        Locator summaryList = page.locator("div.govuk-summary-list")
                .filter(new Locator.FilterOptions()
                .setHas(page.locator("h2")
                .getByText(personFullName,
                        new Locator.GetByTextOptions().setExact(true))));
        Locator serviceRow = summaryList.locator("div.govuk-summary-list__row")
                .filter(new Locator.FilterOptions()
                        .setHas(page.locator("dt.govuk-summary-list__key")
                                .getByText("Service",
                                        new Locator.GetByTextOptions().setExact(true))));
                serviceRow.getByRole(AriaRole.LINK,
                new Locator.GetByRoleOptions().setName("Onboard onboard "+personFullName).setExact(true)
        ).click();
        waitUntilSpinnersAreGone("Loading, please wait");
    }

    public void selectServiceLead(String serviceLead) {
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

    public void verifyOnboard(String PersonFullName) {
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
        clickOnChangeForVLO(personFullName);
        selectVloFromList(vloName);
        waitForButtonToAppear("Save and continue").click();
        assertThat(page.getByText(
                Pattern.compile("Success\\s*You changed the victim")))
                .isVisible();
    }

    public void verifyAssignedVlo(String vloName, String personFullName) {

        Locator summaryList = page.locator("div.govuk-summary-list")
                .filter(new Locator.FilterOptions()
                        .setHas(page.locator("h2")
                                .getByText(personFullName,
                                        new Locator.GetByTextOptions().setExact(true))));
        Locator victimLiaisonOfficerRow = summaryList.locator("div.govuk-summary-list__row")
                .filter(new Locator.FilterOptions()
                        .setHas(page.locator("dt.govuk-summary-list__key")
                                .getByText("Victim liaison officer",
                                        new Locator.GetByTextOptions().setExact(true))));
        assertThat(victimLiaisonOfficerRow).containsText(vloName);
    }


    public void clickOnChangeForVLO(String personFullName) {
        Locator summaryList = page.locator("div.govuk-summary-list")
                .filter(new Locator.FilterOptions()
                        .setHas(page.locator("h2")
                                .getByText(personFullName,
                                        new Locator.GetByTextOptions().setExact(true))));
        Locator victimLiaisonOfficerRow = summaryList.locator("div.govuk-summary-list__row")
                .filter(new Locator.FilterOptions()
                        .setHas(page.locator("dt.govuk-summary-list__key")
                                .getByText("Victim liaison officer",
                                        new Locator.GetByTextOptions().setExact(true))));
        victimLiaisonOfficerRow.getByRole(AriaRole.LINK,
                new Locator.GetByRoleOptions().setName("Change").setExact(true)
        ).click();
        waitUntilSpinnersAreGone("Loading, please wait");
    }

    public void selectVloFromList(String vloName) {
        Locator searchInput = page.locator("#b7-Input_SearchText");
        searchInput.clear();
        searchInput.fill(vloName);
        Locator option = page.getByRole(AriaRole.OPTION,
                new Page.GetByRoleOptions().setName(Pattern.compile(".*, " + vloName + ".*"))
        );
        assertThat(option).isVisible();
        option.click();
    }

    public void navigateVictimHomePage() {
        clickLinkByName("Victims");
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText("Victims");
    }

    public void navigateVictimDetailsTab(String caseUrn, String victimWitnessFullName) {
        waitUntilSpinnersAreGone("Loading, please wait");
        clickLinkByName(victimWitnessFullName);
        waitForHeadingToAppear(victimWitnessFullName);
        waitForText("Case URN: " + caseUrn);
        clickTabByName("Victim Details");
        waitUntilSpinnersAreGone("Loading, please wait");
    }

    public void navigateCaseContactsTab(String caseUrn, String victimWitnessFullName) {
        waitUntilSpinnersAreGone("Loading, please wait");
        clickLinkByName(victimWitnessFullName);
        waitForHeadingToAppear(victimWitnessFullName);
        waitForText("Case URN: " + caseUrn);
        clickTabByName("Case Contacts");
        waitUntilSpinnersAreGone("Loading, please wait");
    }

    public void navigateCommunicationsTab(String caseUrn, String victimWitnessFullName) {
        waitUntilSpinnersAreGone("Loading, please wait");
        clickLinkByName(victimWitnessFullName);
        waitForHeadingToAppear(victimWitnessFullName);
        waitForText("Case URN: " + caseUrn);
        clickTabByName("Communications");
        waitUntilSpinnersAreGone("Loading, please wait");
    }


}
