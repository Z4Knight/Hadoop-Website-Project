package com.oracle.website.date.session.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.oracle.website.date.dimention.DateSessionDimention;
import com.oracle.website.date.user.utils.DateUtils;

public class DateSessionMapper extends Mapper<LongWritable, Text, DateSessionDimention, Text>{

	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, DateSessionDimention, Text>.Context context)
			throws IOException, InterruptedException {
		String[] vals = value.toString().trim().split("\t");
		String date = DateUtils.getDate(vals[1]);
		String info = vals[2];
		String session = "";
		String sessionTime = "";
		DateSessionDimention dateSessionDimention = new DateSessionDimention();
		dateSessionDimention.setDate(date);
		if (info.contains("u_sd=")) {
			session = info.substring(info.indexOf("u_sd="), info.indexOf("u_sd=") + 41);
			dateSessionDimention.setSession("");
			context.write(dateSessionDimention, new Text(session));
			// 直接存储时间
			sessionTime = info.substring(info.indexOf("c_time=") + 7, info.indexOf("c_time=") + 20);
			dateSessionDimention.setSession(session);
			context.write(dateSessionDimention, new Text(sessionTime));
		}
		
	}
}
