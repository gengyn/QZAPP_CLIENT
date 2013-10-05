package com.qingzhou.app.utils;

import com.qingzhou.client.LoadingActivity;
import com.qingzhou.client.R;
import com.qingzhou.client.adapter.CMListAdapter;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.domain.UserBase;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

/**
 * 有关于对话框的工具类
 * @author hihi
 *
 */
public class DialogUtils {

	/**
	 * 退出应用对话框
	 */
	public static void showExitDialog(final Context context) {  
		AlertDialog.Builder builder = new Builder(context);  
		builder.setMessage("确定要退出吗?");  
		builder.setTitle("提示");  
		builder.setPositiveButton("确认", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//exitApp(context);
				System.exit(0);
				//另外一种关闭应用的方式
				//android.os.Process.killProcess(android.os.Process.myPid());
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
	public static void showErrDialog(final Context context,String errStr)
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
	
	/**
	 * 显示长提示
	 * @param context
	 * @param toaskStr
	 */
	public static void showLongToask(Context context,String toaskStr)
	{
		showToask(context, toaskStr, Toast.LENGTH_LONG);
	}
	
	/**
	 * 显示短提示
	 * @param context
	 * @param toaskStr
	 */
	public static void showShortToask(Context context,String toaskStr)
	{
		showToask(context, toaskStr, Toast.LENGTH_SHORT);
	}
	
	/**
	 * 对提示进行简单封装
	 * @param context
	 * @param toaskStr
	 * @param model
	 */
	private static void showToask(Context context,String toaskStr,int model)
	{
		Toast.makeText(context, toaskStr, model).show();
	}
	
	/**
	 * 联络人
	 */
	public static void showAddressBookDialog(Context context,UserBase userBase)
	{
		Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.cm);
		builder.setAdapter(new CMListAdapter(context,userBase),null);
		builder.setTitle(context.getResources().getString(R.string.project_exchange));
		builder.create().show();  
	}
	
	/**
	 * 退出APP
	 * @param context
	 */
	public static void exitApp(Context context)
	{
		Intent intent = new Intent();
		intent.putExtra("FLAG", Constants.INIT_EXITAPP);
	    intent.setClass(context,LoadingActivity.class);
	    context.startActivity(intent);
	}
	
}
