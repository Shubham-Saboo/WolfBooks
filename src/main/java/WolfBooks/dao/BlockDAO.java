package src.main.java.WolfBooks.dao;
import src.main.java.WolfBooks.models.BlockModel;
import src.main.java.WolfBooks.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BlockDAO {

    public boolean addBlock(BlockModel block) {
        String sqlQuery = "INSERT INTO blocks (section_id, textbook_id, block_id, chapter_id, content_type, content, is_hidden, created_by, sequence_number) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, block.getSectionId());
            stmt.setInt(2, block.getTextbookId());
            stmt.setString(3, block.getBlockId());
            stmt.setString(4, block.getChapterId());
            stmt.setString(5, block.getContentType());
            stmt.setString(6, block.getContent());
            stmt.setBoolean(7, block.isHidden());
            stmt.setString(8, block.getCreatedBy());
            stmt.setInt(9, block.getSequenceNumber());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean modifyBlock(BlockModel block) {
        String sqlQuery = "UPDATE blocks SET content_type = ?, content = ?, is_hidden = ? " +
                "WHERE section_id = ? AND textbook_id = ? AND block_id = ? AND chapter_id = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, block.getContentType());
            stmt.setString(2, block.getContent());
            stmt.setBoolean(3, block.isHidden());
            stmt.setString(4, block.getSectionId());
            stmt.setInt(5, block.getTextbookId());
            stmt.setString(6, block.getBlockId());
            stmt.setString(7, block.getChapterId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteBlock(BlockModel block) {
        String sqlQuery = "DELETE FROM blocks WHERE section_id = ? AND textbook_id = ? AND block_id = ? AND chapter_id = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, block.getSectionId());
            stmt.setInt(2, block.getTextbookId());
            stmt.setString(3, block.getBlockId());
            stmt.setString(4, block.getChapterId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Find block by section

    public boolean findBlock(String textbookId, String chapterId, String sectionId) {
        String sqlQuery = "SELECT FROM blocks WHERE textbook_id = ? AND chapter_id = ? AND section_id = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}