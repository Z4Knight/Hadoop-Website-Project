package com.oracle.website.date.session.reduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.oracle.website.date.dimention.DateSessionDimention;
import com.oracle.website.date.user.utils.DateUtils;

public class DateSessionReducer extends Reducer<DateSessionDimention, Text, DateSessionDimention, Text>{

	@Override
	protected void reduce(DateSessionDimention key, Iterable<Text> vals,
			Reducer<DateSessionDimention, Text, DateSessionDimention, Text>.Context context)
			throws IOException, InterruptedException {
		String session = key.getSession();
		HashSet<String> sessionVal = new HashSet<String>();
		ArrayList<Long> sessionTimeArray = new ArrayList<Long>();
		if (session.equals("")) {
			for (Text val : vals) {
				sessionVal.add(val.toString());
			}
			context.write(key, new Text(Integer.toString(sessionVal.size())));
		}
		if (!session.equals("")) {
			for (Text val : vals) {
				sessionTimeArray.add(Long.parseLong(val.toString().trim()));
			}
			Collections.sort(sessionTimeArray);
			long max = sessionTimeArray.get(sessionTimeArray.size() - 1);
			long min = sessionTimeArray.get(0);
			String sessionTime = Long.toString(max - min);
			context.write(key, new Text(sessionTime));
		}
	}
}
