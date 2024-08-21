package com.oriseus.schedule.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkingPlace implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String workingPlaceName;
	private List<Person> workers;
	
	public WorkingPlace(String workingPlaceName) {
		this.workingPlaceName = workingPlaceName;
	}

	public void addWorker(Person person) {
		if (workers == null) {
			workers = new ArrayList<Person>();
		}
		workers.add(person);
	}
	public void addWorkers(Person[] persons) {
		if (workers == null) {
			workers = new ArrayList<Person>();
		}
		workers.addAll(workers);
	}
	
	public String getWorkingPlaceName() {
		return workingPlaceName;
	}

	public void setWorkingPlaceName(String workingPlaceName) {
		this.workingPlaceName = workingPlaceName;
	}

	public List<Person> getWorkers() {
		return workers;
	}

	public void setWorkers(List<Person> workers) {
		this.workers = workers;
	}
	
	
}
