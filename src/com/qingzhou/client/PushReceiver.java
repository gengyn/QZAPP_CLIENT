package com.qingzhou.client;


import com.alibaba.fastjson.JSON;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.qingzhou.client.BuildConfig;
import com.qingzhou.client.common.GlobalParameter;
import com.qingzhou.client.domain.PushExtras;

import cn.jpush.android.api.JPushInterface;


/**
 * 自定义接收器,通知和消息都接收
 * @author hihi
 *
 */
public class PushReceiver extends BroadcastReceiver {
	private static final String TAG = "PushReceiver";
	private Vibrator mVibrator;  //声明一个振动器对象  

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (BuildConfig.DEBUG)
        	Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            if (BuildConfig.DEBUG)
            	Log.d(TAG, "接收Registration Id : " + regId);
        }else if (JPushInterface.ACTION_UNREGISTER.equals(intent.getAction())){
        	String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
        	if (BuildConfig.DEBUG)
        		Log.d(TAG, "接收UnRegistration Id : " + regId);
         
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	if (BuildConfig.DEBUG)
        		Log.d(TAG, "接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	//收到消息后的处理
        	processCustomMessage(context, bundle);
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
        	
        	if (BuildConfig.DEBUG)
        	{
        		int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        		Log.d(TAG, "接收到推送下来的通知");
        		Log.d(TAG, "接收到推送下来的通知的ID: " + notifactionId);
        	}
            //震动事件，收到通知后手机震动
        	startVibrator(context);
            
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
        	if (BuildConfig.DEBUG)
        		Log.d(TAG, "用户点击打开了通知");
            
        	processNotice(context,bundle);
        	
        }else {
        	Log.i(TAG, "没有响应的事件 - " + intent.getAction());
        }
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
	/**
	 * 预留消息的处理方法
	 * @param context
	 * @param bundle
	 */
	private void processCustomMessage(Context context, Bundle bundle) {
		
	}
	
	/**
	 * 通知的处理方法
	 * @param context
	 * @param bundle
	 */
	private void processNotice(Context context, Bundle bundle)
	{
		PushExtras extras = JSON.parseObject(bundle.getString(JPushInterface.EXTRA_EXTRA), PushExtras.class);
		extras.setNotice_id(bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));
		extras.setNotice_title(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
		extras.setNotice_content(bundle.getString(JPushInterface.EXTRA_ALERT));
		
        switch (extras.getNotice_type())
        {
        case GlobalParameter.CLIENT_MYPROJECT:
        	goMyProject(context);
        	break;
        case GlobalParameter.CLIENT_MYCONTRACT:
        	goMyContract(context);
        	break;
        case GlobalParameter.CLIENT_MYINFO:
        	goMyInfo(context);
        	break;
        case GlobalParameter.CLIENT_MYMESSAGE:
        	goMyMessage(context,extras.getMsg_sender());
        	break;
        default:
        	goNoticeDefault(context,extras);	
        }
	}
	/**
	 * APP是否在启动状态，true为启动，false为没启动
	 * @return
	 */
	private boolean isStart()
	{
		return !(MainActivity._instance == null ); 
		//|| MainActivity._instance.isDestroyed()
	}
	
	/**
	 * 启动APP
	 * @param context
	 */
	private void goStart(Context context,int flag)
	{
		Intent i = new Intent(context,WelCome.class);
    	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	i.putExtra("FLAG", flag);
    	context.startActivity(i);
	}
	/**
	 * 非栏目性的通知显示界面
	 * @param context
	 * @param extras
	 */
	private void goNoticeDefault(Context context,PushExtras extras)
	{
		Intent intent = new Intent();
		intent.putExtra("extras", extras);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context,NoticeDefaultActivity.class);
		context.startActivity(intent);
	}
	/**
	 * 跳转我的家装主页面
	 * @param context
	 */
	private void goMyProject(Context context)
	{
		if (isStart())
		{
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("FLAG", GlobalParameter.INIT_PROJECTPLAN);
		    intent.setClass(context,LoadingActivity.class);
		    context.startActivity(intent);
		}else
			goStart(context,GlobalParameter.INIT_PROJECTPLAN);
		
		
	    
	}
	
	/**
	 * 跳转我的合同主界面
	 * @param context
	 */
	private void goMyContract(Context context)
	{
		if (isStart())
		{
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("FLAG", GlobalParameter.INIT_CONTRACT);
		    intent.setClass(context,LoadingActivity.class);
		    context.startActivity(intent);
		}else
			goStart(context, GlobalParameter.INIT_CONTRACT);
		
	}
	
	/**
	 * 跳转轻舟资讯主界面
	 * @param context
	 */
	private void goMyInfo(Context context)
	{
		if (isStart())
		{
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("FLAG", GlobalParameter.INIT_MYINFO);
		    intent.setClass(context,LoadingActivity.class);
			//增加翻页、下拉获取最新资讯功能
		    //intent.setClass(context,MyInfoActivity.class);
		    context.startActivity(intent);
		}else
			goStart(context, GlobalParameter.INIT_MYINFO);

	}
	
	/**
	 * 跳转我的消息
	 * @param context
	 */
	private void goMyMessage(Context context,String sender)
	{
		if (isStart())
		{
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    intent.setClass(context,MyMessageActivity.class);
		    context.startActivity(intent);
		}else
			goStart(context, 0);
	}
	/**
	 * 震动事件
	 * @param context
	 */
	private void startVibrator(Context context)
	{
		 mVibrator = (Vibrator)context.getSystemService(Service.VIBRATOR_SERVICE);
         mVibrator.vibrate(200);
	}
	
}
