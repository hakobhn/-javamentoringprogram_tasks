Feature: Generate emails from template

  Scenario: WITH ALL REQUIRED FIELDS IS SUCCESSFUL

    Given user wants to generate an email content with replacing the following placeholders
      | firstName | lastName | label    | url                                             | date       |
      | Rachel    | Green    | Click    | https://www.baeldung.com/cs/bdd-guide           | 2018-01-01 |
      | Brittany  | Adkins   | Call Me  | https://call.api/2141112222                     | 2009-03-13 |
      | David     | Biggs    | Email Me | https://gmail.com/send/dummy@gmail.com          | 2020-05-25 |
      | Carolina  | Alan     | Ping Me  | https://www.facebook.com/send-push/123fd4ab0jlo | 2021-11-07 |

    When user generates the new emails 'WITH ALL REQUIRED FIELDS'
    Then the generation 'IS SUCCESSFUL'