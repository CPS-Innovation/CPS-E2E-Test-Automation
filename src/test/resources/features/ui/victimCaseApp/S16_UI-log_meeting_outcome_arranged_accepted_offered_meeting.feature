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
      | meetingType                           | meetingTookPlace | meetingAttendees  | meetingLedBy           | agreeToResearch    | meetingNotes | meetingNotesSent | actionsAgreed |
      | CPS pre-trial meeting                 | Yes              | Defence firm      | Victim Liaison Officer | Yes                | Yes          | Yes              | Yes           |
      | Inform victim about charging decision | Yes              | Defence Solicitor | Counsel                | No                 | No           | No               | No            |
      | Other                                 | Yes              | Counsel           | Officer in Charge      | Question Not Asked | Yes          | Yes              | Yes           |

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












#  Background: Create cases with single defendant with multi charge with victim and witness
#    Given create new case using "CM01" for type "single suspect multiple offence"
#    And add "victim" using "LM04" for the case
#
#  @meetingOutcome
#  Scenario: Victim accepts an offered meeting for different meeting types. (FCT2-16226)
#    Given victim details are available in VCA
#    And the "victim" is onboarded as "Universal" service lead in VCA
#    And the Victim liaison officer is assigned to "victim" in VCA
#    And the following meetings are offered using following methods to "victim" in VCA
#      | meetingType                           | offerMethod     |
#      | CPS pre-trial meeting                 | Letter by post  |
#      | Inform victim about charging decision | Letter by email |
#      | Other CPS meeting                     | By telephone    |
##      | Victim Communication Liaison          | Letter by police |
##      | Victims Right to Review               | Letter by ISVA   |
##      | Victim complaint                      | By telephone     |
#
#    And the following offered meeting response from "victim" is recorded in VCA
#      | meetingType                           | offerResponseMethod | offerResponse |
#      | CPS pre-trial meeting                 | Letter by email     | Accepted      |
#      | Inform victim about charging decision | Letter by post      | Accepted      |
#      | Other CPS meeting                     | By telephone        | Accepted      |
##      | Victims Right to Review               | Letter by post      | Accepted      |
##      | Victim complaint                      | By telephone        | Accepted      |
##      | Victim Communication Liaison          | Letter by police    | Accepted      |
#    And the accepted meeting is arranged using following for "victim" in VCA
#      | meetingType                           | meetingSource    | meetingMethod | locationType      | locationName                           |
#      | CPS pre-trial meeting                 | Victim Requested | In Person     | CPS location      | Petty France                           |
#      | Inform victim about charging decision | CPS Offered      | Hybrid        | Magistrates court | Newcastle upon Tyne Magistrates' Court |
#      | Other CPS meeting                     | CPS offered      | Virtual Call  | Virtual Call      |                                        |
##      | Victims Right to Review               | Requested by a Third party | In Person     | Crown court       | Newcastle upon Tyne Crown Court        |
##      | Victim complaint                      | Victim requested           | Hybrid        | Other             | Specify Location                       |
##      | Victim Communication Liaison          | Requested by a third party | Virtual Call  | Virtual Call      |                                        |
#    And the meeting attendees are added and make "Victim liaison officer" as chair person for "victim" meeting
#      | meetingAttendeesRoles  |
#      | Counsel                |
#      | Victim liaison officer |
#      | Defence Solicitor      |
#      | Officer in Charge      |
#    When the arranged meeting is conducted with following details and logged for "victim" in VCA
#      | meetingType                           | meetingDuration    | agreedToResearch   | noteToOci | noteToVictim | proposedActions |
#      | CPS pre-trial meeting                 | 1 hours 59 minutes | Yes                | Yes       | Yes          | Take Action     |
#      | Inform victim about charging decision | 2 hours 00 minutes | No                 | No        | No           | No              |
#      | Other CPS meeting                     | 0 hours 10 minutes | Question not asked | No        | Yes          | Take Action     |
##      | Victims Right to Review               | 0 hours 30 minutes  | Question not asked | No        | Yes          | Take Action     |
##      | Victim complaint                      | 0 hours 59 minutes  | No                 | Yes       | No           | Take Action     |
##      | Victim Communication Liaison          | 10 hours 59 minutes | Yes                | No        | No           | No              |
#    And the following attendees attended the meeting lead by "Counsel"
#      | logAttendeesRoles      |
#      | Counsel                |
#      | Victim liaison officer |
#      | Defence Solicitor      |
#      | Officer in Charge      |
#    Then the logged meeting details are verified for "victim" in VCA