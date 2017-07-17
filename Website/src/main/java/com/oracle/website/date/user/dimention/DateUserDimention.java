package com.oracle.website.date.user.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;


public class DateUserDimention implements WritableComparable<DateUserDimention> {

	private String userDate;
	private String event;
	private Integer type;
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUserDate() {
		return userDate;
	}

	public void setUserDate(String userDate) {
		this.userDate = userDate;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void write(DataOutput out) throws IOException {
		out.writeUTF(userDate);
		out.writeUTF(event);
		out.writeInt(type);
	}

	public void readFields(DataInput in) throws IOException {
		userDate = in.readUTF();
		event = in.readUTF();
		type = in.readInt();
	}

	public int compareTo(DateUserDimention o) {
		if (this == o) {
			return 0;
		}
		int tmp = this.userDate.compareTo(o.userDate);
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
		result = (result * prime) + userDate == null ? 0 : userDate.hashCode();
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
		
		DateUserDimention dateUserDimention = (DateUserDimention) obj;
		if (this.userDate == null) {
			if (dateUserDimention.getUserDate() != null) {
				return false;
			}
		} else if (this.userDate != null) {
			if (dateUserDimention.getUserDate() == null) {
				return false;
			}
		} else if (!this.userDate.equals(dateUserDimention.getUserDate())) {
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
		return userDate;
	}
}
