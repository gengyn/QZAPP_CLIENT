package com.qingzhou.client.version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;
import com.alibaba.fastjson.JSON;

import com.qingzhou.client.R;
import com.qingzhou.client.domain.Version;
import com.qingzhou.client.util.HttpUtils;
import com.qingzhou.client.util.ThreadPoolUtils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

/**
 * 版本检查，版本更新
 * @author hihi
 *
 */
public class VersionUpdate {
	
	private static Context mContext;//上下文
	public static boolean isValid = false;//网络是否正常
	private ProgressBar mProgress; //下载进度条
	static String version_download_url = ""; //服务器端版本存放地址
	static String apkName = "";//apk名称
	String savePath = "";//下载后存放位置
	String appPackName = ""; 
	static String versionJSON = "";//服务器端版本描述文件
	int curVersionCode;//本地版本号
	static int serverVersionCode = -1;//服务端最新版本号
	static String serverVersionName = "";//服务器端最新版本名称
	static String serverUpdateInfo = "";//版本更新信息
	static String serverDate = "";//版本更新时间
	static String serverApkSize = "";//服务器端版本文件大小
	static String forceUpdate = "0";//是否强制更新

	File apkfile;//安装文件FILE对象
	private boolean interceptFlag = false;
	private int progress;
	private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    
    private Thread downLoadThread;
	
	private Dialog netWorkInvalidDialog;
	private Dialog noticeDialog;
	private Dialog downloadDialog;
	/**
	 * 构造方法
	 * @param context
	 */
	public VersionUpdate(Context context)
	{
		this.mContext = context;
		version_download_url = mContext.getResources().getText(R.string.version_download_url).toString();
		savePath = mContext.getResources().getText(R.string.savePath).toString();
		appPackName = mContext.getResources().getText(R.string.appPackName).toString();
		versionJSON = mContext.getResources().getText(R.string.versionJSON).toString();
	}
	
	/**
	 * 检查网络
	 * @return
	 */
	public static void checkNetwork(Context context)
	{
		try{
			mContext = context;
			ConnectivityManager cm = (ConnectivityManager)mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netWorkInfo = cm.getActiveNetworkInfo();
			if (netWorkInfo!= null && netWorkInfo.isAvailable())
				isValid = true;
		}catch(Exception e)
		{
			isValid = false;
		}
	}
	
	/**
	 * 获取服务器端的版本信息
	 */
	public static void getVersionInfo()
	{
		HttpUtils httpUtil = new HttpUtils();
		String returnStr = "";
		try{
			returnStr = httpUtil.httpGetExecute(
					mContext.getResources().getText(R.string.version_download_url).toString() +
					mContext.getResources().getText(R.string.versionJSON).toString());
			
			//解析版本信息
			Version verJson = JSON.parseObject(returnStr,Version.class);
			if (verJson != null)
			{
				serverVersionCode = Integer.parseInt(verJson.getVer_code());
				serverVersionName = verJson.getVer_name();
				serverUpdateInfo = verJson.getUpdate_info();
				serverDate = verJson.getVer_date();
				serverApkSize = verJson.getFile_size();
				apkName = verJson.getFile_name();
				forceUpdate = verJson.getForceupdate();
			}

			isValid = true;
		}catch(Exception e)
		{
			Log.e("获取版本信息失败", e.getMessage());
			isValid = false;
		}

	}
	
	/**
	 * 检查是否有新版本
	 * @return
	 */
	public boolean checkNewVersion()
	{
		boolean isNew = false;
		curVersionCode = getCurVersion();
		Log.i("网络是否正常！",isValid+"");
		Log.i("服务端版本号：",serverVersionCode+"");
		Log.i("客户端版本号：",curVersionCode+"");
		if (isValid)
		{
			if (serverVersionCode > curVersionCode)
			{
				isNew = true;
				showUpdateNoticeDialog();
			}
		}else 
			showNetWorkInvalidDialog();
		
		return isNew;
	}
	
		
	
