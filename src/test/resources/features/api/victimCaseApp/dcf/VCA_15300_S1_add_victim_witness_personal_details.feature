@DCF @vca @regression @S1_addWitnessVictimPersonalDetails

Feature: Add witness and victim personal details and verify wm01u
  As a Victim Liaison Officer
  I want to update witness and victim personal details
  Updated information is reflected in CMS and wm01u message is sent to police system

  Background:
    Given create case CM01 for type "single defendant with charge with all types witness victim"
#    And a "witness" is added using LM04
    And a "witness child" is added using LM04
#    And a "witness police" is added using LM04
#    And a "witness intimidated" is added using LM04
#    And a "witness vulnerable" is added using LM04
#    And a "witness professional" is added using LM04
#    And a "witness expert" is added using LM04
#    And a "witness prisoner" is added using LM04
#    And a "witness interpreter" is added using LM04
#    And a "victim" is added using LM04
#    And a "victim child" is added using LM04
#    And a "victim vulnerable" is added using LM04
#    And a "victim intimidated" is added using LM04
#    And a "victim professional" is added using LM04
#    And a "victim expert" is added using LM04
#    And a "victim prisoner" is added using LM04
#    And a "victim interpreter" is added using LM04
#    And a "victim police" is added using LM04

  @witnessAddPersonalDetails
  Scenario: Add witness title, preferred name, date of birth, gender, ethnicity, disability or access needs and previous convictions details
    Given witness and victim details are available
    When the "witnessChildId" personal details are added to CMS
#    And the "victimPrisonerId" personal details are added to CMS

    And the "witnessChildId" is onboarded and personal details are added to VCA
#    And the "victimPrisonerId" is onboarded and personal details are added to VCA

    Then the "witnessChildId" personal details are verified in CMS and VCA
    And Witness personal details are updated correctly to VCA
