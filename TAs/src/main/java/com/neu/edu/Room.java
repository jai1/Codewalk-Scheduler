package com.neu.edu;

import java.util.LinkedList;
import java.util.List;

public class Room {
	private String location;
	private Integer numberOfTAs;
	private TimeSlot timeSlot;
	private List<TA> tas;
	
	public Room(String location, Integer numberOfTAs, TimeSlot timeSlot) {
		super();
		this.location = location;
		this.numberOfTAs = numberOfTAs;
		this.timeSlot = timeSlot;
		this.tas = new LinkedList<TA>();
	}
	
	public void addTA(TA s) {
		this.tas.add(s);
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getNumberOfTAs() {
		return numberOfTAs;
	}
	public void setNumberOfTAs(Integer numberOfTAs) {
		this.numberOfTAs = numberOfTAs;
	}
	public TimeSlot getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}
	public List<TA> getStudents() {
		return tas;
	}
	public void setStudents(List<TA> students) {
		this.tas = students;
	}
	@Override
	public String toString() {
		return "Room [location=" + location + ", capacity=" + numberOfTAs + ", timeSlot=" + timeSlot + ", students="
				+ tas + "]";
	}

	public String printHeader() {
		return "Day: " + this.timeSlot.getDay() +
		", Time: " + this.timeSlot.getStartTime() +
		", Room: " + this.location +
		", Occupied/Max: " + this.tas.size() + "/" + this.numberOfTAs;
	}

	public String printData() {
		String str="";
		for(TA s: this.tas) 
			str += "CCS ID: " + s.getCCSID() + "\n";
		return str;
	}

	public boolean filled() {
		// TODO Auto-generated method stub
		return tas.size() == numberOfTAs;
	}
}