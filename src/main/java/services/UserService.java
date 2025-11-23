package services;

import dao.UserDAO;
import dao.UserDAOImpl;
import models.User;
import java.util.List;
import java.util.Optional;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    public boolean registerUser(User user) {
        if (userDAO.getUserByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        return userDAO.createUser(user);
    }

    public Optional<User> loginUser(String email, String password) {
        if (userDAO.authenticateUser(email, password)) {
            return userDAO.getUserByEmail(email);
        }
        return Optional.empty();
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public List<User> getUsersByRole(String role) {
        return userDAO.getUsersByRole(role);
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    public boolean changeUserStatus(int userId, String status) {
        return userDAO.updateUserStatus(userId, status);
    }

    public Optional<User> getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public Optional<User> getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }
}