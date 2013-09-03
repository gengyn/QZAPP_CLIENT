package com.qingzhou.client.image.ui;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.qingzhou.client.BuildConfig;
import com.qingzhou.client.domain.RestProjectPhoto;
import com.qingzhou.client.image.util.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * 图片浏览
 * @author hihi
 *
 */
public class ImageGridActivity extends FragmentActivity {
    private static final String TAG = "ImageGridActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            ft.add(android.R.id.content, new ImageGridFragment(photoList), TAG);
            ft.commit();
        }
    }
}
