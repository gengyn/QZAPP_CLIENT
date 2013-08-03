package com.qingzhou.client.common;

import android.app.Application;

/**
 * 全局类
 * @author hihi
 *
 */
public class QcApp extends Application {

	String userToken = "";//客户登录成功后形成的令牌
	String userID = "";//客户编码，暂用
	
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
}
