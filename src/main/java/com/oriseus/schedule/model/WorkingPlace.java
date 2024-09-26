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

	public void setWorkerSelected(Worker worker) {
		for (Worker tempWorker : listOfWorkers) {
			if (tempWorker.equals(worker)) {
				tempWorker.setSelected(true);
				break;
			}
		}
	}

	public void setWorkerNotSelected() {
		for (Worker tempWorker : listOfWorkers) {
			if (tempWorker.isSelected()) {
				tempWorker.setSelected(false);
				break;
			}
		}
	}
}
