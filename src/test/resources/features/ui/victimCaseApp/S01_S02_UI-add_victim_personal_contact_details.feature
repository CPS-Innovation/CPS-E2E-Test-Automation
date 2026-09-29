@regression @vca_ui @VCA_UI_S1 @VCA_UI_S2

Feature: VCA-API-S1_S2:- Add personal and contact details for all category types of victims and victims witness and verify wm01u message
  As a Victim Liaison Officer
  I want to add personal and contact details for all category types of victims and victims witness
  Added information is reflected in CMS and VCA database
  Verify wm01u message is sent to police system

  Background: Create cases with single defendant with multiple charge with all category types of victims and victims witness
  with empty personal and contact details.

#    Given create new case using "CM01" for type "single defendant multiple offence"
#    And add "victim" using "LM04" for the case
#    And the "victim" details are available in CMS

  @addPersonalAndContactDetailsForVictim
  Scenario: Add victim title, preferred name, date of birth, gender, ethnicity, disability or access needs and previous convictions details
  and verify that newly added details are sent in wm01u message. (FCT2-15283 & FCT2-15284)

    Given VLO login to victim case application
#    And "victim" is searched using case reference
#    And the "victim" is onboarded to "Universal" service lead
    #  service lead options = Universal, Enhanced, Rasso, Not aligned
#    And select "No task at this time" for next task for the "victim"
#    And verify that "victim" is onboarded
    # task options = #Inform of a decision to charge
                     #Inform of a no further action decision
                     #Log offered meeting
                     #Log arranged meeting
                     #Log meeting outcome
                     #Another task (not listed)
                     #No task at this time ,
#    When the "Murali" is assigned as VLO to "victim"
  And verify that Vlo "Murali" is assigned to "victim"





#    And I login to case review app
