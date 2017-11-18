package com.sm.tripplanner.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil 
{
	static DBUtil dbutil = null;
	
	public DBUtil()
	{
		try {
			Class.forName("org.relique.jdbc.csv.CsvDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DBUtil getInstance(){
		if(dbutil==null){
			dbutil=new DBUtil();
		}
		return dbutil;
	}
	
	public Connection getConnection()
	{
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:relique:csv:database");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}
