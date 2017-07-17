package com.oracle.website.hourly;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.oracle.website.date.user.constants.WebsiteConstants;
import com.oracle.website.date.user.utils.DateUtils;

public class HourlyMapper extends Mapper<LongWritable, Text, HourlyDimention, Text>{

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, HourlyDimention, Text>.Context context)
			throws IOException, InterruptedException {
		String[] vals = value.toString().split("\t");
		String time = DateUtils.getHourly(vals[1]);
		String info = vals[2];
		String userId = "";
		String sessionId = "";
		String sessionDuration = "";
		HourlyDimention hourlyDimention = new HourlyDimention();
		hourlyDimention.setTime(time);
		String type = WebsiteConstants.HOURLY_USER;
		if (info.contains("u_ud=")) {
			userId = info.substring(info.indexOf("u_ud="), info.indexOf("u_ud=") + 41);
			hourlyDimention.setType(type);
			context.write(hourlyDimention, new Text(userId));
		}
		if (info.contains("u_sd=")) {
			type = WebsiteConstants.HOURLY_SESSION_NUMBERS;
			sessionId = info.substring(info.indexOf("u_sd="), info.indexOf("u_sd=") + 41);
			hourlyDimention.setType(type);
			context.write(hourlyDimention, new Text(sessionId));
			sessionDuration = info.substring(info.indexOf("c_time=") + 7, info.indexOf("c_time=") + 20);
			type = WebsiteConstants.HOURLY_SESSION_LENGTH;
			hourlyDimention.setType(type);
			context.write(hourlyDimention, new Text(sessionDuration));
		}
	}
}
