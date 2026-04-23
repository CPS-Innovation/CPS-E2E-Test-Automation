@DCF @regression @VCA_API_DCF_S10

Feature: VCA-API-S10 -

  Background: Create DCF cases with single defendant with multi charge with victim and witness
    Given create case CM01 for type "single defendant with witness victim"
    And a "witness details" is added with details using LM04
    And a "victim details" is added with details using LM04

