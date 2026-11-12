package com.cps.fct.e2e.stepDefinition.ui;

import com.cps.fct.e2e.pages.PageObjects;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.playwright.PlaywrightManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.picocontainer.annotations.Inject;

import java.util.*;
import java.util.stream.Collectors;

public class VictimCaseAppUiStepDefinition {

    @Inject
    private PageObjects pages;
    @Inject
    private ScenarioContext context;
    @Inject
    private PlaywrightManager playwrightManager;


    @Given("VLO login to victim case application")
    public void newVloLogIntoVca() {
        playwrightManager.setUpBrowser(null);
        pages.useCurrentPage();
        pages.vcaLoginPage.loginIntoVca(
                pages.appsLoginPage.vcaUsername(),
                pages.appsLoginPage.vcaPassword()
        );

    }

    @When("{string} is searched using case reference")
    public void caseReferenceSearch(String personType) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> victimWitnessIds = context.get("victimWitnessMapIds");

        for (String id : victimWitnessIds.get(personType)) {
            pages.vcaHomePage.searchVictimCase(caseUrn);
        }

    }

    @When("the {string} is onboarded to {string} service lead")
    public void onboardVictim(String personType, String serviceType) {

        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        pages.vcaHomePage.onboardVictim(personFullName, serviceType);

    }

    @When("select {string} for next task for the {string}")
    public void selectNextTask(String taskType, String personType) {
        pages.vcaHomePage.selectNextTaskForVictim(taskType);
    }

    @When("verify that {string} is onboarded")
    public void verifyOnboard(String personType) {
//        Map<String, List<String>> victimWitnessIds = context.get("victimWitnessMapIds");

        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        pages.vcaHomePage.verifyVictimOnboard(personFullName);

//        for (String id : victimWitnessIds.get(personType)) {
//            pages.vcaHomePage.verifyVictimOnboard(personFullName);
//        }

    }


    @When("the {string} is assigned as VLO to {string}")
    public void assignVLO(String vloName, String personType) {
        String caseUrn = context.get("caseUrn");

        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.assignVloToVictim(vloName, personFullName);
    }

    @When("verify that Vlo {string} is assigned to {string}")
    public void verifyAssignedVlo(String vloName, String personType) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.verifyAssignedVlo(vloName, personFullName);
    }

    @When("the following {string} personal details are added to CMS")
    public void addVictimCMSPersonDetails(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");

        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateVictimDetailsTab(caseUrn, personFullName);
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            switch (row.get("cmsField")) {
                case "Date of birth":
                    pages.vcaVictimDetailsPage.addDateOfBirth(row.get("justification"));
                    break;
                case "Address":
                    pages.vcaVictimDetailsPage.addAddress(row.get("justification"));
                    break;
                case "Telephone number":
                    pages.vcaVictimDetailsPage.addTelephone(row.get("justification"));
                    break;
                case "Email address":
                    pages.vcaVictimDetailsPage.addEmail(personFullName, row.get("justification"));
                    break;
                default:
                    System.out.println("Specified cms field does not exist");
            }
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @When("the following {string} personal details are added in VCA")
    public void addVictimVCAPersonDetails(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        Map<String, List<String>> personTypeFirstSureNameMap = context.get("personTypeFirstSureNameMap");
        String personFirstLastName = String.valueOf(personTypeFirstSureNameMap.get(personType));
        personFirstLastName = personFirstLastName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateVictimDetailsTab(caseUrn, personFullName);

        for (Map<String, String> row : rows) {
            switch (row.get("vcaField")) {
                case "Preferred name":
                    pages.vcaVictimDetailsPage.addPreferredName(personFirstLastName);
                    break;
                case "Preferred method of contact for CPS":
                    pages.vcaVictimDetailsPage.addPreferredCPSMoc(personFirstLastName, row.get("value"));
                    break;
                case "Preferred contact times":
                    pages.vcaVictimDetailsPage.addPreferredContact(personFirstLastName, row.get("value"));
                    break;
                case "Victim representative details":
                    pages.vcaVictimDetailsPage.addVictimRepresentative(personFirstLastName);
                    break;
                case "Power of attorney details":
                    pages.vcaVictimDetailsPage.addPowerOfAttorney(personFirstLastName);
                    break;
                default:
                    System.out.println("Specified vca field does not exist");
            }
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @Then("the {string} personal details are verified")
    public void verifyVictimDetail(String personType) {
        String caseUrn = context.get("caseUrn");

        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateVictimDetailsTab(caseUrn, personFullName);
        pages.vcaVictimDetailsPage.verifyVictimDetail(personFullName);

    }

    @When("the {string} forename and surname are updated with {string} to CMS")
    public void updateForenameSurname(String personType, String justification) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> victimWitnessIds = context.get("victimWitnessMapIds");

        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        Map<String, String> personTypeFirstSureNameMap = context.get("personTypeFirstSureNameMap");
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateVictimDetailsTab(caseUrn, personFullName);

        String newFirstSureName = pages.vcaVictimDetailsPage.updateFirstAndLastName(justification);
        int spaceIndex = newFirstSureName.lastIndexOf(" ");

        String newPersonFullName = newFirstSureName.substring(spaceIndex + 1).toUpperCase()
                + ", " + newFirstSureName.substring(0, spaceIndex);
        personTypePersonNameMap.put(personType, Collections.singletonList(newPersonFullName));
        personTypeFirstSureNameMap.put(personType, newFirstSureName);
        context.set("personTypePersonNameMap", personTypePersonNameMap);
        context.set("personTypeFirstSureNameMap", personTypeFirstSureNameMap);

        pages.vcaHomePage.navigateVictimHomePage();
    }

    @When("the following {string} personal details are updated to CMS")
    public void updateVictimCMSPersonDetails(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");

        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        Map<String, List<String>> personTypeFirstSureNameMap = context.get("personTypeFirstSureNameMap");
        String personFirstLastName = String.valueOf(personTypeFirstSureNameMap.get(personType));
        personFirstLastName = personFirstLastName.replace("[", "").replace("]", "");

        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateVictimDetailsTab(caseUrn, personFullName);

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            switch (row.get("cmsField")) {
                case "Date of birth":
                    pages.vcaVictimDetailsPage.updateDateOfBirth(row.get("justification"));
                    break;
                case "Gender":
                    pages.vcaVictimDetailsPage.updateGender(personFirstLastName, row.get("justification"));
                    break;
                case "Address":
                    pages.vcaVictimDetailsPage.updateAddress(personFirstLastName, row.get("justification"));
                    break;
                case "Telephone number":
                    pages.vcaVictimDetailsPage.updateTelephone(personFirstLastName, row.get("justification"));
                    break;
                case "Email address":
                    pages.vcaVictimDetailsPage.updateEmail(personFirstLastName, row.get("justification"));
                    break;
                default:
                    System.out.println("Specified cms field does not exist");
            }
        }
    }

    @When("the following {string} personal details are updated in VCA")
    public void updateVictimVCAPersonDetails(String personType, DataTable dataTable) {

        Map<String, List<String>> personTypeFirstSureNameMap = context.get("personTypeFirstSureNameMap");
        String personFirstLastName = String.valueOf(personTypeFirstSureNameMap.get(personType));
        personFirstLastName = personFirstLastName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            switch (row.get("vcaField")) {
                case "Preferred name":
                    pages.vcaVictimDetailsPage.updatePreferredName(personFirstLastName);
                    break;
                case "Preferred correspondence language":
                    pages.vcaVictimDetailsPage.updatePreferredLanguage(personFirstLastName, row.get("value"));
                    break;
                case "Preferred method of contact for CPS":
                    pages.vcaVictimDetailsPage.updatePreferredCPSMoc(personFirstLastName, row.get("value"));
                    break;
                case "Preferred contact times":
                    pages.vcaVictimDetailsPage.updatePreferredContactTime(personFirstLastName, row.get("value"));
                    break;
                case "Victim representative details":
                    pages.vcaVictimDetailsPage.updateVictimRepresentative(personFirstLastName);
                    break;
                case "Power of attorney details":
                    pages.vcaVictimDetailsPage.updatePowerOfAttorney(personFirstLastName);
                    break;
                default:
                    System.out.println("Specified vca field does not exist");
            }
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @When("the following case contacts are added to {string} in VCA application")
    public void addCaseContact(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateCaseContactsTab(caseUrn, personFullName);

        for (Map<String, String> row : rows) {
            switch (row.get("caseContactType")) {
                case "Family Liaison Officer":
                    pages.vcaCaseContactPage.addFamilyLiaisonOfficer();
                    break;
                case "Independent Sexual Violence Adviser":
                    pages.vcaCaseContactPage.addIndependentSexualVA();
                    break;
                case "Independent Domestic Violence Adviser":
                    pages.vcaCaseContactPage.addIndependentDomesticVA();
                    break;
                default:
                    System.out.println("Specified case contact type does not exist");
            }
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @Then("the added case contacts for {string} are verified")
    public void verifyCaseContacts(String personType) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateCaseContactsTab(caseUrn, personFullName);
        pages.vcaCaseContactPage.verifyCaseContact();
        pages.vcaHomePage.navigateVictimHomePage();

    }

    @When("update following case contacts of {string} in VCA application")
    public void updateCaseContact(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");

        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateCaseContactsTab(caseUrn, personFullName);

        for (Map<String, String> row : rows) {
            switch (row.get("caseContactType")) {
                case "Family Liaison Officer":
                    pages.vcaCaseContactPage.updateFamilyLiaisonOfficer();
                    break;
                case "Independent Sexual Violence Adviser":
                    pages.vcaCaseContactPage.updateIndependentSexualVA();
                    break;
                case "Independent Domestic Violence Adviser":
                    pages.vcaCaseContactPage.updateIndependentDomesticVA();
                    break;
                default:
                    System.out.println("Specified case contact type does not exist");
            }
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }


    @Then("{string} case contacts are verified in VCA application")
    public void verifyUpdatedCaseContacts(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            pages.vcaHomePage.searchVictimCase(caseUrn);
            pages.vcaHomePage.navigateCaseContactsTab(caseUrn, personFullName);
            pages.vcaCaseContactPage.verifyUpdatedCaseContact(row.get("caseContactType"));
            pages.vcaHomePage.navigateVictimHomePage();
        }
    }

    @When("the following category type is added to {string} in VCA application")
    public void addNewCategory(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        Map<String, List<String>> personTypeFirstSureNameMap = context.get("personTypeFirstSureNameMap");
        String personFirstLastName = String.valueOf(personTypeFirstSureNameMap.get(personType));
        personFirstLastName = personFirstLastName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateVictimDetailsTab(caseUrn, personFullName);

        for (Map<String, String> row : rows) {
            pages.vcaVictimDetailsPage.addCategory(personFirstLastName, row.get("categoryType"));
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @Then("{string} category types are verified in summary details in VCA application")
    public void verifyCategoryTypes(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Set<String> categoryTypes = new LinkedHashSet<>();

        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateVictimDetailsTab(caseUrn, personFullName);

        for (Map<String, String> row : rows) {
            String categoryType = row.get("categoryType");
            categoryTypes.addAll(Arrays.asList(categoryType.split(",")));
        }

        String newCategoryTypes = String.join(", ", categoryTypes).toLowerCase(Locale.ROOT);
        pages.vcaVictimDetailsPage.verifyCategoryType(newCategoryTypes);
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @When("the following meeting types are not offered to {string} in VCA application")
    public void meetingNotOffered(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        Map<String, List<String>> personTypeFirstSureNameMap = context.get("personTypeFirstSureNameMap");
        String personFirstLastName = String.valueOf(personTypeFirstSureNameMap.get(personType));
        personFirstLastName = personFirstLastName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateCommunicationsTab(caseUrn, personFullName);

        for (Map<String, String> row : rows) {
            pages.vcaCommunicationsPage.navigateMeetings();
            pages.vcaCommunicationsPage.logMeetingNotOffered(personFirstLastName, row.get("meetingType"), row.get("notOfferedReason"));
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @Then("the following not offered meetings to {string} are verified in VCA application")
    public void verifyNotOfferedMeeting(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateCommunicationsTab(caseUrn, personFullName);

        for (Map<String, String> row : rows) {
            pages.vcaCommunicationsPage.navigateMeetings();
            pages.vcaCommunicationsPage.verifyMeetingNotOffered(row.get("meetingType"), row.get("notOfferedReason"));
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @When("the meeting is offered and meeting response is logged for {string} in VCA application")
    public void offerMeetingTypeMethod(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");

        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        Map<String, List<String>> personTypeFirstSureNameMap = context.get("personTypeFirstSureNameMap");
        String personFirstLastName = String.valueOf(personTypeFirstSureNameMap.get(personType));
        personFirstLastName = personFirstLastName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateCommunicationsTab(caseUrn, personFullName);

        for (Map<String, String> row : rows) {
            pages.vcaCommunicationsPage.navigateMeetings();
            pages.vcaCommunicationsPage.logMeetingOffered(personFirstLastName, row.get("meetingType"), row.get("offerMethod"));
            pages.vcaCommunicationsPage.logMeetingResponse(row.get("meetingType"), row.get("meetingResponse"), row.get("meetingResponseMethod"));
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @When("the offered meeting and response is verified for {string} in VCA application")
    public void verifyMeetingOfferResponse(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");

        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateCommunicationsTab(caseUrn, personFullName);

        for (Map<String, String> row : rows) {
            pages.vcaCommunicationsPage.navigateMeetings();
            pages.vcaCommunicationsPage.verifyMeetingOfferResponse(row.get("meetingType"), row.get("offerMethod"), row.get("meetingResponse"), row.get("meetingResponseMethod"));
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @When("the meeting is arranged with meeting method and meeting chair person for {string} in VCA application")
    public void arrangeAcceptedMeeting(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");

        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        Map<String, List<String>> personTypeFirstSureNameMap = context.get("personTypeFirstSureNameMap");
        String personFirstLastName = String.valueOf(personTypeFirstSureNameMap.get(personType));
        personFirstLastName = personFirstLastName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateCommunicationsTab(caseUrn, personFullName);

        List<String> meetingAttendeesRoles = rows.stream()
                .map(row -> row.get("meetingAttendeesRoles"))
                .collect(Collectors.toList());

        List<String> meetingSupportNeeds = rows.stream()
                .map(row -> row.get("supportNeeds"))
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .toList();
        pages.vcaCommunicationsPage.navigateMeetings();

        for (Map<String, String> row : rows) {
            pages.vcaCommunicationsPage.arrangeMeetings(personFirstLastName, row.get("meetingType"), row.get("meetingSource"), row.get("meetingMethod"), row.get("locationType"), row.get("locationName"));
            pages.vcaCommunicationsPage.selectAttendeesAndChairperson(meetingAttendeesRoles, row.get("meetingChairPerson"));
            pages.vcaCommunicationsPage.selectSupportNeeds(meetingSupportNeeds, personFirstLastName);
            pages.vcaCommunicationsPage.confirmArrangedMeeting(row.get("meetingType"));
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }



    @When("the {string} meeting type is arranged because {string} it and meeting method is {string} at {string} in {string} and {string} as chair person with attendees for {string} in VCA application")
    public void arrangeMeetingMethod(String meetingType, String meetingSource, String meetingMethod, String locationType, String locationName,
                                     String chairPerson, String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");

        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        Map<String, List<String>> personTypeFirstSureNameMap = context.get("personTypeFirstSureNameMap");
        String personFirstLastName = String.valueOf(personTypeFirstSureNameMap.get(personType));
        personFirstLastName = personFirstLastName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateCommunicationsTab(caseUrn, personFullName);

        List<String> meetingAttendeesRoles = rows.stream()
                .map(row -> row.get("meetingAttendeesRoles"))
                .collect(Collectors.toList());

        List<String> meetingSupportNeeds = rows.stream()
                .map(row -> row.get("supportNeeds"))
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .toList();

        pages.vcaCommunicationsPage.navigateMeetings();
        pages.vcaCommunicationsPage.arrangeMeetings(personFirstLastName, meetingType, meetingSource, meetingMethod, locationType, locationName);
        pages.vcaCommunicationsPage.selectAttendeesAndChairperson(meetingAttendeesRoles, chairPerson);
        pages.vcaCommunicationsPage.selectSupportNeeds(meetingSupportNeeds, personFirstLastName);
        pages.vcaCommunicationsPage.confirmArrangedMeeting(meetingType);
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @When("cancel the arranged meeting with the cancellation reason for {string} in VCA application")
    public void cancelArrangedMeeting(String personType, DataTable dataTable) {

        String caseUrn = context.get("caseUrn");

        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        Map<String, List<String>> personTypeFirstSureNameMap = context.get("personTypeFirstSureNameMap");
        String personFirstLastName = String.valueOf(personTypeFirstSureNameMap.get(personType));
        personFirstLastName = personFirstLastName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateCommunicationsTab(caseUrn, personFullName);
        pages.vcaCommunicationsPage.navigateMeetings();

        for (Map<String, String> row : rows) {
            pages.vcaCommunicationsPage.cancelArrangedMeeting(personFirstLastName, row.get("meetingType"), row.get("cancellationReason"));
            pages.vcaCommunicationsPage.confirmCancelledMeeting(row.get("meetingType"));
            pages.vcaCommunicationsPage.navigateMeetings();
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @Then("verify the cancelled meeting details for {string} in VCA application")
    public void verifyCancelledMeetingDetails(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        Map<String, List<String>> personTypeFirstSureNameMap = context.get("personTypeFirstSureNameMap");
        String personFirstLastName = String.valueOf(personTypeFirstSureNameMap.get(personType));
        personFirstLastName = personFirstLastName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateCommunicationsTab(caseUrn, personFullName);
        pages.vcaCommunicationsPage.navigateMeetings();
        for (Map<String, String> row : rows) {
            pages.vcaCommunicationsPage.verifyCancelledMeetingDetails(personFirstLastName, row.get("meetingType"), row.get("cancellationReason"));
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @Then("verify the arranged meeting details for {string} in VCA application")
    public void verifyArrangedMeetingDetails(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");

        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        Map<String, List<String>> personTypeFirstSureNameMap = context.get("personTypeFirstSureNameMap");
        String personFirstLastName = String.valueOf(personTypeFirstSureNameMap.get(personType));
        personFirstLastName = personFirstLastName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateCommunicationsTab(caseUrn, personFullName);
        pages.vcaCommunicationsPage.navigateMeetings();

        for (Map<String, String> row : rows) {
            pages.vcaCommunicationsPage.verifyArrangedMeetingDetails(personFirstLastName, row.get("meetingType"), row.get("meetingRequest"), row.get("meetingMethod"), row.get("locationName"), row.get("chairPerson"));
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @When("the arranged meeting is conducted with following details for {string} in VCA application")
    public void conductArrangedMeeting(String personType, DataTable dataTable){
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        Map<String, List<String>> personTypeFirstSureNameMap = context.get("personTypeFirstSureNameMap");
        String personFirstLastName = String.valueOf(personTypeFirstSureNameMap.get(personType));
        personFirstLastName = personFirstLastName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateCommunicationsTab(caseUrn, personFullName);
        pages.vcaCommunicationsPage.navigateMeetings();

        List<String> meetingAttendeesRoles = rows.stream()
                .map(row -> row.get("meetingAttendees"))
                .collect(Collectors.toList());

        for (Map<String, String> row : rows) {
            pages.vcaCommunicationsPage.conductArrangedMeeting(personFullName, personFirstLastName, row.get("meetingType"), row.get("meetingTookPlace"),
                             meetingAttendeesRoles,row.get("meetingLedBy"), row.get("agreeToResearch"), row.get("meetingNotes"), row.get("meetingNotesSent"),
                            row.get("actionsAgreed"));
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }

    @Then("verify the logged meeting outcome details for {string} in VCA application")
    public void verifyLoggedMeeting(String personType, DataTable dataTable) {
        String caseUrn = context.get("caseUrn");
        Map<String, List<String>> personTypePersonNameMap = context.get("personTypePersonNameMap");
        String personFullName = String.valueOf(personTypePersonNameMap.get(personType));
        personFullName = personFullName.replace("[", "").replace("]", "");

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        pages.vcaHomePage.searchVictimCase(caseUrn);
        pages.vcaHomePage.navigateCommunicationsTab(caseUrn, personFullName);
        pages.vcaCommunicationsPage.navigateMeetings();

        for (Map<String, String> row : rows) {
            pages.vcaCommunicationsPage.verifyLoggedMeeting(row.get("meetingType"), row.get("agreeToResearch"),
                    row.get("meetingNotes"), row.get("meetingNotesSent"), row.get("actionsAgreed"));
        }
        pages.vcaHomePage.navigateVictimHomePage();
    }

}
