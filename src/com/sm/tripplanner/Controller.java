package com.sm.tripplanner;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Controller {

	public static void main(String[] args) {
		Stop destination = new Stop();
		destination.setStop_id(33506);
		boolean available = false;
		try {
			available = destination.checkTripAvailability(LocalDate.parse("2017-11-17"), LocalTime.parse("02:21:00"));
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		System.out.println(available);

	}

}
