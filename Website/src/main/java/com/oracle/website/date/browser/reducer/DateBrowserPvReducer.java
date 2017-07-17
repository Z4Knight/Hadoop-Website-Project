package com.oracle.website.date.browser.reducer;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.oracle.website.date.dimention.DateBrowserPvDimention;

public class DateBrowserPvReducer extends Reducer<DateBrowserPvDimention, Text, DateBrowserPvDimention, Text>{

	@Override
	protected void reduce(DateBrowserPvDimention key, Iterable<Text> values,
			Reducer<DateBrowserPvDimention, Text, DateBrowserPvDimention, Text>.Context context)
			throws IOException, InterruptedException {
		
		if (key.getType().equals("pv")) {
			int sum = 0;
			for (Text val : values) {
				sum++;
			}
			context.write(key, new Text(Integer.toString(sum)));
		}
		
		if (key.getType().equals("ud")) {
			HashSet<String> users = new HashSet<String>();
			for (Text val : values) {
				users.add(val.toString());
			}
			
			context.write(key, new Text(Integer.toString(users.size())));
		}
		
	}
}
