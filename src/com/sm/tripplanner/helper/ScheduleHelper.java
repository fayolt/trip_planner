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

	public static List<Schedule> getAvailableTrips(int stopId, LocalDate date, LocalTime time)
			throws ClassNotFoundException, SQLException {
		
		String query = "";
		if(DayType.valueOf(Utils.getDay(date)).equals(DayType.WEEKDAY)) {
			query = "select * from stop_time s " + 
					"join trip t on t.trip_id = s.trip_id " + 
					"join calendar c on c.service_id = t.service_id " + 
					"where stop_id = '"+stopId+"' and c.weekday='1' and departure_time >= '"+time+":00'";
		}else if (DayType.valueOf(Utils.getDay(date)).equals(DayType.SATURDAY)) {
			query = "select * from stop_time s " + 
					"join trip t on t.trip_id=s.trip_id" + 
					"join calendar c on c.service_id = t.service_id" + 
					"where stop_id = '"+stopId+"' and c.saturday='1' and depature_time >= '"+time+"'";
		}else {
			query = "select * from stop_time s " + 
					"join trip t on t.trip_id=s.trip_id" + 
					"join calendar c on c.service_id = t.service_id" + 
					"where stop_id = '"+stopId+"' and c.sunday='1' and depature_time >= '"+time+"'";
		}
		List<Schedule> result = new ArrayList<>();
		ResultSet r = DBUtil.queryDB(conn, query, Integer.MAX_VALUE);

		while (r.next()) {

					Schedule schedule = new Schedule();
					schedule.setDay(DayType.valueOf(Utils.getDay(date)));
					schedule.setDeparture_time(LocalTime.parse(r.getString("DEPARTURE_TIME")));
					schedule.setStop_id(stopId);
					schedule.setStop_sequence(r.getInt("stop_sequence"));
					result.add(schedule);
		}
		return result;
	}
}
