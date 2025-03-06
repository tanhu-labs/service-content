package dev.tanhu.service.content.domain.service;

import dev.tanhu.service.content.domain.command.CreateContentCommand;
import dev.tanhu.service.content.domain.command.DeleteContentCommand;
import dev.tanhu.service.content.domain.command.ReadContentCommand;
import dev.tanhu.service.content.domain.command.UpdateContentCommand;
import dev.tanhu.service.content.persistence.component.PersistenceService;
import dev.tanhu.service.content.persistence.data.Content;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

@Service
public class ContentService implements CommandHandler{

    private final PersistenceService persistenceService;

    public ContentService(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @Override
    public CreateContentCommand saveContent(CreateContentCommand command) {
        String url = UrlGenerator.generateUrl();
        ZonedDateTime creationTime = ZonedDateTime.now(ZoneOffset.UTC);

        Content content = new Content();
        content.setName("domain");
        content.setUsername(command.getUsername());
        content.setText(command.getText());
        content.setUrl(url);
        content.setCreationTime(creationTime);

        Content answer = persistenceService.saveContent(content);

        if (Objects.isNull(answer.getId())) {
            throw new RuntimeException("persistence.error");
        }

        command.setUrl(url);
        command.setCreationTime(creationTime);

        return command;
    }

    @Override
    public ReadContentCommand readContent(ReadContentCommand command) {
        Content content = new Content();
        content.setUrl(command.getUrl());

        Content answer = persistenceService.readContent(content);

        if (Objects.isNull(answer)) {
            throw new RuntimeException("unknown.url.error");
        } else if (!command.getUsername().equals(answer.getUsername())) {
            throw new RuntimeException("unauthorized.request");
        } else {
            command.setText(answer.getText());
            return command;
        }
    }

    @Override
    public UpdateContentCommand updateContent(UpdateContentCommand command) {
        Content content = new Content();
        content.setUrl(command.getUrl());

        Content read = persistenceService.readContent(content);

        if (Objects.isNull(read)) {
            throw new RuntimeException("update.unknown.url.error");
        }

        if (read.getUsername().equals(command.getUsername())) {
            read.setText(command.getText());
            read.setCreationTime(ZonedDateTime.now(ZoneOffset.UTC));

            Content answer = persistenceService.updateContent(read);
            command.setCreationTime(answer.getCreationTime());
        } else {
            throw new RuntimeException("update.authorization.error");
        }

        return command;
    }

    @Override
    public DeleteContentCommand deleteContent(DeleteContentCommand command) {
        Content content = new Content();
        content.setUrl(command.getUrl());

        Content answer = persistenceService.readContent(content);

        if (Objects.isNull(answer)) {
            throw new RuntimeException("delete.unknown.url.error");
        }

        if (answer.getUsername().equals(command.getUsername())) {
            persistenceService.deleteContent(answer);
            command.setDeletionTime(ZonedDateTime.now(ZoneOffset.UTC));
        } else {
            throw new RuntimeException("delete.authorization.error");
        }

        return command;
    }

}
