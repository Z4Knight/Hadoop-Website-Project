package com.oracle.website.date.region;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.blog.connection.JDBCMannger;


public class DateRegionDao {

	
	public DateRegionEntity getDateUserList() {
		Connection con = JDBCMannger.getConnection();
		List<String> xdate = new ArrayList<>();
		List<String> newVals = new ArrayList<>();
		List<String> todayVals = new ArrayList<>();
		List<String> totalVals = new ArrayList<>();
		List<String> visitorVals = new ArrayList<>();
		DateRegionEntity dateUserEntity = new DateRegionEntity();
		try {
			PreparedStatement ps = con.prepareStatement("select date,ip,visitors,ssnums,pvnums,jumpration "
					+ "from website_date_region_count ");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String date = rs.getString(1);
				String visitors = rs.getString(3);
				String newVal = rs.getString(4);
				String todayVal = rs.getString(5);
				String totalVal = rs.getString(6);
//				System.out.println(date);
				xdate.add(date);
				newVals.add(newVal);
				todayVals.add(todayVal);
				totalVals.add(totalVal);
				visitorVals.add(visitors);
				
			}
			dateUserEntity.setListData3(visitorVals);
			dateUserEntity.setBrand(xdate);
			dateUserEntity.setListData(newVals);
			dateUserEntity.setListData1(todayVals);
			dateUserEntity.setListData2(totalVals);
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
		return dateUserEntity;
	}

}
