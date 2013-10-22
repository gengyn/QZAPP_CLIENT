package com.qingzhou.client.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.qingzhou.app.db.BaseDao;
import com.qingzhou.client.domain.Interlocutor;

public class CacheDao extends BaseDao{
	
	String tableName = "APP_CACHE";
	
	public CacheDao(Context context,SQLiteDatabase db) {
		super(context,db);
	}
	
	public CacheDao(Context context)
	{
		super(context);
	}
	
	/**
	 * 新增缓存
	 * @param username
	 * @param userphone
	 * @param type
	 * @param content
	 */
	public void insertCache(String username,String userphone,String type,String content)
	{
		ContentValues cv = new ContentValues();
		
			cv.put("USERNAME", username);
			cv.put("USERPHONE", userphone);
			cv.put("CACHE_TYPE", type);
			cv.put("CACHE_CONTENT", content);
		
		super.insert(tableName, cv);
	}
	
	/**
	 * 删除缓存
	 * @param username
	 * @param userphone
	 * @param type
	 * @return
	 */
	public int delCache(String username,String userphone,String type)
	{
		return super.delete(tableName, 
				"username=? and userphone=? and cache_type=?", 
				new String[]{username,userphone,type});
	}
	
	
	
	/**
	 * 获取缓存
	 * @return
	 */
	public String queryCache(String username,String userphone,String type)
	{
		return (String)super.queryField(String.class, 
				tableName, 
				new String[]{"cache_content"},
				"username=? and userphone=? and cache_type=?", 
				new String[]{username,userphone,type});
				
	}

}
