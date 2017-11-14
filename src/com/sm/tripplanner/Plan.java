package com.sm.tripplanner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Plan {
	private LocalDate date;
	private LocalTime time;
	private Stop destination_point;
	private Stop departure_point;
	private double duration;
	private List<TravelLeg> travel_legs = new ArrayList<>();
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public Stop getDestination_point() {
		return destination_point;
	}
	public void setDestination_point(Stop destination_point) {
		this.destination_point = destination_point;
	}
	public Stop getDeparture_point() {
		return departure_point;
	}
	public void setDeparture_point(Stop departure_point) {
		this.departure_point = departure_point;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public List<TravelLeg> getTravel_legs() {
		return travel_legs;
	}
	public void setTravel_legs(List<TravelLeg> travel_legs) {
		this.travel_legs = travel_legs;
	}
	
	
	
}
