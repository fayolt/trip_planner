package com.sm.tripplanner;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sm.tripplanner.helper.NeighborHelper;

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

	public void setDuration() {
		for (TravelLeg leg : getTravel_legs()) {
			this.duration += leg.getDuration();
		}
	}

	public List<TravelLeg> getTravel_legs() {
		return travel_legs;
	}

	public void setTravel_legs(LinkedList<Stop> path, List<Neighbor> neigbors, LocalTime time) throws ClassNotFoundException, SQLException {
		int i = 0;
		TravelLeg leg = null;
		String routeId="";
		double duration = 0;
		for (Stop stop : path) {
			if (i > 0) {
				
				Map<String, String> n = NeighborHelper.getNeighborInfo(path.get(i-1), stop, time);
				if(n.get("routeId").toString().equals(routeId)) {
					duration += Double.parseDouble(n.get("duration").toString());
					leg.setDuration(duration);
					leg.setEnd(stop);
				}else {
					duration = Double.parseDouble(n.get("duration").toString());
					routeId = n.get("routeId").toString();
					leg = new TravelLeg();
					leg.setDuration(duration);
					leg.setStart(path.get(i-1));
					leg.setEnd(stop);
					
					Route route = NeighborHelper.getRoute(routeId);
					leg.setLeg_type(route.getTransport_mode());
					leg.setRouteNumber(route.getRoute_number());
					getTravel_legs().add(leg);
				}
				time = LocalTime.parse(n.get("time").toString());
			}
			i++;
		}
	}

}
