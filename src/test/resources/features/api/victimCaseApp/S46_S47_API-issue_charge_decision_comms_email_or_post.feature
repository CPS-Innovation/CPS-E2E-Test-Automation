@regression @vca_api_regression @VCA_API_S46 @VCA_API_S47

Feature: VCA-API-S24 - Issue charge decision communication by email or post for victims in Victim Case Application.
  As a Victim Liaison Officer
  I want to issue charge decision communication by email or post for victim.
  verify that charge decision communication is logged.

  Background: Create cases with single defendant with multi charge with victim and witness
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

  @issueChargeDecision
  Scenario: Log charge decision communication where first call attempt was successful and follow up communication is sent in email for victim
    Given victim details are available in VCA
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    When follow up as below for "victim" in VCA
      | journeyType                            | followUpMethod | notes        |
      | Inform of a no further action decision | Post           | Post - Notes |
      #  journeyType :- Inform of a no further action decision or #  Inform of a decision to charge
      #  followUpMethod :- Email or Post
    Then verify the charge decision communication for "victim" in VCA
