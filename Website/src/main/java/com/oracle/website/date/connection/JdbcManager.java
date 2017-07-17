package com.oracle.website.date.connection;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.hadoop.conf.Configuration;

public class JdbcManager {
	public static Connection getConnection(Configuration conf) {
		Connection con =null;
		try {
			Class.forName(conf.get("driver"));
			con = DriverManager.getConnection
					(conf.get("url"), conf.get("username"), conf.get("userpwd"));
			// 设置不自动提交
			con.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

}
