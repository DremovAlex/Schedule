package com.oriseus.schedule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.oriseus.schedule.model.WorkingPlace;
import com.oriseus.schedule.utils.FileHamdler;

public class Company {
    
    private static Company company;
    private List<WorkingPlace> workingPlaces;

    private Company() {
        workingPlaces = new ArrayList<>();
    }

    public static Company getInstants() {
        if (company == null) {
            company = new Company();
            return company;
        } else {
            return company;
        }
    }

    public void addNewWorkingPlace(String name) {
        workingPlaces.add(new WorkingPlace(name));
    }

    public void changeNameWorkingPlaceByName(String oldName, String newName) {
        for (WorkingPlace workingPlace : workingPlaces) {
            if (workingPlace.getName().equals(oldName)) {
                workingPlace.setName(newName);
                break;
            }
        }
    }

    public void deleteWorkingPlaceByName(String name) {
        for (int i = 0; i < workingPlaces.size(); i++) {
            if (workingPlaces.get(i).getName().equals(name)) {
                workingPlaces.remove(i);
                break;
            }
        }
    }

    public List<WorkingPlace> getWorkingPlasesList() {
        return workingPlaces;
    }

    public WorkingPlace getWorkingPlace(String name) {
        for (WorkingPlace workingPlace : workingPlaces) {
            if (workingPlace.getName().equals(name)) {
                return workingPlace;
            }
        }
        return null;
    }

    public void save() {
        FileHamdler.getInstants().saveFile(workingPlaces);
    }

    public void load() {
        workingPlaces = FileHamdler.getInstants().loadFile();
    }

    public void choice(File file) throws FileNotFoundException, IOException {
        FileHamdler.getInstants().choiceFile(file);
    }

    public void create(File directory) throws IOException {
        FileHamdler.getInstants().createFile(directory);
    }
}
