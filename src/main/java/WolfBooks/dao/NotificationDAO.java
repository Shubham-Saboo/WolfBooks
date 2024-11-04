package src.main.java.WolfBooks.dao;

import src.main.java.WolfBooks.models.NotificationModel;
import src.main.java.WolfBooks.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    // Create a new notification
    public boolean createNotification(NotificationModel notification) {
        String sql = "INSERT INTO Notifications (notification_id, content, user_id, is_read) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, notification.getNotificationId());
            pstmt.setString(2, notification.getContent());
            pstmt.setString(3, notification.getUserId());
            pstmt.setBoolean(4, notification.isRead());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a notification by ID
    public NotificationModel getNotificationById(String userId, String notificationId) {
        String sql = "SELECT * FROM Notifications WHERE user_id = ? AND notification_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            pstmt.setString(2, notificationId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractNotificationFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all notifications for a specific user
    public List<NotificationModel> getNotificationsByUser(String userId) {
        List<NotificationModel> notifications = new ArrayList<>();
        String sql = "SELECT * FROM Notifications WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                notifications.add(extractNotificationFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    // Update a notification's read status
    public boolean updateNotificationReadStatus(String userId, String notificationId, boolean isRead) {
        String sql = "UPDATE Notifications SET is_read = ? WHERE user_id = ? AND notification_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set updated fields
            pstmt.setBoolean(1, isRead);
            pstmt.setString(2, userId);
            pstmt.setString(3, notificationId);

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a specific notification by ID
    public boolean deleteNotification(String userId, String notificationId) {
        String sql="DELETE FROM Notifications WHERE user_id=? AND notification_id=?";

        try(Connection conn=DatabaseConnection.getInstance().getConnection();
            PreparedStatement 	pstmt=conn.prepareStatement(sql)){

            pstmt.setString (1 ,userId);
            pstmt .setString (2 ,notificationId);

            int rowsAffected=pstmt .executeUpdate ();
            return rowsAffected>0;
        }catch(SQLException e){
            e.printStackTrace ();
            return false ;
        }
    }

    // Utility method to extract NotificationModel from ResultSet
    private NotificationModel extractNotificationFromResultSet(ResultSet rs)throws SQLException{
        return new NotificationModel(
                rs.getString ("notification_id"),
                rs.getString ("content"),
                rs.getString ("user_id"),
                rs.getBoolean("is_read")
        );
    }
}