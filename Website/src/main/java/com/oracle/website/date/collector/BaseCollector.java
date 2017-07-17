package com.oracle.website.date.collector;

import java.sql.PreparedStatement;

import com.oracle.website.date.dimention.DateUserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;

public interface BaseCollector {
	public void setPreparstatement(PreparedStatement ps, 
			DateUserDimention key, VisitorsCountValue value);
}
