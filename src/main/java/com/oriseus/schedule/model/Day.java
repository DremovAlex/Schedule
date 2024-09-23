package com.oriseus.schedule.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Stack;

public class Day {

	private LocalDate date;
	
	private Stack<DayStatus> stackDayStatus;
	
	private LocalDateTime startWorkTime;
	private LocalDateTime endWorkTime;
	
	public Day(LocalDate date) {
		this.date = date;
		stackDayStatus = new Stack<DayStatus>();
		stackDayStatus.push(DayStatus.DayOff);
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDateTime getStartWorkTime() {
		return startWorkTime;
	}

	public void setStartWorkTime(LocalDateTime startWorkTime) {
		this.startWorkTime = startWorkTime;
	}

	public LocalDateTime getEndWorkTime() {
		return endWorkTime;
	}

	public void setEndWorkTime(LocalDateTime endWorkTime) {
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
}
