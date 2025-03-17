package dev.tanhu.service.content.acceptance.steps.read;

import dev.tanhu.service.content.application.dto.request.ReadContentRequest;
import dev.tanhu.service.content.application.dto.response.ReadContentResponse;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReadContentCucumberStep {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final ParameterizedTypeReference<ReadContentResponse> responseType =
            new ParameterizedTypeReference<ReadContentResponse>() {};

    private ResponseEntity<ReadContentResponse> response;

    private final String text = "test_text";
    private final String creationTime = String.valueOf(ZonedDateTime.now(ZoneOffset.UTC));

    @Given("A content with {string} url and {string} username")
    public void contentWithUrlAndUsername(String url, String username) {
        ContentEntity entity = new ContentEntity();
        entity.setUsername(username);
        entity.setName("domain");
        entity.setUrl(url);
        entity.setText(text);
        entity.setCreationTime(creationTime);

        contentRepository.save(entity);
    }

    @When("A user send a request {string} endpoint with {string} url and {string} username")
    public void userSendRequestEndpointWithUrlAndUsername(String endpoint, String url, String username) {
        if (url.equals("null") || url.isEmpty()) {
            url = null;
        }
        if (username.equals("null") || username.isEmpty()) {
            username = null;
        }
        ReadContentRequest request = new ReadContentRequest();
        request.setUsername(username);
        request.setUrl(url);

        HttpEntity<ReadContentRequest> entity = new HttpEntity<>(request);
        response = testRestTemplate.exchange(endpoint, HttpMethod.POST, entity, responseType);

    }

    @Then("The user should take a message with {int} response status")
    public void userTakeMessageWithResponseStatus(int status) {
        assertEquals(HttpStatus.valueOf(status), response.getStatusCode());
    }

    @And("The message must contain a text")
    public void theMessageContainTextAndCreationTime() {
        assertNotNull(response.getBody());
        assertEquals(text, response.getBody().getText());
    }

}
