@regression @vca_api_regression @VCA_API_S1_S2

Feature: VCA-API-S1_S2 - Add personal and contact details for all category types of victims and witness and verify wm01u message
  As a Victim Liaison Officer
  I want to add personal and contact details for all category types of victims and witness
  Added information is reflected in CMS and VCA database
  Verify wm01u message is sent to police system

  Background: Create cases with single defendant with multiple charge with all category types of victims and witness
  with empty personal and contact details.

    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

#---------------PureVictim Person----------------------#
#    And add "pure victim pure" using "LM04" for the case
#    And add "pure victim vulnerable" using "LM04" for the case
#    And add "pure victim intimidated" using "LM04" for the case

#---------------VictimWitness Person----------------------#
#    And add "victim" using "LM04" for the case
#    And add "victim child" using "LM04" for the case
#    And add "victim expert" using "LM04" for the case
#    And add "victim interpreter" using "LM04" for the case
#    And add "victim intimidated" using "LM04" for the case
#    And add "victim police" using "LM04" for the case
#    And add "victim prisoner" using "LM04" for the case
#    And add "victim professional" using "LM04" for the case
#    And add "victim special" using "LM04" for the case
#    And add "victim vulnerable" using "LM04" for the case

#---------------Witness Person----------------------#
#    And add "witness child" using "LM04" for the case
#    And add "witness police" using "LM04" for the case
#    And add "witness intimidated" using "LM04" for the case
#    And add "witness vulnerable" using "LM04" for the case
#    And add "witness professional" using "LM04" for the case
#    And add "witness expert" using "LM04" for the case
#    And add "witness special" using "LM04" for the case
#    And add "witness prisoner" using "LM04" for the case
#    And add "witness interpreter" using "LM04" for the case

  @addPersonalAndContactDetailsForAllWitnessAndVictimType
  Scenario: Add witness title, preferred name, date of birth, gender, ethnicity, disability or access needs and previous convictions details
  and verify that newly added details are sent in wm01u message.
    Given victim details are available in VCA
    #Service Types - #Universal #Enhanced #Rasso #Not aligned
    And the "victimId" is onboarded as "Universal" service lead to VCA
    And the Victim liaison officer is assigned to "victimId" in VCA
#    And the "victimChildId" is onboarded as "Enhanced" service lead to VCA
#    And the "victimExpertId" is onboarded as "RASSO" service lead to VCA
#    And the "victimInterpreterId" is onboarded as "Not aligned" service lead to VCA

#---------------PureVictim Person----------------------#
#    And the "pureVictimId" is onboarded as "Universal" service lead to VCA
#    And the "pureVictimVulnerableId" is onboarded as "Universal" service lead to VCA
#    And the "pureVictimIntimidatedId" is onboarded as "Universal" service lead to VCA
#---------------VictimWitness Person----------------------#
#    And the "victimChildId" is onboarded as "Universal" service lead to VCA
#    And the "victimExpertId" is onboarded as "Universal" service lead to VCA
#    And the "victimPrisonerId" is onboarded as "Universal" service lead to VCA
#    And the "victimInterpreterId" is onboarded as "Universal" service lead to VCA
#    And the "victimVulnerableId" is onboarded as "Universal" service lead to VCA
#    And the "victimPoliceId" is onboarded as "Universal" service lead to VCA
#    And the "victimProfessionalId" is onboarded as "Universal" service lead to VCA
#    And the "victimIntimidatedId" is onboarded as "Universal" service lead to VCA
#    And the "victimSpecialId" is onboarded as "Universal" service lead to VCA

#---------------Witness Person----------------------#
#    And the "witnessChildId" is onboarded "Universal" service lead to VCA
#    And the "witnessExpertId" is onboarded "Universal" service lead to VCA
#    And the "witnessPrisonerId" is onboarded "Universal" service lead to VCA
#    And the "witnessInterpreterId" is onboarded "Universal" service lead to VCA
#    And the "witnessVulnerableId" is onboarded "Universal" service lead to VCA
#    And the "witnessPoliceId" is onboarded "Universal" service lead to VCA
#    And the "witnessProfessionalId" is onboarded "Universal" service lead to VCA
#    And the "witnessIntimidatedId" is onboarded "Universal" service lead to VCA
#    And the "witnessSpecialId" is onboarded "Universal" service lead to VCA

    When the "victimId" personal details are added to CMS
#    And the "victimChildId" personal details are added to CMS
#    And the "victimExpertId" personal details are added to CMS
#    And the "victimInterpreterId" personal details are added to CMS

