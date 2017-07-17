package com.oracle.website.date.user.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.oracle.website.date.dimention.DateUserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;
import com.oracle.website.date.user.constants.WebsiteConstants;

public class DateUserMapperCount extends Mapper<LongWritable, Text, DateUserDimention, VisitorsCountValue> {
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, DateUserDimention, VisitorsCountValue>.Context context)
			throws IOException, InterruptedException {
		String[] vals = value.toString().split("\t");
		String date = vals[0];
		int newVal = Integer.parseInt(vals[1]);
		int todayVal = Integer.parseInt(vals[2]);
		DateUserDimention dateUserDimention = new DateUserDimention();
		dateUserDimention.setEvent("");
		dateUserDimention.setDate(date);
		dateUserDimention.setType(WebsiteConstants.USERTYPE);
		VisitorsCountValue visitorsCountValue = new VisitorsCountValue("", newVal, todayVal);
		context.write(dateUserDimention, visitorsCountValue);
	}
}