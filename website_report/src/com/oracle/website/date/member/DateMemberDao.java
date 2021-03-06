package com.oracle.website.date.member;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oracle.blog.connection.JDBCMannger;


public class DateMemberDao {
	
	public DateMemberEntity getDateUserList() {
		Connection con = JDBCMannger.getConnection();
		List<String> xdate = new ArrayList<>();
		List<String> newVals = new ArrayList<>();
		List<String> todayVals = new ArrayList<>();
		List<String> totalVals = new ArrayList<>();
		DateMemberEntity entity = new DateMemberEntity();
		try {
			PreparedStatement ps = con.prepareStatement("select date,newval,todayval,totalval "
					+ "from website_date_member_count");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String date = rs.getString(1);
				String newVal = rs.getString(2);
				String todayVal = rs.getString(3);
				String totalVal = rs.getString(4);
//				System.out.println(date);
				xdate.add(date);
				newVals.add(newVal);
				todayVals.add(todayVal);
				totalVals.add(totalVal);
			}
			entity.setBrand(xdate);
			entity.setListData(newVals);
			entity.setListData1(todayVals);
			entity.setListData2(totalVals);
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
