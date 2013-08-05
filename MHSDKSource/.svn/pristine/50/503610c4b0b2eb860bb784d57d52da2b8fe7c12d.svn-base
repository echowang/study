package com.mh.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class MHSDKBaseActivity extends Activity {
	public final static String DRAWABLE = "drawable";
	public final static String LAYOUT = "layout";
	public final static String ID = "id";
	public final static String COLOR = "color";
	public final static String DIMEN = "dimen";
	public final static String STRING = "string";
	public final static String STYLE = "style";
	
	/**
	 * 按资源名称查找资源名称
	 * @param context
	 * @param packageName
	 * @param resourcesName
	 * @return
	 */
	public static int getResourcesIdByName(Context context, String packageName,
			String resourcesName) {
		Resources resources = context.getResources();
		int id = resources.getIdentifier(resourcesName, packageName,
				context.getPackageName());
		if (id == 0) {
			Log.e("com.mh.sdk", "资源文件读取不到！");
		}
		return id;
	}
	
	/**
	 *  按资源名称设置layout
	 * @param resourcesName
	 */
	protected void setContentViewByName(String resourcesName) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		this.setContentView(getResourcesIdByName(this, LAYOUT, resourcesName));
	}
	
	/**
	 *  按资源名称查找View
	 * @param resourcesName
	 * @return
	 */
	protected View findViewByName(String resourcesName) {
		// TODO Auto-generated method stub
		return super
				.findViewById(getResourcesIdByName(this, ID, resourcesName));
	}
	
	/**
	 *  按资源名称查找字符串id
	 * @param resourcesName
	 * @return
	 */
	public int findStringIDByName(String resourcesName) {
		return getResourcesIdByName(this, STRING, resourcesName);
	}
}
