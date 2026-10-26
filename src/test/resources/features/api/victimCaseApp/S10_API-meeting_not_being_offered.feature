@regression @api_vca_regression @VCA_API_S10

Feature: VCA-API-S10:- Meetings of different types is not offered to victims with a reason
  As a Victim Liaison Officer
  I want to record the meeting details that are not being offered with reasons for following meeting types:-
  1. CPS pre-trial meeting
  2. Inform victim about charging decision
  3. Victim Communication Liaison (VCL Scheme)
#  4. Victims' Right to Review (VRR)
#  5. Victim complaint
#  6. Other CPS meeting
  Verify that not offered meeting details are recorded

  Background: Create cases with single defendant with multi charge with victim and witness
    Given create new case using "CM01" for type "single suspect multiple offence"
    And add "victim" using "LM04" for the case

  @ptmNotOffered
  Scenario: Meetings of different types is not offered to victims with a reason. (FCT2-15295)
    Given victim details are available in VCA
    When the "victim" is onboarded as "Universal" service lead in VCA
    Then the Victim liaison officer is assigned to "victim" in VCA
    When the following meetings are not offered to "victim" in VCA
      | meetingType                           | notOfferedReason                                           |
      | CPS pre-trial meeting                 | CPS pre-trial meeting - Not Offered reason                 |
      | Inform victim about charging decision | Inform victim about charging decision - Not Offered reason |
      | Other CPS meeting                     | Other CPS meeting -  Not Offered reason                    |
#      | Victim Communication Liaison          | Victim Communication Liaison - Not Offered reason          |
#      | Victims Right to Review               | Victims Right to Review - Not Offered reason               |
#      | Victim complaint                      | Victim complaint - Not Offered reason                      |

    Then meetings not offered to "victim" are verified
