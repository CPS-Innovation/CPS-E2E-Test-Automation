@DCF @vca @regression

Feature: Add witness personal details and verify wm01u
  As a Victim Liaison Officer
  I want to update personal details of witness
  Updated information is reflected in CMS and wm01u message is sent to police system

  @AddWitness @JSONMerge
  Scenario: Add witness title, date of birth, address, contact and email details.
#    Given create case CM01 for type "single defendant with charge"
#    Given create case CM01 for type "single defendant with multi charge"
#    Given create case CM01 for type "multi defendant with charge"
#    Given create case CM01 for type "multi defendant with multi charge"
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


#    And witness details are available
#    And witness and victim are added to VCA
#    When the witness's personal details are entered
#    And the witness personal details are sent to VCA
#    Then Witness personal details are updated correctly to CMS
#    And Witness personal details are updated correctly to VCA

