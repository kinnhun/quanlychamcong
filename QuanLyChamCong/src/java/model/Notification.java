package model;

import java.sql.Timestamp;

public class Notification {

    private int notificationId;
    private String title;
    private String content;
    private String imageUrl;
    private String fileUrl;
    private int createdBy;
    private Timestamp createdAt;
    private Timestamp scheduledTime;
    private String status;

    public Notification() {
    }

    public Notification(int notificationId, String title, String content, String imageUrl, String fileUrl, int createdBy, Timestamp createdAt, Timestamp scheduledTime, String status) {
        this.notificationId = notificationId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.fileUrl = fileUrl;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.scheduledTime = scheduledTime;
        this.status = status;
    }

    // Getters and setters...
    public int getNotificationId() {
        return notificationId;
    }
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getFileUrl() {
        return fileUrl;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public int getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public Timestamp getScheduledTime() {
        return scheduledTime;
    }
    public void setScheduledTime(Timestamp scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                ", scheduledTime=" + scheduledTime +
                ", status='" + status + '\'' +
                '}';
    }
}
