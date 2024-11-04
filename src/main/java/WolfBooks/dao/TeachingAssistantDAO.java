package src.main.java.WolfBooks.dao;

import src.main.java.WolfBooks.models.TeachingAssistantModel;
import src.main.java.WolfBooks.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeachingAssistantDAO {

    // Create a new teaching assistant
    public boolean createTeachingAssistant(TeachingAssistantModel ta) {
        String sql = "INSERT INTO TeachingAssistants (user_id, course_id, faculty_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ta.getUserId());
            pstmt.setString(2, ta.getCourseId());
            pstmt.setString(3, ta.getFacultyId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a teaching assistant by User ID, Course ID, and Faculty ID
    public TeachingAssistantModel getTeachingAssistantByIds(String userId, String courseId, String facultyId) {
        String sql = "SELECT * FROM TeachingAssistants WHERE user_id = ? AND course_id = ? AND faculty_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            pstmt.setString(2, courseId);
            pstmt.setString(3, facultyId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractTeachingAssistantFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all teaching assistants for a specific course
    public List<TeachingAssistantModel> getTeachingAssistantsByCourse(String courseId) {
        List<TeachingAssistantModel> teachingAssistants = new ArrayList<>();
        String sql = "SELECT * FROM TeachingAssistants WHERE course_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                teachingAssistants.add(extractTeachingAssistantFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachingAssistants;
    }

    // Update a teaching assistant's faculty ID
    public boolean updateTeachingAssistantFaculty(String userId, String courseId, String newFacultyId) {
        String sql = "UPDATE TeachingAssistants SET faculty_id = ? WHERE user_id = ? AND course_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set updated fields
            pstmt.setString(1, newFacultyId);
            pstmt.setString(2, userId);
            pstmt.setString(3, courseId);

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a specific teaching assistant by User ID, Course ID, and Faculty ID
    public boolean deleteTeachingAssistant(String userId, String courseId, String facultyId) {
        String sql="DELETE FROM TeachingAssistants WHERE user_id=? AND course_id=? AND faculty_id=?";

        try(Connection conn=DatabaseConnection.getInstance().getConnection();
            PreparedStatement 	pstmt=conn.prepareStatement(sql)){

            pstmt.setString (1 ,userId);
            pstmt .setString (2 ,courseId );
            pstmt .setString (3 ,facultyId );

            int rowsAffected=pstmt .executeUpdate ();
            return rowsAffected>0;
        }catch(SQLException e){
            e.printStackTrace ();
            return false ;
        }
    }

    // Utility method to extract TeachingAssistantModel from ResultSet
    private TeachingAssistantModel extractTeachingAssistantFromResultSet(ResultSet rs)throws SQLException{
        return new TeachingAssistantModel(
                rs.getString ("user_id"),
                rs.getString ("course_id"),
                rs.getString ("faculty_id")
        );
    }
}
