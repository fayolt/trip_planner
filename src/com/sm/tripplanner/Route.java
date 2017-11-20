package com.sm.tripplanner;

public class Route {
	private String route_id;
	private int route_number;
	private TransportMode transport_mode;

	public Route() {

	}

	public Route(String route_id) {

	}

	public String getRoute_id() {
		return route_id;
	}

	public void setRoute_id(String route_id) {
		this.route_id = route_id;
	}

	public TransportMode getTransport_mode() {
		return transport_mode;
	}

	public void setTransport_mode(TransportMode transport_mode) {
		this.transport_mode = transport_mode;
	}

	public int getRoute_number() {
		return route_number;
	}

	public void setRoute_number(int route_number) {
		this.route_number = route_number;
	}

	

}
