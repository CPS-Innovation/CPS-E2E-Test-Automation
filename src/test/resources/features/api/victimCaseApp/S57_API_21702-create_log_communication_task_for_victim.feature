@regression @vca_api_regression @VCA_API_S57

Feature: VCA-API-S57 -  Create task to log communication for victims in Victim Case Application.
  As a Victim Liaison Officer
  I want to create task to log communication for victim.
  Verify that task is created.

  Background: Create cases with single defendant with multi charge with victim and witness
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

  @issueDecisionVictimNotContacted
  Scenario: Create tasks to log communication for victim in Victim Case Application
    Given victim details are available in VCA
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    When the following tasks to log communication are created for "victim" in VCA
      | taskType                               |
      | Inform of a decision to charge         |
      | Inform of a no further action decision |
    Then verify the task to log communication for the "victim" in VCA