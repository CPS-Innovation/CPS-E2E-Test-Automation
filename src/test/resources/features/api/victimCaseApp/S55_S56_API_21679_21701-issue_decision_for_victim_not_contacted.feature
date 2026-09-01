@regression @vca_api_regression @VCA_API_S55 @VCA_API_S56

Feature: VCA-API-S56 & VCA-API-S56 - Issue decision communications when victim not contacted with a reason for victims.
  As a Victim Liaison Officer
  I want to record decision communications when victim not contacted with a reason.
  Verify that decision communication details are recorded.

  Background: Create cases with single defendant with multi charge with victim and witness
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

  @issueDecisionVictimNotContacted
  Scenario: Decision communication when victim not contacted with a reason is recorded
    Given victim details are available in VCA
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    When the following communication for victim not contacted is logged to "victim" in VCA
      | decisionType       |
      | Decision To Charge |
      | No Further Action  |
    Then verify the logged decision to charge communication for the "victim" in VCA
