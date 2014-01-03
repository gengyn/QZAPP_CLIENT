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
 * 我的合同-金额-报价金额
 * @author hihi
 *
 */
public class MyContractAmountOriginalActivity extends BaseActivity {

	private QcApp qcApp;
	private ImageButton btn_back;
	private ContractAmount contractAmount;
	private TextView quoOriginal;
	private TextView handItemOriginal;
	private TextView mainItemOriginal;
	private TextView manageOriginal;
	private TextView designOriginal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycontract_amount_original);
		
		qcApp = (QcApp)getApplication();
		contractAmount = qcApp.getContract().getContractAmount();
		
		quoOriginal = (TextView) findViewById(R.id.quoOriginal);
		handItemOriginal = (TextView) findViewById(R.id.handItemOriginal);
		mainItemOriginal = (TextView) findViewById(R.id.mainItemOriginal);
		manageOriginal = (TextView) findViewById(R.id.manageOriginal);
		designOriginal = (TextView) findViewById(R.id.designOriginal);
		//赋值
		quoOriginal.setText(StringUtils.formatDecimal(contractAmount.getQuoOriginal()));
		handItemOriginal.setText(StringUtils.formatDecimal(contractAmount.getHandItemOriginal()));
		mainItemOriginal.setText(StringUtils.formatDecimal(contractAmount.getMainItemOriginal()));
		manageOriginal.setText(StringUtils.formatDecimal(contractAmount.getManageOriginal()));
		designOriginal.setText(StringUtils.formatDecimal(contractAmount.getDesignOriginal()));
		
		//close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	MyContractAmountOriginalActivity.this.finish();
            }    
        });
	}
	
	
	
	
}
