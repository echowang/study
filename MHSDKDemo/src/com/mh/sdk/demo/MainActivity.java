package com.mh.sdk.demo;

import com.mh.sdk.platform.MHPlatform;
import com.mh.sdk.platform.MHPlatformCallBack;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	private Button loginBtn;
	private Button changeUserBtn;
	private Button paymentBtn;
	private Button logoutBtn;
	private TextView uidTextView;
	private TextView usernameTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		
		setContentView(R.layout.activity_main);
		
		uidTextView = (TextView) findViewById(R.id.test_uid);
		usernameTextView = (TextView) findViewById(R.id.test_name);
		
		loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(this);
		changeUserBtn = (Button) findViewById(R.id.changeUserBtn);
		changeUserBtn.setOnClickListener(this);
		paymentBtn = (Button) findViewById(R.id.paymentBtn);
		paymentBtn.setOnClickListener(this);
		logoutBtn = (Button) findViewById(R.id.logoutBtn);
		logoutBtn.setOnClickListener(this);
		
		MHPlatform.mhPlatformInit("10000000", "228bf094169a40a3bd188ba37ebe8723");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		MHPlatform platform = MHPlatform.getMHPlatformInstance();
		switch (v.getId()) {
		case R.id.loginBtn:
			platform.mhPlatformLogin(this, new MHPlatformCallBack() {
				@Override
				public void quitMHPlatform() {
					// TODO Auto-generated method stub
					super.quitMHPlatform();
				}
				
				@Override
				public void handleLoginResult(String uid, String username) {
					// TODO Auto-generated method stub
					super.handleLoginResult(uid, username);
					uidTextView.setText(uid);
					usernameTextView.setText(username);
				}
			});
			break;
		case R.id.changeUserBtn:
			platform.mhPlatformChangeUser(this, new MHPlatformCallBack() {
				@Override
				public void quitMHPlatform() {
					// TODO Auto-generated method stub
					super.quitMHPlatform();
				}
				
				@Override
				public void handleLoginResult(String uid, String username) {
					// TODO Auto-generated method stub
					super.handleLoginResult(uid, username);
					System.out.println("uid => " + uid);
					System.out.println("username => " + username);
					
					uidTextView.setText(uid);
					usernameTextView.setText(username);
				}
				
				@Override
				public void handleUserLogout() {
					// TODO Auto-generated method stub
					super.handleUserLogout();
					uidTextView.setText("");
					usernameTextView.setText("");
				}
			});
			break;
		case R.id.paymentBtn:
			platform.mhPlatformPayment(this, "test", "34051", "1","mark", new MHPlatformCallBack() {
				@Override
				public void quitMHPlatform() {
					// TODO Auto-generated method stub
					super.quitMHPlatform();
				}
				
				@Override
				public void handlePaymentResult() {
					// TODO Auto-generated method stub
					super.handlePaymentResult();
				}
			});
			break;
		case R.id.logoutBtn:
			platform.mhPlatformLogout(this, new MHPlatformCallBack() {
				@Override
				public void handleUserLogout() {
					// TODO Auto-generated method stub
					super.handleUserLogout();
					
					uidTextView.setText("");
					usernameTextView.setText("");
					
					//处理游戏中的退出登录操作
				}
			});
			break;
		
		default:
			break;
		}
	}

}
