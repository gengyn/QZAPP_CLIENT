package com.qingzhou.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.qingzhou.app.utils.HttpUtils;
import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.app.utils.ThreadPoolUtils;
import com.qingzhou.app.widget.PullToRefreshListView;
import com.qingzhou.client.adapter.MyInfoViewAdapter;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.common.RestService;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.myinfo);
		qcApp = (QcApp)getApplication();
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
