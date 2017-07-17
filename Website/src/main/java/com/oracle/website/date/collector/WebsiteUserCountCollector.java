package com.oracle.website.date.collector;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.oracle.website.date.dimention.DateUserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;

public class WebsiteUserCountCollector implements BaseCollector{

	public void setPreparstatement(PreparedStatement ps, DateUserDimention key, VisitorsCountValue value) {
		try {
			ps.setString(1, key.getDate());
			ps.setString(2, Integer.toString(value.getNewVal()));
			ps.setString(3, Integer.toString(value.getTodayVal()));
			ps.setString(4, Integer.toString(value.getTotalVal()));
			ps.setString(5, Integer.toString(value.getNewVal()));
			ps.setString(6, Integer.toString(value.getTodayVal()));
			ps.setString(7, Integer.toString(value.getTotalVal()));
			ps.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	} 

	
}
