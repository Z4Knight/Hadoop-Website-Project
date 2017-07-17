package com.oracle.website.date.region.format;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.oracle.pv.utils.DataTransUtils;
import com.oracle.website.date.dimention.DateRegionDimention;
import com.oracle.website.date.dimention.DateSessionDimention;
import com.oracle.website.date.region.dao.RegionDao;
import com.oracle.website.date.session.dao.SessionDao;

import com.oracle.website.date.user.utils.DateUtils;

public class MySqlRegionFormat extends OutputFormat<DateRegionDimention, IntWritable>{
	@Override
	public void checkOutputSpecs(JobContext arg0) throws IOException, InterruptedException {
		
	}

	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext arg0) throws IOException, InterruptedException {
		return new FileOutputCommitter(FileOutputFormat.getOutputPath(arg0), arg0);
	}

	@Override
	public RecordWriter<DateRegionDimention, IntWritable> getRecordWriter(TaskAttemptContext context)
			throws IOException, InterruptedException {
		RegionDao dao = new RegionDao();
		return new MySqlRecordWriter(dao);
	}

	
	class MySqlRecordWriter extends RecordWriter<DateRegionDimention, IntWritable> {

		RegionDao dao;
		HashMap<String, Integer> visitorMaps = new HashMap<>();
		HashMap<String, Integer> sessionMaps = new HashMap<>();
		HashMap<String, Integer> pvMaps = new HashMap<>();
		
		public MySqlRecordWriter(RegionDao dao) {
			this.dao = dao;
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			for (String key : visitorMaps.keySet()) {
				String[] keys = key.split("=");
				String date = keys[0];
				String ip = keys[1];
				String visitors = Integer.toString(visitorMaps.get(key));
				int sessionNums = sessionMaps.get(key);
				int pvNums = pvMaps.get(key) == null ? 0 : pvMaps.get(key);
				double jump = (double)pvNums / (double)sessionNums;
//				System.out.println(key);
//				System.out.println(visitors + "=" + sessionNums + "=" + pvNums + "=" + jump);
				String jumpRation = DataTransUtils.pointToPercent(jump);
				dao.addVal(date, ip, visitors, Integer.toString(sessionNums), Integer.toString(pvNums), jumpRation);
			}
		}

		@Override
		public void write(DateRegionDimention key, IntWritable value) throws IOException, InterruptedException {
			String type = key.getType();
			String date = key.getDate();
			String ip = key.getIp();
			String flag = date + "=" + ip;
			int val = value.get();
			if (type.equals("vi")) {
				visitorMaps.put(flag, val);
			}
			if (type.equals("usd")) {
				sessionMaps.put(flag, val);
			}
			if (type.equals("pv")) {
				pvMaps.put(flag, val);
			}
		}
	}
}
