package com.qingzhou.client;

import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.qingzhou.app.image.ui.ImageGridActivity;
import com.qingzhou.app.utils.CustomerUtils;
import com.qingzhou.app.utils.DialogUtils;
import com.qingzhou.app.utils.HttpUtils;
import com.qingzhou.app.utils.Logger;
import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.app.utils.ThreadPoolUtils;
import com.qingzhou.client.common.AppException;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.common.RestService;
import com.qingzhou.client.dao.CacheDao;
import com.qingzhou.client.domain.LoginStatus;
import com.qingzhou.client.domain.Myinfo;
import com.qingzhou.client.domain.RestProjectPlan;
import com.qingzhou.client.domain.UserBase;
import com.qingzhou.client.domain.Contract;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;

public class LoadingActivity extends BaseActivity{

	private QcApp qcApp;
	int flag;
	String schedetail_id = "";
	private static final int FINISH_MESSAGE = 0x01;
	private static final int ERROR_MESSAGE = 0x02;
	
	private int pageSize = Constants.PAGESIZE;//默认分页每页行数
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
	public void initUserBase() throws AppException
	{
		//频率控制
		if (Constants.LAST_USERBASE != -1 && 
				System.currentTimeMillis() - Constants.LAST_USERBASE < Constants.LIMIT_USERBASE )
			throw new AppException("9997");
		//---------------------------------------------
		String userToken = CustomerUtils.createToken();
					
		LoginStatus loginStatus = new LoginStatus();
		loginStatus.setUser_token(userToken);
		loginStatus.setUser_name(qcApp.getUserName());
		loginStatus.setUser_phone(qcApp.getUserPhone());
					
		HttpUtils httpUtil = new HttpUtils();
		String userBaseJson = "";
		CacheDao cacheDao = new CacheDao(this);
		try{
			userBaseJson = httpUtil.httpPostExecute(RestService.POST_LOGIN_URL, JSON.toJSONString(loginStatus));
			
		}catch(Exception ex)
		{
			//访问内置数据库，是否能获取到缓存数据
			userBaseJson = cacheDao.queryCache(qcApp.getUserName(), qcApp.getUserPhone(), "00");
			if (StringUtils.isEmpty(userBaseJson))
				throw new AppException(ex);
		}
		if (!StringUtils.isEmpty(userBaseJson))
		{
			//设置客户令牌及客户基本信息
			qcApp.setUserToken(userToken);
			qcApp.setUserBase(JSON.parseObject(userBaseJson,UserBase.class));
			//新增客户基本信息缓存
			cacheDao.delCache(qcApp.getUserName(), qcApp.getUserPhone(), "00");
			cacheDao.insertCache(qcApp.getUserName(), qcApp.getUserPhone(), "00", userBaseJson);
			
			Intent intent = new Intent (LoadingActivity.this,MainActivity.class);			
			startActivity(intent);
			//切换动画
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			LoginActivity._instance.finish();
		}else throw new AppException("9996");
		//---------------------------------------------
		//频率控制
		Constants.LAST_USERBASE = System.currentTimeMillis();
	
	}
	
	/**
	 * 重发消息，接收者是客户的
	 * @throws Exception 
	 * @throws ClientProtocolException 
	 */
	public void reSendMsg() throws AppException
	{
		HttpUtils httpUtil = new HttpUtils();
		String resultJson = httpUtil.httpGetExecute(RestService.GET_RESENDMSG_URL+qcApp.getUserPhone());
	}
	
	/**
	 * 获取合同信息
	 * @throws Exception 
	 * @throws ClientProtocolException 
	 */
	public void initContract() throws ClientProtocolException, Exception
	{
		//频率控制
		if (Constants.LAST_MYCONTRACT != -1 && 
				System.currentTimeMillis() - Constants.LAST_MYCONTRACT < Constants.LIMIT_MYCONTRACT )
			throw new AppException("9997");
		//---------------------------------------------
		HttpUtils httpUtil = new HttpUtils();
		String contractJson = httpUtil.httpGetExecute(
				RestService.GET_CONTRACT_URL+ qcApp.getUserToken() + "/"+qcApp.getUserBase().getFormal_contract_id());
		if (!StringUtils.isEmpty(contractJson))
		{
			Contract contract = JSON.parseObject(contractJson, Contract.class);
			qcApp.setContract(contract);
			Intent intent = new Intent (LoadingActivity.this,MyContractActivity.class);			
			startActivity(intent);
			//切换动画
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		}else throw new AppException("9995");
		//---------------------------------------------
		//频率控制
		Constants.LAST_MYCONTRACT = System.currentTimeMillis();
	}
	
	/**
	 * 获取工程进度信息
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	public void initProjectPlan() throws ClientProtocolException, Exception
	{
		//频率控制
		if (Constants.LAST_MYPROJECT != -1 && 
				System.currentTimeMillis() - Constants.LAST_MYPROJECT < Constants.LIMIT_MYPROJECT )
			throw new AppException("9997");
		//---------------------------------------------
		HttpUtils httpUtil = new HttpUtils();
		String projectPlanJson = httpUtil.httpGetExecute(
				RestService.GET_PROJECTPLAN_URL+ qcApp.getUserToken() + "/"+qcApp.getUserBase().getCustomer_id());
		if (!StringUtils.isEmpty(projectPlanJson))
		{
			RestProjectPlan restProjectPlan = JSON.parseObject(projectPlanJson, RestProjectPlan.class);
			qcApp.setProjectPlan(restProjectPlan);
			Intent intent = new Intent (LoadingActivity.this,MyHomeActivity.class);			
			startActivity(intent);
			//切换动画
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		}else throw new AppException("9994");
		//---------------------------------------------
		//频率控制
		Constants.LAST_MYPROJECT = System.currentTimeMillis();
		
	}
	
	/**
	 * 获取图片
	 */
	public void showPhoto() throws ClientProtocolException, Exception
	{
		HttpUtils httpUtil = new HttpUtils();
		String photoPathJson = httpUtil.httpGetExecute(
				RestService.GET_PHOTO_URL+ qcApp.getUserToken() + "/"+schedetail_id+"/"+qcApp.getProjectPlan().getSche_id());
		Intent intent = new Intent();
		intent.putExtra("photoPath", photoPathJson);
		//一个界面完成浏览
		//intent.setClass(LoadingActivity.this,ProjectPhotoActivity.class);
		//先缩略图列表，后整图
		intent.setClass(LoadingActivity.this,ImageGridActivity.class);
		startActivity(intent);
		//切换动画
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}
	
