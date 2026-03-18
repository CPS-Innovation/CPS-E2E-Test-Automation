@DCF @vca @regression @S3_S4_15791_15793

Feature: Update existing witness and victim personal and contact details and verify wm01u
  As a Victim Liaison Officer
  I want to update witness and victim personal and contact details
  Updated information is reflected in CMS and VCA database
  wm01u message is sent to police system

  Background:
    Given create case CM01 for type "single defendant with charge with all types witness victim"
#    And a "witness details" is added with details using LM04
    And a "witness details child" is added with details using LM04
#    And a "victim details" is added with details using LM04
    And a "victim details child" is added with details using LM04

  @addWitnessVictimPersonalAndContactDetailsForAllWitnessVictimType
  Scenario: Add witness title, preferred name, date of birth, gender, ethnicity, disability or access needs and previous convictions details
    Given witness and victim details are available
#    When the "witnessId" personal details are added to CMS
    And the "witnessChildId" personal details are added to CMS
#    And the "victimId" personal details are added to CMS
    And the "victimChildId" personal details are added to CMS

#    When the "witnessId" is onboarded and personal details are added to VCA
    And the "witnessChildId" is onboarded and personal details are added to VCA
#    And the "victimId" is onboarded and personal details are added to VCA
    And the "victimChildId" is onboarded and personal details are added to VCA

#    Then the "witnessId" personal details are verified in CMS and VCA
    And the "witnessChildId" personal details are verified in CMS and VCA
#    And the "victimId" personal details are verified in CMS and VCA
    And the "victimChildId" personal details are verified in CMS and VCA
