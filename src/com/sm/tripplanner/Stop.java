package com.sm.tripplanner;

import java.util.List;

public class Stop extends Location {
	private int stop_id;
	private List<Route> routes;
	
	public int getStop_id() {
		return stop_id;
	}
	public void setStop_id(int stop_id) {
		this.stop_id = stop_id;
	}
	public List<Route> getRoutes() {
		return routes;
	}
	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
	
	
}
