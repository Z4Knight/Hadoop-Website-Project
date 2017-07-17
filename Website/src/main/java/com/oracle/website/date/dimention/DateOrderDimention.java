package com.oracle.website.date.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;


public class DateOrderDimention implements WritableComparable<DateOrderDimention> {

	private String date;
	private String event;
	private Integer type;
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void write(DataOutput out) throws IOException {
		out.writeUTF(date);
		out.writeUTF(event);
		out.writeInt(type);
	}

	public void readFields(DataInput in) throws IOException {
		date = in.readUTF();
		event = in.readUTF();
		type = in.readInt();
	}

	public int compareTo(DateOrderDimention o) {
		if (this == o) {
			return 0;
		}
		int tmp = this.date.compareTo(o.date);
		if (tmp != 0) {
			return tmp;
		}
		
		tmp = this.event.compareTo(o.event);
		if (tmp != 0) {
			return tmp;
		}
		tmp = type.compareTo(o.type);
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
		result = (result * prime) + event == null ? 0 : event.hashCode();
		result = (result * prime) + (type == null ? 0 : type.hashCode());
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
		
		DateOrderDimention dateUserDimention = (DateOrderDimention) obj;
		if (this.date == null) {
			if (dateUserDimention.getDate() != null) {
				return false;
			}
		} else if (this.date != null) {
			if (dateUserDimention.getDate() == null) {
				return false;
			}
		} else if (!this.date.equals(dateUserDimention.getDate())) {
			return false;
		}
		
		if (this.event == null) {
			if (dateUserDimention.getEvent() != null) {
				return false;
			}
		} else if (this.event != null) {
			if (dateUserDimention.getEvent() == null) {
				return false;
			}
		} else if (!this.event.equals(dateUserDimention.getEvent())) {
			return false;
		}
		
		if (this.type == null) {
			if (dateUserDimention.type != null) {
				return false;
			}
		} else if (this.type != null) {
			if (dateUserDimention.type == null) {
				return false;
			}
		} else if (!this.type.equals(dateUserDimention.type)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return date + "\t" + type;
	}
}
