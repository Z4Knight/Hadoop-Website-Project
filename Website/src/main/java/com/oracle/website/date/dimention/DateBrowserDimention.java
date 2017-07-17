package com.oracle.website.date.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class DateBrowserDimention implements WritableComparable<DateBrowserDimention>{
	private DateUserDimention dateUserDimention = 
			new DateUserDimention();
	private String browserInfo;
	public DateUserDimention getDateUserDimention() {
		return dateUserDimention;
	}
	public void setDateUserDimention(DateUserDimention dateUserDimention) {
		this.dateUserDimention = dateUserDimention;
	}

	
	public String getBrowserInfo() {
		return browserInfo;
	}
	public void setBrowserInfo(String browserInfo) {
		this.browserInfo = browserInfo;
	}
	public void write(DataOutput out) throws IOException {
		dateUserDimention.write(out);
		out.writeUTF(browserInfo);
	}
	public void readFields(DataInput in) throws IOException {
		this.dateUserDimention.readFields(in);
		browserInfo = in.readUTF();
	}
	public int compareTo(DateBrowserDimention o) {
		if (this == o) {
			return 0;
		}
		int tmp = this.dateUserDimention.compareTo(o.dateUserDimention);
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
		result = (result * prime) + (dateUserDimention == null ? 0 : dateUserDimention.hashCode());
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
		
		DateBrowserDimention dateBrowserDimention = (DateBrowserDimention) obj;
		if (this.dateUserDimention == null) {
			if (dateBrowserDimention.dateUserDimention != null) {
				return false;
			}
		}else if (this.dateUserDimention != null) {
			if (dateBrowserDimention.dateUserDimention == null) {
				return false;
			}
		} else if (!this.dateUserDimention.equals(dateBrowserDimention.dateUserDimention)) {
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
		return dateUserDimention.toString() + "\t" + browserInfo;
	}
	
}
