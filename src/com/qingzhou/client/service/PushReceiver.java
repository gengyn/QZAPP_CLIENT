package com.qingzhou.client.service;



import com.alibaba.fastjson.JSON;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.qingzhou.client.BuildConfig;
import com.qingzhou.client.ChatActivity;
import com.qingzhou.client.LoadingActivity;
import com.qingzhou.client.MainActivity;
import com.qingzhou.client.MyMessageActivity;
import com.qingzhou.client.NoticeDefaultActivity;
import com.qingzhou.client.R;
import com.qingzhou.client.WelCome;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.domain.Notice;
import com.qingzhou.client.domain.PushExtras;
import com.qingzhou.client.domain.ChatMsg;

import cn.jpush.android.api.BasicPushNotificationBuilder;
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
            	Log.d(TAG, "JPush用户注册成功,接收Registration Id : " + regId);
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
        	BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
    		builder.statusBarDrawable = R.drawable.ic_launcher;
    		//builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为自动消失
    		if (Constants.NOTIFICATION_NEED_SOUND && Constants.NOTIFICATION_NEED_VIBRATE) {
    			builder.notificationDefaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
            } else if (Constants.NOTIFICATION_NEED_SOUND){
            	builder.notificationDefaults = Notification.DEFAULT_SOUND;
            } else if (Constants.NOTIFICATION_NEED_VIBRATE) {
            	builder.notificationDefaults = Notification.DEFAULT_VIBRATE;
            }
    		JPushInterface.setPushNotificationBuilder(1, builder);
            
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
	 * 消息的处理方法
	 * @param context
	 * @param bundle
	 */
	private void processCustomMessage(Context context, Bundle bundle) {
		MessageService msgService = new MessageService(context);
		ChatMsg chatMsg = msgService.receiveMessage(bundle);
		if (!Constants.mymessageActivity_isBackground)
		{
	        Intent msgIntent = new Intent(MyMessageActivity.MESSAGE_RECEIVED_ACTION);
	        msgIntent.putExtra(Constants.KEY_MESSAGE, chatMsg);
	        //发送广播
	        context.sendBroadcast(msgIntent);
		}else if (!Constants.chatActivity_isBackground)
		{
			Intent msgIntent = new Intent(ChatActivity.MESSAGE_RECEIVED_BYID_ACTION);
	        msgIntent.putExtra(Constants.KEY_MESSAGE, chatMsg);
	        //发送广播,通知ChatActivity接收
	        context.sendBroadcast(msgIntent);
		}else
		{
			//发送通知
			NotificationHelper.showMessageNotification(context,chatMsg);
		}
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
        case Constants.CLIENT_MYPROJECT:
        	goMyProject(context);
        	break;
        case Constants.CLIENT_MYCONTRACT:
        	goMyContract(context);
        	break;
        case Constants.CLIENT_MYINFO:
        	goMyInfo(context);
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
		return MainActivity._instance != null;
		//return !GlobalParameter.mainActivity_isBackground;
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
			intent.putExtra("FLAG", Constants.INIT_PROJECTPLAN);
		    intent.setClass(context,LoadingActivity.class);
		    context.startActivity(intent);
		}else
			goStart(context,Constants.INIT_PROJECTPLAN);
  
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
			intent.putExtra("FLAG", Constants.INIT_CONTRACT);
		    intent.setClass(context,LoadingActivity.class);
		    context.startActivity(intent);
		}else
			goStart(context, Constants.INIT_CONTRACT);
		
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
			intent.putExtra("FLAG", Constants.INIT_MYINFO);
		    intent.setClass(context,LoadingActivity.class);
			//增加翻页、下拉获取最新资讯功能
		    //intent.setClass(context,MyInfoActivity.class);
		    context.startActivity(intent);
		}else
			goStart(context, Constants.INIT_MYINFO);

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
