package dao;

import config.DatabaseConfig;
import models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean createUser(User user) {
        String sql = "INSERT INTO users (name, email, password, role, phone, address, date_of_birth) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getAddress());
            stmt.setDate(7, user.getDateOfBirth());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<User> getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting user by ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting user by email: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
        }

        return users;
    }

    @Override
    public List<User> getUsersByRole(String role) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting users by role: " + e.getMessage());
        }

        return users;
    }

    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, role = ?, phone = ?, address = ?, date_of_birth = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setDate(6, user.getDateOfBirth());
            stmt.setString(7, user.getStatus());
            stmt.setInt(8, user.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateUserStatus(int userId, String status) {
        String sql = "UPDATE users SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating user status: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean changePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error changing password: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ? AND password = ? AND status = 'ACTIVE'";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }

        return false;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setDateOfBirth(rs.getDate("date_of_birth"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        user.setStatus(rs.getString("status"));
        return user;
    }
}