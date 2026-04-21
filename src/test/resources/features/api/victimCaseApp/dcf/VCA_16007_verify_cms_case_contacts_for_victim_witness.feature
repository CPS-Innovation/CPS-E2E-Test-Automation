@DCF @vca @regression @16007_cms_case_contacts_for_victim_witness

Feature: JIRA - 16007 - CMS case contact details are verified of victims and witness
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
#  /api/cases/{{DCFCaseID}}
    When the CMS case contact details are requested
#  api/cases/{{DCFCaseID}}/contacts
#    Then the cms case details should be equal as in cms classic  /