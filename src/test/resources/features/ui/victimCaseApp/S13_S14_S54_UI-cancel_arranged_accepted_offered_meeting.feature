@regression @ui_vca_regression @VCA_UI_S13 @VCA_UI_S14 @VCA_UI_S54

Feature: VCA-UI-S13-S14-S54:- Cancel the offered then accepted then arranged victim or victimWitness meeting.
  As a Victim Liaison Officer
  I want to record the cancellation details for arranged victim or victimWitness meeting.
  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  ¦ Meeting Type                            | Meeting Method       | Meeting Arrangement               | Meeting Type   | Location Type        | Meeting Attendees       | Meeting Cancellation      ¦
  ¦-----------------------------------------|----------------------|-----------------------------------|----------------|----------------------|-------------------------|---------------------------¦
  ¦ 1.CPS pre-trial meeting                 | 1.Letter by post     | 1.CPS offered and Victim accepted | 1.In person    | 1.CPS location       | 1.Counsel               | 1.Victim withdrew         ¦
  ¦ 2.Inform victim about charging decision | 2.Letter by email    | 2.Victim requested                | 2.Virtual call | 2.Magistrates court  | 2.Victim Liaison Officer| 2.Meeting created in error¦
  ¦ 3.Other CPS meeting                     | 3.Letter by police   | 3.Requested by third party        | 3.Hybrid       | 3.Crown court        | 3.Officer in Charge     | 3.Change of court date    ¦
  ¦                                         | 4.Letter by ISVA     |                                   |                | 4.Police station     | 4.Defence Solicitor     | 4.No longer required      ¦
  ¦                                         | 5.By telephone       |                                   |                | 5.Other              | 4.Defence firm          | 5.Other Reason            ¦
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Verify that meeting cancellation details are recorded

  Background: Create cases with multi defendant with multi charge with victim and witness.
    Given create new case using "CM01" for type "single suspect multiple offence"
    And add "victim witness" using "LM04" for the case
    And add "victim witness child" using "LM04" for the case
    And the "victimWitness" person details are available in CMS
    And the "victimWitnessChild" person details are available in CMS

  @ui @ui_meetingCancellation
  Scenario: Victim or VictimWitness Cancel an offered, accepted and arranged meeting types. (FCT2-15847 & FCT2-15835 & FCT2-20835)
    Given VLO login to victim case application
    And "victimWitness" is searched using case reference
    And the "victimWitness" is onboarded to "Universal" service lead
    And select "No task at this time" for next task for the "victimWitness"
    And verify that "victimWitness" is onboarded
    And the "Murali" is assigned as VLO to "victimWitness"
    And verify that Vlo "Murali" is assigned to "victimWitness"
    And "victimWitnessChild" is searched using case reference
    And the "victimWitnessChild" is onboarded to "Universal" service lead
    And select "No task at this time" for next task for the "victimWitnessChild"
    And verify that "victimWitnessChild" is onboarded
    And the "Murali" is assigned as VLO to "victimWitnessChild"
    And verify that Vlo "Murali" is assigned to "victimWitnessChild"
    When the meeting is offered and meeting response is logged for "victimWitness" in VCA application
      | meetingType                           | offerMethod     | meetingResponse        | meetingResponseMethod |
      | CPS pre-trial meeting                 | Letter by post  | Log meeting acceptance | Letter by email       |
      | Inform victim about charging decision | Letter by email | Log meeting acceptance | Letter by post        |
      | Other                                 | By telephone    | Log meeting acceptance | By telephone          |
    When the meeting is offered and meeting response is logged for "victimWitnessChild" in VCA application
      | meetingType                           | offerMethod      | meetingResponse        | meetingResponseMethod |
      | CPS pre-trial meeting                 | By telephone     | Log meeting acceptance | Letter by police      |
      | Inform victim about charging decision | Letter by police | Log meeting acceptance | By telephone          |
      | Other                                 | Letter by ISVA   | Log meeting acceptance | Letter by ISVA        |
    When the meeting is arranged with meeting method and meeting chair person for "victimWitness" in VCA application
      | meetingType                           | meetingSource         | meetingMethod | locationType | locationName                    | meetingAttendeesRoles  | meetingChairPerson     | supportNeeds      |
      | CPS pre-trial meeting                 | CPS Offered           | In Person     | CPS location | Petty France                    | Counsel                | Victim liaison officer | Interpreter       |
      | Inform victim about charging decision | Victim Requested      | Virtual call  | Virtual call | Virtual call                    | Victim liaison officer | Counsel                | Someone to attend |
      | Other                                 | Third party requested | Hybrid        | Crown court  | Newcastle upon Tyne Crown Court | Officer in Charge      | Officer in Charge      |                   |
    When the meeting is arranged with meeting method and meeting chair person for "victimWitnessChild" in VCA application
      | meetingType                           | meetingSource         | meetingMethod | locationType      | locationName                           | meetingAttendeesRoles  | meetingChairPerson     | supportNeeds |
      | CPS pre-trial meeting                 | Victim Requested      | Hybrid        | Magistrates court | Newcastle upon Tyne Magistrates' Court | Defence firm           | Defence Solicitor      | Interpreter  |
      | Inform victim about charging decision | Third party requested | In Person     | Other             | Other Location text                    | Defence Solicitor      | Victim liaison officer |              |
      | Other                                 | CPS Offered           | Hybrid        | Police station    | Northumbria                            | Victim liaison officer | Defence firm           |              |
