package com.oriseus.schedule.model;

public enum DayStatus {
	WORKING_DAY, //рабочий день
	FRIDAY_WORKING_DAY, //пятничный рабочий день
	DAY_OFF, //выходной
	SICK_LEAVE, //больничный
	VACATION, //отпуск
	ABSENTEEISM, //прогул
	OVER_TIME, //переработка
	NOT_SET //не назначен
}
