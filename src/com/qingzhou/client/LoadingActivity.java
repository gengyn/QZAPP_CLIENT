package com.qingzhou.client;

import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.qingzhou.client.common.GlobalParameter;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.common.RestService;
import com.qingzhou.client.domain.LoginStatus;
import com.qingzhou.client.domain.Myinfo;
import com.qingzhou.client.domain.RestProjectPlan;
import com.qingzhou.client.domain.UserBase;
import com.qingzhou.client.domain.Contract;
import com.qingzhou.client.domain.RestProjectPhoto;
import com.qingzhou.client.image.ui.ImageGridActivity;
import com.qingzhou.client.util.CustomerUtils;
import com.qingzhou.client.util.HttpUtils;
import com.qingzhou.client.util.StringUtils;
import com.qingzhou.client.util.ThreadPoolUtils;
import com.qingzhou.client.util.DialogUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class LoadingActivity extends Activity{

	private QcApp qcApp;
	int flag;
	String schedetail_id = "";
	private static final int FINISH_MESSAGE = 0x01;
	private static final int ERROR_MESSAGE = 0x02;
	
	private int pageSize = GlobalParameter.PAGESIZE;//默认分页每页行数
	private int pageNo = 1;//默认页数
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.loading);

		qcApp = (QcApp)getApplication();
		
		//取得启动该Activity的Intent对象
        Intent intent = getIntent();
        //取出Intent中附加的数据
        flag = intent.getIntExtra("FLAG",0);
        schedetail_id = intent.getStringExtra("schedetail_id");
        pageNo = intent.getIntExtra("pageNo",1);
        pageSize = intent.getIntExtra("pageSize",10);
        //开启线程并执行
        ThreadPoolUtils.execute(mRunnable);
        
   }
	

	
	/**
	 * 登录成功后，获取客户基本信息，set到QCAPP中，并进入主界面
	 * @throws Exception 
	 * @throws ClientProtocolException 
	 */
	public void initUserBase() throws ClientProtocolException, Exception
	{
		
		String userToken = CustomerUtils.createToken();
					
		LoginStatus loginStatus = new LoginStatus();
		loginStatus.setUser_token(userToken);
		loginStatus.setUser_name(qcApp.getUserName());
		loginStatus.setUser_phone(qcApp.getUserPhone());
					
		HttpUtils httpUtil = new HttpUtils();
		String userBaseJson = httpUtil.httpPostExecute(RestService.POST_LOGIN_URL, JSON.toJSONString(loginStatus));
		if (!StringUtils.isNull(userBaseJson))
		{
			//设置客户令牌及客户基本信息
			qcApp.setUserToken(userToken);
			qcApp.setUserBase(JSON.parseObject(userBaseJson,UserBase.class));
			Intent intent = new Intent (LoadingActivity.this,MainActivity.class);			
			startActivity(intent);
			LoginActivity._instance.finish();
		}
	
	}
	
	/**
	 * 获取合同信息
	 * @throws Exception 
	 * @throws ClientProtocolException 
	 */
	public void initContract() throws ClientProtocolException, Exception
	{
		HttpUtils httpUtil = new HttpUtils();
		String contractJson = httpUtil.httpGetExecute(
				RestService.GET_CONTRACT_URL+"/"+qcApp.getUserBase().getFormal_contract_id());
		if (!StringUtils.isNull(contractJson))
		{
			Contract contract = JSON.parseObject(contractJson, Contract.class);
			qcApp.setContract(contract);
			Intent intent = new Intent (LoadingActivity.this,MyContractActivity.class);			
			startActivity(intent);
		}
	}
	
	/**
	 * 获取工程进度信息
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	public void initProjectPlan() throws ClientProtocolException, Exception
	{
		HttpUtils httpUtil = new HttpUtils();
		String projectPlanJson = httpUtil.httpGetExecute(
				RestService.GET_PROJECTPLAN_URL+qcApp.getUserBase().getCustomer_id());
		if (!StringUtils.isNull(projectPlanJson))
		{
			RestProjectPlan restProjectPlan = JSON.parseObject(projectPlanJson, RestProjectPlan.class);
			qcApp.setProjectPlan(restProjectPlan);
			Intent intent = new Intent (LoadingActivity.this,MyHomeActivity.class);			
			startActivity(intent);
		}
		
	}
	
	/**
	 * 获取图片
	 */
	public void showPhoto() throws ClientProtocolException, Exception
	{
		HttpUtils httpUtil = new HttpUtils();
		String photoPathJson = httpUtil.httpGetExecute(
				RestService.GET_PHOTO_URL+schedetail_id+"/"+qcApp.getProjectPlan().getSche_id());
		Intent intent = new Intent();
		intent.putExtra("photoPath", photoPathJson);
		//一个界面完成浏览
		//intent.setClass(LoadingActivity.this,ProjectPhotoActivity.class);
		//先缩略图列表，后整图
		intent.setClass(LoadingActivity.this,ImageGridActivity.class);
		startActivity(intent);
	}
	
	/**
	 * 获取轻舟资讯列表，根据客户ID进行查询
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	public void initMyinfo() throws ClientProtocolException, Exception
	{
		HttpUtils httpUtil = new HttpUtils();
		String myinfoJson = httpUtil.httpGetExecute(RestService.GET_MYINFO_URL+qcApp.getUserBase().getCustomer_id()+
				"/" + pageNo + "/" + pageSize);
		//暂时不访问网络，内置资讯数据
		//String myinfoJson = "[{\"info_id\":\"1\",\"info_title\":\"什么是轻舟装饰完整家装？\",\"info_type\":\"家装常识\",\"info_url\":\"file:///android_asset/html/2013090901.html\",\"info_date\":\"2013-09-09\"},{\"info_id\":\"2\",\"info_title\":\"轻舟装饰完整家装有哪些优势？\",\"info_type\":\"家装常识\",\"info_url\":\"file:///android_asset/html/2013090902.html\",\"info_date\":\"2013-09-09\"},{\"info_id\":\"3\",\"info_title\":\"完整家装必须包含哪些项目？\",\"info_type\":\"家装常识\",\"info_url\":\"file:///android_asset/html/2013090903.html\",\"info_date\":\"2013-09-09\"},{\"info_id\":\"4\",\"info_title\":\"完整家装设计、施工质量如何保障？\",\"info_type\":\"家装常识\",\"info_url\":\"file:///android_asset/html/2013090904.html\",\"info_date\":\"2013-09-09\"},{\"info_id\":\"5\",\"info_title\":\"完整家装售后服务如何保障？\",\"info_type\":\"家装常识\",\"info_url\":\"file:///android_asset/html/2013090905.html\",\"info_date\":\"2013-09-09\"},{\"info_id\":\"6\",\"info_title\":\"完整家装设计费收取方式？\",\"info_type\":\"家装常识\",\"info_url\":\"file:///android_asset/html/2013090906.html\",\"info_date\":\"2013-09-09\"}]";
		if(!StringUtils.isNull(myinfoJson))
		{
			qcApp.setInfoList(JSONArray.parseArray(myinfoJson,Myinfo.class));
			Intent intent = new Intent (LoadingActivity.this,MyInfoTempActivity.class);
			startActivity(intent);
		}
		
	}
	
	/**
	 * 具体执行方法
	 */
	private Runnable mRunnable = new Runnable() {	
		@Override
		public void run() {
			try {
		        switch(flag)
		        {
		        case GlobalParameter.INIT_USERINFO:
		        	initUserBase();
		        	break;
		        case GlobalParameter.INIT_CONTRACT:
		        	initContract();
		        	break;
		        case GlobalParameter.INIT_PROJECTPLAN:
		        	initProjectPlan();
		        	break;
		        case GlobalParameter.SHOW_PHOTO:
		        	showPhoto();
		        	break;
		        case GlobalParameter.INIT_MYINFO:
		        	initMyinfo();
		        	break;
		        default:
		        	throw new Exception("异常");	
		        }
		    	mHandler.sendEmptyMessage(FINISH_MESSAGE);

			}  catch(Exception e){
				mHandler.sendEmptyMessage(ERROR_MESSAGE);
				e.printStackTrace();
			}
			
		}
	};
	
	/**
	 * 处理
	 */
	private Handler mHandler = new Handler(){
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
			case FINISH_MESSAGE:
				//通讯成功之后，可以写日志以及更新客户操作状态
				
				LoadingActivity.this.finish();
				break;
			case ERROR_MESSAGE:
				Toast.makeText(getBaseContext(), "通信异常，请重试", Toast.LENGTH_LONG).show();
				LoadingActivity.this.finish();
				break;
			default:
				break;
			}
    	};
    };
}