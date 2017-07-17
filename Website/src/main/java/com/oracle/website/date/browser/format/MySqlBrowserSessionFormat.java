package com.oracle.website.date.browser.format;

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

import com.oracle.website.date.browser.dao.BrowserSessionDao;
import com.oracle.website.date.connection.JdbcManager;
import com.oracle.website.date.connection.JdbcManager1;
import com.oracle.website.date.dimention.DateBrowserSessionDimention;
import com.oracle.website.date.dimention.DateSessionDimention;
import com.oracle.website.date.session.dao.SessionDao;
import com.oracle.website.date.user.utils.DateUtils;

public class MySqlBrowserSessionFormat extends OutputFormat<DateBrowserSessionDimention, Text>{

	@Override
	public void checkOutputSpecs(JobContext arg0) throws IOException, InterruptedException {
		
	}

	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext arg0) throws IOException, InterruptedException {
		return new FileOutputCommitter(FileOutputFormat.getOutputPath(arg0), arg0);
	}

	@Override
	public RecordWriter<DateBrowserSessionDimention, Text> getRecordWriter(TaskAttemptContext context)
			throws IOException, InterruptedException {
		BrowserSessionDao dao = new BrowserSessionDao();
		return new MySqlRecordWriter(dao);
	}

	
	class MySqlRecordWriter extends RecordWriter<DateBrowserSessionDimention, Text> {
		BrowserSessionDao dao;
		HashMap<String, Integer> sessionNumberMaps = new HashMap<String, Integer>();
		HashMap<String, Long> sessionDurationMaps = new HashMap<String, Long>();
		HashMap<String, Long> sessionSumMaps = new HashMap<String, Long>();
		public MySqlRecordWriter(BrowserSessionDao dao) {
			this.dao = dao;
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			for (String key : sessionDurationMaps.keySet()) {
				long sessionSum = sessionDurationMaps.get(key);
//				System.out.println(key + "=sessionSum=" + sessionSum);
				String[] keys = key.split("=");
				String date = keys[0];
				String browserInfo = keys[1];
				long sNumber = sessionNumberMaps.get(key);
				long avgSessionTime = sessionSum / sNumber;
				String ssnumber = Long.toString(sNumber);
				String ssduration = DateUtils.getTime(Long.toString(sessionSum));
				String avgssduration = DateUtils.getTime(Long.toString(avgSessionTime));
				dao.addSession(date, browserInfo, ssnumber, ssduration, avgssduration);
			}
		}

		@Override
		public void write(DateBrowserSessionDimention key, Text value) throws IOException, InterruptedException {
			if (key.getDateSessionDimention().getSession().equals("")) {
				long sum = 0;
				sessionNumberMaps.put(key.getDateSessionDimention().getDate() +
						"=" + key.getBrowserInfo(), Integer.parseInt(value.toString()));				
				sessionSumMaps.put(key.getDateSessionDimention().getDate() + 
						"=" + key.getBrowserInfo(), sum);
				
			}
			if (!key.getDateSessionDimention().getSession().equals("")) {
				long ssduration = Long.parseLong(value.toString());
				if (sessionSumMaps.get(key.getDateSessionDimention().getDate() + 
						"=" + key.getBrowserInfo()) == 0) {
					sessionSumMaps.put(key.getDateSessionDimention().getDate() + 
						"=" + key.getBrowserInfo(), ssduration);
				} else if (sessionSumMaps.get(key.getDateSessionDimention().getDate() + 
						"=" + key.getBrowserInfo()) != 0) {
					ssduration += sessionSumMaps.get(key.getDateSessionDimention().getDate() + 
						"=" + key.getBrowserInfo());
					sessionSumMaps.put(key.getDateSessionDimention().getDate() + 
						"=" + key.getBrowserInfo(), ssduration);
					
				}
				sessionDurationMaps.put(key.getDateSessionDimention().getDate() +
						"=" + key.getBrowserInfo(), sessionSumMaps.get(key.getDateSessionDimention().getDate() +
						"=" + key.getBrowserInfo()));
//				System.out.println(key.getDateSessionDimention().getDate() + "=" + key.getBrowserInfo()
//				 + "=" + sessionSumMaps.get(key.getDateSessionDimention().getDate() +
//							"=" + key.getBrowserInfo()));
			}
		}

		
		
	}
	
}
