package dev.tanhu.service.content.application.read;

import dev.tanhu.service.content.application.dto.request.ReadContentRequest;
import dev.tanhu.service.content.application.dto.response.ReadContentResponse;
import dev.tanhu.service.content.domain.command.ReadContentCommand;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContentControllerReadOperationsTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockitoBean
    private CommandHandler commandHandler;

    private final String username = "test_read_username";
    private final String url = "test_read_url";
    private final String text = "test_read_text";

    private final ParameterizedTypeReference<ReadContentResponse> responseType =
            new ParameterizedTypeReference<ReadContentResponse>() {};

    @BeforeEach
    void beforeEach() {
        ReadContentCommand command = new ReadContentCommand();
        command.setUsername(username);
        command.setUrl(url);
        command.setText(text);

        when(commandHandler.readContent(any(ReadContentCommand.class))).thenReturn(command);
    }

    @Test
    void shouldReturnText_whenReadContentRequestReceivedWithUsernameAndUrl() {
        ReadContentRequest request = new ReadContentRequest();
        request.setUsername(username);
        request.setUrl(url);

        ResponseEntity<ReadContentResponse> response = testRestTemplate.exchange(
                "/api/v1/contents/read", HttpMethod.POST, new HttpEntity<>(request), responseType
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(text, response.getBody().getText());
    }

    @Test
    void shouldReturnBadRequest_whenReadContentRequestReceivedWithNullUsername() {
        ReadContentRequest request = new ReadContentRequest();
        request.setUsername(null);
        request.setUrl(url);

        ResponseEntity<ReadContentResponse> response = testRestTemplate.exchange(
                "/api/v1/contents/read", HttpMethod.POST, new HttpEntity<>(request), responseType
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequest_whenReadContentRequestReceivedWithNullUrl() {
        ReadContentRequest request = new ReadContentRequest();
        request.setUsername(username);
        request.setUrl(null);

        ResponseEntity<ReadContentResponse> response = testRestTemplate.exchange(
                "/api/v1/contents/read", HttpMethod.POST, new HttpEntity<>(request), responseType
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
