package com.oriseus.schedule.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class SettingHundler {

    private static SettingHundler settingHundler;
    private Properties properties;

    private SettingHundler() {
        properties = new Properties();
    }

    public static SettingHundler getInstants() {
        if (settingHundler != null) {
            return settingHundler;
        } else {
            settingHundler = new SettingHundler();
            return settingHundler;
        }
    }

    private Properties getProperties() {
        return properties;
    }

    private String getPathToProperties() {
        Path resourceDirectory = Paths.get("src","main", "resources");
        return resourceDirectory.toFile().getAbsolutePath() + "\\properties\\";
    }

    public void saveSavedFilePath(String path) throws FileNotFoundException, IOException {       
        File propertiesFile = new File(getPathToProperties() + "file.properties");
        
        properties.load(new FileInputStream(propertiesFile));
        properties.setProperty("pathToSavedFile", path);
        properties.store(new FileWriter(propertiesFile), "");

    }

    public String loadSavedFilePath() {
        return null;
    }
}
