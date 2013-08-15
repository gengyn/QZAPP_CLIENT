package com.qingzhou.client;

import com.qingzhou.client.util.FileUtil;
import com.qingzhou.client.util.DialogUtil;
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
		
		//检查项目现在包括SD卡、网络
		//检查SD卡
		if (!FileUtil.checkSD(this.getResources().getText(R.string.savePath).toString()))
		{
			DialogUtil.showErrDialog(this,this.getResources().getText(R.string.sdErr).toString());
		}else
		{
			//是否进行版本检查的开关
			boolean isUpdate = new Boolean(this.getResources().getString(R.string.isupdate));
			//检查网络
			VersionUpdate.checkNetwork(this);
			if(VersionUpdate.isValid)
			{
				if (isUpdate)
				{	//获取版本信息
					new Thread(){
						public void run(){
							VersionUpdate.getVersionInfo();
						}
					}.start();
				}
				
				//自动跳转登录界面
				new Handler().postDelayed(new Runnable(){
					@Override
					public void run(){
						Intent intent = new Intent (WelCome.this,Login.class);			
						startActivity(intent);			
						WelCome.this.finish();
					}
				}, 3000);
			}else
			{
				DialogUtil.showErrDialog(this,this.getResources().getText(R.string.networkErr).toString());
			}
		}
   }
	
}
