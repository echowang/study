package com.mh.sdk.util;

import java.net.URLEncoder;

import android.content.Context;
import android.provider.Settings.Secure;

public class MHPlatformConfig {
	public final static String LOGINURL = "http://p.txwy.com/api/signin";
	public final static String REGISTERURL = "http://p.txwy.com/api/reg";
	public final static String PAYMENTURL = "http://i.txwy.com/mpay/index.aspx";
	public final static String FINDPWDURL = "http://p.txwy.com/api/forgetpass";
	public final static String CALLBACKURL = "http://i.txwy.com/mpay/payment_over";
	
	public static String APPID = "10000000";
	public static String APPKEY = "228bf094169a40a3bd188ba37ebe8723";
	
	public static boolean isInitSDK = false; 
	public static boolean isLogin = false;
	
	public static String username = "";
	public static String uid = "";
	
	
	@SuppressWarnings("deprecation")
	public static String getSign(String time){
		String sign = "appid=" + MHPlatformConfig.APPID + "&time=" + time;
//		System.out.println("sign => " + sign);
		sign = URLEncoder.encode(sign.trim());
//		System.out.println("sign => " + sign);
		sign = MHEncryptUtil.hmac_sha1(MHPlatformConfig.APPKEY, sign.trim());
//		System.out.println("sign => " + sign);
		sign = URLEncoder.encode(sign.trim());
//		System.out.println("sign => " + sign);
		return sign;
	}
	
	public static String getDeviceId(Context context){
		String android_id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
//		System.out.println("android id is => " + android_id);
		return android_id;
	}
}
