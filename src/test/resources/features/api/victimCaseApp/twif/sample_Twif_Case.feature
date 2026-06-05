@regression

Feature: sample

#  Background: create Twif case
#    Given create "TWIF" case using "CM01" for type "twif single defendant with charge"
#    And add "twif victim" using "LM04" for the case
#
#  Scenario:Sample for VCA
#    Given witness and victim details are available
#    And the "victimId" is onboarded to VCA


  Scenario:Sample case Create
    Given create new case using "CM01" for type "twif single defendant with charge"
#    And add "twif victim" using "LM04" for the case