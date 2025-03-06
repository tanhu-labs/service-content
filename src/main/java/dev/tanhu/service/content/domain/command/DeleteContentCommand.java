package dev.tanhu.service.content.domain.command;

import java.time.ZonedDateTime;

public class DeleteContentCommand implements ContentCommand{

    private String username;
    private String url;
    private ZonedDateTime deletionTime;

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

    public ZonedDateTime getDeletionTime() {
        return deletionTime;
    }

    public void setDeletionTime(ZonedDateTime deletionTime) {
        this.deletionTime = deletionTime;
    }
}
