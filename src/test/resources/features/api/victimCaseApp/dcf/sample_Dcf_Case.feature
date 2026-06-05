@DCF @regression

Feature: sample

  Background:
    Given create "DCF" case using "CM01" for type "dcf single defendant multiple offence"
    And add "dcf victim" using "LM04" for the case

  Scenario:Sample to VCA onboard DCF case
    Given witness and victim details are available
    And the "victimId" is onboarded to VCA

