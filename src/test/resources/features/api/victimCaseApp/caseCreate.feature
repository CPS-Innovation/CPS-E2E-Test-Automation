@regression @VCA

Feature: sample

  Scenario: Sample
    Given create new case using "CM01" for type "single defendant multiple offence"

    And add "victim" using "LM04" for the case
    And add "victim intimidated" using "LM04" for the case
    And add "victim vulnerable" using "LM04" for the case
#
#    And add "witness" using "LM04" for the case
#    And add "witness child" using "LM04" for the case
#    And add "witness expert" using "LM04" for the case
#    And add "witness interpreter" using "LM04" for the case
#    And add "witness intimidated" using "LM04" for the case
#    And add "witness police" using "LM04" for the case
#    And add "witness prisoner" using "LM04" for the case
#    And add "witness professional" using "LM04" for the case
#    And add "witness vulnerable" using "LM04" for the case
###
##
#    And add "victim witness" using "LM04" for the case
#    And add "victim witness child" using "LM04" for the case
#    And add "victim witness expert" using "LM04" for the case
#    And add "victim witness interpreter" using "LM04" for the case
#    And add "victim witness intimidated" using "LM04" for the case
#    And add "victim witness police" using "LM04" for the case
#    And add "victim witness prisoner" using "LM04" for the case
#    And add "victim witness professional" using "LM04" for the case
#    And add "victim witness vulnerable" using "LM04" for the case
#
    When the "victim" details are available in CMS
    When the "victimIntimidated" details are available in CMS
    When the "victimVulnerable" details are available in CMS

#    When the "witness" details are available in CMS
#    When the "witnessChild" details are available in CMS
#    When the "witnessExpert" details are available in CMS
#    When the "witnessInterpreter" details are available in CMS
#    When the "witnessIntimidated" details are available in CMS
#    When the "witnessPolice" details are available in CMS
#    When the "witnessPrisoner" details are available in CMS
#    When the "witnessProfessional" details are available in CMS
#    When the "witnessVulnerable" details are available in CMS
#
#    When the "victimWitness" details are available in CMS
#    When the "victimWitnessChild" details are available in CMS
#    When the "victimWitnessExpert" details are available in CMS
#    When the "victimWitnessInterpreter" details are available in CMS
#    When the "victimWitnessIntimidated" details are available in CMS
#    When the "victimWitnessPolice" details are available in CMS
#    When the "victimWitnessPrisoner" details are available in CMS
#    When the "victimWitnessProfessional" details are available in CMS
#    When the "victimWitnessVulnerable" details are available in CMS


