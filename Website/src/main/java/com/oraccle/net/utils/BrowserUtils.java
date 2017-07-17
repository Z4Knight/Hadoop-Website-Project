package com.oraccle.net.utils;

import java.net.URLDecoder;

import cz.mallat.uasparser.UserAgentInfo;

public class BrowserUtils {

	public static String getBrowserInfo(String browserInfo) {
		String agentDecoder=URLDecoder.decode(browserInfo);
		UserAgentInfo userAgentInfo=UserAgentUtils.getUserAgentInfo(agentDecoder);
		return userAgentInfo.getUaFamily();
	}
}
