package com.oriseus.schedule.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Stack;

import com.oriseus.schedule.utils.SettingHundler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

public class Day implements Serializable {

	private LocalDate date;
	
	private Stack<DayStatus> stackDayStatus;
	
	private LocalTime startWorkTime;
	private LocalTime endWorkTime;

	private ScheduleType scheduleType;
	private SettingObject settingObject;
	
	public Day(LocalDate date) throws FileNotFoundException, IOException {
		this.date = date;
		
		stackDayStatus = new Stack<DayStatus>();
		stackDayStatus.push(DayStatus.NotSet);
		
		scheduleType = ScheduleType.NotSet;

		settingObject = SettingHundler.getInstants().loadDaySettings();
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartWorkTime() {
		return startWorkTime;
	}

	public void setStartWorkTime(LocalTime startWorkTime) {
		this.startWorkTime = startWorkTime;
	}

	public LocalTime getEndWorkTime() {
		return endWorkTime;
	}

	public void setEndWorkTime(LocalTime endWorkTime) {
		this.endWorkTime = endWorkTime;
	}
	
	public DayStatus peekDayStatus() {
		return stackDayStatus.peek();
	}
	
	public DayStatus popDayStatus() {
		return stackDayStatus.pop();
	}
	
	public void pushDayStatus(DayStatus dayStatus) {
		stackDayStatus.push(dayStatus);
	}
	
	public void setScheduleType(ScheduleType scheduleType) {
		this.scheduleType = scheduleType;
	}
	
	public ScheduleType getScheduleType() {
		return scheduleType;
	}

	public void setSettingObject(SettingObject settingObject) {
		this.settingObject = settingObject;
	}

	public SettingObject getSettingObject() {
		return settingObject;
	}

    public int getHoursOfWork() {
		int minusHours = 0;
		int hours;
		if (scheduleType.equals(ScheduleType.FiveToTwo)) {
			minusHours = settingObject.getNotWorkingHoursFiveToTwo();
		} else if (scheduleType.equals(ScheduleType.TwoToTwo) || scheduleType.equals(ScheduleType.FourToFour)) {
			minusHours = settingObject.getNotWorkingHoursTwoToTwo();
		}

		hours = (endWorkTime.getHour() - startWorkTime.getHour()) - minusHours;

		return hours;
    }
}
