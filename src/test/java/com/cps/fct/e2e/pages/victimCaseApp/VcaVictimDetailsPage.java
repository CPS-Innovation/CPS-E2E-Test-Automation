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

import static com.cps.fct.e2e.utils.common.FakerUtils.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VcaVictimDetailsPage extends BasePage {

    private ScenarioContext context;

    public VcaVictimDetailsPage(PlaywrightContext context) {
        super(context);
    }

    private static final Logger logger =
            LoggerFactory.getLogger(VcaHomePage.class);

    public void navigateVictimDetailsPage(String caseUrn, String victimWitnessFullName){

        waitUntilSpinnersAreGone("Loading, please wait");
        clickLinkByName(victimWitnessFullName);
        waitForHeadingToAppear(victimWitnessFullName);
        waitForText("Case URN: "+caseUrn);
        clickTabByName("Victim Details");

    }


    public void addDateOfBirth(String justification){

        clickLinkByName("Enter date of birth");
        enterText("Day",todayDateDay());
        enterText("Month",todayDateMonth());
        enterText("Year",yearTill2005());
//        getTextboxByName("Why are you changing this information?").click();
        enterText("Why are you changing this information?",justification);
        getButtonByName("Save and continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        assertThat(page.getByText(
                Pattern.compile("Success\\s*Date of birth updated.")))
                .isVisible();

    }

    public void addAddress(String justification){
        clickLinkByName("Enter address");
        waitForText("Address line 1");
        enterText("Address line 1",buildingNumber());
        enterText("Address line 2 (optional)",streetName());
        enterText("Address line 3 (optional)",cityName());
        enterText("Postcode",ukPostCode());
        enterText("Why are you changing this information?",justification);
        getButtonByName("Save and continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        assertThat(page.getByText(
                Pattern.compile("Success\\s*Address updated.")))
                .isVisible();
    }

    public void addTelephone(String justification){
        clickLinkByName("Enter telephone number");
        waitForText("Home (optional)");
        enterText("Home (optional)",homePhone());
        enterText("Mobile (optional)",mobilePhone());
        enterText("Work (optional)",homePhone());
        enterText("Why are you changing this information?",justification);
        getButtonByName("Save and continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        assertThat(page.getByText(
                Pattern.compile("Success\\s*Telephone number updated.")))
                .isVisible();
    }

    public void addEmail(String victimWitnessFullName, String justification){

        String victimWitnessSurname = victimWitnessFullName.substring(0, victimWitnessFullName.indexOf(",")).trim();
        String victimWitnessFirstName = victimWitnessFullName.substring(victimWitnessFullName.indexOf(",")+1).trim();

        victimWitnessSurname = victimWitnessSurname.substring(0, 1).toUpperCase()
                + victimWitnessSurname.substring(1).toLowerCase();

        victimWitnessFirstName = victimWitnessFirstName.substring(0, 1).toUpperCase()
                + victimWitnessFirstName.substring(1).toLowerCase();

        clickLinkByName("Enter email address");
        waitForText("What is " +victimWitnessFirstName+ " "+victimWitnessSurname+"’s email address? (optional)");
        enterText("What is " +victimWitnessFirstName+ " " +victimWitnessSurname+"’s email address? (optional)",email());

        page.locator("textarea").fill("justification");
        getButtonByName("Save and continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        assertThat(page.getByText(
                Pattern.compile("Success\\s*Email address updated.")))
                .isVisible();
    }

    public void addPreferredName(String victimWitnessFirstName){

        clickLinkByName("Enter preferred name");
//        waitForText("What is " +victimWitnessFirstName+ " "+victimWitnessSurname+"’s email address? (optional)");


        page.pause();

    }

    public void addPreferredCPSMoc(){



    }




}
