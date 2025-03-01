package dev.tanhu.service.content.persistence.component;

import dev.tanhu.service.content.persistence.data.Content;

public interface PersistenceService {
    Content saveContent(Content content);
    Content readContent(Content content);
    Content updateContent(Content content);
    void deleteContent(Content content);
}
