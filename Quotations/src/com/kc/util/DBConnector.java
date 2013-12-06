package com.kc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DBConnector {

	private static final Logger LOG = LogManager.getLogger(DBConnector.class);

	private static Connection conn;
	private static String url = "jdbc:mysql://192.168.1.5:3306/quotation";
	private static String user = "root";// Username of database
	private static String pass = "root";// Password of database

	public static Connection connect() throws SQLException {
		
		LOG.info("Enter : connect");
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException cnfe) {
			System.err.println("Error: " + cnfe.getMessage());
		} catch (InstantiationException ie) {
			System.err.println("Error: " + ie.getMessage());
		} catch (IllegalAccessException iae) {
			System.err.println("Error: " + iae.getMessage());
		}
		conn = DriverManager.getConnection(url, user, pass);
		
		LOG.info("Exit : connect");
		return conn;
	}

	public static Connection getConnection() throws SQLException,
			ClassNotFoundException {
		LOG.info("Enter : getConnection");
		if (conn != null && !conn.isClosed())
			return conn;
		connect();
		LOG.info("Exit : getConnection");
		return conn;
	}
}
