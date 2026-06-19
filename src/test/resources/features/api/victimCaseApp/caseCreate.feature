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
    And add "victim" using "LM04" for the case
    And add "victim child" using "LM04" for the case
    And add "victim expert" using "LM04" for the case
    And add "victim interpreter" using "LM04" for the case
    And add "victim intimidated" using "LM04" for the case
    And add "victim police" using "LM04" for the case
    And add "victim prisoner" using "LM04" for the case
    And add "victim professional" using "LM04" for the case
    And add "victim special" using "LM04" for the case
    And add "victim vulnerable" using "LM04" for the case


  Scenario:Sample to VCA onboard
    Given witness and victim details are available
    And the "witnessId" is onboarded to VCA
    And the "victimId" is onboarded to VCA
    And the "witnessChildId" is onboarded to VCA
    And the "witnessExpertId" is onboarded to VCA
    And the "witnessPrisonerId" is onboarded to VCA
    And the "witnessInterpreterId" is onboarded to VCA
    And the "witnessVulnerableId" is onboarded to VCA
    And the "witnessPoliceId" is onboarded to VCA
    And the "witnessProfessionalId" is onboarded to VCA
    And the "witnessIntimidatedId" is onboarded to VCA
    And the "victimChildId" is onboarded to VCA
    And the "victimExpertId" is onboarded to VCA
    And the "victimPrisonerId" is onboarded to VCA
    And the "victimInterpreterId" is onboarded to VCA
    And the "victimVulnerableId" is onboarded to VCA
    And the "victimPoliceId" is onboarded to VCA
    And the "victimProfessionalId" is onboarded to VCA
    And the "victimIntimidatedId" is onboarded to VCA
    And the "victimSpecialId" is onboarded to VCA
