package com.sm.tripplanner;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.sm.tripplanner.helper.Graph;
import com.sm.tripplanner.helper.NeighborHelper;
import com.sm.tripplanner.helper.StopHelper;

public class Controller {

	public static void main(String[] args) {
		try {
			List<Stop> stops = StopHelper.getStops();
			List<Stop> testStops = new ArrayList<>();
			for (int i=0; i<3; i++) {
				testStops.add(stops.get(i));
			}
			Graph network = new Graph(testStops.size());
			for(Stop stop: testStops) {
				List<Neighbor> neighbors = NeighborHelper.getNeighbors(stop.getStop_id());
				for(Neighbor n: neighbors) {
					network.addEdge(stop, n);
				}
			}
			System.out.println("path from " + testStops.get(0).getName() + "to " + testStops.get(1).getName());
			List<List<Stop>> paths = network.getAllPaths(testStops.get(0), testStops.get(1));
			System.out.println(paths.size());
			for(List<Stop> s: paths) {
				for(Stop s1: s) {
					System.out.print(s1.getName() + "-");
				}
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*List<Neighbor> neighbors  = new ArrayList<>();
		Stop destination = new Stop();
		destination.setStop_id(33506);
		boolean available = false;
		try {
			//available = destination.checkTripAvailability(LocalDate.parse("2017-11-17"), LocalTime.parse("23:21:00"));
			neighbors = NeighborHelper.getNeighbors(destination.getStop_id());
		} catch (ClassNotFoundException  | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		System.out.println(neighbors.size());*/

	}

}
