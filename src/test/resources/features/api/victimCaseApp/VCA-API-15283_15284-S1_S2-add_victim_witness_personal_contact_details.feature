@regression @vca_api_regression @VCA_API_S1_S2

Feature: VCA-API-S1_S2 - Add personal and contact details for all category types of victims and victims witness and verify wm01u message
  As a Victim Liaison Officer
  I want to add personal and contact details for all category types of victims and victims witness
  Added information is reflected in CMS and VCA database
  Verify wm01u message is sent to police system

  Background: Create cases with single defendant with multiple charge with all category types of victims and victims witness
  with empty personal and contact details.

    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case
#    And add "victim vulnerable" using "LM04" for the case
#    And add "victim intimidated" using "LM04" for the case
#    And add "victim witness" using "LM04" for the case
#    And add "victim witness child" using "LM04" for the case  
                                          
#---------------Victim Person----------------------#
#  victim
#  victim vulnerable
#  victim intimidated
#---------------Victim Witness Person----------------------#
#    "victim witness"
#    "victim witness child "
#    "victim witness expert"
#    "victim witness interpreter"
#    "victim witness intimidated"
#    "victim witness police"
#    "victim witness prisoner"
#    "victim witness professional"
#    "victim witness special"
#    "victim witness vulnerable"
#---------------Witness Person----------------------#
#    "witness"
#    "witness child"
#    "witness police"
#    "witness intimidated"
#    "witness vulnerable"
#    "witness professional"
#    "witness expert"
#    "witness special"
#    "witness prisoner"
#    "witness interpreter"

  @addPersonalAndContactDetailsForAllWitnessAndVictimType
  Scenario: Add victim title, preferred name, date of birth, gender, ethnicity, disability or access needs and previous convictions details
  and verify that newly added details are sent in wm01u message.
    Given victim details are available in VCA
   #Service Types - #Universal #Enhanced #Rasso #Not aligned
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    
#    And the "victimVulnerable" is onboarded as "Enhanced" service lead in VCA
#    And the Victim liaison officer is assigned to "victimVulnerable" in VCA
#    And the "victimIntimidated" is onboarded as "Rasso" service lead in VCA
#    And the Victim liaison officer is assigned to "victimIntimidated" in VCA
#    And the "victimWitness" is onboarded as "Not aligned" service lead in VCA
#    And the Victim liaison officer is assigned to "victimWitness" in VCA
#    And the "victimWitnessChild" is onboarded as "Universal" service lead in VCA
#    And the Victim liaison officer is assigned to "victimWitnessChild" in VCA

    When the "victim" personal details are added to CMS
    When the "victim" personal details are added to VCA
#    When the "victimVulnerable" personal details are added to CMS
#    When the "victimVulnerable" personal details are added to VCA
#    When the "victimIntimidated" personal details are added to CMS
#    When the "victimIntimidated" personal details are added to VCA
#    When the "victimWitness" personal details are added to CMS
#    When the "victimWitness" personal details are added to VCA
#    When the "victimWitnessChild" personal details are added to CMS
#    When the "victimWitnessChild" personal details are added to VCA

    Then the "victim" personal details are verified in CMS and VCA
#    Then the "victimVulnerable" personal details are verified in CMS and VCA
#    Then the "victimIntimidated" personal details are verified in CMS and VCA
#    Then the "victimWitness" personal details are verified in CMS and VCA
#    Then the "victimWitnessChild" personal details are verified in CMS and VCA

#---------------Victim Person----------------------#
#  "victim"
#  "victimVulnerable"
#  "victimIntimidated"
#---------------VictimWitness Person----------------------#
#  "victimWitness"
#  "victimWitnessChild"
#  "victimWitnessExpert"
#  "victimWitnessPrisoner"
#  "victimWitnessInterpreter"
#  "victimWitnessVulnerable"
#  "victimWitnessPolice"
#  "victimWitnessProfessional"
#  "victimWitnessIntimidated"
#  "victimWitnessSpecial"
#---------------Witness Person----------------------#
#  "witness"
#  "witnessChild"
#  "witnessExpert"
#  "witnessPrisoner"
#  "witnessInterpreter"
#  "witnessVulnerable"
#  "witnessPolice"
#  "witnessProfessional"
#  "witnessIntimidated"
#  "witnessSpecial"