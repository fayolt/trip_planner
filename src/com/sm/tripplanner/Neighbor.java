package com.sm.tripplanner;

public class Neighbor {
	private String id;
	private String route_id;
	private Stop source;
	private Stop destination;
	private long travel_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Stop getSource() {
		return source;
	}

	public void setSource(Stop source) {
		this.source = source;
	}

	public Stop getDestination() {
		return destination;
	}

	public void setDestination(Stop destination) {
		this.destination = destination;
	}

	public long getTravel_time() {
		return travel_time;
	}

	public void setTravel_time(long travel_time) {
		this.travel_time = travel_time;
	}

	public String getRoute_id() {
		return route_id;
	}

	public void setRoute_id(String route_id) {
		this.route_id = route_id;
	}

	

}
