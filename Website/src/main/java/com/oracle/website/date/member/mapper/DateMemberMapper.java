package com.oracle.website.date.member.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.oracle.website.date.dimention.DateUserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;
import com.oracle.website.date.user.constants.WebsiteConstants;
import com.oracle.website.date.user.utils.DateUtils;

public class DateMemberMapper extends Mapper<LongWritable, Text, DateUserDimention, VisitorsCountValue>{

	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, DateUserDimention, VisitorsCountValue>.Context context)
			throws IOException, InterruptedException {
		String[] vals = value.toString().split("\t");
		String date = DateUtils.getDate(vals[1]);
		String[] info = vals[2].split("&");
		String event = "";
		String u_mid = "u_mid";
		
		DateUserDimention dateUserDimention = new DateUserDimention();
		dateUserDimention.setDate(date);
		dateUserDimention.setType(WebsiteConstants.MEMBERTYPE);
		VisitorsCountValue visitorsCountValue = new VisitorsCountValue();
		if (vals[2].contains("p_url=http%3A%2F%2F172.16.0.150%3A8080%2FBIG_DATA_LOG2%2Fdemo4.jsp")) {
			event = "p_url=http%3A%2F%2F172.16.0.150%3A8080%2FBIG_DATA_LOG2%2Fdemo4.jsp";
			dateUserDimention.setEvent(event);
			if (info[8].contains(u_mid)) {
				visitorsCountValue.setNewVal(1);
				visitorsCountValue.setuId("");
				context.write(dateUserDimention, visitorsCountValue);
			}
		}
		if (vals[2].contains(u_mid)) {
			dateUserDimention.setEvent(event);
			visitorsCountValue.setTodayVal(1);
			visitorsCountValue.setuId("");
			context.write(dateUserDimention, visitorsCountValue);
		}
	}
}
