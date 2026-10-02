@regression @pms_api_regression @PMS_API_S20 @PMS_API_S21 @PMS_API_S22 @PMS_API_S

Feature: PMS-API-S20-S21-S22-S4:- Create DCF case with
  1. single suspect with single offence case using CM01
  2. single suspect with multiple offence case using CM01
  3. multiple suspect with single offence using CM01
  4. multiple suspect with multiple offence using CM01
  and create witness, victimWitness and victim using LM04
  verify created case and witness, victimWitness and victim

  @S_DCF_CM01_SS_SO_LM04_W_VW_V
  Scenario: DCF - Create case with single suspect with single offence
  and add witness, victimWitness and victim to the case. (FCT2-21827)

    Given create new case using "CM01" for type "priority single suspect single offence"
#    And add "victim" using "LM04" for the case
#    And add "victim witness" using "LM04" for the case
#    And add "witness" using "LM04" for the case

#  @S21_DCF_CM01_SS_MO_LM04_W_VW_V
#  Scenario: DCF - Create case with single suspect with multiple offence
#  and add witness, victimWitness and victim to the case. (FCT2-21828)
#
#    Given create new case using "CM01" for type "single suspect multiple offence"
#    And add "victim" using "LM04" for the case
#    And add "victim witness" using "LM04" for the case
#    And add "witness" using "LM04" for the case
#
#  @S3_DCF_CM01_MS_SO_LM04_W_VW_V
#  Scenario: DCF - Create case with multiple suspect with single offence
#  and add witness, victimWitness and victim to the case. (FCT2-)
#
#    Given create new case using "CM01" for type "multiple suspect single offence"
#    And add "victim" using "LM04" for the case
#    And add "victim witness" using "LM04" for the case
#    And add "witness" using "LM04" for the case
#
#  @S4_DCF_CM01_MS_MO_LM04_W_VW_V
#  Scenario: DCF - Create case with multiple suspect with multiple offence
#  and add witness, victimWitness and victim to the case. (FCT2-21829)
#
#    Given create new case using "CM01" for type "multiple suspect multiple offence"
#    And add "victim" using "LM04" for the case
#    And add "victim witness" using "LM04" for the case
#    And add "witness" using "LM04" for the case


#    And add "victim vulnerable" using "LM04" for the case
#    And add "victim intimidated" using "LM04" for the case
#
#    And add "victim witness child" using "LM04" for the case
#    And add "victim witness expert" using "LM04" for the case
#    And add "victim witness interpreter" using "LM04" for the case
#    And add "victim witness intimidated" using "LM04" for the case
#    And add "victim witness police" using "LM04" for the case
#    And add "victim witness prisoner" using "LM04" for the case
#    And add "victim witness professional" using "LM04" for the case
#    And add "victim witness vulnerable" using "LM04" for the case
#
#    And add "witness child" using "LM04" for the case
#    And add "witness expert" using "LM04" for the case
#    And add "witness interpreter" using "LM04" for the case
#    And add "witness intimidated" using "LM04" for the case
#    And add "witness police" using "LM04" for the case
#    And add "witness prisoner" using "LM04" for the case
#    And add "witness professional" using "LM04" for the case
#    And add "witness vulnerable" using "LM04" for the case



#---------------Victim Person----------------------#
#   "victim"
#   "victim vulnerable"
#   "victim intimidated"
#---------------Victim Witness Person----------------------#
#    "victim witness"
#    "victim witness child "
#    "victim witness expert"
#    "victim witness interpreter"
#    "victim witness intimidated"
#    "victim witness police"
#    "victim witness prisoner"
#    "victim witness professional"
#    "victim witness vulnerable"
#---------------Witness Person----------------------#
#    "witness"
#    "witness child"
#    "witness police"
#    "witness intimidated"
#    "witness vulnerable"
#    "witness professional"
#    "witness expert"
#    "witness prisoner"
#    "witness interpreter"

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
