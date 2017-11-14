package com.sm.tripplanner;

import java.time.LocalTime;
import java.util.List;

public class Schedule {
	private DayType day;
	private List<LocalTime> times;
	
	public DayType getDay() {
		return day;
	}
	public void setDay(DayType day) {
		this.day = day;
	}
	public List<LocalTime> getTimes() {
		return times;
	}
	public void setTimes(List<LocalTime> times) {
		this.times = times;
	}
	
	
}
