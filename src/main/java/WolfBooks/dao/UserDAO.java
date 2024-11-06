package src.main.java.WolfBooks.dao;

import src.main.java.WolfBooks.models.UserModel;
import src.main.java.WolfBooks.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // ==================== Authentication Operations ====================
    public UserModel authenticateUser(String userId, String password) throws SQLException {
        String query = "SELECT * FROM Users WHERE user_id = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                return mapResultSetToUser(rs);
            }
        }
        return null;
    }

    // ==================== User Management Operations ====================
    public boolean createUser(UserModel user) throws SQLException {
        String query = "INSERT INTO Users (user_id, firstname, lastname, email, password, user_role, first_login) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUserId());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getRole());
            stmt.setBoolean(7, user.isFirstLogin());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean createFacultyAccount(UserModel faculty) throws SQLException {
        if (!"faculty".equals(faculty.getRole().toLowerCase())) {
            throw new IllegalArgumentException("User must be a faculty member");
        }
        return createUser(faculty);
    }

    public boolean createTAAccount(UserModel ta, String courseId, String facultyId) throws SQLException {
        if (!"teaching_assistant".equals(ta.getRole().toLowerCase())) {
            throw new IllegalArgumentException("User must be a teaching assistant");
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstance().getConnection();
            conn.setAutoCommit(false);

            boolean userCreated = createUser(ta);
            if (!userCreated) {
                conn.rollback();
                return false;
            }

            String query = "INSERT INTO TeachingAssistants (user_id, course_id, faculty_id) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, ta.getUserId());
                stmt.setString(2, courseId);
                stmt.setString(3, facultyId);
                if (stmt.executeUpdate() <= 0) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // ==================== User Lookup Operations ====================
    public UserModel findById(String userId) throws SQLException {
        String query = "SELECT * FROM Users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        }
        return null;
    }

    public UserModel findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        }
        return null;
    }

    public UserModel findByEmailAndRole(String email, String role) {
        String query = "SELECT * FROM Users WHERE email = ? and user_role = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, role);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? mapResultSetToUser(rs) : null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<UserModel> listAllUsers() throws SQLException {
        List<UserModel> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }
        return users;
    }

    public List<UserModel> findUsersByRole(String role) throws SQLException {
        List<UserModel> users = new ArrayList<>();
        String query = "SELECT * FROM Users WHERE user_role = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, role.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }
        return users;
    }

    // ==================== Password Management ====================
    public boolean changePassword(String userId, String currentPassword, String newPassword) throws SQLException {
        String verifyQuery = "SELECT * FROM Users WHERE user_id = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement verifyStmt = conn.prepareStatement(verifyQuery)) {

            verifyStmt.setString(1, userId);
            verifyStmt.setString(2, currentPassword);

            if (!verifyStmt.executeQuery().next()) {
                return false;
            }

            String updateQuery = "UPDATE Users SET password = ?, first_login = false WHERE user_id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setString(1, newPassword);
                updateStmt.setString(2, userId);
                return updateStmt.executeUpdate() > 0;
            }
        }
    }

    // ==================== Helper Methods ====================
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
}