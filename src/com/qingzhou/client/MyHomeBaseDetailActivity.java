package com.qingzhou.client;

import java.util.List;

import com.qingzhou.app.utils.DialogUtils;
import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.adapter.BaseDetailListViewAdapter;
import com.qingzhou.client.adapter.ProcessListViewAdapter;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.domain.BaseDetail;
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
 * 我的家装_基础明细活动
 * @author hihi
 *
 */
public class MyHomeBaseDetailActivity extends BaseActivity {

	private ListView baseDetailListView;
	private ImageButton btn_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myhome_basedetail);
		
		baseDetailListView = (ListView) findViewById(R.id.baseDetailList);
		List<BaseDetail> baseDetailList = (List<BaseDetail>) getIntent().getSerializableExtra("BASEDETAILLIST");
		baseDetailListView.setAdapter(new BaseDetailListViewAdapter(this,baseDetailList));
		
		//close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	MyHomeBaseDetailActivity.this.finish();
            }    
        });

		
	}
	
	
}
