package com.oriseus.schedule.controller;

import java.io.IOException;

import com.oriseus.schedule.model.SettingObject;
import com.oriseus.schedule.utils.SettingHundler;
import com.oriseus.schedule.utils.WindowHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsController {

    @FXML
    TextField fiveToTwoTextField;
    @FXML
    TextField twoToTwoTextField;

    @FXML
    Button fiveToTwoUpButton;
    @FXML
    Button fiveToTwoDowButton;

    @FXML
    Button twoToTwoUpButton;
    @FXML
    Button twoToTwoDownButton;

    @FXML
    CheckBox isFridayShortDayCheckBox;

    @FXML
    Button okButton;
    @FXML
    Button cancelButton;

    private int notWorkingHoursFiveToTwo;
    private int notWorkingHoursTwoToTwo;
    private boolean isFridayShortDay;

    @FXML
    public void initialize() {
        if (MainController.settingObject != null) {
            notWorkingHoursFiveToTwo = MainController.settingObject.getNotWorkingHoursFiveToTwo();
            notWorkingHoursTwoToTwo = MainController.settingObject.getNotWorkingHoursTwoToTwo();
            isFridayShortDay = MainController.settingObject.isFridayShortDay();
        }

        setValues();
	} 

    private void setValues() {
        fiveToTwoTextField.setText(String.valueOf(notWorkingHoursFiveToTwo));
        twoToTwoTextField.setText(String.valueOf(notWorkingHoursTwoToTwo));
        isFridayShortDayCheckBox.setSelected(isFridayShortDay);
    }

    @FXML
    public void fiveToTwoUpButtonClick() {
        notWorkingHoursFiveToTwo++;
        setValues();
    }
    
    @FXML
    public void fiveToTwoDowButtonClick() {
        if (notWorkingHoursFiveToTwo > 0) {
            notWorkingHoursFiveToTwo--;
            setValues();
        }
    }
    
    @FXML
    public void twoToTwoUpButtonClick() {
        notWorkingHoursTwoToTwo++;
        setValues();
    }
    
    @FXML
    public void twoToTwoDownButtonClick() {
        if (notWorkingHoursTwoToTwo > 0) {
            notWorkingHoursTwoToTwo--;
            setValues();
        }
    }

    @FXML
	public void okButtonClick() {
        try {
            MainController.settingObject = new SettingObject();
        } catch (IOException e) {
            WindowHandler.getInstants().showErrorMessage("Произошла ошибка!", e.getMessage());
            e.printStackTrace();
        }
        MainController.settingObject.setNotWorkingHoursFiveToTwo(notWorkingHoursFiveToTwo);
        MainController.settingObject.setNotWorkingHoursTwoToTwo(notWorkingHoursTwoToTwo);
        MainController.settingObject.setFridayShortDay(isFridayShortDay);

        try {
            SettingHundler.getInstants().saveDaySettings(MainController.settingObject);
            MainController.settingObject = null;
        } catch (IOException e) {
            WindowHandler.getInstants().showErrorMessage("Произошла ошибка при сохранении настроек!", e.getMessage());
            e.printStackTrace();
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
