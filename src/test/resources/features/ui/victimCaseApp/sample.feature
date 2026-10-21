@regression @ui_vca_regression @VCA_UI_S1 @VCA_UI_S2


Feature: Sample

@ui
Scenario: Sample
  Given create new case using "CM01" for type "single suspect multiple offence"
And add "victim witness" using "LM04" for the case
And the "victimWitness" person details are available in CMS