@regression @VCA_API_S10

Feature: VCA-API-S10 -

  Background: Create DCF cases with single defendant with multi charge with victim and witness
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case
    And add "witness" using "LM04" for the case

