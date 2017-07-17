package com.oracle.website.date.browser.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.oraccle.net.utils.BrowserUtils;
import com.oracle.website.date.dimention.DateBrowserDimention;
import com.oracle.website.date.dimention.DateUserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;
import com.oracle.website.date.user.constants.WebsiteConstants;
import com.oracle.website.date.user.utils.DateUtils;

public class DateBrowserUserMapper extends Mapper<LongWritable, Text, DateBrowserDimention, VisitorsCountValue>{

	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, DateBrowserDimention, VisitorsCountValue>.Context context)
			throws IOException, InterruptedException {
		String[] vals = value.toString().split("\t");
		String date = DateUtils.getDate(vals[1]);
		String[] info = vals[2].split("&");
		String event = "";
		String uId = "";
		String browserInfo = "Other";
		DateUserDimention dateUserDimention = new DateUserDimention();
		dateUserDimention.setDate(date);
		dateUserDimention.setType(WebsiteConstants.USERTYPE_BROWSER);
		VisitorsCountValue visitorsCountValue = new VisitorsCountValue();
		DateBrowserDimention dateBrowserDimention = new DateBrowserDimention();
		if (vals[2].contains("b_iev=")) {
			browserInfo = vals[2].substring(vals[2].indexOf("b_iev=") + 6, vals[2].indexOf("&b_rst="));
			browserInfo = BrowserUtils.getBrowserInfo(browserInfo);
		}
		dateBrowserDimention.setBrowserInfo(browserInfo);
		if (info[0].contains("en=e_l")) {
			event = info[0];
			dateUserDimention.setEvent(event);
			if (info[4] != null && !info[4].equals("")) {
				uId = info[0];
				visitorsCountValue.setuId(uId);
				visitorsCountValue.setNewVal(1);
				
				dateBrowserDimention.setDateUserDimention(dateUserDimention);
				context.write(dateBrowserDimention, visitorsCountValue);
			}
		}
		if (!info[0].contains("en=en_l")) {
			dateUserDimention.setEvent(event);
			dateBrowserDimention.setDateUserDimention(dateUserDimention);
			if (info[6] != null && !info[6].equals("")) {
				uId = info[6];
				visitorsCountValue.setuId(uId);
				context.write(dateBrowserDimention, visitorsCountValue);
			}
		}
		
	}
}
