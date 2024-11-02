package src.main.java.WolfBooks.dao;

import src.main.java.WolfBooks.models.TextbookModel;
import src.main.java.WolfBooks.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TextbookDAO {

    // Create a new textbook
    public boolean createTextbook(TextbookModel textbook) {
        String sql = "INSERT INTO Textbooks (textbook_id, textbook_title) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbook.getTextbookID());
            pstmt.setString(2, textbook.getTextbookTitle());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a textbook by ID
    public TextbookModel getTextbookById(String textbookId) {
        String sql = "SELECT * FROM Textbooks WHERE textbook_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractTextbookFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all textbooks
    public List<TextbookModel> getAllTextbooks() {
        List<TextbookModel> textbooks = new ArrayList<>();
        String sql = "SELECT * FROM Textbooks";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                textbooks.add(extractTextbookFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return textbooks;
    }

    // Update a textbook
    public boolean updateTextbook(TextbookModel textbook) {
        String sql = "UPDATE Textbooks SET textbook_title = ? WHERE textbook_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbook.getTextbookTitle());
            pstmt.setString(2, textbook.getTextbookID());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a textbook
    public boolean deleteTextbook(String textbookId) {
        String sql = "DELETE FROM Textbooks WHERE textbook_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Utility method to extract TextbookModel from ResultSet
    private TextbookModel extractTextbookFromResultSet(ResultSet rs) throws SQLException {
        return new TextbookModel(
                rs.getString("textbook_id"),
                rs.getString("textbook_title")
        );
    }
}