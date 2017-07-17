package com.oracle.website.date.user.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

	public static String getDate(String src) {
		SimpleDateFormat in = new SimpleDateFormat("[dd/MMM/yyyy:HH:mm:ss ZZZZZ]", Locale.US);
		SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = in.parse(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out.format(d);
	}
	
	public static String getHourly(String src) {
		SimpleDateFormat in = new SimpleDateFormat("[dd/MMM/yyyy:HH:mm:ss ZZZZZ]", Locale.US);
		SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		Date d = null;
		try {
			d = in.parse(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out.format(d);
	}
	
	public static String getTime(String times) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		// 使用中国时区的话这个差值应该是28800000ms，也就是8小时。显然，这8个小时的差别就是由于时区产生的，
		// 而如果在开发与日期时间紧密相关的程序时忽略了这一时差，很可能就会产生许多匪夷所思的误差和结果。
		// 解决： 可以将毫秒数 - TimeZone.getDefault().getRawOffset();即可
		return sdf.format(new Date(Long.parseLong(times) - TimeZone.getDefault().getRawOffset())); 
//		return sdf.format(new Date(Long.parseLong(times))); 
	}
	
 

	
	public static void main(String[] args) {
		System.out.println(getHourly("[20/Jun/2017:11:27:31 +0800]"));
	}
}
