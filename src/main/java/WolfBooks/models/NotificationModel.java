package src.main.java.WolfBooks.models;

public class NotificationModel {

    private String notificationId;
    private String content;
    private String userId;
    private boolean isRead;

    // Constructor
    public NotificationModel(String notificationId, String content, String userId, boolean isRead) {
        this.notificationId = notificationId;
        this.content = content;
        this.userId = userId;
        this.isRead = isRead;
    }

    // Getters and Setters
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
