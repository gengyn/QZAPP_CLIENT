package com.qingzhou.client.common;

import android.os.Environment;

public class Constants {

	public final static int INIT_USERINFO = 0x01; //初始化客户信息标识
	public final static int INIT_CONTRACT = 0x02; //初始化合同信息标识
	public final static int INIT_PROJECTPLAN = 0x03;//初始化工程进度标识
	public final static int INIT_MYMESSAGE = 0x04;//初始化我的消息标识
	public final static int SHOW_PHOTO = 0x04;//查看图片
	public final static int INIT_MYINFO = 0X05;//初始化资讯
	public final static int INIT_EXITAPP = 0X06;//退出APP

	//工程状态
	public final static int PROJECT_NORMAL = 0x11; //正常
	public final static int PROJECT_DEFER = 0x12;	 //延误
	public final static int PROJECT_FINISH = 0x13; //竣工
	
	//进程状态
	public final static int PROCESS_NOSTART = 0x21;//未开始
	public final static int PROCESS_GOING = 0x22;//进行中	
	public final static int PROCESS_END = 0x23; //完工
	
	//缓存存放位置，SD卡中
	public final static String CACHE_DIR = 
			Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.qingzhou.client/cache/";
	
	//分页默认值
	public final static int PAGESIZE = 10;//默认分页每页行数
	
	//推送时用到
	public final static int CLIENT_MYPROJECT = 0x01; //我的家装
	public final static int CLIENT_MYCONTRACT = 0x02;//我的合同
	public final static int CLIENT_MYINFO = 0x03;//轻舟资讯
	public final static int CLIENT_MYMESSAGE = 0x04;//我的消息
	
	public final static String[] CLIENT_TAGS = {"QINGZHOU_CLIENT","JINING"};
	
	public final static String KEY_MESSAGE = "message";
	
	public static boolean chatActivity_isBackground = true; //默认对话窗口是否处于关闭状态
	public static boolean mymessageActivity_isBackground = true;//默认联系人列表窗口是否处于关闭状态
	public static boolean mainActivity_isBackground = true;//主界面是否处于关闭状态
	
	public static final boolean NOTIFICATION_NEED_SOUND = false;//通知是否需要声音
    public static final boolean NOTIFICATION_NEED_VIBRATE = true;//通知是否需要震动
    
    //服务访问频率控制
    public static double LIMIT_USERBASE = 3000;
    public static double LAST_USERBASE = -1;
    public static double LIMIT_MYINFO = 5000;
    public static double LAST_MYINFO = -1;
    public static double LIMIT_MYPROJECT = 3000;
    public static double LAST_MYPROJECT = -1;
    public static double LIMIT_MYCONTRACT = 3000;
    public static double LAST_MYCONTRACT = -1;
    public static double LIMIT_MYMESSAGE = 3000;
    public static double LAST_MYMESSAGE = -1;
}