	/**
	 * 获取轻舟资讯列表，根据客户ID进行查询
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	public void initMyinfo() throws ClientProtocolException, Exception
	{
		//频率控制
		if (Constants.LAST_MYINFO != -1 && 
				System.currentTimeMillis() - Constants.LAST_MYINFO < Constants.LIMIT_MYINFO )
			throw new AppException("9997");
		//---------------------------------------------
		HttpUtils httpUtil = new HttpUtils();
		String myinfoJson = httpUtil.httpGetExecute(RestService.GET_MYINFO_URL+ qcApp.getUserToken() + "/"+qcApp.getUserBase().getCustomer_id()+
				"/" + pageNo + "/" + pageSize);
		//暂时不访问网络，内置资讯数据
		//String myinfoJson = "[{\"info_id\":\"1\",\"info_title\":\"什么是轻舟装饰完整家装？\",\"info_type\":\"家装常识\",\"info_url\":\"file:///android_asset/html/2013090901.html\",\"info_date\":\"2013-09-09\"},{\"info_id\":\"2\",\"info_title\":\"轻舟装饰完整家装有哪些优势？\",\"info_type\":\"家装常识\",\"info_url\":\"file:///android_asset/html/2013090902.html\",\"info_date\":\"2013-09-09\"},{\"info_id\":\"3\",\"info_title\":\"完整家装必须包含哪些项目？\",\"info_type\":\"家装常识\",\"info_url\":\"file:///android_asset/html/2013090903.html\",\"info_date\":\"2013-09-09\"},{\"info_id\":\"4\",\"info_title\":\"完整家装设计、施工质量如何保障？\",\"info_type\":\"家装常识\",\"info_url\":\"file:///android_asset/html/2013090904.html\",\"info_date\":\"2013-09-09\"},{\"info_id\":\"5\",\"info_title\":\"完整家装售后服务如何保障？\",\"info_type\":\"家装常识\",\"info_url\":\"file:///android_asset/html/2013090905.html\",\"info_date\":\"2013-09-09\"},{\"info_id\":\"6\",\"info_title\":\"完整家装设计费收取方式？\",\"info_type\":\"家装常识\",\"info_url\":\"file:///android_asset/html/2013090906.html\",\"info_date\":\"2013-09-09\"}]";
		if(!StringUtils.isEmpty(myinfoJson))
		{
			qcApp.setInfoList(JSONArray.parseArray(myinfoJson,Myinfo.class));
			Intent intent = new Intent (LoadingActivity.this,MyInfoTempActivity.class);
			startActivity(intent);
			//切换动画
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		}else throw new AppException("9993");
		//频率控制
		Constants.LAST_MYINFO = System.currentTimeMillis();
	}
	
	/**
	 * 退出APP
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	public void exitApp() throws ClientProtocolException, Exception
	{
		HttpUtils httpUtil = new HttpUtils();
		String delStatus = httpUtil.httpDelExecute(RestService.DEL_LOGIN_URL+qcApp.getUserToken());
		//System.exit(0);
		//android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	/**
	 * 具体执行方法
	 */
	private Runnable mRunnable = new Runnable() {	
		@Override
		public void run() {
			Message msg = new Message();
			try {
		        switch(flag)
		        {
		        case Constants.INIT_USERINFO:
		        	initUserBase();
		        	reSendMsg();
		        	break;
		        case Constants.INIT_CONTRACT:
		        	initContract();
		        	break;
		        case Constants.INIT_PROJECTPLAN:
		        	initProjectPlan();
		        	break;
		        case Constants.SHOW_PHOTO:
		        	showPhoto();
		        	break;
		        case Constants.INIT_MYINFO:
		        	initMyinfo();
		        	break;
		        case Constants.INIT_EXITAPP:
		        	exitApp();
		        	break;
		        default:
		        	throw new AppException("9999");	
		        }
		        msg.what = FINISH_MESSAGE;

			}  catch(Exception e){
				msg.what = ERROR_MESSAGE;
				msg.obj = e;
				e.printStackTrace();
			}
			
			mHandler.sendMessage(msg);
			
		}
	};
	
	/**
	 * 处理
	 */
	private Handler mHandler = new Handler(){			
    	public void handleMessage(Message msg) {
    		if (flag == Constants.INIT_EXITAPP)
    			System.exit(0);
    		
    		switch (msg.what) {
			case FINISH_MESSAGE:
				LoadingActivity.this.finish();
				break;
			case ERROR_MESSAGE:
				DialogUtils.showLongToask(getApplicationContext(), AppException.getMessage(((AppException)msg.obj).getCode()));
				//DialogUtils.showLongToask(getApplicationContext(), ((Exception)msg.obj).getMessage());
				LoadingActivity.this.finish();
				break;
			default:
				break;
			}
    		
    	};
    };
}