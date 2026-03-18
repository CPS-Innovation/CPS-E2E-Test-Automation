@DCF @vca @regression @S10_15838

Feature: Update category type for witness and victim and verify wm01u message
  As a Victim Liaison Officer
  I want to update category type for witness and victim
  Update information is reflected in CMS database
  Verify wm01u message is sent to police system

  Background: Create DCF cases with multi defendant with multi charge with an expert category witness and a victim.
    Given create case CM01 for type "multi defendant with expert witness victim"
    And a "witness expert" is added with details using LM04
    And a "victim details" is added with details using LM04

  @updatePersonalAndContactDetailsForWitnessVictim
  Scenario: Update victim and witness category and verify that update details are sent in wm01u message
    Given witness and victim details are available
    And the "witnessExpertId " is onboarded to VCA
    And the "victimId" is onboarded to VCA
  #    When the "witnessId" personal details are update to VCA
#    When the following category are added to "witnessExpertId" in VCA
#      | Professional |
#      | Interpreter  |
#    And the following category are added to "victimId" in VCA
#      | Vulnerable |
#      | Intimidated  |
#  #    Then the "witnessId" personal details are verified in CMS and VCA
#    Then the "witnessExpertId" categories are verified in CMS
#    And the "vicitmId" categories are verified in CMS
