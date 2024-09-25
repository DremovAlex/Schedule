package com.oriseus.schedule;

import java.util.ArrayList;
import java.util.List;

import com.oriseus.schedule.model.WorkingPlace;

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
}
