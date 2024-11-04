package src.main.java.WolfBooks.models;

public class EnrollmentModel {

    private String userId;
    private String courseId;
    private String userStatus;

    // Constructor
    public EnrollmentModel(String userId, String courseId, String userStatus) {
        this.userId = userId;
        this.courseId = courseId;
        this.userStatus = userStatus;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
