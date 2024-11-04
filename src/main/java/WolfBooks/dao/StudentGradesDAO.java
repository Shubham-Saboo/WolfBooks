package src.main.java.WolfBooks.dao;

import src.main.java.WolfBooks.models.StudentGradesModel;
import src.main.java.WolfBooks.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentGradesDAO {

    public boolean addStudentGrade(StudentGradesModel studentGradesModel) {
        String sqlQuery = "INSERT INTO studentgrades (student_id, course_id, total_points, total_activities) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, studentGradesModel.getStudentId());
            stmt.setString(2, studentGradesModel.getCourseId());
            stmt.setInt(3, studentGradesModel.getTotalPoints());
            stmt.setInt(4, studentGradesModel.getTotalActivities());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public StudentGradesModel getStudentGrade(String studentId, String courseId) {
        String sqlQuery = "SELECT * FROM studentgrades WHERE student_id = ? AND course_id = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, studentId);
            stmt.setString(2, courseId);
            ResultSet rs = stmt.executeQuery();
            return mapResultSetToStudentGrades(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean modifyStudentGrade(StudentGradesModel studentGrade) {
        String sqlQuery = "UPDATE studentgrades SET total_points = ?, total_activities = ? " +
                "WHERE student_id = ? AND course_id = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setInt(1, studentGrade.getTotalPoints());
            stmt.setInt(2, studentGrade.getTotalActivities());
            stmt.setString(3, studentGrade.getStudentId());
            stmt.setString(4, studentGrade.getCourseId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private StudentGradesModel mapResultSetToStudentGrades(ResultSet rs) throws SQLException {
        return new StudentGradesModel(
            rs.getString("student_id"),
            rs.getString("course_id"),
            rs.getInt("total_points"),
            rs.getInt("total_activities")
        );
    }
}
