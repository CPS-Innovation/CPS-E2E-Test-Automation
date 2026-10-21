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
import static com.cps.fct.e2e.utils.common.FakerUtils.firstnameSurname;
import static com.cps.fct.e2e.utils.common.JsonUtils.extractFromJson;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    public void addPreferredName(String victimWitnessFirstSureName){
        clickLinkByName("Enter preferred name");
        waitForText("What is " +victimWitnessFirstSureName+ "’s preferred name?");
        enterTextById("b6-Input_PreferredName",firstName());
        getButtonByName("Save and continue").click();
        assertThat(page.getByText(
                Pattern.compile("Success\\s*Preferred name updated.")))
                .isVisible();
    }

    public void addPreferredCPSMoc(String victimWitnessFirstSureName, String preferredMethod){
        clickLinkByName("Select preferred method of contact for CPS");
        waitForText("What is " +victimWitnessFirstSureName+ "’s preferred method of contact?");
        switch (preferredMethod) {
            case "Home phone":
                getRadioButtonById("b17-RadioButtonHomePhone").click();
                break;
            case "Mobile":
                getRadioButtonById("b17-RadioButtonMobile").click();
                break;
            case "Email":
                getRadioButtonById("b17-RadioButtonEmail").click();
                break;
            case "Post":
                getRadioButtonById("b17-RadioButtonPost").click();
                break;
            case "Via police":
                getRadioButtonById("b17-RadioButtonViaPolice").click();
                break;
//            case "Via Independant Sexual Violence Adviser (ISVA)":
//                getRadioButtonById("b17-RadioButtonViaISVA").click();
//                break;
            default:
                System.out.println("Specified preferred method of contact does not exist");

        }
        getButtonByName("Save and continue").click();
        assertThat(page.getByText(
                Pattern.compile("Success\\s*Preferred method of contact updated")))
                .isVisible();
    }

    public void addPreferredContact(String victimWitnessFirstSureName, String preferredContactTime){
        clickLinkByName("Enter contact times");
        waitForText("What are " +victimWitnessFirstSureName+ "’s preferred contact times?");
        enterTextById("b18-Input_PreferredContactTimes",preferredContactTime);
        getButtonByName("Save and continue").click();
        assertThat(page.getByText(
                Pattern.compile("Success\\s*You changed the preferred contact times for "+victimWitnessFirstSureName+ " to "+preferredContactTime+".")))
                .isVisible();
    }

    public void addVictimRepresentative(String victimWitnessFirstSureName){
        clickLinkByName("Enter victim representative");
        waitForText("Victim representative details");
        waitForText("Name");
        selectTextByLocator(page.locator("#Input_VictimrepresentativeFirstnam"));
        enterTextByLocator(page.locator("#Input_VictimrepresentativeFirstnam"), firstnameSurname());
        enterTextByLocator(page.locator("#Input_Victimrepresentativetelephon"), mobilePhone());
        enterTextByLocator(page.locator("#Input_Victimrepresentativeemail"), email());
        getButtonByName("Save and continue").click();
        assertThat(page.getByText(
                Pattern.compile("Success\\s*You changed the victim representative details for " +victimWitnessFirstSureName)))
                .isVisible();
    }

    public void addPowerOfAttorney(String victimWitnessFirstSureName){
        clickLinkByName("Enter power of attorney");
        waitForText("Power of attorney details");
        waitForText("Name");
        selectTextByLocator(page.locator("#Input_PowerofAttorneyFirstName"));
        enterTextByLocator(page.locator("#Input_PowerofAttorneyFirstName"), firstnameSurname());
        enterTextByLocator(page.locator("#Input_PowerofAttorneytelephonenumb"), mobilePhone());
        enterTextByLocator(page.locator("#Input_PowerofAttorneyemail"), email());
        getButtonByName("Save and continue").click();
        assertThat(page.getByText(
                Pattern.compile("Success\\s*You changed the power of attorney details for " +victimWitnessFirstSureName)))
                .isVisible();
    }

    public void navigateVictimHomePage(){
        clickLinkByName("Victims");
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForText("Victims");
    }

    public void verifyVictimDetail(String victimWitnessFullName){
        assertThat(page.locator("#b31-b3-Value")).hasText(victimWitnessFullName);

        String actualPreferredName = page.locator("#b31-b4-Value").innerText();
        assertFalse(actualPreferredName.trim().isEmpty(), "Expected PreferredName to contain text");

        String actualDoB = page.locator("#b31-b5-Value").innerText();
        assertFalse(actualDoB.trim().isEmpty(), "Expected DoB to contain text");

        String actualAddress = page.locator("#b31-b21-Value").innerText();
        assertFalse(actualAddress.trim().isEmpty(), "Expected Address to contain text");

        String actualPhoneNumber = page.locator("#b31-b23-Value").innerText();
        assertFalse(actualPhoneNumber.trim().isEmpty(), "Expected PhoneNumber to contain text");

        String actualEmail = page.locator("#b31-b25-Value").innerText();
        assertFalse(actualEmail.trim().isEmpty(), "Expected Email to contain text");

        String actualPreferredMoC = page.locator("#b31-b28-Value").innerText();
        assertFalse(actualPreferredMoC.trim().isEmpty(), "Expected PreferredMoC to contain text");

        String actualPreferredContactTime = page.locator("#b31-b31-Value").innerText();
        assertFalse(actualPreferredContactTime.trim().isEmpty(), "Expected PreferredContactTime to contain text");

        String actualVictimRep = page.locator("#b31-b32-Value").innerText();
        assertFalse(actualVictimRep.trim().isEmpty(), "Expected VictimRep to contain text");

        String actualPowerOfAttorney = page.locator("#b31-b34-Value").innerText();
        assertFalse(actualPowerOfAttorney.trim().isEmpty(), "Expected PowerOfAttorney to contain text");

    }


    public String updateFirstAndLastName(String justification){

        clickTextLink(page,"div.govuk-summary-list__row","#b31-b3-Key","Full name","Change");

        waitForText("Name and title");
        selectDropdownByLabel(page.getByLabel("Title (optional)"), "Mr");
        String firstname = firstName();
        String surname = lastName();

        enterText("Forename",firstname);
        enterText("Surname",surname);
        enterText("Why are you changing this information?",justification);
        getButtonByName("Save and continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        assertThat(page.getByText(
                Pattern.compile("Success\\s*Name and title updated.")))
                .isVisible();
        return firstname + " " + surname;

    }

    public void updateDateOfBirth(String justification){

//        Locator row = page.locator("div.govuk-summary-list__row")
//                .filter(new Locator.FilterOptions()
//                        .setHas(page.locator("#b31-b5-Key")
//                                .getByText("Date of birth", new Locator.GetByTextOptions().setExact(true))));
//        row.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Change")).click();
        clickTextLink(page,"div.govuk-summary-list__row","#b31-b5-Key","Date of birth","Change");

        enterText("Day",todayDateDay());
        enterText("Month",todayDateMonth());
        enterText("Year",yearTill2005());
        enterText("Why are you changing this information?",justification);
        getButtonByName("Save and continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        assertThat(page.getByText(
                Pattern.compile("Success\\s*Date of birth updated.")))
                .isVisible();

    }

    public void updateGender(String victimWitnessFirstSureName,  String justification) {

//        Locator row = page.locator("div.govuk-summary-list__row")
//                .filter(new Locator.FilterOptions()
//                        .setHas(page.locator("#b31-b8-Key")
//                                .getByText("Gender", new Locator.GetByTextOptions().setExact(true))));
//        row.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Change")).click();

        clickTextLink(page,"div.govuk-summary-list__row","#b31-b8-Key","Gender","Change");
        waitForText("What is "+victimWitnessFirstSureName+"’s gender? (optional)");
        getRadioButtonById("b9-RadioButton_Male-input").click();
        getButtonByName("Save and continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        assertThat(page.getByText(
                Pattern.compile("Success\\s*Gender updated.")))
                .isVisible();
    }


    public void updateAddress(String victimWitnessFirstSureName, String justification){

//        Locator row = page.locator("div.govuk-summary-list__row")
//                .filter(new Locator.FilterOptions()
//                        .setHas(page.locator("#b31-b21-Key")
//                                .getByText("Address", new Locator.GetByTextOptions().setExact(true))));
//        row.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Change")).click();

        clickTextLink(page,"div.govuk-summary-list__row","#b31-b21-Key","Address","Change");
        waitForText("What is "+victimWitnessFirstSureName+"’s address?");
        waitForText("Address line 1");
        enterText("Address line 1",buildingNumber());
        enterText("Address line 2 (optional)",streetName());
        enterText("Address line 3 (optional)",streetName());
        enterText("Address line 4 (optional)",streetName());
        enterText("Address line 5 (optional)",cityName());
        enterText("Postcode",ukPostCode());
        enterText("Why are you changing this information?",justification);
        getButtonByName("Save and continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        assertThat(page.getByText(
                Pattern.compile("Success\\s*Address updated.")))
                .isVisible();
    }

    public void updateTelephone(String victimWitnessFirstSureName, String justification){

//        Locator row = page.locator("div.govuk-summary-list__row").filter(new Locator.FilterOptions()
//                        .setHas(page.locator("#b31-b23-Key")
//                        .getByText("Telephone number", new Locator.GetByTextOptions().setExact(true))));
//        row.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Change")).click();

        clickTextLink(page,"div.govuk-summary-list__row","#b31-b23-Key","Telephone number","Change");
        waitForText(victimWitnessFirstSureName+"'s telephone numbers");
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

    public void updateEmail(String victimWitnessFirstSureName, String justification){

//        Locator row = page.locator("div.govuk-summary-list__row").filter(new Locator.FilterOptions()
//                .setHas(page.locator("#b31-b25-Key")
//                        .getByText("Email address", new Locator.GetByTextOptions().setExact(true))));
//        row.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Change")).click();

        clickTextLink(page,"div.govuk-summary-list__row","#b31-b25-Key","Email address","Change");

        waitForText("What is "+victimWitnessFirstSureName+"’s email address? (optional)");
        enterTextById("b8-Input_Email",email());

        page.locator("textarea").fill("justification");
        getButtonByName("Save and continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        assertThat(page.getByText(
                Pattern.compile("Success\\s*Email address updated.")))
                .isVisible();
    }

    public void updatePreferredName(String victimWitnessFirstSureName){

//        Locator row = page.locator("div.govuk-summary-list__row").filter(new Locator.FilterOptions()
//                .setHas(page.locator("#b31-b4-Key")
//                        .getByText("Preferred name", new Locator.GetByTextOptions().setExact(true))));
//        row.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Change")).click();
        clickTextLink(page,"div.govuk-summary-list__row","#b31-b4-Key","Preferred name","Change");

        waitForText("What is " +victimWitnessFirstSureName+ "’s preferred name?");
        enterTextById("b6-Input_PreferredName",firstName());
        getButtonByName("Save and continue").click();
        assertThat(page.getByText(
                Pattern.compile("Success\\s*Preferred name updated.")))
                .isVisible();
    }

    public void updatePreferredLanguage(String victimWitnessFirstSureName, String preferredLanguage){

//        Locator row = page.locator("div.govuk-summary-list__row").filter(new Locator.FilterOptions()
//                .setHas(page.locator("#b31-b26-Key")
//                        .getByText("Preferred name", new Locator.GetByTextOptions().setExact(true))));
//        row.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Change")).click();
        clickTextLink(page,"div.govuk-summary-list__row","#b31-b26-Key","Preferred correspondence language","Change");

//        page.pause();
//        waitForText("What is "+victimWitnessFirstSureName+"’s");
        waitForText("English");

        switch (preferredLanguage) {
            case "English":
                getRadioButtonById("b15-RadioButton_English-input").click();
                break;
            case "Welsh":
                getRadioButtonById("b15-RadioButton_Welsh-input").click();
                break;
            case "Other":
                getRadioButtonById("b15-Radio_Other").click();
                getTextboxById("b15-b5-Input_TextEntry").fill("Special Language");
                break;
            default:
                System.out.println("Specified preferred language does not exist");
        }
        getButtonByName("Save and continue").click();
        assertThat(page.getByText(
                Pattern.compile("Success\\s*You changed the preferred correspondence language for "+victimWitnessFirstSureName+" to "+preferredLanguage+".")))
                .isVisible();
    }

    public void updatePreferredCPSMoc(String victimWitnessFirstSureName, String preferredMethod){

//        Locator row = page.locator("div.govuk-summary-list__row").filter(new Locator.FilterOptions()
//                .setHas(page.locator("#b31-b4-Key")
//                        .getByText("Preferred name", new Locator.GetByTextOptions().setExact(true))));
//        row.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Change")).click();

        clickTextLink(page,"div.govuk-summary-list__row","#b31-b28-Key","Preferred method of contact for CPS","Change");

        waitForText("What is " +victimWitnessFirstSureName+ "’s preferred method of contact?");
        switch (preferredMethod) {
            case "Home phone":
                getRadioButtonById("b17-RadioButtonHomePhone").click();
                break;
            case "Mobile":
                getRadioButtonById("b17-RadioButtonMobile").click();
                break;
            case "Email":
                getRadioButtonById("b17-RadioButtonEmail").click();
                break;
            case "Post":
                getRadioButtonById("b17-RadioButtonPost").click();
                break;
            case "Via police":
                getRadioButtonById("b17-RadioButtonViaPolice").click();
                break;
//            case "Via Independant Sexual Violence Adviser (ISVA)":
//                getRadioButtonById("b17-RadioButtonViaISVA").click();
//                break;
            default:
                System.out.println("Specified preferred method of contact does not exist");

        }
        getButtonByName("Save and continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        assertThat(page.getByText(
                Pattern.compile("Success\\s*Preferred method of contact updated")))
                .isVisible();

    }

    public void updatePreferredContactTime(String victimWitnessFirstSureName, String preferredContactTime){

        clickTextLink(page,"div.govuk-summary-list__row","#b31-b31-Key","Preferred contact times ","Change");

        waitForText("What are " +victimWitnessFirstSureName+ "’s preferred contact times?");
        enterTextById("b18-Input_PreferredContactTimes",preferredContactTime);
        getButtonByName("Save and continue").click();
        assertThat(page.getByText(
                Pattern.compile("Success\\s*You changed the preferred contact times for "+victimWitnessFirstSureName+ " to "+preferredContactTime+".")))
                .isVisible();
    }

    public void updateVictimRepresentative(String victimWitnessFirstSureName){

        clickTextLink(page,"div.govuk-summary-list__row","#b31-b32-Key","Victim representative details","Change");

        waitForText("Victim representative details");
        selectTextByLocator(page.locator("#Input_VictimrepresentativeFirstnam"));
        enterTextByLocator(page.locator("#Input_VictimrepresentativeFirstnam"), firstnameSurname());
        enterTextByLocator(page.locator("#Input_Victimrepresentativetelephon"), mobilePhone());
        enterTextByLocator(page.locator("#Input_Victimrepresentativeemail"), email());
        getButtonByName("Save and continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        assertThat(page.getByText(
                Pattern.compile("Success\\s*You changed the victim representative details for " +victimWitnessFirstSureName)))
                .isVisible();
    }

    public void updatePowerOfAttorney(String victimWitnessFirstSureName){

        clickTextLink(page,"div.govuk-summary-list__row","#b31-b34-Key","Power of attorney details","Change");

        waitForText("Power of attorney details");
        waitForText("Name");
        selectTextByLocator(page.locator("#Input_PowerofAttorneyFirstName"));
        enterTextByLocator(page.locator("#Input_PowerofAttorneyFirstName"), firstnameSurname());
        enterTextByLocator(page.locator("#Input_PowerofAttorneytelephonenumb"), mobilePhone());
        enterTextByLocator(page.locator("#Input_PowerofAttorneyemail"), email());
        getButtonByName("Save and continue").click();
        assertThat(page.getByText(
                Pattern.compile("Success\\s*You changed the power of attorney details for " +victimWitnessFirstSureName)))
                .isVisible();
    }





}
