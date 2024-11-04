package src.main.java.WolfBooks.dao;

import src.main.java.WolfBooks.models.EnrollmentModel;
import src.main.java.WolfBooks.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    // Create a new enrollment
    public boolean createEnrollment(EnrollmentModel enrollment) {
        String sql = "INSERT INTO Enrollments (user_id, course_id, user_status) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, enrollment.getUserId());
            pstmt.setString(2, enrollment.getCourseId());
            pstmt.setString(3, enrollment.getUserStatus());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve an enrollment by User ID and Course ID
    public EnrollmentModel getEnrollmentById(String userId, String courseId) {
        String sql = "SELECT * FROM Enrollments WHERE user_id = ? AND course_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            pstmt.setString(2, courseId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractEnrollmentFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all enrollments for a specific course
    public List<EnrollmentModel> getEnrollmentsByCourse(String courseId) {
        List<EnrollmentModel> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM Enrollments WHERE course_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                enrollments.add(extractEnrollmentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }

    // Update an existing enrollment's status
    public boolean updateEnrollmentStatus(String userId, String courseId, String newStatus) {
        String sql = "UPDATE Enrollments SET user_status = ? WHERE user_id = ? AND course_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set updated fields
            pstmt.setString(1, newStatus);
            pstmt.setString(2, userId);
            pstmt.setString(3, courseId);

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete an existing enrollment by User ID and Course ID
    public boolean deleteEnrollment(String userId, String courseId) {
        String sql="DELETE FROM Enrollments WHERE user_id=? AND course_id=?";

        try(Connection conn=DatabaseConnection.getInstance().getConnection();
            PreparedStatement 	pstmt=conn.prepareStatement(sql)){

            pstmt.setString (1 ,userId);
            pstmt .setString (2 ,courseId );

            int rowsAffected=pstmt .executeUpdate ();
            return rowsAffected>0;
        }catch(SQLException e){
            e.printStackTrace ();
            return false ;
        }
    }

    // Utility method to extract EnrollmentModel from ResultSet
    private EnrollmentModel extractEnrollmentFromResultSet(ResultSet rs)throws SQLException{
        return new EnrollmentModel(
                rs.getString ("user_id"),
                rs.getString ("course_id"),
                rs.getString ("user_status")
        );
    }
}
