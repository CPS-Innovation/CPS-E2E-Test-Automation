@regression @ui_vca_regression @VCA_UI_S10

Feature: VCA-UI-S10:- Meetings of different types are not offered to victim or victimWitness with a reason
  As a Victim Liaison Officer
  I want to record the meeting is not being offered with reasons for following types:-
  1. CPS pre-trial meeting
  2. Inform victim about charging decision
  3. Other CPS meeting
  Verify that not offered meeting details are recorded

  Background: Create cases with multi defendant with multi charge with victim and witness.
    Given create new case using "CM01" for type "multiple suspect multiple offence"
    And add "victim witness" using "LM04" for the case
    And the "victimWitness" person details are available in CMS

  @ui @ui_ptmNotOffered
  Scenario: Meetings of different types are not offered to victim or victimWitness with a reason. (FCT2-15295)

    Given VLO login to victim case application
    And "victimWitness" is searched using case reference
    And the "victimWitness" is onboarded to "Universal" service lead
    And select "No task at this time" for next task for the "victimWitness"
    And verify that "victimWitness" is onboarded
    And the "Murali" is assigned as VLO to "victimWitness"
    And verify that Vlo "Murali" is assigned to "victimWitness"
    When the following meeting types are not offered to "victimWitness" in VCA application
      | meetingType           | notOfferedReason                           |
      | CPS pre-trial meeting | CPS pre-trial meeting - Not Offered reason |
      | Inform victim about charging decision | Inform victim about charging decision - Not Offered reason |
      | Other                                 | Other CPS meeting -  Not Offered reason                    |
    Then the following not offered meetings to "victimWitness" are verified in VCA application
      | meetingType           | notOfferedReason                           |
      | CPS pre-trial meeting | CPS pre-trial meeting - Not Offered reason |
      | Inform victim about charging decision | Inform victim about charging decision - Not Offered reason |
      | Other                                 | Other CPS meeting -  Not Offered reason                    |