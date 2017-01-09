package com.neu.edu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.LinkedHashMap;

public class Main {
	final static private String INPUT_ROOM_AVAILABILITY_FILE="./src/main/resources/com/neu/edu/RoomAvailability.txt";
	final static private String INPUT_STUDENT_AVAILABILITY_FILE="./src/main/resources/com/neu/edu/StudentAvailability.txt";
	final static private String OUTPUT_SCHEDULE_FILE="./src/main/resources/com/neu/edu/output.txt";
	
	public static void main(String[] args) {
		try {
			// Hash Map of <Room, Num of students filled>
			LinkedHashMap<Room, Integer> rooms = getRoomAvailability(INPUT_ROOM_AVAILABILITY_FILE);
			// printLinkedHashMap(rooms);
			// Hash Map of <Student, Accomodated?>
			LinkedHashMap<Student, Boolean> students = getStudentDetails(INPUT_STUDENT_AVAILABILITY_FILE, rooms);
			createSchedule(students, rooms);
			printToFile(OUTPUT_SCHEDULE_FILE, rooms, students);
		}
		catch(Exception ex) {
			System.out.println("\nFATAL ERROR OCCURED: \n");
			System.out.println(ex.getMessage());			
		}
	}

	private static void printLinkedHashMap(LinkedHashMap<Room, Integer> rooms) {
		for (Room room: rooms.keySet()){
            System.out.println(room.toString());
		}		
	}

	private static void printToFile(String file, LinkedHashMap<Room, Integer> rooms, LinkedHashMap<Student, Boolean> students) throws Exception{
		Writer writer = new FileWriter(file);
		for(Room r: rooms.keySet()) {
			writer.write("\n******************************************************************\n");
			writer.write(r.printHeader() +"\n");
			writer.write(r.printData() + "\n");
		}
		writer.write("STUDENTS NOT SCHEDULED: \n");
		for(Student s: students.keySet())
			if (!students.get(s))
				writer.write("CCS ID: " + s.getCCSID() +"\n");
		writer.close();
	}

	private static void createSchedule(LinkedHashMap<Student, Boolean> students, LinkedHashMap<Room, Integer> rooms) {
		for(Student s: students.keySet()) {
			boolean accomodated = false;
			for(TimeSlot t:s.getTimeSlots().keySet()) {
				if (accomodated) 
					break;
				for(Room r: rooms.keySet()) {
					if(r.getTimeSlot().startTimeEqual(t) && rooms.get(r) < r.getCapacity()
						&& s.getTimeSlots().get(t) > Student.AVAILABILITY_THRESHOLD
						) {
						//System.out.println("**************************");
						//System.out.println(s.getTimeSlots().get(t));
						//System.out.println(r);
						//System.out.println("**************************");
						r.addStudent(s);
						rooms.put(r, rooms.get(r) + 1);
						accomodated = true;
						break;
					}
				}
			}
			students.put(s, accomodated);
		}
	}

	private static LinkedHashMap<Room, Integer> getRoomAvailability(String file) throws Exception {
		LinkedHashMap<Room, Integer> rooms = new LinkedHashMap<Room, Integer>();
		// File structure is "Day|Start Time|End Time|Location|Capacity"

		FileInputStream fstream = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
			//System.out.println(strLine);
			if (strLine.startsWith("//"))
				continue;
			String[] parts = strLine.split("\\|",-1);
			//System.out.println(parts[0] + "," + parts[1]);
			TimeSlot t = new TimeSlot(parts[0], parts[1], parts[2]);
			Room r = new Room(parts[3], Integer.parseInt(parts[4]), t);
			rooms.put(r, 0);
		}

		//Close the input stream
		br.close();
		fstream.close();
		return rooms;
	}

	private static LinkedHashMap<Student, Boolean> getStudentDetails(String file, LinkedHashMap<Room, Integer> rooms) throws Exception{
		// File structure is "CCS ID|Day|Start Time|Preference"
		LinkedHashMap<String, Student> students = new LinkedHashMap<String, Student>();
		
		FileInputStream fstream = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		//Read File Line By Line
		while ((strLine = br.readLine()) != null) {
			//System.out.println(strLine);
			if (strLine.startsWith("//"))
				continue;
			String[] parts = strLine.split("\\|",-1);
			//System.out.println(parts[0] + "," + parts[3] + Integer.parseInt(parts[3]));ppppppppppppp
			TimeSlot t = new TimeSlot(parts[1], parts[2], "");
			Student s=students.get(parts[0]);
			if ( s != null) 
				s.addTimeSlot(t, Integer.parseInt(parts[3]));
			else 
				students.put(parts[0], new Student(parts[0], t, Integer.parseInt(parts[3])));
		}

		//Close the input stream
		br.close();
		fstream.close();
		
		LinkedHashMap<Student, Boolean> studentMap = new LinkedHashMap<Student, Boolean>();
		for(String CCSID: students.keySet()) {
			Student s = students.get(CCSID);
			for(Room r: rooms.keySet()) {
				if(s.getTimeSlots().get(r.getTimeSlot()) == null)
					s.getTimeSlots().put(r.getTimeSlot(), Student.DEFAULT_AVAILABILITY);
			}
			s.sortTimeSlots();
			studentMap.put(s, false);
			
			System.out.println(s);
		}
		return studentMap;
	}

}


