package com.sm.tripplanner.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
	static DBUtil dbutil = null;
	private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/tripplanner;AUTO_SERVER=TRUE";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";


	public DBUtil() {
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static DBUtil getInstance() {
		if (dbutil == null) {
			dbutil = new DBUtil();
		}
		return dbutil;
	}

	public Connection getConnection() {

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static ResultSet queryDB(Connection conn, String query, int rows)
			throws SQLException, ClassNotFoundException {
		Statement stmt = conn.createStatement();
		if (rows == 1) {
			stmt.setMaxRows(1);
		}
		ResultSet results = stmt.executeQuery(query);
		return results;
	}
	
	public void setupDatabase() throws SQLException {
		 Connection connection = getConnection();
		 Statement stmt = connection.createStatement();;
	        String routeQuery = "DROP TABLE IF EXISTS ROUTE; " +
	        		"CREATE TABLE ROUTE(" + 
        			"  ROUTE_ID VARCHAR(40) PRIMARY KEY, " + 
        			"  NAME VARCHAR(100), " + 
        			"  ROUTE_TYPE INT, " + 
        			"  ROUTE_SHORT_NAME INT, " + 
        			"  COMPETENT_AUTHORITY VARCHAR(50) " + 
        			"  )" + 
        			" AS SELECT " + 
        			"  ROUTE_ID, " + 
        			"  ROUTE_LONG_NAME, " + 
        			"  ROUTE_TYPE, " + 
        			"  ROUTE_SHORT_NAME, " + 
        			"  COMPETENT_AUTHORITY " + 
        			"FROM CSVREAD('database/routes.csv') ";
	        stmt.addBatch(routeQuery);
	        String tripQuery = "DROP TABLE IF EXISTS TRIP; " +
	        		"CREATE TABLE TRIP(" + 
        			"  TRIP_ID INT PRIMARY KEY, " + 
        			"  ROUTE_ID VARCHAR(40), " + 
        			"  SERVICE_ID INT, " + 
        			"  TRIP_LONG_NAME VARCHAR(100), " + 
        			"  DIRECTION_CODE VARCHAR(50) " + 
        			"  )" + 
        			" AS SELECT " + 
        			"  TRIP_ID, " + 
        			"  ROUTE_ID, " + 
        			"  SERVICE_ID, " + 
        			"  TRIP_LONG_NAME, " + 
        			"  DIRECTION_CODE " + 
        			"FROM CSVREAD('database/trips.csv') ";
	        
	        stmt.addBatch(tripQuery);
	        String stopQuery = "DROP TABLE IF EXISTS STOP; " +
	        		"CREATE TABLE STOP(" + 
        			"  STOP_ID INT PRIMARY KEY, " + 
        			"  STOP_NAME VARCHAR(40), " + 
        			"  STOP_LAT FLOAT, " + 
        			"  STOP_LON FLOAT " + 
        			"  )" + 
        			" AS SELECT " + 
        			"  STOP_ID, " + 
        			"  STOP_NAME, " + 
        			"  STOP_LAT, " + 
        			"  STOP_LON " + 
        			"FROM CSVREAD('database/stops.csv') ";
	        stmt.addBatch(stopQuery);
	        String stopTimesQuery = "DROP TABLE IF EXISTS STOP_TIME; " +
	        		"CREATE TABLE STOP_TIME(" + 
        			"  STOP_TIME_ID INT AUTO_INCREMENT PRIMARY KEY, " + 
        			"  TRIP_ID INT, " + 
        			"  DEPARTURE_TIME TIME, " + 
        			"  STOP_ID INT, " + 
        			"  STOP_SEQUENCE INT " + 
        			"  )" + 
        			" AS SELECT " + 
        			"  NULL, " + 
        			"  TRIP_ID, " + 
        			"  DEPARTURE_TIME, " + 
        			"  STOP_ID, " + 
        			"  STOP_SEQUENCE " + 
        			"FROM CSVREAD('database/stop_times.csv') ";
	        stmt.addBatch(stopTimesQuery);
	        String calendarQuery =  "DROP TABLE IF EXISTS CALENDAR; " +
	        		"CREATE TABLE CALENDAR(" + 
        			"  SERVICE_ID INT PRIMARY KEY, " + 
        			"  WEEKDAY INT, " + 
        			"  SATURDAY INT, " + 
        			"  SUNDAY INT " + 
        			"  )" + 
        			" AS SELECT " + 
        			"  SERVICE_ID, " + 
        			"  MONDAY, " + 
        			"  SATURDAY, " + 
        			"  SUNDAY " + 
        			"FROM CSVREAD('database/calendar.csv') ";
	        stmt.addBatch(calendarQuery);
	        
	        try {
	        	
	              stmt.executeBatch(); 
	              connection.commit();
	              
	        } catch (SQLException e) {
	            System.out.println("Exception Message " + e.getLocalizedMessage());
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            connection.close();
	        }
	 }

}