#---------------PureVictim Person----------------------#
#    And the "pureVictimId" personal details are added to CMS
#    And the "pureVictimVulnerableId" personal details are added to CMS
#    And the "pureVictimIntimidatedId" personal details are added to CMS
#---------------VictimWitness Person----------------------#
#    And the "victimChildId" personal details are added to CMS
#    And the "victimExpertId" personal details are added to CMS
#    And the "victimPrisonerId" personal details are added to CMS
#    And the "victimInterpreterId" personal details are added to CMS
#    And the "victimVulnerableId" personal details are added to CMS
#    And the "victimPoliceId" personal details are added to CMS
#    And the "victimProfessionalId" personal details are added to CMS
#    And the "victimIntimidatedId" personal details are added to CMS
#    And the "victimSpecialId" personal details are added to CMS

#---------------Witness Person----------------------#
#    When the "witnessId" personal details are added to CMS
#    And the "witnessChildId" personal details are added to CMS
#    And the "witnessExpertId" personal details are added to CMS
#    And the "witnessPrisonerId" personal details are added to CMS
#    And the "witnessInterpreterId" personal details are added to CMS
#    And the "witnessVulnerableId" personal details are added to CMS
#    And the "witnessPoliceId" personal details are added to CMS
#    And the "witnessProfessionalId" personal details are added to CMS
#    And the "witnessIntimidatedId" personal details are added to CMS
#    And the "witnessSpecialId" personal details are added to CMS

    When the "victimId" personal details are added to VCA
#    And the "witnessChildId" personal details are added to VCA
#    And the "witnessExpertId" personal details are added to VCA
#    And the "witnessInterpreterId" personal details are added to VCA

#---------------PureVictim Person----------------------#
#    And the "pureVictimId" personal details are added to VCA
#    And the "pureVictimVulnerableId" personal details are added to VCA
#    And the "pureVictimIntimidatedId" personal details are added to VCA
#---------------VictimWitness Person----------------------#
#    And the "victimChildId" personal details are added to VCA
#    And the "victimExpertId" personal details are added to VCA
#    And the "victimPrisonerId" personal details are added to VCA
#    And the "victimInterpreterId" personal details are added to VCA
#    And the "victimVulnerableId" personal details are added to VCA
#    And the "victimPoliceId" personal details are added to VCA
#    And the "victimProfessionalId" personal details are added to VCA
#    And the "victimIntimidatedId" personal details are added to VCA
#    And the "victimSpecialId" personal details are added to VCA

#---------------Witness Person----------------------#
#    When the "witnessId" personal details are added to VCA
#    And the "witnessChildId" personal details are added to VCA
#    And the "witnessExpertId" personal details are added to VCA
#    And the "witnessPrisonerId" personal details are added to VCA
#    And the "witnessInterpreterId" personal details are added to VCA
#    And the "witnessVulnerableId" personal details are added to VCA
#    And the "witnessPoliceId" personal details are added to VCA
#    And the "witnessProfessionalId" personal details are added to VCA
#    And the "witnessIntimidatedId" personal details are added to VCA
#    And the "witnessSpecialId" personal details are added to VCA


    Then the "victimId" personal details are verified in CMS and VCA
#    And the "victimChildId" personal details are verified in CMS and VCA
#    And the "victimExpertId" personal details are verified in CMS and VCA
#    And the "victimInterpreterId" personal details are verified in CMS and VCA

#---------------PureVictim Person----------------------#
#    And the "PureVictimId" personal details are verified in CMS and VCA
#    And the "pureVictimVulnerableId" personal details are verified in CMS and VCA
#    And the "pureVictimIntimidatedId" personal details are verified in CMS and VCA
#---------------VictimWitness Person----------------------#
#    And the "victimChildId" personal details are verified in CMS and VCA
#    And the "victimExpertId" personal details are verified in CMS and VCA
#    And the "victimPrisonerId" personal details are verified in CMS and VCA
#    And the "victimInterpreterId" personal details are verified in CMS and VCA
#    And the "victimVulnerableId" personal details are verified in CMS and VCA
#    And the "victimPoliceId" personal details are verified in CMS and VCA
#    And the "victimProfessionalId" personal details are verified in CMS and VCA
#    And the "victimIntimidatedId" personal details are verified in CMS and VCA
#    And the "victimSpecialId" personal details are verified in CMS and VCA

#---------------Witness Person----------------------#
#    Then the "witnessId" personal details are verified in CMS and VCA
#    And the "witnessChildId" personal details are verified in CMS and VCA
#    And the "witnessExpertId" personal details are verified in CMS and VCA
#    And the "witnessPrisonerId" personal details are verified in CMS and VCA
#    And the "witnessInterpreterId" personal details are verified in CMS and VCA
#    And the "witnessVulnerableId" personal details are verified in CMS and VCA
#    And the "witnessPoliceId" personal details are verified in CMS and VCA
#    And the "witnessProfessionalId" personal details are verified in CMS and VCA
#    And the "witnessIntimidatedId" personal details are verified in CMS and VCA
#    And the "witnessSpecialId" personal details are verified in CMS and VCA