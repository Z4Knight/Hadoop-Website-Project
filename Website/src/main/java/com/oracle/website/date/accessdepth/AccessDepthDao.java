package com.oracle.website.date.accessdepth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.oracle.website.date.connection.JdbcManager1;

public class AccessDepthDao {
	public void addVal(String date, String userId, String accessDepth) {
		Connection con = JdbcManager1.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into website_date_accessdepth_count values(?,?,?)"
					+ " ON DUPLICATE KEY UPDATE accessdepth = ?");
			ps.setString(1, date);
			ps.setString(2, userId);
			ps.setString(3, accessDepth);
			ps.setString(4, accessDepth);
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
