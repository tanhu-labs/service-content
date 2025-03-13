package dev.tanhu.service.content.application.update;

import dev.tanhu.service.content.application.dto.request.UpdateContentRequest;
import dev.tanhu.service.content.application.dto.response.UpdateContentResponse;
import dev.tanhu.service.content.domain.command.UpdateContentCommand;
import dev.tanhu.service.content.domain.service.CommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContentControllerUpdateOperationsTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockitoBean
    private CommandHandler commandHandler;

    private final String username = "test_update_username";
    private final String url = "test_update_url";
    private final String text = "test_update_text";
    private final ZonedDateTime creationTime = ZonedDateTime.now(ZoneOffset.UTC);

    private final ParameterizedTypeReference<UpdateContentResponse> responseType =
            new ParameterizedTypeReference<UpdateContentResponse>() {};

    @BeforeEach
    void beforeEach() {
        UpdateContentCommand command = new UpdateContentCommand();
        command.setUsername(username);
        command.setUrl(url);
        command.setText(text);
        command.setCreationTime(creationTime);

        when(commandHandler.updateContent(any(UpdateContentCommand.class))).thenReturn(command);
    }

    @Test
    void shouldReturnUrlAndCreationTime_whenUpdateContentRequestReceivedWithUsernameAndUrlAndText() {
        UpdateContentRequest request = new UpdateContentRequest();
        request.setUsername(username);
        request.setUrl(url);
        request.setText(text);

        ResponseEntity<UpdateContentResponse> response = testRestTemplate.exchange(
                "/api/v1/contents", HttpMethod.PUT, new HttpEntity<>(request), responseType
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(url, response.getBody().getUrl());
        assertEquals(String.valueOf(creationTime), response.getBody().getCreationTime());
    }

    @Test
    void shouldReturnBadRequest_whenUpdateContentRequestReceivedWithNullUsername() {
        UpdateContentRequest request = new UpdateContentRequest();
        request.setUsername(null);
        request.setUrl(url);
        request.setText(text);

        ResponseEntity<UpdateContentResponse> response = testRestTemplate.exchange(
                "/api/v1/contents", HttpMethod.PUT, new HttpEntity<>(request), responseType
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequest_whenUpdateContentRequestReceivedWithNullUrl() {
        UpdateContentRequest request = new UpdateContentRequest();
        request.setUsername(username);
        request.setUrl(null);
        request.setText(text);

        ResponseEntity<UpdateContentResponse> response = testRestTemplate.exchange(
                "/api/v1/contents", HttpMethod.PUT, new HttpEntity<>(request), responseType
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequest_whenUpdateContentRequestReceivedWithNullText() {
        UpdateContentRequest request = new UpdateContentRequest();
        request.setUsername(username);
        request.setUrl(url);
        request.setText(null);

        ResponseEntity<UpdateContentResponse> response = testRestTemplate.exchange(
                "/api/v1/contents", HttpMethod.PUT, new HttpEntity<>(request), responseType
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
