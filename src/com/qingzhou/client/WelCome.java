package com.qingzhou.client;

import android.os.Bundle;
import android.os.Handler;
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
		
		
	new Handler().postDelayed(new Runnable(){
		@Override
		public void run(){
			Intent intent = new Intent (WelCome.this,MainActivity.class);			
			startActivity(intent);			
			WelCome.this.finish();
		}
	}, 2000);
   }
}
