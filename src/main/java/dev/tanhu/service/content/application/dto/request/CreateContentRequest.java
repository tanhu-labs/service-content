package dev.tanhu.service.content.application.dto.request;

import jakarta.validation.constraints.NotNull;

public class CreateContentRequest {

    @NotNull private String username;
    @NotNull private String text;

    public CreateContentRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
