package src.main.java.WolfBooks.dao;

import src.main.java.WolfBooks.models.TeachingAssistantModel;
import src.main.java.WolfBooks.models.UserModel;
import src.main.java.WolfBooks.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeachingAssistantDAO {
    private Connection conn;



    // ==================== Authentication Operations ====================
    public TeachingAssistantModel authenticateTA(String userId, String password) throws SQLException {
        String query = "SELECT u.*, ta.faculty_id FROM Users u " +
                "JOIN TeachingAssistants ta ON u.user_id = ta.user_id " +
                "WHERE u.user_id = ? AND u.password = ? AND u.user_role = 'teaching_assistant'";
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, userId);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                return mapResultSetToTA(rs);
            }
        }
        return null;
    }

    public boolean updatePassword(String userId, String currentPassword, String newPassword) throws SQLException {
        String query = "UPDATE Users SET password = ? WHERE user_id = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();


                PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, newPassword);
            stmt.setString(2, userId);
            stmt.setString(3, currentPassword);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean checkFirstLogin(String userId) throws SQLException {
        String query = "SELECT first_login FROM Users WHERE user_id = ? AND user_role = 'teaching_assistant'";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();


                PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("first_login");
            }
        }
        return false;
    }

    // ==================== Course Access Operations ====================
    public List<String> getAssignedCourses(String taId) throws SQLException {
        List<String> courses = new ArrayList<>();
        String query = "SELECT course_id FROM TeachingAssistants WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, taId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(rs.getString("course_id"));
            }
        }
        return courses;
    }

    public boolean validateTACourseAccess(String taId, String courseId) throws SQLException {
        String query = "SELECT 1 FROM TeachingAssistants WHERE user_id = ? AND course_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, taId);
            stmt.setString(2, courseId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public List<UserModel> viewStudentsInCourse(String courseId) throws SQLException {
        List<UserModel> students = new ArrayList<>();

        String query = "SELECT u.* FROM Users u JOIN Enrollments e ON u.user_id = e.user_id WHERE e.course_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {


            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(mapResultSetToUser(rs));
            }
        }
        return students;
    }

    // ==================== Chapter Management Operations ====================
    public boolean addChapter(String textbookId, String chapterId, String chapterTitle, String createdBy) throws SQLException {
        String query = "INSERT INTO Chapters (textbook_id, chapter_id, chapter_title, created_by) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, textbookId);
            stmt.setString(2, chapterId);
            stmt.setString(3, chapterTitle);
            stmt.setString(4, createdBy);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean hideChapter(String textbookId, String chapterId, String taId, String courseId) throws SQLException {
        if (isTAAssignedToCourse(taId, courseId)) {
            String query = "UPDATE Chapters SET is_hidden = TRUE WHERE textbook_id = ? AND chapter_id = ?";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, textbookId);
                stmt.setString(2, chapterId);
                return stmt.executeUpdate() > 0;
            }
        }
        return false;
    }

    public boolean modifyChapter(String chapterId, String newTitle, String taId, String courseId) throws SQLException {
        if (isTAAssignedToCourse(taId, courseId)) {
            String query = "UPDATE Chapters SET chapter_title = ? WHERE chapter_id = ?";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, newTitle);
                stmt.setString(2, chapterId);
                return stmt.executeUpdate() > 0;
            }
        }
        return false;
    }

    // ==================== Section Management Operations ====================
    public boolean addSection(String textbookId, String chapterId, String sectionId, String sectionTitle, String createdBy) throws SQLException {
        String query = "INSERT INTO Sections (textbook_id, chapter_id, section_id, section_title, created_by) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, textbookId);
            stmt.setString(2, chapterId);
            stmt.setString(3, sectionId);
            stmt.setString(4, sectionTitle);
            stmt.setString(5, createdBy);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean hideSection(String textbookId, String chapterId, String sectionId, String taId, String courseId) throws SQLException {
        if (isTAAssignedToCourse(taId, courseId)) {
            String query = "UPDATE Sections SET is_hidden = TRUE WHERE textbook_id = ? AND chapter_id = ? AND section_id = ?";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, textbookId);
                stmt.setString(2, chapterId);
                stmt.setString(3, sectionId);
                return stmt.executeUpdate() > 0;
            }
        }
        return false;
    }

    public boolean modifySection(String sectionId, String newTitle, String taId, String courseId) throws SQLException {
        if (isTAAssignedToCourse(taId, courseId)) {
            String query = "UPDATE Sections SET section_title = ? WHERE section_id = ?";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, newTitle);
                stmt.setString(2, sectionId);
                return stmt.executeUpdate() > 0;
            }
        }
        return false;
    }

    // ==================== Content Block Operations ====================
    public boolean addContentBlock(String textbookId, String chapterId, String sectionId, String blockId, String contentType, String content, String createdBy) throws SQLException {
        String query = "INSERT INTO Blocks (textbook_id, chapter_id, section_id, block_id, content_type, content, created_by) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, textbookId);
            stmt.setString(2, chapterId);
            stmt.setString(3, sectionId);
            stmt.setString(4, blockId);
            stmt.setString(5, contentType);
            stmt.setString(6, content);
            stmt.setString(7, createdBy);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean hideContentBlock(String textbookId, String chapterId, String sectionId, String blockId, String taId, String courseId) throws SQLException {
        if (isTAAssignedToCourse(taId, courseId)) {
            String query = "UPDATE Blocks SET is_hidden = TRUE WHERE textbook_id = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, textbookId);
                stmt.setString(2, chapterId);
                stmt.setString(3, sectionId);
                stmt.setString(4, blockId);
                return stmt.executeUpdate() > 0;
            }
        }
        return false;
    }

    public boolean modifyContentBlock(String blockId, String newContent, String taId, String courseId) throws SQLException {
        if (isTAAssignedToCourse(taId, courseId)) {
            String query = "UPDATE Blocks SET content = ? WHERE block_id = ?";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, newContent);
                stmt.setString(2, blockId);
                return stmt.executeUpdate() > 0;
            }
        }
        return false;
    }

    // ==================== Activity Operations ====================
