@regression @ui_vca_regression @VCA_UI_S13 @VCA_UI_S14 @VCA_UI_S54

Feature: VCA-UI-S13-S14-S54:- Cancel the offered then accepted then arranged  meeting.
  As a Victim Liaison Officer
  I want to record the cancellation details for arranged victim or victimWitness meeting.
  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  ¦ Meeting Type                            | Meeting Method       | Meeting Arrangement               | Meeting Type   | Location Type        | Meeting Attendees       | Meeting Cancellation      ¦
  ¦-----------------------------------------|----------------------|-----------------------------------|----------------|----------------------|-------------------------|---------------------------¦
  ¦ 1.CPS pre-trial meeting                 | 1.Letter by post     | 1.CPS offered and Victim accepted | 1.In person    | 1.CPS location       | 1.Counsel               | 1.Victim withdrew         ¦
  ¦ 2.Inform victim about charging decision | 2.Letter by email    | 2.Victim requested                | 2.Virtual call | 2.Magistrates' court | 2.Victim Liaison Officer| 2.Meeting created in error¦
  ¦ 3.Other CPS meeting                     | 3.Letter by police   | 3.Requested by third party        | 3.Hybrid       | 3.Crown court        | 3.Officer in Charge     | 3.Change of court date    ¦
  ¦                                         | 4.Letter by ISVA     |                                   |                | 4.Police station     | 4.Defence Solicitor     | 4.No longer required      ¦
  ¦                                         | 5.By telephone       |                                   |                | 5. Other             |                         | 5.Other Reason            ¦
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Verify that meeting cancellation details are recorded

#  Background: Create cases with multi defendant with multi charge with victim and witness.
#    Given create new case using "CM01" for type "single suspect multiple offence"
#    And add "victim witness" using "LM04" for the case
#    And add "victim witness child" using "LM04" for the case
#    And the "victimWitness" person details are available in CMS
#    And the "victimWitnessChild" person details are available in CMS

  @ui @ui_meetingCancellation
  Scenario: Victim or VictimWitness Cancel an offered, accepted and arranged meeting types. (FCT2-15847 & FCT2-15835 & FCT2-20835)
    Given VLO login to victim case application
#    And "victimWitness" is searched using case reference
#    And the "victimWitness" is onboarded to "Universal" service lead
#    And select "No task at this time" for next task for the "victimWitness"
#    And verify that "victimWitness" is onboarded
#    And the "Murali" is assigned as VLO to "victimWitness"
#    And verify that Vlo "Murali" is assigned to "victimWitness"
#    And "victimWitnessChild" is searched using case reference
#    And the "victimWitnessChild" is onboarded to "Universal" service lead
#    And select "No task at this time" for next task for the "victimWitnessChild"
#    And verify that "victimWitnessChild" is onboarded
#    And the "Murali" is assigned as VLO to "victimWitnessChild"
#    And verify that Vlo "Murali" is assigned to "victimWitnessChild"
    When the meeting is offered and meeting response is logged for "victimWitness" in VCA application
      | meetingType                           | offerMethod     | meetingResponse        | meetingResponseMethod |
      | CPS pre-trial meeting                 | Letter by post  | Log meeting acceptance | Letter by email       |
      | Inform victim about charging decision | Letter by email | Log meeting acceptance | Letter by post        |
      | Other                                 | By telephone    | Log meeting acceptance | By telephone          |
#    When the meeting is offered and meeting response is logged for "victimWitnessChild" in VCA application
#      | meetingType                           | offerMethod      | meetingResponse        | meetingResponseMethod |
#      | CPS pre-trial meeting                 | By telephone     | Log meeting acceptance | Letter by police      |
#      | Inform victim about charging decision | Letter by police | Log meeting acceptance | By telephone          |
#      | Other                                 | Letter by ISVA   | Log meeting acceptance | Letter by ISVA        |

    When the "CPS pre-trial meeting" arranged because "Victim Requested" it and meeting method is "In Person" at "locationtype" in "locationName" and "Victim liaison officer" as chair person with attendees for "victimWitness" in VCA application
      | meetingAttendeesRoles  |
      | Counsel                |
      | Victim liaison officer |
      | Defence Solicitor      |
      | Officer in Charge      |

