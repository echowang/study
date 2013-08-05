package com.mh.sdk;

import com.mh.sdk.util.MHPlatformConfig;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MHFindpwdActivity extends MHSDKBaseActivity implements OnClickListener{
	private WebView mWebView;
	private Button backBtn;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewByName("mh_findpwd_activity");
		
		dialog = new ProgressDialog(this);
		dialog.setMessage("正在加载");
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		
		backBtn = (Button) findViewByName("findpwd_back");
		backBtn.setOnClickListener(this);
		
		mWebView = (WebView) findViewByName("findpwd_webview");
		
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		mWebView.clearCache(true);
		
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				super.shouldOverrideUrlLoading(view, url);
//				System.out.println("url => " + url);
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				
				dialog.cancel();
			}

			@Override
			public void onPageStarted(WebView view, String url,
					Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				dialog.show();
			}
		});
		
		mWebView.loadUrl(MHPlatformConfig.FINDPWDURL);
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		dialog.dismiss();
		super.finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (backBtn.getId() == v.getId()) {
			this.finish();
		}
	}
}
