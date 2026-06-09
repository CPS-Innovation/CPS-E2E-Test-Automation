@regression @VCA

Feature: sample

  Background: Case Creation
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "witness" using "LM04" for the case
    And add "witness child" using "LM04" for the case
    And add "witness police" using "LM04" for the case
    And add "witness intimidated" using "LM04" for the case
    And add "witness vulnerable" using "LM04" for the case
    And add "witness professional" using "LM04" for the case
    And add "witness expert" using "LM04" for the case
    And add "witness special" using "LM04" for the case
    And add "witness prisoner" using "LM04" for the case
    And add "witness interpreter" using "LM04" for the case

  Scenario:Sample to VCA onboard
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
#    And the "victimId" is onboarded to VCA
#    When the "witnessId" personal details are added to CMS
#    And the "victimId" personal details are added to CMS
#    When the "witnessId" personal details are added to VCA
#    And the "victimId" personal details are added to VCA
#    Then the "witnessId" personal details are verified in CMS and VCA
#    And the "victimId" personal details are verified in CMS and VCA