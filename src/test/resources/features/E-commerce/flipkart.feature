@All
Feature: Demo testing on E-commerce Site: Flipkart

  Background:
    Given User successfully logs into the application as an "Valid User"

  @Test @Demo
  Scenario: Verify successful login with valid credentials
    Given I navigate to the Flipkart login page