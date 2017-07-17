package com.oracle.website.date.member.reducer;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Reducer;

import com.oracle.website.date.dimention.DateUserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;

public class DateMemberReduer extends Reducer<DateUserDimention, VisitorsCountValue, DateUserDimention, VisitorsCountValue>{

	@Override
	protected void reduce(DateUserDimention key, Iterable<VisitorsCountValue> val,
			Reducer<DateUserDimention, VisitorsCountValue, DateUserDimention, VisitorsCountValue>.Context context)
			throws IOException, InterruptedException {
		String event = key.getEvent();
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
