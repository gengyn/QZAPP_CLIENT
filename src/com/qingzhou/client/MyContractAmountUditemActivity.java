package com.qingzhou.client;

import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.domain.ContractAmount;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的合同-金额-增减项金额
 * @author hihi
 *
 */
public class MyContractAmountUditemActivity extends BaseActivity {

	private QcApp qcApp;
	private ImageButton btn_back;
	private ContractAmount contractAmount;
	private TextView countUdItem;
	private TextView handUdItem;
	private TextView mainUdItem;
	private TextView manageUdItem;
	private TextView designUdItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycontract_amount_uditem);
		
		qcApp = (QcApp)getApplication();
		contractAmount = qcApp.getContract().getContractAmount();
		
		countUdItem = (TextView) findViewById(R.id.countUdItem);
		handUdItem = (TextView) findViewById(R.id.handUdItem);
		mainUdItem = (TextView) findViewById(R.id.mainUdItem);
		manageUdItem = (TextView) findViewById(R.id.manageUdItem);
		designUdItem = (TextView) findViewById(R.id.designUdItem);
		//赋值
		countUdItem.setText(StringUtils.formatDecimal(contractAmount.getCountUdItem()));
		handUdItem.setText(StringUtils.formatDecimal(contractAmount.getHandUdItem()));
		mainUdItem.setText(StringUtils.formatDecimal(contractAmount.getMainUdItem()));
		manageUdItem.setText(StringUtils.formatDecimal(contractAmount.getManageUdItem()));
		designUdItem.setText(StringUtils.formatDecimal(contractAmount.getDesignUdItem()));
		
		//close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	MyContractAmountUditemActivity.this.finish();
            }    
        });
	}
	
	
	
	
}
