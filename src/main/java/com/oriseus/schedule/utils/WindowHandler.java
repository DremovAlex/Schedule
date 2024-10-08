package com.oriseus.schedule.utils;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class WindowHandler {
	
	private static WindowHandler windowHandler;
	
	private WindowHandler() {
	}
	
	public static WindowHandler getInstants() {
		if(windowHandler != null) {
			return windowHandler;
		} else {
			windowHandler = new WindowHandler();
			return windowHandler;
		}
	}
	
	public void openModalWindow(String windowTitle, String windowPath, int width, int height) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(windowPath + ".fxml"));
        Stage stage = new Stage();
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(root, width, height));
        stage.showAndWait();
	}

	public void showErrorMessage(String header, String content) {
		Alert alertMessage = new Alert(AlertType.ERROR);
		alertMessage.setHeaderText(header);
		alertMessage.setContentText(content);
		alertMessage.showAndWait();
	}

	public Optional<ButtonType> openChoiceDialog(String title, String header, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		Optional<ButtonType> result = alert.showAndWait();

		return result;
	}
	
}
