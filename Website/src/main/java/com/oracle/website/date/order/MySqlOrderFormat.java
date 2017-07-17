package com.oracle.website.date.order;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.oracle.pv.utils.DataTransUtils;
import com.oracle.website.date.accessdepth.AccessDepthDao;
import com.oracle.website.date.dimention.DateOrderDimention;
import com.oracle.website.date.dimention.OrderValuesCount;
import com.oracle.website.date.user.constants.WebsiteConstants;

public class MySqlOrderFormat extends OutputFormat<DateOrderDimention, OrderValuesCount>{

	@Override
	public void checkOutputSpecs(JobContext arg0) throws IOException, InterruptedException {
		
	}

	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext arg0) throws IOException, InterruptedException {
		return new FileOutputCommitter(FileOutputFormat.getOutputPath(arg0), arg0);
	}

	@Override
	public RecordWriter<DateOrderDimention, OrderValuesCount> getRecordWriter(TaskAttemptContext context)
			throws IOException, InterruptedException {
		OrderDao dao = new OrderDao();
		return new MySqlRecordWriter(dao);
	}

	
	class MySqlRecordWriter extends RecordWriter<DateOrderDimention, OrderValuesCount> {

		OrderDao dao;
		double sucessAmounts = 0;
		double refundAmoutns = 0;
		double orderAmounts = 0;
		HashMap<String, Integer> numberMaps = new HashMap<>();
		HashMap<String, Double> amountMaps = new HashMap<>();
		HashMap<String, Double> amountTotalMaps = new HashMap<>();
		
		public MySqlRecordWriter(OrderDao dao) {
			this.dao = dao;
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			String date = "";
			String orderType = "";
			String orderNumbers = "";
			String orderAmounts = "";
			String totalAmounts = "";
			for (String key : amountMaps.keySet()) {
				String[] keys = key.split("=");
				date = keys[0];
				orderType = keys[1];
				orderNumbers = Integer.toString(numberMaps.get(key));
				orderAmounts = DataTransUtils.transAmount(amountMaps.get(key));
				totalAmounts = DataTransUtils.transAmount(amountTotalMaps.get(key));
//				System.out.println(key + " n=" + orderNumbers + " a=" + orderAmounts + " ta=" + totalAmounts);
				dao.addVal(date, orderType, orderNumbers, orderAmounts, totalAmounts);
			}
		}

		@Override
		public void write(DateOrderDimention key, OrderValuesCount value) throws IOException, InterruptedException {
			String date = key.getDate();
			int type = key.getType();
			String flag = "";
			int numbers = value.getOrderNumbers();
			double amounts = value.getOrderAmounts();
			if (type == WebsiteConstants.ORDER_E_CS) {
				flag = date + "=" + WebsiteConstants.ORDER_E_CS_STR;
				numberMaps.put(flag, numbers);
				amountMaps.put(flag, amounts);
				sucessAmounts += amounts;
				amountTotalMaps.put(flag, sucessAmounts);
				
			}
			if (type == WebsiteConstants.ORDER_E_CRT) {
				flag = date + "=" + WebsiteConstants.ORDER_E_CRT_STR;
				numberMaps.put(flag, numbers);
				amountMaps.put(flag, amounts);
				refundAmoutns += amounts;
				amountTotalMaps.put(flag, refundAmoutns);
				
			}
			if (type == WebsiteConstants.ORDER_ONUMS) {
				flag = date + "=" + WebsiteConstants.ORDER_ONUMS_STR;
				numberMaps.put(flag, numbers);
				amountMaps.put(flag, amounts);
				orderAmounts += amounts;
				amountTotalMaps.put(flag, orderAmounts);
			}
		}
	}

}
