package com.qingzhou.client.version;

import java.io.File;

import com.qingzhou.app.utils.Logger;
import com.qingzhou.client.R;
import com.qingzhou.client.WelCome;
import com.qingzhou.client.common.Constants;

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
		Logger.i(TAG, "执行action:"+ arg1.getAction());
		if (arg1.getDataString().equals("package:" + arg0.getPackageName()))
		{
			savePath = Constants.CACHE_DIR;
			deleteApk();
			Logger.i(TAG, "删除APK成功！");
			Toast.makeText(arg0, "欢迎重新回到掌上轻舟", Toast.LENGTH_SHORT).show();
			restarApp(arg0);
			Logger.i(TAG,"启动应用成功；");
		}
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
