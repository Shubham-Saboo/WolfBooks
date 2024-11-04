package src.main.java.WolfBooks.models;

public class StudentGradesModel {

    private String studentId;
    private String courseId;
    private int totalPoints;
    private int totalActivities;

    public StudentGradesModel(String studentId, String courseId, int totalPoints, int totalActivities) {
        setStudentId(studentId);
        setCourseId(courseId);
        setTotalPoints(totalPoints);
        setTotalActivities(totalActivities);
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getTotalActivities() {
        return totalActivities;
    }

    public void setTotalActivities(int totalActivities) {
        this.totalActivities = totalActivities;
    }
}
