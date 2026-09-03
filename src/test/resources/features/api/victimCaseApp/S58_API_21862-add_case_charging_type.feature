@regression @vca_api_regression @VCA_API_S58

Feature: VCA-API-S58 - Add case Charging type
  As a Victim Liaison Officer
  I want to add case charging type in case information in victim case application
  Update information is reflected in VCA database
  Verify the added charging type

  Background: Create cases with multi defendant with multi charge with victim personal and contact details.
  with empty personal and contact details.
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

  @addChargingTYpe
  Scenario: Add case Charging type in case information section.
    Given victim details are available in VCA
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    When the charging type "Police Charged" is added to case information to "victim" in VCA
#    #charge type = Red or Green or Police Charged
    Then the case charging type is verified for "victim" in VCA


