@regression @VCA_API_S11

Feature: VCA-API-S50 - Acceptance of offered meetings by victims
  As a Victim Liaison Officer
  I want to record the meeting acceptance by victim for following type and method:-
  ----------------------------------------------------------------------
  ¦ Meeting Type                                ¦ Meeting Method       ¦
  ¦---------------------------------------------¦----------------------¦
  ¦ 1.CPS pre-trial meeting                     ¦ 1.Letter by post     ¦
  ¦ 2.Inform victim about charging decision     ¦ 2.Letter by email    ¦
  ¦ 3.VCL Scheme                                ¦ 3.Letter by police   ¦
  ¦ 4.Victims' Right to Review (VRR)            ¦ 4.Letter by ISVA     ¦
  ¦ 5.Victim complaint                          ¦ 5.By telephone       ¦
  ¦ 6.Other CPS meeting                         ¦                      ¦
  ----------------------------------------------------------------------
  Verify that meeting declined details are recorded

  Background: Create cases with single defendant with multi charge with victim and witness
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

  @ptmDeclined
  Scenario: Victim accepts an offered meeting for different meeting types
    Given witness and victim details are available
    And the "victimId" is onboarded to VCA
    And the Victim liaison officer is assigned to "victimId" in VCA
    When the following meetings via meeting method is offered to "victimId" in VCA
      | meeting                                 | meetingTypeCode | method           | methodTypeCode |
      | CPS pre-trial meeting                   | 1               | Letter by post   | 1              |
      | Inform victim about charging decision   | 2               | Letter by email  | 2              |
      | Stopped or Substantially altered charge | 3               | Letter by police | 3              |
      | Victims Right to Review                 | 4               | Letter by ISVA   | 4              |
      | Victim complaint                        | 5               | By telephone     | 5              |
      | Other CPS meeting                       | 99              | Letter by email  | 5              |
    And offered meetings is declined by "victimId" in VCA
    Then the declined meeting details of "victimId" is verified in VCA
