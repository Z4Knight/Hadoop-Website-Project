package com.oracle.website.date.region.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.oracle.website.date.connection.JdbcManager1;

public class RegionDao {
	public void addVal(String date, String ip, String visitors, String sessionNums, String pvNums, String jumpRation) {
		Connection con = JdbcManager1.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into website_date_region_count values(?,?,?,?,?,?)"
					+ " ON DUPLICATE KEY UPDATE visitors = ?, ssnums = ?,pvnums = ?, jumpration = ?");
			ps.setString(1, date);
			ps.setString(2, ip);
			ps.setString(3, visitors);
			ps.setString(4, sessionNums);
			ps.setString(5, pvNums);
			ps.setString(6, jumpRation);
			ps.setString(7, visitors);
			ps.setString(8, sessionNums);
			ps.setString(9, pvNums);
			ps.setString(10, jumpRation);
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
