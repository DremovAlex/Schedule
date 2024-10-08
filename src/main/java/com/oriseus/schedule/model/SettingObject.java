package com.oriseus.schedule.model;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import com.oriseus.schedule.utils.SettingHundler;

public class SettingObject implements Serializable {

    private int notWorkingHoursFiveToTwo;
    private int notWorkingHoursTwoToTwo;
    private int overTimeHours;
    private boolean isFridayShortDay;
    private boolean isOverTimeShift;
    private boolean isUserChanged;

    public SettingObject() throws FileNotFoundException, IOException {
        
    }

    public void setNotWorkingHoursFiveToTwo(int notWorkingHoursFiveToTwo) {
        this.notWorkingHoursFiveToTwo = notWorkingHoursFiveToTwo;
    }
    public int getNotWorkingHoursFiveToTwo() {
        return notWorkingHoursFiveToTwo;
    }

    public void setNotWorkingHoursTwoToTwo(int notWorkingHoursTwoToTwo) {
        this.notWorkingHoursTwoToTwo = notWorkingHoursTwoToTwo;
    }
    public int getNotWorkingHoursTwoToTwo() {
        return notWorkingHoursTwoToTwo;
    }
    public void setOverTimeHours(int overTimeHours) {
        this.overTimeHours = overTimeHours;
    }
    public int getOverTimeHours() {
        return overTimeHours;
    }
    public void setFridayShortDay(boolean isFridayShortDay) {
        this.isFridayShortDay = isFridayShortDay;
    }
    public boolean isFridayShortDay() {
        return isFridayShortDay;
    }
    public void setOverTimeShift(boolean isOverTimeShift) {
        this.isOverTimeShift = isOverTimeShift;
    }
    public boolean isOverTimeShift() {
        return isOverTimeShift;
    }
    public void setUserChanged(boolean isUserChanged) {
        this.isUserChanged = isUserChanged;
    }
    public boolean isUserChanged() {
        return isUserChanged;
    }
}
