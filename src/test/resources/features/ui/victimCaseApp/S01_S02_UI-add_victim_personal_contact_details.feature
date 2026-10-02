@regression @vca_ui @VCA_UI_S1 @VCA_UI_S2

Feature: VCA-API-S1_S2:- Add personal and contact details for all category types of victims and victims witness and verify wm01u message
  As a Victim Liaison Officer
  I want to add personal and contact details for all category types of victims and victims witness
  Added information is reflected in CMS and VCA database
  Verify wm01u message is sent to police system

  Background: Create cases with single defendant with multiple charge with all category types of victims and victims witness
  with empty personal and contact details.

    Given create new case using "CM01" for type "single suspect multiple offence"
    And add "victim witness without personal data" using "LM04" for the case
    And the "victimWitness" details are available in CMS

  @addPersonalAndContactDetailsForVictim
  Scenario: Add victim title, preferred name, date of birth, gender, ethnicity, disability or access needs and previous convictions details
  and verify that newly added details are sent in wm01u message. (FCT2-15283 & FCT2-15284)

    Given VLO login to victim case application
    And "victimWitness" is searched using case reference
    And the "victimWitness" is onboarded to "Universal" service lead
#      service lead options = Universal, Enhanced, Rasso, Not aligned
    And select "No task at this time" for next task for the "victimWitness"
#     task options = #Inform of a decision to charge
#                     Inform of a no further action decision
#                     Log offered meeting
#                     Log arranged meeting
#                     Log meeting outcome
#                     Another task (not listed)
#                     No task at this time ,
    And verify that "victimWitness" is onboarded
    When the "Murali" is assigned as VLO to "victimWitness"
    And verify that Vlo "Murali" is assigned to "victimWitness"
    When the following "victimWitness" personal details are added to CMS
      | field                               | value | justification |
      | Date of birth                       |       |               |
      | Gender                              |       |               |
      | Address                             |       |               |
      | Telephone number                    |       |               |
      | Email address                       |       |               |
#    When the following "victimWitness" personal details are added in VCA
#      | field                               | value |
#      | Preferred name                      |       |
#      | Preferred method of contact for CPS |       |
#      | Preferred contact times             |       |
#      | Victim representative details       |       |
#      | Power of attorney details           |       |

#    Then the "victimWitness" personal details are verified






#    And I login to case review app
