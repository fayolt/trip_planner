package com.sm.tripplanner;

public class TravelLeg {
	private Stop start;
	private	Stop end;
	private double duration;
	private TransportMode leg_type;
	public Stop getStart() {
		return start;
	}
	public void setStart(Stop start) {
		this.start = start;
	}
	public Stop getEnd() {
		return end;
	}
	public void setEnd(Stop end) {
		this.end = end;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public TransportMode getLeg_type() {
		return leg_type;
	}
	public void setLeg_type(TransportMode leg_type) {
		this.leg_type = leg_type;
	}
	
	
}
