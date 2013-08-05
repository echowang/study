package com.mh.sdk.service;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

import com.mh.sdk.util.MHEncryptUtil;
import com.mh.sdk.util.MHHttpClient;
import com.mh.sdk.util.MHPlatformConfig;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class MHRegisterService extends AsyncTask<String, Integer, String>{
	private ProgressDialog dialog;
	private Context context;
//	private String username;
	private String password;
	private String fuid;
	
	public MHRegisterService(Context context){
		this.context = context;
		dialog = new ProgressDialog(context);
		dialog.setMessage("正在注册用户");
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
	}

	public void registerUser(String username, String password,String fuid){
		if (username == null || "".equals(username) || password == null || "".equals(password)) {
			return ;
		}
		
//		this.username = username;
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
		param.put("device_id", MHPlatformConfig.getDeviceId(context));
		
//		System.out.println("fudid => " + fuid);
		
		String result = MHHttpClient.get(MHPlatformConfig.REGISTERURL + "?" + MHHttpClient.buildParam(param).trim());
		
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
		System.out.println("register result => " + result);
		dialog.dismiss();
		//{"ret":"","msg":"OK","uid":"17651786","username":"tiytihvghjj"}
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
					MHLoginService loginService = new MHLoginService(context);
					loginService.userLogin(username, password,fuid);
				}else{
					MHGenericService.createAlertDialog(context, "数据验证失败").show();
				}
				
			}else{
				MHGenericService.createAlertDialog(context, msg).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
