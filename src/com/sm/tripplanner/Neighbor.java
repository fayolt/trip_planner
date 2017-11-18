package com.sm.tripplanner;

public class Neighbor extends Stop {
	private boolean is_next;
	private int trip_id;
	private double travel_time;

	public boolean isIs_next() {
		return is_next;
	}

	public void setIs_next(boolean is_next) {
		this.is_next = is_next;
	}

	public int getTrip_id() {
		return trip_id;
	}

	public void setTrip_id(int trip_id) {
		this.trip_id = trip_id;
	}

	public double getTravel_time() {
		return travel_time;
	}

	public void setTravel_time(double travel_time) {
		this.travel_time = travel_time;
	}

}
