package com.oriseus.schedule.controller;

import com.oriseus.schedule.model.Worker;
import com.oriseus.schedule.utils.WindowHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewWorkerController {

    @FXML
    TextField nameTextField;
    @FXML
    TextField surnamTextField;
    @FXML
    TextField secondNameTextField;
    @FXML
    TextField positionTextField;
    @FXML
    TextField phoneNumberTextField;
    
    @FXML
    Button okButton;
    @FXML
    Button cancelButton;

    @FXML
    public void initialize() {
	} 

    @FXML
	public void okButtonClick() {
        if ((!nameTextField.getText().isEmpty() || !nameTextField.getText().isBlank()) &&
            (!surnamTextField.getText().isEmpty() || !surnamTextField.getText().isBlank()) &&
            (!secondNameTextField.getText().isEmpty() || !secondNameTextField.getText().isBlank())) {
            MainController.tempWorker = new Worker(nameTextField.getText(), surnamTextField.getText(), secondNameTextField.getText(), positionTextField.getText(), phoneNumberTextField.getText());

            Stage stage = (Stage) okButton.getScene().getWindow();
		    stage.close();
        } else {
            WindowHandler.getInstants().showErrorMessage("Поля не заполнены.", "Пожалуйста, заполните имя, фамилию и отчество. Номер телефона не обязателен к заполнению.");
        }
	}

	@FXML
	public void cancelButtonClick() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

}
