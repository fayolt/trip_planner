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
		String query = "SELECT * FROM stop_times WHERE stop_id = '" + stopId + "'";
		List<Schedule> result = new ArrayList<>();
		ResultSet r = DBUtil.queryDB(conn, query, Integer.MAX_VALUE);

		while (r.next()) {
			if (LocalTime.parse(r.getString("DEPARTURE_TIME")).compareTo(time) >= 0) {
				if (checkDay(r.getInt("trip_id"), DayType.valueOf(Utils.getDay(date)))) {
					Schedule schedule = new Schedule();
					schedule.setDay(DayType.valueOf(Utils.getDay(date)));
					schedule.setDeparture_time(LocalTime.parse(r.getString("DEPARTURE_TIME")));
					schedule.setStop_id(stopId);
					schedule.setTrip_id(r.getInt("trip_id"));
					schedule.setStop_sequence(r.getInt("stop_sequence"));
					result.add(schedule);
				}
			}
		}
		conn.close();
		return result;
	}

	public static List<Schedule> getSchedules(int stop_id, DayType day) throws ClassNotFoundException, SQLException {
		List<Schedule> result = new ArrayList<>();
		String query = "SELECT * FROM stop_times where stop_id = '" + stop_id + "' ";

		ResultSet r = DBUtil.queryDB(conn, query, Integer.MAX_VALUE);
		while (r.next()) {
			if (checkDay(r.getInt("trip_id"), day)) {
				Schedule schedule = new Schedule();
				schedule.setDay(day);
				schedule.setDeparture_time(LocalTime.parse(r.getString("DEPARTURE_TIME")));
				schedule.setStop_id(stop_id);
				schedule.setTrip_id(r.getInt("trip_id"));
				schedule.setStop_sequence(r.getInt("stop_sequence"));
				result.add(schedule);
			}
		}
		return result;
	}

	public static boolean checkDay(int trip_id, DayType day) throws ClassNotFoundException, SQLException {
		boolean result = false;
		String serviceQuery = "SELECT * FROM trips WHERE trip_id = '" + trip_id + "'";
		ResultSet service = DBUtil.queryDB(conn, serviceQuery, 1);
		service.next();
		int serviceId = service.getInt("SERVICE_ID");
		String q = "";
		if (day.equals(DayType.WEEKDAY)) {
			q = "SELECT * FROM calendar where SERVICE_ID = '" + serviceId + "' AND MONDAY = '1'";
		} else if (day.equals(DayType.SATURDAY)) {
			q = "SELECT * FROM calendar where SERVICE_ID = '" + serviceId + "' AND SATURDAY = '1'";
		} else {
			q = "SELECT * FROM calendar where SERVICE_ID = '" + serviceId + "' AND SUNDAY = '1'";
		}
		ResultSet resultSet = DBUtil.queryDB(conn, q, Integer.MAX_VALUE);
		if (resultSet.next()) {
			result = true;
		}
		return result;
	}

	public static Schedule getScheduleByTripAndStop(int trip_id, int stop_id)
			throws ClassNotFoundException, SQLException {
		Schedule result = null;
		String query = "SELECT * FROM stop_times where trip_id = '" + trip_id + "' AND stop_id = '" + stop_id + "'";

		ResultSet r = DBUtil.queryDB(conn, query, Integer.MAX_VALUE);
		if (r.next()) {
			result = new Schedule();
			result.setDeparture_time(LocalTime.parse(r.getString("DEPARTURE_TIME")));
			result.setStop_id(stop_id);
			result.setTrip_id(r.getInt("trip_id"));
			result.setStop_sequence(r.getInt("stop_sequence"));
		}
		return result;
	}

}
