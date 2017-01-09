package com.neu.edu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Main {
	final static private Integer DEFAULT_AVAILABILITY = 50;
	final static private Integer MIN_THRESHOLD = 40;

	public static void main(String[] args) {
		try {
			LinkedHashMap<Room, Boolean> rooms = getRoomAvailability(
					"./src/main/resources/com/neu/edu/RoomAvailability.txt");
			LinkedHashMap<TA, Boolean> tas = getTADetails("./src/main/resources/com/neu/edu/StudentAvailability.txt",
					rooms);
			createSchedule(tas, rooms);
			printToFile("./src/main/resources/com/neu/edu/output.txt", rooms, tas);
		} catch (Exception ex) {
			System.out.println("\nFATAL ERROR OCCURED: \n");
			System.out.println(ex.getMessage());
		}
	}

	private static void printLinkedHashMap(LinkedHashMap<Room, Boolean> rooms) {
		for (Room room : rooms.keySet()) {
			System.out.println(room.toString());
		}
	}

	private static void printToFile(String file, LinkedHashMap<Room, Boolean> rooms,
			LinkedHashMap<TA, Boolean> students) throws Exception {
		Writer writer = new FileWriter(file);
		for (Room r : rooms.keySet()) {
			writer.write("\n******************************************************************\n");
			writer.write(r.printHeader() + "\n");
			writer.write(r.printData() + "\n");
		}
		writer.write("Details: \n");
		for (Room r : rooms.keySet()) {
			if (!r.filled()) {
				writer.write(r.printHeader() + "\n");
				writer.write("ROOM NOT FILLED" + "\n");
			}
		}
		
		for (TA s : students.keySet()) {
			writer.write("CCS ID: " + s.getCCSID() + " Scheduled/Max: " + s.getNumberOfCodeWalksScheduled() + "/" + s.getMaxCodeWalksToBeScheduled() + "\n");
		}
		writer.close();
	}

	private static void createSchedule(LinkedHashMap<TA, Boolean> tas, LinkedHashMap<Room, Boolean> rooms) {
		int totalTARequired = 0;
		for (Room r : rooms.keySet()) {
			totalTARequired += r.getNumberOfTAs();
		}
		int averageTARequired = totalTARequired / tas.size();

		for (Room r : rooms.keySet()) {
				for (TA ta : tas.keySet()) {
					if (r.filled()) {
						break;
					}
					TimeSlot ts = r.getTimeSlot();
					Integer preference = ta.getTimeSlots().get(ts);
					if (ta.canBeScheduled(averageTARequired)) {
						if (preference != null && preference.compareTo(MIN_THRESHOLD) >= 0) {
							ta.incrementCodeWalksScheduled();
							ta.removeTimeSlot(ts);
							r.addTA(ta);
						}
					}
				}
		}
		
		// To avoid more TAs getting average number of slots and few TAs getting (average - 2) slots
		averageTARequired ++;
		for (Room r : rooms.keySet()) {
			for (TA ta : tas.keySet()) {
				if (r.filled()) {
					break;
				}
				TimeSlot ts = r.getTimeSlot();
				Integer preference = ta.getTimeSlots().get(ts);
				if (ta.canBeScheduled(averageTARequired)) {
					if (preference != null && preference.compareTo(MIN_THRESHOLD) >= 0) {
						ta.incrementCodeWalksScheduled();
						ta.removeTimeSlot(ts);
						r.addTA(ta);
					}
				}
			}
		}
		
	}

	private static LinkedHashMap<Room, Boolean> getRoomAvailability(String file) throws Exception {
		LinkedHashMap<Room, Boolean> rooms = new LinkedHashMap<Room, Boolean>();
		// File structure is "Day|Start Time|End Time|Location|Number of TAS"

		FileInputStream fstream = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		// Read File Line By Line
		while ((strLine = br.readLine()) != null) {
			// System.out.println(strLine);
			if (strLine.startsWith("//"))
				continue;
			String[] parts = strLine.split("\\|", -1);
			// System.out.println(parts[0] + "," + parts[1]);
			TimeSlot t = new TimeSlot(parts[0], parts[1], parts[2]);
			Room r = new Room(parts[3], Integer.parseInt(parts[4]), t);
			rooms.put(r, false);
		}

		// Close the input stream
		br.close();
		fstream.close();
		return rooms;
	}

	private static LinkedHashMap<TA, Boolean> getTADetails(String file, LinkedHashMap<Room, Boolean> rooms)
			throws Exception {
		// File structure is "Max Number Of CW|CCS ID|Day|Start Time|Preference"
		LinkedHashMap<String, TA> tas = new LinkedHashMap<String, TA>();

		FileInputStream fstream = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		// Read File Line By Line
		while ((strLine = br.readLine()) != null) {
			// System.out.println(strLine);
			if (strLine.startsWith("//"))
				continue;
			String[] parts = strLine.split("\\|", -1);
			int maxCodeWalksToBeScheduled = Integer.parseInt(parts[0]);
			String CCSID = parts[1];
			String day = parts[2];
			String startTime = parts[3];
			int preference = Integer.parseInt(parts[4]);
			TimeSlot t = new TimeSlot(day, startTime, "");
			TA s = tas.get(CCSID);
			if (s != null)
				s.addTimeSlot(t, preference);
			else
				tas.put(CCSID, new TA(CCSID, t, preference, maxCodeWalksToBeScheduled));
		}

		// Close the input stream
		br.close();
		fstream.close();

		// Map <TA, isScheduled?>
		LinkedHashMap<TA, Boolean> taMap = new LinkedHashMap<TA, Boolean>();
		for (String CCSID : tas.keySet()) {
			TA s = tas.get(CCSID);
			for (Room r : rooms.keySet()) {
				if (s.getTimeSlots().get(r.getTimeSlot()) == null)
					s.getTimeSlots().put(r.getTimeSlot(), DEFAULT_AVAILABILITY);
			}
			s.sortTimeSlots();
			taMap.put(s, false);
			System.out.println(s);
		}
		return taMap;
	}

}
