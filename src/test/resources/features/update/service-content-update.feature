Feature: service content update
  As a registered user, I want to update a content from service

  Scenario: successfully update content
    Given A content with "testurlupd" url and "test_username" username and "test_text" text
    When A user send a request "/api/v1/contents" endpoint with "testurlupd" url and "test_username" username and "test_update" text
    Then A message with 200 response status must be taken
    And The message must contain a creation time and url


  Scenario: bad request for null url
    Given A content with "testurlupd" url and "test_username" username and "test_text" text
    When A user send a request "/api/v1/contents" endpoint with "" url and "test_username" username and "test_update" text
    Then A message with 400 response status must be taken

  Scenario: bad request for null username
    Given A content with "testurlupd" url and "test_username" username and "test_text" text
    When A user send a request "/api/v1/contents" endpoint with "testurlupd" url and "" username and "test_update" text
    Then A message with 400 response status must be taken

  Scenario: bad request for null text
    Given A content with "testurlupd" url and "test_username" username and "test_text" text
    When A user send a request "/api/v1/contents" endpoint with "testurlupd" url and "test_username" username and "" text
    Then A message with 400 response status must be taken