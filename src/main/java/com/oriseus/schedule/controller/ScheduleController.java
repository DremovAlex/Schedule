package com.oriseus.schedule.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.oriseus.schedule.App;
import com.oriseus.schedule.model.Day;
import com.oriseus.schedule.model.DayStatus;
import com.oriseus.schedule.model.Person;
import com.oriseus.schedule.model.ScheduleType;
import com.oriseus.schedule.model.WorkingPlace;

import Utils.NotDefineValueException;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ScheduleController {
    
	private final String NAMES_OF_MONTHS[] = {
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
			"Декабрь"
	};
	private final String NAMES_OF_WEEK[] = {
			"Пн",
			"Вт",
			"Ср",
			"Чт",
			"Пт",
			"Сб",
			"Вс"
	};
	
	private Color workingDayColor;
	private Color dayOffColor;
	private Color sickLeaveColor;
	private Color vacationColor;
	private Color absenteismColor;
	private Color overTimeColor;
	private Color notSetColor;
	
	private boolean isFridayShortDay;
	private int notWorkingHoursWorkingDay;
	private int notWorkingHoursShift;
	
	@FXML
	ToolBar toolBar;
	
	@FXML
	Button addWorkingPlaceButton;
	
	@FXML
	TabPane tabPane;
	
	@FXML
	AnchorPane anchorPane;
	
	@FXML
	ContextMenu rectangleContextMenu;
	@FXML
	ContextMenu personContextMenu;
	
	@FXML
	MenuItem selectFile;
	@FXML
	MenuItem selectDirectoryToCreateFile;
	@FXML
	MenuItem saveProgress;
	@FXML
	MenuItem loadProgress;
	
	@FXML
	MenuItem settingsItem;
	
	private List<WorkingPlace> workingPlaces = new ArrayList<WorkingPlace>();
	
	protected static String newWorkingPlaceName;
	protected static Person newPerson;
	protected static Day tempDay;
	
	private Calendar calendar = new GregorianCalendar();
	private Integer monthCount;
	private Integer yearCount;
	private Integer tempDayCountInMonth;
	private Person tempPerson;
	protected static Integer tempCountDay;
	
	private String pathToSaveFileString;
	private File saveFile;
	
	private final int SCREEN_WIGHT = 350;
	private final int SCREEN_HIGHT = 1440;
	
	// Добавить новую вкладку с рабочим местом
	public void addNewWorkingPlaceButton() throws IOException {
        startNewWindow("addNewWorkingPlace", "Создание нового рабочего места", 650, 300, false);
        
        if (newWorkingPlaceName != null && !newWorkingPlaceName.isBlank()) {
        	WorkingPlace workingPlace = new WorkingPlace(newWorkingPlaceName);
        	workingPlaces.add(workingPlace);
        	newWorkingPlaceName = null;
        	
        	Tab tab = new Tab(workingPlace.getWorkingPlaceName());
        	tabPane.getTabs().add(tab);
        } else {
        	return;
        }
	}
	
	// Удалить выбранную вкладку с рабочим местом
	public void deleteWorkingPlace() {
		Optional<ButtonType> option = showAlertMessage("Удаление вкладки", "Вы уверенны что хотите удалить вкладку?", "Нажмите Ок для удаления или Отмену для возврата.");
		
		if (option.get() == ButtonType.OK) {
			workingPlaces.remove(tabPane.getSelectionModel().getSelectedIndex());
			
			Tab currentTab = tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex());
			closeTab(currentTab);
		} else {
			return;
		}
	}
	
	// Добавить нового работника. Номер телефона не обязателен. День начала работы указывается потом
	public void addNewWorker() throws IOException {
		startNewWindow("addNewWorker", "Добавление нового сотрудника", 600, 550, false);
        
        if (newPerson != null) {
        	Person person = new Person(newPerson.getName(), newPerson.getSurname(), newPerson.getSecondName(), newPerson.getPhoneNumber());
        	workingPlaces.get(tabPane.getSelectionModel().getSelectedIndex()).addWorker(person);
        	newPerson = null;       	
        } else {
        	return;
        }
        
        try {
			addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
		} catch (NotDefineValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changeWorker(Person person) throws IOException {
		
		Optional<ButtonType> option = showAlertMessage("Удаление вкладки", "Вы уверенны что хотите изменить данные сотрудника?", "Нажмите Ок для изменения или Отмену для возврата.");

		if (option.get() == ButtonType.OK) {
			newPerson = person;
			startNewWindow("changeWorker", "Изменить данные сотрудника", 650, 520, false);
								
			for (int i = 0; i < workingPlaces.get(tabPane.getSelectionModel().getSelectedIndex()).getWorkers().size(); i++) {
				if (workingPlaces.get(tabPane.getSelectionModel().getSelectedIndex()).getWorkers().get(i).equals(newPerson)) {
					workingPlaces.get(tabPane.getSelectionModel().getSelectedIndex()).getWorkers().get(i).setName(newPerson.getName());
					workingPlaces.get(tabPane.getSelectionModel().getSelectedIndex()).getWorkers().get(i).setSurname(newPerson.getSurname());
					workingPlaces.get(tabPane.getSelectionModel().getSelectedIndex()).getWorkers().get(i).setSecondName(newPerson.getSecondName());
					workingPlaces.get(tabPane.getSelectionModel().getSelectedIndex()).getWorkers().get(i).setPhoneNumber(newPerson.getPhoneNumber());
					break;
				}
			}
			tempPerson = null;
			newPerson = null;
			
			try {
				addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			} else {
				return;
			}
	}
	
	public void changePhoneNumber(Person person) throws IOException {
		Optional<ButtonType> option = showAlertMessage("Удаление вкладки", "Вы уверенны что хотите изменить номер телефона сотрудника?", "Нажмите Ок для удаления или Отмену для возврата.");
		
		if (option.get() == ButtonType.OK) {
			newPerson = person;
			
			startNewWindow("changePhoneNumber", "Изменить номер телефона сотрудника", 650, 300, false);
			
			for (int i = 0; i < workingPlaces.get(tabPane.getSelectionModel().getSelectedIndex()).getWorkers().size(); i++) {
				if (workingPlaces.get(tabPane.getSelectionModel().getSelectedIndex()).getWorkers().get(i).equals(newPerson)) {
					workingPlaces.get(tabPane.getSelectionModel().getSelectedIndex()).getWorkers().get(i).setPhoneNumber(newPerson.getPhoneNumber());
					break;
				}
			}
			tempPerson = null;
			newPerson = null;
			
			try {
				addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} else {
			return;
		}
	}
	
	public void deleteWorker(Person person) {
		Optional<ButtonType> optional = showAlertMessage("Удаление раболтника", "Вы действительно хотите удалить данного сотрудника?", "Нажмите Ок для удаления или Отмена для выхода.");
		if (optional.get().equals(ButtonType.OK)) {
			for (int i = 0; i < workingPlaces.get(tabPane.getSelectionModel().getSelectedIndex()).getWorkers().size(); i++) {
				if (workingPlaces.get(tabPane.getSelectionModel().getSelectedIndex()).getWorkers().get(i).equals(person)) {
					workingPlaces.get(tabPane.getSelectionModel().getSelectedIndex()).getWorkers().remove(i);
					break;
				}
			}
			try {
				addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void selectFile() throws FileNotFoundException, IOException {
		FileChooser fileChooser = new FileChooser();
		saveFile = fileChooser.showOpenDialog(new Stage());
		writePath(saveFile.getAbsolutePath());
		pathToSaveFileString = saveFile.getAbsolutePath();
	}
	
	public void selectDirectoryToCreateFile() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File dir = directoryChooser.showDialog(new Stage());
		if (dir != null) {
			createFile(dir);
		}
	}
	
	public void createFile(File file) {
		saveFile = new File(file, "SavedFile");
		try {
			saveFile.createNewFile();
			writePath(saveFile.getAbsolutePath());
			pathToSaveFileString = saveFile.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveProgress() {
		saveFile = new File(pathToSaveFileString);
		if (saveFile == null || pathToSaveFileString == null || pathToSaveFileString.isBlank()) {
			showErrorMessage("Ошибка сохранения", "Файл отсутствует!", "Выберите файл перед тем как сохранять");
			return;
		}
		FileOutputStream outputStream;
		ObjectOutputStream objectOutputStream;
		try {
			outputStream = new FileOutputStream(saveFile);
			objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(workingPlaces);
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadProgress() {
		tabPane.getTabs().clear();
		saveFile = new File(pathToSaveFileString);
		if (saveFile == null || pathToSaveFileString == null || pathToSaveFileString.isBlank()) {
			showErrorMessage("Ошибка загрузки", "Файл отсутствует!", "Выберите файл перед тем как загружать");
			return;
		}
		FileInputStream fileInputStream;
		ObjectInputStream objectInputStream;
		try {
			fileInputStream = new FileInputStream(saveFile);
			objectInputStream = new ObjectInputStream(fileInputStream);
			List<WorkingPlace> list = (List<WorkingPlace>) objectInputStream.readObject();
			workingPlaces =  list;
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("ERROR");
		}
		try {
			printAfterLoad();
		} catch (NotDefineValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void settings() throws IOException {
		startNewWindow("settings", "Настройки", 1000, 1100, false);
		loadProperties();
		for (WorkingPlace workingPlace : workingPlaces) {
			for (Person person : workingPlace.getWorkers()) {
				try {
					person.getScheduleObject(yearCount).changeAfterReloadSettings();
				} catch (NotDefineValueException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
		} catch (NotDefineValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @FXML
    public void initialize() throws FileNotFoundException, IOException {
    	tabPane.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
    	tabPane.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
    	
    	pathToSaveFileString = readPath();
    	monthCount = calendar.get(Calendar.MONTH);
    	yearCount = calendar.get(Calendar.YEAR);
    	
    	loadProperties();
    	
    	rectangleContextMenu = new ContextMenu();
    	MenuItem startWorkMenuItemFiveToTwo = new MenuItem("Указать расписание 5/2");
    	MenuItem startWorkMenuItemTwoToTwo = new MenuItem("Указать расписание 2/2");
    	MenuItem startWorkMenuItemFourToFour = new MenuItem("Указать расписание 4/4");
    	MenuItem cancelWorkMenuItem = new MenuItem("Удалить расписание");
    	MenuItem startVacationMenuItem = new MenuItem("Укажите начало отпуска");
    	MenuItem cancelVacationMenuItem = new MenuItem("Отменить отпуск");
    	MenuItem startSickLeaveMenuItem = new MenuItem("Укажите начало больничного");
    	MenuItem cancelSickLeaveMenuItem = new MenuItem("Отменить больничный");
    	MenuItem startAbsenteismMenuItem = new MenuItem("Указать прогул");
    	MenuItem cancelAbsenteisMenuItem = new MenuItem("Отменить прогул");
    	MenuItem setOvertimeMenuItem = new MenuItem("Указать переработку");
    	MenuItem cancelOvertimeMenuItem = new MenuItem("Отменить переработку");
    	MenuItem changeDayMenuItem = new MenuItem("Изменить день");
    	
    	startWorkMenuItemFiveToTwo.setOnAction((event) -> {
    		try {
				if (tempPerson.getScheduleObject(yearCount).getScheduleType().equals(ScheduleType.NOT_SET)) {    			
					LocalDate startWork = LocalDate.of(yearCount, monthCount + 1, tempDayCountInMonth + 1);    	    
					LocalDate firstDayAtWork = tempPerson.getScheduleObject(yearCount).setSchedule(startWork, ScheduleType.FIVE_TO_TWO);
					
					for (int i = 1; i < 2034 - (yearCount + 1); i++) {
						firstDayAtWork = tempPerson.getScheduleObject(yearCount + i).setSchedule(firstDayAtWork, ScheduleType.FIVE_TO_TWO);
					}
					
					tempDayCountInMonth = null;
					tempPerson = null;
					addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
				} else {		
					showErrorMessage("Ошибка", "Ошибка добавления расписания.", "У данного сотрудника уже указано расписание, сначала удалите текущее расписание.");
				    
				    tempDayCountInMonth = null;
					tempPerson = null;
					addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
				}
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	});
    	startWorkMenuItemTwoToTwo.setOnAction((event) -> {
    		try {
				if (tempPerson.getScheduleObject(yearCount).getScheduleType().equals(ScheduleType.NOT_SET)) {   
				    LocalDate startWork = LocalDate.of(yearCount, monthCount + 1, tempDayCountInMonth + 1);    	    
					LocalDate firstDayAtWork = tempPerson.getScheduleObject(yearCount).setSchedule(startWork, ScheduleType.TWO_TO_TWO);
					
					for (int i = 1; i < 2034 - (yearCount + 1); i++) {
						firstDayAtWork = tempPerson.getScheduleObject(yearCount + i).setSchedule(firstDayAtWork, ScheduleType.TWO_TO_TWO);
					}					tempDayCountInMonth = null;
					
					tempPerson = null;
					addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
				} else {
					showErrorMessage("Ошибка", "Ошибка добавления расписания.", "У данного сотрудника уже указано расписание, сначала удалите текущее расписание.");
				    
				    tempDayCountInMonth = null;
					tempPerson = null;
					addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
				}
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    	startWorkMenuItemFourToFour.setOnAction((event) -> {
    		try {
				if (tempPerson.getScheduleObject(yearCount).getScheduleType().equals(ScheduleType.NOT_SET)) {   
				    LocalDate startWork = LocalDate.of(yearCount, monthCount + 1, tempDayCountInMonth + 1);    	    
					LocalDate firstDayAtWork = tempPerson.getScheduleObject(yearCount).setSchedule(startWork, ScheduleType.FOUR_TO_FOUR);
					
					for (int i = 1; i < 2034 - (yearCount + 1); i++) {
						firstDayAtWork = tempPerson.getScheduleObject(yearCount + i).setSchedule(firstDayAtWork, ScheduleType.FOUR_TO_FOUR);
					}					tempDayCountInMonth = null;
					
					tempPerson = null;
					addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
				} else {
					showErrorMessage("Ошибка", "Ошибка добавления расписания.", "У данного сотрудника уже указано расписание, сначала удалите текущее расписание.");
				    
				    tempDayCountInMonth = null;
					tempPerson = null;
					addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
				}
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    	cancelWorkMenuItem.setOnAction((event) -> {
    		Optional<ButtonType> optional = showAlertMessage("Удаление графика", "Вы действительно хотите удалить график данного сотрудника?", "Нажмите Ок для удаления или Отмена для выхода.");
    		if (optional.get().equals(ButtonType.OK)) {
	    		try {
	    			for (int i = 0; i < 9; i++) {
	    				tempPerson.getScheduleObject(2024 + i).cancelSchedule();
					}
				} catch (NotDefineValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		tempDayCountInMonth = null;
	    		tempPerson = null;
	    		try {
					addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
				} catch (NotDefineValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
    		} else {
				return;
			}
    	});
    	startVacationMenuItem.setOnAction((event) -> {
    	    LocalDate startWork = LocalDate.of(yearCount, monthCount + 1, tempDayCountInMonth + 1);
    	    
    	    try {
				startNewWindow("addQuantityOfDays", "Укажите количество дней", 600, 300, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	    
    	    try {
				tempPerson.getScheduleObject(yearCount).setStartVacation(startWork, tempCountDay);
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    		tempDayCountInMonth = null;
    		tempPerson = null;
    		tempCountDay = null;
    		try {
				addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    	cancelVacationMenuItem.setOnAction((event) -> {
    	    LocalDate startWork = LocalDate.of(yearCount, monthCount + 1, tempDayCountInMonth + 1);
    	    
    	    try {
				startNewWindow("addQuantityOfDays", "Укажите количество дней", 600, 300, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	    
    	    try {
				tempPerson.getScheduleObject(yearCount).cancelVacation(startWork, tempCountDay);
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    		tempDayCountInMonth = null;
    		tempPerson = null;
    		tempCountDay = null;
    		try {
				addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    	startSickLeaveMenuItem.setOnAction((event) -> {
    	    LocalDate startWork = LocalDate.of(yearCount, monthCount + 1, tempDayCountInMonth + 1);
    	    
    	    try {
				startNewWindow("addQuantityOfDays", "Укажите количество дней", 600, 300, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	    
    	    try {
				tempPerson.getScheduleObject(yearCount).setStartSickLeave(startWork, tempCountDay);
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    		tempDayCountInMonth = null;
    		tempPerson = null;
    		tempCountDay = null;
    		try {
				addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    	cancelSickLeaveMenuItem.setOnAction((event) -> {
    	    LocalDate startWork = LocalDate.of(yearCount, monthCount + 1, tempDayCountInMonth + 1);
    	    
    	    try {
				startNewWindow("addQuantityOfDays", "Укажите количество дней", 600, 300, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	    
    	    try {
				tempPerson.getScheduleObject(yearCount).cancelSickLeave(startWork, tempCountDay);
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    		tempDayCountInMonth = null;
    		tempPerson = null;
    		tempCountDay = null;
    		try {
				addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    	startAbsenteismMenuItem.setOnAction((event) -> {
    	    LocalDate startWork = LocalDate.of(yearCount, monthCount + 1, tempDayCountInMonth + 1);
    	    
    	    try {
				startNewWindow("addQuantityOfDays", "Укажите количество дней", 600, 300, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	    
    	    try {
				tempPerson.getScheduleObject(yearCount).setStartAbsenteism(startWork, tempCountDay);
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    		tempDayCountInMonth = null;
    		tempPerson = null;
    		tempCountDay = null;
    		try {
				addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    	cancelAbsenteisMenuItem.setOnAction((event) -> {
    	    LocalDate startWork = LocalDate.of(yearCount, monthCount + 1, tempDayCountInMonth + 1);
    	    
    	    try {
				startNewWindow("addQuantityOfDays", "Укажите количество дней", 600, 300, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	    
    	    try {
				tempPerson.getScheduleObject(yearCount).cancelAbsenteism(startWork, tempCountDay);
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    		tempDayCountInMonth = null;
    		tempPerson = null;
    		tempCountDay = null;
    		try {
				addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    	setOvertimeMenuItem.setOnAction((event) -> {
    	    LocalDate startWork = LocalDate.of(yearCount, monthCount + 1, tempDayCountInMonth + 1);
    	    
    	    try {
				startNewWindow("addQuantityOfDays", "Укажите количество часов", 600, 300, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	    
    	    try {
				tempPerson.getScheduleObject(yearCount).setOvertime(startWork, tempCountDay);
			} catch (NotDefineValueException e) {
				showErrorMessage("Ошибка добавления", "Выбран не рабочий день", "Для добавления времени переработки выберите \"Изменить день\"");
				e.printStackTrace();
			}

    		tempDayCountInMonth = null;
    		tempPerson = null;
    		tempCountDay = null;
    		try {
				addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    	cancelOvertimeMenuItem.setOnAction((event) -> {
    	    LocalDate startWork = LocalDate.of(yearCount, monthCount + 1, tempDayCountInMonth + 1);
    	    
    	    try {
				tempPerson.getScheduleObject(yearCount).cancelOvertime(startWork);
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    		tempDayCountInMonth = null;
    		tempPerson = null;
    		tempCountDay = null;
    		try {
				addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    	changeDayMenuItem.setOnAction((event) -> {
    		
    		LocalDate startWork = LocalDate.of(yearCount, monthCount + 1, tempDayCountInMonth + 1);
    		try {
				tempDay = tempPerson.getScheduleObject(yearCount).getDaysOfYear().get(startWork.getDayOfYear() - 1);
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    		try {
				startNewWindow("changeDay", "Редактирование дня", 600, 420, false);
			} catch (IOException e) {
				e.printStackTrace();
			}

    		try {
    			if (tempDay != null) {
    				tempPerson.getScheduleObject(yearCount).changeDay(startWork, tempDay);
    			}
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		tempDayCountInMonth = null;
    		tempPerson = null;
    		tempCountDay = null;
    		try {
				addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
			} catch (NotDefineValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    	rectangleContextMenu.getItems().addAll(startWorkMenuItemFiveToTwo, 
    										   startWorkMenuItemTwoToTwo,
    										   startWorkMenuItemFourToFour,
    										   cancelWorkMenuItem, 
    										   startVacationMenuItem,
    										   cancelVacationMenuItem,
    										   startSickLeaveMenuItem,
    										   cancelSickLeaveMenuItem,
    										   startAbsenteismMenuItem,
    										   cancelAbsenteisMenuItem,
    										   setOvertimeMenuItem,
    										   cancelOvertimeMenuItem,
    										   changeDayMenuItem);
    	
    	personContextMenu = new ContextMenu();
    	MenuItem changePersonDetailsMenuItem = new MenuItem("Изменить данные сотрудника");
    	MenuItem changePhoneNumberMenuItem = new MenuItem("Изменить номер телефона");
    	MenuItem deletePersonMenuItem = new MenuItem("Удалить сотрудника");
    	  	
    	changePersonDetailsMenuItem.setOnAction((event) -> {
    		try {
				changeWorker(tempPerson);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    	changePhoneNumberMenuItem.setOnAction((event) -> {
    		try {
				changePhoneNumber(tempPerson);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    	deletePersonMenuItem.setOnAction((event) -> {
    		deleteWorker(tempPerson);
    	});
    	
    	personContextMenu.getItems().addAll(changePersonDetailsMenuItem,
    										changePhoneNumberMenuItem,
    										deletePersonMenuItem);
    	
    }
    
    // Удаляет вкладку
    private void closeTab(Tab tab) {
        EventHandler<Event> handler = tab.getOnClosed();
        if (null != handler) {
            handler.handle(null);
        } else {
            tab.getTabPane().getTabs().remove(tab);
        }
    }
/*
 *  Возвращает HBox с кнопками вперед и назад, именем месяца    
 */
    //Надо поменять textarea
    private HBox addButtonsHBox(int nameOfMounth) {
    	HBox managerHBox = new HBox();
    	
    	Button leftButton = new Button();
    	leftButton.setText(" < ");
    	TextArea textArea = new TextArea();
    	textArea.setText(NAMES_OF_MONTHS[monthCount] + " " + yearCount);
    	Button rightButton = new Button();
    	rightButton.setText(" > ");
    	
    	leftButton.setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (monthCount > 0) { 
					--monthCount;
				} else if (monthCount == 0 && yearCount > 2024) {
					--yearCount;
					monthCount = 11;
				}	
				textArea.setText(NAMES_OF_MONTHS[monthCount]);
			        
				try {
					addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
				} catch (NotDefineValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
    	rightButton.setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (monthCount < 11) { 
					++monthCount;
				} else if (monthCount == 11 && yearCount < 2035) {
					++yearCount;
					monthCount = 0;
				}	
				textArea.setText(NAMES_OF_MONTHS[monthCount]);
					
				try {
					addContentToTab(tabPane.getSelectionModel().getSelectedIndex());
				} catch (NotDefineValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
    	
    	textArea.setMinSize(200, 10);
    	textArea.setPrefSize(200, 10);
    	leftButton.setPrefSize(50, 10);
    	rightButton.setPrefSize(50, 10);
    	
    	managerHBox.getChildren().add(leftButton);
    	managerHBox.getChildren().add(textArea);
    	managerHBox.getChildren().add(rightButton);
    	
    	managerHBox.setSpacing(10.0);
    	
    	return managerHBox;
    }
    
    /*
     * 	Метод обновляет сцену, перерисовывает все по новому, необходимо при изменении внутри сцены, для мгновенного отображения.
     */
    private void addContentToTab(int index) throws NotDefineValueException {
    	
		VBox workersVBox = new VBox();
        workersVBox.getChildren().add(addButtonsHBox(monthCount));
        List<Person> list = workingPlaces.get(index).getWorkers();
        
        for (Person person : list) {
        	workersVBox.getChildren().add(addWorkers(person));
        }
        
        anchorPane.setPrefSize(SCREEN_HIGHT, SCREEN_WIGHT * list.size());
        tabPane.getTabs().get(index).setContent(workersVBox);
    }
    
    private void printAfterLoad() throws NotDefineValueException {
    	Tab tab;
    	for (WorkingPlace workingPlace : workingPlaces) {
    		tab = new Tab();
    		tab.setText(workingPlace.getWorkingPlaceName());
    		tabPane.getTabs().add(tab);
    	}
    	for (int i = 0; i < tabPane.getTabs().size(); i++) {
    		addContentToTab(i);
    	}
    }
    
    /*
     * 	Возвращает VBox, внутри имя, фамилия, отчество работника и VBox с датами, квадратами и временем работы. 
     */
    private VBox addWorkers(Person person) throws NotDefineValueException {
    	VBox workerBox = new VBox();
    	workerBox.setSpacing(5.0);
    	
    	Text nameText = new Text();
    	nameText.setFont(Font.font(18));
    	nameText.setText(person.getName() + " " + person.getSurname() + " " + person.getSecondName() + " Номер телефона: " + person.getPhoneNumber());
		
    	nameText.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				personContextMenu.show(nameText, Side.BOTTOM, 0, 0);	
				tempPerson = person;
			}
		});
    	
    	workerBox.getChildren().add(nameText);    	
    	workerBox.getChildren().add(addRectangleSchedule(person));
    	
    	return workerBox;
	}
    
    /*
     * 	Возвращает VBox состоящий из двух HBox. Первый состоит из вбоксов, каждый содержит в себе номер дня, название дня недели и двух квадратов.
     * 	Квадраты илюстрируют дневную и ночную смену.
     * 	Второй HBox содержит в себе две строки, общее время работы за месяц и общее время переработок.
     */
    private VBox addRectangleSchedule(Person person) throws NotDefineValueException {
    	VBox rectanglesVBox = new VBox();
    	HBox scheduleBox = new HBox();
    	VBox textBox = new VBox();
    	
    	rectanglesVBox.setSpacing(10.0);
    	scheduleBox.setSpacing(10);
    	textBox.setSpacing(10);
    	
    	Text dayShift = new Text("Дневная смена");
    	Text nightShift = new Text("Ночная смена");
    	Text startWorkTimeText = new Text("Начало работы");
    	Text endWorkTimeText = new Text("Окончание работы");
    	Text workTimeText = new Text("Время работы");
    	Text overTimeText = new Text("Время переработки");
    	
    	textBox.getChildren().add(new Text(""));
    	textBox.getChildren().add(new Text(""));
    	textBox.getChildren().add(dayShift);
    	textBox.getChildren().add(nightShift);
    	textBox.getChildren().add(startWorkTimeText);
    	textBox.getChildren().add(endWorkTimeText);
    	textBox.getChildren().add(workTimeText);
    	textBox.getChildren().add(overTimeText);
    	
    	scheduleBox.getChildren().add(textBox);
    	
    	Text[] dates = new Text[person.getScheduleObject(yearCount).getDaysInMonth(monthCount + 1).length];
    	Day[] days = person.getScheduleObject(yearCount).getDaysInMonth(monthCount + 1);
    	Rectangle[] rectangles1 = getDaysRectangles(person.getScheduleObject(yearCount).getDaysInMonth(monthCount + 1), person.getScheduleObject(yearCount).getScheduleType());
    	Rectangle[] rectangles2 = getNightRectangles(person.getScheduleObject(yearCount).getDaysInMonth(monthCount + 1), person.getScheduleObject(yearCount).getScheduleType());
    	
    	for (int i = 0; i < rectangles1.length; i++) {
    		final int fuckStupidJava = i;

    		rectangles1[fuckStupidJava].setOnMouseClicked(new EventHandler<Event>() {
    			@Override
    			public void handle(Event event) {
    				rectangleContextMenu.show(rectangles1[fuckStupidJava], Side.BOTTOM, 0, 0);
    				tempDayCountInMonth = fuckStupidJava;
    				tempPerson = person;
    			}
    		});
    		rectangles2[fuckStupidJava].setOnMouseClicked(new EventHandler<Event>() {
    			@Override
    			public void handle(Event event) {
    				rectangleContextMenu.show(rectangles1[fuckStupidJava], Side.BOTTOM, 0, 0);
    				tempDayCountInMonth = fuckStupidJava;
    				tempPerson = person;
    			}
    		});
    		
    		dates[i] = new Text(days[i].getDay().getDayOfMonth() + "\n " + NAMES_OF_WEEK[days[i].getDay().getDayOfWeek().getValue() - 1]);
    		Text startWork = new Text();
    		Text endWork = new Text();
    		Text hoursOfWork = new Text(String.valueOf(days[i].getHoursOfWork()));
    		Text overTime = new Text(String.valueOf(days[i].getHoursOfOvertime()));
    		
    		if (days[i].getStartWork() != null && days[i].getEndWork() != null) {
    			startWork.setText(days[i].getStartWork().toString());
    			endWork.setText(days[i].getEndWork().toString());
    		} else {
    			startWork.setText("N/A");
    			endWork.setText("N/A");
    			hoursOfWork.setText("0");
    		}
    		
    		VBox vBox = new VBox();
    		vBox.setSpacing(10);
    		vBox.getChildren().add(dates[i]);
    		vBox.getChildren().add(rectangles1[i]);
    		vBox.getChildren().add(rectangles2[i]);
    		vBox.getChildren().add(startWork);
    		vBox.getChildren().add(endWork);
    		vBox.getChildren().add(hoursOfWork);
    		vBox.getChildren().add(overTime);
    		scheduleBox.getChildren().add(vBox);
    	}
    	
    	rectanglesVBox.getChildren().add(scheduleBox);
    	rectanglesVBox.getChildren().add(getHoursInMonth(person.getScheduleObject(yearCount).getDaysInMonth(monthCount + 1)));
    	
    	return rectanglesVBox;
    }
    
    // Возвращает строку с часами работы и переработки
    private HBox getHoursInMonth(Day[] days) {
    	HBox hoursHBox = new HBox();
    	int hours = 0;
    	int overtimeHours = 0;
    	
    	for (Day day : days) {
    		if (day.peekDayStatus().equals(DayStatus.WORKING_DAY) || day.peekDayStatus().equals(DayStatus.OVER_TIME)) {
    			hours += day.getHoursOfWork();
    			overtimeHours += day.getHoursOfOvertime();
    		}
    	}
    	
    	Text hoursText = new Text("Общее количество часов в месяц: " + hours);
    	Text overtimeHoursText = new Text("Общее количество часов переработок в месяц: " + overtimeHours);
    	
    	hoursHBox.setSpacing(10.0);
    	hoursHBox.getChildren().add(hoursText);
    	hoursHBox.getChildren().add(overtimeHoursText);
    	return hoursHBox;
    }
    
    /*
     * 	Возвращает массив с квадратами, илюстрирующими дневные смены.
     */
    private Rectangle[] getDaysRectangles(Day[] days, ScheduleType scheduleType) {
    	Rectangle[] rectangles = new Rectangle[days.length];
    	for (int i = 0; i < days.length; i++) {
    		Rectangle rectangle = new Rectangle(30, 30);
    		
    		if ((days[i].peekDayStatus().equals(DayStatus.WORKING_DAY)) && days[i].isDayShift()) {
    			rectangle.setFill(getColorDayStatus(DayStatus.WORKING_DAY));
    		} else if ((days[i].peekDayStatus().equals(DayStatus.WORKING_DAY)) && days[i].isNightShift()) {
    			rectangle.setFill(getColorDayStatus(DayStatus.DAY_OFF));
    		} else if ((days[i].peekDayStatus().equals(DayStatus.WORKING_DAY)) && days[i].isFiveToTwo()) {
    			rectangle.setFill(getColorDayStatus(DayStatus.WORKING_DAY));
    		} else if (days[i].peekDayStatus().equals(DayStatus.OVER_TIME) && days[i].isNightShift()) {
    			rectangle.setFill(getColorDayStatus(DayStatus.DAY_OFF));
    		} else if (days[i].peekDayStatus().equals(DayStatus.OVER_TIME)) {
        			rectangle.setFill(getColorDayStatus(DayStatus.OVER_TIME));
    		} else if (days[i].peekDayStatus().equals(DayStatus.FRIDAY_WORKING_DAY) && days[i].isFiveToTwo()) {
    			rectangle.setFill(getColorDayStatus(DayStatus.WORKING_DAY));	
    		} else if (days[i].peekDayStatus().equals(DayStatus.DAY_OFF)) {
    			rectangle.setFill(getColorDayStatus(DayStatus.DAY_OFF)); 	
    		} else if (days[i].peekDayStatus().equals(DayStatus.OVER_TIME)) {
    			rectangle.setFill(overTimeColor);
    		} else if (days[i].peekDayStatus().equals(DayStatus.VACATION)) {
    			rectangle.setFill(getColorDayStatus(DayStatus.VACATION)); 
    		} else if (days[i].peekDayStatus().equals(DayStatus.SICK_LEAVE)) {
    			rectangle.setFill(getColorDayStatus(DayStatus.SICK_LEAVE));
    		} else if (days[i].peekDayStatus().equals(DayStatus.ABSENTEEISM)) {
    			rectangle.setFill(getColorDayStatus(DayStatus.ABSENTEEISM));
    		} else {
    			rectangle.setFill(getColorDayStatus(DayStatus.NOT_SET));
    		}
	
    		rectangles[i] = rectangle;	
    	}
    	return rectangles;
    }
    
    /*
     * 	Возвращает массив квадратов, илюстрирующими ночные смены.
     */
    private Rectangle[] getNightRectangles(Day[] days, ScheduleType scheduleType) {
    	Rectangle[] rectangles = new Rectangle[days.length];
    	for (int i = 0; i < days.length; i++) {
    		Rectangle rectangle = new Rectangle(30, 30);

    		if ((days[i].peekDayStatus().equals(DayStatus.WORKING_DAY)) && days[i].isNightShift()) {
    			rectangle.setFill(getColorDayStatus(DayStatus.WORKING_DAY));
    		} else if ((days[i].peekDayStatus().equals(DayStatus.WORKING_DAY)) && days[i].isDayShift()) {
    			rectangle.setFill(getColorDayStatus(DayStatus.DAY_OFF));
    		} else if ((days[i].peekDayStatus().equals(DayStatus.WORKING_DAY)) && days[i].isFiveToTwo()) {
    			rectangle.setFill(getColorDayStatus(DayStatus.DAY_OFF));
    		} else if (days[i].peekDayStatus().equals(DayStatus.FRIDAY_WORKING_DAY)) {
    			rectangle.setFill(getColorDayStatus(DayStatus.DAY_OFF));
    		} else if (days[i].peekDayStatus().equals(DayStatus.OVER_TIME) && days[i].isFiveToTwo()) {
    			rectangle.setFill(getColorDayStatus(DayStatus.DAY_OFF));
    		} else if (days[i].peekDayStatus().equals(DayStatus.OVER_TIME) && days[i].isDayShift()) {
    			rectangle.setFill(getColorDayStatus(DayStatus.DAY_OFF));
    		} else if (days[i].peekDayStatus().equals(DayStatus.OVER_TIME) && days[i].isNightShift()) {
    			rectangle.setFill(getColorDayStatus(DayStatus.OVER_TIME));
    		} else if (days[i].peekDayStatus().equals(DayStatus.DAY_OFF)) {
    			rectangle.setFill(getColorDayStatus(DayStatus.DAY_OFF)); 	
    		} else if (days[i].peekDayStatus().equals(DayStatus.OVER_TIME)) {
    			rectangle.setFill(getColorDayStatus(DayStatus.OVER_TIME));
    		} else if (days[i].peekDayStatus().equals(DayStatus.VACATION)) {
    			rectangle.setFill(getColorDayStatus(DayStatus.VACATION)); 
    		} else if (days[i].peekDayStatus().equals(DayStatus.SICK_LEAVE)) {
    			rectangle.setFill(getColorDayStatus(DayStatus.SICK_LEAVE));
    		} else if (days[i].peekDayStatus().equals(DayStatus.ABSENTEEISM)) {
    			rectangle.setFill(getColorDayStatus(DayStatus.ABSENTEEISM));
    		} else {
    			rectangle.setFill(getColorDayStatus(DayStatus.NOT_SET));
    		}
    	rectangles[i] = rectangle;	
    	}
    	return rectangles;
    }
    
    /*
     * 	Открывает побочное окно, что бы пользователь смог внести какие либо данные в программу.
     * 	@fileName - имя fxml файла без расширения.
     * 	@title - строка поясняющая пользователю, как необходимо взаимодействовать с окном.
     */
    private void startNewWindow(String fileName, String title, double wight, double height, boolean isResizable) throws IOException {	    
        Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/" + fileName + ".fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle(title);
        stage.setResizable(isResizable);
        stage.setWidth(wight);
        stage.setHeight(height);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
    private void showErrorMessage(String title, String headerText, String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    
    private Optional<ButtonType> showAlertMessage(String title, String headerText, String contentText) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		Optional<ButtonType> option = alert.showAndWait();	 
		return option;
    }
    
    private Color getColorDayStatus(DayStatus dayStatus) {    	
    	switch (dayStatus) {
			case WORKING_DAY:
				return workingDayColor;
			case FRIDAY_WORKING_DAY:
				return workingDayColor;
			case DAY_OFF:
				return dayOffColor;
			case SICK_LEAVE:
				return sickLeaveColor;
			case VACATION:
				return vacationColor;
			case ABSENTEEISM:
				return absenteismColor;
			case OVER_TIME:
				return overTimeColor;
			default:
				return notSetColor;
		}
    }
    
    private void writePath(String path) throws FileNotFoundException, IOException {   	
        try (OutputStream output = new FileOutputStream("file.properties")) {
            Properties properties = new Properties();
            properties.setProperty("saveFile", path);
            properties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    
    private String readPath() throws FileNotFoundException, IOException {
        String file = null;
    	try (InputStream input = new FileInputStream("file.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            file = properties.getProperty("saveFile");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return file;
    }
    
    public static String getPathToAplication() { 	
    	File currentClass = null;
		try {
			currentClass = new File(URLDecoder.decode(ScheduleController.class
			         .getProtectionDomain()
			         .getCodeSource()
			         .getLocation()
			         .getPath(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String classDirectory = currentClass.getParent();
    	return classDirectory.substring(0, classDirectory.lastIndexOf(File.separator));
    }
    
    private void loadProperties() {
    	Properties dayProperties = new Properties();
    	try {
			dayProperties.load(new FileInputStream("day.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	workingDayColor = Color.valueOf(dayProperties.getProperty("workingDayColor"));
    	dayOffColor = Color.valueOf(dayProperties.getProperty("dayOffColor"));
    	sickLeaveColor = Color.valueOf(dayProperties.getProperty("sickLeaveColor"));
    	vacationColor = Color.valueOf(dayProperties.getProperty("vacationColor"));
    	absenteismColor = Color.valueOf(dayProperties.getProperty("absenteismColor"));
    	overTimeColor = Color.valueOf(dayProperties.getProperty("overTimeColor"));
 		notSetColor = Color.valueOf(dayProperties.getProperty("notSetColor"));
 		
    	isFridayShortDay = Boolean.getBoolean(dayProperties.getProperty("isFridayShortDay"));
    	notWorkingHoursWorkingDay = Integer.valueOf(dayProperties.getProperty("notWorkingHoursWorkingDay"));
    	notWorkingHoursShift = Integer.valueOf(dayProperties.getProperty("notWorkingHoursShift"));
    }
}
































