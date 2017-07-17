package com.oracle.website.clear;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ClearTest {
	public static void main(String[] args) {
		Configuration conf = new Configuration();
		
		try {
			Job job = Job.getInstance(conf);
			job.setJarByClass(ClearTest.class);
			
			job.setMapperClass(ClearWebsite.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(NullWritable.class);
			
			FileInputFormat.setInputPaths(job, new Path("hdfs://z4knight1:9000/website_log.txt"));
			FileOutputFormat.setOutputPath(job, new Path("hdfs://z4knight1:9000/website"));
			
			boolean bool =job.waitForCompletion(true);
			System.out.println(bool);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
	}
}
