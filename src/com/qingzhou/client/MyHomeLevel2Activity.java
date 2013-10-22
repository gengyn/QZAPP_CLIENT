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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myhomelevel2list);
		qcApp = (QcApp)getApplication();
		rpp = qcApp.getProjectPlan();		
		processListView = (ListView) findViewById(R.id.processLevel2List);
		
		Intent intent = getIntent();
		index = intent.getIntExtra("INDEX",0);	
		
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
