package com.sm.tripplanner;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.sm.tripplanner.helper.ScheduleHelper;

public class Stop extends Location {
	private int stop_id;

	public Stop() {

	}

	public Stop(int stop_id) {
		this.stop_id = stop_id;
	}

	public boolean checkTripAvailability(LocalDate date, LocalTime time)
			throws IOException, ClassNotFoundException, SQLException {
		List<Schedule> times = ScheduleHelper.getAvailableTrips(getStop_id(), date, time);
		if (times.size() > 0)
			return true;
		else
			return false;
	}

	public int getStop_id() {
		return stop_id;
	}

	public void setStop_id(int stop_id) {
		this.stop_id = stop_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + stop_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stop other = (Stop) obj;
		if (stop_id != other.stop_id)
			return false;
		return true;
	}

}
