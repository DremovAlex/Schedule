package com.oriseus.schedule.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.oriseus.schedule.controller.ScheduleController;

import Utils.NotDefineValueException;

public class ScheduleObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final LocalTime DAY_START_WORK = LocalTime.of(8, 30);
	private final LocalTime DAY_END_WORK = LocalTime.of(17, 30);
	private final LocalTime DAY_SHIFT_END_WORK = LocalTime.of(20, 30);
	private final LocalTime NIGHT_SHIFT_START_WORK = LocalTime.of(20, 30);
	private final LocalTime NIGHT_SHIFT_END_WORK = LocalTime.of(8, 30);
	
	private List<Day> daysOfYear;
	private int year;
	private ScheduleType scheduleType;
	
	private boolean isFridayShortDay;
	private int notWorkingHoursWorkingDay;
	private int notWorkingHoursShift;
	private int hoursOfWorkDayWork;
	private int hoursOfWorkDayShiftWork;
	
	public ScheduleObject(int year) {
		this.year = year;
		
		daysOfYear = new LinkedList<Day>();
		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		int daysInYear;
		if (calendar.isLeapYear(year)) {
			daysInYear = 366;
		} else {
			daysInYear = 365;
		}
		
		LocalDate localDate = LocalDate.of(year, 1, 1);
		for (int i = 0; i < daysInYear; i++) {
			Day day = new Day(localDate.plusDays(i));
			daysOfYear.add(day);
		}
		
		scheduleType = ScheduleType.NOT_SET;
		
		loadProperties();
	}

	public Day[] getDaysInMonth(int numberOfMonth) {
		YearMonth leapYearMonth = YearMonth.of(getYear(), numberOfMonth);
		int numberDaysInMonth = leapYearMonth.lengthOfMonth();
 
		Day[] daysInMonth = new Day[numberDaysInMonth];

		LocalDate date = LocalDate.of(year, numberOfMonth, 1);
		
		for (int i = date.getDayOfYear() - 1, j = 0; j < daysInMonth.length; i++, j++) {		
			Day day = daysOfYear.get(i);
			daysInMonth[j] = day;
		}		
		
		return daysInMonth;
	}
	
	public LocalDate setSchedule(LocalDate localDate, ScheduleType scheduleType) {
		int count = 1;
		int tempCount = 0;
		this.scheduleType = scheduleType;
		LocalDate nextDayOfWork = null;
		loadProperties();
		switch (scheduleType) {
		case TWO_TO_TWO:
			for (int i = 0; i < daysOfYear.size(); i++) {
				daysOfYear.get(i).setScheduleType(scheduleType);
				if (daysOfYear.get(i).getDay().isEqual(localDate) || daysOfYear.get(i).getDay().isAfter(localDate)) {
					if (count <= 2) {
						daysOfYear.get(i).setDayStatus(DayStatus.WORKING_DAY);
						daysOfYear.get(i).setStartWork(DAY_START_WORK);
						daysOfYear.get(i).setEndWork(DAY_SHIFT_END_WORK);
						daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayShiftWork);
						daysOfYear.get(i).setDayShift(true);						
						tempCount = count;
						count++;					
					} else if (count == 3) {
						daysOfYear.get(i).setDayStatus(DayStatus.DAY_OFF);
						tempCount = count;
						count++;				
					} else {
						daysOfYear.get(i).setDayStatus(DayStatus.DAY_OFF);
						count = 1;
						tempCount = 4;
					}	
				}
			}
		
			for (int i = 6; i >= 0; i--) {
				daysOfYear.get(i).setDayStatus(DayStatus.DAY_OFF);
			}
			
			nextDayOfWork = daysOfYear.get(daysOfYear.size() - 1).getDay().plusDays(5 - tempCount);
			scheduleType = ScheduleType.TWO_TO_TWO;
			return nextDayOfWork;
		case FIVE_TO_TWO:
			for (int i = 0; i < daysOfYear.size(); i++) {
				daysOfYear.get(i).setScheduleType(scheduleType);
				if (daysOfYear.get(i).getDay().isEqual(localDate) || daysOfYear.get(i).getDay().isAfter(localDate)) {
					if (count < 5) {
						daysOfYear.get(i).setDayStatus(DayStatus.WORKING_DAY);
						daysOfYear.get(i).setStartWork(DAY_START_WORK);
						daysOfYear.get(i).setEndWork(DAY_END_WORK);
						daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayWork);
						daysOfYear.get(i).setFiveToTwo(true);
						nextDayOfWork = daysOfYear.get(i).getDay().plusDays(2);
						tempCount = count;
						count++; 
					} else if (count == 5) {
						daysOfYear.get(i).setDayStatus(DayStatus.FRIDAY_WORKING_DAY);
						daysOfYear.get(i).setStartWork(DAY_START_WORK);
						if (isFridayShortDay) {
							daysOfYear.get(i).setEndWork(DAY_END_WORK.minusHours(1));
							daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayWork - 1);
						} else {
							daysOfYear.get(i).setEndWork(DAY_END_WORK);
							daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayWork);
						}
						daysOfYear.get(i).setFiveToTwo(true);
						nextDayOfWork = daysOfYear.get(i).getDay().plusDays(2);
						tempCount = count;
						count++;
					} else if (count == 6) {
						daysOfYear.get(i).setDayStatus(DayStatus.DAY_OFF);
						daysOfYear.get(i).setFiveToTwo(true);
						tempCount = count;
						count++;
					} else {
						daysOfYear.get(i).setDayStatus(DayStatus.DAY_OFF);
						daysOfYear.get(i).setFiveToTwo(true);
						tempCount = count;
						count = 1;
					}
				}
			}

			for (int i = 6; i >= 0; i--) {
				daysOfYear.get(i).setDayStatus(DayStatus.DAY_OFF);
			}
			
			nextDayOfWork = daysOfYear.get(daysOfYear.size() - 1).getDay().plusDays(8 - tempCount);
			scheduleType = ScheduleType.FIVE_TO_TWO;
			return nextDayOfWork;
		case FOUR_TO_FOUR:
			for (int i = 0; i < daysOfYear.size(); i++) {
				daysOfYear.get(i).setScheduleType(scheduleType);
				if (daysOfYear.get(i).getDay().isEqual(localDate) || daysOfYear.get(i).getDay().isAfter(localDate)) {
					if (count == 1 || count == 2) {
						daysOfYear.get(i).setDayStatus(DayStatus.WORKING_DAY);
						daysOfYear.get(i).setStartWork(DAY_START_WORK);
						daysOfYear.get(i).setEndWork(DAY_SHIFT_END_WORK);
						daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayShiftWork);
						daysOfYear.get(i).setDayShift(true);
						tempCount = count;
						count++;
					} else if (count == 3 || count == 4) {
						daysOfYear.get(i).setDayStatus(DayStatus.DAY_OFF);
						count++;
					} else if (count == 5 || count == 6) {
						daysOfYear.get(i).setDayStatus(DayStatus.WORKING_DAY);
						daysOfYear.get(i).setStartWork(NIGHT_SHIFT_START_WORK);
						daysOfYear.get(i).setEndWork(NIGHT_SHIFT_END_WORK);
						daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayShiftWork);
						daysOfYear.get(i).setNightShift(true);
						tempCount = count;
						count++;
					} else if (count == 7) {
						daysOfYear.get(i).setDayStatus(DayStatus.DAY_OFF);
						tempCount = count;
						count++;
					} else {
						daysOfYear.get(i).setDayStatus(DayStatus.DAY_OFF);
						tempCount = count;
						count = 1;
					}
				}
			}
			for (int i = 6; i >= 0; i--) {
				daysOfYear.get(i).setDayStatus(DayStatus.DAY_OFF);
			}
			
			nextDayOfWork = daysOfYear.get(daysOfYear.size() - 1).getDay().plusDays(9 - tempCount);
			scheduleType = ScheduleType.FOUR_TO_FOUR;
			return nextDayOfWork;
		default:
			return nextDayOfWork;
		}
	}
	
	public void cancelSchedule() {
		for (Day day : daysOfYear) {
			day.setDayStatus(DayStatus.NOT_SET);
			day.setStartWork(null);
			day.setEndWork(null);
			day.setHoursOfWork(0);
			day.setHoursOfOvertime(0);
			day.setFiveToTwo(false);
			day.setDayShift(false);
			day.setNightShift(false);
		}
		scheduleType = ScheduleType.NOT_SET;
	}
	
	public void setStartVacation(LocalDate localDate, int dayCount) {
		loadProperties();
		int count = 1;
		for (int i = 0; i < daysOfYear.size(); i++) {
			if (daysOfYear.get(i).getDay().isEqual(localDate) || daysOfYear.get(i).getDay().isAfter(localDate)) {
				if (count <= dayCount) {
					daysOfYear.get(i).setDayStatus(DayStatus.VACATION);
					daysOfYear.get(i).setStartWork(null);
					daysOfYear.get(i).setEndWork(null);
					daysOfYear.get(i).setHoursOfWork(0);
					count++;
				}
			}
		}
	}
	
	public void cancelVacation(LocalDate localDate, Integer dayCount) {
		loadProperties();
		int count = 1;
		for (int i = 0; i < daysOfYear.size(); i++) {
			if (daysOfYear.get(i).getDay().isEqual(localDate) || daysOfYear.get(i).getDay().isAfter(localDate)) {
				if (count <= dayCount && daysOfYear.get(i).peekDayStatus().equals(DayStatus.VACATION)) {
					daysOfYear.get(i).popDayStatus();
					if (daysOfYear.get(i).isFiveToTwo()) {
						daysOfYear.get(i).setStartWork(DAY_START_WORK);
						
						if (daysOfYear.get(i).peekDayStatus().equals(DayStatus.FRIDAY_WORKING_DAY)) {
							daysOfYear.get(i).setEndWork(DAY_END_WORK.minusHours(1));
							daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayWork - 1);
						} else {
							daysOfYear.get(i).setEndWork(DAY_END_WORK);
							daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayWork);
						}
						
					} else if (daysOfYear.get(i).isDayShift()) {
						daysOfYear.get(i).setStartWork(DAY_START_WORK);
						daysOfYear.get(i).setEndWork(DAY_SHIFT_END_WORK);
						daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayShiftWork);
					} else if (daysOfYear.get(i).isNightShift()) {
						daysOfYear.get(i).setStartWork(NIGHT_SHIFT_START_WORK);
						daysOfYear.get(i).setEndWork(NIGHT_SHIFT_END_WORK);
						daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayShiftWork);
					} else {
						daysOfYear.get(i).setStartWork(null);
						daysOfYear.get(i).setEndWork(null);
						daysOfYear.get(i).setHoursOfWork(0);
					}

					count++;
				} else {
					return;
				}
			}
		}
	}
	
	public void setStartSickLeave(LocalDate localDate, int dayCount) {
		int count = 1;
		for (int i = 0; i < daysOfYear.size(); i++) {
			if (daysOfYear.get(i).getDay().isEqual(localDate) || daysOfYear.get(i).getDay().isAfter(localDate)) {
				if (count <= dayCount) {
					daysOfYear.get(i).setDayStatus(DayStatus.SICK_LEAVE);
					daysOfYear.get(i).setStartWork(null);
					daysOfYear.get(i).setEndWork(null);
					daysOfYear.get(i).setHoursOfWork(0);
					count++;
				}
			}
		}
	}
	
	public void cancelSickLeave(LocalDate localDate, int dayCount) {
		loadProperties();
		int count = 1;
		for (int i = 0; i < daysOfYear.size(); i++) {
			if (daysOfYear.get(i).getDay().isEqual(localDate) || daysOfYear.get(i).getDay().isAfter(localDate)) {
				if (count <= dayCount && daysOfYear.get(i).peekDayStatus().equals(DayStatus.SICK_LEAVE)) {
					daysOfYear.get(i).popDayStatus();
					if (daysOfYear.get(i).isFiveToTwo()) {
						daysOfYear.get(i).setStartWork(DAY_START_WORK);
						
						if (daysOfYear.get(i).peekDayStatus().equals(DayStatus.FRIDAY_WORKING_DAY)) {
							daysOfYear.get(i).setEndWork(DAY_END_WORK.minusHours(1));
							daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayWork - 1);
						} else {
							daysOfYear.get(i).setEndWork(DAY_END_WORK);
							daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayWork);
						}
						
					} else if (daysOfYear.get(i).isDayShift()) {
						daysOfYear.get(i).setStartWork(DAY_START_WORK);
						daysOfYear.get(i).setEndWork(DAY_SHIFT_END_WORK);
						daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayShiftWork);
					} else if (daysOfYear.get(i).isNightShift()) {
						daysOfYear.get(i).setStartWork(NIGHT_SHIFT_START_WORK);
						daysOfYear.get(i).setEndWork(NIGHT_SHIFT_END_WORK);
						daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayShiftWork);
					} else {
						daysOfYear.get(i).setStartWork(null);
						daysOfYear.get(i).setEndWork(null);
						daysOfYear.get(i).setHoursOfWork(0);
					}
					count++;
				} else {
					return;
				}
			}
		}
	}
	
	public void setStartAbsenteism(LocalDate localDate, int dayCount) {
		int count = 1;
		for (int i = 0; i < daysOfYear.size(); i++) {
			if (daysOfYear.get(i).getDay().isEqual(localDate) || daysOfYear.get(i).getDay().isAfter(localDate)) {
				if (count <= dayCount) {
					daysOfYear.get(i).setDayStatus(DayStatus.ABSENTEEISM);
					daysOfYear.get(i).setStartWork(null);
					daysOfYear.get(i).setEndWork(null);
					daysOfYear.get(i).setHoursOfWork(0);
					count++;
				}
			}
		}
	}
	
	public void cancelAbsenteism(LocalDate localDate, int dayCount) {
		loadProperties();
		int count = 1;
		for (int i = 0; i < daysOfYear.size(); i++) {
			if (daysOfYear.get(i).getDay().isEqual(localDate) || daysOfYear.get(i).getDay().isAfter(localDate)) {
				if (count <= dayCount && daysOfYear.get(i).peekDayStatus().equals(DayStatus.ABSENTEEISM)) {
					daysOfYear.get(i).popDayStatus();
					if (daysOfYear.get(i).isFiveToTwo()) {
						daysOfYear.get(i).setStartWork(DAY_START_WORK);
						
						if (daysOfYear.get(i).peekDayStatus().equals(DayStatus.FRIDAY_WORKING_DAY)) {
							daysOfYear.get(i).setEndWork(DAY_END_WORK.minusHours(1));
							daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayWork - 1);
						} else {
							daysOfYear.get(i).setEndWork(DAY_END_WORK);
							daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayWork);
						}
						
					} else if (daysOfYear.get(i).isDayShift()) {
						daysOfYear.get(i).setStartWork(DAY_START_WORK);
						daysOfYear.get(i).setEndWork(DAY_SHIFT_END_WORK);
						daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayShiftWork);
					} else if (daysOfYear.get(i).isNightShift()) {
						daysOfYear.get(i).setStartWork(NIGHT_SHIFT_START_WORK);
						daysOfYear.get(i).setEndWork(NIGHT_SHIFT_END_WORK);
						daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayShiftWork);
					} else {
						daysOfYear.get(i).setStartWork(null);
						daysOfYear.get(i).setEndWork(null);
						daysOfYear.get(i).setHoursOfWork(0);
					}

					count++;
				} else {
					return;
				}
			}
		}
	}
	
	public void setOvertime(LocalDate localDate, int hoursCount) throws NotDefineValueException {
		for (int i = 0; i < daysOfYear.size(); i++) {
			if (daysOfYear.get(i).getDay().isEqual(localDate)) {
				if (daysOfYear.get(i).peekDayStatus().equals(DayStatus.WORKING_DAY) || daysOfYear.get(i).peekDayStatus().equals(DayStatus.FRIDAY_WORKING_DAY)) {
					daysOfYear.get(i).setDayStatus(DayStatus.OVER_TIME);
					daysOfYear.get(i).setHoursOfOvertime(hoursCount);
					daysOfYear.get(i).setEndWork(daysOfYear.get(i).getEndWork().plusHours(hoursCount));
					break;
				} else {
					throw new NotDefineValueException();
				}
			}
		}
	}
	
	public void cancelOvertime(LocalDate localDate) {
		loadProperties();
		for (int i = 0; i < daysOfYear.size(); i++) {
			if (daysOfYear.get(i).getDay().isEqual(localDate)) {
				daysOfYear.get(i).popDayStatus();
				daysOfYear.get(i).setEndWork(daysOfYear.get(i).getEndWork().minusHours(daysOfYear.get(i).getHoursOfOvertime()));				
				daysOfYear.get(i).setHoursOfOvertime(0);
				break;
			}
		}
	}
	/////////////////ПОЧИНИТЬ ВРЕМЯ И ДОБАВИТЬ ОБЕД!\\\\\\\\\\\\\\\\\\
	public void changeDay(LocalDate localDate, Day day) {
		loadProperties();
		for (int i = 0; i < daysOfYear.size(); i++) {
			if (daysOfYear.get(i).getDay().isEqual(localDate)) {
				daysOfYear.get(i).setStartWork(day.getStartWork());
				daysOfYear.get(i).setEndWork(day.getEndWork());
				if (day.getHoursOfWork() < 0) {
					daysOfYear.get(i).setHoursOfWork(-day.getHoursOfWork());
				} else {
					daysOfYear.get(i).setHoursOfWork(day.getHoursOfWork());

				}			
				daysOfYear.get(i).setDayStatus(day.peekDayStatus());
				
				if (day.getScheduleType().equals(ScheduleType.FIVE_TO_TWO) && day.peekDayStatus().equals(DayStatus.WORKING_DAY)) {
					daysOfYear.get(i).setFiveToTwo(true);
				}
				if (day.getScheduleType().equals(ScheduleType.TWO_TO_TWO) && day.peekDayStatus().equals(DayStatus.WORKING_DAY)) {
					daysOfYear.get(i).setDayShift(true);
				}
				if (day.getScheduleType().equals(ScheduleType.FOUR_TO_FOUR) && day.peekDayStatus().equals(DayStatus.WORKING_DAY)) {
					if (day.getStartWork().getHour() < 20) {
						daysOfYear.get(i).setDayShift(true);
					} else {
						daysOfYear.get(i).setNightShift(true);
					}
				}
				break;
			}
		}
	}
	
	public List<Day> getDaysOfYear() {
		return daysOfYear;
	}

	public void setDaysOfYear(List<Day> daysOfYear) {
		this.daysOfYear = daysOfYear;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public ScheduleType getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(ScheduleType scheduleType) {
		this.scheduleType = scheduleType;
	}
    
	private void loadProperties() {
    	Properties dayProperties = new Properties();
    	try {
			dayProperties.load(new FileInputStream(ScheduleController.getPathToAplication() + 
					File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "day.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
 		
    	isFridayShortDay = Boolean.valueOf(dayProperties.getProperty("isFridayShortDay"));
    	notWorkingHoursWorkingDay = Integer.valueOf(dayProperties.getProperty("notWorkingHoursWorkingDay"));
    	notWorkingHoursShift = Integer.valueOf(dayProperties.getProperty("notWorkingHoursShift"));
    	
    	hoursOfWorkDayWork = (DAY_END_WORK.getHour() - DAY_START_WORK.getHour()) - notWorkingHoursWorkingDay;
    	hoursOfWorkDayShiftWork = (DAY_SHIFT_END_WORK.getHour() - DAY_START_WORK.getHour()) - notWorkingHoursShift;
    }

	public void changeAfterReloadSettings() {
		loadProperties();
		for (int i = 0; i < daysOfYear.size(); i++) {
			if ((daysOfYear.get(i).peekDayStatus().equals(DayStatus.WORKING_DAY) && daysOfYear.get(i).isDayShift()) ||
					(daysOfYear.get(i).peekDayStatus().equals(DayStatus.WORKING_DAY) && daysOfYear.get(i).isNightShift())) {
				daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayShiftWork);
			} else if (daysOfYear.get(i).peekDayStatus().equals(DayStatus.WORKING_DAY) && daysOfYear.get(i).isFiveToTwo()) {
				daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayWork);
			} else if (daysOfYear.get(i).peekDayStatus().equals(DayStatus.FRIDAY_WORKING_DAY) && isFridayShortDay) {
				daysOfYear.get(i).setEndWork(DAY_END_WORK.minusHours(1));
				daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayWork - 1);
			} else if (daysOfYear.get(i).peekDayStatus().equals(DayStatus.FRIDAY_WORKING_DAY) && !isFridayShortDay) {
				daysOfYear.get(i).setEndWork(DAY_END_WORK);
				daysOfYear.get(i).setHoursOfWork(hoursOfWorkDayWork);
			}
		}
	}
}














