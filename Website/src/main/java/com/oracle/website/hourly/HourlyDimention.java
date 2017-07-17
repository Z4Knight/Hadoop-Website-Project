package com.oracle.website.hourly;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;


public class HourlyDimention implements WritableComparable<HourlyDimention>{

	private String time;
	
	private String type;
	
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void write(DataOutput out) throws IOException {
		out.writeUTF(time);
		out.writeUTF(type);
	}

	public void readFields(DataInput in) throws IOException {
		time = in.readUTF();
		type = in.readUTF();
	}

	public int compareTo(HourlyDimention o) {
		if (this == o) {
			return 0;
		}
		
		int tmp = this.time.compareTo(o.time);
		if (tmp != 0) {
			return tmp;
		}
		
		tmp = this.type.compareTo(o.type);
		if (tmp != 0) {
			return tmp;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		int result = 1;
		int prime = 100;
		result = (result * prime) + time == null ? 0 : time.hashCode();
		result = (result * prime) + type == null ? 0 : type.hashCode();
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
		
		HourlyDimention hourlyDimention = (HourlyDimention) obj;
		if (this.time == null) {
			if (hourlyDimention.getTime() != null) {
				return false;
			}
		} else if (this.time != null) {
			if (hourlyDimention.getTime() == null) {
				return false;
			}
		} else if (!this.time.equals(hourlyDimention.getTime())) {
			return false;
		}
		
		if (this.type == null) {
			if (hourlyDimention.getType() != null) {
				return false;
			}
		} else if (this.type != null) {
			if (hourlyDimention.getType() == null) {
				return false;
			}
		} else if (!this.type.equals(hourlyDimention.getType())) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return time + "\t" + type;
	}
}
