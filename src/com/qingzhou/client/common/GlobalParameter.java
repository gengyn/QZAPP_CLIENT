package com.qingzhou.client.common;

import android.os.Environment;

public class GlobalParameter {

	public final static int INIT_USERINFO = 0x01; //初始化客户信息标识
	public final static int INIT_CONTRACT = 0x02; //初始化合同信息标识
	public final static int INIT_PROJECTPLAN = 0x03;//初始化工程进度标识
	public final static int SHOW_PHOTO = 0x04;//查看图片
	
	
	//工程状态
	public final static int PROJECT_NORMAL = 0x11; //正常
	public final static int PROJECT_DEFER = 0x12;	 //延误
	public final static int PROJECT_FINISH = 0x13; //竣工
	
	//进程状态W
	public final static int PROCESS_NOSTART = 0x21;//未开始
	public final static int PROCESS_GOING = 0x22;//进行中	
	public final static int PROCESS_END = 0x23; //完工
	
	//缓存存放位置，SD卡中
	public final static String CACHE_DIR = 
			Environment.getExternalStorageDirectory().toString() + "/QZ/cache/";
}
