@DCF @vca @regression @S10_15838

Feature: Update category type for witness and victim and verify wm01u message
  As a Victim Liaison Officer
  I want to update category type for witness and victim
  Update information is reflected in CMS database
  Verify wm01u message is sent to police system

  Background: Create DCF cases with multi defendant with multi charge with witness and victim.
    Given create case CM01 for type "multi defendant with witness and victim"
    And a "witness" is added using LM04
    And a "victim details" is added with details using LM04

  @updatePersonalAndContactDetailsForWitnessVictim
  Scenario: Update victim and witness category and verify that update details are sent in wm01u message
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
#    When the "witnessExpertId" personal details are update to VCA
#    When the "witnessId" personal details are updated to CMS
    When the category "Expert" is added to "witnessId" in VCA
    When the category "Vulnerable" is added to "victimId" in VCA
#    When the following category as "Interpreter" are added to "witnessExpertId" in VCA
#    And the following category are added to "victimId" in VCA
#      | Vulnerable  |
#      | Intimidated |
#  #    Then the "witnessId" personal details are verified in CMS and VCA
#    Then the "witnessExpertId" categories are verified in CMS
#    And the "vicitmId" categories are verified in CMS
