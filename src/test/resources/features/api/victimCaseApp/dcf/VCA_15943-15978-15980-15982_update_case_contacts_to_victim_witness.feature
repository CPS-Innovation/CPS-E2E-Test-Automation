@DCF @vca @regression @15943_15978_15980_15982_update_case_contacts

Feature: JIRA - 15943_15978_15980_15982 - Update case contact details of victims and witness.
  As a Victim Liaison Officer
  I want to update case contact details of victim and witness
  Verify that case contact details are changed for victim and witness

  Background: Create DCF cases with multi defendant with multi charge with victim and witness.
    Given create case CM01 for type "multi defendant with witness victim"
    And a "witness details" is added with details using LM04
    And a "victim details" is added with details using LM04

  @updateCaseContactsToVictim
  Scenario: Update case contact details of victim then verify
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
    And the "Victim Liaison Officer" is added to "victimId" in VCA
    And the "Family Liaison Officer" is added to "victimId" in VCA
    And the "Independent Sexual Violence Adviser" is added to "victimId" in VCA
    And the "Independent Domestic Violence Adviser" is added to "victimId" in VCA
    When the "Victim Liaison Officer" details are changed for "victimId" in VCA
    And  the "Family Liaison Officer" details are changed for "victimId" in VCA
    And the "Independent Sexual Violence Adviser" details are changed for "victimId" in VCA
    And the "Independent Domestic Violence Adviser" details are changed for "victimId" in VCA
    Then the "Victim Liaison Officer" changes for "victimId" are verified in VCA
    And the "Family Liaison Officer" changes for "victimId" are verified in VCA
    And the "Independent Sexual Violence Adviser" changes for "victimId" are verified in VCA
    And the "Independent Domestic Violence Adviser" changes for "victimId" are verified in VCA

  @updateCaseContactsOfWitness
  Scenario: Update case contact details of witness then verify
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
    And the "Victim Liaison Officer" is added to "witnessId" in VCA
    When the "Victim Liaison Officer" details are changed for "witnessId" in VCA
    Then the "Victim Liaison Officer" changes for "witnessId" are verified in VCA
