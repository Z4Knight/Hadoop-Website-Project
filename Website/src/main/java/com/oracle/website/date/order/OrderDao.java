package com.oracle.website.date.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.oracle.website.date.connection.JdbcManager1;

public class OrderDao {
	public void addVal(String date, String orderType, String orderNumbers, String orderAmounts, String totalAmounts) {
		Connection con = JdbcManager1.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into website_date_order_count values(?,?,?,?,?)"
					+ " ON DUPLICATE KEY UPDATE numbers = ?,amounts = ?,totalamounts = ?");
			ps.setString(1, date);
			ps.setString(2, orderType);
			ps.setString(3, orderNumbers);
			ps.setString(4, orderAmounts);
			ps.setString(5, totalAmounts);
			ps.setString(6, orderNumbers);
			ps.setString(7, orderAmounts);
			ps.setString(8, totalAmounts);
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
