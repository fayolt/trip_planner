package com.sm.tripplanner;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.sm.tripplanner.helper.DBUtil;
import com.sm.tripplanner.helper.NeighborHelper;

public class Controller {

	public static void main(String[] args) {
		
		try {
			DBUtil.getInstance().setupDatabase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*List<Neighbor> neighbors  = new ArrayList<>();
		Stop destination = new Stop();
		destination.setStop_id(33506);
		boolean available = false;
		try {
			//available = destination.checkTripAvailability(LocalDate.parse("2017-11-17"), LocalTime.parse("23:21:00"));
			neighbors = NeighborHelper.getNeighbors(destination.getStop_id());
		} catch (ClassNotFoundException  | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		System.out.println(neighbors.size());
*/
	}

}
