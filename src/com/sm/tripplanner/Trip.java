package com.sm.tripplanner;

import java.util.TreeMap;

public class Trip {
	private int trip_id;
	private Route route;
	private String direction_code;
	private TreeMap<Integer, Stop> stops;
	
	public int getTrip_id() {
		return trip_id;
	}
	public void setTrip_id(int trip_id) {
		this.trip_id = trip_id;
	}
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}

	public String getDirection_code() {
		return direction_code;
	}
	public void setDirection_code(String direction_code) {
		this.direction_code = direction_code;
	}
	public TreeMap<Integer, Stop> getStops() {
		return stops;
	}
	public void setStops(TreeMap<Integer, Stop> stops) {
		this.stops = stops;
	}
	
	
}
