@regression @ui_vca_regression @VCA_UI_S24 @VCA_UI_S25 @VCA_UI_S38

Feature: VCA-UI-S24-S25-S38:- Log charge decision communication where first call attempt was successful and follow up communication is sent in email or post for victim or victimWitness in Victim Case Application.
  As a Victim Liaison Officer
  I want to log charge decision communication for victim or victimWitness.
  where first call attempt was successful and SMS is sent
  then follow up communication is sent in email
  verify that charge decision communication is logged.

  Background: Create cases with multi defendant with multi charge with victim and witness.
    Given create new case using "CM01" for type "single suspect multiple offence"
    And add "victim witness" using "LM04" for the case
    And add "victim witness child" using "LM04" for the case
    And the "victimWitness" person details are available in CMS
    And the "victimWitnessChild" person details are available in CMS

  @ui @ui_issueDecisionVictimNotContacted
  Scenario: Log charge decision communication where first telephone call attempt was successful and follow up communication is sent in email for victim or victimWitness. (FCT2-16349 & 16369 & 16382)
    Given VLO login to victim case application
    And "victimWitness" is searched using case reference
    And the "victimWitness" is onboarded to "Universal" service lead
    And select "No task at this time" for next task
    And verify that "victimWitness" is onboarded
    And the "Murali" is assigned as VLO to "victimWitness"
    And "victimWitnessChild" is searched using case reference
    And the "victimWitnessChild" is onboarded to "Universal" service lead
    And select "No task at this time" for next task
    And verify that "victimWitnessChild" is onboarded
    And the "Murali" is assigned as VLO to "victimWitnessChild"
    When the first telephone call attempt is successful to inform "victimWitness" about the charge decision in VCA application
      | journeyType        | contactMethod  | callDirection | informVictim | notes                  |
      | Decision to charge | Telephone call | Inbound       | Yes          | Telephone call - Notes |
      #  journeyType :- Decision to charge or #  No further action decision
#    When the first telephone call attempt is successful to inform "victimWitnessChild" about the charge decision in VCA application
#      | journeyType                | contactMethod  | callDirection | informVictim | notes                  |
#      | No further action decision | Telephone call | Inbound       | Yes          | Telephone call - Notes |
      #  journeyType :- Decision to charge or #  No further action decision
    And follow up communication for "victimWitness" in VCA application
      | journeyType        | followUpMethod | notes        |
      | Decision to charge | Post           | Post - Notes |
      #  journeyType :- Decision to charge or #  No further action decision
      #  followUpMethod :- Email or Post ot They do not want a letter
    Then verify the successful first telephone call attempt to inform "victimWitness" is recorded in VCA application
      | journeyType        | contactMethod  | callDirection | informVictim | notes                  |
      | Decision to charge | Telephone call | Inbound       | Yes          | Telephone call - Notes |
      #  journeyType :- Decision to charge or #  No further action decision
    Then verify the successful first telephone call attempt to inform "victimWitnessChild" is recorded in VCA application
      | journeyType                | contactMethod  | callDirection | informVictim | notes                  |
      | No further action decision | Telephone call | Inbound       | Yes          | Telephone call - Notes |
      #  journeyType :- Decision to charge or #  No further action decision
















#    Given victim details are available in VCA
#    And the "victim" is onboarded as "Universal" service lead in VCA
#    And the Victim liaison officer is assigned to "victim" in VCA
#    When the first telephone call attempt is successful to inform victim with following details for "victim" in VCA
#      | journeyType                            | informVictim | callDirection | notes                  |
#      | Inform of a no further action decision | Yes          | Inbound       | Telephone call - Notes |
#      #  journeyType :- Inform of a no further action decision or #  Inform of a decision to charge
#    And follow up as below for "victim" in VCA
#      | journeyType                            | followUpMethod | notes        |
#      | Inform of a no further action decision | Post           | Post - Notes |
#      #  followUpMethod :- Email or Post
#    Then verify the charge decision telephone and followup communication for "victim" in VCA
#      | journeyType                            | followUpMethod | callAttempt | smsSent |
#      | Inform of a no further action decision | Post           | first       | No      |
#      #  journeyType :- Inform of a no further action decision or #  Inform of a decision to charge