	/**
	 * 获取本地版本信息
	 * @return
	 * 
	 */
	private int getCurVersion() 
	{
		int verCode = -1;
		try{
			verCode = mContext.getPackageManager().getPackageInfo(appPackName, 0).versionCode;	
		}catch(Exception e){
			Log.e("getCurVersion", e.getMessage());
		}
		return verCode;
	}
	
	/**
	 * 网络无效时对话框
	 */
	private void showNetWorkInvalidDialog()
	{
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("网络监测");
		builder.setMessage("监测出您的网络无效，请检查网络");
		builder.setPositiveButton("退出应用", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				System.exit(0);//退出应用
			}
		});
		netWorkInvalidDialog = builder.create();
		netWorkInvalidDialog.setCanceledOnTouchOutside(false);//点击屏幕不消失
		netWorkInvalidDialog.show();
	}
	
	/**
	 * 下载提醒对话框,需要判断是否强制更新
	 */
	private void showUpdateNoticeDialog(){
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");
		builder.setMessage("发现有新的版本，是否更新？\r\n版本号为:" 
				+ serverVersionName +"\r\n更新说明："+serverUpdateInfo + "\r\n文件大小：" +serverApkSize);
		builder.setPositiveButton("下载", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();			
			}
		});
		if (forceUpdate.equals("1"))
		{
			builder.setNegativeButton("退出应用", new OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.exit(0);//退出应用
				}
			});
		}else
		{
			builder.setNegativeButton("以后再说", new OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
		
			});
		}
		noticeDialog = builder.create();
		noticeDialog.setCanceledOnTouchOutside(false);//点击屏幕不消失
		noticeDialog.show();
	}
	
	/**
	 * 下载进度条
	 */
	private void showDownloadDialog(){
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");
		
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.progress, null);
		mProgress = (ProgressBar)v.findViewById(R.id.progress);
		
		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.setCanceledOnTouchOutside(false);//点击屏幕不消失
		downloadDialog.show();
		
		downloadApk();
	}
	
	/**
	 * 下载APK文件
	 */
	private void downloadApk(){
//		downLoadThread = new Thread(mdownApkRunnable);
//		downLoadThread.start();
		ThreadPoolUtils.execute(mdownApkRunnable);
	}
	
	/**
	 * 开启线程下载
	 */
	private Runnable mdownApkRunnable = new Runnable() {	
		@Override
		public void run() {
			try {
				URL url = new URL(version_download_url+apkName);
			
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();
				
				File file = new File(savePath);
				if(!file.exists()){
					file.mkdir();
				}
				File apkFile = new File(savePath+apkName);
				FileOutputStream fos = new FileOutputStream(apkFile);
				
				int count = 0;
				byte buf[] = new byte[1024];
				
				do{   		   		
		    		int numread = is.read(buf);
		    		count += numread;
		    	    progress =(int)(((float)count / length) * 100);
		    	    //更新进度
		    	    mHandler.sendEmptyMessage(DOWN_UPDATE);
		    		if(numread <= 0){	
		    			//下载完成通知安装
		    			mHandler.sendEmptyMessage(DOWN_OVER);
		    			break;
		    		}
		    		fos.write(buf,0,numread);
		    	}while(!interceptFlag);//点击取消就停止下载.
				
				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			}
			
		}
	};
	
	
	private Handler mHandler = new Handler(){
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				break;
			case DOWN_OVER:
				downloadDialog.dismiss();
				installApk();
				break;
			default:
				break;
			}
    	};
    };
    
    /**
     * 执行安装
     */
    private void installApk(){
		apkfile = new File(savePath+apkName);
        if (!apkfile.exists()) {
            return;
        }    
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
        mContext.startActivity(i);
        
        //五分钟后删除下载的安装文件
//        new Handler().postDelayed(new Runnable(){
//			@Override
//			public void run(){
//				deleteApk();
//			}
//		}, 5*60*1000);
	} 
	
}
