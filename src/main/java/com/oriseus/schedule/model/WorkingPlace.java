package com.oriseus.schedule.model;

import java.util.ArrayList;
import java.util.List;

public class WorkingPlace {
	private String name;
	private List<Worker> listOfWorkers;
	
	public WorkingPlace(String name) {
		this.name = name;
		listOfWorkers = new ArrayList<Worker>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Worker> getListOfWorkers() {
		return listOfWorkers;
	}

	public void setListOfWorkers(List<Worker> listOfWorkers) {
		this.listOfWorkers = listOfWorkers;
	}
	
	public void addWorker(Worker worker) {
		listOfWorkers.add(worker);
	}
	
	public void removeWorker(Worker worker) {
		listOfWorkers.remove(worker);
	}
}
