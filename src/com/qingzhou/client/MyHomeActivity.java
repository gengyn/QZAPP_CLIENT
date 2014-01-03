package com.qingzhou.client;

import java.util.List;

import com.qingzhou.app.utils.DialogUtils;
import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.adapter.ProcessListViewAdapter;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.domain.RestProjectPlan;
import com.qingzhou.client.domain.RestProjectPlanDetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


/**
 * 我的家装活动
 * @author hihi
 *
 */
public class MyHomeActivity extends BaseActivity {

	private QcApp qcApp;
	private RestProjectPlan rpp;
	private ListView processListView;
	private ImageButton btn_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myhome);
		qcApp = (QcApp)getApplication();
		rpp = qcApp.getProjectPlan();
		

		processListView = (ListView) findViewById(R.id.processLevel1List);
		
		
		//close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	MyHomeActivity.this.finish();
            }    
        });

		
		initProcessList(rpp.getProjectPlanDetailList());
	}

	
	/**
	 * 初始化一级工程进度
	 * @param processList
	 */
	private void initProcessList(List<RestProjectPlanDetail> processList)
	{
		processListView.setAdapter(new ProcessListViewAdapter(this,processList));
		processListView.setSelection(rpp.getCurrID());
		processListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(getBaseContext(), "selectd:" +arg2 +" id:"+arg3, Toast.LENGTH_LONG).show();
				if (rpp.getProjectPlanDetailList().get(arg2).getRestProjectPlanDetailList().size()>0)
				{
					Intent intent = new Intent();
					intent.putExtra("INDEX", arg2);
					intent.putExtra("TITLE", rpp.getProjectPlanDetailList().get(arg2).getProject_process_name());
				    intent.setClass(MyHomeActivity.this,MyHomeLevel2Activity.class);
				    startActivity(intent);
				}
				
			}
		});

	}
	
	
}
