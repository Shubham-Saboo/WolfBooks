package src.main.java.WolfBooks.models;

public class CourseModel {

    private String courseId;
    private String courseTitle;
    private String facultyId;
    private String startDate;
    private String endDate;
    private String courseType;
    private int capacity;
    private String token;
    private int textbookId;

    public CourseModel(String courseId, String courseTitle, String facultyId, String startDate, String endDate, String courseType, int capacity, String token, int textbookId) {
        setCourseId(courseId);
        setCourseTitle(courseTitle);
        setFacultyId(facultyId);
        setStartDate(startDate);
        setEndDate(endDate);
        setCourseType(courseType);
        setCapacity(capacity);
        setToken(token);
        setTextbookId(textbookId);
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(int textbookId) {
        this.textbookId = textbookId;
    }
}
