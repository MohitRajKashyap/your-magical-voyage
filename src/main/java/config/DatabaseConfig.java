package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/?user=root";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "@Mohit21";

    private static DatabaseConfig instance;
    private Connection connection;

    private DatabaseConfig() {
        initializeConnection();
    }

    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    private void initializeConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("useSSL", "false");
            properties.setProperty("serverTimezone", "UTC");
            properties.setProperty("characterEncoding", "UTF-8");

            connection = DriverManager.getConnection(URL, properties);
            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            initializeConnection();
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}