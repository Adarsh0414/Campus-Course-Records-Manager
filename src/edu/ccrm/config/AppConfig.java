package edu.ccrm.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AppConfig {

    private static final AppConfig INSTANCE = new AppConfig();
    private final Properties properties = new Properties();

    private AppConfig() {
        // Load properties from a config file
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                // Set default values
                properties.setProperty("data.folder", "data");
                properties.setProperty("backup.folder", "backup");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public Path getDataFolderPath() {
        return Paths.get(properties.getProperty("data.folder", "data"));
    }

    public Path getBackupFolderPath() {
        return Paths.get(properties.getProperty("backup.folder", "backup"));
    }
}
