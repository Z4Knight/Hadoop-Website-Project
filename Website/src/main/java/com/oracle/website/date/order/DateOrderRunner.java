package com.oracle.website.date.order;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.oracle.website.date.dimention.DateOrderDimention;
import com.oracle.website.date.dimention.OrderValuesCount;

public class DateOrderRunner implements Tool{
	private Configuration conf;

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public Configuration getConf() {
		return conf;
	}

	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(conf);
		job.setJarByClass(DateOrderRunner.class);
		
		job.setMapperClass(DateOrderMapper.class);
		job.setMapOutputKeyClass(DateOrderDimention.class);
		job.setMapOutputValueClass(OrderValuesCount.class);
		
		job.setOutputKeyClass(DateOrderDimention.class);
		job.setReducerClass(DateOrderReducer.class);
		job.setOutputValueClass(OrderValuesCount.class);
		
		job.setOutputFormatClass(MySqlOrderFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://z4knight1:9000/website/part-r-00000"));
//		FileOutputFormat.setOutputPath(job, new Path("hdfs://z4knight1:9000/website_date_order"));

		
		if (job.waitForCompletion(true)) {
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int result = ToolRunner.run(new DateOrderRunner(), args);
		System.out.println(result);
	}
}
