package com.oracle.website.date.user.runner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.oracle.website.date.dimention.DateUserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;
import com.oracle.website.date.member.mapper.DateMemberMapperCount;
import com.oracle.website.date.member.reducer.DateMemberReducerCount;
import com.oracle.website.date.user.format.MySqlUserFormat;
import com.oracle.website.date.user.mapper.DateUserMapperCount;
import com.oracle.website.date.user.reducer.DateUserReducerCount;

public class DateUserCountRunner implements Tool {
	private Configuration conf;

	public void setConf(Configuration conf) {
		this.conf = conf;
		conf.addResource("jdbc_cfg.xml");
		conf.addResource("sql_collector.xml");
		conf.addResource("sql_mapper.xml");
	}

	public Configuration getConf() {
		return conf;
	}

	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(conf);
		job.setJarByClass(DateUserCountRunner.class);
		
//		job.setMapperClass(DateUserMapperCount.class);
		job.setMapOutputKeyClass(DateUserDimention.class);
		job.setMapperClass(DateMemberMapperCount.class);
		job.setMapOutputValueClass(VisitorsCountValue.class);
		
//		job.setReducerClass(DateUserReducerCount.class);
		job.setOutputKeyClass(DateUserDimention.class);
		job.setReducerClass(DateMemberReducerCount.class);
		job.setOutputValueClass(VisitorsCountValue.class);
		
		job.setOutputFormatClass(MySqlUserFormat.class);
//		FileInputFormat.setInputPaths(job, new Path("hdfs://z4knight1:9000/website_date_user/part-r-00000"));
//		FileOutputFormat.setOutputPath(job, new Path("hdfs://z4knight1:9000/website_date_user_count"));
		FileInputFormat.setInputPaths(job, new Path("hdfs://z4knight1:9000/website_date_member/part-r-00000"));
//		FileOutputFormat.setOutputPath(job, new Path("hdfs://z4knight1:9000/website_date_member_count"));
		
		if (job.waitForCompletion(true)) {
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int result = ToolRunner.run(new DateUserCountRunner(), args);
		System.out.println(result);
	}
	
}
