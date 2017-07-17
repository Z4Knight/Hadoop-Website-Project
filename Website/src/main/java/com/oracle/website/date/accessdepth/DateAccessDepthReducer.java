package com.oracle.website.date.accessdepth;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.oracle.website.date.dimention.DateAccessDepthDimention;

public class DateAccessDepthReducer extends Reducer<DateAccessDepthDimention, Text, DateAccessDepthDimention, IntWritable>{

	@Override
	protected void reduce(DateAccessDepthDimention key, Iterable<Text> value,
			Reducer<DateAccessDepthDimention, Text, DateAccessDepthDimention, IntWritable>.Context context)
			throws IOException, InterruptedException {
		HashSet<String> urls = new HashSet<>();
		for (Text val : value) {
			urls.add(val.toString());
		}
		context.write(key, new IntWritable(urls.size()));
		
	}
}
