package com.qingzhou.client.common;

import android.app.Application;

import com.qingzhou.client.domain.UserBase;
import com.qingzhou.client.domain.Contract;

/**
 * 全局类
 * @author hihi
 *
 */
public class QcApp extends Application {

	String userToken = "";//客户登录成功后形成的令牌
	String userName = "";//客户姓名
	String userPhone = "";//客户电话
	
	UserBase userBase ;//客户的基本信息
	Contract contract;//合同信息
	
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	
	public UserBase getUserBase() {
		return userBase;
	}
	public void setUserBase(UserBase userBase) {
		this.userBase = userBase;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
