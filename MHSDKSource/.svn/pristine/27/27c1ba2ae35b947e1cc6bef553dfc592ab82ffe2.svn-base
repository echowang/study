package com.mh.sdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class MHGenericUtil {
	public static boolean isNetworkAvailable(Context context) {
		return (isWiFiActive(context) || is3GAvailable(context));
	}

	/**
	 *  判断wifi是否已经连接
	 * @param context
	 * @return
	 */
	public static boolean isWiFiActive(Context context) {
		WifiManager mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
		int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
		if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
//			System.out.println("**** WIFI is on");
			return true;
		} else {
//			System.out.println("**** WIFI is off");
			return false;
		}
	}

	/**
	 *  判断3G网络是否已经连接
	 * @param context
	 * @return
	 */
	public static boolean is3GAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
//			System.out.println("**** newwork is off");
			return false;
		} else {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info == null) {
//				System.out.println("**** newwork is off");
				return false;
			} else {
				if (info.isAvailable()) {
//					System.out.println("**** newwork is on");
					return true;
				}
			}
		}
//		System.out.println("**** newwork is off");
		return false;
	}
}
