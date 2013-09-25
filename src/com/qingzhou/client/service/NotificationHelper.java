package com.qingzhou.client.service;

import java.util.zip.Adler32;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSON;
import com.qingzhou.app.utils.HttpUtils;
import com.qingzhou.app.utils.Logger;
import com.qingzhou.app.utils.ThreadPoolUtils;
import com.qingzhou.client.MainActivity;
import com.qingzhou.client.MyMessageActivity;
import com.qingzhou.client.R;
import com.qingzhou.client.WelCome;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.common.RestService;
import com.qingzhou.client.domain.ChatMsg;
import com.qingzhou.client.domain.Notice;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

@SuppressLint("NewApi")
public class NotificationHelper {
	
	private static final String TAG = "NotificationHelper";
	private static final int FINISH_MESSAGE = 0x01;
	private static final int ERROR_MESSAGE = 0x02;
	
	private Notice notice;
	
    /**
     * 得到新消息后，发送通知
     * @param context
     * @param nm
     * @param notice
     */
    public static void showMessageNotification(Context context,ChatMsg chatMsg)
    {
    	Intent select = new Intent();
    	if (MainActivity._instance != null)
		{
	        select.setClass(context, MyMessageActivity.class);
	        select.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		}else
		{
			select.setClass(context, WelCome.class);
			select.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			select.putExtra("FLAG", Constants.INIT_MYMESSAGE);
		}
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, select, PendingIntent.FLAG_UPDATE_CURRENT);
        
        Notification.Builder builder = new Notification.Builder(context)
        .setTicker("收到新消息")
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle("我的消息")
        .setContentText(chatMsg.getText())
        .setAutoCancel(true)
        .setContentIntent(contentIntent);
        
        int notificationId = getNofiticationID("我的消息");
        
        Logger.d(TAG, notificationId+"");
        
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        @SuppressWarnings("deprecation")
		Notification notification = builder.getNotification();
        
        showMessageNotificationLocal(context,nm,notification,notificationId);
    }
    
    private static void showMessageNotificationLocal(
    		Context context, NotificationManager nm, Notification notification, int notificationId) {
        notification.ledARGB = 0xff00ff00;
        notification.ledOnMS = 300;
        notification.ledOffMS = 1000;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        boolean needSound = Constants.NOTIFICATION_NEED_SOUND;
        boolean needVibrate = Constants.NOTIFICATION_NEED_VIBRATE;
        
    	if (needSound && needVibrate) {
        	notification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        } else if (needSound){
        	notification.defaults = Notification.DEFAULT_SOUND;
        } else if (needVibrate) {
        	notification.defaults = Notification.DEFAULT_VIBRATE;
        }
    	
        nm.notify(notificationId, notification);
    }
    
    public static int getNofiticationID(String friend) {
        if (TextUtils.isEmpty(friend)) {
            return 0;
        }
        
        int nId = 0;
        Adler32 adler32 = new Adler32();
        adler32.update(friend.getBytes());
        nId = (int) adler32.getValue();
        if (nId < 0) {
            nId = Math.abs(nId);
        }
        
        if (nId < 0) {
            nId = Math.abs(nId);
        }
        return nId;
    }
    
    /**
     * 发送通知接口
     * @param notice
     * @throws ClientProtocolException
     * @throws Exception
     */
    private void sendNofitication()
    {
    	try{
    		HttpUtils httpUtil = new HttpUtils();
    		String resultCode = httpUtil.httpPostExecute(RestService.POST_SENDNOTICE_URL, JSON.toJSONString(notice));
    	}catch(Exception ex)
    	{
    		Logger.e(TAG, ex.toString());
    	}
    	
    }
    
    public void sendNofitication(Notice notice)
    {
    	this.notice = notice;
    	//开启线程并执行
        ThreadPoolUtils.execute(mRunnable);
    }
    
    /**
	 * 具体执行方法
	 */
	private Runnable mRunnable = new Runnable() {	
		@Override
		public void run() {
			try {
				//发送通知
				sendNofitication();
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
				
				break;
			case ERROR_MESSAGE:
				
				break;
			default:
				break;
			}
    	};
    };


}
