package com.oracle.website.date.session.format;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
import com.oracle.website.date.user.utils.DateUtils;

public class MySqlSessionFormat extends OutputFormat<DateSessionDimention, Text>{

	@Override
	public void checkOutputSpecs(JobContext arg0) throws IOException, InterruptedException {
		
	}

	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext arg0) throws IOException, InterruptedException {
		return new FileOutputCommitter(FileOutputFormat.getOutputPath(arg0), arg0);
	}

	@Override
	public RecordWriter<DateSessionDimention, Text> getRecordWriter(TaskAttemptContext context)
			throws IOException, InterruptedException {
		SessionDao dao = new SessionDao();
		return new MySqlRecordWriter(dao);
	}

	
	class MySqlRecordWriter extends RecordWriter<DateSessionDimention, Text> {

		SessionDao dao;
		Connection con;
		HashMap<String, Integer> sessionNumberMap = new HashMap<String, Integer>();
		HashMap<String, Long> sessionDurationMap = new HashMap<String, Long>();
		long sum;
		public MySqlRecordWriter(SessionDao dao) {
			this.dao = dao;
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			for (String key : sessionDurationMap.keySet()) {
				long sessionSum = sessionDurationMap.get(key);
//				System.out.println(key + "=sessionSum=" + sessionSum);
				long sNumber = sessionNumberMap.get(key);
				long avgSessionTime = sessionSum / sNumber;
				String ssnumber = Long.toString(sNumber);
				String ssduration = DateUtils.getTime(Long.toString(sessionSum));
				String avgssduration = DateUtils.getTime(Long.toString(avgSessionTime));
				dao.addSession(key, ssnumber, ssduration, avgssduration);
			}
		}

		@Override
		public void write(DateSessionDimention key, Text value) throws IOException, InterruptedException {
			if (key.getSession().equals("")) {
				sessionNumberMap.put(key.getDate(), Integer.parseInt(value.toString()));
				sum = 0;
			}
			if (!key.getSession().equals("")) {
				long ssduration = Long.parseLong(value.toString());
				sum += ssduration;
				sessionDurationMap.put(key.getDate(), sum);	
			}
		}

		
		
	}
	
}
