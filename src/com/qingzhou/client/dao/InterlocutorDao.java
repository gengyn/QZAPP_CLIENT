package com.qingzhou.client.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.qingzhou.app.db.BaseDao;
import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.domain.ChatMsg;
import com.qingzhou.client.domain.Interlocutor;

public class InterlocutorDao extends BaseDao<Interlocutor>{
	
	String tableName = "APP_INTERLOCUTOR";
	
	public InterlocutorDao(Context context,SQLiteDatabase db) {
		super(context,db);
	}
	
	public InterlocutorDao(Context context)
	{
		super(context);
	}
	
	
	public void insertMsg(ChatMsg chatMsg,boolean isSend)
	{
		ContentValues cv = new ContentValues();
		if (isSend)
		{
			cv.put("MY_MOBILE", chatMsg.getSender());
			cv.put("I_MOBILE", chatMsg.getReceiver());
			cv.put("ISREADED", "1");
		}
		else 
		{
			cv.put("MY_MOBILE", chatMsg.getReceiver());
			cv.put("I_MOBILE", chatMsg.getSender());
			cv.put("ISREADED", "0");
		}
		cv.put("LAST_MESSAGE", chatMsg.getText());
		cv.put("LAST_TIME", StringUtils.getCurDate());
		super.insert(tableName, cv);
	}
	
	public int updateMsg(ChatMsg chatMsg,boolean isSend)
	{
		ContentValues cv = new ContentValues();
		if (isSend)
		{
			cv.put("MY_MOBILE", chatMsg.getSender());
			cv.put("I_MOBILE", chatMsg.getReceiver());
			cv.put("ISREADED", "1");
		}
		else 
		{
			cv.put("MY_MOBILE", chatMsg.getReceiver());
			cv.put("I_MOBILE", chatMsg.getSender());
			cv.put("ISREADED", "0");
		}
		cv.put("LAST_MESSAGE", chatMsg.getText());
		cv.put("LAST_TIME", StringUtils.getCurDate());
		
		return super.update(tableName, cv, "my_mobile=? and i_mobile=?", new String[]{(String) cv.get("MY_MOBILE"),(String) cv.get("I_MOBILE")});
	}
	
	/**
	 * 更新已读标识
	 * @param i_mobile
	 * @return
	 */
	public int updateReadFlag(String my_mobile,String i_mobile)
	{
		ContentValues cv = new ContentValues();
		cv.put("ISREADED", "1");
		return super.update(tableName,cv,"my_mobile=? and i_mobile=?",new String[]{my_mobile,i_mobile});
	}
	
	/**
	 * 获取对话人列表及最新的消息内容
	 * @return
	 */
	public List<Interlocutor> listInterlocutor(String my_mobile)
	{
		return (List<Interlocutor>) super.queryList
				(Interlocutor.class,
						tableName, 
						new String[]{"i_mobile","last_message","last_time","isreaded"}, 
						"my_mobile=?", new String[]{my_mobile}, "LAST_TIME desc",null,null);
	}
	
	/**
	 * 删除某个对话人
	 * @param i_mobile
	 * @return
	 */
	public int delInterlocutor(String my_mobile,String i_mobile)
	{
		return super.delete(tableName, "my_mobile=? and i_mobile=?", new String[]{my_mobile,i_mobile});
	}

}
