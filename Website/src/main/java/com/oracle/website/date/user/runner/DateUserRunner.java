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
import com.oracle.website.date.member.mapper.DateMemberMapper;
import com.oracle.website.date.member.reducer.DateMemberReduer;
import com.oracle.website.date.user.mapper.DateUserMapper;
import com.oracle.website.date.user.reducer.DateUserReducer;

public class DateUserRunner implements Tool{
	private Configuration conf;

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public Configuration getConf() {
		return conf;
	}

	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(conf);
		job.setJarByClass(DateUserRunner.class);
		
//		job.setMapperClass(DateUserMapper.class);
		job.setMapOutputKeyClass(DateUserDimention.class);
		job.setMapperClass(DateMemberMapper.class);
		job.setMapOutputValueClass(VisitorsCountValue.class);
		
//		job.setReducerClass(DateUserReducer.class);
		job.setOutputKeyClass(DateUserDimention.class);
		job.setReducerClass(DateMemberReduer.class);
		job.setOutputValueClass(VisitorsCountValue.class);
		
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://z4knight1:9000/website/part-r-00000"));
//		FileOutputFormat.setOutputPath(job, new Path("hdfs://z4knight1:9000/website_date_user"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://z4knight1:9000/website_date_member"));
		if (job.waitForCompletion(true)) {
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int result = ToolRunner.run(new DateUserRunner(), args);
		System.out.println(result);
	}
	
}
