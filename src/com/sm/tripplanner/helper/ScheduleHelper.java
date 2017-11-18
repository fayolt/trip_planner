package com.sm.tripplanner.helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.sm.tripplanner.DayType;
import com.sm.tripplanner.Schedule;

public class ScheduleHelper {
	static Connection conn = DBUtil.getInstance().getConnection();
	
	public static List<Schedule> getSchedules(int stopId, LocalDate date, LocalTime time) throws ClassNotFoundException, SQLException{
		String query = "SELECT trip_id, departure_time FROM stop_times WHERE stop_id = '"+stopId+"'";
		List<Schedule> result = new ArrayList<>();
		ResultSet r  = DBUtil.queryDB(conn, query, Integer.MAX_VALUE);

		
		while(r.next()) {
			if (LocalTime.parse(r.getString("DEPARTURE_TIME")).compareTo(time)>=0) {
				String serviceQuery = "SELECT * FROM trips WHERE trip_id = '"+r.getInt("trip_id")+"'";
				ResultSet service  = DBUtil.queryDB(conn,serviceQuery,1);
				service.next();
				int serviceId = service.getInt("SERVICE_ID");
				String availabilityQuery = "";
				if(Utils.getDay(date).equals(DayType.WEEKDAY.toString())) {
					availabilityQuery = "SELECT * FROM calendar where SERVICE_ID = '"+serviceId+"' AND MONDAY = '1'";
				}else if (Utils.getDay(date).equals(DayType.SATURDAY.toString())) {
					availabilityQuery = "SELECT * FROM calendar where SERVICE_ID = '"+serviceId+"' AND SATURDAY = '1'";
				}else {
					availabilityQuery = "SELECT * FROM calendar where SERVICE_ID = '"+serviceId+"' AND SUNDAY = '1'";
				}
				
				ResultSet resultSet  = DBUtil.queryDB(conn, availabilityQuery, Integer.MAX_VALUE);
				if(resultSet.next()) {
					Schedule schedule = new Schedule();
					schedule.setDay(DayType.valueOf(Utils.getDay(date)));
					schedule.setDeparture_time(LocalTime.parse(r.getString("DEPARTURE_TIME")));
					result.add(schedule);
				}
			}
		}
		conn.close();
		return result;
	}
	
	
	
}
