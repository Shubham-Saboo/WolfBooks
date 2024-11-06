package src.main.java.WolfBooks.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import src.main.java.WolfBooks.util.DatabaseConnection;

public class QueryDAO {

    // 1. Query to print the number of sections in the first chapter of a textbook
    public void printNumberOfSectionsInFirstChapter(String textbookId, String chapterId) throws SQLException {
        String sql = "SELECT COUNT(*) AS section_count FROM Sections WHERE textbook_id = ? AND chapter_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            pstmt.setString(2, chapterId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int sectionCount = rs.getInt("section_count");
                System.out.println("Number of sections in Chapter " + chapterId + ": " + sectionCount);
            }
        }
    }

    // 2. Query to print names of faculty and TAs with their roles
    public void printFacultyAndTAsWithRoles() throws SQLException {
        String sql = "SELECT u.firstname, u.lastname, 'faculty' AS role FROM Users u " +
                "JOIN Courses c ON u.user_id = c.faculty_id " +
                "UNION SELECT u.firstname, u.lastname, 'teaching_assistant' AS role FROM Users u " +
                "JOIN TeachingAssistants ta ON u.user_id = ta.user_id";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String role = rs.getString("role");
                System.out.println(firstName + " " + lastName + ": " + role);
            }
        }
    }

    // 3. Query to print active courses with faculty and total number of students
    public void printActiveCoursesWithFacultyAndStudents() throws SQLException {
        String sql = "SELECT c.course_id, CONCAT(u.firstname, ' ', u.lastname) AS faculty_name, COUNT(e.user_id) AS total_students " +
                "FROM Courses c JOIN Users u ON c.faculty_id = u.user_id LEFT JOIN Enrollments e ON c.course_id = e.course_id " +
                "WHERE c.course_type = 'Active' GROUP BY c.course_id, faculty_name";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String courseId = rs.getString("course_id");
                String facultyName = rs.getString("faculty_name");
                int totalStudents = rs.getInt("total_students");
                System.out.println(courseId + ": Faculty - " + facultyName + ", Students - " + totalStudents);
            }
        }
    }

    // 4. Query to find the course with the largest waiting list
    public void printCourseWithLargestWaitingList() throws SQLException {
        String sql = "SELECT e.course_id, COUNT(*) AS waiting_list_count FROM Enrollments e WHERE e.user_status = 'Pending' GROUP BY e.course_id ORDER BY waiting_list_count DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String courseId = rs.getString("course_id");
                int waitingListCount = rs.getInt("waiting_list_count");
                System.out.println("Course ID: " + courseId + ", Waiting List Count: " + waitingListCount);
            }
        }
    }

    // 5. Query to print contents of Chapter 02 from Textbook 101
    public void printContentsOfChapter02() throws SQLException {
        String sql = "SELECT content_type, content FROM Blocks where chapter_id='Chap02' AND textbook_id='101' ORDER BY block_id";
        try (Connection conn=DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt=conn.prepareStatement(sql)) {

            ResultSet rs=pstmt.executeQuery();
            while(rs.next()) {
                String contentType=rs.getString("content_type");
                String content=rs.getString("content");
                System.out.println(contentType+ ": "+ content);
            }
        }
    }

    // 6. Query to print incorrect answers for Q2 of Activity0 in Sec02 of Chap01 in textbook 101
    public void printIncorrectAnswersForQuestion() throws SQLException {
        String sql="SELECT answer_one AS incorrect_answer_1, explanation_one AS explanation_1, answer_three AS incorrect_answer_2, explanation_three AS explanation_2, answer_four AS incorrect_answer_3, explanation_four AS explanation_3 FROM Questions WHERE textbook_id='101' AND chapter_id='Chap01' AND section_id='Sec02' AND block_id='Block01' AND activity_id='ACT0' AND question_id='Q2'";

        try(Connection conn=DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql)) {

            ResultSet rs=pstmt.executeQuery();
            while(rs.next()) {
                System.out.println("Incorrect Answer 1: "+rs.getString("incorrect_answer_1")+", Explanation: "+rs.getString("explanation_1"));
                System.out.println("Incorrect Answer 2: "+rs.getString("incorrect_answer_2")+", Explanation: "+rs.getString("explanation_2"));
                System.out.println("Incorrect Answer 3: "+rs.getString("incorrect_answer_3")+", Explanation: "+rs.getString("explanation_3"));
            }
        }
    }

    // 7. Query to find books in active status by one instructor and evaluation status by another
    public void findBooksInActiveAndEvaluationStatus() throws SQLException {
        String sql="SELECT t.textbook_id FROM Textbooks t JOIN Courses ac ON t.textbook_id=ac.textbook_id AND ac.course_type='Active' JOIN Courses ec ON t.textbook_id=ec.textbook_id AND ec.course_type='Evaluation' WHERE ac.faculty_id<>ec.faculty_id";
        try(Connection conn=DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql)) {

            ResultSet rs=pstmt.executeQuery();
            while(rs.next()) {
                System.out.println("Textbook ID: "+rs.getString("textbook _id")+", Active Faculty ID: "+rs.getString("active_faculty")+", Evaluation Faculty ID: "+rs.getString("eval_faculty"));
            }
        }
    }
}
