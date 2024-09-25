package com.oriseus.schedule.utils;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	
	public void openModalWindow(String windowTitle, String windowPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(windowPath + ".fxml"));
        Stage stage = new Stage();
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(root, 500, 200));
        stage.showAndWait();
	}

	public void showErrorMessage(String header, String content) {
		Alert alertMessage = new Alert(AlertType.ERROR);
		alertMessage.setHeaderText(header);
		alertMessage.setContentText(content);
		alertMessage.showAndWait();
	}
	
}
