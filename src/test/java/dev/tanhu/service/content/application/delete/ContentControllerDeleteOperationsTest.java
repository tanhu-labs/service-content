package dev.tanhu.service.content.application.delete;

import dev.tanhu.service.content.application.dto.request.DeleteContentRequest;
import dev.tanhu.service.content.application.dto.response.DeleteContentResponse;
import dev.tanhu.service.content.domain.command.DeleteContentCommand;
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
class ContentControllerDeleteOperationsTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockitoBean
    private CommandHandler commandHandler;

    private final String username = "test_delete_username";
    private final String url = "test_delete_url";
    private final ZonedDateTime deletionTime = ZonedDateTime.now(ZoneOffset.UTC);

    private final ParameterizedTypeReference<DeleteContentResponse> responseType =
            new ParameterizedTypeReference<DeleteContentResponse>() {};

    @BeforeEach
    void beforeEach() {
        DeleteContentCommand command = new DeleteContentCommand();
        command.setUsername(username);
        command.setUrl(url);
        command.setDeletionTime(deletionTime);

        when(commandHandler.deleteContent(any(DeleteContentCommand.class))).thenReturn(command);
    }

    @Test
    void shouldReturnDeletionTime_whenDeleteContentRequestReceivedWithUsernameAndUrl() {
        DeleteContentRequest request = new DeleteContentRequest();
        request.setUsername(username);
        request.setUrl(url);

        ResponseEntity<DeleteContentResponse> response = testRestTemplate.exchange(
                "/api/v1/contents", HttpMethod.DELETE, new HttpEntity<>(request), responseType
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(String.valueOf(deletionTime), response.getBody().getDeletionTime());
    }

    @Test
    void shouldReturnBadRequest_whenDeleteContentRequestReceivedWithNullUsername() {
        DeleteContentRequest request = new DeleteContentRequest();
        request.setUsername(null);
        request.setUrl(url);

        ResponseEntity<DeleteContentResponse> response = testRestTemplate.exchange(
                "/api/v1/contents", HttpMethod.DELETE, new HttpEntity<>(request), responseType
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequest_whenDeleteContentRequestReceivedWithNullUrl() {
        DeleteContentRequest request = new DeleteContentRequest();
        request.setUsername(username);
        request.setUrl(null);

        ResponseEntity<DeleteContentResponse> response = testRestTemplate.exchange(
                "/api/v1/contents", HttpMethod.DELETE, new HttpEntity<>(request), responseType
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
