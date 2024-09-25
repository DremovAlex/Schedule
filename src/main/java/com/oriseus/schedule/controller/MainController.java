package com.oriseus.schedule.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.oriseus.schedule.model.Worker;
import com.oriseus.schedule.model.WorkingPlace;
import com.oriseus.schedule.utils.WindowHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MainController {
	
	private List<WorkingPlace> workingPlaces = new ArrayList<WorkingPlace>();
	
	@FXML
	ScrollPane mainScrollPane;
	@FXML
	TabPane mainTabPane;
	
	@FXML
	Button createNewWorkingPlaceButton;
	@FXML
	Button changeWorkingPlaceButton;
	@FXML
	Button deleteWorkingPlaceButton;

    @FXML
    public void initialize() {
		mainTabPane.getTabs().add(new Tab("fdjghnkjdfhnkg"));
		mainTabPane.getTabs().add(new Tab("fdjghnkjdfhnkg"));
		mainTabPane.getTabs().add(new Tab("fdjghnkjdfhnkg"));
    }

    private VBox getNameAndPhoneNumber(Worker worker) {
    	Text name = new Text(worker.getSurname() + " " + worker.getName() + " " + worker.getSecondName());
    	Text phoneNumber = new Text(worker.getPhoneNumber());
    	
    	VBox vbox = new VBox();
    	
    	vbox.getChildren().add(name);
    	vbox.getChildren().add(phoneNumber);
    	
    	return vbox;
    }
    
    private HBox addRectangles(int days) {
    	HBox hBox = new HBox();
    	hBox.setSpacing(10);
    	
    	for (int i = 0; i < days; i++) {
    		hBox.getChildren().add(getRectangle());
    	}

    	return hBox;
    }
    
    private VBox getRectangle() {
    	VBox rectangleBox = new VBox();
    	
    	Rectangle[] rectangles = new Rectangle[24];
    	
    	for (int i = 0; i < rectangles.length; i++) {
    		rectangles[i] = new Rectangle(60, 10);
    		rectangles[i].setFill(Color.BLUE);
    		
    		rectangleBox.getChildren().add(rectangles[i]);
    	}    	

    	return rectangleBox;
    }

	public void addNewWorkingPlace(String nameOfWorkingPlace) {

		System.out.println(mainTabPane + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		workingPlaces.add(new WorkingPlace(nameOfWorkingPlace));
		Tab tab = new Tab(nameOfWorkingPlace);
		mainTabPane.getTabs().add(tab);
//		refreshScene();
	}

	public void changeWorkingPlaceName(String newNameOfWorkingPlace) {
		for (WorkingPlace workingPlace : workingPlaces) {
			if (workingPlace.getName().equals(getNameOfSelectedTab())) {
				workingPlace.setName(newNameOfWorkingPlace);
				break;
			}
		}

		//refreshScene();
	}

	public String getNameOfSelectedTab() {
		return mainTabPane.getSelectionModel().getSelectedItem().getText();
	}

	private void refreshScene() {
		mainTabPane.getTabs().clear();
		for (WorkingPlace workingPlace : workingPlaces) {
			mainTabPane.getTabs().add(new Tab(workingPlace.getName()));
		}
	}

    @FXML
    public void createNewWorkingPlace() {
    	try {
			WindowHandler.getInstants().openModalWindow("Создание нового рабочего места", 
					"newWorkingPlaceWindow");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    public void changeWorkingPlace() {
    	try {
    		WindowHandler.getInstants().openModalWindow("Изменение названия рабочего места", 
    				"changeWorkingPlace");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    public void deleteWorkingPlace() {
    	System.out.println("delete");
    }

}
































