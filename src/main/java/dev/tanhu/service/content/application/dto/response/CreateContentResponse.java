package dev.tanhu.service.content.application.dto.response;

public class CreateContentResponse {

    private String url;
    private String creationTime;

    public CreateContentResponse() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
