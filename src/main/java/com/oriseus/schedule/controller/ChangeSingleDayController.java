package com.oriseus.schedule.controller;

import com.oriseus.schedule.model.Day;
import com.oriseus.schedule.model.DayStatus;
import com.oriseus.schedule.utils.WindowHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalTime;


public class ChangeSingleDayController {

    @FXML
    ChoiceBox<String> dayStatusChoiceBox;

    @FXML
    Button startWorkHourUpButton;
    @FXML
    Button startWorkMinuteUpButton;
    @FXML
    Button startWorkHourDownButton;
    @FXML
    Button startWorkMinuteDownButton;

    @FXML
    Button endWorkHourUpButton;
    @FXML
    Button endWorkMinuteUpButton;
    @FXML
    Button endWorkHourDownButton;
    @FXML
    Button endWorkMinuteDownButton;

    @FXML
    TextField startWorkHourTextField;
    @FXML
    TextField startWorkMinuteTextField;
    @FXML
    TextField endWorkHourTextField;
    @FXML
    TextField endWorkMinuteTextField;

    @FXML
    Button okButton;
    @FXML
    Button cancelButton;

    private Day tempDay;

    private int startDayHour;
    private int startDayMinute;
    private int endDayHour;
    private int endDayMinute;

    private String[] dayStatusStrings = {"Не установлено", "Выходной день", "Рабочий день", "Больничный", "Отпуск", "Прогул"};

    @FXML
    public void initialize() {
		dayStatusChoiceBox.getItems().addAll(dayStatusStrings);

        dayStatusChoiceBox.setValue(dayStatusStrings[getIntexDayStatus(MainController.technicalDay.peekDayStatus())]);
        
        if (MainController.technicalDay.peekDayStatus().equals(DayStatus.WorkingDay)) {
            startDayHour = MainController.technicalDay.getStartWorkTime().getHour();
            startDayMinute = MainController.technicalDay.getStartWorkTime().getMinute();
            endDayHour = MainController.technicalDay.getEndWorkTime().getHour();
            endDayMinute = MainController.technicalDay.getEndWorkTime().getMinute();
        }

        setTextToTextFields();
    }

    private int getIntexDayStatus(DayStatus dayStatus) {
        switch (dayStatus) {
            case NotSet: return 0;
            case DayOff: return 1;
            case WorkingDay: return 2;
            case SickLeave: return 3;
            case Vacation: return 4;
            case Absenteeism: return 5;
            default:
                return 0;
        }
    }

    private DayStatus convertStringValueToDayStatus(String value) {
        switch (value) {
            case "Выходной день":
                return DayStatus.DayOff;
            case "Больничный":
                return DayStatus.SickLeave;
            case "Рабочий день":
                return DayStatus.WorkingDay;
            case "Отпуск":
                return DayStatus.Vacation;
            case "Прогул":
                return DayStatus.Absenteeism;
            default:
                return DayStatus.NotSet;
        }
    }

    private void getValuesFromTextFields() {
        if (tryToConvertStringToInteger(startWorkHourTextField.getText())) {
            startDayHour = Integer.parseInt(startWorkHourTextField.getText());
            checkHour(startDayHour);
        } else {
            WindowHandler.getInstants().showErrorMessage("Ошибка данных", "Вы ввели не коректные данные.");
            return;
        }
        if (tryToConvertStringToInteger(startWorkMinuteTextField.getText())) {
            startDayMinute = Integer.parseInt(startWorkMinuteTextField.getText()); 
            checkMinute(startDayMinute);
        } else {
            WindowHandler.getInstants().showErrorMessage("Ошибка данных", "Вы ввели не коректные данные.");
            return;
        }
        if (tryToConvertStringToInteger(endWorkHourTextField.getText())) {
            endDayHour = Integer.parseInt(endWorkHourTextField.getText());
            checkHour(endDayHour);
        } else {
            WindowHandler.getInstants().showErrorMessage("Ошибка данных", "Вы ввели не коректные данные.");
            return;
        }
        if (tryToConvertStringToInteger(endWorkMinuteTextField.getText())) {
            endDayMinute = Integer.parseInt(endWorkMinuteTextField.getText());
            checkMinute(endDayMinute);
        } else {
            WindowHandler.getInstants().showErrorMessage("Ошибка данных", "Вы ввели не коректные данные.");
            return;
        }
    }

