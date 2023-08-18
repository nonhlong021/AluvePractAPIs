@run
Feature: Creating and Validating Bookings

  Scenario Outline: User creates a booking
    When the user makes a booking with "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
    Then the booking is created successfully
    Examples:
      | firstname    | lastname   | totalprice | depositpaid | checkin      | checkout     | additionalneeds |
      | Jim          | Brown      | 111        | true        | 2023-08-17  | 2023-08-18   | Breakfast      |

  Scenario: User validates the correctness of booking XML details
    When the user gets the booking using the id
    Then the details are correct

