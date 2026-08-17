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
    When the following meetings are not offered to "victim" in VCA
      | meetingType                             | notOfferedReason                                             |
      | CPS pre-trial meeting                   | CPS pre-trial meeting - Not Offered reason                   |
      | Inform victim about charging decision   | Inform victim about charging decision - Not Offered reason   |
      | Stopped or Substantially altered charge | Stopped or Substantially altered charge - Not Offered reason |
      | Victims Right to Review                 | Victims Right to Review - Not Offered reason                 |
      | Victim complaint                        | Victim complaint - Not Offered reason                        |
      | Other CPS meeting                       | Other CPS meeting -  Not Offered reason                      |
  Then meetings not offered to "victim" are verified
