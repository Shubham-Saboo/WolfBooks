package src.main.java.WolfBooks.dao;

import src.main.java.WolfBooks.models.ActivityModel;
import src.main.java.WolfBooks.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAO {

    // Create a new activity
    public boolean createActivity(ActivityModel activity) {
        String sql = "INSERT INTO Activities (activity_id, section_id, textbook_id, chapter_id, block_id, is_hidden, created_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, activity.getActivityId());
            pstmt.setString(2, activity.getSectionId());
            pstmt.setString(3, activity.getTextbookId());
            pstmt.setString(4, activity.getChapterId());
            pstmt.setString(5, activity.getBlockId());
            pstmt.setBoolean(6, activity.isHidden());
            pstmt.setString(7, activity.getCreatedBy());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve an activity by ID
    public ActivityModel getActivityById(String textbookId, String chapterId, String sectionId, String blockId, String activityId) {
        String sql = "SELECT * FROM Activities WHERE textbook_id = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND activity_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            pstmt.setString(2, chapterId);
            pstmt.setString(3, sectionId);
            pstmt.setString(4, blockId);
            pstmt.setString(5, activityId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractActivityFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all activities for a specific block
    public List<ActivityModel> getActivitiesByBlock(String textbookId, String chapterId, String sectionId, String blockId) {
        List<ActivityModel> activities = new ArrayList<>();
        String sql = "SELECT * FROM Activities WHERE textbook_id = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            pstmt.setString(2, chapterId);
            pstmt.setString(3, sectionId);
            pstmt.setString(4, blockId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                activities.add(extractActivityFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }

    // Update an existing activity
    public boolean updateActivity(ActivityModel activity) {
        String sql = "UPDATE Activities SET is_hidden = ?, created_by = ? WHERE textbook_id = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND activity_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set updated fields
            pstmt.setBoolean(1, activity.isHidden());
            pstmt.setString(2, activity.getCreatedBy());

            // Set identifiers
            pstmt.setString(3, activity.getTextbookId());
            pstmt.setString(4, activity.getChapterId());
            pstmt.setString(5, activity.getSectionId());
            pstmt.setString(6, activity.getBlockId());
            pstmt.setString(7, activity.getActivityId());

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete an existing activity by ID
    public boolean deleteActivity(String textbookId,String chapterId,String sectionId,String blockId,String activityId) {
        String sql="DELETE FROM Activities WHERE textbook_id=? AND chapter_id=? AND section_id=? AND block_id=? AND activity_id=?";

        try(Connection conn=DatabaseConnection.getInstance().getConnection();
            PreparedStatement 	pstmt=conn.prepareStatement(sql)){

            pstmt.setString (1,textbookId);
            pstmt.setString (2 ,chapterId );
            pstmt.setString (3 ,sectionId );
            pstmt.setString (4 ,blockId );
            pstmt.setString (5 ,activityId );

            int rowsAffected=pstmt.executeUpdate ();
            return rowsAffected>0;
        }catch(SQLException e){
            e.printStackTrace ();
            return false ;
        }
    }

    // Utility method to extract ActivityModel from ResultSet
    private ActivityModel extractActivityFromResultSet(ResultSet rs)throws SQLException{
        return new ActivityModel(
                rs.getString ("activity _id"),
                rs.getString ("block _id"),
                rs.getString ("section _id"),
                rs.getString ("chapter _id"),
                rs.getString ("textbook _id"),
                rs.getBoolean("is_hidden"),
                rs.getString ("created_by")
        );
    }
}
