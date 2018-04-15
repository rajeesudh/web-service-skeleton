Feature: Customer

  Scenario Outline: Get customer successfully
    Given let variable "accountNumber" equal to "<accountNumber>"
    When the client performs GET request on "customers/{(accountNumber)}"
    Then status code is 200
    And response contains property "accountNumber" with value "<accountNumber>"
    And response contains property "firstName" with value "<firstName>"
    And response contains property "lastName" with value "<lastName>"
    And response contains an array "addresses" of size <numberOfAddresses>

    Examples:
      | accountNumber | firstName | lastName | numberOfAddresses |
      | 1111111111    | Joe       | Bloggs   | 1                 |

  Scenario Outline: Get customer failure
    Given let variable "accountNumber" equal to "<accountNumber>"
    When the client performs GET request on "customers/{(accountNumber)}"
    Then status code is <statusCode>
    And response contains property "message" with value "<message>"

    Examples:
      | accountNumber | statusCode | message                                                  |
      | 9999999999    | 404        | customer with account number {(accountNumber)} not found |
      | 999999999     | 400        | getCustomer.accountNumber: must match "^\d{10}$"         |
      | 99999999999   | 400        | getCustomer.accountNumber: must match "^\d{10}$"         |