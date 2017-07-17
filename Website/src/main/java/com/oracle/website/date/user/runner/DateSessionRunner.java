package com.oracle.website.date.user.runner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.oracle.website.date.dimention.DateSessionDimention;
import com.oracle.website.date.session.format.MySqlSessionFormat;
import com.oracle.website.date.session.mapper.DateSessionMapper;
import com.oracle.website.date.session.reduce.DateSessionReducer;
import com.oracle.website.date.user.format.MySqlUserFormat;

public class DateSessionRunner implements Tool {

	private Configuration conf;

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public Configuration getConf() {
		return conf;
	}

	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(conf);
		job.setJarByClass(DateSessionRunner.class);
		
		job.setMapperClass(DateSessionMapper.class);
		job.setMapOutputKeyClass(DateSessionDimention.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(DateSessionDimention.class);
		job.setReducerClass(DateSessionReducer.class);
		job.setOutputValueClass(Text.class);
		
		job.setOutputFormatClass(MySqlSessionFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://z4knight1:9000/website/part-r-00000"));
//		FileOutputFormat.setOutputPath(job, new Path("hdfs://z4knight1:9000/website_date_session"));

		
		if (job.waitForCompletion(true)) {
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int result = ToolRunner.run(new DateSessionRunner(), args);
		System.out.println(result);
	}
	
}
