@regression @vca_api_regression @VCA_API_S10

Feature: VCA-API-S10 - Meetings are not offered to victims for different meeting types with a reason
  As a Victim Liaison Officer
  I want to record the meetings is not being offered with reasons for following types:-
  1. CPS pre-trial meeting
  2. Inform victim about charging decision
  3. Stopped or Substantially altered charge (VCL Scheme)
  4. Victims' Right to Review (VRR)
  5. Victim complaint
  6. Other CPS meeting
  Verify that not offered meeting details are recorded

  Background: Create cases with single defendant with multi charge with victim and witness
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

  @ptmNotOffered
  Scenario: Meetings are not offered to victims for different meeting types with a reason
    Given victim details are available in VCA
    When the "victim" is onboarded as "Universal" service lead in VCA
    Then the Victim liaison officer is assigned to "victim" in VCA
    When the following meetings is not offered to "victimId" in VCA
      | meeting                                 | meetingTypeCode | reason                                                       |
      | CPS pre-trial meeting                   | 1               | CPS pre-trial meeting - Not Offered reason                   |
      | Inform victim about charging decision   | 2               | Inform victim about charging decision - Not Offered reason   |
      | Stopped or Substantially altered charge | 3               | Stopped or Substantially altered charge - Not Offered reason |
      | Victims Right to Review                 | 4               | Victims Right to Review - Not Offered reason                 |
      | Victim complaint                        | 5               | Victim complaint - Not Offered reason                        |
      | Other CPS meeting                       | 99              | Other CPS meeting -  Not Offered reason                      |
    Then the offered meeting details of "victimId" is verified in VCA
