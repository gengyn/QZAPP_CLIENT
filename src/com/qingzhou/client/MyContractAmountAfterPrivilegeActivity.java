package com.qingzhou.client;

import java.util.List;

import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.adapter.FavorableViewAdapter;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.domain.ContractAmount;
import com.qingzhou.client.domain.ContractDiscount;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的合同-金额-后期优惠
 * @author hihi
 *
 */
public class MyContractAmountAfterPrivilegeActivity extends BaseActivity {

	private QcApp qcApp;
	private ImageButton btn_back;
	private ContractAmount contractAmount;
	List<ContractDiscount> contractDiscountList;
	private TextView countAfterPrivilege;
	private TextView handItemAfterPrivilege;
	private TextView mainItemAfterPrivilege;
	private TextView manageAfterPrivilege;
	private TextView designAfterPrivilege;
	private TextView otherAfterPrivilege;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycontract_amount_afterprivilege);
		
		qcApp = (QcApp)getApplication();
		contractAmount = qcApp.getContract().getContractAmount();

		countAfterPrivilege = (TextView) findViewById(R.id.countAfterPrivilege);
		handItemAfterPrivilege = (TextView) findViewById(R.id.handItemAfterPrivilege);
		mainItemAfterPrivilege = (TextView) findViewById(R.id.mainItemAfterPrivilege);
		manageAfterPrivilege = (TextView) findViewById(R.id.manageAfterPrivilege);
		designAfterPrivilege = (TextView) findViewById(R.id.designAfterPrivilege);
		otherAfterPrivilege = (TextView) findViewById(R.id.otherAfterPrivilege);
		
		countAfterPrivilege.setText(StringUtils.formatDecimal(contractAmount.getCountAfterPrivilege()));
		handItemAfterPrivilege.setText(StringUtils.formatDecimal(contractAmount.getHandItemAfterPrivilege()));
		mainItemAfterPrivilege.setText(StringUtils.formatDecimal(contractAmount.getMainItemAfterPrivilege()));
		manageAfterPrivilege.setText(StringUtils.formatDecimal(contractAmount.getManageAfterPrivilege()));
		designAfterPrivilege.setText(StringUtils.formatDecimal(contractAmount.getDesignAfterPrivilege()));
		otherAfterPrivilege.setText(StringUtils.formatDecimal(contractAmount.getOtherAfterPrivilege()));
		
		//close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	MyContractAmountAfterPrivilegeActivity.this.finish();
            }    
        });
	}
	

	
	
	
	
}
