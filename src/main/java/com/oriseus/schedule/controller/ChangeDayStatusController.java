package com.oriseus.schedule.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeDayStatusController {

    @FXML
    Button lefyButton;
    @FXML
    Button rightButton;
    @FXML
    Button okButton;
    @FXML
    Button cancelButton;

    @FXML
    TextField textField;

    private int numberOfDays;

    @FXML
    public void initialize() {
        numberOfDays = 1;
        textField.setText(String.valueOf(numberOfDays));
    }

    @FXML
    public void leftButtonClick() {
        numberOfDays = Integer.valueOf(textField.getText());
        if (numberOfDays > 0) {
            numberOfDays--;
        }
        textField.setText(String.valueOf(numberOfDays));
    }

    @FXML
    public void rightButtonClick() {
        numberOfDays = Integer.valueOf(textField.getText());
        numberOfDays++;
        textField.setText(String.valueOf(numberOfDays));
    }

        @FXML
	public void okButtonClick() {
        MainController.dayCounter = Integer.valueOf(textField.getText());

		Stage stage = (Stage) okButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void cancelButtonClick() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}
