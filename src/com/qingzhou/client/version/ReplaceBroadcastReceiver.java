package com.qingzhou.client.version;

import java.io.File;

import com.qingzhou.client.R;
import com.qingzhou.client.WelCome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * 监听安装应用和更新应用事件
 * @author hihi
 *
 */
public class ReplaceBroadcastReceiver extends BroadcastReceiver{
	private static final String TAG="Replace";
	
	String apkName = "QZAPP_CLIENT.apk";//apk名称
	String savePath = "";//下载后存放位置
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		savePath = arg0.getResources().getText(R.string.savePath).toString();
		deleteApk();
		Log.i(TAG, "删除APK成功！");
		Toast.makeText(arg0, "重新回到轻舟客户版", Toast.LENGTH_SHORT).show();
		restarApp(arg0);
		Log.i(TAG,"启动应用成功；");
	}
	
	/**
     * 删除安装文件
     */
    private void deleteApk()
    {
    	File apkfile = new File(savePath+apkName);
    	if (apkfile.exists())
    		apkfile.delete();
    }
    
    /**
     * 在安装成功后，重新启动应用
     */
    private void restarApp(Context context)
    {
    	Intent i = new Intent(context,WelCome.class);
    	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	context.startActivity(i);
    }
}
