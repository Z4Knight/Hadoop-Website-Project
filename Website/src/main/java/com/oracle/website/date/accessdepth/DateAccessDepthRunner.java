package com.oracle.website.date.accessdepth;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.oracle.website.date.dimention.DateAccessDepthDimention;
import com.oracle.website.date.dimention.DateRegionDimention;
import com.oracle.website.date.region.format.MySqlRegionFormat;
import com.oracle.website.date.region.mapper.DateRegionMapper;
import com.oracle.website.date.region.reducer.DateRegionReducer;
import com.oracle.website.date.region.runner.DateRegionRunner;

public class DateAccessDepthRunner implements Tool{
	private Configuration conf;

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public Configuration getConf() {
		return conf;
	}

	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(conf);
		job.setJarByClass(DateAccessDepthRunner.class);
		
		job.setMapperClass(DateAccessDepthMapper.class);
		job.setMapOutputKeyClass(DateAccessDepthDimention.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(DateAccessDepthDimention.class);
		job.setReducerClass(DateAccessDepthReducer.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setOutputFormatClass(MySqlAccessDepthFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://z4knight1:9000/website/part-r-00000"));
//		FileOutputFormat.setOutputPath(job, new Path("hdfs://z4knight1:9000/website_date_accessdepth"));

		
		if (job.waitForCompletion(true)) {
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int result = ToolRunner.run(new DateAccessDepthRunner(), args);
		System.out.println(result);
	}
	
}
