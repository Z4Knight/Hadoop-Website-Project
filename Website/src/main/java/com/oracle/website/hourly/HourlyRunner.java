package com.oracle.website.hourly;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class HourlyRunner implements Tool {

	private Configuration conf;

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public Configuration getConf() {
		return conf;
	}

	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(conf);
		job.setJarByClass(HourlyRunner.class);
		
		job.setMapperClass(HourlyMapper.class);
		job.setMapOutputKeyClass(HourlyDimention.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(HourlyDimention.class);
		job.setReducerClass(HourlyReducer.class);
		job.setOutputValueClass(Text.class);
		
		job.setOutputFormatClass(MySqlHourlyFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://z4knight1:9000/website/part-r-00000"));
//		FileOutputFormat.setOutputPath(job, new Path("hdfs://z4knight1:9000/website_hourly"));

		
		if (job.waitForCompletion(true)) {
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int result = ToolRunner.run(new HourlyRunner(), args);
		System.out.println(result);
	}
	
}
