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
import android.util.DisplayMetrics;
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
public class MyHomeGeneralizeActivity extends BaseActivity {

	private QcApp qcApp;
	private RestProjectPlan rpp;
	private TextView project_name;
	private TextView project_explain;
	private TextView previous_title;
	private TextView previous_value;
	private TextView next_title;
	private TextView next_value;
	private TextView base_detail_title;
	private TextView base_detail_value;
	private TextView main_detail_title;
	private TextView main_detail_value;
	
	private Button btn_exchange;
	private Button btn_mycon;
	private ImageButton btn_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myhome_generalize);
		qcApp = (QcApp)getApplication();
		rpp = qcApp.getProjectPlan();
		
		previous_title = (TextView)findViewById(R.id.previous_title);
		previous_value = (TextView)findViewById(R.id.previous_value);
		next_title = (TextView)findViewById(R.id.next_title);
		next_value = (TextView)findViewById(R.id.next_value);
		base_detail_title = (TextView)findViewById(R.id.base_detail_title);
		base_detail_value = (TextView)findViewById(R.id.base_detail_value);
		main_detail_title = (TextView)findViewById(R.id.main_detail_title);
		main_detail_value = (TextView)findViewById(R.id.main_detail_value);
		
		base_detail_value.setText(StringUtils.formatDecimal(rpp.getBase_detail_value()));
		main_detail_value.setText(StringUtils.formatDecimal(rpp.getMain_detail_value()));
		
		
		project_name = (TextView)findViewById(R.id.project_name);
		project_explain = (TextView)findViewById(R.id.project_explain);
		project_name.setText(qcApp.getUserBase().getReg_village_name());
		project_explain.setText(getExplain());
		
		initGeneralize();
		
		btn_exchange = (Button)findViewById(R.id.btn_exchange);
		btn_mycon = (Button)findViewById(R.id.btn_mycon);
		
		btn_exchange.setOnClickListener(new View.OnClickListener() {  
			            @Override  
			            public void onClick(View arg0) {  
			            	start_exchange();
			            }  
			        }); 
		
	
		//close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	MyHomeGeneralizeActivity.this.finish();
            }    
        });

	}
	
	/**
	 * 页面显示控制，根据不同的工程进度
	 */
	private void initGeneralize()
	{
		//如工程已竣工不显示下一阶段及明细
		if (rpp.getPlanStatus() == Constants.PROJECT_FINISH)
		{
			previous_value.setText(rpp.getProjectPlanDetailList().get(rpp.getCurrID()).getProject_process_name());
			next_title.setVisibility(View.GONE);
        	next_value.setVisibility(View.GONE);
        	base_detail_title.setVisibility(View.GONE);
        	base_detail_value.setVisibility(View.GONE);
        	main_detail_title.setVisibility(View.GONE);
        	main_detail_value.setVisibility(View.GONE);
		
		}else
			//未知进度则什么都不显示
		if (rpp.getPlanStatus() != Constants.PROJECT_NORMAL && rpp.getPlanStatus() != Constants.PROJECT_DEFER)
		{
			previous_title.setVisibility(View.GONE);
			previous_value.setVisibility(View.GONE);
			next_title.setVisibility(View.GONE);
        	next_value.setVisibility(View.GONE);
        	base_detail_title.setVisibility(View.GONE);
        	base_detail_value.setVisibility(View.GONE);
        	main_detail_title.setVisibility(View.GONE);
        	main_detail_value.setVisibility(View.GONE);
		}
		else
		//如当前进度为最后一阶段时，下一阶段不显示
		if (rpp.getCurrID() == rpp.getProjectPlanDetailList().size()-1)
		{
			next_title.setVisibility(View.GONE);
        	next_value.setVisibility(View.GONE);
        	
        	previous_value.setText(rpp.getProjectPlanDetailList().get(rpp.getCurrID()-1).getProject_process_name());
		}else
		//如当前进度为第一阶段时，前一阶段不显示
		if (rpp.getCurrID() == 0)
		{
			previous_title.setVisibility(View.GONE);
			previous_value.setVisibility(View.GONE);
			
			next_value.setText(rpp.getProjectPlanDetailList().get(rpp.getCurrID()+1).getProject_process_name());
		}else
		{
			previous_value.setText(rpp.getProjectPlanDetailList().get(rpp.getCurrID()-1).getProject_process_name());
			next_value.setText(rpp.getProjectPlanDetailList().get(rpp.getCurrID()+1).getProject_process_name());
		}
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
        case Constants.PROJECT_FINISH:
        	strExplain = getResources().getString(R.string.project_finish);
        	break;
        case Constants.PROJECT_NORMAL:
        	strExplain = String.format(getResources().getString(R.string.project_normal),rpp.getCurrPlanName(),rpp.getTime_limit());
        	break;
        case Constants.PROJECT_DEFER:
        	strExplain = String.format(getResources().getString(R.string.project_delay),rpp.getCurrPlanName(),rpp.getTime_limit());
        	break;
        default:
        	strExplain = "未知工程进度";
        }
		return strExplain;
	}
	
	/**
	 * 查看工程进度列表
	 * @param v
	 */
	public void goProcess(View v)
	{
		Intent intent = new Intent();
		intent.setClass(MyHomeGeneralizeActivity.this,MyHomeActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}
	
	/**
	 * 我的合同點擊事件
	 */
	public void start_mycontract(View v)
	{
	    Intent intent = new Intent();
		intent.putExtra("FLAG", Constants.INIT_CONTRACT);
	    intent.setClass(MyHomeGeneralizeActivity.this,LoadingActivity.class);
	    startActivity(intent);
	}
	
	/**
	 * 工程联络人点击事件
	 */
	private void start_exchange()
	{
		DialogUtils.showAddressBookDialog(MyHomeGeneralizeActivity.this, qcApp.getUserBase());
	}
	
	
	public void stylist_phone_onclick(View v)
	{
		DialogUtils.action_call(MyHomeGeneralizeActivity.this,qcApp.getUserBase().getReg_stylist_mobile());
	}
	
	public void project_mgr_phone_onclick(View v)
	{
		DialogUtils.action_call(MyHomeGeneralizeActivity.this,qcApp.getUserBase().getReg_project_mgr_mobile());
	}
	
	public void customer_mgr_phone_onclick(View v)
	{
		DialogUtils.action_call(MyHomeGeneralizeActivity.this,qcApp.getUserBase().getReg_customer_mgr_mobile());
	}
	
	public void stylist_message_onclick(View v)
	{
		DialogUtils.toMessage(MyHomeGeneralizeActivity.this,qcApp.getUserBase().getReg_stylist_mobile());
	}
	
	public void project_mgr_message_onclick(View v)
	{
		DialogUtils.toMessage(MyHomeGeneralizeActivity.this,qcApp.getUserBase().getReg_project_mgr_mobile());
	}
	
	public void customer_mgr_message_onclick(View v)
	{
		DialogUtils.toMessage(MyHomeGeneralizeActivity.this,qcApp.getUserBase().getReg_customer_mgr_mobile());
	}
	
	
}
