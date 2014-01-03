package com.qingzhou.client;

import java.util.Map;

import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.domain.ContractPayment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的合同-金额-客户付款情况
 * 
 * @author hihi
 * 
 */
public class MyContractAmountPaidInfoActivity extends BaseActivity {

	private QcApp qcApp;
	private ImageButton btn_back;
	private Map<String, ContractPayment> cpMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycontract_amount_paiyinfo);

		qcApp = (QcApp) getApplication();
		cpMap = qcApp.getContract().getCpMap();

		initTotal();
		initDesignBargain();
		initContractBargain();
		initDesign();
		initBaseManage();
		initBaseDownpayment();
		initBaseMedium();
		initBaseMoreorless();
		initBaseMoreorlessManage();
		initBaseMoreorlessDesign();
		initBaseFinalpayment();
		initMainProject();
		initMainMoreorless();
		initOther();

		// close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener() {// 创建监听
			public void onClick(View v) {
				MyContractAmountPaidInfoActivity.this.finish();
			}
		});
	}

	/**
	 * 初始化合计
	 */
	private void initTotal() {
		TextView total_contract = (TextView) findViewById(R.id.total_contract);
		TextView total_paid = (TextView) findViewById(R.id.total_paid);
		TextView total_nopaid = (TextView) findViewById(R.id.total_nopaid);

		total_contract.setText(String.format(
				getResources().getString(R.string.payable_title),
				StringUtils.formatDecimal(cpMap.get("16").getContractMoney()
						.trim())));
		total_paid
				.setText(String.format(
						getResources().getString(R.string.paid_title),
						StringUtils.formatDecimal(cpMap.get("16").getPayMoney()
								.trim())));
		total_nopaid.setText(String.format(
				getResources().getString(R.string.nopaid_title),
				StringUtils.formatDecimal(cpMap.get("16").getNoPayMoney()
						.trim())));
		if (Double.parseDouble(cpMap.get("16").getNoPayMoney()) > 0)
			total_nopaid.setTextColor(getResources().getColor(R.color.red));
	}
	
	/**
	 * 初始化设计定金
	 */
	private void initDesignBargain()
	{
		TextView design_bargain_contract = (TextView) findViewById(R.id.design_bargain_contract);
		TextView design_bargain_paid = (TextView) findViewById(R.id.design_bargain_paid);
		TextView design_bargain_nopid = (TextView) findViewById(R.id.design_bargain_nopid);

		design_bargain_contract.setText(String.format(
				getResources().getString(R.string.payable_title),
				StringUtils.formatDecimal(cpMap.get("01").getContractMoney()
						.trim())));
		design_bargain_paid
				.setText(String.format(
						getResources().getString(R.string.paid_title),
						StringUtils.formatDecimal(cpMap.get("01").getPayMoney()
								.trim())));
		design_bargain_nopid.setText(String.format(
				getResources().getString(R.string.nopaid_title),
				StringUtils.formatDecimal(cpMap.get("01").getNoPayMoney()
						.trim())));
		if (Double.parseDouble(cpMap.get("01").getNoPayMoney()) > 0)
			design_bargain_nopid.setTextColor(getResources().getColor(R.color.red));
	}
	
	/**
	 * 初始化合同定金
	 */
	private void initContractBargain()
	{
		TextView contract_bargain_contract = (TextView) findViewById(R.id.contract_bargain_contract);
		TextView contract_bargain_paid = (TextView) findViewById(R.id.contract_bargain_paid);
		TextView contract_bargain_nopid = (TextView) findViewById(R.id.contract_bargain_nopid);

		contract_bargain_contract.setText(String.format(
				getResources().getString(R.string.payable_title),
				StringUtils.formatDecimal(cpMap.get("02").getContractMoney()
						.trim())));
		contract_bargain_paid
				.setText(String.format(
						getResources().getString(R.string.paid_title),
						StringUtils.formatDecimal(cpMap.get("02").getPayMoney()
								.trim())));
		contract_bargain_nopid.setText(String.format(
				getResources().getString(R.string.nopaid_title),
				StringUtils.formatDecimal(cpMap.get("02").getNoPayMoney()
						.trim())));
		if (Double.parseDouble(cpMap.get("02").getNoPayMoney()) > 0)
			contract_bargain_nopid.setTextColor(getResources().getColor(R.color.red));
	}
	
	/**
	 * 初始化设计费
	 */
	private void initDesign()
	{
		TextView design_contract = (TextView) findViewById(R.id.design_contract);
		TextView design_paid = (TextView) findViewById(R.id.design_paid);
		TextView design_nopid = (TextView) findViewById(R.id.design_nopid);

		design_contract.setText(String.format(
				getResources().getString(R.string.payable_title),
				StringUtils.formatDecimal(cpMap.get("10").getContractMoney()
						.trim())));
		design_paid
				.setText(String.format(
						getResources().getString(R.string.paid_title),
						StringUtils.formatDecimal(cpMap.get("10").getPayMoney()
								.trim())));
		design_nopid.setText(String.format(
				getResources().getString(R.string.nopaid_title),
				StringUtils.formatDecimal(cpMap.get("10").getNoPayMoney()
						.trim())));
		if (Double.parseDouble(cpMap.get("10").getNoPayMoney()) > 0)
			design_nopid.setTextColor(getResources().getColor(R.color.red));
	}
	/**
	 * 初始化基础工程管理费
	 */
	private void initBaseManage()
	{
		TextView base_manage_contract = (TextView) findViewById(R.id.base_manage_contract);
		TextView base_manage_paid = (TextView) findViewById(R.id.base_manage_paid);
		TextView base_manage_nopaid = (TextView) findViewById(R.id.base_manage_nopaid);

		base_manage_contract.setText(String.format(
				getResources().getString(R.string.payable_title),
				StringUtils.formatDecimal(cpMap.get("05").getContractMoney()
						.trim())));
		base_manage_paid
				.setText(String.format(
						getResources().getString(R.string.paid_title),
						StringUtils.formatDecimal(cpMap.get("05").getPayMoney()
								.trim())));
		base_manage_nopaid.setText(String.format(
				getResources().getString(R.string.nopaid_title),
				StringUtils.formatDecimal(cpMap.get("05").getNoPayMoney()
						.trim())));
		if (Double.parseDouble(cpMap.get("05").getNoPayMoney()) > 0)
			base_manage_nopaid.setTextColor(getResources().getColor(R.color.red));
	}
	/**
	 * 初始化基础工程首期款
	 */
	private void initBaseDownpayment()
	{
		TextView base_downpayment_contract = (TextView) findViewById(R.id.base_downpayment_contract);
		TextView base_downpayment_paid = (TextView) findViewById(R.id.base_downpayment_paid);
		TextView base_downpayment_nopaid = (TextView) findViewById(R.id.base_downpayment_nopaid);

		base_downpayment_contract.setText(String.format(
				getResources().getString(R.string.payable_title),
				StringUtils.formatDecimal(cpMap.get("03").getContractMoney()
						.trim())));
		base_downpayment_paid
				.setText(String.format(
						getResources().getString(R.string.paid_title),
						StringUtils.formatDecimal(cpMap.get("03").getPayMoney()
								.trim())));
		base_downpayment_nopaid.setText(String.format(
				getResources().getString(R.string.nopaid_title),
				StringUtils.formatDecimal(cpMap.get("03").getNoPayMoney()
						.trim())));
		if (Double.parseDouble(cpMap.get("03").getNoPayMoney()) > 0)
			base_downpayment_nopaid.setTextColor(getResources().getColor(R.color.red));
	}
	
	/**
	 * 初始化基础工程中期款
	 */
	private void initBaseMedium()
	{
		TextView base_medium_contract = (TextView) findViewById(R.id.base_medium_contract);
		TextView base_medium_paid = (TextView) findViewById(R.id.base_medium_paid);
		TextView base_medium_nopaid = (TextView) findViewById(R.id.base_medium_nopaid);

		base_medium_contract.setText(String.format(
				getResources().getString(R.string.payable_title),
				StringUtils.formatDecimal(cpMap.get("04").getContractMoney()
						.trim())));
		base_medium_paid
				.setText(String.format(
						getResources().getString(R.string.paid_title),
						StringUtils.formatDecimal(cpMap.get("04").getPayMoney()
								.trim())));
		base_medium_nopaid.setText(String.format(
				getResources().getString(R.string.nopaid_title),
				StringUtils.formatDecimal(cpMap.get("04").getNoPayMoney()
						.trim())));
		if (Double.parseDouble(cpMap.get("04").getNoPayMoney()) > 0)
			base_medium_nopaid.setTextColor(getResources().getColor(R.color.red));
	}
	
	/**
	 * 初始化基础增减项款
	 */
	private void initBaseMoreorless()
	{
		TextView base_moreorless_contract = (TextView) findViewById(R.id.base_moreorless_contract);
		TextView base_moreorless_paid = (TextView) findViewById(R.id.base_moreorless_paid);
		TextView base_moreorless_nopaid = (TextView) findViewById(R.id.base_moreorless_nopaid);

		base_moreorless_contract.setText(String.format(
				getResources().getString(R.string.payable_title),
				StringUtils.formatDecimal(cpMap.get("13").getContractMoney()
						.trim())));
		base_moreorless_paid
				.setText(String.format(
						getResources().getString(R.string.paid_title),
						StringUtils.formatDecimal(cpMap.get("13").getPayMoney()
								.trim())));
		base_moreorless_nopaid.setText(String.format(
				getResources().getString(R.string.nopaid_title),
				StringUtils.formatDecimal(cpMap.get("13").getNoPayMoney()
						.trim())));
		if (Double.parseDouble(cpMap.get("13").getNoPayMoney()) > 0)
			base_moreorless_nopaid.setTextColor(getResources().getColor(R.color.red));
	}
	
	/**
	 * 初始化基础增减项管理费
	 */
	private void initBaseMoreorlessManage()
	{
		TextView base_moreorless_manage_contract = (TextView) findViewById(R.id.base_moreorless_manage_contract);
		TextView base_moreorless_manage_paid = (TextView) findViewById(R.id.base_moreorless_manage_paid);
		TextView base_moreorless_manage_nopaid = (TextView) findViewById(R.id.base_moreorless_manage_nopaid);

		base_moreorless_manage_contract.setText(String.format(
				getResources().getString(R.string.payable_title),
				StringUtils.formatDecimal(cpMap.get("09").getContractMoney()
						.trim())));
		base_moreorless_manage_paid
				.setText(String.format(
						getResources().getString(R.string.paid_title),
						StringUtils.formatDecimal(cpMap.get("09").getPayMoney()
								.trim())));
		base_moreorless_manage_nopaid.setText(String.format(
				getResources().getString(R.string.nopaid_title),
				StringUtils.formatDecimal(cpMap.get("09").getNoPayMoney()
						.trim())));
		if (Double.parseDouble(cpMap.get("09").getNoPayMoney()) > 0)
			base_moreorless_manage_nopaid.setTextColor(getResources().getColor(R.color.red));
	}
	
	/**
	 * 初始化基础增减项设计费
	 */
	private void initBaseMoreorlessDesign()
	{
		TextView base_moreorless_design_contract = (TextView) findViewById(R.id.base_moreorless_design_contract);
		TextView base_moreorless_design_paid = (TextView) findViewById(R.id.base_moreorless_design_paid);
		TextView base_moreorless_design_nopaid = (TextView) findViewById(R.id.base_moreorless_design_nopaid);

		base_moreorless_design_contract.setText(String.format(
				getResources().getString(R.string.payable_title),
				StringUtils.formatDecimal(cpMap.get("11").getContractMoney()
						.trim())));
		base_moreorless_design_paid
				.setText(String.format(
						getResources().getString(R.string.paid_title),
						StringUtils.formatDecimal(cpMap.get("11").getPayMoney()
								.trim())));
		base_moreorless_design_nopaid.setText(String.format(
				getResources().getString(R.string.nopaid_title),
				StringUtils.formatDecimal(cpMap.get("11").getNoPayMoney()
						.trim())));
		if (Double.parseDouble(cpMap.get("11").getNoPayMoney()) > 0)
			base_moreorless_design_nopaid.setTextColor(getResources().getColor(R.color.red));
	}
	
	/**
	 * 初始化基础工程尾款
	 */
	private void initBaseFinalpayment()
	{
		TextView base_finalpayment_contract = (TextView) findViewById(R.id.base_finalpayment_contract);
		TextView base_finalpayment_paid = (TextView) findViewById(R.id.base_finalpayment_paid);
		TextView base_finalpayment_nopaid = (TextView) findViewById(R.id.base_finalpayment_nopaid);

		base_finalpayment_contract.setText(String.format(
				getResources().getString(R.string.payable_title),
				StringUtils.formatDecimal(cpMap.get("06").getContractMoney()
						.trim())));
		base_finalpayment_paid
				.setText(String.format(
						getResources().getString(R.string.paid_title),
						StringUtils.formatDecimal(cpMap.get("06").getPayMoney()
								.trim())));
		base_finalpayment_nopaid.setText(String.format(
				getResources().getString(R.string.nopaid_title),
				StringUtils.formatDecimal(cpMap.get("06").getNoPayMoney()
						.trim())));
		if (Double.parseDouble(cpMap.get("06").getNoPayMoney()) > 0)
			base_finalpayment_nopaid.setTextColor(getResources().getColor(R.color.red));
	}

	/**
	 * 初始化主材项目款
	 */
	private void initMainProject()
	{
		TextView main_project_contract = (TextView) findViewById(R.id.main_project_contract);
		TextView main_project_paid = (TextView) findViewById(R.id.main_project_paid);
		TextView main_project_nopaid = (TextView) findViewById(R.id.main_project_nopaid);

		main_project_contract.setText(String.format(
				getResources().getString(R.string.payable_title),
				StringUtils.formatDecimal(cpMap.get("07").getContractMoney()
						.trim())));
		main_project_paid
				.setText(String.format(
						getResources().getString(R.string.paid_title),
						StringUtils.formatDecimal(cpMap.get("07").getPayMoney()
								.trim())));
		main_project_nopaid.setText(String.format(
				getResources().getString(R.string.nopaid_title),
				StringUtils.formatDecimal(cpMap.get("07").getNoPayMoney()
						.trim())));
		if (Double.parseDouble(cpMap.get("07").getNoPayMoney()) > 0)
			main_project_nopaid.setTextColor(getResources().getColor(R.color.red));
	}
	
	/**
	 * 初始化主材增减款项
	 */
	private void initMainMoreorless()
	{
		TextView main_moreorless_contract = (TextView) findViewById(R.id.main_moreorless_contract);
		//TextView main_moreorless_paid = (TextView) findViewById(R.id.main_moreorless_paid);
		//TextView main_moreorless_nopaid = (TextView) findViewById(R.id.main_moreorless_nopaid);

		main_moreorless_contract.setText(String.format(
				getResources().getString(R.string.updownprice_title),
				StringUtils.formatDecimal(cpMap.get("14").getContractMoney()
						.trim())));
//		main_moreorless_paid
//				.setText(String.format(
//						getResources().getString(R.string.paid_title),
//						StringUtils.formatDecimal(cpMap.get("14").getPayMoney()
//								.trim())));
//		main_moreorless_nopaid.setText(String.format(
//				getResources().getString(R.string.nopaid_title),
//				StringUtils.formatDecimal(cpMap.get("14").getNoPayMoney()
//						.trim())));
//		if (Double.parseDouble(cpMap.get("14").getNoPayMoney()) > 0)
//			main_moreorless_nopaid.setTextColor(getResources().getColor(R.color.red));
	}
	
	/**
	 * 初始化其他款项
	 */
	private void initOther()
	{
		TextView other_contract = (TextView) findViewById(R.id.other_contract);
		TextView other_paid = (TextView) findViewById(R.id.other_paid);
		TextView other_nopaid = (TextView) findViewById(R.id.other_nopaid);

		other_contract.setText(String.format(
				getResources().getString(R.string.payable_title),
				StringUtils.formatDecimal(cpMap.get("12").getContractMoney()
						.trim())));
		other_paid
				.setText(String.format(
						getResources().getString(R.string.paid_title),
						StringUtils.formatDecimal(cpMap.get("12").getPayMoney()
								.trim())));
		other_nopaid.setText(String.format(
				getResources().getString(R.string.nopaid_title),
				StringUtils.formatDecimal(cpMap.get("12").getNoPayMoney()
						.trim())));
		if (Double.parseDouble(cpMap.get("12").getNoPayMoney()) > 0)
			other_nopaid.setTextColor(getResources().getColor(R.color.red));
	}
}
