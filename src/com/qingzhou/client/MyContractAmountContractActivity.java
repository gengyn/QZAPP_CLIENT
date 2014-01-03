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
 * 我的合同-金额-合同金额
 * @author hihi
 *
 */
public class MyContractAmountContractActivity extends BaseActivity {

	private QcApp qcApp;
	private ImageButton btn_back;
	private ContractAmount contractAmount;
	private TextView countContract;
	private TextView handContract;
	private TextView mainContract;
	private TextView manageContract;
	private TextView designContract;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycontract_amount_contract);
		
		qcApp = (QcApp)getApplication();
		contractAmount = qcApp.getContract().getContractAmount();
		
		countContract = (TextView) findViewById(R.id.countContract);
		handContract = (TextView) findViewById(R.id.handContract);
		mainContract = (TextView) findViewById(R.id.mainContract);
		manageContract = (TextView) findViewById(R.id.manageContract);
		designContract = (TextView) findViewById(R.id.designContract);
		//赋值
		countContract.setText(StringUtils.formatDecimal(contractAmount.getCountContract()));
		handContract.setText(StringUtils.formatDecimal(contractAmount.getHandContract()));
		mainContract.setText(StringUtils.formatDecimal(contractAmount.getMainContract()));
		manageContract.setText(StringUtils.formatDecimal(contractAmount.getManageContract()));
		designContract.setText(StringUtils.formatDecimal(contractAmount.getDesignContract()));
		
		//close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	MyContractAmountContractActivity.this.finish();
            }    
        });
	}
	
	
	
	
}
