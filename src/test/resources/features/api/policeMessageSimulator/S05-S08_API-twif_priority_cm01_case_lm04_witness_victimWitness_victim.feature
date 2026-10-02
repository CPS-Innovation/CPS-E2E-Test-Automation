@regression @pms_api_regression @PMS_API_S5 @PMS_API_S6 @PMS_API_S7 @PMS_API_S8

Feature: PMS-API-S5-S6-S7-S8:- Create TWIF priority case with
  1. single suspect with single offence priority case using CM01
  2. single suspect with multiple offence priority case using CM01
  3. multiple suspect with single offence priority case using CM01
  4. multiple suspect with multiple offence priority case using CM01
  and create witness, victimWitness and victim using LM04 for each priority cases
  verify created priority case and witness, victimWitness and victim

  @S5_TWIF_PRIORITY_CM01_SS_SO_LM04_W_VW_V
  Scenario: TWIF - Create priority case with single suspect with single offence
  and add witness, victimWitness and victim to the case. (FCT2-21812)

    Given create new case using "CM01" for type "priority single suspect single offence"
    And add "priority case victim" using "LM04" for the case
    And add "priority case victim witness" using "LM04" for the case
    And add "priority case witness" using "LM04" for the case

  @S6_TWIF_PRIORITY_CM01_SS_MO_LM04_W_VW_V
  Scenario: TWIF - Create priority case with single suspect with multiple offence
  and add witness, victimWitness and victim to the case. (FCT2-21813)

    Given create new case using "CM01" for type "priority single suspect multiple offence"
    And add "priority case victim" using "LM04" for the case
    And add "priority case victim witness" using "LM04" for the case
    And add "priority case witness" using "LM04" for the case

  @S7_TWIF_PRIORITY_CM01_MS_SO_LM04_W_VW_V
  Scenario: TWIF - Create priority case with multiple suspect with single offence
  and add witness, victimWitness and victim to the case. (FCT2-21814)

    Given create new case using "CM01" for type "priority multiple suspect single offence"
    And add "priority case victim" using "LM04" for the case
    And add "priority case victim witness" using "LM04" for the case
    And add "priority case witness" using "LM04" for the case

  @S8_TWIF_PRIORITY_CM01_MS_MO_LM04_W_VW_V
  Scenario: TWIF - Create priority case with multiple suspect with multiple offence
  and add witness, victimWitness and victim to the case. (FCT2-21815)

    Given create new case using "CM01" for type "priority multiple suspect multiple offence"
    And add "priority case victim" using "LM04" for the case
    And add "priority case victim witness" using "LM04" for the case
    And add "priority case witness" using "LM04" for the case


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
