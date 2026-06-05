@regression

Feature: sample

  Background: Case Creation
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case
    And add "witness" using "LM04" for the case


  Scenario:Sample to VCA onboard
    Given witness and victim details are available
    And the "victimId" is onboarded to VCA
    And the "witnessId" is onboarded to VCA