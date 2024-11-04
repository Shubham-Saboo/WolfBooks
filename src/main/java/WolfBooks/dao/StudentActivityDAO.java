package src.main.java.WolfBooks.dao;

import src.main.java.WolfBooks.models.StudentActivityModel;
import src.main.java.WolfBooks.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentActivityDAO {

    public StudentActivityModel getStudentActivity(String studentId, String courseId, String textbookId, String chapterId,
                                                   String sectionId, String blockId, String questionId, String uniqueActivityId) {
        String sqlQuery = "SELECT * FROM studentactivities WHERE student_id = ? AND course_id = ? AND textbook_id = ? AND " +
                "chapter_id = ? AND section_id = ? AND block_id = ? AND question_id = ? AND unique_activity_id = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, studentId);
            stmt.setString(2, courseId);
            stmt.setString(3, textbookId);
            stmt.setString(4, chapterId);
            stmt.setString(5, sectionId);
            stmt.setString(6, blockId);
            stmt.setString(7, questionId);
            stmt.setString(8, uniqueActivityId);
            ResultSet rs = stmt.executeQuery();
            return mapResultSetToSA(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<StudentActivityModel> getStudentActivities(String studentId) {
        String sqlQuery = "SELECT * FROM studentactivities WHERE student_id = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();
            List<StudentActivityModel> studentActivities = new ArrayList<>();
            while (rs.next()) {
                studentActivities.add(mapResultSetToSA(rs));
            }
            return studentActivities;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean addStudentActivity(StudentActivityModel studentActivityModel) {
        String sqlQuery = "INSERT INTO studentactivities VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, studentActivityModel.getStudentId());
            stmt.setString(2, studentActivityModel.getCourseId());
            stmt.setString(3, studentActivityModel.getTextbookId());
            stmt.setString(4, studentActivityModel.getChapterId());
            stmt.setString(5, studentActivityModel.getSectionId());
            stmt.setString(6, studentActivityModel.getBlockId());
            stmt.setString(7, studentActivityModel.getQuestionId());
            stmt.setString(8, studentActivityModel.getUniqueActivityId());
            stmt.setInt(9, studentActivityModel.getScore());
            stmt.setString(10, studentActivityModel.getTimestamp());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean modifyStudentActivity(StudentActivityModel studentActivityModel) {
        String sqlQuery = "UPDATE studentactivities SET score = ?, sa_timestamp = ? " +
                "WHERE student_id = ? AND course_id = ? AND textbook_id = ? AND chapter_id = ? " +
                "AND section_id = ? AND block_id = ? AND question_id = ? AND unique_activity_id = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setInt(1, studentActivityModel.getScore());
            stmt.setString(2, studentActivityModel.getTimestamp());
            stmt.setString(3, studentActivityModel.getStudentId());
            stmt.setString(4, studentActivityModel.getCourseId());
            stmt.setString(5, studentActivityModel.getTextbookId());
            stmt.setString(6, studentActivityModel.getChapterId());
            stmt.setString(7, studentActivityModel.getSectionId());
            stmt.setString(8, studentActivityModel.getBlockId());
            stmt.setString(9, studentActivityModel.getQuestionId());
            stmt.setString(10, studentActivityModel.getUniqueActivityId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private StudentActivityModel mapResultSetToSA(ResultSet rs) throws SQLException {
        return new StudentActivityModel(
            rs.getString("student_id"),
            rs.getString("course_id"),
            rs.getString("textbook_id"),
            rs.getString("chapter_id"),
            rs.getString("section_id"),
            rs.getString("block_id"),
            rs.getString("question_id"),
            rs.getString("unique_activity_id"),
            rs.getInt("score"),
            rs.getString("sa_timestamp")
        );
    }
}
