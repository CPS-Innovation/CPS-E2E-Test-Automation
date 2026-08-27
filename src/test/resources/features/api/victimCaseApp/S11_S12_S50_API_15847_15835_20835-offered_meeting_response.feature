@regression @vca_api_regression @VCA_API_S11 @VCA_API_S12 @VCA_API_S50

Feature: VCA-API-S11-S12-S50 - Record the offered meetings response by victims
  As a Victim Liaison Officer
  I want to record the meetings response by victim for following type and method:-
  ------------------------------------------------------------------------------------------
  ¦ Meeting Type                                ¦ Meeting Method       ¦ Meeting Response  ¦
  ¦---------------------------------------------¦----------------------¦-------------------¦
  ¦ 1.CPS pre-trial meeting                     ¦ 1.Letter by post     ¦ 1.Accept          ¦
  ¦ 2.Inform victim about charging decision     ¦ 2.Letter by email    ¦ 2.Decline         ¦
  ¦ 3.VCL Scheme                                ¦ 3.Letter by police   ¦ 3.No Response     ¦
  ¦ 4.Victims' Right to Review (VRR)            ¦ 4.Letter by ISVA     ¦                   ¦
  ¦ 5.Victim complaint                          ¦ 5.By telephone       ¦                   ¦
  ¦ 6.Other CPS meeting                         ¦                      ¦                   ¦
  ------------------------------------------------------------------------------------------
  Verify that meeting response details are recorded

  Background: Create cases with single defendant with multi charge with victim and witness
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

  @meetingResponse
  Scenario: Victim decline an offered meeting for different meeting types
    Given victim details are available in VCA
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    And the following meetings are offered using following methods to "victim" in VCA
      | meetingType                           | offerMethod      |
      | CPS pre-trial meeting                 | Letter by post   |
      | Inform victim about charging decision | Letter by email  |
      | Victim Communication Liaison          | Letter by police |
      | Victims Right to Review               | Letter by ISVA   |
      | Victim complaint                      | By telephone     |
      | Other CPS meeting                     | Letter by email  |
    When the following offered meeting response from "victim" is recorded in VCA
      | meetingType                           | offerResponseMethod | offerResponse |
      | CPS pre-trial meeting                 | Letter by email     | Accepted      |
      | Inform victim about charging decision | Letter by police    | Declined      |
      | Victim Communication Liaison          | No Response         | No Response   |
      | Victims Right to Review               | By telephone        | Accepted      |
      | Victim complaint                      | Letter by email     | Declined      |
      | Other CPS meeting                     | No Response         | No Response   |
    Then meeting response is verified for "victim" is recorded in VCA

