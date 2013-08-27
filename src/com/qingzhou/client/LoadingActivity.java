package com.qingzhou.client;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSON;
import com.qingzhou.client.common.GlobalParameter;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.common.RestService;
import com.qingzhou.client.domain.LoginStatus;
import com.qingzhou.client.domain.RestProjectPlan;
import com.qingzhou.client.domain.UserBase;
import com.qingzhou.client.domain.Contract;
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
	private static final int FINISH_MESSAGE = 0x01;
	private static final int ERROR_MESSAGE = 0x02;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.loading);
		
		qcApp = (QcApp)getApplication();
		
		//取得启动该Activity的Intent对象
        Intent intent = getIntent();
        //取出Intent中附加的数据,是否是切换用户登录，如为空，默认为不是切换用户
        flag = intent.getIntExtra("FLAG",0);
        //开启线程并执行
        ThreadPoolUtils.execute(mRunnable);
        
   }
	
	/**
	 * 登录成功后，获取客户基本信息，set到QCAPP中，并进入主界面
	 * @throws Exception 
	 * @throws ClientProtocolException 
	 */
	public void intUserBase() throws ClientProtocolException, Exception
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
		Contract contract = JSON.parseObject(contractJson, Contract.class);
		qcApp.setContract(contract);
		Intent intent = new Intent (LoadingActivity.this,MyContractActivity.class);			
		startActivity(intent);
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
		RestProjectPlan restProjectPlan = JSON.parseObject(projectPlanJson, RestProjectPlan.class);
		qcApp.setProjectPlan(restProjectPlan);
		Intent intent = new Intent (LoadingActivity.this,MyHomeActivity.class);			
		startActivity(intent);
		
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
		        	intUserBase();
		        	break;
		        case GlobalParameter.INIT_CONTRACT:
		        	initContract();
		        	break;
		        case GlobalParameter.INIT_PROJECTPLAN:
		        	initProjectPlan();
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