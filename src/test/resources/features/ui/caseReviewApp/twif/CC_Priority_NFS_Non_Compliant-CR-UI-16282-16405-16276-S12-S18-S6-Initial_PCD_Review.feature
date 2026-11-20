@regression @caseReview @ui
Feature: CR-UI-S12 - CC Initial PCD Review for Priority NFS Non Compliant
  As a prosecutor lawyer,
  I want to complete CC initial NFS Compliant day PCD reviews for FullCode, Threshold and Early Advice Tests
  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.

  Background: Case Creation
    Given create new case using "CM01" for type "priority single suspect single offence"
    And precharge the RED "CC" triage case for "NFS Non Compliant" PCD review


  @CR_UI_16282 @CR_UI_S12 @ThresholdTestWith7DaysActionPlan
  Scenario: Priority NFS Non Compliant PCD review of test type CC and Threshold Test with 7 days action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "Priority PCD Review"
    And select test as "Threshold Test"
    And I complete the Threshold Test pre-charge analysis details with:
      | field                       | value                               |
      | Suspect-victim relationship | Partner                             |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I preview pre charge analysis
    When I make charging decision as following:
      | decision type     | decision code                   | reason | out come of case | offence category |
      | No further action | K - No prosecution - Evidential | D81    | D80              | Robbery          |

    And I add an action point plan with:
      | field              | value                |
      | Date required by   | 7 days               |
      | Chaser task        | No                   |
      | Related to suspect | All                  |
      | Action             | Orders on Conviction |

    And I submit the charging decision as following
      | Investigative stage        | Method | Create MG3 document |
      | Bail for charging decision | Area   | False               |

    Then review is submitted successfully


  @CR_UI_16405 @CR_UI_S18 @FullCodeTestWithoutActionPlan
  Scenario: Priority NFS Non Compliant PCD review of test type CC and FullCode Test without action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "Priority PCD Review"
    And select test as "Full Code Test"
    And I complete the pre-charge analysis details with:
      | field                       | value                              |
      | Suspect-victim relationship | Partner                            |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I preview pre charge analysis
    And I choose DG compliant as Yes
    When I make charging decision as following:
      | decision type     | decision code                   | reason | out come of case | offence category |
      | No further action | K - No prosecution - Evidential | D81    | D80              | Robbery          |

    And I continue without action plan
    And I submit the charging decision as following
      | Investigative stage        | Method | Create MG3 document |
      | Bail for charging decision | Area   | False               |

    Then review is submitted successfully

  @CR_UI_16276 @CR_UI_S6 @EarlyAdviceWith7DaysActionPlan
  Scenario: Priority NFS Non Compliant PCD review of test type CC and Early Advise with 7 days action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "Priority PCD Review"
    And select test as "Early Advice"
    And I complete the Early Advice pre-charge analysis details with:
      | field                       | value                              |
      | Suspect-victim relationship | Partner                            |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I select PCD principal offence category as "Robbery"
    And I add an action point plan with:
      | field              | value                |
      | Date required by   | 7 days               |
      | Chaser task        | No                   |
      | Related to suspect | All                  |
      | Action             | Orders on Conviction |

    And I submit the charging decision as following
      | Investigative stage        | Create MG3 document |
      | Bail for charging decision | False               |

    Then review is submitted successfully
