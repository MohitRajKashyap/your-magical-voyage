package config;

import java.util.Properties;

public class ApplicationConfig {
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(ApplicationConfig.class.getClassLoader()
                    .getResourceAsStream("application.properties"));
        } catch (Exception e) {
            System.err.println("Error loading application properties: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}