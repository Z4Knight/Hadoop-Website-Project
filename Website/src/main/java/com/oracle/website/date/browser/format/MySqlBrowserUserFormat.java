package com.oracle.website.date.browser.format;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.oracle.website.date.browser.dao.BaseDao;
import com.oracle.website.date.browser.dao.BrowserUserDao;
import com.oracle.website.date.collector.BaseCollector;
import com.oracle.website.date.connection.JdbcManager;
import com.oracle.website.date.dimention.DateBrowserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;

public class MySqlBrowserUserFormat extends OutputFormat<DateBrowserDimention, VisitorsCountValue>{

	@Override
	public void checkOutputSpecs(JobContext arg0) throws IOException, InterruptedException {
	}

	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext arg0) throws IOException, InterruptedException {
		return new FileOutputCommitter(FileOutputFormat.getOutputPath(arg0), arg0);
	}

	@Override
	public RecordWriter<DateBrowserDimention, VisitorsCountValue> getRecordWriter(TaskAttemptContext context)
			throws IOException, InterruptedException {
		
		BrowserUserDao dao = new BrowserUserDao();
		return new MySqlRecordWriter(dao);
	}

	class MySqlRecordWriter extends RecordWriter<DateBrowserDimention, VisitorsCountValue> {

		BaseDao dao;
		int totalVal = 0;
		int total = 0;
		HashSet<String> keySet = new HashSet<String>();
		TreeMap<String, Integer> browserNewValsMaps = new TreeMap<String, Integer>();
		TreeMap<String, Integer> browserTodayValsMaps = new TreeMap<String, Integer>();
		TreeMap<String, Integer> browserTotalValsMaps = new TreeMap<String, Integer>();
		public MySqlRecordWriter(BaseDao dao) {
			this.dao = dao;
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			ArrayList<String> keys = new ArrayList<String>(keySet);
			Collections.sort(keys);
			for (String key : keys) {
				String[] vals = key.split("=");
				String date = vals[0];
				String browserInfo = vals[1];
				int newVal = browserNewValsMaps.get(key) == null ? 0 : browserNewValsMaps.get(key);
				int todayVal = browserTodayValsMaps.get(key) ==  null ? 0 : browserTodayValsMaps.get(key);
				total = newVal + todayVal ;
				if (browserTotalValsMaps.get(browserInfo) == null) {
					browserTotalValsMaps.put(browserInfo, total);
				}else {
					total += browserTotalValsMaps.get(browserInfo);
					browserTotalValsMaps.put(browserInfo, total);
				}
				totalVal = browserTotalValsMaps.get(browserInfo) == null ? 0 : browserTotalValsMaps.get(browserInfo);
				dao.addVal(date, browserInfo, Integer.toString(newVal), Integer.toString(todayVal), Integer.toString(totalVal));
			}
		}

		@Override
		public void write(DateBrowserDimention key, VisitorsCountValue value) throws IOException, InterruptedException {
			keySet.add(key.getDateUserDimention().getDate() + "=" + key.getBrowserInfo());
			if (key.getDateUserDimention().getEvent().equals("")) {
				browserTodayValsMaps.put(key.getDateUserDimention().getDate() + "=" + key.getBrowserInfo(), value.getTodayVal());
			}
			if (!key.getDateUserDimention().getEvent().equals("")) {
				browserNewValsMaps.put(key.getDateUserDimention().getDate() + "=" + key.getBrowserInfo(), value.getNewVal());
			}
			
		}
		
	}
	
}
