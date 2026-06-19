@regression @VCA_API_S5_S6_S7

Feature: VCA-API-S5_S6_S7 - Add and update case contacts to victims and witness.
  As a Victim Liaison Officer
  I want to add case contacts to victim and witness
  I want to update case contacts to victim and witness
  Verify that case contacts are added to victim and witness
  Verify that case contacts are updated to victim and witness

  Background: Create cases with multi defendant with multi charge with victim and witness.
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case
    And add "witness" using "LM04" for the case

  @addCaseContactsToVictim
  Scenario: Add case contacts to victims then verify
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
    When the "Family Liaison Officer" is added to "victimId" in VCA
    And the "Independent Sexual Violence Adviser" is added to "victimId" in VCA
    And the "Independent Domestic Violence Adviser" is added to "victimId" in VCA
    Then the "Family Liaison Officer" for "victimId" is verified in VCA
    And the "Independent Sexual Violence Adviser" for "victimId" is verified in VCA
    And the "Independent Domestic Violence Adviser" for "victimId" is verified in VCA

  @addCaseContactsToWitness
  Scenario: Add case contacts to witnesses then verify
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
    When the "Family Liaison Officer" is added to "witnessId" in VCA
    Then the "Family Liaison Officer" for "witnessId" is verified in VCA

  @updateCaseContactsToVictim
  Scenario: Update case contact details of victim then verify
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
    And the "Family Liaison Officer" is added to "victimId" in VCA
    And the "Independent Sexual Violence Adviser" is added to "victimId" in VCA
    And the "Independent Domestic Violence Adviser" is added to "victimId" in VCA
    When the "Family Liaison Officer" details are changed for "victimId" in VCA
    And the "Independent Sexual Violence Adviser" details are changed for "victimId" in VCA
    And the "Independent Domestic Violence Adviser" details are changed for "victimId" in VCA
    Then the "Family Liaison Officer" changes for "victimId" are verified in VCA
    And the "Independent Sexual Violence Adviser" changes for "victimId" are verified in VCA
    And the "Independent Domestic Violence Adviser" changes for "victimId" are verified in VCA

  @updateCaseContactsOfWitness
  Scenario: Update case contact details of witness then verify
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
    And the "Family Liaison Officer" is added to "witnessId" in VCA
    When the "Family Liaison Officer" details are changed for "witnessId" in VCA
    Then the "Family Liaison Officer" changes for "witnessId" are verified in VCA