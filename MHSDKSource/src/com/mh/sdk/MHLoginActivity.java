package com.mh.sdk;

import java.util.Set;

import com.mh.sdk.platform.MHPlatform;
import com.mh.sdk.service.MHGenericService;
import com.mh.sdk.service.MHLoginService;
import com.mh.sdk.util.MHGenericUtil;

import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MHLoginActivity extends MHSDKBaseActivity implements OnClickListener{
	private Button loginBtn;
	private Button registerBtn;
	private Button backBtn;
	private Button findpwdBtn;
	private EditText usernameText;
	private EditText passwordText;
	private CheckBox autoCheckBox;
	private ImageView allUsersBtn;
	private String[] allUsers;
	
	private int userSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewByName("mh_login_activity");
		
		loginBtn = (Button) findViewByName("login_login");
        loginBtn.setOnClickListener(this);
        registerBtn = (Button) findViewByName("login_register");
        registerBtn.setOnClickListener(this);
        backBtn = (Button) findViewByName("login_back");
        backBtn.setOnClickListener(this);
        findpwdBtn = (Button) findViewByName("login_findpwd");
        findpwdBtn.setOnClickListener(this);
        allUsersBtn = (ImageView) findViewByName("login_down_arrow");
        allUsersBtn.setOnClickListener(this);
        
        usernameText = (EditText) findViewByName("login_username");
        passwordText = (EditText) findViewByName("login_password");
        autoCheckBox = (CheckBox) findViewByName("login_autologin");
        autoCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				MHGenericService.setIsAutoLogin(MHLoginActivity.this, isChecked);
			}
		});
        
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		 Intent intent = getIntent();
	     if(intent.hasExtra("errormsg")){
	        String msg = intent.getStringExtra("errormsg");
	        if(msg != null && msg.length() > 0){
	        	MHGenericService.createAlertDialog(this, msg);
	        }
	     }
		
		String lastUserName = MHGenericService.getLastLoginUser(this);
		String userPwd = MHGenericService.getPasswordByUsername(this, lastUserName);
		usernameText.setText(lastUserName.trim());
		passwordText.setText(userPwd.trim());
		autoCheckBox.setChecked(true);
		MHGenericService.setIsAutoLogin(this,true);
		
		Set<String> users = MHGenericService.getAllLoginUser(this);
		if(users != null && users.size() > 1){
			allUsers = new String[users.size()];
			int i = 0;
			for(String user : users){
				allUsers[i++] = user;
			}
			allUsersBtn.setVisibility(View.VISIBLE);
		}else{
			allUsersBtn.setVisibility(View.GONE);
		}
	}
	
	private boolean verifyUserInfo(String username,String password){
		if(username == null || username.length() == 0){
			MHGenericService.createAlertDialog(this,"请输入用户名").show();
			return false;
		}
		if(password == null || password.length() == 0){
			MHGenericService.createAlertDialog(this,"请输入密码").show();
			return false;
		}
		return true;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
        if (keyCode == KeyEvent.KEYCODE_BACK) {
             //do something...
        	MHPlatform.getMHPlatformInstance().getCallBackListener().quitMHPlatform();
          }
          return super.onKeyDown(keyCode, event);
      }
	
	public void loginAction(String username,String userpwd){
		MHLoginService loginService = new MHLoginService(this);
		loginService.userLogin(username, userpwd,this.getResources().getString(findStringIDByName("fuid")));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String username = usernameText.getEditableText().toString().trim();
		String password = passwordText.getEditableText().toString().trim();
		if (loginBtn.getId() == v.getId()) {
			if(!MHGenericUtil.isNetworkAvailable(this)){
				MHGenericService.createAlertDialog(this, "当前网络不可用,请检查网络设置").show();
				return ;
			}
			if(verifyUserInfo(username, password)){
				//隐藏键盘
				InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
				if ( imm.isActive( ) ) {
					imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );
				}
				
				loginAction(username, password);
			}
			return ;
		}
		
		if (registerBtn.getId() == v.getId()) {
			Intent intent = new Intent(this, MHRegisterActivity.class);
			this.startActivity(intent);
			this.finish();
			return ;
		}
		
		if (backBtn.getId() == v.getId()) {
			MHPlatform.getMHPlatformInstance().getCallBackListener().quitMHPlatform();
			this.finish();
		}
		
		if (findpwdBtn.getId() == v.getId()) {
			Intent intent = new Intent(this, MHFindpwdActivity.class);
			this.startActivity(intent);
		}
		
		if(allUsersBtn.getId() == v.getId()){
			String user = usernameText.getEditableText().toString();
			for(int i = 0; i < allUsers.length; i++){
				if(user.equals(allUsers[i])){
					userSelected = i;
					break;
				}
			}
			Dialog dialog=null;
			Builder builder = new Builder(this);
			builder.setTitle("选择用户");
			builder.setSingleChoiceItems(allUsers, userSelected, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String lastUserName = allUsers[which];
					String userPwd = MHGenericService.getPasswordByUsername(MHLoginActivity.this, lastUserName);
					usernameText.setText(lastUserName);
					passwordText.setText(userPwd);
					dialog.dismiss();
				}
			});
			dialog = builder.create();
			dialog.show();
			return ;
		}
	}

}
