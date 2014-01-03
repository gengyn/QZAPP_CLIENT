package com.qingzhou.client;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.qingzhou.app.utils.CustomerUtils;
import com.qingzhou.app.utils.DialogUtils;
import com.qingzhou.app.utils.FileUtils;
import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.common.QcApp;

import com.alibaba.fastjson.JSON;

/**
 * 我的设置
 * @author hihi
 *
 */
public class MyConfigActivity extends BaseActivity {
	private TextView mUser;  //客户名称
	private TextView mPhone; //客户电话
	private QcApp qcApp;
	private ImageButton btn_back;
		
	public static MyConfigActivity _instance = null; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myconfig);
     		
        _instance = this;
        qcApp = (QcApp)getApplication();
        
        mUser = (TextView)findViewById(R.id.user_name);
        mPhone = (TextView)findViewById(R.id.user_mobile);
        
        mUser.setText(qcApp.getUserName());
        mPhone.setText(qcApp.getUserPhone());
        
      //close button
  		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
  		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
              public void onClick(View v) {    
            	  MyConfigActivity.this.finish();
              }    
          });

    }
    
    public void reLogin(View arg0)
	{
		Intent intent = new Intent();
		intent.putExtra("isReLogin", true);
		intent.setClass(MyConfigActivity.this,LoginActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		MyConfigActivity.this.finish();

	}

		
	
}
