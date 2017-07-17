package com.oracle.website.date.browser.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.oracle.website.date.connection.JdbcManager1;
import com.oracle.website.date.dimention.DateBrowserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;

public class BrowserUserDao implements BaseDao {

	public void addVal(String date, String browserType, String newVal, String todayVal, String totalVal) {
		Connection con = JdbcManager1.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into website_date_browser_user_count values(?,?,?,?,?)"
					+ " ON DUPLICATE KEY UPDATE newval = ?, todayval = ?,totalval = ?");
			ps.setString(1, date);
			ps.setString(2, browserType);
			ps.setString(3, newVal);
			ps.setString(4, todayVal);
			ps.setString(5, totalVal);
			ps.setString(6, newVal);
			ps.setString(7, todayVal);
			ps.setString(8, totalVal);
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
