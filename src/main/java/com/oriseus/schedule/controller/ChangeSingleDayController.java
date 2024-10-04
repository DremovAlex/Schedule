package com.oriseus.schedule.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import com.oriseus.schedule.model.Day;
import com.oriseus.schedule.model.DayStatus;
import com.oriseus.schedule.utils.WindowHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class ChangeSingleDayController {

    @FXML
    ChoiceBox<String> dayStatusChoiceBox;
    @FXML
    ChoiceBox<Integer> startDayHourChoiceBox;
    @FXML
    ChoiceBox<Integer> startDayMinutesChoiceBox;
    @FXML
    ChoiceBox<Integer> endDayHourChoiceBox;
    @FXML
    ChoiceBox<Integer> endDayMinutesChoiceBox;

    @FXML
    Button okButton;
    @FXML
    Button cancelButton;

    private Day tempDay;

    @FXML
    public void initialize() {
		dayStatusChoiceBox.getItems().addAll("Выходной день",
														 "Рабочий день",
														 "Больничный",
														 "Отпуск",
														 "Прогул");

        fillChoiceBoxes();
    }

    private void fillChoiceBoxes() {
        for (int i = 0; i < 24; i++) {
            startDayHourChoiceBox.getItems().add(i);
            endDayHourChoiceBox.getItems().add(i);
        }

        for (int i = 0; i < 60; i += 10) {
            startDayMinutesChoiceBox.getItems().add(i);
            endDayMinutesChoiceBox.getItems().add(i);
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

    @FXML
    public void okButtonClick() {

        if (dayStatusChoiceBox.getValue() == null ||
            startDayHourChoiceBox.getValue() == null ||
            startDayMinutesChoiceBox.getValue() == null ||
            endDayHourChoiceBox.getValue() == null ||
            endDayMinutesChoiceBox.getValue() == null) {
                WindowHandler.getInstants().showErrorMessage("Не заполнены поля",
                     "Пожалуйста, заполните все значения для изменения дня.");
                     return;
            }

        MainController.technicalDay.pushDayStatus(convertStringValueToDayStatus(dayStatusChoiceBox.getValue()));
        MainController.technicalDay.setStartWorkTime(LocalTime.of(startDayHourChoiceBox.getValue(),
                                                                startDayMinutesChoiceBox.getValue()));
        MainController.technicalDay.setEndWorkTime(LocalTime.of(endDayHourChoiceBox.getValue(),
                                                                endDayMinutesChoiceBox.getValue()));

        Stage stage = (Stage) okButton.getScene().getWindow();
		stage.close();
    }

    @FXML
    public void cancelButtonClick() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
    }

}
