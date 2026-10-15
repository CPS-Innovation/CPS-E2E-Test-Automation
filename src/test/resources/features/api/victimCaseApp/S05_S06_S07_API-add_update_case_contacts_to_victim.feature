@regression @vca_api_regression @VCA_API_S5 @VCA_API_S6 @VCA_API_S7

Feature: VCA-API-S5_S6_S7:- Add and update cps-contacts to victims
  As a Victim Liaison Officer
  I want to add cps-contacts to victim
  I want to update cps-contacts to victim
  Verify that cps-contacts are added to victim
  Verify that cps-contacts are updated to victim

  Background: Create cases with multi defendant with multi charge with victim and witness.
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case


  @addCpsContactToVictim
<<<<<<<< HEAD:src/test/resources/features/api/victimCaseApp/S05_S06_S07_API-add_update_case_contacts_to_victim.feature
  Scenario: Add contacts to victims and then verify the contacts. (FCT2-15288 & FCT2-15290 & FCT2-15291)
========
  Scenario: Add contacts to victims and then verify the contacts. (FCT2-15288 & FCT2-15290)
>>>>>>>> 6aca73a9d25cc0d9b2e2453fb50aae44012b5097:src/test/resources/features/api/victimCaseApp/S5_S6_S7_API-add_update_case_contacts_to_victim.feature
    Given victim details are available in VCA
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    When the following cps-contacts are added to "victim" in VCA
      | cpsContactType                        |
      | Family Liaison Officer                |
      | Independent Sexual Violence Adviser   |
      | Independent Domestic Violence Adviser |
    Then the added cps-contacts for "victim" are verified

  @updateCpsContactToVictim
<<<<<<<< HEAD:src/test/resources/features/api/victimCaseApp/S05_S06_S07_API-add_update_case_contacts_to_victim.feature
  Scenario: Update the existing cps-contacts for victims and then verify. (FCT2-15288 & FCT2-15290 & FCT2-15291)
========
  Scenario: Update the existing cps-contacts for victims and then verify. (FCT2-15291)
>>>>>>>> 6aca73a9d25cc0d9b2e2453fb50aae44012b5097:src/test/resources/features/api/victimCaseApp/S5_S6_S7_API-add_update_case_contacts_to_victim.feature
    Given victim details are available in VCA
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    And the following cps-contacts are added to "victim" in VCA
      | cpsContactType                        |
      | Family Liaison Officer                |
    When the following cps-contacts are updated to "victim" in VCA
      | cpsContactType                        |
      | Family Liaison Officer                |
    Then the updated cps-contacts for "victim" are verified