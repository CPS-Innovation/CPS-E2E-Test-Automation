@regression @VCA_API_S51

Feature: VCA-API-S51 - Arrange the accepted meeting offer by victim with details.
  As a Victim Liaison Officer
  I want to record the meeting arrangement details for offered and accepted meeting by victim for following:-
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
    Given witness and victim details are available
    And the "victimId" is onboarded to VCA
    And the Victim liaison officer is assigned to "victimId" in VCA
    And the following meetings via meeting method is offered to "victimId" in VCA
      | meeting                                 | meetingTypeCode | method           | methodTypeCode |
      | CPS pre-trial meeting                   | 1               | Letter by post   | 1              |
      | Inform victim about charging decision   | 2               | Letter by email  | 2              |
      | Stopped or Substantially altered charge | 3               | Letter by police | 3              |
      | Victims Right to Review                 | 4               | Letter by ISVA   | 4              |
      | Victim complaint                        | 5               | By telephone     | 5              |
      | Other CPS meeting                       | 99              | Letter by email  | 5              |
    And offered meetings is "Accepted" by "victimId" in VCA
    When the "In-person" meeting is arranged with following details
      | meeting                                 | meetingTypeCode | method           | methodTypeCode | meetingSource              | meetingSourceCode | meetingType | methodTypeCode | LocationType      | LocationTypeCode | LocationName                           |
      | CPS pre-trial meeting                   | 1               | Letter by post   | 1              | CPS offered                | 1                 | In-person   | 1              | CPS location      | 1                | Petty France                           |
      | Inform victim about charging decision   | 2               | Letter by email  | 2              | Victim requested           | 2                 | Hybrid      | 2              | Magistrates court | 2                | Newcastle upon Tyne Magistrates' Court |
      | Stopped or Substantially altered charge | 3               | Letter by police | 3              | Requested by a third party | 3                 | In-person   | 3              | Crown court       | 3                | Newcastle upon Tyne Crown Court        |
      | Victims Right to Review                 | 4               | Letter by ISVA   | 4              | CPS offered                | 1                 | Hybrid      | 3              | Police station    | 4                | Northumbria                            |
      | Victim complaint                        | 5               | By telephone     | 5              | Victim requested           | 2                 | In person   | 1              | Other             | 5                | Test Automation                        |
      | Other CPS meeting                       | 99              | Letter by email  | 5              | Requested by a third party | 3                 | Hybrid      | 2              | CPS location      | 1                | Petty France                           |
    And the following meeting attendees are added
    Then the meeting arragement details of "victimId" is verified in VCA



