package com.oracle.website.date.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class DateAccessDepthDimention implements WritableComparable<DateAccessDepthDimention>{

	private String date;
	private String userId;
	
	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void write(DataOutput out) throws IOException {
		out.writeUTF(date);
		out.writeUTF(userId);
	}

	public void readFields(DataInput in) throws IOException {
		date = in.readUTF();
		userId = in.readUTF();
	}

	public int compareTo(DateAccessDepthDimention o) {
		if (this == o) {
			return 0;
		}
		
		int tmp = this.date.compareTo(o.date);
		if (tmp != 0) {
			return tmp;
		}
		
		tmp = this.userId.compareTo(o.userId);
		if (tmp != 0) {
			return tmp;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		int result = 1;
		int prime = 100;
		result = (result * prime) + date == null ? 0 : date.hashCode();
		result = (result * prime) + userId == null ? 0 : userId.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		DateAccessDepthDimention dateAccessDepthDimention = (DateAccessDepthDimention) obj;
		if (this.date == null) {
			if (dateAccessDepthDimention.getDate() != null) {
				return false;
			}
		} else if (this.date != null) {
			if (dateAccessDepthDimention.getDate() == null) {
				return false;
			}
		} else if (!this.date.equals(dateAccessDepthDimention.getDate())) {
			return false;
		}
		
		if (this.userId == null) {
			if (dateAccessDepthDimention.getUserId() != null) {
				return false;
			}
		} else if (this.userId != null) {
			if (dateAccessDepthDimention.getUserId() == null) {
				return false;
			}
		} else if (!this.userId.equals(dateAccessDepthDimention.getUserId())) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return date + "\t" + userId;
	}
	
}
