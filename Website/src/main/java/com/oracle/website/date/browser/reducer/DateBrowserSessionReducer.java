package com.oracle.website.date.browser.reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.oracle.website.date.dimention.DateBrowserSessionDimention;

public class DateBrowserSessionReducer extends Reducer<DateBrowserSessionDimention, Text, DateBrowserSessionDimention, Text>{

	@Override
	protected void reduce(DateBrowserSessionDimention key, Iterable<Text> vals,
			Reducer<DateBrowserSessionDimention, Text, DateBrowserSessionDimention, Text>.Context context)
			throws IOException, InterruptedException {
		String session = key.getDateSessionDimention().getSession();
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
