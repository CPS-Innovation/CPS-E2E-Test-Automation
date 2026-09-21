@regression @vca_ui @VCA_API_S1 @VCA_API_S2

Feature: VCA-API-S1_S2:- Add personal and contact details for all category types of victims and victims witness and verify wm01u message
  As a Victim Liaison Officer
  I want to add personal and contact details for all category types of victims and victims witness
  Added information is reflected in CMS and VCA database
  Verify wm01u message is sent to police system

#  Background: Create cases with single defendant with multiple charge with all category types of victims and victims witness
#  with empty personal and contact details.
#
#    Given create new case using "CM01" for type "single defendant multiple offence"
#    And add "victim" using "LM04" for the case

  @addPersonalAndContactDetailsForVictim
  Scenario: Add victim title, preferred name, date of birth, gender, ethnicity, disability or access needs and previous convictions details
  and verify that newly added details are sent in wm01u message. (FCT2-15283 & FCT2-15284)
    Given VLO login to victim case application
#    And I login to case review app
