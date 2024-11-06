package src.main.java.WolfBooks.dao;

import src.main.java.WolfBooks.models.ChapterModel;
import src.main.java.WolfBooks.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChapterDAO {

    // Create a new chapter
    public boolean createChapter(ChapterModel chapter) {
        String sql = "INSERT INTO Chapters (chapter_id, chapter_title, textbook_id, is_hidden, created_by) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, chapter.getChapterId());
            pstmt.setString(2, chapter.getChapterTitle());
            pstmt.setString(3, chapter.getTextbookId());
            pstmt.setBoolean(4, chapter.isHidden());
            pstmt.setString(5, chapter.getCreatedBy());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a chapter by ID
    public static ChapterModel getChapterById(String textbookId, String chapterId) {
        String sql = "SELECT * FROM Chapters WHERE textbook_id = ? AND chapter_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            pstmt.setString(2, chapterId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractChapterFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all chapters for a specific textbook
    public List<ChapterModel> getChaptersByTextbook(String textbookId) {
        List<ChapterModel> chapters = new ArrayList<>();
        String sql = "SELECT * FROM Chapters WHERE textbook_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                chapters.add(extractChapterFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chapters;
    }

    // Update a chapter
    public boolean updateChapter(ChapterModel chapter) {
        String sql = "UPDATE Chapters SET chapter_title = ?, is_hidden = ? WHERE textbook_id = ? AND chapter_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, chapter.getChapterTitle());
            pstmt.setBoolean(2, chapter.isHidden());
            pstmt.setString(3, chapter.getTextbookId());
            pstmt.setString(4, chapter.getChapterId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a chapter
    public boolean deleteChapter(String textbookId, String chapterId) {
        String sql = "DELETE FROM Chapters WHERE textbook_id = ? AND chapter_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            pstmt.setString(2, chapterId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Utility method to extract ChapterModel from ResultSet
    private static ChapterModel extractChapterFromResultSet(ResultSet rs) throws SQLException {
        ChapterModel chapter = new ChapterModel(
                rs.getString("chapter_id"),
                rs.getString("chapter_title"),
                rs.getString("textbook_id"),
                rs.getBoolean("is_hidden"),
                rs.getString("created_by")
        );
        return chapter;
    }
}