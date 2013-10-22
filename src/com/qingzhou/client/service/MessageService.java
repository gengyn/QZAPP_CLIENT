package com.qingzhou.client.service;

import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qingzhou.app.db.Configuration;
import com.qingzhou.app.db.DBHelper;
import com.qingzhou.app.utils.HttpUtils;
import com.qingzhou.app.utils.Logger;
import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.app.utils.ThreadPoolUtils;
import com.qingzhou.client.dao.InterlocutorDao;
import com.qingzhou.client.dao.MessageLogDao;
import com.qingzhou.client.common.AppException;
import com.qingzhou.client.common.RestService;
import com.qingzhou.client.domain.ChatMsg;
import com.qingzhou.client.domain.Interlocutor;
import com.qingzhou.client.domain.MessageLog;
import com.qingzhou.client.domain.MyMessage;

/**
 * 消息处理服务
 */
public class MessageService {

	private static final String TAG = "MessageService";
	private Context mContext;
	private ChatMsg chatMsg;
	
	
	private static final int FINISH_MESSAGE = 0x01;
	private static final int ERROR_MESSAGE = 0x02;
	
	public MessageService(Context context)
	{
		this.mContext = context;
	}
	//发送消息
	public void sendMessage(ChatMsg chatMsg)
	{		
		
		this.chatMsg = chatMsg;
		//开启线程并执行
        ThreadPoolUtils.execute(mRunnable);
	}
	
	/**
	 * 调用接口发送消息
	 */
	private void pushMsg() throws ClientProtocolException, Exception
	{
		//保存消息
		saveMsgDB(mContext,chatMsg,true);
		
		MyMessage message = new MyMessage();
		message.setTarget_flag(1);//发送给员工
		message.setMessage_type("00");
		message.setMessage_content(chatMsg.getText());
		message.setSender(chatMsg.getSender());
		message.setReceiver(chatMsg.getReceiver());
		message.setImg_url("img");
		message.setVoice_url("voice");
		HttpUtils httpUtil = new HttpUtils();
		String resultCode = httpUtil.httpPostExecute(RestService.POST_SENDMESSAGE_URL, JSON.toJSONString(message));
		if (!resultCode.equals("0"))
			throw new AppException(resultCode);
	}
	
	//接收消息
	public ChatMsg receiveMessage(Bundle bundle)
	{
		String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);

        if (StringUtils.isEmpty(message)) {
            Logger.w(TAG, "消息为空");
            return new ChatMsg();
        }
        ChatMsg chatMsg = JSON.parseObject(bundle.getString(JPushInterface.EXTRA_EXTRA),ChatMsg.class);
        chatMsg.setText(message);
        chatMsg.setComMeg(false);
        //保存入库
        saveMsgDB(mContext,chatMsg,false);
        return chatMsg;
	}
	
	//保存消息
	private void saveMsgDB(Context context,ChatMsg chatMsg,boolean isSend)
	{
		DBHelper dbHelper = new DBHelper(context, Configuration.DB_NAME,
				null, Configuration.DB_VERSION);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		try
		{
			MessageLogDao messageLogDao = new MessageLogDao(context,db);
			messageLogDao.insertMsg(chatMsg);
			InterlocutorDao interlocutorDao = new InterlocutorDao(context,db);
			int i = interlocutorDao.updateMsg(chatMsg,isSend);
			if (i<=0)
				interlocutorDao.insertMsg(chatMsg,isSend);
			
			db.setTransactionSuccessful();
			db.endTransaction();
		}catch(Exception e)
		{
			Logger.e(TAG, e.toString());
			db.endTransaction();
		}finally
		{
			if (db != null && db.isOpen())
				db.close();
		}
	}
	
	/**
	 * 具体执行方法
	 */
	private Runnable mRunnable = new Runnable() {	
		@Override
		public void run() {
			try {
				//发送消息
				pushMsg();
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
				//成功后保存消息
				//saveMsgDB(mContext,chatMsg,true);
				break;
			case ERROR_MESSAGE:
				Toast.makeText(mContext, "消息未送达，可能对方未在线", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
    	};
    };
    
    /**
     * 获取对话人列表
     * @return
     */
    public List<Interlocutor> listInterlocutor(String my_mobile)
    {
    	InterlocutorDao interlocutorDao = new InterlocutorDao(mContext);
    	return interlocutorDao.listInterlocutor(my_mobile);
    }
    
    /**
     * 删除某个对话人的所有消息
     * @param i_mobile
     * @return
     */
    public int delMessage(String my_mobile,String i_mobile)
    {
    	DBHelper dbHelper = new DBHelper(mContext, Configuration.DB_NAME,
				null, Configuration.DB_VERSION);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		int count = 0;
		try
		{
			InterlocutorDao interlocutorDao = new InterlocutorDao(mContext,db);
			count = interlocutorDao.delInterlocutor(my_mobile,i_mobile);
			if (count > 0)
			{
				MessageLogDao messageLogDao = new MessageLogDao(mContext,db);
				count = messageLogDao.delMessage(my_mobile,i_mobile);
			}
			
			db.setTransactionSuccessful();
			db.endTransaction();
		}catch(Exception e)
		{
			Log.e(TAG, e.toString());
			db.endTransaction();
		}finally
		{
			if (db != null && db.isOpen())
				db.close();
		}
		return count;
    	
    }
    
    /**
     * 获取我与某人的对话记录
     * @param myMobile
     * @param otherMobile
     * @return
     */
    public List<MessageLog> listMyMessage(String myMobile,String otherMobile)
    {
    	//先置标识，不需要在一个事务里
    	setReadFlag(myMobile,otherMobile);
    	
    	MessageLogDao messageLogDao = new MessageLogDao(mContext);
    	return messageLogDao.listMsg(myMobile, otherMobile);
    }
    
    /**
     * 设置消息未已读
     * @param op
     */
    public void setReadFlag(String myMobile,String otherMobile)
    {
    	InterlocutorDao interlocutorDao = new InterlocutorDao(mContext);
    	interlocutorDao.updateReadFlag(myMobile,otherMobile);
    }
    
    
}
