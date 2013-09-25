package com.qingzhou.client.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.app.Application;
import android.util.Log;

import com.qingzhou.client.domain.RestProjectPlan;
import com.qingzhou.client.domain.Myinfo;
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
	RestProjectPlan projectPlan;//工程进度信息
	
	int goFlag = 0;//跳转标识
	
	
	List<Myinfo> infoList;//资讯列表
	
	Map<String,String> addressBook = new HashMap<String,String>();//通讯录
	
	/**
	 * 读取配置信息
	 * @return
	 */
	private Properties loadProperties() {
	
        Properties props = new Properties();
        try {
            int id = this.getResources().getIdentifier("qcapp", "raw",
    		this.getPackageName());
		    props.load(this.getResources().openRawResource(id));
		} catch (Exception e) {
		    Log.e("QCAPP", "Could not find the properties file.", e);
		 }
		return props;
	}
	
	/**
	 * 获得通讯录
	 * @return
	 */
	public Map<String,String> getAddressBook()
	{
		//客户自己
		addressBook.put(getUserPhone(), "我");
		//设计师
		addressBook.put(
				getUserBase().getReg_stylist_mobile(), 
				getUserBase().getReg_stylist_name());
		//客户经理
		addressBook.put(
				getUserBase().getReg_customer_mgr_mobile(), 
				getUserBase().getReg_customer_mgr_name());
		//项目经理
		addressBook.put(
				getUserBase().getReg_project_mgr_mobile(), 
				getUserBase().getReg_project_mgr_name());
		
		return addressBook;
	}
	
	public int getGoFlag() {
		return goFlag;
	}
	public void setGoFlag(int goFlag) {
		this.goFlag = goFlag;
	}
	public List<Myinfo> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<Myinfo> infoList) {
		this.infoList = infoList;
	}
	public RestProjectPlan getProjectPlan() {
		return projectPlan;
	}
	public void setProjectPlan(RestProjectPlan projectPlan) {
		this.projectPlan = projectPlan;
	}
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
