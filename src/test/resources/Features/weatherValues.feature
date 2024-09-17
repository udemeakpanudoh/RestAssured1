@apiWeatherTest
Feature: Weather Values Search

  Scenario: Retrieve weather values of a specific timelines
    Given I send a request to search tomorrow's weather
#    When I search for the university "University of Witwatersrand"
#    Then I should get the "state-province" value