package com.oracle.website.clear;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ClearWebsite extends Mapper<LongWritable, Text, Text, NullWritable>{

	protected void map(LongWritable key, Text value, Mapper<LongWritable,Text,Text,NullWritable>.Context context) throws java.io.IOException ,InterruptedException {
		String val = value.toString();
		String[] vals = val.split(" ");
		String ip = "";
		String date = "";
		String information = "";
		if (val.contains("?")) {
			ip = vals[0];
			date = vals[3] + " " + vals[4];
			information = vals[6];
		}
		// /HHUspringmvc/images/logo.jpg?en=e_e&ca=event%E7%9A%84category%E5%90%8D%E7%A7%B0
		// &ac=event%E7%9A%84action%E5%90%8D%E7%A7%B0&kv_key1=value1&kv_key2=value2&du=1245
		// &ver=1&pl=website&sdk=js&u_ud=857317C2-DE1F-44B9-BB3A-B9BEC06BC00F&u_mid=zhangsan
		// &u_sd=33ACEDA5-521A-4078-A745-5E90332C255B&c_time=1497854221324&l=zh-CN
		// &b_iev=Mozilla%2F5.0%20(Windows%20NT%206.1%3B%20WOW64%3B%20rv%3A54.0)%20Gecko%2F20100101%20Firefox%2F54.0&b_rst=1920*1080
//		String[] infs = information.split("&");
//		String event = infs[0];
//		String category = infs[1];
//		String action = infs[2];
//		String kv1 = infs[3];
//		String kv2 = infs[4];
//		String du = infs[5];
//		String version = infs[6];
//		String plation = infs[7];
//		String sdk = infs[8];
//		String uId = infs[9];
//		String uMemberId = infs[10];
//		String uSessionId = infs[11];
//		String cTime = infs[12];
//		String language = infs[13];
//		String bInformation = infs[14];
//		String bResolution = infs[15];
		context.write(new Text(ip + "\t" + date + "\t" + information ), NullWritable.get());
	};
}
