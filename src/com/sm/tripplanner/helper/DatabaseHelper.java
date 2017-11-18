package com.sm.tripplanner.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.sm.tripplanner.DayType;
import com.sm.tripplanner.Schedule;

public class DatabaseHelper {
	static Connection conn = DBUtil.getInstance().getConnection();
	public static ResultSet queryDB(String query, int rows) throws SQLException, ClassNotFoundException {
	    // Create a Statement object to execute the query with.
	    // A Statement is not thread-safe.
	    Statement stmt = conn.createStatement();
	    if(rows==1) {
	    	stmt.setMaxRows(1);
	    }
	    System.out.println(query);
	    // Select the ID and NAME columns from sample.csv
	    ResultSet results = stmt.executeQuery(query);
	    // Dump out the results to a CSV file with the same format
	    // using CsvJdbc helper function
	    //boolean append = true;
	    //CsvDriver.writeToCsv(results, System.out, append);

	    // Clean up
	    
	    return results;
	}
	
	public static List<Schedule> getSchedules(int stopId, LocalDate date, LocalTime time) throws ClassNotFoundException, SQLException{
		String query = "SELECT trip_id, departure_time FROM stop_times WHERE stop_id = '"+stopId+"'";
		List<Schedule> result = new ArrayList<>();
		ResultSet r  = queryDB(query, Integer.MAX_VALUE);

		
		while(r.next()) {
			if (LocalTime.parse(r.getString("DEPARTURE_TIME")).compareTo(time)>=0) {
				String serviceQuery = "SELECT * FROM trips WHERE trip_id = '"+r.getInt("trip_id")+"'";
				ResultSet service  = queryDB(serviceQuery,1);
				service.next();
				int serviceId = service.getInt("SERVICE_ID");
				String availabilityQuery = "";
				if(getDay(date).equals(DayType.WEEKDAY.toString())) {
					availabilityQuery = "SELECT * FROM calendar where SERVICE_ID = '"+serviceId+"' AND MONDAY = '1'";
				}else if (getDay(date).equals(DayType.SATURDAY.toString())) {
					availabilityQuery = "SELECT * FROM calendar where SERVICE_ID = '"+serviceId+"' AND SATURDAY = '1'";
				}else {
					availabilityQuery = "SELECT * FROM calendar where SERVICE_ID = '"+serviceId+"' AND SUNDAY = '1'";
				}
				
				ResultSet resultSet  = queryDB(availabilityQuery, Integer.MAX_VALUE);
				if(resultSet.next()) {
					Schedule schedule = new Schedule();
					schedule.setDay(DayType.valueOf(getDay(date)));
					schedule.setDeparture_time(LocalTime.parse(r.getString("DEPARTURE_TIME")));
					result.add(schedule);
				}
			}
		}
		conn.close();
		return result;
	}
	
	public static String getDay(LocalDate date) {
		String result = "";
		String day = date.getDayOfWeek().name();
		if(day.equals("MONDAY")||day.equals("TUESDAY")||day.equals("WEDNESDAY")||day.equals("THURSDAY")||day.equals("FRIDAY")) {
			result = DayType.WEEKDAY.toString();
		}else if(day.equals("SATURDAY")) {
			result = DayType.SATURDAY.toString();
		}else {
			result = DayType.SUNDAY_HOLIDAY.toString();
		}
	
		return result;
				
	}
}
