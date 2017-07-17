package com.oracle.website.date.browser.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.oracle.website.date.connection.JdbcManager1;

public class BrowserSessionDao {
	public void addSession(String date, String browserInfo, String ssnumber, String ssduration, String avgssduration) {
		Connection con = JdbcManager1.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into website_date_browser_session_count values(?,?,?,?,?)"
					+ " ON DUPLICATE KEY UPDATE ssnumber = ?, ssduration = ?,avgssduration = ?");
			ps.setString(1, date);
			ps.setString(2, browserInfo);
			ps.setString(3, ssnumber);
			ps.setString(4, ssduration);
			ps.setString(5, avgssduration);
			ps.setString(6, ssnumber);
			ps.setString(7, ssduration);
			ps.setString(8, avgssduration);
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
