@DCF @vca @regression @S3_S4_15791_15793

Feature: Update existing witness and victim personal and contact details and verify wm01u
  As a Victim Liaison Officer
  I want to update personal and contact details for witness and victim
  Update information is reflected in CMS and VCA database
  Verify wm01u message is sent to police system

  Background: Create DCF cases with multi defendant with multi charge with witness and victim personal and contact details.
  with empty personal and contact details.
    Given create case CM01 for type "multi defendant with multi charge with witness victim details"
    And a "witness details" is added with details using LM04
    And a "victim details" is added with details using LM04

  @updatePersonalAndContactDetailsForWitnessVictim
  Scenario: Update existing title, preferred name, date of birth, gender, ethnicity, disability or access needs and previous convictions details
            and verify that update details are sent in wm01u message.
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA

    When the "witnessId" personal details are updated to CMS
    And the "victimId" personal details are updated to CMS

    When the "witnessId" personal details are added to VCA
    And the "victimId" personal details are added to VCA

    When the "witnessId" personal details are update to VCA
    And the "victimId" personal details are update to VCA

    Then the "witnessId" personal details are verified in CMS and VCA
    And the "victimId" personal details are verified in CMS and VCA