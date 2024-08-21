package com.oriseus.schedule.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddNewWorkingPlaceController {
	
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
			text.setText("Text field is blank!");
		} else {
			ScheduleController.newWorkingPlaceName = textField.getText();
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
    }
}
