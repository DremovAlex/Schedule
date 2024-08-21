package com.oriseus.schedule.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QuantityController {
    
	@FXML
	Text label;
	@FXML
	Text days;
	@FXML
	Button leftButton;
	@FXML
	Button rightButton;
	@FXML
	Button okButton;
	@FXML
	Button cancelButton;
	
	Integer dayCount;
	
	public void leftButtonClick() {
		if (dayCount > 0) {
			dayCount--;
		}
		days.setText(String.valueOf(dayCount));
	}
	
	public void rightButtonClick() {
		dayCount++;
		days.setText(String.valueOf(dayCount));
	}
	
	public void okButtonClick() {
		ScheduleController.tempCountDay = dayCount;
		Stage stage = (Stage) okButton.getScene().getWindow();
		stage.close();
	}
	
	public void cancelButtonClick() {
		ScheduleController.tempCountDay = null;
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML
    public void initialize() {
		dayCount = 1;
    	leftButton.setText(" < ");
    	rightButton.setText(" > ");
    	okButton.setText("ok");
    	cancelButton.setText("cancel");
    }
}
