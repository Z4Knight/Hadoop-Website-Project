package com.oracle.website.date.browser.dao;

import com.oracle.website.date.dimention.DateBrowserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;

public interface BaseDao {

	public void addVal(String date, String browserType, String newVal, String todayVal, String totalVal);
}
