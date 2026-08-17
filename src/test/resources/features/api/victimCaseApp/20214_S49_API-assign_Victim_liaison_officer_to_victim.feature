@regression @vca_api_regression @VCA_API_S49

Feature: VCA-API-S49 - Add Victim liaison officer (VLO) to victims.
  As a Victim Liaison Officer
  I want to add Victim liaison officer (VLO) to victim
  Verify that Victim liaison officer (VLO) to victim

  Background: Create cases with single defendant with multi charge with victim.
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

  @addCaseContactsToVictim
  Scenario: Add Victim liaison officer (VLO) to victims then verify
    Given victim details are available in VCA
    When the "victim" is onboarded as "Universal" service lead in VCA
    Then the Victim liaison officer is assigned to "victim" in VCA
