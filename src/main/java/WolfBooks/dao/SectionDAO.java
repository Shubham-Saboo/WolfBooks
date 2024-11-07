package src.main.java.WolfBooks.dao;

import src.main.java.WolfBooks.models.SectionModel;
import src.main.java.WolfBooks.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO {

    // Create a new section
    public boolean createSection(SectionModel section) {
        String sql = "INSERT INTO Sections (section_id, textbook_id, section_title, chapter_id, is_hidden, created_by) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, section.getSectionId());
            pstmt.setString(2, section.getTextbookId());
            pstmt.setString(3, section.getTitle());
            pstmt.setString(4, section.getChapterId());
            pstmt.setBoolean(5, section.isHidden());
            pstmt.setString(6, section.getCreatedBy());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a section by ID (modified to match FacultyService requirements)
    public static SectionModel getSectionById(String textbookId, String chapterId, String sectionId) {
        String sql = "SELECT * FROM Sections WHERE textbook_id = ? AND chapter_id = ? AND section_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            pstmt.setString(2, chapterId);
            pstmt.setString(3, sectionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractSectionFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Retrieve all sections for a specific chapter
    public List<SectionModel> getSectionsByChapter(String chapterId) {
        List<SectionModel> sections = new ArrayList<>();
        String sql = "SELECT * FROM Sections WHERE chapter_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, chapterId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                sections.add(extractSectionFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sections;
    }

    // Update a section
    public boolean updateSection(SectionModel section) {
        String sql = "UPDATE Sections SET section_title = ?, is_hidden = ? WHERE section_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, section.getTitle());
            pstmt.setBoolean(2, section.isHidden());
            pstmt.setString(3, section.getSectionId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a section (modified to match FacultyService requirements)
    public boolean deleteSection(String sectionId) {
        String sql = "DELETE FROM Sections WHERE section_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sectionId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Utility method to extract SectionModel from ResultSet
    private static SectionModel extractSectionFromResultSet(ResultSet rs) throws SQLException {
        return new SectionModel(
            rs.getString("section_id"),
            rs.getString("textbook_id"),
            rs.getString("chapter_id"),
            rs.getString("section_title"),
            rs.getBoolean("is_hidden"),
            rs.getString("created_by")
        );
    }
}