#      | meetingType                           | meetingSource              | meetingMethod | locationType      | locationName                           |
#      | CPS pre-trial meeting                 | CPS pre-trial meeting      | In Person     | CPS location      | Petty France                           |
#      | Inform victim about charging decision | CPS Offered                | Hybrid        | Magistrates court | Newcastle upon Tyne Magistrates' Court |
#      | Other                                 | Third party Requested | Virtual Call  | Virtual Call      |                                        |
#


#      #offerMethod  = #Letter by post #Letter by email #Letter by police #Letter by ISVA #By telephone
#      #ResponseMethod  = #Letter by post #Letter by email #Letter by police #Letter by ISVA #By telephone

#
#
#  @meetingCancellationReason
#  Scenario: Victim accepts an offered meeting for different meeting types. (FCT2-16211 & FCT2-16215 & FCT2-21805)
#    Given victim details are available in VCA
#    And the "victim" is onboarded as "Universal" service lead in VCA
#    And the Victim liaison officer is assigned to "victim" in VCA
#    And the following meetings are offered using following methods to "victim" in VCA
#      | meetingType                           | offerMethod      |
#      | CPS pre-trial meeting                 | Letter by post   |
#      | Inform victim about charging decision | Letter by email  |
#      | Victim Communication Liaison          | Letter by police |
#      | Victims Right to Review               | Letter by ISVA   |
#      | Victim complaint                      | By telephone     |
#      | Other CPS meeting                     | Letter by email  |
#    And the following offered meeting response from "victim" is recorded in VCA
#      | meetingType                           | offerResponseMethod | offerResponse |
#      | CPS pre-trial meeting                 | Letter by email     | Accepted      |
#      | Inform victim about charging decision | Letter by post      | Accepted      |
#      | Victims Right to Review               | Letter by post      | Accepted      |
#      | Victim complaint                      | By telephone        | Accepted      |
#      | Other CPS meeting                     | Letter by email     | Accepted      |
#      | Victim Communication Liaison          | Letter by police    | Accepted      |
#    And the accepted meeting is arranged using following for "victim" in VCA
#      | meetingType                           | meetingSource              | meetingMethod | locationType      | locationName                           |
#      | CPS pre-trial meeting                 | Victim Requested           | In Person     | CPS location      | Petty France                           |
#      | Inform victim about charging decision | CPS Offered                | Hybrid        | Magistrates court | Newcastle upon Tyne Magistrates' Court |
#      | Victims Right to Review               | Requested by a Third party | In Person     | Crown court       | Newcastle upon Tyne Crown Court        |
#      | Victim complaint                      | Victim requested           | Hybrid        | Other             | Specify Location                       |
#      | Other CPS meeting                     | CPS offered                | Virtual Call  | Virtual Call      |                                        |
#      | Victim Communication Liaison          | Requested by a third party | Virtual Call  | Virtual Call      |                                        |
    And the meeting attendees are added and make "Victim liaison officer" as chair person for "victim" meeting
      | meetingAttendeesRoles  |
      | Counsel                |
      | Victim liaison officer |
      | Defence Solicitor      |
      | Officer in Charge      |
#    When the arranged meeting is cancelled with a reason for "victim" in VCA
#      | meetingType                           | cancellationReason       |
#      | CPS pre-trial meeting                 | Victim withdrew          |
#      | Inform victim about charging decision | Meeting created in error |
#      | Victims Right to Review               | Change of court date     |
#      | Victim complaint                      | No longer required       |
#      | Other CPS meeting                     | Other Cancel Reason      |
#      | Victim Communication Liaison          | Victim withdrew          |
#    Then the cancelled meeting details for "victim" in verified
