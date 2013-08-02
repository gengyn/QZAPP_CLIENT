package com.qingzhou.client;

import com.qingzhou.client.version.VersionUpdate;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;

/**
 * 欢迎页
 * @author hihi
 *
 */
public class WelCome extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.welcome);
		//检查网络
		VersionUpdate.checkNetwork(this);
		if(VersionUpdate.isValid)
		{
			//是否能获取版本信息
			new Thread(){
				public void run(){
					VersionUpdate.getVersionInfo();
				}
			}.start();
		}
		
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run(){
				Intent intent = new Intent (WelCome.this,MainActivity.class);			
				startActivity(intent);			
				WelCome.this.finish();
			}
		}, 3000);
   }
	
}
