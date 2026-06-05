@TWIF @regression

Feature: sample

  Scenario:Sample to create TWIF case

    Given create "TWIF" case using "CM01" for type "twif single defendant with charge"
    And add "twif victim" using "LM04" for the case
