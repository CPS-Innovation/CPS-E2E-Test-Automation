@regression @ui_vca_regression @VCA_UI_S9

Feature: VCA-UI-S9:- Add category type to victim or victimWitness and verify wm01u message
  As a Victim Liaison Officer
  I want to add category type to victim or victimWitness
  Update information is reflected in VCA
  Verify wm01u message is sent to police system

  Background: Create cases with multi defendant with multi charge with victim and witness.
    Given create new case using "CM01" for type "multiple suspect multiple offence"
    And add "victim witness child" using "LM04" for the case
    And the "victimWitnessChild" person details are available in CMS

  @ui @addNewCategoryToVictim
  Scenario: Add new category to victim or victimWitness and verify that update details are sent in wm01u message. (FCT2-15837)

    Given VLO login to victim case application
    And "victimWitnessChild" is searched using case reference
    And the "victimWitnessChild" is onboarded to "Universal" service lead
    And select "No task at this time" for next task for the "victimWitnessChild"
    And verify that "victimWitnessChild" is onboarded
    And the "Murali" is assigned as VLO to "victimWitnessChild"
    And verify that Vlo "Murali" is assigned to "victimWitnessChild"
    When the following category type is added to "victimWitnessChild" in VCA application
      | categoryType         |
      | Vulnerable           |
      | Intimidated          |
    Then "victimWitnessChild" category types are verified in summary details in VCA application
      | categoryType         |
      | Vulnerable           |
      | Intimidated          |