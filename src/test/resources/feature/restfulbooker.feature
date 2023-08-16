@run
Feature: Creating and Validating Bookings

  Scenario Outline: User creates a booking using XML
    Given the user has a valid booking with name "<firstname>" lastname "<lastname>" price "<totalprice>" deposit "<depositpaid>"
    And check-in "<checkin>" and check-out "<checkout>" and addons "<additionalneeds>"
    When the user submits the booking XML
    Then the booking is created successfully
    Examples:
      | firstname    | lastname   | totalprice | depositpaid | checkin      | checkout     | additionalneeds |
      | John         | Doe        | 200        | true        | 2023-08-20   | 2023-08-25   | none            |

  Scenario: User validates the correctness of booking XML details
    Given a booking with the following details exists:
      | firstname    | lastname   | totalprice | depositpaid | checkin      | checkout     | additionalneeds |
      | Jane         | Smith      | 150        | false       | 2023-09-10   | 2023-09-15   | breakfast       |
    When the user validates the booking XML details for the booking
    Then the XML details are correct

