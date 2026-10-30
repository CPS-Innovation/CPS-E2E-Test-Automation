@regression @ui_vca_regression @VCA_UI_S11 @VCA_UI_S12 @VCA_UI_S50

Feature: VCA-UI-S11-S12-S50:- Record the victim or victimWitness response to the offered meeting.
  As a Victim Liaison Officer
  I want to record the response of the following offered meeting type and method to victim or victimWitness:-
  ------------------------------------------------------------------------------------------
  ¦ Meeting Type                                ¦ Meeting Method       ¦ Meeting Response  ¦
  ¦---------------------------------------------¦----------------------¦-------------------¦
  ¦ 1.CPS pre-trial meeting                     ¦ 1.Letter by post     ¦ 1.Accept          ¦
  ¦ 2.Inform victim about charging decision     ¦ 2.Letter by email    ¦ 2.Decline         ¦
  ¦ 3.Other CPS meeting                         ¦ 3.By telephone       ¦ 3.No Response     ¦
  ¦                                             ¦ 4.Letter by police   ¦                   ¦
  ¦                                             ¦ 5.Letter by ISVA     ¦                   ¦
  ------------------------------------------------------------------------------------------
  Verify that meeting response details are recorded

  Background: Create cases with multi defendant with multi charge with victim and witness.
    Given create new case using "CM01" for type "multiple suspect multiple offence"
    And add "victim witness" using "LM04" for the case
    And add "victim witness child" using "LM04" for the case
    And the "victimWitness" person details are available in CMS
    And the "victimWitnessChild" person details are available in CMS

  @ui @ui_meetingResponse
  Scenario: Victim or VictimWitness decline an offered different meeting types. (FCT2-15847 & FCT2-15835 & FCT2-20835)
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
      | Inform victim about charging decision | Letter by email | Log meeting declined   | Letter by post        |
      | Other                                 | By telephone    | Log no response        |                       |
    When the meeting is offered and meeting response is logged for "victimWitnessChild" in VCA application
      | meetingType                           | offerMethod      | meetingResponse        | meetingResponseMethod |
      | CPS pre-trial meeting                 | By telephone     | Log meeting declined   | By telephone          |
      | Inform victim about charging decision | Letter by police | Log no response        |                       |
      | Other                                 | Letter by ISVA   | Log meeting acceptance | Letter by police      |
    Then the offered meeting and response is verified for "victimWitness" in VCA application
      | meetingType                           | offerMethod     | meetingResponse        | meetingResponseMethod |
      | CPS pre-trial meeting                 | Letter by post  | Log meeting acceptance | Letter by email       |
      | Inform victim about charging decision | Letter by email | Log meeting declined   | Letter by post        |
      | Other                                 | By telephone    | Log no response        |                       |
    Then the offered meeting and response is verified for "victimWitnessChild" in VCA application
      | meetingType                           | offerMethod      | meetingResponse        | meetingResponseMethod |
      | CPS pre-trial meeting                 | By telephone     | Log meeting declined   | By telephone          |
      | Inform victim about charging decision | Letter by police | Log no response        |                       |
      | Other                                 | Letter by ISVA   | Log meeting acceptance | Letter by police      |

#      #offerMethod  = #Letter by post #Letter by email #Letter by police #Letter by ISVA #By telephone
#      #ResponseMethod  = #Letter by post #Letter by email #Letter by police #Letter by ISVA #By telephone
