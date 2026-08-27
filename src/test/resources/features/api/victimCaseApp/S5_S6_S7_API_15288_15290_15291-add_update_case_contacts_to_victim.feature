@regression @vca_api_regression @VCA_API_S5 @VCA_API_S6 @VCA_API_S7

Feature: VCA-API-S5_S6_S7 - Add and update cps-contacts to victims
  As a Victim Liaison Officer
  I want to add cps-contacts to victim
  I want to update cps-contacts to victim
  Verify that cps-contacts are added to victim
  Verify that cps-contacts are updated to victim

  Background: Create cases with multi defendant with multi charge with victim and witness.
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case


  @addCpsContactToVictim
  Scenario: Add contacts to victims and then verify the contacts
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
  Scenario: Update the existing cps-contacts for victims and then verify
    Given victim details are available in VCA
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    And the following cps-contacts are added to "victim" in VCA
      | cpsContactType                        |
      | Family Liaison Officer                |
      | Independent Sexual Violence Adviser   |
      | Independent Domestic Violence Adviser |
    When the following cps-contacts are updated to "victim" in VCA
      | cpsContactType                        |
      | Family Liaison Officer                |
      | Independent Sexual Violence Adviser   |
      | Independent Domestic Violence Adviser |
    Then the updated cps-contacts for "victim" are verified