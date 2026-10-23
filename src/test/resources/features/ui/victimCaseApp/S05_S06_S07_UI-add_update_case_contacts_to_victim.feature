@regression @ui_vca_regression @VCA_UI_S5 @VCA_UI_S6 @VCA_UI_S7

Feature: VCA-UI-S5_S6_S7:- Add and update case contacts to victims or victimWitness person
  As a Victim Liaison Officer
  I want to add case contacts to victims or victimWitness
  I want to update case contacts to victims or victimWitness
  Verify that case contacts are added to victims or victimWitness
  Verify that case contacts are updated to victims or victimWitness

  Background: Create cases with multi defendant with multi charge with victim and witness.
    Given create new case using "CM01" for type "multiple suspect multiple offence"
    And add "victim witness" using "LM04" for the case
    And the "victimWitness" person details are available in CMS

  @ui @addCpsContactToVictim
  Scenario: Add case contacts to victims or victimWitness and then verify the case contacts. (FCT2-15288 & FCT2-15290 & FCT2-15291)
    Given VLO login to victim case application
    And "victimWitness" is searched using case reference
    And the "victimWitness" is onboarded to "Universal" service lead
    And select "No task at this time" for next task for the "victimWitness"
    And verify that "victimWitness" is onboarded
    And the "Murali" is assigned as VLO to "victimWitness"
    And verify that Vlo "Murali" is assigned to "victimWitness"
    When the following case contacts are added to "victimWitness" in VCA application
      | caseContactType                       |
      | Family Liaison Officer                |
      | Independent Sexual Violence Adviser   |
      | Independent Domestic Violence Adviser |
    Then the added case contacts for "victimWitness" are verified

  @ui @updateCpsContactToVictim
  Scenario: Update case contacts of victims or victimWitness and then verify the contacts. (FCT2-15288 & FCT2-15290 & FCT2-15291)
    Given VLO login to victim case application
    And "victimWitness" is searched using case reference
    And the "victimWitness" is onboarded to "Universal" service lead
    And select "No task at this time" for next task for the "victimWitness"
    And verify that "victimWitness" is onboarded
    And the "Murali" is assigned as VLO to "victimWitness"
    And verify that Vlo "Murali" is assigned to "victimWitness"
    And the following case contacts are added to "victimWitness" in VCA application
      | caseContactType                       |
      | Family Liaison Officer                |
      | Independent Sexual Violence Adviser   |
      | Independent Domestic Violence Adviser |
    When update following case contacts of "victimWitness" in VCA application
      | caseContactType                       |
      | Family Liaison Officer                |
      | Independent Sexual Violence Adviser   |
      | Independent Domestic Violence Adviser |
    Then "victimWitness" case contacts are verified in VCA application

