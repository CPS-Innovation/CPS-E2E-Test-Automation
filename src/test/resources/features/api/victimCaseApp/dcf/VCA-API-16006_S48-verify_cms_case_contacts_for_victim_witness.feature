@DCF @regression @VCA_API_DCF_S48

Feature: VCA-API-S48 - CMS case contact details are verified of victims and witness
  As a Victim Liaison Officer
  I want to verify CMS case contact details of victim and witness
  Verify that CMS case contact details are as equal as in cms classic for victim and witness

  Background: Create DCF cases with single defendant with multi charge with victim and witness
    Given create case CM01 for type "single defendant with witness victim"
    And a "witness details" is added with details using LM04
    And a "victim details" is added with details using LM04

  @verifyCmsCaseContacts
  Scenario: CMS case contact details are verified of victims and witness
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
    Then the cms case details should be equal as in cms classic