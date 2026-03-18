@DCF @vca @regression @15943_15978_15980_15982_add_case_contacts

Feature: Add case contacts to victims and witness.
  As a Victim Liaison Officer
  I want to add case contacts for victim and witness
  Verify that case contacts are added

  Background: Create DCF cases with multi defendant with multi charge with a victim.
    Given create case CM01 for type "multi defendant with witness victim"
    And a "witness details" is added with details using LM04
    And a "victim details" is added with details using LM04

  @addCaseContactsToVictim
  Scenario: Add case contacts to various categories of Victims and Witnesses and verify
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
    When the "Victim Liaison Officer" is added to "victimId" in VCA
    And the "Family Liaison Officer" is added to "victimId" in VCA
    And the "Independent Sexual Violence Adviser" is added to "victimId" in VCA
    And the "Independent Domestic Violence Adviser" is added to "victimId" in VCA
    Then the "Victim Liaison Officer" for "victimId" is verified in VCA
    And the "Family Liaison Officer" for "victimId" is verified in VCA
    And the "Independent Sexual Violence Adviser" for "victimId" is verified in VCA
    And the "Independent Domestic Violence Adviser" for "victimId" is verified in VCA

  @addCaseContactsToWitness
  Scenario: Add case contacts to various categories of Victims and Witnesses and verify
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
    When the "Family Liaison Officer" is added to "witnessId" in VCA
    Then the "Family Liaison Officer" for "witnessId" is verified in VCA