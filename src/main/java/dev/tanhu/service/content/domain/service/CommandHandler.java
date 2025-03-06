package dev.tanhu.service.content.domain.service;

import dev.tanhu.service.content.domain.command.CreateContentCommand;
import dev.tanhu.service.content.domain.command.DeleteContentCommand;
import dev.tanhu.service.content.domain.command.ReadContentCommand;
import dev.tanhu.service.content.domain.command.UpdateContentCommand;

public interface CommandHandler {

    CreateContentCommand saveContent(CreateContentCommand command);
    ReadContentCommand readContent(ReadContentCommand command);
    UpdateContentCommand updateContent(UpdateContentCommand command);
    DeleteContentCommand deleteContent(DeleteContentCommand command);
}
