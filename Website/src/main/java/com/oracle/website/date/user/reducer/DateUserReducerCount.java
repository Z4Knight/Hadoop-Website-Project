package com.oracle.website.date.user.reducer;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.oracle.website.date.dimention.DateUserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;

public class DateUserReducerCount extends Reducer<DateUserDimention, VisitorsCountValue, DateUserDimention, VisitorsCountValue>{

	int sum = 0;
	@Override
	protected void reduce(DateUserDimention key, Iterable<VisitorsCountValue> values,
			Reducer<DateUserDimention, VisitorsCountValue, DateUserDimention, VisitorsCountValue>.Context context)
			throws IOException, InterruptedException {
		int newVal = 0;
		int todayVal = 0;
		int total = 0;
		for (VisitorsCountValue visitorsCountValue : values) {
			newVal += visitorsCountValue.getNewVal();
			todayVal += visitorsCountValue.getTodayVal();
			total = newVal + todayVal;
		}
		sum += total;
		VisitorsCountValue visitorsCountValue = new VisitorsCountValue("", newVal, todayVal);
		visitorsCountValue.setTotalVal(sum);
		context.write(key, visitorsCountValue);
	}
}
