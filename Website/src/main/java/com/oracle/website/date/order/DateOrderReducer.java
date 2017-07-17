package com.oracle.website.date.order;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Reducer;

import com.oracle.website.date.dimention.DateOrderDimention;
import com.oracle.website.date.dimention.OrderValuesCount;
import com.oracle.website.date.user.constants.WebsiteConstants;

public class DateOrderReducer extends Reducer<DateOrderDimention, OrderValuesCount, DateOrderDimention, OrderValuesCount>{

	@Override
	protected void reduce(DateOrderDimention key, Iterable<OrderValuesCount> values,
			Reducer<DateOrderDimention, OrderValuesCount, DateOrderDimention, OrderValuesCount>.Context context)
			throws IOException, InterruptedException {
		int orderNumbers = 0;
		double orderAmounts = 0;
		if (key.getType() == WebsiteConstants.ORDER_E_CS) {
			for (OrderValuesCount orderValuesCount : values) {
				orderNumbers += orderValuesCount.getOrderNumbers();
				orderAmounts += orderValuesCount.getOrderAmounts();
			}
			context.write(key, new OrderValuesCount(orderNumbers, orderAmounts));
		}
		if (key.getType() == WebsiteConstants.ORDER_E_CRT) {
			for (OrderValuesCount orderValuesCount : values) {
				orderNumbers += orderValuesCount.getOrderNumbers();
				orderAmounts += orderValuesCount.getOrderAmounts();
			}
			context.write(key, new OrderValuesCount(orderNumbers, orderAmounts));
		}
		if (key.getType() == WebsiteConstants.ORDER_ONUMS) {
			for (OrderValuesCount orderValuesCount : values) {
				orderNumbers += orderValuesCount.getOrderNumbers();
				orderAmounts += orderValuesCount.getOrderAmounts();
			}
			context.write(key, new OrderValuesCount(orderNumbers, orderAmounts));
		}
		
	}
}
