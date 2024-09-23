package com.oriseus.schedule.controller;

import com.oriseus.schedule.model.Worker;
import com.oriseus.schedule.model.WorkingPlace;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MainController {
	
	@FXML
	ScrollPane mainScrollPane;
	@FXML
	TabPane mainTabPane;
	
    @FXML
    public void initialize() {
    	
    	WorkingPlace reguan = new WorkingPlace("Reguan");
    	Worker worker = new Worker("Ivan", "Ivanov", "Ivanovich", "9 999 999 99 99");
    	reguan.addWorker(worker);
    	
        Tab tab = new Tab(reguan.getName());
        
        VBox mainBox = new VBox();
        mainBox.getChildren().add(getNameAndPhoneNumber(reguan.getListOfWorkers().getFirst()));
        mainBox.getChildren().add(addRectangles(30));
        
        tab.setContent(mainBox);
        
        mainTabPane.getTabs().add(tab);
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
}
































