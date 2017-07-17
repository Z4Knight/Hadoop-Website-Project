package com.oracle.website.date.order;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.oracle.website.date.dimention.DateOrderDimention;
import com.oracle.website.date.dimention.OrderValuesCount;
import com.oracle.website.date.user.constants.WebsiteConstants;
import com.oracle.website.date.user.utils.DateUtils;

public class DateOrderMapper extends Mapper<LongWritable, Text, DateOrderDimention, OrderValuesCount>{

	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, DateOrderDimention, OrderValuesCount>.Context context)
			throws IOException, InterruptedException {
		String[] vals = value.toString().split("\t");
		String date = DateUtils.getDate(vals[1]);
		String info = vals[2];
		String event = "";
		int orderNumbers = 0;
		double orderAmounts = 0;
		DateOrderDimention dateOrderDimention = new DateOrderDimention();
		OrderValuesCount orderValuesCount = new OrderValuesCount();
		dateOrderDimention.setDate(date);
		if (info.contains("en=e_cs")) {
			event = "en=e_cs";
			dateOrderDimention.setEvent(event);
			dateOrderDimention.setType(WebsiteConstants.ORDER_E_CS);
			orderNumbers = 1;
			if (info.contains("cua=")) {
				String amount = info.substring(info.indexOf("cua=") + 4, info.indexOf("cua=") + 10);
				orderAmounts = Double.parseDouble(amount);
			}
			orderValuesCount.setOrderNumbers(orderNumbers);
			orderValuesCount.setOrderAmounts(orderAmounts);
			context.write(dateOrderDimention, orderValuesCount);
		}
		if (info.contains("en=e_cr")) {
			event = "en=e_cr";
			dateOrderDimention.setType(WebsiteConstants.ORDER_E_CRT);
			dateOrderDimention.setEvent(event);
			orderNumbers = 1;
			if (info.contains("cua=")) {
				String amount = info.substring(info.indexOf("cua=") + 4, info.indexOf("cua=") + 10);
				orderAmounts = Double.parseDouble(amount);
			}
			orderValuesCount.setOrderNumbers(orderNumbers);
			orderValuesCount.setOrderAmounts(orderAmounts);
			context.write(dateOrderDimention, orderValuesCount);
		}
		if (info.contains("oid=")) {
			event = "oid=";
			dateOrderDimention.setType(WebsiteConstants.ORDER_ONUMS);
			dateOrderDimention.setEvent(event);
			orderNumbers = 1;
			if (info.contains("cua=")) {
				String amount = info.substring(info.indexOf("cua=") + 4, info.indexOf("cua=") + 10);
				orderAmounts = Double.parseDouble(amount);
			}
			orderValuesCount.setOrderNumbers(orderNumbers);
			orderValuesCount.setOrderAmounts(orderAmounts);
			context.write(dateOrderDimention, orderValuesCount);
		}
	}
}
