@regression @vca_api_regression @VCA_API_S15 @VCA_API_S51 @VCA_API_S53

Feature: VCA-API-S15-S51-S53 - Arrange the accepted meeting offer to victim with details.
  As a Victim Liaison Officer
  I want to record the meeting arrangement details for accepted offered meeting to victim for following:-
  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  ¦ Meeting Type                            | Meeting Method       | Meeting Arrangement               | Meeting Type   | Location Type        | Meeting Attendees       |
  ¦-----------------------------------------|----------------------|-----------------------------------|----------------|----------------------|-------------------------|
  ¦ 1.CPS pre-trial meeting                 | 1.Letter by post     | 1.CPS offered and Victim accepted | 1.In person    | 1.CPS location       | 1.Counsel               |
  ¦ 2.Inform victim about charging decision | 2.Letter by email    | 2.Victim requested                | 2.Virtual call | 2.Magistrates' court | 2.Victim Liaison Officer|
  ¦ 3.VCL Scheme                            | 3.Letter by police   | 3.Requested by third party        | 3.Hybrid       | 3.Crown court        | 3.Officer in Charge     |
  ¦ 4.Victims' Right to Review (VRR)        | 4.Letter by ISVA     |                                   |                | 4.Police station     | 4.Defence Solicitor     |
  ¦ 5.Victim complaint                      | 5.By telephone       |                                   |                | 5. Other             | 5.Defence firm          |
  ¦ 6.Other CPS meeting                     |                      |                                   |                |                      |                         |
  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Verify that meeting acceptance details are recorded

  Background: Create cases with single defendant with multi charge with victim and witness
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

  @meetingInPersonArrange
  Scenario: Victim accepts an offered meeting for different meeting types
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
    And the following offered meeting response from "victim" is recorded in VCA
      | meetingType                           | offerResponseMethod | offerResponse |
      | CPS pre-trial meeting                 | Letter by email     | Accepted      |
      | Inform victim about charging decision | Letter by post      | Accepted      |
      | Victims Right to Review               | Letter by post      | Accepted      |
      | Victim complaint                      | By telephone        | Accepted      |
      | Other CPS meeting                     | Letter by email     | Accepted      |
      | Victim Communication Liaison          | Letter by police    | Accepted      |
    When the accepted meeting is arranged using following for "victim" in VCA
      | meetingType                           | meetingSource              | meetingMethod | locationType      | locationName                           |
      | CPS pre-trial meeting                 | Victim Requested           | In Person     | CPS location      | Petty France                           |
      | Inform victim about charging decision | CPS Offered                | Hybrid        | Magistrates court | Newcastle upon Tyne Magistrates' Court |
      | Victims Right to Review               | Requested by a Third party | In Person     | Crown court       | Newcastle upon Tyne Crown Court        |
      | Victim complaint                      | Victim requested           | Hybrid        | Other             | Specify Location                       |
      | Other CPS meeting                     | CPS offered                | Virtual Call  | Virtual Call      |                                        |
      | Victim Communication Liaison          | Requested by a third party | Virtual Call  | Virtual Call      |                                        |
    And the meeting attendees are added and make "Victim liaison officer" as chair person for "victim" meeting
      | meetingAttendeesRoles  |
      | Counsel                |
      | Victim liaison officer |
      | Defence Solicitor      |
      | Officer in Charge      |
    Then the arranged meeting and attendees details for "victim" in verified
#    TODO - Need to fix the assertion

