package com.qingzhou.client.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.jpush.android.api.c;

import com.qingzhou.app.db.BaseDao;
import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.domain.ChatMsg;
import com.qingzhou.client.domain.MessageLog;

public class MessageLogDao extends BaseDao<MessageLog>{
	
	String tableName = "APP_MESSAGE_LOG";
	public MessageLogDao(Context context) {
		super(context);
	}
	
	public MessageLogDao(Context context,SQLiteDatabase db)
	{
		super(context,db);
	}
	
	public void insertMsg(ChatMsg chatMsg)
	{
		ContentValues cv = new ContentValues();
		cv.put("SENDER_MOBILE", chatMsg.getSender());
		cv.put("SENDER_NAME", chatMsg.getSenderName());
		cv.put("RECEIVER_MOBILE", chatMsg.getReceiver());
		cv.put("MSG_CONTENT",chatMsg.getText());
		cv.put("MSG_TIME", StringUtils.getCurDate());
		cv.put("IMG_URL", chatMsg.getImg_url());
		cv.put("VOICE_URL", chatMsg.getVoice_url());
		super.insert(tableName, cv);
	}
	
	/**
	 * 查询两者对话的所有消息记录
	 * @param myMobile
	 * @param otherMobile
	 * @return
	 */
	public List<MessageLog> listMsg(String myMobile,String otherMobile)
	{
		return super.queryList(MessageLog.class, tableName, new String[]{"*"},
				"(sender_mobile=? and receiver_mobile=?) or (sender_mobile=? and receiver_mobile=?)",
				new String[]{myMobile,otherMobile,otherMobile,myMobile}, 
				"MSG_TIME asc", null, null);
		
	}
	/**
	 * 删除某个对话人的所有消息
	 * @param mobile
	 * @return
	 */
	public int delMessage(String myMobile,String otherMobile)
	{
		return super.delete(tableName, 
				"(sender_mobile=? and receiver_mobile=?) or (sender_mobile=? and receiver_mobile=?)",
				new String[]{myMobile,otherMobile,otherMobile,myMobile});
	}
}
