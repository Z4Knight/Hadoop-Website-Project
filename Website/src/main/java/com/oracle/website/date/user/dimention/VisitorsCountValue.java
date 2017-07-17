package com.oracle.website.date.user.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class VisitorsCountValue implements Writable{
	
	private String uId;
	private int todayVal;
	private int totalVal;
	private int newVal;

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public int getNewVal() {
		return newVal;
	}

	public void setNewVal(int newVal) {
		this.newVal = newVal;
	}

	public int getTodayVal() {
		return todayVal;
	}

	public void setTodayVal(int todayVal) {
		this.todayVal = todayVal;
	}

	public int getTotalVal() {
		return totalVal;
	}

	public void setTotalVal(int totalVal) {
		this.totalVal = totalVal;
	}

	public VisitorsCountValue() {
		
	}
	
	
	public VisitorsCountValue(String uId,int newVal, int todayVal) {
		super();
		this.uId = uId;
		this.newVal = newVal;
		this.todayVal = todayVal;
		this.totalVal = todayVal + newVal;
	}

	public void write(DataOutput out) throws IOException {
		out.writeUTF(uId);
		out.writeInt(newVal);
		out.writeInt(todayVal);
		out.writeInt(totalVal);
	}

	public void readFields(DataInput in) throws IOException {
		uId = in.readUTF();
		newVal = in.readInt();
		todayVal = in.readInt();
		totalVal = in.readInt();
	}

	
	@Override
	public String toString() {
		return newVal + "\t" + todayVal + "\t" + totalVal;
	}
}
