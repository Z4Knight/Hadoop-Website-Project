package com.oracle.website.date.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class DateBrowserPvDimention implements WritableComparable<DateBrowserPvDimention>{

	private String date;
	
	private String browserInfo;
	
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBrowserInfo() {
		return browserInfo;
	}

	public void setBrowserInfo(String browserInfo) {
		this.browserInfo = browserInfo;
	}

	public void write(DataOutput out) throws IOException {
		out.writeUTF(date);
		out.writeUTF(browserInfo);
		out.writeUTF(type);
	}

	public void readFields(DataInput in) throws IOException {
		date = in.readUTF();
		browserInfo = in.readUTF();
		type = in.readUTF();
	}

	public int compareTo(DateBrowserPvDimention o) {
		if (this == o) {
			return 0;
		}
		int tmp = this.date.compareTo(o.date);
		if (tmp != 0) {
			return tmp;
		}
		
		tmp = this.browserInfo.compareTo(o.browserInfo);
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
		result = (result * prime) + date == null ? 0 : date.hashCode();
		result = (result * prime) + browserInfo == null ? 0 : browserInfo.hashCode();
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
		
		DateBrowserPvDimention dateBrowserPvDimention = (DateBrowserPvDimention) obj;
		if (this.date == null) {
			if (dateBrowserPvDimention.getDate() != null) {
				return false;
			}
		} else if (this.date != null) {
			if (dateBrowserPvDimention.getDate() == null) {
				return false;
			}
		} else if (!this.date.equals(dateBrowserPvDimention.getDate())) {
			return false;
		}
		
		if (this.browserInfo == null) {
			if (dateBrowserPvDimention.getBrowserInfo() != null) {
				return false;
			}
		} else if (this.browserInfo != null) {
			if (dateBrowserPvDimention.getBrowserInfo() == null) {
				return false;
			}
		} else if (!this.browserInfo.equals(dateBrowserPvDimention.getBrowserInfo())) {
			return false;
		}
		
		if (this.type == null) {
			if (dateBrowserPvDimention.getType() != null) {
				return false;
			}
		} else if (this.type != null) {
			if (dateBrowserPvDimention.getType() == null) {
				return false;
			}
		} else if (!this.type.equals(dateBrowserPvDimention.getType())) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return date + "\t" + browserInfo + "\t" + type;
	}
}
