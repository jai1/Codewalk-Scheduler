package com.neu.edu;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Student {

	final static public Integer DEFAULT_AVAILABILITY = 50;
	final static public Integer AVAILABILITY_THRESHOLD = 25;
	
	private String ccsID;
	// HashMap<TimeSlot, Integer>
	private HashMap<TimeSlot, Integer> timeSlots;
	public Student(String ccsID, TimeSlot t, Integer favorability) {
		super();
		this.ccsID = ccsID;
		this.timeSlots = new HashMap<TimeSlot, Integer>();
		if(t != null)
			this.timeSlots.put(t, favorability);
	}
	
	
	
	public String getCCSID() {
		return ccsID;
	}
	public void setCcsID(String ccsID) {
		this.ccsID = ccsID;
	}
	public HashMap<TimeSlot, Integer> getTimeSlots() {
		return timeSlots;
	}
	public void setTimeSlots(HashMap<TimeSlot, Integer> timeSlots) {
		this.timeSlots = timeSlots;
	}

	@Override
	public String toString() {
		return "Student [ccsID=" + ccsID + ", timeSlots=" + timeSlots
				+ "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ccsID == null) ? 0 : ccsID.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (ccsID == null) {
			if (other.ccsID != null)
				return false;
		} else if (!ccsID.equals(other.ccsID))
			return false;
		return true;
	}



	public void addTimeSlot(TimeSlot t, Integer favorability) {
		this.timeSlots.put(t, favorability);
		
	}



	public void sortTimeSlots()
	{
	    List<Map.Entry<TimeSlot, Integer>> list =
	        new LinkedList<Map.Entry<TimeSlot, Integer>>( timeSlots.entrySet() );
	    Collections.sort( list, new Comparator<Map.Entry<TimeSlot, Integer>>()
	    {
	        public int compare( Map.Entry<TimeSlot, Integer> o1, Map.Entry<TimeSlot, Integer> o2 )
	        {
	            return (o2.getValue()).compareTo( o1.getValue() );
	        }
	    } );
	
	    timeSlots = new LinkedHashMap<TimeSlot, Integer>();
	    for (Map.Entry<TimeSlot, Integer> entry : list)
	    {
	        timeSlots.put( entry.getKey(), entry.getValue() );
	    }
	}

}
