package com.oriseus.schedule.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChangePhoneNumberController {
	@FXML
	Text text;
	
	@FXML
	TextField textField;
	
	@FXML
	Button okButton;
	
	@FXML
	Button cancelButton;
	
	public void okButtonClick() {		
		if (textField.getText().isBlank()) {
			text.setText("Вы не ввели номер телефона!");
		} else {
			ScheduleController.newPerson.setPhoneNumber(textField.getText());
			
			Stage stage = (Stage) okButton.getScene().getWindow();
			stage.close();
		}		
	}
	
	public void cancelButtonClick() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
    @FXML
    public void initialize() {
    	textField.setText(ScheduleController.newPerson.getPhoneNumber());
    }
}
