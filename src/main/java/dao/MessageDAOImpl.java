package dao;

import config.DatabaseConfig;
import models.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements MessageDAO {

    @Override
    public boolean createMessage(Message message) {
        String sql = "INSERT INTO messages (from_user_id, to_user_id, subject, message, message_type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, message.getFromUserId());
            stmt.setInt(2, message.getToUserId());
            stmt.setString(3, message.getSubject());
            stmt.setString(4, message.getMessage());
            stmt.setString(5, message.getMessageType());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error creating message: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Message> getMessagesByUserId(int userId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT m.*, u1.name as from_user_name, u2.name as to_user_name " +
                "FROM messages m " +
                "LEFT JOIN users u1 ON m.from_user_id = u1.id " +
                "LEFT JOIN users u2 ON m.to_user_id = u2.id " +
                "WHERE m.from_user_id = ? OR m.to_user_id = ? " +
                "ORDER BY m.created_at DESC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                messages.add(mapResultSetToMessage(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting messages by user ID: " + e.getMessage());
        }

        return messages;
    }

    @Override
    public List<Message> getUnreadMessagesByUserId(int userId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT m.*, u1.name as from_user_name, u2.name as to_user_name " +
                "FROM messages m " +
                "LEFT JOIN users u1 ON m.from_user_id = u1.id " +
                "LEFT JOIN users u2 ON m.to_user_id = u2.id " +
                "WHERE m.to_user_id = ? AND m.is_read = FALSE " +
                "ORDER BY m.created_at DESC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                messages.add(mapResultSetToMessage(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting unread messages: " + e.getMessage());
        }

        return messages;
    }

    @Override
    public boolean updateMessageReadStatus(int messageId, boolean isRead) {
        String sql = "UPDATE messages SET is_read = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isRead);
            stmt.setInt(2, messageId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating message read status: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteMessage(int messageId) {
        String sql = "DELETE FROM messages WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, messageId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting message: " + e.getMessage());
            return false;
        }
    }

    @Override
    public int getUnreadMessageCount(int userId) {
        String sql = "SELECT COUNT(*) as unread_count FROM messages WHERE to_user_id = ? AND is_read = FALSE";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("unread_count");
            }

        } catch (SQLException e) {
            System.err.println("Error getting unread message count: " + e.getMessage());
        }

        return 0;
    }

    private Message mapResultSetToMessage(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setId(rs.getInt("id"));
        message.setFromUserId(rs.getInt("from_user_id"));
        message.setToUserId(rs.getInt("to_user_id"));
        message.setFromUserName(rs.getString("from_user_name"));
        message.setToUserName(rs.getString("to_user_name"));
        message.setSubject(rs.getString("subject"));
        message.setMessage(rs.getString("message"));
        message.setMessageType(rs.getString("message_type"));
        message.setRead(rs.getBoolean("is_read"));
        message.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return message;
    }
}