#Author: vkundanapally26@massmutual.com
@TryIt
Feature: Run through Jenkins
  Jenkins Docker Run

  @LetC
  Scenario: Get Title of Google Page
    Given Goto the google page
    And Get the title of the page
    Then Print in console