package com.oracle.website.date.user.constants;

public class WebsiteConstants {

	// 用户分析
	public static final int USERTYPE = 1;
	// 会员分析
	public static final int MEMBERTYPE = 2;
	
	// 用户分析
	public static final int USERTYPE_BROWSER = 3;
	// 会员分析
	public static final int MEMBERTYPE_BROWSER = 4;
	
	
	// 订单分析-支付成功
	public static final int ORDER_E_CS = 5;
	// 订单分析-退款
	public static final int ORDER_E_CRT = 6;
	// 订单分析-订单
	public static final int ORDER_ONUMS = 7;
	
	// 订单分析-支付成功
	public static final String ORDER_E_CS_STR = "success";
	// 订单分析-退款
	public static final String ORDER_E_CRT_STR = "refund";
	// 订单分析-订单
	public static final String ORDER_ONUMS_STR = "order";
	
	
	// Hourly分析-用户数
	public static final String HOURLY_USER = "user_numbers";
	// Hourly分析-会话数
	public static final String HOURLY_SESSION_NUMBERS = "session_numbers";
	// Hourly分析-会话长度
	public static final String HOURLY_SESSION_LENGTH = "session_length";
}
