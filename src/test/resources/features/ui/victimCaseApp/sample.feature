@sample


Feature: Sample


  @ui @ui_sample
  Scenario: Victim or VictimWitness decline an offered different meeting types. (FCT2-15847 & FCT2-15835 & FCT2-20835)
    Given VLO login to victim case application
    Then the offered meeting and response is verified for "victimWitness" in VCA application
      | meetingType                           | offerMethod     | meetingResponse        | meetingResponseMethod |
      | CPS pre-trial meeting                 | Letter by post  | Log meeting acceptance | Letter by email       |
#      | Inform victim about charging decision | Letter by email | Log meeting declined   | Letter by post        |
#      | Other                                 | By telephone    | Log no response        |                       |




#    And the "victimWitness" is onboarded to "Universal" service lead
#  Scenario:Sample
#    Given create new case using "CM01" for type "multiple suspect multiple offence"
#    And add "victim witness" using "LM04" for the case
#    And add "victim witness child" using "LM04" for the case
#    And the "victimWitness" person details are available in CMS
#    And the "victimWitnessChild" person details are available in CMS