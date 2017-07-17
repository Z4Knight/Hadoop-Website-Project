package com.oracle.website.date.user.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.oracle.website.date.dimention.DateUserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;
import com.oracle.website.date.user.constants.DateType;
import com.oracle.website.date.user.constants.WebsiteConstants;
import com.oracle.website.date.user.utils.DateUtils;

public class DateUserMapper extends Mapper<LongWritable, Text, DateUserDimention, VisitorsCountValue> {

	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, DateUserDimention, VisitorsCountValue>.Context context)
			throws IOException, InterruptedException {
		String[] vals = value.toString().trim().split("\t");
		String date = DateUtils.getDate(vals[1]);
		String[] info = vals[2].split("&");
		String event = "";
		String uId = "";
		DateUserDimention dateUserDimention = new DateUserDimention();
		dateUserDimention.setDate(date);
		dateUserDimention.setType(WebsiteConstants.USERTYPE);
		VisitorsCountValue visitorsCountValue = new VisitorsCountValue();

		if (info[0].contains("en=e_l")) {
			event = info[0];
			dateUserDimention.setEvent(event);
			if (info[4] != null&& !info[4].equals("")) {
				uId = info[4];
				visitorsCountValue.setuId(uId);
				visitorsCountValue.setNewVal(1);
				context.write(dateUserDimention, visitorsCountValue);
			}
		}
		
		if (!info[0].contains("en=e_l")) {
			dateUserDimention.setEvent(event);
			if (info[6] != null && !info[6].equals("")) {
				uId = info[6];
				visitorsCountValue.setuId(uId);
				context.write(dateUserDimention, visitorsCountValue);
			}
		}
		
	}
}
