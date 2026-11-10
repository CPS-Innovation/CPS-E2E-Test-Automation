@regression @ui_vca_regression @VCA_UI_S16

Feature: VCA-UI-S16:- Log the conducted arranged meeting outcome of victim or victimWitness meeting.
  As a Victim Liaison Officer
  I want to record the log the meeting outcome details for arranged victim or victimWitness meeting.
  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  ¦ Meeting Type                            | Meeting Method       | Meeting Arrangement               | Meeting Type   | Location Type        | Meeting Attendees       ¦
  ¦-----------------------------------------|----------------------|-----------------------------------|----------------|----------------------|-------------------------¦
  ¦ 1.CPS pre-trial meeting                 | 1.Letter by post     | 1.CPS offered and Victim accepted | 1.In person    | 1.CPS location       | 1.Counsel               ¦
  ¦ 2.Inform victim about charging decision | 2.Letter by email    | 2.Victim requested                | 2.Virtual call | 2.Magistrates court  | 2.Victim Liaison Officer¦
  ¦ 3.Other CPS meeting                     | 3.Letter by police   | 3.Requested by third party        | 3.Hybrid       | 3.Crown court        | 3.Officer in Charge     ¦
  ¦                                         | 4.Letter by ISVA     |                                   |                | 4.Police station     | 4.Defence Solicitor     ¦
  ¦                                         | 5.By telephone       |                                   |                | 5.Other              | 4.Defence firm          ¦
  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Verify that meeting outcome details are recorded

  Background: Create cases with multi defendant with multi charge with victim and witness.
    Given create new case using "CM01" for type "single suspect multiple offence"
    And add "victim witness" using "LM04" for the case
    And add "victim witness child" using "LM04" for the case
    And the "victimWitness" person details are available in CMS
    And the "victimWitnessChild" person details are available in CMS

  @ui @ui_meetingConducted
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
    When the "CPS pre-trial meeting" meeting type is arranged because "CPS Offered" it and meeting method is "In person" at "CPS location" in "Petty France" and "Victim Liaison Officer" as chair person with attendees for "victimWitness" in VCA application
      | meetingAttendeesRoles  | supportNeeds      |
      | Defence firm           | Interpreter       |
      | Victim liaison officer | Someone to attend |
      | Defence Solicitor      |                   |
      | Counsel                |                   |
      | Officer in Charge      |                   |
    When the "Inform victim about charging decision" meeting type is arranged because "Victim Requested" it and meeting method is "Virtual call" at "Virtual call" in "Virtual call" and "Counsel" as chair person with attendees for "victimWitness" in VCA application
      | meetingAttendeesRoles  | supportNeeds |
      | Defence firm           | Interpreter  |
      | Victim liaison officer |              |
      | Defence Solicitor      |              |
      | Counsel                |              |
      | Officer in Charge      |              |
    When the "Other" meeting type is arranged because "Third party requested" it and meeting method is "Hybrid" at "Magistrates court" in "Newcastle upon Tyne Magistrates' Court" and "Officer in Charge" as chair person with attendees for "victimWitness" in VCA application
      | meetingAttendeesRoles  | supportNeeds      |
      | Defence firm           | Someone to attend |
      | Victim liaison officer |                   |
      | Defence Solicitor      |                   |
      | Counsel                |                   |
      | Officer in Charge      |                   |
    When the "CPS pre-trial meeting" meeting type is arranged because "Victim Requested" it and meeting method is "In person" at "Crown court" in "Newcastle upon Tyne Crown Court" and "Defence firm" as chair person with attendees for "victimWitnessChild" in VCA application
      | meetingAttendeesRoles  | supportNeeds      |
      | Defence firm           | Interpreter       |
      | Victim liaison officer | Someone to attend |
      | Defence Solicitor      |                   |
      | Counsel                |                   |
      | Officer in Charge      |                   |
    When the "Inform victim about charging decision" meeting type is arranged because "Third party requested" it and meeting method is "Hybrid" at "Police station" in "Northumbria" and "Defence Solicitor" as chair person with attendees for "victimWitnessChild" in VCA application
      | meetingAttendeesRoles  | supportNeeds |
      | Defence firm           | Interpreter  |
      | Victim liaison officer |              |
      | Defence Solicitor      |              |
      | Counsel                |              |
      | Officer in Charge      |              |
    When the "Other" meeting type is arranged because "Victim Requested" it and meeting method is "In person" at "Other" in "Other Location text" and "Victim Liaison Officer" as chair person with attendees for "victimWitnessChild" in VCA application
      | meetingAttendeesRoles  | supportNeeds      |
      | Defence firm           | Someone to attend |
      | Victim liaison officer |                   |
      | Defence Solicitor      |                   |
      | Counsel                |                   |
      | Officer in Charge      |                   |
    When the arranged meeting is conducted with following details for "victimWitness" in VCA application
      | meetingType                           | meetingTookPlace | meetingAttendees       | meetingLedBy           | agreeToResearch    | meetingNotes | meetingNotesSent | actionsAgreed |
      | CPS pre-trial meeting                 | Yes              | Victim Liaison Officer | Victim Liaison Officer | Yes                | Yes          | Yes              | Yes           |
      | Inform victim about charging decision | Yes              | Counsel                | Counsel                | No                 | No           | No               | No            |
      | Other                                 | Yes              | Officer in Charge      | Officer in Charge      | Question Not Asked | Yes          | Yes              | Yes           |
    When the arranged meeting is conducted with following details for "victimWitnessChild" in VCA application
      | meetingType                           | meetingTookPlace | meetingAttendees  | meetingLedBy      | agreeToResearch    | meetingNotes | meetingNotesSent | actionsAgreed |
      | CPS pre-trial meeting                 | Yes              | Defence firm      | Defence firm      | No                 | No           | No               | No            |
      | Inform victim about charging decision | Yes              | Defence Solicitor | Defence Solicitor | Question Not Asked | No           | No               | No            |
      | Other                                 | Yes              | Victim            | Victim            | No                 | No           | Yes              | Yes           |
    Then verify the logged meeting outcome details for "victimWitness" in VCA application
      | meetingType                           | agreeToResearch    | meetingNotes | meetingNotesSent | actionsAgreed |
      | CPS pre-trial meeting                 | Yes                | Yes          | Yes              | Yes           |
      | Inform victim about charging decision | No                 | No           | No               | No            |
      | Other                                 | Question Not Asked | Yes          | Yes              | Yes           |
    Then verify the logged meeting outcome details for "victimWitnessChild" in VCA application
      | meetingType                           | agreeToResearch    | meetingNotes | meetingNotesSent | actionsAgreed |
      | CPS pre-trial meeting                 | No                 | No           | No               | No            |
      | Inform victim about charging decision | Question Not Asked | No           | No               | No            |
      | Other                                 | No                 | No           | Yes              | Yes           |

#   #meetingType :- 1.#CPS pre-trial meeting  2.#Inform victim about charging decision 3.#Other
#   #meetingSource :- 1.#CPS Offered 2.#Victim Requested 3.#Third party requested
#   #meetingMethod :- 1.#In person 2.#Virtual call 3.#Hybrid
#     #NOTE:- Enter #Virtual call as locationType and locationName for 2.#Virtual call meetingMethod
#     #NOTE:- locationType and locationName is required for 1.#In person and 3.#Hybrid meetingMethod
#   #locationType :- 1.#CPS location  2.#Magistrates court 3.#Crown court 4.#Police station 5.#other
#   #locationName - Enter location Name as equal to defined list
#                  #exmaple :- 1.#CPS location == Petty France
#                              2.#Magistrates court == Newcastle upon Tyne Magistrates' Court
#                              3.#Crown court == Newcastle upon Tyne Crown Court
#                              4.#Police station == Northumbria
#                              5.#Other == Other Location text
#   #offerMethod  = #Letter by post #Letter by email #Letter by police #Letter by ISVA #By telephone
#   #ResponseMethod  = #Letter by post #Letter by email #Letter by police #Letter by ISVA #By telephone