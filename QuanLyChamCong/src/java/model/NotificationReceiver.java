package model;

import java.sql.Timestamp;

public class NotificationReceiver {

    private int id;
    private int notificationId;
    private int userId;
    private String status;
    private Timestamp readAt;

    public NotificationReceiver() {
    }

    public NotificationReceiver(int id, int notificationId, int userId, String status, Timestamp readAt) {
        this.id = id;
        this.notificationId = notificationId;
        this.userId = userId;
        this.status = status;
        this.readAt = readAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getReadAt() {
        return readAt;
    }

    public void setReadAt(Timestamp readAt) {
        this.readAt = readAt;
    }

    @Override
    public String toString() {
        return "NotificationReceiver{" +
                "id=" + id +
                ", notificationId=" + notificationId +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                ", readAt=" + readAt +
                '}';
    }
}
