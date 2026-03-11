@DCF @vca @regression @S1_addWitnessVictimPersonalDetails

Feature: Add witness and victim personal details and verify wm01u
  As a Victim Liaison Officer
  I want to update witness and victim personal details
  Updated information is reflected in CMS and wm01u message is sent to police system

  Background:
    Given create case CM01 for type "single defendant with charge with all types witness victim"
    And a "witness" is added using LM04
    And a "witness child" is added using LM04
    And a "witness police" is added using LM04
    And a "witness intimidated" is added using LM04
    And a "witness vulnerable" is added using LM04
    And a "witness professional" is added using LM04
    And a "witness expert" is added using LM04
    And a "witness prisoner" is added using LM04
    And a "witness interpreter" is added using LM04
    And a "victim" is added using LM04
    And a "victim child" is added using LM04
    And a "victim vulnerable" is added using LM04
    And a "victim intimidated" is added using LM04
    And a "victim professional" is added using LM04
    And a "victim expert" is added using LM04
    And a "victim prisoner" is added using LM04
    And a "victim interpreter" is added using LM04
    And a "victim police" is added using LM04

  @witnessAddPersonalDetails
  Scenario: Add witness title, preferred name, date of birth, gender, ethnicity, disability or access needs and previous convictions details
    Given witness and victim details are available
    When the "witnessId" personal details are added to CMS
    And the "witnessChildId" personal details are added to CMS
    And the "witnessExpertId" personal details are added to CMS
    And the "witnessPrisonerId" personal details are added to CMS
    And the "witnessInterpreterId" personal details are added to CMS
    And the "witnessVulnerableId" personal details are added to CMS
    And the "witnessPoliceId" personal details are added to CMS
    And the "witnessProfessionalId" personal details are added to CMS
    And the "witnessIntimidatedId" personal details are added to CMS
    And the "victimId" personal details are added to CMS
    And the "victimChildId" personal details are added to CMS
    And the "victimExpertId" personal details are added to CMS
    And the "victimPrisonerId" personal details are added to CMS
    And the "victimInterpreterId" personal details are added to CMS
    And the "victimVulnerableId" personal details are added to CMS
    And the "victimPoliceId" personal details are added to CMS
    And the "victimProfessionalId" personal details are added to CMS
    And the "victimIntimidatedId" personal details are added to CMS
    
    When the "witnessId" is onboarded and personal details are added to VCA
    And the "witnessChildId" is onboarded and personal details are added to VCA
    And the "witnessExpertId" is onboarded and personal details are added to VCA
    And the "witnessPrisonerId" is onboarded and personal details are added to VCA
    And the "witnessInterpreterId" is onboarded and personal details are added to VCA
    And the "witnessVulnerableId" is onboarded and personal details are added to VCA
    And the "witnessPoliceId" is onboarded and personal details are added to VCA
    And the "witnessProfessionalId" is onboarded and personal details are added to VCA
    And the "witnessIntimidatedId" is onboarded and personal details are added to VCA
    And the "victimId" is onboarded and personal details are added to VCA
    And the "victimChildId" is onboarded and personal details are added to VCA
    And the "victimExpertId" is onboarded and personal details are added to VCA
    And the "victimPrisonerId" is onboarded and personal details are added to VCA
    And the "victimInterpreterId" is onboarded and personal details are added to VCA
    And the "victimVulnerableId" is onboarded and personal details are added to VCA
    And the "victimPoliceId" is onboarded and personal details are added to VCA
    And the "victimProfessionalId" is onboarded and personal details are added to VCA
    And the "victimIntimidatedId" is onboarded and personal details are added to VCA
    
    Then the "witnessId" personal details are verified in CMS and VCA
    And the "witnessChildId" personal details are verified in CMS and VCA
    And the "witnessExpertId" personal details are verified in CMS and VCA
    And the "witnessPrisonerId" personal details are verified in CMS and VCA
    And the "witnessInterpreterId" personal details are verified in CMS and VCA
    And the "witnessVulnerableId" personal details are verified in CMS and VCA
    And the "witnessPoliceId" personal details are verified in CMS and VCA
    And the "witnessProfessionalId" personal details are verified in CMS and VCA
    And the "witnessIntimidatedId" personal details are verified in CMS and VCA
    And the "victimId" personal details are verified in CMS and VCA
    And the "victimChildId" personal details are verified in CMS and VCA
    And the "victimExpertId" personal details are verified in CMS and VCA
    And the "victimPrisonerId" personal details are verified in CMS and VCA
    And the "victimInterpreterId" personal details are verified in CMS and VCA
    And the "victimVulnerableId" personal details are verified in CMS and VCA
    And the "victimPoliceId" personal details are verified in CMS and VCA
    And the "victimProfessionalId" personal details are verified in CMS and VCA
    And the "victimIntimidatedId" personal details are verified in CMS and VCA