Feature: service content read
  As a registered user, I want to read a content from service

  Scenario: successfully read content
    Given A content with "testurlone" url and "test_username" username
    When A user send a request "/api/v1/contents/read" endpoint with "testurlone" url and "test_username" username
    Then The user should take a message with 200 response status
    And The message must contain a text

  Scenario: bad request for null url
    Given A content with "testurlone" url and "test_username" username
    When A user send a request "/api/v1/contents/read" endpoint with "" url and "test_username" username
    Then The user should take a message with 400 response status

  Scenario: bad request for null username
    Given A content with "testurlone" url and "test_username" username
    When A user send a request "/api/v1/contents/read" endpoint with "testurlone" url and "" username
    Then The user should take a message with 400 response status