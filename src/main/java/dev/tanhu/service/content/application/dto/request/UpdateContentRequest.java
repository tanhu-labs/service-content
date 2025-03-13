package dev.tanhu.service.content.application.dto.request;

import jakarta.validation.constraints.NotNull;

public class UpdateContentRequest {

    @NotNull private String username;
    @NotNull private String url;
    @NotNull private String text;

    public UpdateContentRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
