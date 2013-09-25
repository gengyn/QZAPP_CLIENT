package com.qingzhou.client;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.qingzhou.app.utils.DialogUtils;
import com.qingzhou.app.utils.Logger;
import com.qingzhou.client.LoadingActivity;
import com.qingzhou.client.version.VersionUpdate;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.BuildConfig;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 主界面活动
 * @author hihi
 *
 */
public class MainActivity extends Activity implements TagAliasCallback{

	private static final String TAG = "MainActivity";
	public static MainActivity _instance = null; 
	
	private QcApp qcApp;
	private TextView main_title;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 _instance = this;
		qcApp = (QcApp)getApplication();
		main_title = (TextView) this.findViewById(R.id.main_title);
		main_title.setText(String.format(getResources().getString(R.string.hello),qcApp.getUserName()));
		
		//初始化 JPush
		initJpush();
		
		//版本更新
		VersionUpdate verUpdate = new VersionUpdate(this);
		verUpdate.checkNewVersion();
		
		//界面跳转
		jumpActivity();
	}
	
	@Override
	public void onResume() {
    	Logger.d(TAG, "mainActivity启动状态");
    	Constants.mainActivity_isBackground = false;
		super.onResume();
	}

	@Override
	public void onPause() {
		Logger.d(TAG, "mainActivity关闭状态");
		Constants.mainActivity_isBackground = true;
		super.onPause();
	}

	/**
	 * 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
	 * 绑定alias和tags，alias为客户编号，tags内置，可以修改和扩展
	 */
	private void initJpush(){
		if (BuildConfig.DEBUG)
			Log.i(TAG, "初始化JPush");
		JPushInterface.init(getApplicationContext());
		//最多保留十条
		JPushInterface.setLatestNotifactionNumber(getApplicationContext(), 10);
		//设置alias
		JPushInterface.setAliasAndTags(getApplicationContext(), qcApp.getUserPhone(),null,this);
		//设置tags
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : Constants.CLIENT_TAGS) {
			
			tagSet.add(sTagItme);
		}
		JPushInterface.setAliasAndTags(getApplicationContext(), null, tagSet, this);
	}
	/**
	 * 菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * 判断是否返回键，并且没有其他Activity时，提示退出
	 */
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
        	DialogUtils.showExitDialog(this);  
            return true;  
        }  
        return true;  
    } 
	
	

	/**
	 * 我的消息点击事件
	 * @param arg0
	 */
	public void mymessage_onClick(View arg0) {
		
		Intent intent = new Intent();
	    intent.setClass(MainActivity.this,MyMessageActivity.class);
	    startActivity(intent);
		
	}
	/**
	 * 轻舟资讯点击事件
	 * @param arg0
	 */
	public void myinfo_onclick(View arg0)
	{
		Intent intent = new Intent();
		intent.putExtra("FLAG", Constants.INIT_MYINFO);
	    intent.setClass(MainActivity.this,LoadingActivity.class);
		//增加翻页、下拉获取最新资讯功能
	    //intent.setClass(MainActivity.this,MyInfoActivity.class);
	    startActivity(intent);
	}
	
	/**
	 * 我的家装点击事件
	 * @param arg0
	 */
	public void myhome_onclick(View arg0)
	{
		Intent intent = new Intent();
		intent.putExtra("FLAG", Constants.INIT_PROJECTPLAN);
	    intent.setClass(MainActivity.this,LoadingActivity.class);
	    startActivity(intent);
	}
	
	/**
	 * 我的合同点击事件
	 * @param arg0
	 */
	public void mycontract_onclick(View arg0)
	{
		Intent intent = new Intent();
		intent.putExtra("FLAG", Constants.INIT_CONTRACT);
	    intent.setClass(MainActivity.this,LoadingActivity.class);
	    startActivity(intent);
	}
	
	/**
	 * 临时方法，切换用户
	 * @param arg0
	 */
	public void reLogin(View arg0)
	{
		Intent intent = new Intent();
		intent.putExtra("isReLogin", true);
		intent.setClass(MainActivity.this,LoginActivity.class);
		startActivity(intent);
		MainActivity.this.finish();
	}


	@Override
	public void gotResult(int code, String alias, Set<String> tags) {
		// TODO Auto-generated method stub
		String logs ;
		switch (code) {
		case 0:
			logs = "Set tag and alias success, alias = " + alias + "; tags = " + tags;
			if (BuildConfig.DEBUG)
				Log.i(TAG, logs);
			break;
		
		default:
			logs = "Failed with errorCode = " + code + " " + alias + "; tags = " + tags;
			Log.e(TAG, logs);
		}
	}
	
	/**
	 * 打开通知后的界面自动跳转
	 */
	private void jumpActivity()
	{
		switch (qcApp.getGoFlag())
		{
		case Constants.INIT_MYMESSAGE:
			mymessage_onClick(null);
			break;
		case Constants.INIT_CONTRACT:
			mycontract_onclick(null);
			break;
		case Constants.INIT_PROJECTPLAN:
			myhome_onclick(null);
			break;
		case Constants.INIT_MYINFO:
			myinfo_onclick(null);
			break;
		}
	}

}
