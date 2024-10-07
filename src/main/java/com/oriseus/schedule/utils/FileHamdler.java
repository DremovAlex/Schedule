package com.oriseus.schedule.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.oriseus.schedule.model.WorkingPlace;

public class FileHamdler {

    private static FileHamdler fileHamdler;
    private File savedFile;

    private FileHamdler() {
    }

    public static FileHamdler getInstants() {
        if (fileHamdler != null) {
            return fileHamdler;
        } else {
            fileHamdler = new FileHamdler();
            return fileHamdler;
        }
    }

    public void saveFile(List<WorkingPlace> list) {
        //Добавить сериализацию
    }

    public List<WorkingPlace> loadFile() {
        //Добавить десериализацию
        return null;
    }

    public void createFile(File directory) throws FileNotFoundException, IOException {
        SettingHundler.getInstants().saveSavedFilePath(directory + "\\SaveFile");
        savedFile = new File(directory + "\\SaveFile");
        if (!savedFile.exists()) {
            savedFile.createNewFile();
        }
    }

    public void choiceFile(File file) throws FileNotFoundException, IOException {
        SettingHundler.getInstants().saveSavedFilePath(file.getAbsolutePath());
        savedFile = file;
    }
}
