package com.oriseus.schedule.utils;

import java.io.*;
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

    public void saveFile(List<WorkingPlace> list) throws FileNotFoundException, IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(SettingHundler.getInstants().loadSavedFilePath()));
 
        objectOutputStream.writeObject(list);
        objectOutputStream.close();
    }

    public List<WorkingPlace> loadFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(SettingHundler.getInstants().loadSavedFilePath()));
 
        @SuppressWarnings("unchecked")
        List<WorkingPlace> list = (List<WorkingPlace>) objectInputStream.readObject();
        
        objectInputStream.close();

        return list;
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
