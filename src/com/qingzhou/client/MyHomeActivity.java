package com.qingzhou.client;

import java.util.List;

import com.qingzhou.client.common.GlobalParameter;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.domain.RestProjectPlan;
import com.qingzhou.client.domain.RestProjectPlanDetail;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


/**
 * 我的家装活动
 * @author hihi
 *
 */
public class MyHomeActivity extends Activity {

	private QcApp qcApp;
	private RestProjectPlan rpp;
	private TextView project_name;
	private TextView project_explain;
	private ListView processListView;
	private Button btn_exchange;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myhome);
		qcApp = (QcApp)getApplication();
		rpp = qcApp.getProjectPlan();
		
		project_name = (TextView)findViewById(R.id.project_name);
		project_explain = (TextView)findViewById(R.id.project_explain);
		processListView = (ListView) findViewById(R.id.processLevel1List);
		project_name.setText(qcApp.getUserBase().getReg_village_name());
		project_explain.setText(getExplain());
		
		btn_exchange = (Button)findViewById(R.id.btn_exchange);
		
		btn_exchange.setOnClickListener(new View.OnClickListener() {  
			            @Override  
			            public void onClick(View arg0) {  
			            	start_exchange();
			            }  
			        });  

		
		initProcessList(rpp.getProjectPlanDetailList());
	}
	
	/**
	 * 获取项目说明
	 * @return
	 */
	private String getExplain()
	{
		String strExplain = "";
		switch(rpp.getPlanStatus())
        {
        case GlobalParameter.PROJECT_FINISH:
        	strExplain = getResources().getString(R.string.project_finish);
        	break;
        case GlobalParameter.PROJECT_NORMAL:
        	strExplain = String.format(getResources().getString(R.string.project_normal),rpp.getCurrPlanName());
        	break;
        case GlobalParameter.PROJECT_DEFER:
        	strExplain = String.format(getResources().getString(R.string.project_delay),rpp.getCurrPlanName());
        	break;
        default:
        	strExplain = "未知工程进度";
        }
		return strExplain;
	}
	
	/**
	 * 初始化一级工程进度
	 * @param processList
	 */
	private void initProcessList(List<RestProjectPlanDetail> processList)
	{
		processListView.setAdapter(new ProcessListViewAdapter(this,processList));
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
				    intent.setClass(MyHomeActivity.this,MyHomeLevel2Activity.class);
				    startActivity(intent);
				}
				
			}
		});

	}
	
	/**
	 * 我的合同點擊事件
	 */
	public void start_mycontract(View v)
	{
	    Intent intent = new Intent();
		intent.putExtra("FLAG", GlobalParameter.INIT_CONTRACT);
	    intent.setClass(MyHomeActivity.this,LoadingActivity.class);
	    startActivity(intent);
	}
	
	/**
	 * 工程溝通點擊事件
	 */
	public void start_exchange()
	{
		Builder builder = new AlertDialog.Builder(MyHomeActivity.this);
		builder.setIcon(R.drawable.cm);
		builder.setAdapter(new CMListAdapter(MyHomeActivity.this,qcApp.getUserBase()),null);
		builder.setTitle(getResources().getString(R.string.project_exchange));
		builder.create().show();  
	}
	

	
	public void stylist_phone_onclick(View v)
	{
		action_call(qcApp.getUserBase().getReg_stylist_mobile());
	}
	
	public void project_mgr_phone_onclick(View v)
	{
		action_call(qcApp.getUserBase().getReg_project_mgr_mobile());
	}
	
	public void customer_mgr_phone_onclick(View v)
	{
		action_call(qcApp.getUserBase().getReg_customer_mgr_mobile());
	}
	
	/**
	 * 拨打电话
	 * @param strPhone
	 */
	public void action_call(String strPhone)
	{
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+strPhone));
		// 将意图传给操作系统
		// startActivity方法专门将意图传给操作系统
		MyHomeActivity.this.startActivity(intent);
	}
	
	public void stylist_message_onclick(View v)
	{
		qcApp.getUserBase().getReg_stylist();
	}
	
	public void project_mgr_message_onclick(View v)
	{
		qcApp.getUserBase().getReg_project_mgr();
	}
	
	public void customer_mgr_message_onclick(View v)
	{
		qcApp.getUserBase().getReg_customer_mgr();
	}
	
	/**
	 * 到我的消息界面
	 * @param worker
	 */
	private void toMessage(String worker)
	{
		Intent intent = new Intent();
		intent.putExtra("WORKER", worker);
		intent.putExtra("FLAG", 0);
	    intent.setClass(MyHomeActivity.this,LoadingActivity.class);
	    startActivity(intent);
	}
	
	
	
}
