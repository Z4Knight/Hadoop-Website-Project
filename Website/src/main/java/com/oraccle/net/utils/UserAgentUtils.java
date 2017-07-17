package com.oraccle.net.utils;

import java.io.IOException;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;

public class UserAgentUtils
{

	static UASparser uaSparser;
	static
	{
		try {
			uaSparser= new UASparser(OnlineUpdater.getVendoredInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static UserAgentInfo getUserAgentInfo(String agent)
	{
		UserAgentInfo userAgent=null;
		try {
			userAgent=uaSparser.parse(agent);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userAgent;
	}
}
