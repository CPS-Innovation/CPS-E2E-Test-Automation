@regression @caseReview @ui
Feature: CR-UI-S15_S3_S9 - RASSO Initial PCD Review
  As a prosecutor lawyer,
  I want to complete RASSO initial 28 day PCD reviews for Full Code, Threshold Test and Early Advice
  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.

  Background: Case Creation
    Given create new case using "CM01" for type "single suspect multi offence"
    And precharge the "RASSO" triage case for "28Day" PCD review


  @CR_UI_16402 @CR_UI_S15 @fullCodeWith7DaysActionPlan
  Scenario: 28 days RASSO PCD review of test type full code with 7 days action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "28 day PCD Review"
    And select test as "Full Code Test"
    And I complete the pre-charge analysis details with:
      | field                       | value                               |
      | Suspect-victim relationship | Partner                             |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I preview pre charge analysis
    And I choose DG compliant as Yes
    When I make charging decision as following:
      | decision type             | decision code                                              | offence category |
      | Further evidence required | H - Request further evidence to complete evidential report | Robbery          |

    And I add an action point plan for "7 days" and "Key Witness Details"
    And I submit the charging decision as following
      | Investigative stage        | Method | Create MG3 document |
      | Bail for charging decision | Area   | true                |

    Then review is submitted successfully


  @CR_UI_16279 @CR_UI_S9 @thresholdWith7daysActionPlan
  Scenario: 28 days RASSO PCD review of test type Threshold with 7 days action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "28 day PCD Review"
    And select test as "Threshold Test"
    And I complete the Threshold Test pre-charge analysis details with:
      | field                       | value                              |
      | Suspect-victim relationship | Partner                            |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I preview pre charge analysis
    When I make charging decision as following:
      | decision type             | decision code                                              | offence category |
      | Further evidence required | H - Request further evidence to complete evidential report | Robbery          |

    And I add an action point plan for "7 days" and "Record of taped interview"
    And I submit the charging decision as following
      | Investigative stage        | Method | Create MG3 document |
      | Bail for charging decision | Area   | true                |

    Then review is submitted successfully


  @ui @CR_UI_16272 @CR_UI_S3 @earlyAdviceWithoutActionPlan
  Scenario: 28 days RASSO PCD review of test type Early Advice without action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "28 day PCD Review"
    And select test as "Early Advice"
    And I complete the Early Advice pre-charge analysis details with:
      | field                       | value                              |
      | Suspect-victim relationship | Partner                            |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I select PCD principal offence category as "Robbery"
    And I continue without action plan
    And I submit the charging decision as following
      | Investigative stage        | Create MG3 document |
      | Bail for charging decision | true                |

    Then review is submitted successfully
