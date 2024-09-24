package com.oriseus.schedule.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ScheduleObject {

	List<Day> dayLinkedList;
	
	public ScheduleObject() {
		dayLinkedList = new LinkedList<Day>();
		setListOfDays();		
	}
	
	// Возвращает список с днями года
	public List<Day> getDaysOfYear(int year) {
		
		List<Day> daysOfYearList = new LinkedList<Day>();
		
		for (Day day : dayLinkedList) {
			if (day.getDate().getYear() == year) {
				daysOfYearList.add(day);
			} else if (day.getDate().getYear() < year) {
				break;
			}
		}
		
		return daysOfYearList;
	}
	
	// Возвращает список с днями месяца
	public List<Day> getDaysOfMonth(int year, int month) {
		
		List<Day> daysOfYearList = getDaysOfYear(year);
		List<Day> daysOfMonthList = new LinkedList<Day>();
		
		for (Day day : daysOfYearList) {
			if (day.getDate().getMonthValue() == month) {
				daysOfMonthList.add(day);
			} else if (day.getDate().getMonthValue() < month) {
				break;
			}
		}
		
		return daysOfMonthList;
	}
	
	private void setListOfDays() {
		LocalDate pastDate = LocalDate.of(2020, 1, 1);
		LocalDate futureDate = LocalDate.of(2039, 12, 31);
		
		Day day = new Day(pastDate);
		dayLinkedList.add(day);
		
		int counterDay = 1;
		
		while (dayLinkedList.getLast().getDate().isEqual(futureDate)) {
			dayLinkedList.add(new Day(day.getDate().plusDays(counterDay)));
		}
	}

	public List<Day> getDayLinkedList() {
		return dayLinkedList;
	}

	public void setDayLinkedList(List<Day> dayLinkedList) {
		this.dayLinkedList = dayLinkedList;
	}
}
