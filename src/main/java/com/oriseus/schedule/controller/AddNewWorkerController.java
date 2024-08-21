package com.oriseus.schedule.controller;

import com.oriseus.schedule.model.Person;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddNewWorkerController {

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
			text.setText("Text field is blank!");
		} else {
			ScheduleController.newPerson = new Person(nameTextField.getText(), surnameTextField.getText(), secondNameTextField.getText(), phoneNumberTextField.getText());
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
    }
}
