@regression @vca_api_regression @VCA_API_S48

Feature: VCA-API-S48 - CMS contacts are verified of victims
  As a Victim Liaison Officer
  I want to verify CMS contacts of victim
  Verify that CMS contacts are as equal as in cms for victim

  Background: Create cases with single defendant with multi charge with victim
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

  @verifyCmsCaseContacts
  Scenario: CMS case contact details are verified of victims
    Given victim details are available in VCA
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    Then the case cms contact is verified in VCA
#    --- Need to fix the assertion after json input CM01 request is changed