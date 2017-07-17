package com.oracle.website.date.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class DateBrowserSessionDimention implements WritableComparable<DateBrowserSessionDimention>{
	private DateSessionDimention dateSessionDimention = 
			new DateSessionDimention();
	private String browserInfo;
	public DateSessionDimention getDateSessionDimention() {
		return dateSessionDimention;
	}
	public void setDateSessionDimention(DateSessionDimention dateSessionDimention) {
		this.dateSessionDimention = dateSessionDimention;
	}

	
	public String getBrowserInfo() {
		return browserInfo;
	}
	public void setBrowserInfo(String browserInfo) {
		this.browserInfo = browserInfo;
	}
	public void write(DataOutput out) throws IOException {
		dateSessionDimention.write(out);
		out.writeUTF(browserInfo);
	}
	public void readFields(DataInput in) throws IOException {
		this.dateSessionDimention.readFields(in);
		browserInfo = in.readUTF();
	}
	public int compareTo(DateBrowserSessionDimention o) {
		if (this == o) {
			return 0;
		}
		int tmp = this.dateSessionDimention.compareTo(o.dateSessionDimention);
		if (tmp != 0) {
			return tmp;
		}
		
		tmp = this.browserInfo.compareTo(o.browserInfo);
		if (tmp != 0) {
			return tmp;
		}
		return 0;
	}
	@Override
	public int hashCode() {
		int result = 1;
		int prime = 100;
		result = (result * prime) + (dateSessionDimention == null ? 0 : dateSessionDimention.hashCode());
		result = (result * prime) + browserInfo == null ? 0 : browserInfo.hashCode();
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
		
		DateBrowserSessionDimention dateBrowserDimention = (DateBrowserSessionDimention) obj;
		if (this.dateSessionDimention == null) {
			if (dateBrowserDimention.dateSessionDimention != null) {
				return false;
			}
		}else if (this.dateSessionDimention != null) {
			if (dateBrowserDimention.dateSessionDimention == null) {
				return false;
			}
		} else if (!this.dateSessionDimention.equals(dateBrowserDimention.dateSessionDimention)) {
			return false;
		}
		if (this.browserInfo == null) {
			if (dateBrowserDimention.browserInfo != null) {
				return false;
			}
		}else if (this.browserInfo != null) {
			if (dateBrowserDimention.browserInfo == null) {
				return false;
			}
		} else if (!this.browserInfo.equals(dateBrowserDimention.browserInfo)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return dateSessionDimention.toString() + "\t" + browserInfo;
	}
	
}
