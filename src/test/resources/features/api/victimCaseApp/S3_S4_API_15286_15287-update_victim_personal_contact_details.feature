@regression @vca_api_regression @VCA_API_S3 @VCA_API_S4

Feature: VCA-API-S3_S4 - Update existing victim personal and contact details and verify wm01u
  As a Victim Liaison Officer
  I want to update personal and contact details for victim
  Update information is reflected in CMS and VCA database
  Verify wm01u message is sent to police system

  Background: Create cases with multi defendant with multi charge with victim personal and contact details.
  with empty personal and contact details.
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

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

  @updatePersonalAndContactDetailsForVictim
  Scenario: Update existing title, preferred name, date of birth, gender, ethnicity, disability or access needs and previous convictions details
            and verify that update details are sent in wm01u message.
    Given victim details are available in VCA
   #Service Types - #Universal #Enhanced #Rasso #Not aligned
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    When the "victim" personal details are updated to CMS
    When the "victim" personal details are update to VCA
    Then the "victim" personal details are verified in CMS and VCA

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

