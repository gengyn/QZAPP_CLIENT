package com.qingzhou.client;

import com.qingzhou.client.LoadingActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

/**
 * 主界面活动
 * @author hihi
 *
 */
public class MainActivity extends Activity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * 菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * 我的消息点击事件
	 * @param arg0
	 */
	public void mymessage_onClick(View arg0) {
		
		Intent intent = new Intent();
	    intent.setClass(MainActivity.this,LoadingActivity.class);
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
	    intent.setClass(MainActivity.this,MyHomeActivity.class);
	    startActivity(intent);
	}
	
	/**
	 * 我的合同点击事件
	 * @param arg0
	 */
	public void mycontract_onclick(View arg0)
	{
		Intent intent = new Intent();
	    intent.setClass(MainActivity.this,MyContractActivity.class);
	    startActivity(intent);
	}
	
	
}
