package dev.tanhu.service.content.acceptance.steps.create;

import dev.tanhu.service.content.application.dto.request.CreateContentRequest;
import dev.tanhu.service.content.application.dto.response.CreateContentResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateContentCucumberStep {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private CreateContentRequest request;
    private ResponseEntity<CreateContentResponse> response;

    private final ParameterizedTypeReference<CreateContentResponse> responseType
            = new ParameterizedTypeReference<CreateContentResponse>() {};

    @Given("A user with {string} username that wants to create a {string} content")
    public void requestWithUsernameAndText(String username, String text) {
        if (username.equals("null") || username.isEmpty()) {
            username = null;
        }
        if (text.equals("null") || text.isEmpty()) {
            text = null;
        }

        request = new CreateContentRequest();
        request.setUsername(username);
        request.setText(text);
    }

    @When("The user sends a request to {string} endpoint")
    public void sendRequestWithUrl(String url) {
        HttpEntity<CreateContentRequest> entity = new HttpEntity<>(request);
        response = testRestTemplate.exchange(url, HttpMethod.POST, entity, responseType);
    }

    @Then("The user should see a message with {int} response status")
    public void successfulContentCreateResponse(int status) {
        assertEquals(HttpStatus.valueOf(status), response.getStatusCode());
    }

    @And("Message must include a url and a content creation time")
    public void messageWithUrlAndCreationTime() {
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getUrl());
        assertNotNull(response.getBody().getCreationTime());
    }
}
