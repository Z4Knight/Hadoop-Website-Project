package com.oracle.website.date.session.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.oracle.website.date.connection.JdbcManager1;

public class SessionDao {
	public void addSession(String date, String ssnumber, String ssduration, String avgssduration) {
		Connection con = JdbcManager1.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into website_date_session_count values(?,?,?,?)"
					+ " ON DUPLICATE KEY UPDATE ssnumber = ?, ssduration = ?,avgssduration = ?");
			ps.setString(1, date);
			ps.setString(2, ssnumber);
			ps.setString(3, ssduration);
			ps.setString(4, avgssduration);
			ps.setString(5, ssnumber);
			ps.setString(6, ssduration);
			ps.setString(7, avgssduration);
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
