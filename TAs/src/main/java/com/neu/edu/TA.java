package com.neu.edu;

import static java.util.Map.Entry.comparingByValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TA {

	private String ccsID;
	private HashMap<TimeSlot, Integer> timeSlots;
	private Integer maxCodeWalksToBeScheduled = 4;
	private Integer numberOfCodeWalksScheduled = 0;

	public TA(String ccsID, TimeSlot t, Integer favorability, int maxCodeWalksToBeScheduled) {
		super();
		this.ccsID = ccsID;
		this.timeSlots = new HashMap<TimeSlot, Integer>();
		if(t != null) {
			this.timeSlots.put(t, favorability);
		}
		this.maxCodeWalksToBeScheduled = maxCodeWalksToBeScheduled;
	}
	
	public Integer getMaxCodeWalksToBeScheduled() {
		return maxCodeWalksToBeScheduled;
	}

	public void setMaxCodeWalksToBeScheduled(Integer maxCodeWalksToBeScheduled) {
		this.maxCodeWalksToBeScheduled = maxCodeWalksToBeScheduled;
	}
	
	public boolean canBeScheduled(int requiredNumberOfCodeWalkPerTA) {
		// System.out.println("ccsID" + ccsID + " numberOfCodeWalksScheduled: " + numberOfCodeWalksScheduled);
		return (numberOfCodeWalksScheduled < requiredNumberOfCodeWalkPerTA
				&& numberOfCodeWalksScheduled < maxCodeWalksToBeScheduled);
	}
	
	public void incrementCodeWalksScheduled() {
		numberOfCodeWalksScheduled++;
	}
	
	
	public Integer getNumberOfCodeWalksScheduled() {
		return numberOfCodeWalksScheduled;
	}

	public void setNumberOfCodeWalksScheduled(Integer numberOfCodeWalksScheduled) {
		this.numberOfCodeWalksScheduled = numberOfCodeWalksScheduled;
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
		return "TA [ccsID=" + ccsID + ", timeSlots=" + timeSlots + ", maxCodeWalksToBeScheduled="
				+ maxCodeWalksToBeScheduled + ", numberOfCodeWalksScheduled=" + numberOfCodeWalksScheduled + "]";
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
		TA other = (TA) obj;
		if (ccsID == null) {
			if (other.ccsID != null)
				return false;
		} else if (!ccsID.equals(other.ccsID))
			return false;
		return true;
	}



	public void addTimeSlot(TimeSlot t, int favorability) {
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
	    for (Map.Entry<TimeSlot, Integer> entry : list) {
	        timeSlots.put( entry.getKey(), entry.getValue() );
	    }
	}

	public void removeTimeSlot(TimeSlot ts) {
		timeSlots.remove(ts);
		
	}

}
