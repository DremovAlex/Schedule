package com.oriseus.schedule.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChangeWorkerController {

	@FXML
	Text text;
		
	@FXML
	TextField nameTextField;
	@FXML
	TextField surnameTextField;
	@FXML
	TextField secondNameTextField;
	@FXML
	TextField phoneNumberTextField;
		
	@FXML
	Button okButton;
	@FXML
	Button cancelButton;
		
	public void okButtonClick() {		
		if (nameTextField.getText().isBlank() || surnameTextField.getText().isBlank() || secondNameTextField.getText().isBlank()) {
			text.setText("Данные сотрудника не заполнены!");
		} else {
			ScheduleController.newPerson.setName(nameTextField.getText());
			ScheduleController.newPerson.setSurname(surnameTextField.getText());
			ScheduleController.newPerson.setSecondName(secondNameTextField.getText());
			ScheduleController.newPerson.setPhoneNumber(phoneNumberTextField.getText());
			
			Stage stage = (Stage) okButton.getScene().getWindow();
			stage.close();
		}		
	}
		
	public void cancelButtonClick() {
		ScheduleController.newPerson = null;
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
		
	@FXML
	public void initialize() {
		nameTextField.setText(ScheduleController.newPerson.getName());
		surnameTextField.setText(ScheduleController.newPerson.getSurname());
		secondNameTextField.setText(ScheduleController.newPerson.getSecondName());
		phoneNumberTextField.setText(ScheduleController.newPerson.getPhoneNumber());
	}
}

