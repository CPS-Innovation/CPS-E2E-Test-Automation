@regression @ui_vca_regression @VCA_UI_S17 @VCA_UI_S18 @VCA_UI_S19 @VCA_UI_S20 @VCA_UI_S21 @VCA_UI_S22

Feature: VCA-UI-S17 to S23:- Log other communications with the purpose information to victim or victimWitness.
  As a Victim Liaison Officer
  I want to record other communications to victim or victimWitness for following types:-
  ------------------------------------------------------------------
  ¦ Communication Type ¦ Direction  ¦ Person contacted ¦ Purpose   ¦
  ¦--------------------¦------------¦------------------¦-----------¦
  ¦ 1.Telephone call   ¦ 1.Inbound  ¦ 1.Victim         ¦ Free Text ¦
  ¦ 2.Email            ¦ 2.Outbound ¦ 2.Other          ¦           ¦
  ¦ 3.Post             ¦            ¦                  ¦           ¦
  ¦ 4.Text Message     ¦            ¦                  ¦           ¦
  ¦ 5.In person        ¦            ¦                  ¦           ¦
  ¦ 6.Other            ¦            ¦                  ¦           ¦
  ------------------------------------------------------------------
  Verify that communication details are recorded.

  Background: Create cases with multi defendant with multi charge with victim and witness.
    Given create new case using "CM01" for type "single suspect multiple offence"
    And add "victim witness" using "LM04" for the case
    And add "victim witness child" using "LM04" for the case
    And the "victimWitness" person details are available in CMS
    And the "victimWitnessChild" person details are available in CMS

  @ui @ui_logOtherCommunications
  Scenario: Victim or VictimWitness Cancel an offered, accepted and arranged meeting types. (FCT2-16229 & 16232 & 16235 & 16238 & 16241 & 16244 & 16247)
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
    When the other communication are logged to "victimWitness" in VCA application
      | communicationType | direction | personContacted | purpose                             |
      | Telephone call    | Inbound   | Victim          | Telephone call communication reason |
      | Email             | Outbound  | Other           | Email communication reason          |
      | Post              | Inbound   | Other           | Post communication reason           |
      | Text message      | Outbound  | Victim          | Text message communication reason   |
      | In Person         |           | Other           | In person communication reason      |
      | Other             | Outbound  | Victim          | Other communication reason          |
    Then verify the logged other communication types for "victimWitness" in VCA application
      | communicationType | direction | personContacted | purpose                             |
      | Telephone call    | Inbound   | Victim          | Telephone call communication reason |
      | Email             | Outbound  | Other           | Email communication reason          |
      | Post              | Inbound   | Other           | Post communication reason           |
      | Text message      | Outbound  | Victim          | Text message communication reason   |
      | In Person         |           | Other           | In person communication reason      |
      | Other             | Outbound  | Victim          | Other communication reason          |







#
#
#  @logOtherCommunications
#  Scenario: Communication is logged with the purpose information to victims. (FCT2-16229 & 16232 & 16235 & 16238 & 16241 & 16244 & 16247)
#    Given victim details are available in VCA
#    And the "victim" is onboarded as "Universal" service lead in VCA
#    And the Victim liaison officer is assigned to "victim" in VCA
#    When the following "other" communication are logged to "victim" in VCA
#      | communicationType | direction | personContacted | purpose                             |
#      | Telephone call    | Inbound   | Victim          | Telephone call communication reason |
#      | Email             | Outbound  | Other           | Email communication reason          |
#      | Post              | Inbound   | Other           | Post communication reason           |
#      | Text Message      | Outbound  | Victim          | Text Message communication reason   |
#      | In Person         | Inbound   | Other           | In person communication reason      |
#      | Other             | Outbound  | Victim          | Other communication reason          |
#    Then verify the logged other communication for the "victim" in VCA