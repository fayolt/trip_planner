package com.sm.tripplanner;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
			for (Map.Entry<Integer, List<Neighbor>> entry : network.neighbors.entrySet())
			{
				System.out.println(entry.getKey());
			    for (Neighbor n: entry.getValue())
			    	System.out.println(n.getStop_id() + " " + n.getTrip_id() + " " + n.getName() + " " + n.getIs_next());
			}
			/*
			System.out.println("path from " + testStops.get(0).getName() + "to " + testStops.get(2).getName());
			List<List<Neighbor>> paths = network.getAllPaths(testStops.get(0), testStops.get(2));
			System.out.println(paths.size());
			for(List<Neighbor> s: paths) {
				for(Stop s1: s) {
					System.out.print(s1.getName() + "-");
				}
			}
			Set<Integer> trips_1 = TripPlanner.getTrips(testStops.get(0).getStop_id());
			Set<Integer> trips_2 = TripPlanner.getTrips(testStops.get(2).getStop_id());
			
			Set<Integer> intersection = new HashSet<Integer>(trips_1); // use the copy constructor
			intersection.retainAll(trips_2);
			
			for (Integer i : intersection)
				System.out.println(i);*/
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
