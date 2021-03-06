package com.qingzhou.client.common;

public class RestService {

	//final static String SERVICE_URL = "http://218.59.147.66:80/appService/rest/service/";
	final static String SERVICE_URL = "http://192.168.1.176:8089/appService/rest/service/";
	public final static String LAST_VERSION_URL = SERVICE_URL + "version/";//最新版本服务地址,暂没有使用
	public final static String POST_LOGIN_URL = SERVICE_URL + "login";//新增客户登录信息
	public final static String PUT_LOGIN_URL = SERVICE_URL + "login";//更新最新操作时间
	public final static String DEL_LOGIN_URL = SERVICE_URL + "login/";//删除客户登录信息
	public final static String GET_CONTRACT_URL = SERVICE_URL + "contract/";//获取合同信息及优惠信息
	public final static String GET_CONTRACT_BASEINVENTORY_URL = SERVICE_URL + "contract/baseinventory/";//获取基础结算清单
	public final static String GET_CONTRACT_MATERIALINVENTORY_URL = SERVICE_URL + "contract/materialinventory/";//获取主材结算清单
	public final static String GET_PROJECTPLAN_URL = SERVICE_URL + "projectplan/";//GET项目进度情况
	public final static String GET_PROJECTPLAN_BASEDETAIL_URL = SERVICE_URL + "projectplan/basedetail/";//GET项目进度基础明细
	public final static String GET_PROJECTPLAN_MAINDETAIL_URL = SERVICE_URL + "projectplan/maindetail/";//GET项目进度主材明细
	public final static String GET_PHOTO_URL = SERVICE_URL + "projectplan/photo/";//获取图片
	public final static String GET_MYINFO_URL = SERVICE_URL + "info/";//获取轻舟资讯
	public final static String POST_SENDMESSAGE_URL = SERVICE_URL + "push/message";//即时通讯、发送消息
	public final static String POST_SENDNOTICE_URL = SERVICE_URL + "push/notice";//发送通知
	public final static String GET_RESENDMSG_URL = SERVICE_URL + "push/message/";//消息重发
}
