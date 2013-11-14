package com.qingzhou.client;

import java.util.ArrayList;
import java.util.List;

import com.qingzhou.app.utils.DialogUtils;
import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.adapter.FavorableViewAdapter;
import com.qingzhou.client.common.QcApp;

import com.qingzhou.client.domain.Contract;
import com.qingzhou.client.domain.ContractDiscount;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 资讯活动
 * 
 * @author hihi
 * 
 */
public class MyContractActivity extends BaseActivity {

	public static MyContractActivity instance = null;
	private QcApp qcApp;
	private ViewPager mTabPager;
	//private ImageView mTabImg;
	private ImageView mTab1, mTab2, mTab3, mTab4;
	private int zero = 0;
	private int currIndex = 0;
	private int one;
	private int two;
	private int three;
	
	private ImageButton btn_back;
	
		
	Contract contract;
	List<ContractDiscount> contractDiscountList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycontract);
		
		//close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	MyContractActivity.this.finish();
            }    
        });

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		instance = this;
		qcApp = (QcApp)getApplication();
		contract = qcApp.getContract();
		contractDiscountList = contract.getContractList();
		//初始化界面
		initPager();
		
		
	}
	
	/**
	 * 初始化基础数据
	 */
	public void initBaseData(View v)
	{
		TextView contract_num;
		TextView contract_type_name;
		TextView contractsigned_date;
		TextView reg_name ;
		TextView reg_phone;
		TextView reg_village_name;
		TextView reg_construct_address;
		TextView reg_customer_mgr_name;
		TextView reg_stylist_name;
		TextView reg_project_mgr_name;
		
		contract_num = (TextView) v.findViewById(R.id.contract_num);
		contract_type_name = (TextView) v.findViewById(R.id.contract_type_name);
		contractsigned_date = (TextView) v.findViewById(R.id.contractsigned_date);
		reg_name = (TextView) v.findViewById(R.id.reg_name);
		reg_phone =  (TextView) v.findViewById(R.id.reg_phone);
		reg_village_name = (TextView) v.findViewById(R.id.reg_village_name);
		reg_construct_address = (TextView) v.findViewById(R.id.reg_construct_address);
		reg_customer_mgr_name =  (TextView) v.findViewById(R.id.reg_customer_mgr_name);
		//reg_customer_mgr_mobile = (TextView) v.findViewById(R.id.reg_customer_mgr_mobile);
		reg_stylist_name = (TextView) v.findViewById(R.id.reg_stylist_name);
		//reg_stylist_mobile = (TextView) v.findViewById(R.id.reg_stylist_mobile);
		reg_project_mgr_name = (TextView) v.findViewById(R.id.reg_project_mgr_name);
		//reg_project_mgr_mobile = (TextView) v.findViewById(R.id.reg_project_mgr_mobile);
		
		
		contract_num.setText(contract.getContract_num());
		contract_type_name.setText(contract.getContract_type_name());
		contractsigned_date.setText(contract.getContractsigned_date());
		reg_name.setText(contract.getReg_name());
		reg_phone.setText(contract.getReg_phone());
		reg_village_name.setText(contract.getReg_village_name());
		reg_construct_address.setText(contract.getReg_construct_address());
		reg_customer_mgr_name.setText(contract.getReg_customer_mgr_name());
		//reg_customer_mgr_mobile.setText(contract.getReg_customer_mgr_mobile());
		reg_stylist_name.setText(contract.getReg_stylist_name());
		//reg_stylist_mobile.setText(contract.getReg_stylist_mobile());
		reg_project_mgr_name.setText(contract.getReg_project_mgr_name());
		//reg_project_mgr_mobile.setText(contract.getReg_project_mgr_mobile());
	}
	
	/**
	 * 显示项目经理的沟通方式
	 * @param v
	 */
	public void show_project_mgr(View v)
	{
		if (!StringUtils.isEmpty(contract.getReg_project_mgr_mobile()))
			DialogUtils.showAddressBookItemDialog(this, contract.getReg_project_mgr_name(), contract.getReg_project_mgr_mobile());
	}
	/**
	 * 显示客户经理的沟通方式
	 * @param v
	 */
	public void show_customer_mgr(View v)
	{
		if (!StringUtils.isEmpty(contract.getReg_customer_mgr_mobile()))
			DialogUtils.showAddressBookItemDialog(this, contract.getReg_customer_mgr_name(), contract.getReg_customer_mgr_mobile());
	}
	
	/**
	 * 显示设计师的沟通方式
	 * @param v
	 */
	public void show_stylist(View v)
	{
		if (!StringUtils.isEmpty(contract.getReg_stylist_mobile()))
			DialogUtils.showAddressBookItemDialog(this, contract.getReg_stylist_name(), contract.getReg_stylist_mobile());
	}
	
	/**
	 * 初始化进度
	 * @param v
	 */
	public void initDate(View v)
	{
		TextView contractsigned_date1 = (TextView) v.findViewById(R.id.contractsigned_date1);
		TextView contractbegindate = (TextView) v.findViewById(R.id.contractbegindate);
		TextView contractenddate = (TextView) v.findViewById(R.id.contractenddate);
		TextView date_number = (TextView) v.findViewById(R.id.date_number);
		
		contractsigned_date1.setText(contract.getContractsigned_date());
		contractbegindate.setText(contract.getContractbegindate());
		contractenddate.setText(contract.getContractenddate());
		date_number.setText(contract.getDate_number()+"天");		
	}
	
	/**
	 * 初始化金额
	 * @param v
	 */
	public void initAmount(View v)
	{
		TextView contract_sum_price = (TextView) v.findViewById(R.id.contract_sum_price);
		TextView base_item_price = (TextView) v.findViewById(R.id.base_item_price);
		TextView main_item_price = (TextView) v.findViewById(R.id.main_item_price);
		TextView finish_item_price = (TextView) v.findViewById(R.id.finish_item_price);
		TextView design_fee = (TextView) v.findViewById(R.id.design_fee);
		TextView manage_fee = (TextView) v.findViewById(R.id.manage_fee);

		TextView favorable_price = (TextView) v.findViewById(R.id.favorable_price);
		TextView fact_totalmoney = (TextView) v.findViewById(R.id.fact_totalmoney);
		
		contract_sum_price.setText(StringUtils.formatDecimal(contract.getContract_sum_price()));
		base_item_price.setText(StringUtils.formatDecimal(contract.getBase_item_price()));
		main_item_price.setText(StringUtils.formatDecimal(contract.getMain_item_price()));
		finish_item_price.setText(StringUtils.formatDecimal(contract.getFinish_item_price()));
		design_fee.setText(StringUtils.formatDecimal(contract.getDesign_fee()));
		manage_fee.setText(StringUtils.formatDecimal(contract.getManage_fee()));
		favorable_price.setText(StringUtils.formatDecimal(contract.getFavorable_price()));
		fact_totalmoney.setText(StringUtils.formatDecimal(contract.getFact_totalmoney()));
	}
	
	/**
	 * 初始化优惠信息
	 * @param v
	 */
	public void initFavorable(View v)
	{
		TextView favorable_price = (TextView) v.findViewById(R.id.favorable_price);
		favorable_price.setText(StringUtils.formatDecimal(contract.getFavorable_price()));
		
		ListView favorablelist = (ListView) v.findViewById(R.id.favorablelist);
		//favorablelist.setEnabled(false);
		favorablelist.setAdapter(new FavorableViewAdapter(getBaseContext(),contractDiscountList));
	}
	
	
	/**
	 * 初始化页面
	 */
	public void initPager()
	{
		mTabPager = (ViewPager) findViewById(R.id.tabpager);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		mTab1 = (ImageView) findViewById(R.id.img_base);
		mTab2 = (ImageView) findViewById(R.id.img_project_date);
		mTab3 = (ImageView) findViewById(R.id.img_amount);
		mTab4 = (ImageView) findViewById(R.id.img_favorable);
		//mTabImg = (ImageView) findViewById(R.id.img_tab_now);
		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        //计算增量,如果屏幕大小的四分子一比图片的宽度122还大，计算一个增量
//        if ((width/4 - 122) > 0)
//        {
//        	zero = (width/4 - 122)/2;
//        	 //定义初始位置
//            mTabImg.setPadding(zero, 0, 0, 0);
//        }
//        one = width/4; 
//        two = one*2 ;
//        three = one*3 ;
       
        
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.mycontract_base, null);
		initBaseData(view1);
		View view2 = mLi.inflate(R.layout.mycontract_project_date, null);
		initDate(view2);
		View view3 = mLi.inflate(R.layout.mycontract_amount, null);
		initAmount(view3);
		View view4 = mLi.inflate(R.layout.mycontract_favorable, null);
		initFavorable(view4);
		
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);

		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};
		mTabPager.setAdapter(mPagerAdapter);
	}

	/**
	 * 单击事件
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_base_pressed));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_project_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_amount_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, 0, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_favorable_normal));
				}
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_project_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_base_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_amount_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, one, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_favorable_normal));
				}
				break;
			case 2:
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_amount_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, two, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_base_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_project_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_favorable_normal));
				}
				break;
			case 3:
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_favorable_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, three, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_base_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, three, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_project_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, three, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_amount_normal));
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(150);
			//mTabImg.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

}
