@DCF @vca @regression @S10_15838

Feature: JIRA - 15838 - Update category type for witness and victim and verify wm01u message
  As a Victim Liaison Officer
  I want to update category type for witness and victim
  Update information is reflected in CMS database
  Verify wm01u message is sent to police system

  Background: Create DCF cases with multi defendant with multi charge with witness and victim.
    Given create case CM01 for type "multi defendant with witness and victim"
    And a "witness" is added using LM04
    And a "victim details" is added with details using LM04

  @addWitnessCategoryAndUpdateVictimCategory
  Scenario: Update victim and witness category and verify that update details are sent in wm01u message
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
    When the category "Expert" is added to "witnessId" in VCA
    And the category "Vulnerable" is update to "victimId" in VCA
    Then the "witnessId" and "victimId" category is verified in CMS