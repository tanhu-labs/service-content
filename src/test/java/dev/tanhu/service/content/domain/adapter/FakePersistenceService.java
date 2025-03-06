package dev.tanhu.service.content.domain.adapter;

import dev.tanhu.service.content.persistence.component.PersistenceService;
import dev.tanhu.service.content.persistence.data.Content;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

public class FakePersistenceService implements PersistenceService {
    @Override
    public Content saveContent(Content content) {
        if (content.getUsername().equals("test_username")) {
            content.setId(UUID.randomUUID());
        }
        return content;
    }

    @Override
    public Content readContent(Content content) {
        switch (content.getUrl()) {
            case "test_read_url" -> {
                content.setId(UUID.randomUUID());
                content.setName("domain");
                content.setUsername("test_read_username");
                content.setText("test_read_text");
                content.setCreationTime(ZonedDateTime.now(ZoneOffset.UTC));

                return content;
            }
            case "test_update_url" -> {
                content.setId(UUID.randomUUID());
                content.setName("domain");
                content.setUsername("test_update_username");
                content.setText("test_update_text");
                content.setCreationTime(ZonedDateTime.now(ZoneOffset.UTC));

                return content;
            }
            case "test_delete_url" -> {
                content.setId(UUID.randomUUID());
                content.setName("domain");
                content.setUsername("test_delete_username");
                content.setText("test_delete_text");
                content.setCreationTime(ZonedDateTime.now(ZoneOffset.UTC));

                return content;
            }
            case "test_read_unknown_url",
                 "test_update_unknown_url",
                 "test_delete_unknown_url" -> {
                return null;
            }
        }

        throw new RuntimeException("unknown test case");
    }

    @Override
    public Content updateContent(Content content) {

        return content;
    }

    @Override
    public void deleteContent(Content content) {

    }
}
