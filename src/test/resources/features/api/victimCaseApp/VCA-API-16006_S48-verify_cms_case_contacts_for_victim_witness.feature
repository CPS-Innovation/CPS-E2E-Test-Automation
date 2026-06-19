@regression @VCA_API_S48

Feature: VCA-API-S48 - CMS case contact details are verified of victims and witness
  As a Victim Liaison Officer
  I want to verify CMS case contact details of victim and witness
  Verify that CMS case contact details are as equal as in cms classic for victim and witness

  Background: Create cases with single defendant with multi charge with victim and witness
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case
    And add "witness" using "LM04" for the case

  @verifyCmsCaseContacts
  Scenario: CMS case contact details are verified of victims and witness
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
#    Then the cms case details should be equal as in cms classic  --- Need to fix the assertion after json input CM01 request is changed