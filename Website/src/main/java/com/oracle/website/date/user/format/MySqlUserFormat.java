package com.oracle.website.date.user.format;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import com.oracle.website.date.collector.BaseCollector;
import com.oracle.website.date.connection.JdbcManager;
import com.oracle.website.date.dimention.DateUserDimention;
import com.oracle.website.date.dimention.VisitorsCountValue;

public class MySqlUserFormat extends OutputFormat<DateUserDimention, VisitorsCountValue> {

	@Override
	public void checkOutputSpecs(JobContext arg0) throws IOException, InterruptedException {
	}

	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext arg0) throws IOException, InterruptedException {
		return new FileOutputCommitter(FileOutputFormat.getOutputPath(arg0), arg0);
	}

	@Override
	public RecordWriter<DateUserDimention, VisitorsCountValue> getRecordWriter(TaskAttemptContext context)
			throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		Connection con = JdbcManager.getConnection(conf);
		
		return new MySqlRecordWriter(conf, con);
	}

	
	class MySqlRecordWriter extends RecordWriter<DateUserDimention, VisitorsCountValue> {
		
		Configuration conf;
		Connection con;
		int count = 0;
		HashMap<String, PreparedStatement> psMaps = new HashMap<String, PreparedStatement>();
		public MySqlRecordWriter(Configuration conf, Connection con) {
			this.conf = conf;
			this.con = con;
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			try {
				for (String psKey : psMaps.keySet()) {
					PreparedStatement ps = psMaps.get(psKey);
					ps.executeBatch();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				try {
					for (String psKey : psMaps.keySet()) {
						PreparedStatement ps = psMaps.get(psKey);
						ps.close();
					}
					con.commit();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				} finally {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		@Override
		public void write(DateUserDimention key, VisitorsCountValue value) throws IOException, InterruptedException {
			PreparedStatement ps = psMaps.get("website" + key.getType());
			if (ps == null) {
				try {
					ps = con.prepareStatement(conf.get("website_" + key.getType()));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				psMaps.put("website" + key.getType(), ps);
			}
			count++;
			
			String classPath = conf.get("collector_" + key.getType());
			
			try {
				BaseCollector baseCollector = (BaseCollector) Class.forName(classPath).newInstance();
				baseCollector.setPreparstatement(ps, key, value);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			if (count % 10 == 0) {
				try {
					ps.executeBatch();
					con.commit();
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				count = 0;
			}
		}
		
	}
}
