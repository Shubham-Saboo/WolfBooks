package src.main.java.WolfBooks.models;


import java.util.Date;

public class CourseModel {

    private String courseId;
    private String courseTitle;
    private String facultyId;
    private Date startDate;
    private Date endDate;
    private String courseType;
    private int capacity;
    private String token;
    private String textbookId;

    public CourseModel(String courseId, String courseTitle, String facultyId, Date startDate, Date endDate, String courseType, int capacity, String token, String textbookId) {
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

    public Date getStartDate() { return startDate;}

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public String getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(String textbookId) {
        this.textbookId = textbookId;
    }
}
