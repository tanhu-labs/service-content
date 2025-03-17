package dev.tanhu.service.content.acceptance.steps.update;

import dev.tanhu.service.content.application.dto.request.UpdateContentRequest;
import dev.tanhu.service.content.application.dto.response.UpdateContentResponse;
import dev.tanhu.service.content.persistence.model.ContentEntity;
import dev.tanhu.service.content.persistence.repository.ContentRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class UpdateContentCucumberStep {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final ParameterizedTypeReference<UpdateContentResponse> responseType =
            new ParameterizedTypeReference<UpdateContentResponse>() {};

    private ResponseEntity<UpdateContentResponse> response;

    private final String creationTime = String.valueOf(ZonedDateTime.now(ZoneOffset.UTC));
    private String url;

    @Given("A content with {string} url and {string} username and {string} text")
    public void contentWithUrlAndUsernameAndText(String url, String username, String text) {
        this.url = url;

        ContentEntity entity = new ContentEntity();
        entity.setUsername(username);
        entity.setName("domain");
        entity.setUrl(url);
        entity.setText(text);
        entity.setCreationTime(creationTime);

        contentRepository.save(entity);
    }

    @When("A user send a request {string} endpoint with {string} url and {string} username and {string} text")
    public void userSendRequestEndpointWithUrlAndUsernameAndText(String endpoint, String url, String username, String text) {
        if (url.equals("null") || url.isEmpty()) {
            url = null;
        }
        if (username.equals("null") || username.isEmpty()) {
            username = null;
        }
        if (text.equals("null") || text.isEmpty()) {
            text = null;
        }

        UpdateContentRequest request = new UpdateContentRequest();
        request.setUsername(username);
        request.setUrl(url);
        request.setText(text);

        HttpEntity<UpdateContentRequest> entity = new HttpEntity<>(request);
        response = testRestTemplate.exchange(endpoint, HttpMethod.PUT, entity, responseType);
    }

    @Then("A message with {int} response status must be taken")
    public void messageWithResponseStatus(int status) {
        assertEquals(HttpStatus.valueOf(status), response.getStatusCode());
    }

    @And("The message must contain a creation time and url")
    public void messageContainCreationTimeAndUrl() {
        assertNotNull(response.getBody());
        assertEquals(creationTime, response.getBody().getCreationTime());
        assertEquals(url, response.getBody().getUrl());
    }


}
