package com.oriseus.schedule.controller;

import com.oriseus.schedule.Company;
import com.oriseus.schedule.utils.WindowHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeWorkingPlaceController {

    @FXML
    TextField textField;
    @FXML
    Button okButton;
    @FXML
    Button cancelButton;

    private static String tabName;

    @FXML
    public void initialize() {
        tabName = MainController.currentTabName;
        textField.setText(tabName);
    }

    @FXML
    public void okButtonClick() {
        if (!textField.getText().isEmpty() || !textField.getText().isBlank()) {
			Company.getInstants().changeNameWorkingPlaceByName(tabName, textField.getText());
            MainController.currentTabName = null;
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
