package com.oracle.pv.utils;

import java.text.NumberFormat;

public class DataTransUtils {
	public static String pointToPercent(double point) {
        
        //获取格式化对象
        NumberFormat nf = NumberFormat.getPercentInstance();
        
        //设置百分数精确到小数点保留几位小数
        nf.setMinimumFractionDigits(2);
        
        String percent = nf.format(point);
        
        return percent;
    }
	
	public static String transAmount(double amount) {
		if (amount < 10000) {
			return Double.toString(amount);
		} else if (amount > 10000) {
			double val = amount / 10000;
			String result = String.format("%.3f", val);
			return result + "W";
		}
		return null;
	}
}
