package dev.tanhu.service.content.persistence.component;

import dev.tanhu.service.content.persistence.data.Content;
import dev.tanhu.service.content.persistence.model.ContentEntity;
import dev.tanhu.service.content.persistence.repository.ContentRepository;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class ContentPersistenceService implements PersistenceService{


    private final ContentRepository contentRepository;

    public ContentPersistenceService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }


    @Override
    public Content saveContent(Content content) {
        ContentEntity entity = new ContentEntity();
        entity.setName(content.getName());
        entity.setUsername(content.getUsername());
        entity.setUrl(content.getUrl());
        entity.setText(content.getText());
        entity.setCreationTime(String.valueOf(content.getCreationTime()));


        ContentEntity result = contentRepository.save(entity);

        content.setId(result.getId());
        return content;
    }

    @Override
    public Content readContent(Content content) {
        Optional<ContentEntity> answer = contentRepository.findByUrl(content.getUrl());

        if (answer.isPresent()) {
            ContentEntity entity = answer.get();
            content.setId(entity.getId());
            content.setName(entity.getName());
            content.setUsername(entity.getUsername());
            content.setUrl(entity.getUrl());
            content.setText(entity.getText());
            content.setCreationTime(ZonedDateTime.parse(entity.getCreationTime()));

            return content;
        }

        throw new RuntimeException("unknown.url");
    }

    @Override
    public Content updateContent(Content content) {
        Optional<ContentEntity> answer = contentRepository.findByUrl(content.getUrl());

        if (answer.isPresent()) {
            ContentEntity entity = answer.get();
            entity.setText(content.getText());

            ContentEntity response = contentRepository.save(entity);
            content.setId(response.getId());
            content.setName(response.getName());
            content.setUsername(response.getUsername());
            content.setUrl(response.getUrl());
            content.setText(response.getText());
            content.setCreationTime(ZonedDateTime.parse(response.getCreationTime()));

            return content;
        }

        throw new RuntimeException("unknown.url");
    }

    @Override
    public void deleteContent(Content content) {
        Optional<ContentEntity> answer = contentRepository.findByUrl(content.getUrl());

        if (answer.isPresent()) {
            ContentEntity entity = answer.get();
            contentRepository.delete(entity);
        } else {
            throw new RuntimeException("unknown.url");
        }
    }


}
