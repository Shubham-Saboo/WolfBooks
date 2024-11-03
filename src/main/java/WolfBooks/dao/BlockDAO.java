package src.main.java.WolfBooks.dao;

import src.main.java.WolfBooks.models.BlockModel;
import src.main.java.WolfBooks.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlockDAO {

    // Create a new block
    public boolean createBlock(BlockModel block) {
        String sql = "INSERT INTO Blocks (section_id, textbook_id, block_id, chapter_id, content_type, content, is_hidden, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, block.getSectionId());
            pstmt.setString(2, block.getTextbookId());
            pstmt.setString(3, block.getBlockId());
            pstmt.setString(4, block.getChapterId());
            pstmt.setString(5, block.getContentType());
            pstmt.setString(6, block.getContent());
            pstmt.setBoolean(7, block.isHidden());
            pstmt.setString(8, block.getCreatedBy());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a block by ID
    public BlockModel getBlockById(String textbookId, String chapterId, String sectionId, String blockId) {
        String sql = "SELECT * FROM Blocks WHERE textbook_id = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            pstmt.setString(2, chapterId);
            pstmt.setString(3, sectionId);
            pstmt.setString(4, blockId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractBlockFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all blocks for a specific section
    public List<BlockModel> getBlocksBySection(String textbookId, String chapterId, String sectionId) {
        List<BlockModel> blocks = new ArrayList<>();
        String sql = "SELECT * FROM Blocks WHERE textbook_id = ? AND chapter_id = ? AND section_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            pstmt.setString(2, chapterId);
            pstmt.setString(3, sectionId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                blocks.add(extractBlockFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blocks;
    }

    // Update a block
    public boolean updateBlock(BlockModel block) {
        String sql = "UPDATE Blocks SET content_type = ?, content = ?, is_hidden = ? WHERE textbook_id = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, block.getContentType());
            pstmt.setString(2, block.getContent());
            pstmt.setBoolean(3, block.isHidden());
            pstmt.setString(4, block.getTextbookId());
            pstmt.setString(5, block.getChapterId());
            pstmt.setString(6, block.getSectionId());
            pstmt.setString(7, block.getBlockId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a block
    public boolean deleteBlock(String textbookId, String chapterId, String sectionId, String blockId) {
        String sql = "DELETE FROM Blocks WHERE textbook_id = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            pstmt.setString(2, chapterId);
            pstmt.setString(3, sectionId);
            pstmt.setString(4, blockId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Utility method to extract BlockModel from ResultSet
    private BlockModel extractBlockFromResultSet(ResultSet rs) throws SQLException {
        return new BlockModel(
                rs.getString("block_id"),
                rs.getString("section_id"),
                rs.getString("textbook_id"),
                rs.getString("chapter_id"),
                rs.getString("content_type"),
                rs.getString("content"),
                rs.getBoolean("is_hidden"),
                rs.getString("created_by")
        );
    }
}