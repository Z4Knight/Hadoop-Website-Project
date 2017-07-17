package com.oracle.website.hourly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.oracle.website.date.connection.JdbcManager1;

public class HourlyDao {
	public void addVal(String time, String type, String data) {
		Connection con = JdbcManager1.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into website_hourly_count values(?,?,?)"
					+ " ON DUPLICATE KEY UPDATE data = ?");
			ps.setString(1, time);
			ps.setString(2, type);
			ps.setString(3, data);
			ps.setString(4, data);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
