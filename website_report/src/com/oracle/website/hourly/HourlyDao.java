package com.oracle.website.hourly;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.oracle.blog.connection.JDBCMannger;


public class HourlyDao {
	
	public HourlyEntity getDateUserList() {
		Connection con = JDBCMannger.getConnection();
		HashSet<String> times = new HashSet<>();
		List<String> userVals = new ArrayList<>();
		List<String> sessionVals = new ArrayList<>();
		List<String> sessionLength = new ArrayList<>();
		HourlyEntity entity = new HourlyEntity();
		try {
			PreparedStatement ps = con.prepareStatement("select time,type,data "
					+ "from website_hourly_count");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String time = rs.getString(1);
				String type = rs.getString(2);
				String data = rs.getString(3);
//				System.out.println(date);
				times.add(time);
				if (type.equals("user_numbers")) {
					userVals.add(data);
				}
				if (type.equals("session_numbers")) {
					sessionVals.add(data);
				}
				if (type.equals("session_length")) {
					sessionLength.add(data);
				}
			}
			List<String> xtime = new ArrayList<>(times);
			Collections.sort(xtime);
			entity.setBrand(xtime);
			entity.setListData(userVals);
			entity.setListData1(sessionVals);
			entity.setListData2(sessionLength);
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
		return entity;
	}

}
