package com.oriseus.schedule.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.oriseus.schedule.App;
import com.oriseus.schedule.utils.WindowHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewWorkingPlaceController {

	@FXML
	TextField textField;
	@FXML
	Button okButton;
	@FXML
	Button cancelButton;

	MainController mainController;

	@FXML
    public void initialize() {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/primary.fxml"));
		try {
			Parent root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainController = loader.getController();
	}
	
	@FXML
	public void okButtonClick() {
		if (!textField.getText().isEmpty() || !textField.getText().isBlank()) {
			mainController.addNewWorkingPlace(textField.getText());
		} else {
			WindowHandler.getInstants().showErrorMessage("Название рабочего места пустое.", "Пожалуйста укажите название рабочего места.");
		}
		Stage stage = (Stage) okButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void cancelButtonClick() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}
