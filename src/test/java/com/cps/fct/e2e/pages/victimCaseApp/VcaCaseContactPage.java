package com.cps.fct.e2e.pages.victimCaseApp;

import com.cps.fct.e2e.pages.BasePage;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.cps.fct.e2e.utils.common.FakerUtils.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class VcaCaseContactPage extends BasePage {

//    @Inject
//    private ScenarioContext context;

    private final ScenarioContext scenarioContext;

    public VcaCaseContactPage(PlaywrightContext context,  ScenarioContext scenarioContext) {
        super(context);
        this.scenarioContext = scenarioContext;
    }

    private static final Logger logger =
            LoggerFactory.getLogger(VcaCaseContactPage.class);

    public void addFamilyLiaisonOfficer(){
        clickLinkByName("Add Family Liaison Officer");
        waitForText("Add Family Liaison Officer");

        String firstName = firstName();
        String surName = lastName().toUpperCase();

        selectTextByLocator(page.locator("#Input_Full_Name"));
        enterTextByLocator(page.locator("#Input_Full_Name"), surName+", "+firstName);
        enterTextByLocator(page.locator("#Input_Email2"),email());
        enterTextByLocator(page.locator("#Input_Telephone2"),mobilePhone());
        getButtonByName("Continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");

        Locator row = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions().setHasText("Family Liaison Officer"));

        assertThat(row).isVisible();
        assertThat(row.locator("td[data-header='Name']")).not().hasText("");
        assertThat(row.locator("td[data-header='Email']")).not().hasText("");
        assertThat(row.locator("td[data-header='Telephone']")).not().hasText("");

    }

    public void addIndependentSexualVA(){
        clickLinkByName("Add Independent Sexual Violence Adviser (ISVA)");
        waitForText("Add Independent Sexual");
        String firstName = firstName();
        String surName = lastName().toUpperCase();
        selectTextByLocator(page.locator("#Input_Full_Name"));
        enterTextByLocator(page.locator("#Input_Full_Name"), surName+", "+firstName);
        enterTextByLocator(page.locator("#Input_AddressLine1"), buildingNumber());
        enterTextByLocator(page.locator("#Input_AddressLine2"), streetName());
        enterTextByLocator(page.locator("#Input_AddressLine3"), streetName());
        enterTextByLocator(page.locator("#Input_AddressLine4"), streetName());
        enterTextByLocator(page.locator("#Input_AddressLine5"), countyName());
        enterTextByLocator(page.locator("#Input_Postcode"), ukPostCode());
        enterTextByLocator(page.locator("#Input_City"), cityName());
        enterTextByLocator(page.locator("#Input_Country"), countyName());
        enterTextByLocator(page.locator("#Input_Email2"),email());
        enterTextByLocator(page.locator("#Input_Telephone2"),mobilePhone());
        getButtonByName("Continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");
        waitForTextToAppear("Contacts");
        Locator row = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions().setHasText("Independent Sexual Violence Adviser (ISVA)"));

        assertThat(row).isVisible();
        assertThat(row.locator("td[data-header='Name']")).not().hasText("");
        assertThat(row.locator("td[data-header='Address']")).not().hasText("");
        assertThat(row.locator("td[data-header='Email']")).not().hasText("");
        assertThat(row.locator("td[data-header='Telephone']")).not().hasText("");
    }

    public void addIndependentDomesticVA(){
        clickLinkByName("Add Independent Domestic Violence Adviser (IDVA)");
        waitForText("Add Independent Domestic");
        String firstName = firstName();
        String surName = lastName().toUpperCase();
        selectTextByLocator(page.locator("#Input_Full_Name"));
        enterTextByLocator(page.locator("#Input_Full_Name"), surName+", "+firstName);
        enterTextByLocator(page.locator("#Input_AddressLine1"), buildingNumber());
        enterTextByLocator(page.locator("#Input_AddressLine2"), streetName());
        enterTextByLocator(page.locator("#Input_AddressLine3"), streetName());
        enterTextByLocator(page.locator("#Input_AddressLine4"), streetName());
        enterTextByLocator(page.locator("#Input_AddressLine5"), countyName());
        enterTextByLocator(page.locator("#Input_Postcode"), ukPostCode());
        enterTextByLocator(page.locator("#Input_City"), cityName());
        enterTextByLocator(page.locator("#Input_Country"), countyName());
        enterTextByLocator(page.locator("#Input_Email2"),email());
        enterTextByLocator(page.locator("#Input_Telephone2"),mobilePhone());
        getButtonByName("Continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");

        Locator row = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions().setHasText("Independent Domestic Violence Adviser (IDVA)"));

        assertThat(row).isVisible();
        assertThat(row.locator("td[data-header='Name']")).not().hasText("");
        assertThat(row.locator("td[data-header='Address']")).not().hasText("");
        assertThat(row.locator("td[data-header='Email']")).not().hasText("");
        assertThat(row.locator("td[data-header='Telephone']")).not().hasText("");

    }

    public void verifyCaseContact(){

        Locator familyLiaisonOfficer = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions().setHasText("Family Liaison Officer"));

        assertThat(familyLiaisonOfficer.locator("td[data-header='Name']")).not().hasText("");
        assertThat(familyLiaisonOfficer.locator("td[data-header='Email']")).not().hasText("");
        assertThat(familyLiaisonOfficer.locator("td[data-header='Telephone']")).not().hasText("");

        Locator independentSexualVA = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions().setHasText("Independent Domestic Violence Adviser (IDVA)"));

        assertThat(independentSexualVA.locator("td[data-header='Name']")).not().hasText("");
        assertThat(independentSexualVA.locator("td[data-header='Address']")).not().hasText("");
        assertThat(independentSexualVA.locator("td[data-header='Email']")).not().hasText("");
        assertThat(independentSexualVA.locator("td[data-header='Telephone']")).not().hasText("");


        Locator independentDomesticVA = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions().setHasText("Independent Domestic Violence Adviser (IDVA)"));

        assertThat(independentDomesticVA.locator("td[data-header='Name']")).not().hasText("");
        assertThat(independentDomesticVA.locator("td[data-header='Address']")).not().hasText("");
        assertThat(independentDomesticVA.locator("td[data-header='Email']")).not().hasText("");
        assertThat(independentDomesticVA.locator("td[data-header='Telephone']")).not().hasText("");

    }

    public void updateFamilyLiaisonOfficer(){
        waitUntilSpinnersAreGone("Loading, please wait");
        Map<String, List<String>> familyLiaisonOfficerMapDetails = new HashMap<>();

        Locator familyLiaisonOfficer = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions().setHasText("Family Liaison Officer"));

        String existingFullName = familyLiaisonOfficer.locator("td[data-header='Name']").innerText();
        String existingEmail = familyLiaisonOfficer.locator("td[data-header='Email']").innerText();
        String existingTelephone = familyLiaisonOfficer.locator("td[data-header='Telephone']").innerText();

        familyLiaisonOfficerMapDetails.put("FamilyLiaisonOfficer", Arrays.asList(existingFullName, existingEmail, existingTelephone));
        scenarioContext.set("familyLiaisonOfficerMapDetails", familyLiaisonOfficerMapDetails);

        clickTextLink(page,"tr.govuk-table__row","td[data-header='Role']","Family Liaison Officer","Change");

        String firstName = firstName();
        String surName = lastName().toUpperCase();

        selectTextByLocator(page.locator("#Input_Full_Name"));
        enterTextByLocator(page.locator("#Input_Full_Name"), surName+", "+firstName);
        enterTextByLocator(page.locator("#Input_Email2"),email());
        enterTextByLocator(page.locator("#Input_Telephone2"),mobilePhone());
        getButtonByName("Continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");

        Locator row = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions()
                        .setHasText("Family Liaison Officer"));

        assertThat(row.locator("td[data-header='Name']")).not().hasText("");
        assertThat(row.locator("td[data-header='Email']")).not().hasText("");
        assertThat(row.locator("td[data-header='Telephone']")).not().hasText("");

    }

    public void updateIndependentSexualVA(){
        waitUntilSpinnersAreGone("Loading, please wait");
        Map<String, List<String>> IndependentSexualMapDetails = new HashMap<>();

        Locator IndependentSexual = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions().setHasText("Independent Sexual Violence Adviser (ISVA)"));

        String existingFullName = IndependentSexual.locator("td[data-header='Name']").innerText();
        String existingEmail = IndependentSexual.locator("td[data-header='Email']").innerText();
        String existingTelephone = IndependentSexual.locator("td[data-header='Telephone']").innerText();

        IndependentSexualMapDetails.put("IndependentSexual", Arrays.asList(existingFullName, existingEmail, existingTelephone));
        scenarioContext.set("IndependentSexualMapDetails", IndependentSexualMapDetails);

        clickTextLink(page,"tr.govuk-table__row","td[data-header='Role']","Independent Sexual Violence Adviser (ISVA)","Change");

        String firstName = firstName();
        String surName = lastName().toUpperCase();
        selectTextByLocator(page.locator("#Input_Full_Name"));
        enterTextByLocator(page.locator("#Input_Full_Name"), surName+", "+firstName);
        enterTextByLocator(page.locator("#Input_AddressLine1"), buildingNumber());
        enterTextByLocator(page.locator("#Input_AddressLine2"), streetName());
        enterTextByLocator(page.locator("#Input_AddressLine3"), streetName());
        enterTextByLocator(page.locator("#Input_AddressLine4"), streetName());
        enterTextByLocator(page.locator("#Input_AddressLine5"), countyName());
        enterTextByLocator(page.locator("#Input_Postcode"), ukPostCode());
        enterTextByLocator(page.locator("#Input_City"), cityName());
        enterTextByLocator(page.locator("#Input_Country"), countyName());
        enterTextByLocator(page.locator("#Input_Email2"),email());
        enterTextByLocator(page.locator("#Input_Telephone2"),mobilePhone());
        getButtonByName("Continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");

        Locator row = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions()
                        .setHasText("Independent Sexual Violence Adviser (ISVA)"));

        assertThat(row.locator("td[data-header='Name']")).not().hasText("");
        assertThat(row.locator("td[data-header='Address']")).not().hasText("");
        assertThat(row.locator("td[data-header='Email']")).not().hasText("");
        assertThat(row.locator("td[data-header='Telephone']")).not().hasText("");
    }

    public void updateIndependentDomesticVA(){
        waitUntilSpinnersAreGone("Loading, please wait");

        Map<String, List<String>> IndependentDomesticMapDetails = new HashMap<>();

        Locator IndependentDomestic = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions().setHasText("Independent Domestic Violence Adviser (IDVA)"));

        String existingFullName = IndependentDomestic.locator("td[data-header='Name']").innerText();
        String existingEmail = IndependentDomestic.locator("td[data-header='Email']").innerText();
        String existingTelephone = IndependentDomestic.locator("td[data-header='Telephone']").innerText();

        IndependentDomesticMapDetails.put("IndependentDomestic", Arrays.asList(existingFullName, existingEmail, existingTelephone));
        scenarioContext.set("IndependentDomesticMapDetails", IndependentDomesticMapDetails);

        clickTextLink(page,"tr.govuk-table__row","td[data-header='Role']","Independent Domestic Violence Adviser (IDVA)","Change");

        String firstName = firstName();
        String surName = lastName().toUpperCase();
        selectTextByLocator(page.locator("#Input_Full_Name"));
        enterTextByLocator(page.locator("#Input_Full_Name"), surName+", "+firstName);
        enterTextByLocator(page.locator("#Input_AddressLine1"), buildingNumber());
        enterTextByLocator(page.locator("#Input_AddressLine2"), streetName());
        enterTextByLocator(page.locator("#Input_AddressLine3"), streetName());
        enterTextByLocator(page.locator("#Input_AddressLine4"), streetName());
        enterTextByLocator(page.locator("#Input_AddressLine5"), countyName());
        enterTextByLocator(page.locator("#Input_Postcode"), ukPostCode());
        enterTextByLocator(page.locator("#Input_City"), cityName());
        enterTextByLocator(page.locator("#Input_Country"), countyName());
        enterTextByLocator(page.locator("#Input_Email2"),email());
        enterTextByLocator(page.locator("#Input_Telephone2"),mobilePhone());
        getButtonByName("Continue").click();
        waitUntilSpinnersAreGone("Loading, please wait");

        Locator row = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions()
                        .setHasText("Independent Domestic Violence Adviser (IDVA)"));

        assertThat(row.locator("td[data-header='Name']")).not().hasText("");
        assertThat(row.locator("td[data-header='Address']")).not().hasText("");
        assertThat(row.locator("td[data-header='Email']")).not().hasText("");
        assertThat(row.locator("td[data-header='Telephone']")).not().hasText("");

    }

    public void verifyUpdatedCaseContact(){
        waitUntilSpinnersAreGone("Loading, please wait");

//        Family Liaison Officer
        Map<String, List<String>> familyLiaisonOfficerMapDetails = scenarioContext.get("familyLiaisonOfficerMapDetails");
        List<String> familyLiaisonOfficerDetails = familyLiaisonOfficerMapDetails.get("FamilyLiaisonOfficer");

        String oldFullName = familyLiaisonOfficerDetails.getFirst();
        String oldEmail = familyLiaisonOfficerDetails.get(1);
        String oldTelephone = familyLiaisonOfficerDetails.get(2);

        Locator familyLiaisonOfficer = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions().setHasText("Family Liaison Officer"));

        String newFullName = familyLiaisonOfficer.locator("td[data-header='Name']").innerText();
        assertNotEquals(oldFullName, newFullName);
        String newEmail = familyLiaisonOfficer.locator("td[data-header='Email']").innerText();
        assertNotEquals(oldEmail, newEmail);
        String newTelephone = familyLiaisonOfficer.locator("td[data-header='Telephone']").innerText();
        assertNotEquals(oldTelephone, newTelephone);

        //        Independent Sexual Violence Adviser
        Map<String, List<String>> IndependentSexualMapDetails = scenarioContext.get("IndependentSexualMapDetails");
        List<String> IndependentSexualDetails = IndependentSexualMapDetails.get("IndependentSexual");

        String oldISVAFullName = IndependentSexualDetails.getFirst();
        String oldISVAEmail = IndependentSexualDetails.get(1);
        String oldISVATelephone = IndependentSexualDetails.get(2);

        Locator IndependentSexualViolenceAdviser = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions().setHasText("Independent Sexual Violence Adviser (ISVA)"));

        String newISVAFullName = IndependentSexualViolenceAdviser.locator("td[data-header='Name']").innerText();
        assertNotEquals(oldISVAFullName, newISVAFullName);
        String newISVAEmail = IndependentSexualViolenceAdviser.locator("td[data-header='Email']").innerText();
        assertNotEquals(oldISVAEmail, newISVAEmail);
        String newISVATelephone = IndependentSexualViolenceAdviser.locator("td[data-header='Telephone']").innerText();
        assertNotEquals(oldISVATelephone, newISVATelephone);

        //        Independent Domestic  Violence Adviser
        Map<String, List<String>> IndependentDomesticMapDetails = scenarioContext.get("IndependentDomesticMapDetails");
        List<String> IndependentDomesticDetails = IndependentDomesticMapDetails.get("IndependentDomestic");

        String oldIDVAFullName = IndependentDomesticDetails.getFirst();
        String oldIDVAEmail = IndependentDomesticDetails.get(1);
        String oldIDVATelephone = IndependentDomesticDetails.get(2);

        Locator IndependentDomesticViolenceAdviser = page.locator("tr.govuk-table__row")
                .filter(new Locator.FilterOptions().setHasText("Independent Domestic Violence Adviser (IDVA)"));

        String newIDVAFullName = IndependentDomesticViolenceAdviser.locator("td[data-header='Name']").innerText();
        assertNotEquals(oldIDVAFullName, newIDVAFullName);
        String newIDVAEmail = IndependentDomesticViolenceAdviser.locator("td[data-header='Email']").innerText();
        assertNotEquals(oldIDVAEmail, newIDVAEmail);
        String newIDVATelephone = IndependentDomesticViolenceAdviser.locator("td[data-header='Telephone']").innerText();
        assertNotEquals(oldIDVATelephone, newIDVATelephone);

    }




}
