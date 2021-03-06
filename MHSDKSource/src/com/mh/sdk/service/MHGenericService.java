package com.mh.sdk.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mh.sdk.util.MHEncryptUtil;

public class MHGenericService {
	private final static String ENCRYPTKEY = "52601B187AE3BD6953035F81820BD5FA";

	/**
	 * 创建提示对话框
	 * 
	 * @param context
	 * @param message
	 * @return
	 */
	public static Builder createAlertDialog(Context context, String message) {
		Builder builder = new Builder(context);
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setMessage(message);
		return builder;
	}

	/**
	 * 保存用户信息
	 * 
	 * @param context
	 * @param username
	 * @param password
	 */
	@SuppressWarnings("unchecked")
	public static void saveLoginUser(Context context, String username,
			String password) {
		username.trim();
		password.trim();
		if (username == null || "".equals(username) || password == null
				|| "".equals(password)) {
			return;
		}
		try {
			password = MHEncryptUtil.encrypt(ENCRYPTKEY, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MHUserInfo", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("LastLoginName", username);

		GsonBuilder builder = new GsonBuilder();
		// 不转换没有 @Expose 注解的字段
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		Map<String, String> allUsers;
		String mapJson = sharedPreferences.getString("AllLoginUser", null);
		if (mapJson == null) {
			allUsers = new HashMap<String, String>();
		} else {
			allUsers = (Map<String, String>) gson.fromJson(mapJson,
					new TypeToken<Map<String, String>>() {
					}.getType());
		}
		if (allUsers.containsKey(username)) {
			allUsers.remove(username);
		}
		allUsers.put(username, password);
		editor.putString("AllLoginUser", gson.toJson(allUsers));
		editor.commit();
	}

	/**
	 * 获取最后登录的用户
	 * 
	 * @param context
	 * @return
	 */
	public static String getLastLoginUser(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MHUserInfo", Context.MODE_PRIVATE);
		return sharedPreferences.getString("LastLoginName", "");
	}

	/**
	 * 获取所有登录的用户
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> getAllLoginUser(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MHUserInfo", Context.MODE_PRIVATE);
		String mapJson = sharedPreferences.getString("AllLoginUser", null);
		if (mapJson != null) {
			GsonBuilder builder = new GsonBuilder();
			// 不转换没有 @Expose 注解的字段
			builder.excludeFieldsWithoutExposeAnnotation();
			Gson gson = builder.create();
			Map<String, String> allUsers = (Map<String, String>) gson.fromJson(
					mapJson, new TypeToken<Map<String, String>>() {
					}.getType());
			Set<String> keys = allUsers.keySet();
			return keys;
		}
		return null;
	}

	/**
	 * 根据用户名获取密码
	 * 
	 * @param context
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getPasswordByUsername(Context context, String username) {
		if (username == null || "".equals(username)) {
			return "";
		}

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MHUserInfo", Context.MODE_PRIVATE);
		String mapJson = sharedPreferences.getString("AllLoginUser", null);
		if (mapJson != null) {
			GsonBuilder builder = new GsonBuilder();
			// 不转换没有 @Expose 注解的字段
			builder.excludeFieldsWithoutExposeAnnotation();
			Gson gson = builder.create();
			Map<String, String> allUsers = (Map<String, String>) gson.fromJson(
					mapJson, new TypeToken<Map<String, String>>() {
					}.getType());
			if (allUsers.containsKey(username)) {
				String password = allUsers.get(username);
				try {
					password = MHEncryptUtil.decrypt(ENCRYPTKEY,
							allUsers.get(username));
					// System.out.println("解密后的 pwd => " + password);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return password;
			}

		}
		return "";
	}

	/**
	 * 获取是否自动登录
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getIsAutoLogin(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MHUserInfo", Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean("autoLogin", true);
	}

	/**
	 * 设置自动登录
	 * 
	 * @param context
	 * @param isLogin
	 */
	public static void setIsAutoLogin(Context context, boolean isLogin) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MHUserInfo", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("autoLogin", isLogin);
		editor.commit();
	}
}
