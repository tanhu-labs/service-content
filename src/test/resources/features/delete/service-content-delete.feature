Feature: service content delete
  As a registered user, I want to delete a content from service

  Scenario: successfully delete content
    Given A content with "test_username" username and "testurltwo" url
    When A user send a request "/api/v1/contents" endpoint with "test_username" username and "testurltwo" url
    Then A message with 200 response status is given
    And The message must contain a deletion time

  Scenario: bad request for null url
    Given A content with "testurltwo" url and "test_username" username
    When A user send a request "/api/v1/contents/read" endpoint with "" url and "test_username" username
    Then The user should take a message with 400 response status

  Scenario: bad request for null username
    Given A content with "testurltwo" url and "test_username" username
    When A user send a request "/api/v1/contents/read" endpoint with "testurltwo" url and "" username
    Then The user should take a message with 400 response status