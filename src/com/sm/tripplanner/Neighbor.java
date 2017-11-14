package com.sm.tripplanner;

public class Neighbor {
	private Stop previous;
	private Stop next;
	private double travel_time;
	
	public Stop getPrevious() {
		return previous;
	}
	public void setPrevious(Stop previous) {
		this.previous = previous;
	}
	public Stop getNext() {
		return next;
	}
	public void setNext(Stop next) {
		this.next = next;
	}
	public double getTravel_time() {
		return travel_time;
	}
	public void setTravel_time(double travel_time) {
		this.travel_time = travel_time;
	}
	
	
}
