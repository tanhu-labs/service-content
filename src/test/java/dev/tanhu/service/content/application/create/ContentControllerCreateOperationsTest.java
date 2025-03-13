package dev.tanhu.service.content.application.create;

import dev.tanhu.service.content.application.dto.request.CreateContentRequest;
import dev.tanhu.service.content.application.dto.response.CreateContentResponse;
import dev.tanhu.service.content.domain.command.CreateContentCommand;
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
class ContentControllerCreateOperationsTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockitoBean
    private CommandHandler commandHandler;

    private final String username = "test_create_username";
    private final String text = "test_create_text";
    private final String url = "test_create_url";
    private final ZonedDateTime creationTime = ZonedDateTime.now(ZoneOffset.UTC);

    private final ParameterizedTypeReference<CreateContentResponse> responseType =
            new ParameterizedTypeReference<CreateContentResponse>() {};

    @BeforeEach
    void beforeEach() {
        CreateContentCommand command = new CreateContentCommand();
        command.setUsername(username);
        command.setText(text);
        command.setUrl(url);
        command.setCreationTime(creationTime);

        when(commandHandler.saveContent(any(CreateContentCommand.class))).thenReturn(command);
    }

    @Test
    void shouldReturnUrlAndCreationTime_whenCreateContentRequestReceivedWithUsernameAndText() {

        CreateContentRequest request = new CreateContentRequest();
        request.setUsername(username);
        request.setText(text);

        ResponseEntity<CreateContentResponse> response = testRestTemplate.exchange(
                "/api/v1/contents", HttpMethod.POST, new HttpEntity<>(request), responseType
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(url, response.getBody().getUrl());
        assertEquals(String.valueOf(creationTime), response.getBody().getCreationTime());
    }

    @Test
    void shouldReturnBadResponse_whenCreateContentRequestWithNullUsername() {

        CreateContentRequest request = new CreateContentRequest();
        request.setUsername(null);
        request.setText(text);

        ResponseEntity<CreateContentResponse> response = testRestTemplate.exchange(
                "/api/v1/contents", HttpMethod.POST, new HttpEntity<>(request), responseType
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnBadResponse_whenCreateContentRequestWithNullText() {

        CreateContentRequest request = new CreateContentRequest();
        request.setUsername(username);
        request.setText(null);

        ResponseEntity<CreateContentResponse> response = testRestTemplate.exchange(
                "/api/v1/contents", HttpMethod.POST, new HttpEntity<>(request), responseType
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
