package com.oriseus.schedule.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalTime;

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
			} else if (day.getDate().getYear() > year) {
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
			} else if (day.getDate().getMonthValue() > month) {
				break;
			}
		}
		
		return daysOfMonthList;
	}

	//Возвращает конкретный день в месяце
	public Day getDayByYearAndMonth(int year, int month, int day) {
		return getDaysOfMonth(year, month).get(day - 1);
	}
	
	public int getNumberOfDaysInMonth(int year, int month) {
		return getDaysOfMonth(year, month).size();
	}

	//Расписание 5/2
	public void setFiveToTwoSchedule(Day startDay) {
		
		for (Day day : dayLinkedList) {
			if (day.getDate().isEqual(startDay.getDate()) || day.getDate().isAfter(startDay.getDate())) {				
				day.setScheduleType(ScheduleType.FiveToTwo);
				
				if (day.getDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) || day.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
					day.pushDayStatus(DayStatus.DayOff);
				} else {
					day.pushDayStatus(DayStatus.WorkingDay);
					day.setStartWorkTime(LocalTime.of(8, 30));
					day.setEndWorkTime(LocalTime.of(17, 30));
				}
			
			}
		}
	}

	public void setTwoToTwoSchedule(Day startDay, int numberOfDay) {
		for (int i = 0; i < dayLinkedList.size(); i++) {
			if (dayLinkedList.get(i).getDate().isEqual(startDay.getDate()) || dayLinkedList.get(i).getDate().isAfter(startDay.getDate())) {
				dayLinkedList.get(i).setScheduleType(ScheduleType.TwoToTwo);
				if (numberOfDay == 1 || numberOfDay == 2) {
					dayLinkedList.get(i).pushDayStatus(DayStatus.WorkingDay);
					dayLinkedList.get(i).setStartWorkTime(LocalTime.of(8, 30));
					dayLinkedList.get(i).setEndWorkTime(LocalTime.of(20, 30));
					numberOfDay++;
				} else if (numberOfDay == 3 ) {
					dayLinkedList.get(i).pushDayStatus(DayStatus.DayOff);
					numberOfDay++; 
				} else {
					dayLinkedList.get(i).pushDayStatus(DayStatus.DayOff);
					numberOfDay = 1;
				}
			}
		}
	}

	public void setFourToFourSchedule(Day startDay, int numberOfDay) {
		for (int i = 0; i < dayLinkedList.size(); i++) {
			if (dayLinkedList.get(i).getDate().isEqual(startDay.getDate()) || dayLinkedList.get(i).getDate().isAfter(startDay.getDate())) {
				dayLinkedList.get(i).setScheduleType(ScheduleType.FourToFour);
				if (numberOfDay == 1 || numberOfDay == 2) {
					dayLinkedList.get(i).pushDayStatus(DayStatus.WorkingDay);
					dayLinkedList.get(i).setStartWorkTime(LocalTime.of(8, 30));
					dayLinkedList.get(i).setEndWorkTime(LocalTime.of(20, 30));
					numberOfDay++;
				} else if (numberOfDay == 3 || numberOfDay == 4) {
					dayLinkedList.get(i).pushDayStatus(DayStatus.DayOff);
					numberOfDay++; 
				} else if (numberOfDay == 5 || numberOfDay == 6) {
					dayLinkedList.get(i).pushDayStatus(DayStatus.WorkingDay);
					dayLinkedList.get(i).setStartWorkTime(LocalTime.of(20, 30));
					dayLinkedList.get(i).setEndWorkTime(LocalTime.of(8, 30));
					numberOfDay++;
				} else if (numberOfDay == 7) {
					dayLinkedList.get(i).pushDayStatus(DayStatus.DayOff);
					numberOfDay++; 
				} else {
					dayLinkedList.get(i).pushDayStatus(DayStatus.DayOff);
					numberOfDay = 1;
				}
			}
		}
	}

	private void setListOfDays() {
		LocalDate pastDate = LocalDate.of(2020, 1, 1);
		LocalDate futureDate = LocalDate.of(2039, 12, 31);
		
		Day day = new Day(pastDate);
		dayLinkedList.add(day);
		
		int counterDay = 1;
		
		while (!dayLinkedList.getLast().getDate().isEqual(futureDate)) {
			dayLinkedList.add(new Day(day.getDate().plusDays(counterDay)));
			counterDay++;
		}
	}

	public List<Day> getDayLinkedList() {
		return dayLinkedList;
	}

	public void setDayLinkedList(List<Day> dayLinkedList) {
		this.dayLinkedList = dayLinkedList;
	}
}
