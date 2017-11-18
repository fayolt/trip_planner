package com.sm.tripplanner;

import java.time.LocalTime;

public class Schedule {
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

}
