package com.oracle.website.date.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcManager1 {
	public static Connection getConnection() {
		Connection con =null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/website", "root", "admin");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}
}
