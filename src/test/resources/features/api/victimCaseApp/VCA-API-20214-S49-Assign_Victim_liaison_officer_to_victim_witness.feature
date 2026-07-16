@regression @VCA_API_S49

Feature: VCA-API-S49 - Add Victim liaison officer (VLO) to victims and witness.
  As a Victim Liaison Officer
  I want to add Victim liaison officer (VLO) to victim and witness
  Verify that Victim liaison officer (VLO) to victim and witness

  Background: Create cases with single defendant with multi charge with victim and witness.
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case
#    And add "witness" using "LM04" for the case

  @addCaseContactsToVictim
  Scenario: Add Victim liaison officer (VLO) to victims then verify
    Given witness and victim details are available
#    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
    When the Victim liaison officer is assigned to "victimId" in VCA
#    And the Victim liaison officer is assigned to "witnessId" in VCA
    Then assigned Victim liaison officer for "victimId" is verified
#    And assigned Victim liaison officer for "witnessId" is verified
