package com.oriseus.schedule.controller;

import java.time.LocalTime;

import com.oriseus.schedule.model.Day;
import com.oriseus.schedule.model.DayStatus;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChangeDayController {
	
	@FXML
	Text label;
	@FXML
	Text labelStartWorkText = new Text();
	@FXML
	Text hoursStartWork;
	@FXML
	Text minutesStartWork;
	@FXML
	Text labelEndWorkText;
	@FXML
	Text hoursEndWork;
	@FXML
	Text minutesEndWork;
	@FXML
	Text dayStatus;
	
	@FXML
	ChoiceBox<Integer> hoursStartWorkChoiceBox;
	@FXML
	ChoiceBox<Integer> minutesStartWorkChoiceBox;
	@FXML
	ChoiceBox<Integer> hoursEndWorkChoiceBox;
	@FXML
	ChoiceBox<Integer> minutesEndWorkChoiceBox;
	@FXML
	ChoiceBox<String> dayStatusChoiceBox;
	
	@FXML
	Button okButton;
	@FXML
	Button cancelButton;
	
	private Day day;
	
    @FXML
    public void initialize() {
    	
    	day = ScheduleController.tempDay;
    	
    	label.setText(ScheduleController.tempDay.getDay().getDayOfMonth() + " " + ScheduleController.tempDay.getDay().getMonth() + " " + ScheduleController.tempDay.getDay().getYear());
    	labelStartWorkText.setText("Начало рабочего дня");
    	hoursStartWork.setText("Часы");
    	minutesStartWork.setText("Минуты");
    	labelEndWorkText.setText("Конец рабочего дня");
    	hoursEndWork.setText("Часы");
    	minutesEndWork.setText("Минуты");
    	dayStatus.setText("Статус дня");
    	
    	getHours();
    	getMinutes();
    	getDayStatuses();
    	
    	if (ScheduleController.tempDay.getStartWork() != null) {
    		hoursStartWorkChoiceBox.setValue(ScheduleController.tempDay.getStartWork().getHour());
    		hoursEndWorkChoiceBox.setValue(ScheduleController.tempDay.getEndWork().getHour());
    		minutesStartWorkChoiceBox.setValue(ScheduleController.tempDay.getEndWork().getMinute());
    		minutesEndWorkChoiceBox.setValue(ScheduleController.tempDay.getEndWork().getMinute());
    	
    		switch (day.peekDayStatus()) {
    			case WORKING_DAY:
    				dayStatusChoiceBox.setValue("Рабочий день");
    				break;
    			case OVER_TIME:
    				dayStatusChoiceBox.setValue("Переработка");
    				break;
    			case DAY_OFF:
    				dayStatusChoiceBox.setValue("Выходной");
    				break;
    			case SICK_LEAVE:
    				dayStatusChoiceBox.setValue("Больничный");
    				break;
    			case VACATION:
    				dayStatusChoiceBox.setValue("Отпуск");
    				break;
    			case ABSENTEEISM:
    				dayStatusChoiceBox.setValue("Прогул");
    				break;
    			default:
    				dayStatusChoiceBox.setValue(" - ");
    				break;
    		}
    	}
    }
    
    private void getMinutes() {
    	for (int i = 0; i < 60;) {
    		minutesStartWorkChoiceBox.getItems().add(i);
    		minutesEndWorkChoiceBox.getItems().add(i);
    		i += 5;
    	}
    }
    
    private void getHours() {
    	for (int i = 0 ; i < 24; i++) {		
    		hoursStartWorkChoiceBox.getItems().add(i);
    		hoursEndWorkChoiceBox.getItems().add(i);
    	}
    }
    
    private void getDayStatuses() {
    	dayStatusChoiceBox.getItems().add("Рабочий день");
    	dayStatusChoiceBox.getItems().add("Переработка");
    	dayStatusChoiceBox.getItems().add("Выходной");
    	dayStatusChoiceBox.getItems().add("Больничный");
    	dayStatusChoiceBox.getItems().add("Отпуск");
    	dayStatusChoiceBox.getItems().add("Прогул");
    }
    
    public void okButtonClick() {
    	day = ScheduleController.tempDay;
    	switch (dayStatusChoiceBox.getValue()) {
			case "Рабочий день":
				day.setDayStatus(DayStatus.WORKING_DAY);
	    		day.setStartWork(LocalTime.of(hoursStartWorkChoiceBox.getValue(), minutesStartWorkChoiceBox.getValue()));
	    		day.setEndWork(LocalTime.of(hoursEndWorkChoiceBox.getValue(), minutesEndWorkChoiceBox.getValue()));
	    		day.setHoursOfWork(day.getEndWork().getHour() - day.getStartWork().getHour());
				break;
			case "Переработка":
				day.setDayStatus(DayStatus.OVER_TIME);
	    		day.setStartWork(LocalTime.of(hoursStartWorkChoiceBox.getValue(), minutesStartWorkChoiceBox.getValue()));
	    		day.setEndWork(LocalTime.of(hoursEndWorkChoiceBox.getValue(), minutesEndWorkChoiceBox.getValue()));
	    		day.setHoursOfOvertime(day.getEndWork().getHour() - day.getStartWork().getHour());
				break;
			case "Выходной":
				day.setDayStatus(DayStatus.DAY_OFF);
				break;
			case "Больничный":
				day.setDayStatus(DayStatus.SICK_LEAVE);
				break;
			case "Отпуск":
				day.setDayStatus(DayStatus.VACATION);
				break;
			case "Прогул":
				day.setDayStatus(DayStatus.ABSENTEEISM);
				break;
			default:
				day.setDayStatus(DayStatus.NOT_SET);
				break;
		}
    	
    	ScheduleController.tempDay = day;
		Stage stage = (Stage) okButton.getScene().getWindow();
		stage.close();
    }
    
    public void cancelButtonClick() {
    	ScheduleController.tempDay = null;
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
    }
}


















