package dao;

import models.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    boolean createUser(User user);
    Optional<User> getUserById(int id);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    List<User> getUsersByRole(String role);
    boolean updateUser(User user);
    boolean deleteUser(int id);
    boolean updateUserStatus(int userId, String status);
    boolean changePassword(int userId, String newPassword);
    boolean authenticateUser(String email, String password);
}