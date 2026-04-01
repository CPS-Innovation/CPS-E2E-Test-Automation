@DCF @vca @regression @16007_cms_case_contacts_for_victim_witness

Feature: CMS case contact details are verified of victims and witness
  As a Victim Liaison Officer
  I want to verify CMS case contact details of victim and witness
  Verify that CMS case contact details are as equal as in cms classic for victim and witness

  Background: Create DCF cases with multi defendant with multi charge with victim and witness
    Given create case CM01 for type "multi defendant with witness victim"
    And a "witness details" is added with details using LM04
    And a "victim details" is added with details using LM04

  @verifyCmsCaseContacts
  Scenario: CMS case contact details are verified of victims and witness
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
    And the "Victim Liaison Officer" is added to "victimId" in VCA
    And the "Family Liaison Officer" is added to "victimId" in VCA
#    When the "victimId" cms case contact is verified
#    Then the cms case details are as equal as in cms classic for "victimId"