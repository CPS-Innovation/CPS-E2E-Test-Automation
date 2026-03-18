@DCF @vca @regression @S3_S4_15791_15793

Feature: Update existing witness and victim personal and contact details and verify wm01u
  As a Victim Liaison Officer
  I want to update witness and victim personal and contact details
  Updated information is reflected in CMS and VCA database
  wm01u message is sent to police system

  Background:
    Given create case CM01 for type "multi defendant with multi charge for witness victim details"
    And a "witness details" is added with details using LM04
    And a "victim details" is added with details using LM04

  @updatePersonalAndContactDetailsForWitnessVictim
  Scenario: Update existing title, preferred name, date of birth, gender, ethnicity, disability or access needs and previous convictions details
    Given witness and victim details are available
    When the "witnessId" personal details are updated to CMS
    And the "victimId" personal details are updated to CMS

#    When the "witnessId" is onboarded and personal details are added to VCA
    When the "witnessId" is onboarded and personal details are update to VCA
#    And the "victimId" is onboarded and personal details are added to VCA
    And the "victimId" is onboarded and personal details are update to VCA

    Then the "witnessId" personal details are verified in CMS and VCA
    And the "victimId" personal details are verified in CMS and VCA

