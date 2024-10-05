package com.oriseus.schedule.controller;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;
import java.time.format.TextStyle;
import java.util.List;

import com.oriseus.schedule.Company;
import com.oriseus.schedule.model.Day;
import com.oriseus.schedule.model.DayStatus;
import com.oriseus.schedule.model.ScheduleType;
import com.oriseus.schedule.model.Worker;
import com.oriseus.schedule.model.WorkingPlace;
import com.oriseus.schedule.utils.WindowHandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
	private Worker selectedWorker;
	private Day selectedDay;

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

	@FXML
	Button deleteScheduleFromCurrentButton;
	@FXML
	Button deleteScheduleButton;

	@FXML
	ChoiceBox<String> addScheduleChoiceBox;
	@FXML
	Button confirmAddScheduleButton;

	@FXML
	ChoiceBox<String> deleteScheduleChoiceBox;
	@FXML
	Button confirmDeleteScheduleButton;

	@FXML
	ChoiceBox<String> dayStatusChoiceBox;
	@FXML
	Button dayStatusButton;

	ContextMenu workerContextMenu;
	ContextMenu monthContextMenu;
	ContextMenu setScheduleContextMenu;

	public static String currentTabName; 
	public static Worker tempWorker;
	public static int dayCounter;
	public static Day technicalDay;

    @FXML
    public void initialize() {

		addScheduleChoiceBox.getItems().addAll("Пять - два", 
															"Два - два без ночных смен", 
															"Два - два с ночными сменами");

		deleteScheduleChoiceBox.getItems().addAll("Удалить расписание с текущего дня",
																"Удалить расписание полностью");

		dayStatusChoiceBox.getItems().addAll("Выходной день",
														 "Рабочий день",
														 "Больничный",
														 "Отпуск",
														 "Прогул",
														 "Удалить изменения дня");

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

		setScheduleContextMenu = new ContextMenu();
		MenuItem setFiveToTwoMenuItem = new MenuItem("Установить расписание 5/2");
		MenuItem setTwoToTwoMenuItem = new MenuItem("Установить расписание 2/2 без ночных смен");
		MenuItem setFourToFourMenuItem = new MenuItem("Установить расписание 2/2 с ночными сменами");

		setFiveToTwoMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				setFiveToTwoSchedule();
			}
		});
		setTwoToTwoMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				setTwoToTwoSchedule();
			}
		});
		setFourToFourMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				setFourToFourSchedule();
			}
		});

		setScheduleContextMenu.getItems().addAll(setFiveToTwoMenuItem, setTwoToTwoMenuItem, setFourToFourMenuItem);
    }

    private VBox getNameAndPhoneNumber(Worker worker) {
    	Text name = new Text(worker.getSurname() + " " + worker.getName() + " " + worker.getSecondName());
    	Text phoneNumber = new Text("Номер телефона: " + worker.getPhoneNumber());

		Text whatIsSelected = new Text();
		if (selectedDay != null) {
			Locale locale = new Locale.Builder().setLanguage("ru").setRegion("RU").build();
			whatIsSelected.setText("Выбрано: " + selectedDay.getDate().getDayOfMonth() + " " + 
												months[selectedDay.getDate().getMonthValue()] + " " + 
												selectedDay.getDate().getYear() + " " + 
												getDayString(selectedDay.getDate(), locale));
		} else {
			whatIsSelected.setText("");
		}
    	
		name.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.isPrimaryButtonDown()) {
					selectedWorker = worker;
					refreshScene();
				} else if (mouseEvent.isSecondaryButtonDown()) {
					tempWorker = worker;
					workerContextMenu.show(name, Side.BOTTOM, 0, 0);
				}
			}
		});

		if (worker.equals(selectedWorker)) {
			name.setUnderline(true);
		}

    	VBox vbox = new VBox();
    	
    	vbox.getChildren().add(name);
    	vbox.getChildren().add(phoneNumber);
		vbox.getChildren().add(whatIsSelected);
    	
    	return vbox;
    }
    
    private HBox addRectangles(List<Day> daysList, Worker worker) {

    	HBox hBox = new HBox();
    	hBox.setSpacing(10);
    	
    	for (int i = 0; i < daysList.size(); i++) {

			VBox rectangleBox = getRectangle(daysList.get(i));
			final int day = i;

			rectangleBox.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if (mouseEvent.isPrimaryButtonDown()) {
						selectedDay = daysList.get(day);
						tempWorker = worker;
						refreshScene();
					} else if (mouseEvent.isSecondaryButtonDown()) {
						if (selectedDay != null && selectedDay.equals(daysList.get(day))) {
							tempWorker = worker;					
							setScheduleContextMenu.show(rectangleBox, Side.BOTTOM, 0, 0);
						}
					}
				}
			});

			if (daysList.get(i).equals(selectedDay)) {
				rectangleBox.setStyle("-fx-border-width: 1pt; -fx-border-color: red");
			}

			hBox.getChildren().add(rectangleBox);
    	}

    	return hBox;
    }
 /*   
	//Возвращает vbox состоящий из 24 квадратов, илюстрирующих часы дня
    private VBox getRectangle(Day day) {
    	VBox rectangleBox = new VBox();
    	rectangleBox.getChildren().add(new Text(String.valueOf(day.getDate().getDayOfMonth())));

    	Rectangle[] rectangles = new Rectangle[24];
    	
    	for (int i = 0; i < rectangles.length; i++) {
    		rectangles[i] = getColoredRectangle(i, day);

			if (day.peekDayStatus().equals(DayStatus.WorkingDay) && (i == day.getStartWorkTime().getHour())) {
				StackPane stackPane = new StackPane();
				
				Text text = new Text();
				text.setText(String.valueOf(day.getStartWorkTime().getHour() + " : " + day.getStartWorkTime().getMinute()));
				text.setStyle("-fx-font-size: 9;");
				
				stackPane.getChildren().addAll(rectangles[i], text);
				rectangleBox.getChildren().add(stackPane);
			} else if (day.peekDayStatus().equals(DayStatus.WorkingDay) && (i == day.getEndWorkTime().getHour())) {
				StackPane stackPane = new StackPane();
				
				Text text = new Text();
				text.setText(String.valueOf(day.getEndWorkTime().getHour() + " : " + day.getEndWorkTime().getMinute()));
				text.setStyle("-fx-font-size: 9;");
				
				stackPane.getChildren().addAll(rectangles[i], text);
				rectangleBox.getChildren().add(stackPane);
			} else {
				StackPane stackPane = new StackPane();
				stackPane.getChildren().add(rectangles[i]);
				rectangleBox.getChildren().add(rectangles[i]);
			}

    	}
		
		Text startEndWorkText = new Text();
		startEndWorkText.setText(String.valueOf(day.getStartWorkTime() + "\n" + day.getEndWorkTime()));

		rectangleBox.getChildren().add(startEndWorkText);

    	return rectangleBox;
    }

 	private Rectangle getColoredRectangle(int hour, Day day) {
		
		Rectangle rectangle = new Rectangle(70, 12);

		switch (day.peekDayStatus()) {
			case NotSet:
				rectangle.setFill(Color.BLACK);
				break;
			case WorkingDay:
				if (day.getStartWorkTime().isBefore(day.getEndWorkTime()) && hour >= day.getStartWorkTime().getHour() && hour <= day.getEndWorkTime().getHour()) {
					rectangle.setFill(Color.GREEN); 
				} else if (day.getStartWorkTime().isAfter(day.getEndWorkTime()) && (hour >= day.getStartWorkTime().getHour() || hour <= day.getEndWorkTime().getHour())) {
					rectangle.setFill(Color.GREEN);
				} else {
					rectangle.setFill(Color.GREY);
				}
				break;
			case DayOff:
				rectangle.setFill(Color.GRAY);
				break;
			case Vacation:
				rectangle.setFill(Color.BISQUE);
				break;
			case SickLeave:
				rectangle.setFill(Color.AQUA);
				break;
			case Absenteeism:
				rectangle.setFill(Color.RED);
				break;
			default:
				break;
		}

		return rectangle;
	}
*/
	private VBox getRectangle(Day day) {

		VBox rectangleBox = new VBox();
		Rectangle[] rectangle = new Rectangle[2];
		StackPane stackPane = null;
		Text text = null;

		rectangleBox.getChildren().add(new Text(String.valueOf(day.getDate().getDayOfMonth() + " " +
																day.getDate().getDayOfWeek())));

		for (int i = 0; i < rectangle.length; i++) {
			stackPane = new StackPane();
			
			if (day.peekDayStatus().equals(DayStatus.WorkingDay)) {
				
				if (i == 0 && day.getStartWorkTime().isBefore(day.getEndWorkTime())) {
					rectangle[i] = getColoredRectangle(day, 100, 50);
					
					text = new Text();
					text.setStyle("-fx-font-size: 28;");
					text.setText(String.valueOf(day.getStartWorkTime().getHour() + ":" + 
												day.getStartWorkTime().getMinute()));
					stackPane.getChildren().addAll(rectangle[i], text);
					
					rectangleBox.getChildren().add(stackPane);		
				} else if (i == 1 && day.getStartWorkTime().isBefore(day.getEndWorkTime())) {
					rectangle[i] = getColoredRectangle(day, 100, 50);
					
					text = new Text();
					text.setStyle("-fx-font-size: 28;");
					text.setText(String.valueOf(day.getEndWorkTime().getHour() + ":" + 
												day.getEndWorkTime().getMinute()));
					stackPane.getChildren().addAll(rectangle[i], text);
					
					rectangleBox.getChildren().add(stackPane);
				} else if (i == 0 && day.getStartWorkTime().isAfter(day.getEndWorkTime())) {
					rectangle[i] = getColoredRectangle(day, 100, 50);
					
					text = new Text();
					text.setStyle("-fx-font-size: 28;");
					text.setText(String.valueOf(day.getStartWorkTime().getHour() + ":" + 
												day.getStartWorkTime().getMinute()));
					stackPane.getChildren().addAll(rectangle[i], text);
					
					rectangleBox.getChildren().add(stackPane);		
				} else if (i == 1 && day.getStartWorkTime().isAfter(day.getEndWorkTime())) {
					rectangle[i] = getColoredRectangle(day, 100, 50);
					
					text = new Text();
					text.setStyle("-fx-font-size: 28;");
					text.setText(String.valueOf(day.getEndWorkTime().getHour() + ":" + 
												day.getEndWorkTime().getMinute()));
					stackPane.getChildren().addAll(rectangle[i], text);
					
					rectangleBox.getChildren().add(stackPane);
				}

			} else {
				Text dayStatusText = new Text();
				dayStatusText.setText(getDayStatusString(day));
				dayStatusText.setRotate(45);

				stackPane.getChildren().addAll(getColoredRectangle(day, 100, 100), dayStatusText);
				rectangleBox.getChildren().add(stackPane);
				break;
			}

		}
		return rectangleBox;
	}

	private Rectangle getColoredRectangle(Day day, int width, int height) {

		Rectangle rectangle = new Rectangle(width, height);

		switch (day.peekDayStatus()) {
			case NotSet:
				rectangle.setFill(Color.BLACK);
				break;
			case WorkingDay:
				rectangle.setFill(Color.GREEN);
				break;
			case DayOff:
				rectangle.setFill(Color.GREY);
				break;
			case SickLeave:
				rectangle.setFill(Color.AQUA);
				break;
			case Vacation:
				rectangle.setFill(Color.BEIGE);
				break;
			case Absenteeism:
				rectangle.setFill(Color.RED);
				break; 
			default:
				break;
		}

		return rectangle;
	}

	private String getDayStatusString(Day day) {
		switch (day.peekDayStatus()) {
			case NotSet:
				return "Не указано";
			case DayOff:
				return "Выходной";
			case Vacation:
				return "Отпуск";
			case SickLeave:
				return "Больничный";
			case Absenteeism:
				return "Прогул";
			default:
				return "";
		}
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

		for (Worker worker : workingPlace.getListOfWorkers()) {
			VBox box = new VBox();

			box.getChildren().add(getNameAndPhoneNumber(worker));
			box.getChildren().add(addRectangles(worker.getScheduleObject().getDaysOfMonth(year, month), worker));
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

	private String getDayString(LocalDate date, Locale locale) {
		DayOfWeek day = date.getDayOfWeek();
		return day.getDisplayName(TextStyle.FULL, locale);
	}

	@FXML
	public void setFiveToTwoSchedule() {
		if (!selectedDay.getScheduleType().equals(ScheduleType.NotSet)) {
			WindowHandler.getInstants().showErrorMessage("Расписание уже задано", "Нельзя указать расписание поверх уже существующего. Пожалуйста сначала удалите старое расписание.");
			return;
		}
		tempWorker.getScheduleObject().setFiveToTwoSchedule(selectedDay);		
		refreshScene();
	}
	@FXML
	public void setTwoToTwoSchedule() {
		if (!selectedDay.getScheduleType().equals(ScheduleType.NotSet)) {
			WindowHandler.getInstants().showErrorMessage("Расписание уже задано", "Нельзя указать расписание поверх уже существующего. Пожалуйста сначала удалите старое расписание.");
			return;
		}
		tempWorker.getScheduleObject().setTwoToTwoSchedule(selectedDay, 1);		
		refreshScene();
	}
	@FXML
	public void setFourToFourSchedule() {
		if (!selectedDay.getScheduleType().equals(ScheduleType.NotSet)) {
			WindowHandler.getInstants().showErrorMessage("Расписание уже задано", "Нельзя указать расписание поверх уже существующего. Пожалуйста сначала удалите старое расписание.");
			return;
		}
		tempWorker.getScheduleObject().setFourToFourSchedule(selectedDay, 1);
		refreshScene();
	}

/* 	@FXML
	public void deleteScheduleFromCurrent() {
		tempWorker.getScheduleObject().deleteSchedule(selectedDay);
		refreshScene();
	}
	@FXML
	public void deleteScheduleq() {
		tempWorker.getScheduleObject().deleteSchedule(new Day(LocalDate.of(2020, 1, 1)));
		refreshScene();
	}
*/
	@FXML
	public void addScheduleFromChoiceBox() {
		switch (addScheduleChoiceBox.getValue()) {
			case "Пять - два":
				setFiveToTwoSchedule();
				break;
			case "Два - два без ночных смен":
				setTwoToTwoSchedule();
				break;
			case "Два - два с ночными сменами":
				setFourToFourSchedule();
				break;
			default:
				break;
		}
	}

	@FXML
	public void deleteSchedule() {
		switch (deleteScheduleChoiceBox.getValue()) {
			case "Удалить расписание с текущего дня":
				tempWorker.getScheduleObject().deleteSchedule(selectedDay);
				refreshScene();
				break;
			case "Удалить расписание полностью" :
				tempWorker.getScheduleObject().deleteSchedule(new Day(LocalDate.of(2020, 1, 1)));
				refreshScene();
				break;
			default:
				break;
		}
	}

	@FXML
	public void changeDayStatus() {
		switch (dayStatusChoiceBox.getValue()) {
			case "Рабочий день":
			try {
				WindowHandler.getInstants().openModalWindow("Добавление рабочих дней", 
								"changeDayStatus", 600, 300);
			} catch (IOException e) {
				e.printStackTrace();
			}

			tempWorker.getScheduleObject().addWorkingDays(selectedDay, dayCounter);
			refreshScene();
			
			tempWorker = null;
			selectedDay = null;
			dayCounter = 0;
			break;
			case "Выходной день":
				try {
					WindowHandler.getInstants().openModalWindow("Добавление выходных дней", 
									"changeDayStatus", 600, 300);
				} catch (IOException e) {
					e.printStackTrace();
				}

				tempWorker.getScheduleObject().addDayOffDays(selectedDay, dayCounter);
				refreshScene();
				
				tempWorker = null;
				selectedDay = null;
				dayCounter = 0;
				break;
			case "Больничный":
				try {
					WindowHandler.getInstants().openModalWindow("Добавление больничного", 
									"changeDayStatus", 600, 300);
				} catch (IOException e) {
					e.printStackTrace();
				}

				tempWorker.getScheduleObject().addSickLeaveDays(selectedDay, dayCounter);
				refreshScene();
				
				tempWorker = null;
				selectedDay = null;
				dayCounter = 0;
				break;
			case "Отпуск":
				try {
					WindowHandler.getInstants().openModalWindow("Добавление отпуска", 
									"changeDayStatus", 600, 300);
				} catch (IOException e) {
					e.printStackTrace();
				}

				tempWorker.getScheduleObject().addVacationDays(selectedDay, dayCounter);
				refreshScene();
				
				tempWorker = null;
				selectedDay = null;
				dayCounter = 0;
				break;
			case "Прогул":
				try {
					WindowHandler.getInstants().openModalWindow("Добавление прогулов", 
									"changeDayStatus", 600, 300);
				} catch (IOException e) {
					e.printStackTrace();
				}

				tempWorker.getScheduleObject().addAbsenteeismDays(selectedDay, dayCounter);
				refreshScene();
				
				tempWorker = null;
				selectedDay = null;
				dayCounter = 0;
				break;
			case "Удалить изменения дня":
				try {
					WindowHandler.getInstants().openModalWindow("Удалить изменения дня", 
									"changeDayStatus", 600, 300);
				} catch (IOException e) {
					e.printStackTrace();
				}

				tempWorker.getScheduleObject().cancelChangeDays(selectedDay, dayCounter);
				refreshScene();
				
				tempWorker = null;
				selectedDay = null;
				dayCounter = 0;
				break;
			default:
				break;
		}
	}

	@FXML
	public void changeSingleDay() {
		technicalDay = new Day(selectedDay.getDate());
		technicalDay.setScheduleType(selectedDay.getScheduleType());
		technicalDay.pushDayStatus(selectedDay.peekDayStatus());
		technicalDay.setStartWorkTime(selectedDay.getStartWorkTime());
		technicalDay.setEndWorkTime(selectedDay.getEndWorkTime());

		try {
			WindowHandler.getInstants().openModalWindow("Изменение отдельного дня", 
							"changeSingleDay", 600, 300);
		} catch (IOException e) {
			e.printStackTrace();
		}

		tempWorker.getScheduleObject().changeSingleDay(technicalDay);

		refreshScene();

		technicalDay = null;
		dayCounter = 0;

	}
}
