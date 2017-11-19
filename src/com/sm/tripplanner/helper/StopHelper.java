package com.sm.tripplanner.helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sm.tripplanner.Stop;

public class StopHelper {
	static Connection conn = DBUtil.getInstance().getConnection();
	
	public static List<Stop> getStops() throws ClassNotFoundException, SQLException{
		String query = "SELECT DISTINCT stop_id, stop_name, stop_lat, stop_lon FROM stops";
		List<Stop> result = new ArrayList<>();
		ResultSet r  = DBUtil.queryDB(conn, query, Integer.MAX_VALUE);

		
		while(r.next()) {
			Stop stop = new Stop();
			stop.setStop_id(r.getInt("stop_id"));
			stop.setLatitude(r.getDouble("stop_lat"));
			stop.setLongitude(r.getDouble("stop_lon"));
			stop.setName(r.getString("stop_name"));
			result.add(stop);
		}
		return result;
	}
	
	public static Stop get(int stopId) throws ClassNotFoundException, SQLException{
		String query = "SELECT * FROM stops WHERE stop_id = '"+stopId+ "'";
		Stop result = null;
		ResultSet r  = DBUtil.queryDB(conn, query, 1);
		if(r.next()) {
			result = new Stop();
			result.setStop_id(r.getInt("stop_id"));
			result.setLatitude(r.getDouble("stop_lat"));
			result.setLongitude(r.getDouble("stop_lon"));
			result.setName(r.getString("stop_name"));
		}
		return result;
	}
	
	
	
}
