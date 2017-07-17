package com.oracle.website.date.browser.reducer;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.mapreduce.Reducer;

import com.oracle.website.date.dimention.DateBrowserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;

public class DateBrowserUserReducer extends Reducer<DateBrowserDimention, VisitorsCountValue, DateBrowserDimention, VisitorsCountValue>{

	@Override
	protected void reduce(DateBrowserDimention key, Iterable<VisitorsCountValue> value,
			Reducer<DateBrowserDimention, VisitorsCountValue, DateBrowserDimention, VisitorsCountValue>.Context context)
			throws IOException, InterruptedException {
		String event = key.getDateUserDimention().getEvent();
		int newVal = 0;
		int totalVal = 0;
		VisitorsCountValue visitorsCountValue = new VisitorsCountValue();
		if (!event.equals("")) {
			for (VisitorsCountValue val : value) {
				newVal += val.getNewVal();
			}
			visitorsCountValue.setNewVal(newVal);
			context.write(key, visitorsCountValue);
		}
		HashSet<String> todayVal = new HashSet<String>();
		if (event.equals("")) {
			for (VisitorsCountValue val : value) {
				todayVal.add(val.getuId());
			}
			totalVal = newVal + todayVal.size();
			visitorsCountValue.setNewVal(newVal);
			visitorsCountValue.setTodayVal(todayVal.size());
			visitorsCountValue.setTotalVal(totalVal);
			context.write(key, visitorsCountValue);
		}
	}
}
