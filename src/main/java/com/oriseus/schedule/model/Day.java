package com.oriseus.schedule.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Stack;

public class Day implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private LocalDate day;
	private int hoursOfWork;
	private int hoursOfOvertime;
	private LocalTime startWork;
	private LocalTime endWork;
	private DayStatus dayStatus;
	private boolean isFiveToTwo;
	private boolean isDayShift;
	private boolean isNightShift;
	private Stack<DayStatus> dayStatusStack;
	private ScheduleType scheduleType;
	
	public Day(LocalDate day) {
		this.day = day;
		dayStatusStack = new Stack<DayStatus>();
		dayStatus = DayStatus.NOT_SET;
		dayStatusStack.push(dayStatus);
		isFiveToTwo = false;
		isDayShift = false;
		isNightShift = false;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public int getHoursOfWork() {
		return hoursOfWork;
	}

	public void setHoursOfWork(int hoursOfWork) {
		this.hoursOfWork = hoursOfWork;
	}
	
	public LocalTime getStartWork() {
		return startWork;
	}

	public void setStartWork(LocalTime startWork) {
		this.startWork = startWork;
	}

	public LocalTime getEndWork() {
		return endWork;
	}

	public void setEndWork(LocalTime endWork) {
		this.endWork = endWork;
	}

	public int getHoursOfOvertime() {
		return hoursOfOvertime;
	}

	public void setHoursOfOvertime(int hoursOfOvertime) {
		this.hoursOfOvertime = hoursOfOvertime;
	}

	public DayStatus peekDayStatus() {
		return dayStatusStack.peek();
	}
	
	public DayStatus popDayStatus() {
		return dayStatusStack.pop();
	}

	public void setDayStatus(DayStatus dayStatus) {
		dayStatusStack.push(dayStatus);
	}

	public boolean isFiveToTwo() {
		return isFiveToTwo;
	}

	public void setFiveToTwo(boolean isFiveToTwo) {
		this.isFiveToTwo = isFiveToTwo;
	}

	public boolean isDayShift() {
		return isDayShift;
	}

	public void setDayShift(boolean isDayShift) {
		this.isDayShift = isDayShift;
	}

	public boolean isNightShift() {
		return isNightShift;
	}

	public void setNightShift(boolean isNightShift) {
		this.isNightShift = isNightShift;
	}

	public ScheduleType getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(ScheduleType scheduleType) {
		this.scheduleType = scheduleType;
	}
	
}
