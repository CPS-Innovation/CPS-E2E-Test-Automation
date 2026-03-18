@DCF @vca @regression @S1_addWitnessVictimPersonalDetails

Feature: S1 test
  As a Victim Liaison Officer
  I want to update witness and victim personal details
  Updated information is reflected in CMS and wm01u message is sent to police system

  Background:
    Given create case CM01 for type "single defendant with charge for two witness two victim"
    And a "witness" is added using LM04
    And a "witness 2" is added using LM04
    And a "victim" is added using LM04
    And a "victim 2" is added using LM04

  @witnessAddPersonalDetails
  Scenario: S1 test functionality
    Given witness and victim details are available
    When the "witnessId" personal details are added to CMS
    And the "victimId" personal details are added to CMS

    When the "witnessId" is onboarded and personal details are added to VCA
    And the "victimId" is onboarded and personal details are added to VCA

    Then the "witnessId" personal details are verified in CMS and VCA
#    And the "witnessId2" personal details are verified in CMS and VCA
#    And the "victimId" personal details are verified in CMS and VCA
#    And the "victimId2" personal details are verified in CMS and VCA
