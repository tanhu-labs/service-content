package dev.tanhu.service.content.application.dto.request;

import jakarta.validation.constraints.NotNull;

public class DeleteContentRequest {

    @NotNull private String username;
    @NotNull private String url;

    public DeleteContentRequest() {
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
}
