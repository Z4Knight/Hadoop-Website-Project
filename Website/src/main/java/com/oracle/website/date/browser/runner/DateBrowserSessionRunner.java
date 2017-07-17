package com.oracle.website.date.browser.runner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.oracle.website.date.browser.format.MySqlBrowserSessionFormat;
import com.oracle.website.date.browser.mapper.DateBrowserSessionMapper;
import com.oracle.website.date.browser.reducer.DateBrowserSessionReducer;
import com.oracle.website.date.dimention.DateBrowserSessionDimention;
import com.oracle.website.date.dimention.DateSessionDimention;
import com.oracle.website.date.session.format.MySqlSessionFormat;
import com.oracle.website.date.session.mapper.DateSessionMapper;
import com.oracle.website.date.session.reduce.DateSessionReducer;
import com.oracle.website.date.user.format.MySqlUserFormat;

public class DateBrowserSessionRunner implements Tool {

	private Configuration conf;

	public void setConf(Configuration conf) {
		this.conf = conf;
//		conf.addResource("jdbc_cfg.xml");
//		conf.addResource("sql_collector.xml");
//		conf.addResource("sql_mapper.xml");
	}

	public Configuration getConf() {
		return conf;
	}

	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(conf);
		job.setJarByClass(DateBrowserSessionRunner.class);
		
		job.setMapperClass(DateBrowserSessionMapper.class);
		job.setMapOutputKeyClass(DateBrowserSessionDimention.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(DateBrowserSessionDimention.class);
		job.setReducerClass(DateBrowserSessionReducer.class);
		job.setOutputValueClass(Text.class);
		
		job.setOutputFormatClass(MySqlBrowserSessionFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://z4knight1:9000/website/part-r-00000"));
//		FileOutputFormat.setOutputPath(job, new Path("hdfs://z4knight1:9000/website_date_browser_session"));

		
		if (job.waitForCompletion(true)) {
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int result = ToolRunner.run(new DateBrowserSessionRunner(), args);
		System.out.println(result);
	}
	
}
