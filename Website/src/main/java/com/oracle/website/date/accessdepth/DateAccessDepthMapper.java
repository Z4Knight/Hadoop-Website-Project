package com.oracle.website.date.accessdepth;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.oracle.website.date.dimention.DateAccessDepthDimention;
import com.oracle.website.date.user.utils.DateUtils;

public class DateAccessDepthMapper extends Mapper<LongWritable, Text, DateAccessDepthDimention, Text>{

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, DateAccessDepthDimention, Text>.Context context)
			throws IOException, InterruptedException {
		String[] vals = value.toString().split("\t");
		String date = DateUtils.getDate(vals[1]);
		String info = vals[2];
		String userId = "";
		String url = "";
		DateAccessDepthDimention dateAccessDepthDimention = new DateAccessDepthDimention();
		if (info.contains("u_ud=") && info.contains("p_url=")) {
			userId = info.substring(info.indexOf("u_ud="), info.indexOf("u_ud=") + 41);
			url = info.split("&")[1];
			dateAccessDepthDimention.setDate(date);
			dateAccessDepthDimention.setUserId(userId);
			context.write(dateAccessDepthDimention, new Text(url));
		}
	}
}
