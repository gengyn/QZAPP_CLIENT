package com.qingzhou.client.util;

import com.qingzhou.client.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * 有关于对话框的工具类
 * @author hihi
 *
 */
public class DialogUtils {

	/**
	 * 退出应用对话框
	 */
	public static void showExitDialog(Context context) {  
		AlertDialog.Builder builder = new Builder(context);  
		builder.setMessage("确定要退出吗?");  
		builder.setTitle("提示");  
		builder.setPositiveButton("确认", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//另外一种关闭应用的方式
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();				
			}
		});
        
        builder.create().show();  
    }  
	
	/**
	 * 默认错误提示框，直接退出应用
	 * @param context
	 * @param errStr
	 */
	public static void showErrDialog(Context context,String errStr)
	{
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(context.getResources().getDrawable(R.drawable.login_error_icon));
		builder.setTitle("错误");
		builder.setMessage(errStr);
		builder.setPositiveButton("退出应用", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				System.exit(0);//退出应用
			}
		});
		builder.create().show();
	}
}