#      # CPS location :- #Petty France        # Magistrates court :-  #Newcastle upon Tyne Magistrates' Court
#      # Crown court  :- #Newcastle upon Tyne Crown Court       # Police station:- #Northumbria     # Other :- #Other Location text
    When cancel the arranged meeting with the cancellation reason for "victimWitness" in VCA application
      | meetingType                           | cancellationReason       |
      | CPS pre-trial meeting                 | Victim withdrew          |
      | Inform victim about charging decision | Meeting created in error |
      | Other                                 | Change of court date     |
    When cancel the arranged meeting with the cancellation reason for "victimWitnessChild" in VCA application
      | meetingType                           | cancellationReason |
      | CPS pre-trial meeting                 | Victim withdrew    |
      | Inform victim about charging decision | No longer required |
      | Other                                 | Other              |
    Then verify the cancelled meeting details for "victimWitness" in VCA application
      | meetingType                           | cancellationReason       |
      | CPS pre-trial meeting                 | Victim withdrew          |
      | Inform victim about charging decision | Meeting created in error |
      | Other                                 | Change of court date     |
    Then verify the cancelled meeting details for "victimWitnessChild" in VCA application
      | meetingType                           | cancellationReason |
      | CPS pre-trial meeting                 | Victim withdrew    |
      | Inform victim about charging decision | No longer required |
      | Other                                 | Other              |

#  cancellationReason :- 1.#Victim withdrew 2.#Meeting created in error 3.#Change of court date 4.#No longer required 5.#Other Cancel Reason
#  meetingType :- 1.#CPS pre-trial meeting  2.#Inform victim about charging decision 3.#Other
#  meetingSource :- 1.#CPS Offered 2.#Victim Requested 3.#Third party requested
#  meetingMethod :- 1.#In person 2.#Virtual call 3.#Hybrid
# NOTE:- Enter #Virtual call as locationType and locationName for 2.#Virtual call meetingMethod
# NOTE:- locationType and locationName is required for 1.#In person and 3.#Hybrid meetingMethod
#  locationType :- 1.#CPS location  2.#Magistrates court 3.#Crown court 4.#Police station 5.#other
#  locationName - Enter location Name as equal to defined list
#                  #exmaple :- 1.#CPS location == Petty France
#                              2.#Magistrates court == Newcastle upon Tyne Magistrates' Court
#                              3.#Crown court == Newcastle upon Tyne Crown Court
#                              4.#Police station == Northumbria
#                              5.#Other == Other Location text
#      #offerMethod  = #Letter by post #Letter by email #Letter by police #Letter by ISVA #By telephone
#      #ResponseMethod  = #Letter by post #Letter by email #Letter by police #Letter by ISVA #By telephone
