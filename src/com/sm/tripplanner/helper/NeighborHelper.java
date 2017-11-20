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

	public static List<Neighbor> getNeighbors(int stop_id) throws ClassNotFoundException, SQLException {
		String query = "SELECT * FROM STOP_TIME where  stop_id = '" + stop_id + "'";

		List<Neighbor> result = new ArrayList<>();
		ResultSet r = DBUtil.queryDB(conn, query, Integer.MAX_VALUE);
		int id = 0;
		while (r.next()) {
			int sequenceId = r.getInt("stop_sequence");
			LocalTime stoptime = r.getTime("DEPARTURE_TIME").toLocalTime();
			String neighborsQuery = "SELECT * FROM STOP_TIME s join TRIP t on t.TRIP_ID = s.TRIP_ID where  s.TRIP_ID= '"
					+ r.getInt("trip_id") + "'" + " AND (stop_sequence = '" + (sequenceId + 1)
					+ "' OR stop_sequence = '" + (sequenceId - 1) + "') AND direction_code like 'A%'";
			ResultSet resultSet = DBUtil.queryDB(conn, neighborsQuery, Integer.MAX_VALUE);
			while (resultSet.next()) {
				Neighbor neighbor = new Neighbor();
				Stop source = new Stop();
				Stop destination = new Stop();

				if (resultSet.getInt("stop_sequence") > sequenceId) {
					destination.setStop_id(resultSet.getInt("STOP_ID"));
					source.setStop_id(stop_id);
				} else {
					destination.setStop_id(stop_id);
					source.setStop_id(resultSet.getInt("STOP_ID"));
				}

				LocalTime neighbortime = resultSet.getTime("DEPARTURE_TIME").toLocalTime();
				neighbor.setDestination(destination);
				neighbor.setSource(source);
				neighbor.setId("" + id + 1);
				neighbor.setTravel_time(Math.abs(MINUTES.between(stoptime, neighbortime)));
				result.add(neighbor);
			}
		}
		return result;
	}

}
