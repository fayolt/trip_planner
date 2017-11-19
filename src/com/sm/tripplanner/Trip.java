package com.sm.tripplanner;

import java.util.List;

public class Trip {
	private int trip_id;
	private Route route;
	private String direction_code;
	private List<Stop> stops;
	
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
	public List<Stop> getStops() {
		return stops;
	}
	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}
	
	
}
