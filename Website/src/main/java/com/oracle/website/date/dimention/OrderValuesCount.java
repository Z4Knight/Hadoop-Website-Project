package com.oracle.website.date.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class OrderValuesCount implements Writable{

	private int orderNumbers;
	private double orderAmounts;
	
	
	public OrderValuesCount(int orderNumbers, double orderAmounts) {
		super();
		this.orderNumbers = orderNumbers;
		this.orderAmounts = orderAmounts;
	}

	public OrderValuesCount() {
		
	}

	public int getOrderNumbers() {
		return orderNumbers;
	}

	public void setOrderNumbers(int orderNumbers) {
		this.orderNumbers = orderNumbers;
	}

	public double getOrderAmounts() {
		return orderAmounts;
	}

	public void setOrderAmounts(double orderAmounts) {
		this.orderAmounts = orderAmounts;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(orderNumbers);
		out.writeDouble(orderAmounts);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		orderNumbers = in.readInt();
		orderAmounts = in.readDouble();
	}
	
	@Override
	public String toString() {
		return orderNumbers + "\t" + orderAmounts;
	}

	
}
