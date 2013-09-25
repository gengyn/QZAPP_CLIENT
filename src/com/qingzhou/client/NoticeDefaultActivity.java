package com.qingzhou.client;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qingzhou.app.utils.CustomerUtils;
import com.qingzhou.app.utils.DialogUtils;
import com.qingzhou.app.utils.FileUtils;
import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.domain.PushExtras;

import com.alibaba.fastjson.JSON;

/**
 * 通知显示页
 * @author hihi
 *
 */
public class NoticeDefaultActivity extends Activity {
	
	private TextView notice_content;
	private WebView notice_url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_default);
        
        //取得启动该Activity的Intent对象
        Intent intent = getIntent();
        PushExtras extras = (PushExtras) intent.getSerializableExtra("extras");
        
        notice_content = (TextView) this.findViewById(R.id.notice_content);
        notice_url  = (WebView) this.findViewById(R.id.notice_url);
        
        if (extras != null)
        {
        	notice_content.setText(extras.getNotice_content());
        	notice_url.loadUrl(extras.getNotice_url());
        }
        

       
        
    }
}
