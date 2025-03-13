package dev.tanhu.service.content.application.dto.response;

public class DeleteContentResponse {

    private String deletionTime;

    public DeleteContentResponse() {
    }

    public String getDeletionTime() {
        return deletionTime;
    }

    public void setDeletionTime(String deletionTime) {
        this.deletionTime = deletionTime;
    }
}
