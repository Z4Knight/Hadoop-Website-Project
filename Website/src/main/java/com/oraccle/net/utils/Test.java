package com.oraccle.net.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cz.mallat.uasparser.UserAgentInfo;

public class Test {

	public static void main(String[] args) {
		
		String agent="Mozilla%2F5.0%20(Windows%20NT%206.1%3B%20WOW64%3B%20rv%3A54.0)%20Gecko%2F20100101%20Firefox%2F54.0";
//		String agentDecoder=URLDecoder.decode(agent);
//		UserAgentInfo userAgentInfo=UserAgentUtils.getUserAgentInfo(agentDecoder);
//		System.out.println(userAgentInfo.getUaFamily());
		
		System.out.println(BrowserUtils.getBrowserInfo(agent));
//		
//		try {
//			System.out.println(URLDecoder.decode("%E4%BA%BA%E6%B0%91%E5%B8%81","UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
