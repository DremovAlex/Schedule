package com.oriseus.schedule.controller;

import java.io.IOException;
import java.time.LocalDate;

import com.oriseus.schedule.Company;
import com.oriseus.schedule.model.Worker;
import com.oriseus.schedule.model.WorkingPlace;
import com.oriseus.schedule.utils.WindowHandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MainController {

	private final String[] months = {"",
									 "Январь",
									 "Февраль",
									 "Март",
									 "Апрель",
									 "Май",
									 "Июнь",
									 "Июль",
									 "Август",
									 "Сентябрь",
									 "Октябрь",
									 "Ноябрь",
									 "Декабрь"};

	private int month;
	private int year;

	@FXML
	ScrollPane mainScrollPane;
	@FXML
	TabPane mainTabPane;
	
	@FXML
	Label monthLabel;
	@FXML
	Button leftButton;
	@FXML
	Button rightButton;

	@FXML
	Button createNewWorkingPlaceButton;
	@FXML
	Button changeWorkingPlaceButton;
	@FXML
	Button deleteWorkingPlaceButton;

	@FXML
	Button addNewWorkerButton;
	@FXML
	Button changeWorkerButton;
	@FXML
	Button deleteWorkerButton;

	ContextMenu workerContextMenu;
	ContextMenu monthContextMenu;

	public static String currentTabName; 
	public static Worker tempWorker;

    @FXML
    public void initialize() {

		month = LocalDate.now().getMonthValue();
		year = LocalDate.now().getYear();
		monthLabel.setText(months[month] + " " + year);

		monthLabel.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.isPrimaryButtonDown()) {

				} else if (mouseEvent.isSecondaryButtonDown()) {;
					monthContextMenu.show(monthLabel, Side.BOTTOM, 0, 0);
				}
			}
		});

		workerContextMenu = new ContextMenu();	
		MenuItem changeWorkerMenuItem = new MenuItem("Изменить работника");
		changeWorkerMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				
				changeWorker();
			}
		});
		MenuItem deleteWorkerMenuItem = new MenuItem("Удалить работника");
		deleteWorkerMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				
				deleteWorker();
			}
		});
		
		workerContextMenu.getItems().addAll(changeWorkerMenuItem, deleteWorkerMenuItem);

		monthContextMenu = new ContextMenu();
		
		MenuItem januaryMenuItem = new MenuItem("Январь");
		MenuItem februaryMenuItem = new MenuItem("Февраль");
		MenuItem marchMenuItem = new MenuItem("Март");
		MenuItem aprilMenuItem = new MenuItem("Апрель");
		MenuItem mayMenuItem = new MenuItem("Май");
		MenuItem juneMenuItem = new MenuItem("Июнь");
		MenuItem julyMenuItem = new MenuItem("Июль");
		MenuItem augustMenuItem = new MenuItem("Август");
		MenuItem septemberMenuItem = new MenuItem("Сентябрь");
		MenuItem octoberMenuItem = new MenuItem("Октябрь");
		MenuItem novemberMenuItem = new MenuItem("Ноябрь");
		MenuItem decemberMenuItem = new MenuItem("Декабрь");
		
		januaryMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				month = 1;
				refreshScene();
			}
		});
		februaryMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				month = 2;
				refreshScene();
			}
		});
		marchMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				month = 3;
				refreshScene();
			}
		});
		aprilMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				month = 4;
				refreshScene();
			}
		});
		mayMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				month = 5;
				refreshScene();
			}
		});
		juneMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				month = 6;
				refreshScene();
			}
		});
		julyMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				month = 7;
				refreshScene();
			}
		});
		augustMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				month = 8;
				refreshScene();
			}
		});
		septemberMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				month = 9;
				refreshScene();
			}
		});
		octoberMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				month = 10;
				refreshScene();
			}
		});
		novemberMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				month = 11;
				refreshScene();
			}
		});
		decemberMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				month = 12;
				refreshScene();
			}
		});
		
		monthContextMenu.getItems().addAll(januaryMenuItem, februaryMenuItem, marchMenuItem, aprilMenuItem, mayMenuItem, juneMenuItem, julyMenuItem, augustMenuItem, septemberMenuItem, novemberMenuItem, decemberMenuItem);
    }

    private VBox getNameAndPhoneNumber(Worker worker) {
    	Text name = new Text(worker.getSurname() + " " + worker.getName() + " " + worker.getSecondName());
    	Text phoneNumber = new Text("Номер телефона: " + worker.getPhoneNumber());
    	
		name.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.isPrimaryButtonDown()) {
					name.setUnderline(true);
					tempWorker = worker;
				} else if (mouseEvent.isSecondaryButtonDown()) {
					tempWorker = worker;
					workerContextMenu.show(name, Side.BOTTOM, 0, 0);
				}
			}
		});

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
    		rectangles[i] = new Rectangle(30, 5);
    		rectangles[i].setFill(Color.BLUE);
    		
    		rectangleBox.getChildren().add(rectangles[i]);
    	}    	

		rectangleBox.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.isPrimaryButtonDown()) {
					rectangleBox.setStyle("-fx-border-width: 2pt; -fx-border-color: red");
				} else if (mouseEvent.isSecondaryButtonDown()) {
					
				}
			}
		});

    	return rectangleBox;
    }

	public String getNameOfSelectedTab() {
		return mainTabPane.getSelectionModel().getSelectedItem().getText();
	}

	private void refreshScene() {
		monthLabel.setText(months[month] + year);
		mainTabPane.getTabs().clear();

		for (WorkingPlace workingPlace : Company.getInstants().getWorkingPlasesList()) {
			Tab tab = new Tab(workingPlace.getName());
			
			addContentToTab(tab, workingPlace);
			
			mainTabPane.getTabs().add(tab);
		}
	}

	private void addContentToTab(Tab tab, WorkingPlace workingPlace) {
		VBox vBox = new VBox();

		int daysInMonth = 0;

		for (Worker worker : workingPlace.getListOfWorkers()) {
			VBox box = new VBox();

			daysInMonth = worker.getScheduleObject().getNumberOfDaysInMonth(year, month);
			box.getChildren().add(getNameAndPhoneNumber(worker));
			box.getChildren().add(addRectangles(daysInMonth));
			vBox.getChildren().add(box);			
		}
		tab.setContent(vBox);
	}

    @FXML
    public void createNewWorkingPlace() {
    	try {
    		WindowHandler.getInstants().openModalWindow("Изменение названия рабочего места", 
    				"newWorkingPlaceWindow", 500, 200);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		refreshScene();
    }

    @FXML
    public void changeWorkingPlace() {
		currentTabName = getNameOfSelectedTab();
    	try {
    		WindowHandler.getInstants().openModalWindow("Изменение названия рабочего места", 
    				"changeWorkingPlaceWindow", 500, 200);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		refreshScene();
    }

    @FXML
    public void deleteWorkingPlace() {
    	String tabName = getNameOfSelectedTab();

		for (int i = 0; i < Company.getInstants().getWorkingPlasesList().size(); i++) {
			if (Company.getInstants().getWorkingPlasesList().get(i).getName().equals(tabName)) {
				Company.getInstants().getWorkingPlasesList().remove(i);
			}
		}

		refreshScene();
    }

	@FXML
	public void addNewWorker() {
		try {
    		WindowHandler.getInstants().openModalWindow("Создание нового работника", 
    				"newWorkerWindow", 600, 600);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		currentTabName = getNameOfSelectedTab();
		addNewWorker(Company.getInstants().getWorkingPlace(currentTabName), tempWorker);

		currentTabName = null;
		tempWorker = null;

		refreshScene();
	}

	@FXML
	public void changeWorker() {

		if (tempWorker == null) {
			WindowHandler.getInstants().showErrorMessage("Не выбран работник.", 
			"Пожалуйста, выберите работника перед изминением.");
			return;
		} 

		Worker searchingWorker = tempWorker;

		try {
    		WindowHandler.getInstants().openModalWindow("Изменение данных работника", 
    				"changeWorkerWindow", 600, 600);
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentTabName = getNameOfSelectedTab();
		
		changeWorker(Company.getInstants().getWorkingPlace(currentTabName), searchingWorker);

		currentTabName = null;
		tempWorker = null;

		refreshScene();
	}

	@FXML
	public void deleteWorker() {

		if (tempWorker == null) {
			WindowHandler.getInstants().showErrorMessage("Не выбран работник.", "Пожалуйста, выберите работника перед изминением.");
			return;
		} 

		currentTabName = getNameOfSelectedTab();
		
		deleteWorker(Company.getInstants().getWorkingPlace(currentTabName), tempWorker);

		currentTabName = null;
		tempWorker = null;

		refreshScene();
	}

	private void addNewWorker(WorkingPlace workingPlace, Worker worker) {
		workingPlace.addWorker(worker);
	}

	private void changeWorker(WorkingPlace workingPlace, Worker worker) {
		for (int i = 0; i < workingPlace.getListOfWorkers().size(); i++) {
			if (workingPlace.getListOfWorkers().get(i).equals(worker)) {
				workingPlace.getListOfWorkers().get(i).setName(tempWorker.getName());
				workingPlace.getListOfWorkers().get(i).setSurname(tempWorker.getSurname());
				workingPlace.getListOfWorkers().get(i).setSecondName(tempWorker.getSecondName());
				workingPlace.getListOfWorkers().get(i).setPhoneNumber(tempWorker.getPhoneNumber());
				break;
			}
		}		
	}

	private void deleteWorker(WorkingPlace workingPlace, Worker worker) {
		for (int i = 0; i < workingPlace.getListOfWorkers().size(); i++) {
			if (workingPlace.getListOfWorkers().get(i).equals(worker)) {
				workingPlace.getListOfWorkers().remove(i);
				break;
			}
		}
	}

	@FXML
	private void leftMonthButtonPressed() {
		if (month > 1) {
			--month;
		} else if (month == 1 && year > 2020) {
			--year;
			month = 12;
		}
		refreshScene();
	}

	@FXML
	private void rightMonthButtonPressed() {
		if (month < 12) {
			++month;
		} else if (month == 12 && year < 2035) {
			++year;
			month = 1;
		}
		refreshScene();
	}
}