//    public boolean modifyActivity(String activityId, String newDetails, String taId, String courseId) throws SQLException {
//        if (isTAAssignedToCourse(taId, courseId)) {
//            String query = "UPDATE Activities SET details = ? WHERE activity_id = ?";
//            try (Connection conn = DatabaseConnection.getInstance().getConnection();
//                PreparedStatement stmt = conn.prepareStatement(query)) {
//                stmt.setString(1, newDetails);
//                stmt.setString(2, activityId);
//                return stmt.executeUpdate() > 0;
//            }
//        }
//        return false;
//    }

    // ==================== Helper Methods ====================
    private TeachingAssistantModel mapResultSetToTA(ResultSet rs) throws SQLException {
        TeachingAssistantModel ta = new TeachingAssistantModel(
                rs.getString("user_id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getBoolean("first_login"),
                rs.getString("faculty_id")
        );
        ta.setAssignedCourseIds(getAssignedCourses(ta.getUserId()));
        return ta;
    }

    private UserModel mapResultSetToUser(ResultSet rs) throws SQLException {
        return new UserModel(
                rs.getString("user_id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("user_role"),
                rs.getBoolean("first_login")
        );
    }

    private boolean isTAAssignedToCourse(String taId, String courseId) throws SQLException {
        String query = "SELECT 1 FROM TeachingAssistants WHERE user_id = ? AND course_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, taId);
            stmt.setString(2, courseId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }



    public boolean hideActivity(String textbookId, String chapterId, String sectionId, String blockId, String activityId, String taId, String courseId) throws SQLException {
        if (isTAAssignedToCourse(taId, courseId)) {
            String query = "UPDATE Activities SET is_hidden = TRUE WHERE textbook_id = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND activity_id = ?";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, textbookId);
                stmt.setString(2, chapterId);
                stmt.setString(3, sectionId);
                stmt.setString(4, blockId);
                stmt.setString(5, activityId);
                return stmt.executeUpdate() > 0;
            }
        }
        return false;
    }

// ==================== Deleting Content Operations ====================
public boolean deleteChapter(String chapterId, String taId) throws SQLException {
    if (validateTAOwnership(taId, chapterId, "Chapters")) {
        String query = "DELETE FROM Chapters WHERE chapter_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, chapterId);
            return stmt.executeUpdate() > 0;
        }
    }
    return false;
}

