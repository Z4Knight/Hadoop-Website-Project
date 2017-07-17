package com.oracle.website.date.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class DateRegionDimention implements WritableComparable<DateRegionDimention>{

	private String ip;
	private String date;
	private String session;
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(ip);
		out.writeUTF(date);
		out.writeUTF(session);
		out.writeUTF(type);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		ip = in.readUTF();
		date = in.readUTF();
		session = in.readUTF();
		type = in.readUTF();
	}

	@Override
	public int compareTo(DateRegionDimention o) {
		if (this == o) {
			return 0;
		}
		int tmp = this.ip.compareTo(o.ip);
		if (tmp != 0) {
			return tmp;
		}
	    tmp = this.date.compareTo(o.date);
		if (tmp != 0) {
			return tmp;
		}
		tmp = this.session.compareTo(o.session);
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
		result = (result * prime) + ip == null ? 0 : ip.hashCode();
		result = (result * prime) + session == null ? 0 : session.hashCode();
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
		
		DateRegionDimention dateRegionDimention = (DateRegionDimention) obj;
		if (this.date == null) {
			if (dateRegionDimention.getDate() != null) {
				return false;
			}
		} else if (this.date != null) {
			if (dateRegionDimention.getDate() == null) {
				return false;
			}
		} else if (!this.date.equals(dateRegionDimention.getDate())) {
			return false;
		}
		
		if (this.ip == null) {
			if (dateRegionDimention.getIp() != null) {
				return false;
			}
		} else if (this.ip != null) {
			if (dateRegionDimention.getIp() == null) {
				return false;
			}
		} else if (!this.ip.equals(dateRegionDimention.getIp())) {
			return false;
		}
		
		if (this.session == null) {
			if (dateRegionDimention.getSession() != null) {
				return false;
			}
		} else if (this.session != null) {
			if (dateRegionDimention.getSession() == null) {
				return false;
			}
		} else if (!this.session.equals(dateRegionDimention.getSession())) {
			return false;
		}
		
		
		if (this.type == null) {
			if (dateRegionDimention.type != null) {
				return false;
			}
		} else if (this.type != null) {
			if (dateRegionDimention.type == null) {
				return false;
			}
		} else if (!this.type.equals(dateRegionDimention.type)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return date + "\t" + ip + "\t" + type;
	}
	
}
