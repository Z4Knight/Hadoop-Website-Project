package com.oracle.website.hourly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.oracle.website.date.user.constants.WebsiteConstants;

public class HourlyReducer extends Reducer<HourlyDimention, Text, HourlyDimention, Text>{

	@Override
	protected void reduce(HourlyDimention key, Iterable<Text> values,
			Reducer<HourlyDimention, Text, HourlyDimention, Text>.Context context)
			throws IOException, InterruptedException {
		String type = key.getType();
		HashSet<String> numbers = new HashSet<>();
		if (type.equals(WebsiteConstants.HOURLY_USER)) {
			for (Text text : values) {
				numbers.add(text.toString());
			}
			context.write(key, new Text(Integer.toString(numbers.size())));
		}
		numbers.clear();
		if (type.equals(WebsiteConstants.HOURLY_SESSION_NUMBERS)) {
			for (Text text : values) {
				numbers.add(text.toString());
			}
			context.write(key, new Text(Integer.toString(numbers.size())));
		}
		ArrayList<Long> sessionTimes = new ArrayList<>();
		if (type.equals(WebsiteConstants.HOURLY_SESSION_LENGTH)) {
			for (Text text : values) {
				sessionTimes.add(Long.parseLong(text.toString().trim()));
			}
			Collections.sort(sessionTimes);
			long max = sessionTimes.get(sessionTimes.size() - 1);
			long min = sessionTimes.get(0);
			String sessionTime = Long.toString(max - min);
			context.write(key, new Text(sessionTime));
		}
	}
}
