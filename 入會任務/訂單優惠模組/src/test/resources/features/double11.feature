@double_11_discount
Feature: Double 11 promotional offers
  As a shopper
  I want the system to calculate my order total with applicable promotions
  So that I can understand how much to pay and what items I will receive

  Scenario: For every 10 items of the same product purchased, the total price of the 10 items will be discounted by 20%
    Given the threshold discount promotion is configured:
      | threshold               | discount |
      | same product >= 10      | 20%      |
    When a customer places an order with:
      | productName | quantity | unitPrice |
      | 褲子         | 12       | 100       |
    Then the order summary should be:
      | originalAmount | discount | totalAmount |
      | 1200           | 200      | 1000        |
    And the customer should receive:
      | productName | quantity |
      | 褲子         | 12       |

  Scenario: For every 10 items of the same product purchased, the total price of the 10 items will be discounted by 20%
    Given the threshold discount promotion is configured:
      | threshold               | discount |
      | same product >= 10      | 20%      |
    When a customer places an order with:
      | productName | quantity | unitPrice |
      | 褲子         | 27       | 100       |
    Then the order summary should be:
      | originalAmount | discount | totalAmount |
      | 2700           | 400      | 2300        |
    And the customer should receive:
      | productName | quantity |
      | 褲子         | 27       |

  Scenario: For every 10 items of the same product purchased, the total price of the 10 items will be discounted by 20%
    Given the threshold discount promotion is configured:
      | threshold               | discount |
      | same product >= 10      | 20%      |
    When a customer places an order with:
      | productName | quantity | unitPrice |
      | A           | 1        | 100       |
      | B           | 1        | 100       |
      | C           | 1        | 100       |
      | D           | 1        | 100       |
      | F           | 1        | 100       |
      | G           | 1        | 100       |
      | H           | 1        | 100       |
      | I           | 1        | 100       |
      | J           | 1        | 100       |
      | K           | 1        | 100       |
    Then the order summary should be:
      | originalAmount | discount | totalAmount |
      | 1000           | 0        | 1000        |
    And the customer should receive:
      | productName | quantity |
      | A           | 1        |
      | B           | 1        |
      | C           | 1        |
      | D           | 1        |
      | F           | 1        |
      | G           | 1        |
      | H           | 1        |
      | I           | 1        |
      | J           | 1        |
      | K           | 1        |