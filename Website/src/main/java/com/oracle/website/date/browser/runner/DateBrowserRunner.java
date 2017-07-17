package com.oracle.website.date.browser.runner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.oracle.website.date.browser.format.MySqlBrowserMemberFormat;
import com.oracle.website.date.browser.format.MySqlBrowserUserFormat;
import com.oracle.website.date.browser.mapper.DateBrowserMemberMapper;
import com.oracle.website.date.browser.mapper.DateBrowserUserMapper;
import com.oracle.website.date.browser.reducer.DateBrowserMemberReducer;
import com.oracle.website.date.browser.reducer.DateBrowserUserReducer;
import com.oracle.website.date.dimention.DateBrowserDimention;
import com.oracle.website.date.dimention.DateUserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;
import com.oracle.website.date.member.mapper.DateMemberMapper;
import com.oracle.website.date.member.reducer.DateMemberReduer;
import com.oracle.website.date.user.runner.DateUserRunner;

public class DateBrowserRunner implements Tool{
	private Configuration conf;

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public Configuration getConf() {
		return conf;
	}

	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(conf);
		job.setJarByClass(DateBrowserRunner.class);
		

		job.setMapOutputKeyClass(DateBrowserDimention.class);
//		job.setMapperClass(DateBrowserUserMapper.class);
		job.setMapperClass(DateBrowserMemberMapper.class);
		job.setMapOutputValueClass(VisitorsCountValue.class);

		job.setOutputKeyClass(DateBrowserDimention.class);
//		job.setReducerClass(DateBrowserUserReducer.class);
		job.setReducerClass(DateBrowserMemberReducer.class);
		job.setOutputValueClass(VisitorsCountValue.class);
		
//		job.setOutputFormatClass(MySqlBrowserUserFormat.class);
		job.setOutputFormatClass(MySqlBrowserMemberFormat.class);
		FileInputFormat.setInputPaths(job, new Path("hdfs://z4knight1:9000/website/part-r-00000"));
//		FileOutputFormat.setOutputPath(job, new Path("hdfs://z4knight1:9000/website_date_browser_user"));

		if (job.waitForCompletion(true)) {
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int result = ToolRunner.run(new DateBrowserRunner(), args);
		System.out.println(result);
	}
	
}

