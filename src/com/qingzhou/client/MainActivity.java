package com.qingzhou.client;

import com.qingzhou.client.LoadingActivity;
import com.qingzhou.client.version.VersionUpdate;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.KeyEvent;
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
        	showExitDialog();  
            return true;  
        }  
        return true;  
    } 
	
	/**
	 * 退出应用对话框
	 */
	protected void showExitDialog() {  
		AlertDialog.Builder builder = new Builder(this);  
		builder.setMessage("确定要退出吗?");  
		builder.setTitle("提示");  
		builder.setPositiveButton("确认", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//另外一种关闭应用的方式
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();				
			}
		});
        
        builder.create().show();  
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
