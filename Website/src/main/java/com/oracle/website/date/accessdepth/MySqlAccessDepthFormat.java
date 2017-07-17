package com.oracle.website.date.accessdepth;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.oracle.pv.utils.DataTransUtils;
import com.oracle.website.date.dimention.DateAccessDepthDimention;
import com.oracle.website.date.dimention.DateRegionDimention;
import com.oracle.website.date.region.dao.RegionDao;

public class MySqlAccessDepthFormat extends OutputFormat<DateAccessDepthDimention, IntWritable>{

	@Override
	public void checkOutputSpecs(JobContext arg0) throws IOException, InterruptedException {
		
	}

	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext arg0) throws IOException, InterruptedException {
		return new FileOutputCommitter(FileOutputFormat.getOutputPath(arg0), arg0);
	}

	@Override
	public RecordWriter<DateAccessDepthDimention, IntWritable> getRecordWriter(TaskAttemptContext context)
			throws IOException, InterruptedException {
		AccessDepthDao dao = new AccessDepthDao();
		return new MySqlRecordWriter(dao);
	}

	
	class MySqlRecordWriter extends RecordWriter<DateAccessDepthDimention, IntWritable> {

		AccessDepthDao dao;
		
		public MySqlRecordWriter(AccessDepthDao dao) {
			this.dao = dao;
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			
		}

		@Override
		public void write(DateAccessDepthDimention key, IntWritable value) throws IOException, InterruptedException {
			String date = key.getDate();
			String userId = key.getUserId();
			String accessDepth = Integer.toString(value.get());
			dao.addVal(date, userId, accessDepth);
			
		}
	}

}
