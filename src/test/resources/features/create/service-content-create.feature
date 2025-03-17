Feature: content creation
  As a registered user, I want to create a text content

  Scenario: successfully content creation
    Given A user with "test_username" username that wants to create a "test_text" content
    When The user sends a request to "/api/v1/contents" endpoint
    Then The user should see a message with 201 response status
    And Message must include a url and a content creation time

  Scenario: bad request for null username
    Given A user with "null" username that wants to create a "test_text" content
    When The user sends a request to "/api/v1/contents" endpoint
    Then The user should see a message with 400 response status

  Scenario: bad request for null text
    Given A user with "test_username" username that wants to create a "" content
    When The user sends a request to "/api/v1/contents" endpoint
    Then The user should see a message with 400 response status