package com.qingzhou.client;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.fastjson.JSONArray;
import com.qingzhou.app.image.util.Utils;
import com.qingzhou.app.photo.PhotoFragment;
import com.qingzhou.client.domain.RestProjectPhoto;

public class ProjectPhotoActivity extends FragmentActivity{
	
	private static final String TAG = "ProjectPhotoActivity";
    
	@Override
	    public void onCreate(Bundle savedInstanceState) {
			if (BuildConfig.DEBUG) {
	            Utils.enableStrictMode();
	        }
	        super.onCreate(savedInstanceState);
	       
	        //取得启动该Activity的Intent对象
	        Intent intent = getIntent();
	        String pathJson = intent.getStringExtra("photoPath");
	        List<RestProjectPhoto> photoList = JSONArray.parseArray(pathJson,RestProjectPhoto.class); 
	       
	        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
	            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	            ft.add(android.R.id.content, new PhotoFragment(photoList), TAG);
	            ft.commit();
	        }
	        
	 }
}
