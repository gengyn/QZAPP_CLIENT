package com.qingzhou.client;

import com.alibaba.fastjson.JSON;
import com.qingzhou.client.LoadingActivity;
import com.qingzhou.client.version.VersionUpdate;
import com.qingzhou.client.common.GlobalParameter;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.util.DialogUtils;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

/**
 * 主界面活动
 * @author hihi
 *
 */
public class MainActivity extends Activity{

	private QcApp qcApp;
	private TextView main_title;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		qcApp = (QcApp)getApplication();
		main_title = (TextView) this.findViewById(R.id.main_title);
		main_title.setText(String.format(getResources().getString(R.string.hello),qcApp.getUserName()));
		
		//版本更新
		VersionUpdate verUpdate = new VersionUpdate(this);
		verUpdate.checkNewVersion();
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
	    intent.setClass(MainActivity.this,MainWeixin.class);
	    startActivity(intent);
		
	}
	/**
	 * 轻舟资讯点击事件
	 * @param arg0
	 */
	public void myinfo_onclick(View arg0)
	{
		Intent intent = new Intent();
	    intent.setClass(MainActivity.this,MyInfoActivity.class);
	    startActivity(intent);
	}
	
	/**
	 * 我的家装点击事件
	 * @param arg0
	 */
	public void myhome_onclick(View arg0)
	{
		Intent intent = new Intent();
		intent.putExtra("FLAG", GlobalParameter.INIT_PROJECTPLAN);
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
		intent.putExtra("FLAG", GlobalParameter.INIT_CONTRACT);
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
		intent.setClass(MainActivity.this,Login.class);
		startActivity(intent);
		MainActivity.this.finish();
	}
	
	
}
