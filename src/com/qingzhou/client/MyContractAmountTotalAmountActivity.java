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
 * 我的合同-金额-竣工结算金额
 * @author hihi
 *
 */
public class MyContractAmountTotalAmountActivity extends BaseActivity {

	private QcApp qcApp;
	private ImageButton btn_back;
	private ContractAmount contractAmount;
	private TextView countTotalAmount;
	private TextView handTotal;
	private TextView mainTotal;
	private TextView manageTotal;
	private TextView designTotal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycontract_amount_totalamount);
		
		qcApp = (QcApp)getApplication();
		contractAmount = qcApp.getContract().getContractAmount();
		
		countTotalAmount = (TextView) findViewById(R.id.countTotalAmount);
		handTotal = (TextView) findViewById(R.id.handTotal);
		mainTotal = (TextView) findViewById(R.id.mainTotal);
		manageTotal = (TextView) findViewById(R.id.manageTotal);
		designTotal = (TextView) findViewById(R.id.designTotal);
		//赋值
		countTotalAmount.setText(StringUtils.formatDecimal(contractAmount.getCountTotalAmount()));
		handTotal.setText(StringUtils.formatDecimal(contractAmount.getHandTotal()));
		mainTotal.setText(StringUtils.formatDecimal(contractAmount.getMainTotal()));
		manageTotal.setText(StringUtils.formatDecimal(contractAmount.getManageTotal()));
		designTotal.setText(StringUtils.formatDecimal(contractAmount.getDesignTotal()));
		
		//close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	MyContractAmountTotalAmountActivity.this.finish();
            }    
        });
	}
	
	
	
	
}
