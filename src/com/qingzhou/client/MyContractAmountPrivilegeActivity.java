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
 * 我的合同-金额-优惠
 * @author hihi
 *
 */
public class MyContractAmountPrivilegeActivity extends BaseActivity {

	private QcApp qcApp;
	private ImageButton btn_back;
	private ContractAmount contractAmount;
	List<ContractDiscount> contractDiscountList;
	private TextView countPrivilege;
	private TextView handItemPrivilege;
	private TextView mainItemPrivilege;
	private TextView managePrivilege;
	private TextView designPrivilege;
	private TextView otherPrivilege;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycontract_favorable);
		//setContentView(R.layout.mycontract_amount_privilege);
		
		qcApp = (QcApp)getApplication();
		contractAmount = qcApp.getContract().getContractAmount();
		contractDiscountList = qcApp.getContract().getContractList();
		
		initFavorable();
		//initPrivilege();
		
		//close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	MyContractAmountPrivilegeActivity.this.finish();
            }    
        });
	}
	
	/**
	 * 初始化优惠信息
	 * @param v
	 */
	public void initFavorable()
	{
		TextView favorable_price = (TextView) findViewById(R.id.favorable_price);
		favorable_price.setText(StringUtils.formatDecimal(contractAmount.getCountPrivilege()));
		
		ListView favorablelist = (ListView) findViewById(R.id.favorablelist);
		favorablelist.setAdapter(new FavorableViewAdapter(this,contractDiscountList));
	}
	
	/**
	 * 初始化优惠信息,非优惠明细
	 */
	public void initPrivilege()
	{
		countPrivilege = (TextView) findViewById(R.id.countPrivilege);
		handItemPrivilege = (TextView) findViewById(R.id.handItemPrivilege);
		mainItemPrivilege = (TextView) findViewById(R.id.mainItemPrivilege);
		managePrivilege = (TextView) findViewById(R.id.managePrivilege);
		designPrivilege = (TextView) findViewById(R.id.designPrivilege);
		otherPrivilege = (TextView) findViewById(R.id.otherPrivilege);
		
		countPrivilege.setText(StringUtils.formatDecimal(contractAmount.getCountPrivilege()));
		handItemPrivilege.setText(StringUtils.formatDecimal(contractAmount.getHandItemPrivilege()));
		mainItemPrivilege.setText(StringUtils.formatDecimal(contractAmount.getMainItemPrivilege()));
		managePrivilege.setText(StringUtils.formatDecimal(contractAmount.getManagePrivilege()));
		designPrivilege.setText(StringUtils.formatDecimal(contractAmount.getDesignPrivilege()));
		otherPrivilege.setText(StringUtils.formatDecimal(contractAmount.getOtherPrivilege()));
	}
	
	
	
	
}
