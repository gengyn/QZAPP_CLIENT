package com.qingzhou.client;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.qingzhou.client.adapter.MyInfoViewAdapter;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.domain.Myinfo;

/**
 * 资讯活动
 * 
 * @author hihi
 * 
 */
public class MyInfoTempActivity extends BaseActivity {

	private QcApp qcApp;
	private ListView infoListView;
	List<Myinfo> mInfo = new ArrayList<Myinfo>();
	
	private ImageButton btn_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.myinfo);
		qcApp = (QcApp)getApplication();
		
		//close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	MyInfoTempActivity.this.finish();
            }    
        });
		initInfoList();
	}

	
	/**
	 * 初始化资讯
	 */
	private void initInfoList() {
				
		infoListView = (ListView) findViewById(R.id.infotemp_list);
		mInfo = qcApp.getInfoList();
		infoListView.setAdapter(new MyInfoViewAdapter(this, mInfo));

		//加入单击事件
		infoListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Intent intent = new Intent (MyInfoTempActivity.this,MyInfoContentActivity.class);	
				Myinfo myinfo = (Myinfo)mInfo.get(arg2);
				intent.putExtra("MYINFO", myinfo);
				startActivity(intent);
			}

		});
	}
}
