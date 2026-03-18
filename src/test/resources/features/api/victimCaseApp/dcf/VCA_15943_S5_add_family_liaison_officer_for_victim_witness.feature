@DCF @vca @regression @S5_15943

Feature: Add Family Liaison Officer to victim.
  As a Victim Liaison Officer
  I want to add family liaison officer for victim
  Verify that Family Liaison Officer is added

  Background: Create DCF cases with multi defendant with multi charge with a victim.
    Given create case CM01 for type "multi defendant with a victim"
    And a "victim details" is added with details using LM04

  @addFamilyLiaisonOfficer
  Scenario: Add Family Liaison Officer to victim and verify
    Given witness and victim details are available
    And the "victimId" is onboarded to VCA
    When the "Family Liaison Officer" is added to "victimId" in VCA

#
#    When the category "Expert" is added to "witnessId" in VCA
#    And the category "Vulnerable" is update to "victimId" in VCA
#    Then the "witnessId" and "victimId" category is verified in CMS