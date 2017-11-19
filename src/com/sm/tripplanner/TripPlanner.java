package com.sm.tripplanner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sm.tripplanner.helper.DBUtil;

public class TripPlanner {
	List<Plan> plans = new ArrayList<>();
	
	public static Set<Integer> getTrips(int stopId) throws ClassNotFoundException, SQLException {
		
		Connection conn = DBUtil.getInstance().getConnection();
		
		String query = "SELECT trip_id, stop_sequence, departure_time FROM stop_times where  stop_id = '" + stopId + "'";
		Set<Integer> result = new HashSet();
		ResultSet r  = DBUtil.queryDB(conn, query, Integer.MAX_VALUE);
		while(r.next()) {
			result.add(r.getInt("trip_id"));
		}
		
		conn.close();
		
		return result;
		
	}
}
