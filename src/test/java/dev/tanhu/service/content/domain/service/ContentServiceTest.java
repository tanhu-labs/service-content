package dev.tanhu.service.content.domain.service;

import dev.tanhu.service.content.domain.adapter.FakePersistenceService;
import dev.tanhu.service.content.domain.command.CreateContentCommand;
import dev.tanhu.service.content.domain.command.DeleteContentCommand;
import dev.tanhu.service.content.domain.command.ReadContentCommand;
import dev.tanhu.service.content.domain.command.UpdateContentCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContentServiceTest {

    private CommandHandler commandHandler;

    @BeforeEach
    void before() {
        commandHandler = new ContentService(new FakePersistenceService());
    }

    @Test
    void shouldCreateContent_whenCreateContentCommandReceivedWithUsernameAndText() {
        //given
        CreateContentCommand command = new CreateContentCommand();
        command.setUsername("test_username");
        command.setText("test text");

        //when
        CreateContentCommand answer = commandHandler.saveContent(command);

        //then
        assertThat(answer).isNotNull();
        assertThat(answer.getUsername()).isEqualTo(command.getUsername());
        assertThat(answer.getText()).isEqualTo(command.getText());
        assertThat(answer.getUrl()).isNotNull();
        assertThat(answer.getCreationTime()).isNotNull();
    }

    @Test
    void shouldThrowException_whenPersistenceErrorHappen() {
        //given
        CreateContentCommand command = new CreateContentCommand();
        command.setUsername("test_exception_username");
        command.setText("test_exception_text");

        //when
        Throwable throwable = assertThrows(Throwable.class, () -> commandHandler.saveContent(command));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("persistence.error");
    }

    @Test
    void shouldReadContent_whenReadContentCommandWithProperUsernameAndUrl() {

        //given
        ReadContentCommand command = new ReadContentCommand();
        command.setUsername("test_read_username");
        command.setUrl("test_read_url");

        //when
        ReadContentCommand answer = commandHandler.readContent(command);

        assertThat(answer).isNotNull();
        assertThat(answer.getText()).isEqualTo("test_read_text");
    }

    @Test
    void shouldThrowException_whenReceivedUnknownUrl() {
        //given
        ReadContentCommand command = new ReadContentCommand();
        command.setUsername("test_read_username");
        command.setUrl("test_read_unknown_url");

        //when
        Throwable throwable = assertThrows(Throwable.class, () -> commandHandler.readContent(command));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("unknown.url.error");

    }

    @Test
    void shouldThrowException_whenReceivedUnknownUsername() {
        //given
        ReadContentCommand command = new ReadContentCommand();
        command.setUsername("test_read_unknown_username");
        command.setUrl("test_read_url");

        //when
        Throwable throwable = assertThrows(Throwable.class, () -> commandHandler.readContent(command));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("unauthorized.request");
    }

    @Test
    void shouldUpdateContent_whenUpdateContentCommandWithProperUsernameAndUrl() {
        //given
        UpdateContentCommand command = new UpdateContentCommand();
        command.setUsername("test_update_username");
        command.setUrl("test_update_url");
        command.setText("test_update_text");

        //when
        UpdateContentCommand answer = commandHandler.updateContent(command);

        //then
        assertThat(answer).isNotNull();
        assertThat(answer.getUsername()).isEqualTo(command.getUsername());
        assertThat(answer.getUrl()).isEqualTo(command.getUrl());
        assertThat(answer.getText()).isEqualTo(command.getText());
        assertThat(answer.getCreationTime()).isNotNull();
    }

    @Test
    void shouldThrowException_whenUpdateContentCommandWithUnknownUrl() {
        //given
        UpdateContentCommand command = new UpdateContentCommand();
        command.setUsername("test_update_username");
        command.setUrl("test_update_unknown_url");
        command.setText("test_update_text");

        //when
        Throwable throwable = assertThrows(Throwable.class, () -> commandHandler.updateContent(command));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("update.unknown.url.error");
    }

    @Test
    void shouldThrowException_whenUpdateContentCommandWithUnknownUsername() {
        //before
        UpdateContentCommand command = new UpdateContentCommand();
        command.setUrl("test_update_url");
        command.setUsername("test_update_unknown_url");

        //when
        Throwable throwable = assertThrows(Throwable.class, () -> commandHandler.updateContent(command));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("update.authorization.error");
    }

    @Test
    void shouldDeleteContent_whenDeleteContentCommandWithProperUsernameAndUrl() {
        //given
        DeleteContentCommand command = new DeleteContentCommand();
        command.setUsername("test_delete_username");
        command.setUrl("test_delete_url");

        //when
        DeleteContentCommand answer = commandHandler.deleteContent(command);

        //then
        assertThat(answer).isNotNull();
        assertThat(answer.getDeletionTime()).isNotNull();

    }

    @Test
    void shouldThrowException_whenDeleteContentCommandWithUnknownUrl() {
        //given
        DeleteContentCommand command = new DeleteContentCommand();
        command.setUsername("test_delete_username");
        command.setUrl("test_delete_unknown_url");

        //when
        Throwable throwable = assertThrows(Throwable.class, () -> commandHandler.deleteContent(command));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("delete.unknown.url.error");

    }

    @Test
    void shouldThrowException_whenDeleteContentCommandWithUnknownUsername() {
        //given
        DeleteContentCommand command = new DeleteContentCommand();
        command.setUsername("test_delete_unknown_username");
        command.setUrl("test_delete_url");

        //then
        Throwable throwable = assertThrows(Throwable.class, () -> commandHandler.deleteContent(command));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("delete.authorization.error");
    }
}