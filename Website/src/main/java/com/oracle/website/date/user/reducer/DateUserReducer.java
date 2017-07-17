package com.oracle.website.date.user.reducer;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.mapreduce.Reducer;

import com.oracle.website.date.dimention.DateUserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;

public class DateUserReducer extends Reducer<DateUserDimention, VisitorsCountValue, DateUserDimention, VisitorsCountValue> {

	
	@Override
	protected void reduce(DateUserDimention key, Iterable<VisitorsCountValue> value,
			Reducer<DateUserDimention, VisitorsCountValue, DateUserDimention, VisitorsCountValue>.Context context)
			throws IOException, InterruptedException {
		String event = key.getEvent();
		int newVal = 0;
		int totalVal = 0;
		VisitorsCountValue viCountValue = new VisitorsCountValue();
		if (!event.equals("")) {
			for (VisitorsCountValue visitorsCountValue : value) {
				newVal += visitorsCountValue.getNewVal();
			}
			viCountValue.setNewVal(newVal);
			context.write(key, viCountValue);
		}
		HashSet<String> todayVal = new HashSet<String>(); 
		if (event.equals("")) {
			for (VisitorsCountValue visitorsCountValue : value) {
				todayVal.add(visitorsCountValue.getuId());
			}
			totalVal = newVal + todayVal.size();
			viCountValue.setNewVal(newVal);
			viCountValue.setTodayVal(todayVal.size());
			viCountValue.setTotalVal(totalVal); 
			context.write(key, viCountValue);
		}
		
		
	}
}
