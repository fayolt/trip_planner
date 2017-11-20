package com.sm.tripplanner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.sm.tripplanner.helper.Graph;
import com.sm.tripplanner.helper.NeighborHelper;
import com.sm.tripplanner.helper.StopHelper;

public class Controller {

	public static void main(String[] args) {
		Graph network = null;
		/*
		 * try { DBUtil.getInstance().setupDatabase(); } catch (SQLException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */
		try {
			network = buildNetwork();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TripPlanner planner = new TripPlanner(network);
		planner.execute(new Stop(24874));
		LinkedList<Stop> path = planner.getPath(new Stop(26852));
		
		System.out.println("Paths" + path.size());
		int i = 0;
		long time=0l;
		for (Stop vertex : path) {
			if(i>0) {
			time = planner.getDistance(path.get(i), path.get(i-1));
			System.out.println(path.get(i).getStop_id() + " " + path.get(i-1).getStop_id());
			}
			System.out.println(time);
			i++;
		}
	}

	public static Graph buildNetwork() throws ClassNotFoundException, SQLException {
		List<Stop> edges = StopHelper.getStops();

		List<Neighbor> vertices = new ArrayList<>();

		for (Stop stop : edges) {
			vertices.addAll(NeighborHelper.getNeighbors(stop.getStop_id()));
		}

		return new Graph(vertices, edges);
	}

}