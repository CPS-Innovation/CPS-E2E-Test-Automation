@regression @vca_api_regression @VCA_API_S9

Feature: VCA-API-S9 - Add category type for victim and verify wm01u message
  As a Victim Liaison Officer
  I want to add category type for victim
  Update information is reflected in CMS database
  Verify wm01u message is sent to police system

  Background: Create cases with multi defendant with multi charge with witness and victim.
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim Witness" using "LM04" for the case
#NOTE currently works for victimWitness -- as we have a defect for victim
  @addCategoryToVictim
  Scenario: Add category to victim and verify that update details are sent in wm01u message
    Given victim details are available in VCA
    And the "victimWitness" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victimWitness" in VCA
    When the following category type is added to "victimWitness" in VCA
      | categoryType         |
      | Child under 18 years |
      | Vulnerable           |
      | Intimidated          |
   Then the added categories to "victimWitness" are verified

#  Serving police officer
#  Child under 18 years
#  Professional
#  Expert witness
#  Vulnerable
#  Intimidated
#  Serving prisoner or on remand
#  Interpreter instructed by prosecution