package com.oracle.website.date.browser.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.oracle.website.date.connection.JdbcManager1;

public class BrowserPvDao {
	public void addPv(String date, String browserInfo, String pv, String avgpv) {
		Connection con = JdbcManager1.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into website_date_browser_pv_count values(?,?,?,?)"
					+ " ON DUPLICATE KEY UPDATE pv = ?, avgpv = ?");
			ps.setString(1, date);
			ps.setString(2, browserInfo);
			ps.setString(3, pv);
			ps.setString(4, avgpv);
			ps.setString(5, pv);
			ps.setString(6, avgpv);
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
