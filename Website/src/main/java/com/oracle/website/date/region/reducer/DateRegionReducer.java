package com.oracle.website.date.region.reducer;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.oracle.website.date.dimention.DateRegionDimention;

public class DateRegionReducer extends Reducer<DateRegionDimention, Text, DateRegionDimention, IntWritable>{

	@Override
	protected void reduce(DateRegionDimention key, Iterable<Text> values,
			Reducer<DateRegionDimention, Text, DateRegionDimention, IntWritable>.Context context)
			throws IOException, InterruptedException {
		String type = key.getType();
		int sum = 0;
		if (type.equals("vi")) {
			for (Text text : values) {
				sum++;
			}
			context.write(key, new IntWritable(sum));
		}
		HashSet<String> sessions = new HashSet<>();
		if (type.equals("usd")) {
			for (Text val : values) {
				sessions.add(val.toString());
			}
			context.write(key, new IntWritable(sessions.size()));
		}
		if (type.equals("pv")) {
			int count = 0;
			for (Text val : values) {
				count += Integer.parseInt(val.toString());
			}
			if (count == 1) {
				context.write(key, new IntWritable(1));
			}
			
		}
	}
}
