package com.oracle.website.date.region.runner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.oracle.website.date.dimention.DateRegionDimention;
import com.oracle.website.date.dimention.DateSessionDimention;
import com.oracle.website.date.region.format.MySqlRegionFormat;
import com.oracle.website.date.region.mapper.DateRegionMapper;
import com.oracle.website.date.region.reducer.DateRegionReducer;
import com.oracle.website.date.session.format.MySqlSessionFormat;
import com.oracle.website.date.session.mapper.DateSessionMapper;
import com.oracle.website.date.session.reduce.DateSessionReducer;
import com.oracle.website.date.user.runner.DateSessionRunner;

public class DateRegionRunner implements Tool {

	private Configuration conf;

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public Configuration getConf() {
		return conf;
	}

	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(conf);
		job.setJarByClass(DateRegionRunner.class);
		
		job.setMapperClass(DateRegionMapper.class);
		job.setMapOutputKeyClass(DateRegionDimention.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(DateRegionDimention.class);
		job.setReducerClass(DateRegionReducer.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setOutputFormatClass(MySqlRegionFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://z4knight1:9000/website/part-r-00000"));
//		FileOutputFormat.setOutputPath(job, new Path("hdfs://z4knight1:9000/website_date_region"));

		
		if (job.waitForCompletion(true)) {
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int result = ToolRunner.run(new DateRegionRunner(), args);
		System.out.println(result);
	}
	
}

