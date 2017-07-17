package com.oracle.website.date.browser.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.oraccle.net.utils.BrowserUtils;
import com.oracle.website.date.dimention.DateBrowserPvDimention;
import com.oracle.website.date.user.utils.DateUtils;

public class DateBrowserPvMapper extends Mapper<LongWritable, Text, DateBrowserPvDimention, Text> {

	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, DateBrowserPvDimention, Text>.Context context)
			throws IOException, InterruptedException {
		String[] vals = value.toString().split("\t");
		String date = DateUtils.getDate(vals[1]);
		String browserInfo = "Other";
		String userId = "";
		DateBrowserPvDimention dateBrowserPvDimention = new DateBrowserPvDimention();
		dateBrowserPvDimention.setDate(date);
		if (vals[2].contains("b_iev=")) {
			browserInfo = vals[2].substring(vals[2].indexOf("b_iev=") + 6, vals[2].indexOf("&b_rst="));
			browserInfo = BrowserUtils.getBrowserInfo(browserInfo);
			dateBrowserPvDimention.setBrowserInfo(browserInfo);
			dateBrowserPvDimention.setType("pv");
			context.write(dateBrowserPvDimention, new Text("1"));
			if (vals[2].contains("u_ud=")) {
				dateBrowserPvDimention.setBrowserInfo(browserInfo);
				dateBrowserPvDimention.setType("ud");
				userId = vals[2].substring(vals[2].indexOf("u_ud="), vals[2].indexOf("u_ud=") + 41);
				context.write(dateBrowserPvDimention, new Text(userId));
			}
		}
		
		
	}
}