    private void setTextToTextFields() {
        startWorkHourTextField.setText(String.valueOf(startDayHour));
        startWorkMinuteTextField.setText(String.valueOf(startDayMinute));
        endWorkHourTextField.setText(String.valueOf(endDayHour));
        endWorkMinuteTextField.setText(String.valueOf(endDayMinute));
    }

    private boolean tryToConvertStringToInteger(String value) {    
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void checkHour(int hour) {
        if (hour < 0 || hour > 23) {
            WindowHandler.getInstants().showErrorMessage("Ошибка данных", "Вы ввели не коректные данные.");
            return;
        }
    }

    private void checkMinute(int minute) {
        if (minute < 0 || minute > 59) {
            WindowHandler.getInstants().showErrorMessage("Ошибка данных", "Вы ввели не коректные данные.");
            return;
        }
    }

    @FXML
    public void startWorkHourUpButtonClick() {
        getValuesFromTextFields();
        
        if (startDayHour < 23) 
            startDayHour++;
        
        setTextToTextFields();
    }

    @FXML
    public void startWorkMinuteUpButtonClick() {
        getValuesFromTextFields();
        
        if (startDayMinute < 59) {
            startDayMinute++;
        } else if (startDayMinute == 59 && startDayHour < 23) {
            startDayHour++;
            startDayMinute = 0;
        }

        setTextToTextFields();
    }

    @FXML
    public void startWorkHourDownButtonClick() {
        getValuesFromTextFields();
        
        if (startDayHour > 0) 
            startDayHour--;

        setTextToTextFields();
    }

    @FXML
    public void startWorkMinuteDownButtonClick() {
        getValuesFromTextFields();

        if (startDayMinute > 0) {
            startDayMinute--;
        } else if (startDayMinute == 0 && startDayHour > 0) {
            startDayHour--;
            startDayMinute = 59;
        }

        setTextToTextFields();
    }

    @FXML
    public void endWorkHourUpButtonClick() {
        getValuesFromTextFields();
        
        if (endDayHour < 23) 
            endDayHour++;
        
        setTextToTextFields();
    }

    @FXML
    public void endWorkMinuteUpButtonClick() {
        getValuesFromTextFields();
        
        if (endDayMinute < 59) {
            endDayMinute++;
        } else if (endDayMinute == 59 && endDayHour < 23) {
            endDayHour++;
            endDayMinute = 0;
        }

        setTextToTextFields();
    }

    @FXML
    public void endWorkHourDownButtonClick() {
        getValuesFromTextFields();
        
        if (endDayHour > 0) 
            endDayHour--;

        setTextToTextFields();
    }

    @FXML
    public void endWorkMinuteDownButtonClick() {
        getValuesFromTextFields();

        if (endDayMinute > 0) {
            endDayMinute--;
        } else if (endDayMinute == 0 && endDayHour > 0) {
            endDayHour--;
            endDayMinute = 59;
        }

        setTextToTextFields();
    }

    @FXML
    public void okButtonClick() {
        if (dayStatusChoiceBox.getValue().equals(dayStatusStrings[2])) {
            getValuesFromTextFields();
            MainController.technicalDay.pushDayStatus(convertStringValueToDayStatus(dayStatusChoiceBox.getValue()));
            MainController.technicalDay.setStartWorkTime(LocalTime.of(startDayHour, startDayMinute));
            MainController.technicalDay.setEndWorkTime(LocalTime.of(endDayHour, endDayMinute));
        } else {
            MainController.technicalDay.pushDayStatus(convertStringValueToDayStatus(dayStatusChoiceBox.getValue()));
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
