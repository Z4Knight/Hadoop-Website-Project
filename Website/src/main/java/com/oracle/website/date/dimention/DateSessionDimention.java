package com.oracle.website.date.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class DateSessionDimention implements WritableComparable<DateSessionDimention> {
	
	private String date;
	private String session;
	
	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public void write(DataOutput out) throws IOException {
		out.writeUTF(date);
		out.writeUTF(session);
	}

	public void readFields(DataInput in) throws IOException {
		date = in.readUTF();
		session = in.readUTF();
	}

	public int compareTo(DateSessionDimention o) {
		if (this == o) {
			return 0;
		}
		
		int tmp = this.date.compareTo(o.date);
		if (tmp != 0) {
			return tmp;
		}
		
		tmp = this.session.compareTo(o.session);
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
		result = (result * prime) + session == null ? 0 : session.hashCode();
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
		
		DateSessionDimention dateSessionDimention = (DateSessionDimention) obj;
		if (this.date == null) {
			if (dateSessionDimention.getDate() != null) {
				return false;
			}
		} else if (this.date != null) {
			if (dateSessionDimention.getDate() == null) {
				return false;
			}
		} else if (!this.date.equals(dateSessionDimention.getDate())) {
			return false;
		}
		
		if (this.session == null) {
			if (dateSessionDimention.getSession() != null) {
				return false;
			}
		} else if (this.session != null) {
			if (dateSessionDimention.getSession() == null) {
				return false;
			}
		} else if (!this.session.equals(dateSessionDimention.getSession())) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return date + "\t" + session;
	}
}
