@regression @DCF_VCA_API_S9

Feature: VCA-API-S10 - Update category type for witness and victim and verify wm01u message
  As a Victim Liaison Officer
  I want to update category type for witness and victim
  Update information is reflected in CMS database
  Verify wm01u message is sent to police system

  Background: Create cases with multi defendant with multi charge with witness and victim.
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case
#    And add "witness" using "LM04" for the case

  @addWitnessCategoryAndUpdateVictimCategory
  Scenario: Update victim and witness category and verify that update details are sent in wm01u message
    Given witness and victim details are available
#    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
    And the Victim liaison officer is assigned to "victimId" in VCA

    When the category "Expert" is added to "victimId" in VCA
    And the category "Vulnerable" is update to "victimId" in VCA
#    Then the "witnessId" and "victimId" category is verified in CMS