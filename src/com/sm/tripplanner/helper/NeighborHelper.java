package com.sm.tripplanner.helper;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sm.tripplanner.Neighbor;
import com.sm.tripplanner.Route;
import com.sm.tripplanner.Stop;
import com.sm.tripplanner.TransportMode;

public class NeighborHelper {
	static Connection conn = DBUtil.getInstance().getConnection();

	public static List<Neighbor> getNeighbors(Stop stop, LocalTime time) throws ClassNotFoundException, SQLException {
		String query = "SELECT * FROM STOP_TIME where  stop_id = '" + stop.getStop_id() + "' and departure_time >= '"+time+":00' and departure_time <= '"+time.plusHours(1)+":00'";

		List<Neighbor> result = new ArrayList<>();
		ResultSet r = DBUtil.queryDB(conn, query, Integer.MAX_VALUE);
		int id = 0;
		while (r.next()) {
			int sequenceId = r.getInt("stop_sequence");
			LocalTime stoptime = r.getTime("DEPARTURE_TIME").toLocalTime();
			String neighborsQuery = "SELECT * FROM STOP_TIME s join TRIP t on t.TRIP_ID = s.TRIP_ID  join stop st on st.stop_id=s.stop_id where  s.TRIP_ID= '"
					+ r.getInt("trip_id") + "'" + " AND (stop_sequence = '" + (sequenceId + 1)
					+ "' OR stop_sequence = '" + (sequenceId - 1) + "')";
			ResultSet resultSet = DBUtil.queryDB(conn, neighborsQuery, Integer.MAX_VALUE);
			while (resultSet.next()) {
				Neighbor neighbor = new Neighbor();
				Stop source = new Stop();
				Stop destination = new Stop();
				if (resultSet.getInt("stop_sequence") > sequenceId) {
					destination.setStop_id(resultSet.getInt("STOP_ID"));
					destination.setName(resultSet.getString("STOP_NAME"));
					source.setStop_id(stop.getStop_id());
					source.setName(stop.getName());
				} else {
					destination.setStop_id(stop.getStop_id());
					destination.setName(stop.getName());
					source.setStop_id(resultSet.getInt("STOP_ID"));
					source.setName(resultSet.getString("STOP_NAME"));
				}

				LocalTime neighbortime = resultSet.getTime("DEPARTURE_TIME").toLocalTime();
				neighbor.setDestination(destination);
				neighbor.setSource(source);
				neighbor.setId("" + id + 1);
				neighbor.setTravel_time(Math.abs(MINUTES.between(stoptime, neighbortime)));
				neighbor.setRoute_id(resultSet.getString("ROUTE_ID"));
				result.add(neighbor);
			}
		}
		return result;
	}
	
	public static Map<String, String> getNeighborInfo(Stop source, Stop destination, LocalTime time) throws ClassNotFoundException, SQLException {
		Map<String, String> result = new HashMap<>();
		String query = "select * from stop_time s join trip t on t.trip_id=s.trip_id where (stop_id='"+source.getStop_id()+"' or stop_id='"+destination.getStop_id()+"') and departure_time>='"+time+":00' and departure_time<='"+time.plusHours(1)+":00' limit 2";
		List<LocalTime> times = new ArrayList<>();
		ResultSet resultSet = DBUtil.queryDB(conn, query, Integer.MAX_VALUE);
		String routeId = "";
		String lasttime = "";
		while(resultSet.next()) {
			times.add(resultSet.getTime("departure_time").toLocalTime());
			routeId = resultSet.getString("ROUTE_ID");
			lasttime = resultSet.getString("DEPARTURE_TIME");
		}
		result.put("routeId", routeId);
		result.put("duration", ""+Math.abs(MINUTES.between(times.get(0), times.get(1))));
		result.put("time", lasttime);
		return result;
	}
	
	public static Route getRoute(String routeId) throws SQLException, ClassNotFoundException {
		String query = "SELECT * FROM route WHERE route_id = '"+routeId+ "'";
		Route result = null;
		ResultSet r  = DBUtil.queryDB(conn, query, 1);
		if(r.next()) {
			result = new Route();
			result.setRoute_id(r.getString("route_id"));
			result.setTransport_mode(TransportMode.values()[r.getInt("route_type")]);
			result.setRoute_number(r.getInt("route_short_name"));
		}
		return result;
	}

}
