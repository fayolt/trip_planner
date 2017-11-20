package com.sm.tripplanner;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.sm.tripplanner.helper.DBUtil;
import com.sm.tripplanner.helper.Graph;
import com.sm.tripplanner.helper.NeighborHelper;
import com.sm.tripplanner.helper.StopHelper;

public class Controller {
	static Graph network = null;
	
	static LocalDate date = null;
	static LocalTime time = null;
	public static void main(String[] args) {
		Stop departure = null;
		Stop destination = null;
		try {
			System.out.println("Please wait while we setup the system...");
			DBUtil.getInstance().setupDatabase();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("All set!!");
			System.out.println("Welcome to MyTripPlanner");

			while (departure == null) {
				try {
					System.out.println("Please enter the departure stop: ");
					Scanner getDeparture = new Scanner(System.in);
					String depart = getDeparture.nextLine();
					departure = StopHelper.getByName(depart);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (departure == null) {
					System.out.println("Unknown Stop, please try again.");
				}
			}

			while (destination == null) {

				try {
					System.out.println("Please enter the destination stop: ");
					Scanner getDestination = new Scanner(System.in);
					String dest = getDestination.nextLine();
					destination = StopHelper.getByName(dest);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}

				if (destination == null) {
					System.out.println("Unknown Stop, please try again.");
				}
			}
			while (date == null) {
				try {
					System.out.println("Please enter the departure date (yyyy-mm-dd): ");
					Scanner getDate = new Scanner(System.in);
					String d = getDate.nextLine();
					date = LocalDate.parse(d);
				} catch (NoSuchElementException e2) {
					System.out.println("Wrong date, please try again");
				} catch (DateTimeParseException e2) {
					System.out.println("Wrong date, please try again");
				}

			}
			while (time == null) {
				try {
					System.out.println("Please enter the departure time (HH:mm): ");
					Scanner getTime = new Scanner(System.in);
					String t = getTime.nextLine();
					time = LocalTime.parse(t + ":00");
				} catch (NoSuchElementException e2) {
					System.out.println("Wrong time, please try again");
				} catch (DateTimeParseException e2) {
					System.out.println("Wrong time, please try again");
				}
			}

			System.out.println("Please wait while we process your request...");

		}

		try {

			if (departure.checkTripAvailability(date, time)) {
				network = buildNetwork(time);
				getOptimalTripPlan(departure, destination);
			} else {
				System.out.println("No available Trips");
			}
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}

	}

	public static Graph buildNetwork(LocalTime time) throws ClassNotFoundException, SQLException {
		List<Stop> edges = StopHelper.getStops();

		List<Neighbor> vertices = new ArrayList<>();

		for (Stop stop : edges) {
			vertices.addAll(NeighborHelper.getNeighbors(stop, time));
		}

		return new Graph(vertices, edges);
	}

	public static void getOptimalTripPlan(Stop departure, Stop destination) {
		try {
			
			TripPlanner planner = new TripPlanner(network);
			planner.execute(departure);
			LinkedList<Stop> path = planner.getOptimalTrip(destination);

			if (path != null) {
				Plan plan = new Plan();
				plan.setDate(date);
				plan.setTime(time);
				plan.setDeparture_point(departure);
				plan.setDestination_point(destination);
				plan.setTravel_legs(path, network.getVertices(), time);
				plan.setDuration();

				System.out.println("Optimal Plan");
				System.out.println("Departure: " + plan.getDeparture_point().getName());
				System.out.println("Destination: " + plan.getDestination_point().getName());
				System.out.println("Departure Date and Time: " + plan.getDate() + " " + plan.getTime());
				System.out.println("Duration: " + plan.getDuration());
				System.out.println("<<<<<<<<<<<<<<<<<Travel Legs>>>>>>>>>>>>>>>");
				int i = 0;
				for (TravelLeg leg : plan.getTravel_legs()) {
					i++;
					System.out.println("<<<<<<<<<<Leg : " + i + ">>>>>>>>>>>>>");
					System.out.println("Start: " + leg.getStart().getName());
					System.out.println("End: " + leg.getEnd().getName());
					System.out.println("Duration: " + leg.getDuration());
					System.out.println("By: " + leg.getLeg_type());
					System.out.println("On route number: " + leg.getRouteNumber());
				}
			} else {
				System.out.println("No available Paths");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}