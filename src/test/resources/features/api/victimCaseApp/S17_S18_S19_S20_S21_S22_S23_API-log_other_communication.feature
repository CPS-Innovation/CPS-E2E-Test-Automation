@regression @vca_api_regression @VCA_API_S17 @VCA_API_S18 @VCA_API_S19 @VCA_API_S20 @VCA_API_S21 @VCA_API_S22

Feature: VCA-API-S16 - Log other communications with the purpose information to victims.
  As a Victim Liaison Officer
  I want to record other communications to victim for following types:-
  ------------------------------------------------------------------
  ¦ Communication Type ¦ Direction  ¦ Person contacted ¦ Purpose   ¦
  ¦--------------------¦------------¦------------------¦-----------¦
  ¦ 1.Telephone call   ¦ 1.Inbound  ¦ 1.Victim         ¦ Free Text ¦
  ¦ 2.Email            ¦ 2.Outbound ¦ 2.Other          ¦           ¦
  ¦ 3.Post             ¦            ¦                  ¦           ¦
  ¦ 4.Text Message     ¦            ¦                  ¦           ¦
  ¦ 5.In person        ¦            ¦                  ¦           ¦
  ¦ 6.Other            ¦            ¦                  ¦           ¦
  ------------------------------------------------------------------
  Verify that communication details are recorded.

  Background: Create cases with single defendant with multi charge with victim and witness
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

  @logOtherCommunications
  Scenario: Communication is logged with the purpose information to victims
    Given victim details are available in VCA
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    When the following "other" communication are logged to "victim" in VCA
      | communicationType | direction | personContacted | purpose                             |
      | Telephone call    | Inbound   | Victim          | Telephone call communication reason |
      | Email             | Outbound  | Other           | Email communication reason          |
      | Post              | Inbound   | Other           | Post communication reason           |
      | Text Message      | Outbound  | Victim          | Text Message communication reason   |
      | In Person         | Inbound   | Other           | In person communication reason      |
      | Other             | Outbound  | Victim          | Other communication reason          |
    Then verify the logged other communication for the "victim" in VCA
