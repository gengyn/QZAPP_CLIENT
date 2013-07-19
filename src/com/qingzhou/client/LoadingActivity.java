package com.qingzhou.client;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

public class LoadingActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.loading);
			
	new Handler().postDelayed(new Runnable(){
		@Override
		public void run(){
			Intent intent = new Intent (LoadingActivity.this,MainWeixin.class);			
			startActivity(intent);			
			LoadingActivity.this.finish();
			//Toast.makeText(getApplicationContext(), "loading", Toast.LENGTH_SHORT).show();
		}
	}, 200);
   }
}