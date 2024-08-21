package com.oriseus.schedule.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SettingController {

	@FXML
	Button okButton;
	@FXML
	Button cancelButton;
	
	@FXML
	ColorPicker workingDayColorPicker;
	@FXML
	ColorPicker dayOffColorPicker;
	@FXML
	ColorPicker sickLeaveColorPicker;
	@FXML
	ColorPicker vacationColorPicker;
	@FXML
	ColorPicker absenteismColorPicker;
	@FXML
	ColorPicker overTimeColorPicker;
	@FXML
	ColorPicker notSetColorPicker;
	
	@FXML
	CheckBox fridayCheckBox;
	@FXML
	ChoiceBox<Integer> minusHoursFiveToTwoChoiceBox;
	@FXML
	ChoiceBox<Integer> minusHoursShiftChoiceBox;
	
	private Properties dayProperties;
	
	@FXML
    public void initialize() {
		getHours();
				
    	dayProperties = new Properties();
    	try {
			dayProperties.load(new FileInputStream(ScheduleController.getPathToAplication() + 
					File.separator + "src" + File.separator + "main" + File.separator +"resources" + File.separator + "day.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	workingDayColorPicker.setValue(Color.valueOf(dayProperties.getProperty("workingDayColor")));
		dayOffColorPicker.setValue(Color.valueOf(dayProperties.getProperty("dayOffColor")));
		sickLeaveColorPicker.setValue(Color.valueOf(dayProperties.getProperty("sickLeaveColor")));
		vacationColorPicker.setValue(Color.valueOf(dayProperties.getProperty("vacationColor")));
		absenteismColorPicker.setValue(Color.valueOf(dayProperties.getProperty("absenteismColor")));
		overTimeColorPicker.setValue(Color.valueOf(dayProperties.getProperty("overTimeColor")));
		notSetColorPicker.setValue(Color.valueOf(dayProperties.getProperty("notSetColor")));
		
		fridayCheckBox.setSelected(Boolean.valueOf(dayProperties.getProperty("isFridayShortDay")));
		
		minusHoursFiveToTwoChoiceBox.setValue(Integer.valueOf(dayProperties.getProperty("notWorkingHoursWorkingDay")));
		minusHoursShiftChoiceBox.setValue(Integer.valueOf(dayProperties.getProperty("notWorkingHoursShift")));
	}
	
	public void okButtonClick() {
        try (OutputStream output = new FileOutputStream(ScheduleController.getPathToAplication() + 
        		File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "day.properties")) {
        	
        	dayProperties.setProperty("workingDayColor", workingDayColorPicker.getValue().toString());
            dayProperties.setProperty("dayOffColor", dayOffColorPicker.getValue().toString());
            dayProperties.setProperty("sickLeaveColor", sickLeaveColorPicker.getValue().toString());
            dayProperties.setProperty("vacationColor", vacationColorPicker.getValue().toString());
            dayProperties.setProperty("absenteismColor", absenteismColorPicker.getValue().toString());
            dayProperties.setProperty("overTimeColor", overTimeColorPicker.getValue().toString());
            dayProperties.setProperty("notSetColor", notSetColorPicker.getValue().toString());
            
            dayProperties.setProperty("isFridayShortDay", String.valueOf(fridayCheckBox.isSelected()));
            dayProperties.setProperty("notWorkingHoursWorkingDay", minusHoursFiveToTwoChoiceBox.getValue().toString());
            dayProperties.setProperty("notWorkingHoursShift", minusHoursShiftChoiceBox.getValue().toString());
            
            dayProperties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
		
		
		Stage stage = (Stage) okButton.getScene().getWindow();
		stage.close();
	}
	public void cancelButtonClick() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
    private void getHours() {
    	for (int i = 0 ; i < 13; i++) {		
    		minusHoursFiveToTwoChoiceBox.getItems().add(i);
    		minusHoursShiftChoiceBox.getItems().add(i);
    	}
    }
}
