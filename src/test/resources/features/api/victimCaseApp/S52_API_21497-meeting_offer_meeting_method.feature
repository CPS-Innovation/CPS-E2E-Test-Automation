@regression @vca_api_regression @VCA_API_S52

Feature: VCA-API-S11 - Offer meetings via meeting methods to victims
  As a Victim Liaison Officer
  I want to record the following meeting type offered via meeting method to victim:-
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
  Verify that meeting offered and meeting method details are recorded

  Background: Create cases with single defendant with multi charge with victim and witness
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

  @ptmDeclined
  Scenario: Offer different meeting types via meeting method types for a victim
    Given victim details are available in VCA
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    When the following meetings are offered using following methods to "victim" in VCA
      | meetingType                           | offerMethod      |
      | CPS pre-trial meeting                 | Letter by post   |
      | Inform victim about charging decision | Letter by email  |
      | Victim Communication Liaison          | Letter by police |
      | Victims Right to Review               | Letter by ISVA   |
      | Victim complaint                      | By telephone     |
      | Other CPS meeting                     | Letter by email  |
    Then offered meeting type and method is verified for "victim" in VCA

