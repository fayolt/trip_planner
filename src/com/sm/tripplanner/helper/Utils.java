package com.sm.tripplanner.helper;

import java.time.LocalDate;

import com.sm.tripplanner.DayType;

public class Utils {
	public static String getDay(LocalDate date) {
		String result = "";
		String day = date.getDayOfWeek().name();
		if(day.equals("MONDAY")||day.equals("TUESDAY")||day.equals("WEDNESDAY")||day.equals("THURSDAY")||day.equals("FRIDAY")) {
			result = DayType.WEEKDAY.toString();
		}else if(day.equals("SATURDAY")) {
			result = DayType.SATURDAY.toString();
		}else {
			result = DayType.SUNDAY_HOLIDAY.toString();
		}
	
		return result;
				
	}
}
