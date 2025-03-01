package dev.tanhu.service.content.persistence.component;

import dev.tanhu.service.content.persistence.data.Content;
import dev.tanhu.service.content.persistence.model.ContentEntity;
import dev.tanhu.service.content.persistence.repository.ContentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContentPersistenceServiceTest {

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private ContentRepository contentRepository;

    @Test
    void shouldSaveContent_whenReceivedContentWithUrl() {
        //given
        Content content = new Content();
        content.setName("test_name");
        content.setUsername("test_username");
        content.setUrl("test_url");
        content.setText("test_text");
        content.setCreationTime(ZonedDateTime.now(ZoneOffset.UTC));

        //when
        Content answer = persistenceService.saveContent(content);

        assertThat(answer).isNotNull();
        assertThat(answer.getId()).isNotNull();
        assertThat(answer.getName()).isEqualTo(content.getName());
        assertThat(answer.getUsername()).isEqualTo(content.getUsername());
        assertThat(answer.getUrl()).isEqualTo(content.getUrl());
        assertThat(answer.getText()).isEqualTo(content.getText());
        assertThat(answer.getCreationTime()).isEqualTo(content.getCreationTime());
    }

    @Test
    void shouldReadContent_whenReceivedUrl() {
        //before
        ContentEntity entity = new ContentEntity();
        entity.setName("test_read_name");
        entity.setUsername("test_read_username");
        entity.setUrl("test_read_url");
        entity.setText("test_read_text");
        entity.setCreationTime(String.valueOf(ZonedDateTime.now(ZoneOffset.UTC)));

        contentRepository.save(entity);

        //given
        Content content = new Content();
        content.setUrl("test_read_url");

        //when
        Content answer = persistenceService.readContent(content);

        //then
        assertThat(answer).isNotNull();
        assertThat(answer.getId()).isEqualTo(entity.getId());
        assertThat(answer.getName()).isEqualTo(entity.getName());
        assertThat(answer.getUsername()).isEqualTo(entity.getUsername());
        assertThat(answer.getUrl()).isEqualTo(entity.getUrl());
        assertThat(answer.getCreationTime()).isEqualTo(ZonedDateTime.parse(entity.getCreationTime()));
    }

    @Test
    void shouldThrowException_whenReceivedUnknownUrl() {
        //given
        Content content = new Content();
        content.setUrl("test_unknown_url");

        //when
        Throwable answer = assertThrows(Throwable.class, () -> persistenceService.readContent(content));

        //then
        assertThat(answer).isNotNull();
        assertThat(answer.getMessage()).isEqualTo("unknown.url");

    }

    @Test
    void shouldUpdateContent_whenReceivedContentWithUrl() {
        //before
        ContentEntity entity = new ContentEntity();
        entity.setName("test_update_name");
        entity.setUsername("test_update_username");
        entity.setUrl("test_update_url");
        entity.setText("test_update_before_text");
        entity.setCreationTime(String.valueOf(ZonedDateTime.now(ZoneOffset.UTC)));

        contentRepository.save(entity);

        //given
        Content content = new Content();
        content.setUrl("test_update_url");
        content.setText("test_update_after_text");

        //when
        Content answer = persistenceService.updateContent(content);

        //then
        assertThat(answer).isNotNull();
        assertThat(answer.getId()).isNotNull();
        assertThat(answer.getName()).isEqualTo(entity.getName());
        assertThat(answer.getUsername()).isEqualTo(entity.getUsername());
        assertThat(answer.getUrl()).isEqualTo(entity.getUrl());
        assertThat(answer.getText()).isEqualTo(content.getText());
        assertThat(answer.getCreationTime()).isEqualTo(entity.getCreationTime());
    }

    @Test
    void shouldDeleteContent_whenReceivedExistingUrl() {
        //before
        ContentEntity entity = new ContentEntity();
        entity.setName("test_delete_name");
        entity.setUsername("test_delete_username");
        entity.setUrl("test_delete_url");
        entity.setText("test_delete_text");
        entity.setCreationTime(String.valueOf(ZonedDateTime.now(ZoneOffset.UTC)));

        contentRepository.save(entity);

        //given
        Content content = new Content();
        content.setUrl("test_delete_url");

        //when
        persistenceService.deleteContent(content);

        //then
        Optional<ContentEntity> answer = contentRepository.findByUrl(content.getUrl());
        assertThat(answer).isEqualTo(Optional.empty());
    }
}