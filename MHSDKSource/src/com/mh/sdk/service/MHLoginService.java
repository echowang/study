package com.mh.sdk.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mh.sdk.MHLoginActivity;
import com.mh.sdk.MHRegisterActivity;
import com.mh.sdk.platform.MHPlatform;
import com.mh.sdk.util.MHEncryptUtil;
import com.mh.sdk.util.MHHttpClient;
import com.mh.sdk.util.MHPlatformConfig;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class MHLoginService extends AsyncTask<String, Integer, String>{
	private ProgressDialog dialog;
	private Context context;
	private String password;
	private String fuid;
	
	public MHLoginService(Context context){
		this.context = context;
		dialog = new ProgressDialog(context);
		dialog.setMessage("正在登录");
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
	}

	public void userLogin(String username,String password,String fuid){
		if (username == null || "".equals(username) || password == null || "".equals(password)) {
			return ;
		}
		this.password = password;
		this.fuid = fuid;
		this.execute(username,password);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String username = params[0];
		String password = params[1];
		
		String time = String.valueOf(System.currentTimeMillis() / 1000);
		String sign = MHPlatformConfig.getSign(time);
		
		Map<String,String> param = new HashMap<String,String>();
		param.put("appid", MHPlatformConfig.APPID);
		param.put("appkey", MHPlatformConfig.APPKEY);
		param.put("sig", sign.trim());
		param.put("time", time);
		param.put("username", username);
		param.put("password", password);
		param.put("fuid", fuid);
		
		String loginUrl = MHPlatformConfig.LOGINURL + "?" + MHHttpClient.buildParam(param).trim();
//		System.out.println("loginUrl => " + loginUrl);
		String result = MHHttpClient.get(loginUrl);
		return result;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.show();
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.dismiss();
		System.out.println("login result => " + result);
		
		try {
			JSONObject jsonObject = new JSONObject(result);
			String ret = jsonObject.getString("ret");
			String msg = jsonObject.getString("msg");
			
			if(ret != null && "".equals(ret)){
				String uid = jsonObject.getString("uid");
				String username = jsonObject.getString("username");
				String time = jsonObject.getString("time");
				String sign = jsonObject.getString("sign");
				
				//sign=md5(md5(time + ret  + uid  + username) + "ghjEfdgs")
				String signString = MHEncryptUtil.toMd5(MHEncryptUtil.toMd5(time + ret  + uid  + username) + "ghjEfdgs");
//				System.out.println("sign => " + sign);
//				System.out.println("signString => " + signString);
				
				if(sign.equals(signString)){
					MHPlatformConfig.username = username;
					MHPlatformConfig.uid = uid;
					MHGenericService.saveLoginUser(context, username.trim(), password.trim());
					MHPlatformConfig.isLogin = true;
					MHPlatform.getMHPlatformInstance().getCallBackListener().handleLoginResult(uid, username);
					if(context instanceof MHLoginActivity){
						Activity activity = (Activity) context;
						activity.finish();
					}else if(context instanceof MHRegisterActivity){
						Activity activity = (Activity) context;
						activity.finish();
					}
				}else{
					Toast.makeText(context, "数据验证失败", Toast.LENGTH_SHORT).show();
				}
				
				
			}else{
				if(!(context instanceof MHLoginActivity)){
					Intent intent = new Intent(context, MHLoginActivity.class);
					intent.putExtra("errormsg", msg);
					context.startActivity(intent);
				}else{
					MHGenericService.createAlertDialog(context, msg).show();
				}
				
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
