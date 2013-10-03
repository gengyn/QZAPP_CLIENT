package com.qingzhou.client;

import com.qingzhou.app.utils.DialogUtils;
import com.qingzhou.app.utils.FileUtils;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.version.VersionUpdate;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.app.Activity;
import android.content.Intent;

/**
 * 欢迎页
 * @author hihi
 *
 */
public class WelCome extends Activity{

	private QcApp qcApp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.welcome);
		
		qcApp = (QcApp)getApplication();
		
		Intent intent = getIntent();
		qcApp.setGoFlag(intent.getIntExtra("FLAG",0));
		
//		final View view = View.inflate(this, R.layout.welcome, null);
//		setContentView(view);
//		
//		//渐变展示启动屏
//		AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
//		aa.setDuration(2000);
//		view.startAnimation(aa);
//		aa.setAnimationListener(new AnimationListener()
//		{
//			@Override
//			public void onAnimationEnd(Animation arg0) {
//				Intent intent = new Intent (WelCome.this,LoginActivity.class);			
//				startActivity(intent);			
//				WelCome.this.finish();
//			}
//			@Override
//			public void onAnimationRepeat(Animation animation) {}
//			@Override
//			public void onAnimationStart(Animation animation) {}
//			
//		});
		
		//检查项目现在包括SD卡、网络
		//检查SD卡
		if (!FileUtils.checkSD(this.getResources().getText(R.string.savePath).toString()))
		{
			DialogUtils.showLongToask(this,this.getResources().getText(R.string.sdErr).toString());
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
						Intent intent = new Intent (WelCome.this,LoginActivity.class);			
						startActivity(intent);			
						WelCome.this.finish();
					}
				}, 2000);
			}else
			{
				DialogUtils.showLongToask(this,this.getResources().getText(R.string.networkErr).toString());
			}
		}
   }
	
}
