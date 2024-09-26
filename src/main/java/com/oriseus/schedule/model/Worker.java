package com.oriseus.schedule.model;

public class Worker {

	private String name;
	private String surname;
	private String secondName;
	private String phoneNumber;
	private ScheduleObject scheduleObject;
	private boolean isSelected;
	
	public Worker(String name, String surname, String secondName) {
		this.name = name;
		this.surname = surname;
		this.secondName = secondName;
		scheduleObject = new ScheduleObject();
	}
	
	public Worker(String name, String surname, String secondName, String phoneNumber) {
		this.name = name;
		this.surname = surname;
		this.secondName = secondName;
		this.phoneNumber = phoneNumber;
		scheduleObject = new ScheduleObject();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public ScheduleObject getScheduleObject() {
		return scheduleObject;
	}

	public void setScheduleObject(ScheduleObject scheduleObject) {
		this.scheduleObject = scheduleObject;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isSelected() {
		return isSelected;
	}
}
