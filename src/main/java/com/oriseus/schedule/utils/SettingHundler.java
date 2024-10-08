package com.oriseus.schedule.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.oriseus.schedule.model.SettingObject;

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

    public String loadSavedFilePath() throws FileNotFoundException, IOException {
        File propertiesFile = new File(getPathToProperties() + "file.properties");
        
        properties.load(new FileInputStream(propertiesFile));
        String path = properties.getProperty("pathToSavedFile");

        return path;
    }

    public SettingObject loadDaySettings() throws FileNotFoundException, IOException {
        File propertiesFile = new File(getPathToProperties() + "daySettings.properties");
        
        properties.load(new FileInputStream(propertiesFile));
        int notWorkingHoursFiveToTwo = Integer.valueOf(properties.getProperty("notWorkingHoursFiveToTwo"));
        int notWorkingHoursTwoToTwo = Integer.valueOf(properties.getProperty("notWorkingHoursTwoToTwo"));
        boolean isFridayShortDay = Boolean.valueOf(properties.getProperty("isFridayShortDay"));

        SettingObject settingObject = new SettingObject();
        settingObject.setNotWorkingHoursFiveToTwo(notWorkingHoursFiveToTwo);
        settingObject.setNotWorkingHoursTwoToTwo(notWorkingHoursTwoToTwo);
        settingObject.setFridayShortDay(isFridayShortDay);
        return settingObject;
    }

    public void saveDaySettings(SettingObject settingObject) throws FileNotFoundException, IOException {
        File propertiesFile = new File(getPathToProperties() + "daySettings.properties");
        
        properties.load(new FileInputStream(propertiesFile));
        
        properties.setProperty("notWorkingHoursFiveToTwo", String.valueOf(settingObject.getNotWorkingHoursFiveToTwo()));
        properties.setProperty("notWorkingHoursTwoToTwo", String.valueOf(settingObject.getNotWorkingHoursTwoToTwo()));
        properties.setProperty("isFridayShortDay", String.valueOf(settingObject.isFridayShortDay()));

        properties.store(new FileWriter(propertiesFile), "");
    }
}
