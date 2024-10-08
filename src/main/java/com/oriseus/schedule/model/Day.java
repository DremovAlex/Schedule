package com.oriseus.schedule.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Stack;
import java.io.Serializable;

public class Day implements Serializable {

	private LocalDate date;
	
	private Stack<DayStatus> stackDayStatus;
	
	private LocalTime startWorkTime;
	private LocalTime endWorkTime;

	private ScheduleType scheduleType;
	
	public Day(LocalDate date) {
		this.date = date;
		
		stackDayStatus = new Stack<DayStatus>();
		stackDayStatus.push(DayStatus.NotSet);
		
		scheduleType = ScheduleType.NotSet;
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
}
