package com.oracle.website.date.browser.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.oraccle.net.utils.BrowserUtils;
import com.oracle.website.date.dimention.DateBrowserDimention;
import com.oracle.website.date.dimention.DateBrowserSessionDimention;
import com.oracle.website.date.dimention.DateSessionDimention;
import com.oracle.website.date.user.utils.DateUtils;

public class DateBrowserSessionMapper extends Mapper<LongWritable, Text, DateBrowserSessionDimention, Text>{

	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, DateBrowserSessionDimention, Text>.Context context)
			throws IOException, InterruptedException {
		String[] vals = value.toString().trim().split("\t");
		String date = DateUtils.getDate(vals[1]);
		String info = vals[2];
		String session = "";
		String sessionTime = "";
		String browserInfo = "Other";
		DateSessionDimention dateSessionDimention = new DateSessionDimention();
		dateSessionDimention.setDate(date);
		DateBrowserSessionDimention browserSessionDimention = new DateBrowserSessionDimention();
		if (vals[2].contains("b_iev=")) {
			browserInfo = vals[2].substring(vals[2].indexOf("b_iev=") + 6, vals[2].indexOf("&b_rst="));
			browserInfo = BrowserUtils.getBrowserInfo(browserInfo);
		}
		browserSessionDimention.setBrowserInfo(browserInfo);
		if (info.contains("u_sd=")) {
			session = info.substring(info.indexOf("u_sd="), info.indexOf("u_sd=") + 41);
			dateSessionDimention.setSession("");
			browserSessionDimention.setDateSessionDimention(dateSessionDimention);
			context.write(browserSessionDimention, new Text(session));
			// 直接存储时间
			sessionTime = info.substring(info.indexOf("c_time=") + 7, info.indexOf("c_time=") + 20);
			dateSessionDimention.setSession(session);
			context.write(browserSessionDimention, new Text(sessionTime));
		}

	}
}
