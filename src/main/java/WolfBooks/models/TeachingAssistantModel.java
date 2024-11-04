package src.main.java.WolfBooks.models;

public class TeachingAssistantModel {

    private String userId;
    private String courseId;
    private String facultyId;

    // Constructor
    public TeachingAssistantModel(String userId, String courseId, String facultyId) {
        this.userId = userId;
        this.courseId = courseId;
        this.facultyId = facultyId;
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

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }
}
