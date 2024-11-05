package src.main.java.WolfBooks.dao;

import src.main.java.WolfBooks.models.CourseModel;
import src.main.java.WolfBooks.util.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // Create a new course
    public boolean createCourse(CourseModel course) {
        String sql = "INSERT INTO Courses (course_id, course_title, faculty_id, start_date, end_date, course_type, capacity, token, textbook_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getCourseId());
            pstmt.setString(2, course.getCourseTitle());
            pstmt.setString(3, course.getFacultyId());
            pstmt.setDate(4, course.getStartDate());
            pstmt.setDate(5, course.getEndDate());
            pstmt.setString(6, course.getCourseType());
            pstmt.setInt(7, course.getCapacity());
            pstmt.setString(8, course.getToken());
            pstmt.setString(9, course.getTextbookId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a course by ID
    public CourseModel getCourseById(String courseId) {
        String sql = "SELECT * FROM Courses WHERE course_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractCourseFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all courses
    public List<CourseModel> getAllCourses() {
        List<CourseModel> courses = new ArrayList<>();
        String sql = "SELECT * FROM Courses";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                courses.add(extractCourseFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    // Update an existing course
    public boolean updateCourse(CourseModel course) {
        String sql = "UPDATE Courses SET course_title = ?, faculty_id = ?, start_date = ?, end_date = ?, " +
                "course_type = ?, capacity = ?, token = ?, textbook_id = ? WHERE course_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set updated fields
            pstmt.setString(1, course.getCourseTitle());
            pstmt.setString(2, course.getFacultyId());
            pstmt.setDate(3, course.getStartDate());
            pstmt.setDate(4, course.getEndDate());
            pstmt.setString(5, course.getCourseType());
            pstmt.setInt(6, course.getCapacity());
            pstmt.setString(7, course.getToken());
            pstmt.setString(8, course.getTextbookId());

            // Set identifier
            pstmt.setString(9, course.getCourseId());

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a specific Course by ID
    public boolean deleteCourse(String courseId) {
        String sql="DELETE FROM Courses WHERE course_id=?";

        try(Connection conn=DatabaseConnection.getInstance().getConnection();
            PreparedStatement 	pstmt=conn.prepareStatement(sql)){

            pstmt.setString (1 ,courseId);

            int rowsAffected=pstmt .executeUpdate ();
            return rowsAffected>0;
        } catch(SQLException e) {
            e.printStackTrace ();
            return false ;
        }
    }

    public CourseModel getCourseByToken(String token) {
        String sql = "SELECT * FROM Courses WHERE token = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, token);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? extractCourseFromResultSet(rs) : null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Utility method to extract CourseModel from ResultSet
    private CourseModel extractCourseFromResultSet(ResultSet rs)throws SQLException{
        return new CourseModel(
                rs.getString ("course_id"),
                rs.getString ("course_title"),
                rs.getString ("faculty_id"),
                rs.getDate ("start_date"),
                rs.getDate ("end_date"),
                rs.getString ("course_type"),
                rs.getInt("capacity"),
                rs.getString ("token"),
                rs.getString ("textbook_id")
        );
    }
}