public boolean deleteSection(String sectionId, String taId) throws SQLException {
    if (validateTAOwnership(taId, sectionId, "Sections")) {
        String query = "DELETE FROM Sections WHERE section_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sectionId);
            return stmt.executeUpdate() > 0;
        }
    }
    return false;
}

    public boolean deleteContentBlock(String textbookId, String chapterId, String sectionId, String blockId, String taId) throws SQLException {
        // First drop the existing procedure
        String dropProc = "DROP PROCEDURE IF EXISTS DeleteBlockAndActivity";

        // Create the procedure
        String createProc =
                "CREATE PROCEDURE DeleteBlockAndActivity(" +
                        "    IN p_textbook_id VARCHAR(50)," +
                        "    IN p_chapter_id VARCHAR(50)," +
                        "    IN p_section_id VARCHAR(50)," +
                        "    IN p_block_id VARCHAR(50)" +
                        ")" +
                        "BEGIN " +
                        "    DELETE FROM StudentActivities " +
                        "    WHERE textbook_id = p_textbook_id " +
                        "    AND chapter_id = p_chapter_id " +
                        "    AND section_id = p_section_id " +
                        "    AND block_id = p_block_id; " +

                        "    DELETE FROM Questions " +
                        "    WHERE textbook_id = p_textbook_id " +
                        "    AND chapter_id = p_chapter_id " +
                        "    AND section_id = p_section_id " +
                        "    AND block_id = p_block_id; " +

                        "    DELETE FROM Activities " +
                        "    WHERE textbook_id = p_textbook_id " +
                        "    AND chapter_id = p_chapter_id " +
                        "    AND section_id = p_section_id " +
                        "    AND block_id = p_block_id; " +

                        "    DELETE FROM Blocks " +
                        "    WHERE textbook_id = p_textbook_id " +
                        "    AND chapter_id = p_chapter_id " +
                        "    AND section_id = p_section_id " +
                        "    AND block_id = p_block_id; " +
                        "END";

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            // Drop existing procedure
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(dropProc);
                stmt.execute(createProc);
            }

            // Call the procedure
            String callProc = "{call DeleteBlockAndActivity(?, ?, ?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(callProc)) {
                cstmt.setString(1, textbookId);
                cstmt.setString(2, chapterId);
                cstmt.setString(3, sectionId);
                cstmt.setString(4, blockId);

                cstmt.execute();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error in deleteContentBlock: " + e.getMessage());
            throw e;
        }
    }

//public boolean deleteContentBlock(String blockId, String taId) throws SQLException {
//    if (validateTAOwnership(taId, blockId, "Blocks")) {
//        String query = "DELETE FROM Blocks WHERE block_id = ?";
//        try (Connection conn = DatabaseConnection.getInstance().getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, blockId);
//            return stmt.executeUpdate() > 0;
//
//
//        }
//    }
//}

//public boolean deleteContentBlock(String textbookId, String chapterId, String sectionId, String blockId, String taId) throws SQLException {
//    String procedureCall = "CALL DeleteBlockAndActivity(?, ?, ?, ?)";  // Note: lowercase 'call'
//
//    try (Connection conn = DatabaseConnection.getInstance().getConnection();
//         CallableStatement stmt = conn.prepareCall(procedureCall)) {
//
//        stmt.setString(1, textbookId);
//        stmt.setString(2, chapterId);
//        stmt.setString(3, sectionId);
//        stmt.setString(4, blockId);
//
//        stmt.execute();
//        return true;
//    } catch (SQLException e) {
//        System.err.println("Error executing DeleteBlockAndActivity: " + e.getMessage());
//        throw e;
//    }
//}
//
//// ==================== Validation Helpers ====================
private boolean validateTAOwnership(String taId, String contentId, String tableName) throws SQLException {
    String query = "SELECT created_by FROM " + tableName + " WHERE id = ?";
    try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, contentId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("created_by").equals(taId);
        }
    }
    return false;
}


public List<String> getAssignedTextbooks(String taId) throws SQLException {
    List<String> textbooks = new ArrayList<>();
    String query = "SELECT DISTINCT t.textbook_id, t.textbook_title " +
                  "FROM Textbooks t " +
                  "JOIN Courses c ON t.textbook_id = c.textbook_id " +
                  "JOIN TeachingAssistants ta ON c.course_id = ta.course_id " +
                  "WHERE ta.user_id = ?";

    try (Connection conn = DatabaseConnection.getInstance().getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, taId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            textbooks.add(rs.getString("textbook_title"));


        }
    }
    return textbooks;
}


public List<String> getAssignedTextbooks(String taId, String courseId) throws SQLException {
    List<String> textbooks = new ArrayList<>();
    String query = "SELECT t.textbook_id " +
                  "FROM Textbooks t " +
                  "JOIN Courses c ON t.textbook_id = c.textbook_id " +
                  "WHERE c.course_id = ? AND c.course_id IN (" +
                  "    SELECT course_id FROM TeachingAssistants WHERE user_id = ?" +
                  ")";

    try (Connection conn = DatabaseConnection.getInstance().getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, courseId);
        stmt.setString(2, taId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            textbooks.add(rs.getString("textbook_id"));

        }
    }
    return textbooks;
}



    // ==================== Other Helper Methods ====================
private boolean validateAccess(String userId, String contentId, String table) throws SQLException {
    String query = "SELECT 1 FROM " + table + " WHERE created_by = ? AND id = ?";
    try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, userId);
        stmt.setString(2, contentId);
        ResultSet rs = stmt.executeQuery();
        return rs.next();  // Returns true if the user created the content
    }
}

// ==================== Connection Management ====================

}
