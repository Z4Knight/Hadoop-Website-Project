package com.oracle.website.date.order;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.oracle.blog.connection.JDBCMannger;


public class DateOrderDao {
	private String time;
	
	public DateOrderDao (String time) {
		this.time = time;
	}
	
	public DateOrderEntity getDateUserList() {
		Connection con = JDBCMannger.getConnection();
		HashSet<String> xdate = new HashSet<>();

		List<String> list1 = new ArrayList<>();

		DateOrderEntity dateUserEntity = new DateOrderEntity();
		try {
			PreparedStatement ps = con.prepareStatement("select date,ordertype,numbers,amounts,totalamounts "
					+ "from website_date_order_count");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String date = rs.getString(1);
				String orderType = rs.getString(2);
				String numbers = rs.getString(3);
				String amounts = rs.getString(4);
				String totalAmounts = rs.getString(5);
//				System.out.println(date + "=" + orderType);
				xdate.add(date);
				list1.add(numbers);
				list1.add(amounts);
				list1.add(totalAmounts);
				
			}
			
			List<String> xTime = new ArrayList<>(xdate);
			Collections.sort(xTime);
			for (int i = 0; i < list1.size(); i++) {
				System.out.println(list1.get(i) + "=" + i);
			}
			dateUserEntity.setBrand(xTime);
			dateUserEntity.setListData(list1);
//			dateUserEntity.setListData1(todayVals);
//			dateUserEntity.setListData2(totalVals);
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
