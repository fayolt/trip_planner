package com.sm.tripplanner.helper;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.sm.tripplanner.Neighbor;
import com.sm.tripplanner.Stop;

public class NeighborHelper {
	static Connection conn = DBUtil.getInstance().getConnection();
	
	public static List<Neighbor> getNeighbors(int stop_id) throws ClassNotFoundException, SQLException{
		String query = "SELECT DISTINCT trip_id, stop_sequence, departure_time FROM stop_times where  stop_id = '" + stop_id + "'";
		List<Neighbor> result = new ArrayList<>();
		ResultSet r  = DBUtil.queryDB(conn, query, Integer.MAX_VALUE);
		while(r.next()) {
			int sequenceId = r.getInt("stop_sequence");
			LocalTime stoptime = LocalTime.parse(r.getString("departure_time"));
			String tripsQuery = "SELECT * FROM stop_times where  trip_id = '" + r.getInt("trip_id") + "'"
					+ " AND (stop_sequence = '"+(sequenceId+1)+"' OR stop_sequence = '"+(sequenceId-1)+"')" ;
			ResultSet resultSet  = DBUtil.queryDB(conn, tripsQuery, Integer.MAX_VALUE);
			while(resultSet.next()) {
				String trip = "SELECT * FROM trips where  trip_id = '" + r.getInt("trip_id") + "' AND direction_code='A>B'";
				ResultSet tripSet  = DBUtil.queryDB(conn, trip, 1);
				if(tripSet.next()) {
					Neighbor n = new Neighbor();
					Stop s = StopHelper.get(resultSet.getInt("stop_id"));
					if(resultSet.getInt("stop_sequence")>sequenceId)
						n.setIs_next(true);
					LocalTime neighbortime = LocalTime.parse(resultSet.getString("departure_time"));
					n.setTravel_time(MINUTES.between(stoptime, neighbortime));
					n.setTrip_id(r.getInt("trip_id"));
					n.setLatitude(s.getLatitude());
					n.setLongitude(s.getLongitude());
					n.setStop_id(s.getStop_id());
					n.setName(s.getName());
					result.add(n);
				}
				
			}
		}
		conn.close();
		return result;
	}
	
}
