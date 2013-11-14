package com.qingzhou.client;

import java.util.List;

import com.qingzhou.client.adapter.ProcessListViewAdapter;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.domain.RestProjectPlan;
import com.qingzhou.client.domain.RestProjectPlanDetail;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
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
 * 我的家装二级列表
 * @author hihi
 *
 */
public class MyHomeLevel2Activity extends BaseActivity {

	private QcApp qcApp;
	private RestProjectPlan rpp;
	private ListView processListView;
	private int index; 
	private String title;
	private TextView process_title;
	private ImageButton btn_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myhomelevel2list);
		qcApp = (QcApp)getApplication();
		rpp = qcApp.getProjectPlan();		
		
		//close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
		      public void onClick(View v) {    
		    	  MyHomeLevel2Activity.this.finish();
		      }    
		});
				
		processListView = (ListView) findViewById(R.id.processLevel2List);
		process_title = (TextView) findViewById(R.id.process_title);
		
		Intent intent = getIntent();
		index = intent.getIntExtra("INDEX",0);	
		title = intent.getStringExtra("TITLE");
		
		process_title.setText(title);
		initProcessList(rpp.getProjectPlanDetailList().get(index).getRestProjectPlanDetailList());
	}
	
	
	/**
	 * 初始化二级级工程进度
	 * @param processList
	 */
	private void initProcessList(List<RestProjectPlanDetail> processList)
	{
		processListView.setAdapter(new ProcessListViewAdapter(this,processList));

	}

}
