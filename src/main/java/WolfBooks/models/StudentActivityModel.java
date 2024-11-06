package src.main.java.WolfBooks.models;

import java.sql.Timestamp;

public class StudentActivityModel {
    private String studentId;
    private String courseId;
    private String textbookId;
    private String chapterId;
    private String sectionId;
    private String blockId;
    private String questionId;
    private String uniqueActivityId;
    private Timestamp timestamp;
    int score = 0;

    public StudentActivityModel(String studentId, String courseId, String textbookId, String chapterId, String sectionId, String blockId, String questionId, String uniqueActivityId, int score, Timestamp timestamp) {
        setStudentId(studentId);
        setCourseId(courseId);
        setTextbookId(textbookId);
        setChapterId(chapterId);
        setSectionId(sectionId);
        setBlockId(blockId);
        setQuestionId(questionId);
        setUniqueActivityId(uniqueActivityId);
        setScore(score);
        setTimestamp(timestamp);
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

    public String getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(String textbookId) {
        this.textbookId = textbookId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUniqueActivityId() {
        return uniqueActivityId;
    }

    public void setUniqueActivityId(String uniqueActivityId) {
        this.uniqueActivityId = uniqueActivityId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
