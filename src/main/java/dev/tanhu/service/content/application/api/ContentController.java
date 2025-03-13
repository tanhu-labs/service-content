package dev.tanhu.service.content.application.api;

import dev.tanhu.service.content.application.dto.request.CreateContentRequest;
import dev.tanhu.service.content.application.dto.request.DeleteContentRequest;
import dev.tanhu.service.content.application.dto.request.ReadContentRequest;
import dev.tanhu.service.content.application.dto.request.UpdateContentRequest;
import dev.tanhu.service.content.application.dto.response.CreateContentResponse;
import dev.tanhu.service.content.application.dto.response.DeleteContentResponse;
import dev.tanhu.service.content.application.dto.response.ReadContentResponse;
import dev.tanhu.service.content.application.dto.response.UpdateContentResponse;
import dev.tanhu.service.content.domain.command.CreateContentCommand;
import dev.tanhu.service.content.domain.command.DeleteContentCommand;
import dev.tanhu.service.content.domain.command.ReadContentCommand;
import dev.tanhu.service.content.domain.command.UpdateContentCommand;
import dev.tanhu.service.content.domain.service.CommandHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contents")
public class ContentController {

    private final CommandHandler commandHandler;

    public ContentController(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping
    public ResponseEntity<CreateContentResponse> createContent(@Valid @RequestBody CreateContentRequest request) {

        CreateContentCommand command = new CreateContentCommand();
        command.setUsername(request.getUsername());
        command.setText(request.getText());

        CreateContentCommand answer = commandHandler.saveContent(command);

        CreateContentResponse response = new CreateContentResponse();
        response.setUrl(answer.getUrl());
        response.setCreationTime(String.valueOf(answer.getCreationTime()));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/read")
    public ResponseEntity<ReadContentResponse> readContent(@Valid @RequestBody ReadContentRequest request) {

        ReadContentCommand command = new ReadContentCommand();
        command.setUsername(request.getUsername());
        command.setUrl(request.getUrl());

        ReadContentCommand answer = commandHandler.readContent(command);

        ReadContentResponse response = new ReadContentResponse();
        response.setText(answer.getText());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<UpdateContentResponse> updateContent(@Valid @RequestBody UpdateContentRequest request) {

        UpdateContentCommand command = new UpdateContentCommand();
        command.setUsername(request.getUsername());
        command.setUrl(request.getUrl());
        command.setText(request.getText());

        UpdateContentCommand answer = commandHandler.updateContent(command);

        UpdateContentResponse response = new UpdateContentResponse();
        response.setUrl(answer.getUrl());
        response.setCreationTime(String.valueOf(answer.getCreationTime()));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<DeleteContentResponse> deleteContent(@Valid @RequestBody DeleteContentRequest request) {

        DeleteContentCommand command = new DeleteContentCommand();
        command.setUsername(request.getUsername());
        command.setUrl(request.getUrl());

        DeleteContentCommand answer = commandHandler.deleteContent(command);

        DeleteContentResponse response = new DeleteContentResponse();
        response.setDeletionTime(String.valueOf(answer.getDeletionTime()));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
