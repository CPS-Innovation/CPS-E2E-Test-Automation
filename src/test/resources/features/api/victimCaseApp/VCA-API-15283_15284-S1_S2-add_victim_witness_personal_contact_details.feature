@regression @VCA_API_S1_S2

Feature: VCA-API-S1_S2 - Add personal and contact details for all category types of witness and victim and verify wm01u message
  As a Victim Liaison Officer
  I want to add personal and contact details for all category types of witness and victim
  Added information is reflected in CMS and VCA database
  Verify wm01u message is sent to police system

  Background: Create cases with single defendant with multiple charge with all category types of witness and victim
  with empty personal and contact details.

    Given create new case using "CM01" for type "single defendant multiple offence"
#    And add "witness" using "LM04" for the case
#
#    And add "victim" using "LM04" for the case

#    And add "victim pure" using "LM04" for the case
    And add "victim pure vulnerable" using "LM04" for the case
    And add "victim pure intimidated" using "LM04" for the case

#    And add "witness child" using "LM04" for the case
#    And add "witness police" using "LM04" for the case
#    And add "witness intimidated" using "LM04" for the case
#    And add "witness vulnerable" using "LM04" for the case
#    And add "witness professional" using "LM04" for the case
#    And add "witness expert" using "LM04" for the case
#    And add "witness special" using "LM04" for the case
#    And add "witness prisoner" using "LM04" for the case
#    And add "witness interpreter" using "LM04" for the case


#    And add "victim child" using "LM04" for the case
#    And add "victim expert" using "LM04" for the case
#    And add "victim interpreter" using "LM04" for the case
#    And add "victim intimidated" using "LM04" for the case
#    And add "victim police" using "LM04" for the case
#    And add "victim prisoner" using "LM04" for the case
#    And add "victim professional" using "LM04" for the case
#    And add "victim special" using "LM04" for the case
#    And add "victim vulnerable" using "LM04" for the case

  @addPersonalAndContactDetailsForAllWitnessAndVictimType
  Scenario: Add witness title, preferred name, date of birth, gender, ethnicity, disability or access needs and previous convictions details
  and verify that newly added details are sent in wm01u message.
    Given witness and victim details are available
#    And the "witnessId" is onboarded to VCA
#    And the "victimId" is onboarded to VCA
#    And the "pureVictimId" is onboarded to VCA

#    And the "witnessChildId" is onboarded to VCA
#    And the "witnessExpertId" is onboarded to VCA
#    And the "witnessPrisonerId" is onboarded to VCA
#    And the "witnessInterpreterId" is onboarded to VCA
#    And the "witnessVulnerableId" is onboarded to VCA
#    And the "witnessPoliceId" is onboarded to VCA
#    And the "witnessProfessionalId" is onboarded to VCA
#    And the "witnessIntimidatedId" is onboarded to VCA
#    And the "witnessSpecialId" is onboarded to VCA
#
#    And the "victimChildId" is onboarded to VCA
#    And the "victimExpertId" is onboarded to VCA
#    And the "victimPrisonerId" is onboarded to VCA
#    And the "victimInterpreterId" is onboarded to VCA
#    And the "victimVulnerableId" is onboarded to VCA
#    And the "victimPoliceId" is onboarded to VCA
#    And the "victimProfessionalId" is onboarded to VCA
#    And the "victimIntimidatedId" is onboarded to VCA
#    And the "victimSpecialId" is onboarded to VCA

    When the "witnessId" personal details are added to CMS
    And the "victimId" personal details are added to CMS
    And the "pureVictimId" personal details are added to CMS

#    And the "witnessChildId" personal details are added to CMS
#    And the "witnessExpertId" personal details are added to CMS
#    And the "witnessPrisonerId" personal details are added to CMS
#    And the "witnessInterpreterId" personal details are added to CMS
#    And the "witnessVulnerableId" personal details are added to CMS
#    And the "witnessPoliceId" personal details are added to CMS
#    And the "witnessProfessionalId" personal details are added to CMS
#    And the "witnessIntimidatedId" personal details are added to CMS
#    And the "witnessSpecialId" personal details are added to CMS
#
#    And the "victimChildId" personal details are added to CMS
#    And the "victimExpertId" personal details are added to CMS
#    And the "victimPrisonerId" personal details are added to CMS
#    And the "victimInterpreterId" personal details are added to CMS
#    And the "victimVulnerableId" personal details are added to CMS
#    And the "victimPoliceId" personal details are added to CMS
#    And the "victimProfessionalId" personal details are added to CMS
#    And the "victimIntimidatedId" personal details are added to CMS
#    And the "victimSpecialId" personal details are added to CMS

#    When the "witnessId" personal details are added to VCA
#    And the "victimId" personal details are added to VCA
#    And the "pureVictimId" personal details are added to VCA

#    And the "witnessChildId" personal details are added to VCA
#    And the "witnessExpertId" personal details are added to VCA
#    And the "witnessPrisonerId" personal details are added to VCA
#    And the "witnessInterpreterId" personal details are added to VCA
#    And the "witnessVulnerableId" personal details are added to VCA
#    And the "witnessPoliceId" personal details are added to VCA
#    And the "witnessProfessionalId" personal details are added to VCA
#    And the "witnessIntimidatedId" personal details are added to VCA
#    And the "witnessSpecialId" personal details are added to VCA
#
#    And the "victimChildId" personal details are added to VCA
#    And the "victimExpertId" personal details are added to VCA
#    And the "victimPrisonerId" personal details are added to VCA
#    And the "victimInterpreterId" personal details are added to VCA
#    And the "victimVulnerableId" personal details are added to VCA
#    And the "victimPoliceId" personal details are added to VCA
#    And the "victimProfessionalId" personal details are added to VCA
#    And the "victimIntimidatedId" personal details are added to VCA
#    And the "victimSpecialId" personal details are added to VCA

#    Then the "witnessId" personal details are verified in CMS and VCA
#    And the "victimId" personal details are verified in CMS and VCA
#    And the "PureVictimId" personal details are verified in CMS and VCA

#    And the "witnessChildId" personal details are verified in CMS and VCA
#    And the "witnessExpertId" personal details are verified in CMS and VCA
#    And the "witnessPrisonerId" personal details are verified in CMS and VCA
#    And the "witnessInterpreterId" personal details are verified in CMS and VCA
#    And the "witnessVulnerableId" personal details are verified in CMS and VCA
#    And the "witnessPoliceId" personal details are verified in CMS and VCA
#    And the "witnessProfessionalId" personal details are verified in CMS and VCA
#    And the "witnessIntimidatedId" personal details are verified in CMS and VCA
#    And the "witnessSpecialId" personal details are verified in CMS and VCA
#
#    And the "victimChildId" personal details are verified in CMS and VCA
#    And the "victimExpertId" personal details are verified in CMS and VCA
#    And the "victimPrisonerId" personal details are verified in CMS and VCA
#    And the "victimInterpreterId" personal details are verified in CMS and VCA
#    And the "victimVulnerableId" personal details are verified in CMS and VCA
#    And the "victimPoliceId" personal details are verified in CMS and VCA
#    And the "victimProfessionalId" personal details are verified in CMS and VCA
#    And the "victimIntimidatedId" personal details are verified in CMS and VCA
#    And the "victimSpecialId" personal details are verified in CMS and VCA
