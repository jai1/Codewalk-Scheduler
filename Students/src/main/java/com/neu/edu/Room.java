package com.neu.edu;

import java.util.LinkedList;
import java.util.List;

public class Room {
	private String location;
	private Integer capacity;
	private TimeSlot timeSlot;
	private List<Student> students;
	
	public Room(String location, Integer capacity, TimeSlot timeSlot) {
		super();
		this.location = location;
		this.capacity = capacity;
		this.timeSlot = timeSlot;
		this.students = new LinkedList<Student>();
	}
	
	public void addStudent(Student s) {
		this.students.add(s);
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	public TimeSlot getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	@Override
	public String toString() {
		return "Room [location=" + location + ", capacity=" + capacity + ", timeSlot=" + timeSlot + ", students="
				+ students + "]";
	}

	public String printHeader() {
		return "Day: " + this.timeSlot.getDay() +
		", Time: " + this.timeSlot.getStartTime() +
		", Room: " + this.location +
		", Occupied/Max: " + this.students.size() + "/" + this.capacity;
	}

	public String printData() {
		String str="";
		for(Student s: this.students) 
			str += "CCS ID: " + s.getCCSID() + "\n";
		return str;
	}
}