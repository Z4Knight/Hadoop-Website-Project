package com.oracle.website.date.browser.reducer;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Reducer;

import com.oracle.website.date.dimention.DateBrowserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;

public class DateBrowserMemberReducer extends Reducer<DateBrowserDimention, VisitorsCountValue, DateBrowserDimention, VisitorsCountValue> {

	@Override
	protected void reduce(DateBrowserDimention key, Iterable<VisitorsCountValue> val,
			Reducer<DateBrowserDimention, VisitorsCountValue, DateBrowserDimention, VisitorsCountValue>.Context context)
			throws IOException, InterruptedException {
		String event = key.getDateUserDimention().getEvent();
		int newVal = 0;
		int todayVal = 0;
		VisitorsCountValue viCountValue;
		if (!event.equals("")) {
			for (VisitorsCountValue visitorsCountValue : val) {
				newVal += visitorsCountValue.getNewVal();
			}
		    viCountValue = new VisitorsCountValue("", newVal, todayVal);
			context.write(key, viCountValue);
		}
		
		if (event.equals("")) {
			for (VisitorsCountValue visitorsCountValue : val) {
				todayVal += visitorsCountValue.getTodayVal();
			}
			viCountValue = new VisitorsCountValue("", newVal, todayVal);
			context.write(key, viCountValue);
		}
	}
}
