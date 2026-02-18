Feature: Demo for usage of framework

  @DCF1
  Scenario: check the DCF case exists in CMS
    Given case URN exists in CMS
    Then Case details are correct

  @TWIF
  Scenario: check the TWF case exists in CMS
    Given case URN exists in CMS
    Then Case details are correct

  @DCF1
  Scenario: Demo test for police charge message
    Given create case "CM01" for type "single defendant with charge"
    And a "witness" is added using LM04

