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
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.oracle.website.date.browser.dao.BaseDao;
import com.oracle.website.date.browser.dao.BrowserPvDao;
import com.oracle.website.date.browser.dao.BrowserUserDao;
import com.oracle.website.date.collector.BaseCollector;
import com.oracle.website.date.connection.JdbcManager;
import com.oracle.website.date.dimention.DateBrowserDimention;
import com.oracle.website.date.dimention.DateBrowserPvDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;

public class MySqlBrowserPvFormat extends OutputFormat<DateBrowserPvDimention, Text>{

	@Override
	public void checkOutputSpecs(JobContext arg0) throws IOException, InterruptedException {
	}

	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext arg0) throws IOException, InterruptedException {
		return new FileOutputCommitter(FileOutputFormat.getOutputPath(arg0), arg0);
	}

	@Override
	public RecordWriter<DateBrowserPvDimention, Text> getRecordWriter(TaskAttemptContext context)
			throws IOException, InterruptedException {
		
		BrowserPvDao dao = new BrowserPvDao();
		return new MySqlRecordWriter(dao);
	}

	class MySqlRecordWriter extends RecordWriter<DateBrowserPvDimention, Text> {
		BrowserPvDao dao;
		HashMap<String, Integer> pvMaps = new HashMap<String, Integer>();
		HashMap<String, Integer> udMaps = new HashMap<String, Integer>();
		public MySqlRecordWriter(BrowserPvDao dao) {
			this.dao = dao;
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			for (String key : pvMaps.keySet()) {
				String[] keys = key.split("=");
				String date = keys[0];
				String browserInfo = keys[1];
				int pv = pvMaps.get(key);
				int ud = udMaps.get(key);
				float avgpv =(float)pv /(float) ud;
				dao.addPv(date, browserInfo, Integer.toString(pv), Float.toString(avgpv));
			}
		}

		@Override
		public void write(DateBrowserPvDimention key, Text value) throws IOException, InterruptedException {
		
			String date = key.getDate();
			String browserInfo = key.getBrowserInfo();
			if (key.getType().equals("pv")) {
				int pv = Integer.parseInt(value.toString());
				pvMaps.put(date + "=" + browserInfo, pv);
			}
			if (key.getType().equals("ud")) {
				int ud = Integer.parseInt(value.toString());
				udMaps.put(date + "=" + browserInfo, ud);
			}
		}
		
	}
	
}
