package com.oracle.ip.utils;

import com.oracle.ip.utils.IPSeekerExt.RegionInfo;

public class IPUtils {
	public static String getIPAddress(String ip) {
		IPSeekerExt ipSeekerExt = new IPSeekerExt();
		RegionInfo info = ipSeekerExt.analyticIp(ip);
		return info.getCountry()+" "+info.getProvince();
	}
	
	public static void main(String[] args) {
		System.out.println(getIPAddress("127.0.0.1"));
	}
}
