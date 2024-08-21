package com.oriseus.schedule.model;

import java.io.Serializable;

import Utils.NotDefineValueException;

public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	private String secondName;
	private String phoneNumber;
	private ScheduleObject[] scheduleObjects;
		
	public Person(String name, String surname, String secondName) {
		this.name = name;
		this.surname = surname;
		this.secondName = secondName;
		this.phoneNumber = "";
		scheduleObjects = createScheduleObjects();
	}
	
	public Person(String name, String surname, String secondName, String phoneNumber) {
		this.name = name;
		this.surname = surname;
		this.secondName = secondName;
		this.phoneNumber = phoneNumber;
		scheduleObjects = createScheduleObjects();
	}
	
	private ScheduleObject[] createScheduleObjects() {
		ScheduleObject[] scheduleObjects = new ScheduleObject[10];
		for (int i = 0; i < scheduleObjects.length; i++) {
			scheduleObjects[i] = new ScheduleObject(2024 + i);
		}
		return scheduleObjects;
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
	
	public ScheduleObject getScheduleObject(int year) throws NotDefineValueException {
		if (year > 2034) throw new NotDefineValueException();
		return scheduleObjects[year - 2024];
	}

	public void setScheduleObject(ScheduleObject[] scheduleObjects) {
		this.scheduleObjects = scheduleObjects;
	}
}
