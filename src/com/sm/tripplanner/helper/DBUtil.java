package com.sm.tripplanner.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	static DBUtil dbutil = null;

	public DBUtil() {
		try {
			Class.forName("org.relique.jdbc.csv.CsvDriver");
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
			conn = DriverManager.getConnection("jdbc:relique:csv:database");
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
		System.out.println(query);
		ResultSet results = stmt.executeQuery(query);
		return results;
	}

}
