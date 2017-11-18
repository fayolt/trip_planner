package com.sm.tripplanner;

import java.time.LocalTime;

public class Schedule {
	private int stop_sequence;
	private int trip_id;
	private int stop_id;
	private DayType day;
	private LocalTime departure_time;

	public LocalTime getDeparture_time() {
		return departure_time;
	}

	public void setDeparture_time(LocalTime departure_time) {
		this.departure_time = departure_time;
	}

	public DayType getDay() {
		return day;
	}

	public void setDay(DayType day) {
		this.day = day;
	}

	public int getStop_sequence() {
		return stop_sequence;
	}

	public void setStop_sequence(int stop_sequence) {
		this.stop_sequence = stop_sequence;
	}

	public int getTrip_id() {
		return trip_id;
	}

	public void setTrip_id(int trip_id) {
		this.trip_id = trip_id;
	}

	public int getStop_id() {
		return stop_id;
	}

	public void setStop_id(int stop_id) {
		this.stop_id = stop_id;
	}
	
	

}
