package dev.tanhu.service.content.acceptance.steps.delete;

import dev.tanhu.service.content.application.dto.request.DeleteContentRequest;
import dev.tanhu.service.content.application.dto.response.DeleteContentResponse;
import dev.tanhu.service.content.persistence.model.ContentEntity;
import dev.tanhu.service.content.persistence.repository.ContentRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

public class DeleteContentCucumberStep {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final ParameterizedTypeReference<DeleteContentResponse> responseType =
            new ParameterizedTypeReference<DeleteContentResponse>() {};

    private ResponseEntity<DeleteContentResponse> response;

    @Given("A content with {string} username and {string} url")
    public void contentWithUsernameAndUrl(String username, String url) {
        ContentEntity entity = new ContentEntity();
        entity.setUsername(username);
        entity.setName("domain");
        entity.setUrl(url);
        entity.setText("test_text");
        entity.setCreationTime(String.valueOf(ZonedDateTime.now(ZoneOffset.UTC)));

        contentRepository.save(entity);
    }

    @When("A user send a request {string} endpoint with {string} username and {string} url")
    public void userSendRequestEndpointWithUsernameAndUrl(String endpoint, String username, String url) {
        if (username.equals("null") || username.isEmpty()) {
            username = null;
        }
        if (url.equals("null") || url.isEmpty()) {
            url = null;
        }

        DeleteContentRequest request = new DeleteContentRequest();
        request.setUsername(username);
        request.setUrl(url);

        HttpEntity<DeleteContentRequest> entity = new HttpEntity<>(request);
        response = testRestTemplate.exchange(endpoint, HttpMethod.DELETE, entity, responseType);
    }

    @Then("A message with {int} response status is given")
    public void messageWithResponseStatusIsGiven(int status) {
        assertEquals(HttpStatus.valueOf(status), response.getStatusCode());
    }

    @And("The message must contain a deletion time")
    public void messageContainDeletionTime() {
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getDeletionTime());
    }
}
