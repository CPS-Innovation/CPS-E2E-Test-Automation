@sample


Feature: Sample

@ui
Scenario: Sample
  Given create new case using "CM01" for type "single suspect multiple offence"
And add "victim witness" using "LM04" for the case
And the "victimWitness" person details are available in CMS