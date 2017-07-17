package com.oracle.website.hourly;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.AbstractDocument.Content;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.server.namenode.status_jsp;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.oracle.website.date.connection.JdbcManager;
import com.oracle.website.date.connection.JdbcManager1;
import com.oracle.website.date.dimention.DateSessionDimention;
import com.oracle.website.date.session.dao.SessionDao;
import com.oracle.website.date.user.constants.WebsiteConstants;
import com.oracle.website.date.user.utils.DateUtils;

public class MySqlHourlyFormat extends OutputFormat<HourlyDimention, Text>{

	@Override
	public void checkOutputSpecs(JobContext arg0) throws IOException, InterruptedException {
		
	}

	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext arg0) throws IOException, InterruptedException {
		return new FileOutputCommitter(FileOutputFormat.getOutputPath(arg0), arg0);
	}

	@Override
	public RecordWriter<HourlyDimention, Text> getRecordWriter(TaskAttemptContext context)
			throws IOException, InterruptedException {
		HourlyDao dao = new HourlyDao();
		return new MySqlRecordWriter(dao);
	}

	
	class MySqlRecordWriter extends RecordWriter<HourlyDimention, Text> {

		HourlyDao dao;

		public MySqlRecordWriter(HourlyDao dao) {
			this.dao = dao;
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			
		}

		@Override
		public void write(HourlyDimention key, Text value) throws IOException, InterruptedException {
			String time = key.getTime();
			String type = key.getType();
			String data = value.toString();
			if (type.equals(WebsiteConstants.HOURLY_SESSION_LENGTH)) {
				data = DateUtils.getTime(data);
			}
//			System.out.println(time + "=" +type + "=" + data);
			dao.addVal(time, type, data);
		}
	}
	
}
