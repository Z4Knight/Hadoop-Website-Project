package com.oracle.website.date.region.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.oracle.ip.utils.IPUtils;
import com.oracle.website.date.dimention.DateRegionDimention;
import com.oracle.website.date.user.utils.DateUtils;

public class DateRegionMapper extends Mapper<LongWritable, Text, DateRegionDimention, Text> {

	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, DateRegionDimention, Text>.Context context)
			throws IOException, InterruptedException {
		String[] vals = value.toString().split("\t");
		String ip = IPUtils.getIPAddress(vals[0]);
		String date = DateUtils.getDate(vals[1]);
		String session = "";
		DateRegionDimention dateRegionDimention = new DateRegionDimention();
		dateRegionDimention.setDate(date);
		dateRegionDimention.setIp(ip);
		if (!ip.equals("unknown unknown")) {
			dateRegionDimention.setSession(session);
			dateRegionDimention.setType("vi");
			context.write(dateRegionDimention, new Text("1"));
			if (vals[2].contains("u_sd=")) {
				session = vals[2].substring(vals[2].indexOf("u_sd="), vals[2].indexOf("u_sd=") + 41);
				dateRegionDimention.setSession("");
				dateRegionDimention.setType("usd");
				context.write(dateRegionDimention, new Text(session));
				dateRegionDimention.setType("pv");
				dateRegionDimention.setSession(session);
				context.write(dateRegionDimention, new Text("1"));
			}
		}
		
	}
}
