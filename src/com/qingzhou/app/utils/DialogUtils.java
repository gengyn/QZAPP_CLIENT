package com.qingzhou.app.utils;

import java.util.Stack;

import com.qingzhou.client.AppManager;
import com.qingzhou.client.LoadingActivity;
import com.qingzhou.client.MyHomeActivity;
import com.qingzhou.client.R;
import com.qingzhou.client.adapter.CMListAdapter;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.domain.UserBase;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
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
				//退出应用
				AppManager.getAppManager().AppExit(context);
				//System.exit(0);
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
				//System.exit(0);
				//退出应用
				AppManager.getAppManager().AppExit(context);
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
	 * 自定义样式，显示联络人的沟通方式
	 * @param context
	 * @param itemName
	 * @param itemMobile
	 */
	public static void showAddressBookItemDialog(final Context context,String itemName,final String itemMobile)
	{
		final AlertDialog dlg = new AlertDialog.Builder(context).create();
		 dlg.show();
		 Window window = dlg.getWindow();
		 window.setContentView(R.layout.addressbookitem_dialog);
		 LinearLayout ab_item_layout = (LinearLayout) window.findViewById(R.id.ab_item_layout);
		 ab_item_layout.getBackground().setAlpha(100);
		 
		 TextView ab_item_name = (TextView) window.findViewById(R.id.ab_item_name);
		 ab_item_name.setText(itemName);
		 
		 ImageButton btn_ad_item_phone = (ImageButton)window.findViewById(R.id.btn_ad_item_phone);
		 btn_ad_item_phone.getBackground().setAlpha(20);
		 btn_ad_item_phone.setOnClickListener(new View.OnClickListener() {  
	            @Override  
	            public void onClick(View arg0) {  
	            	DialogUtils.action_call(context,itemMobile);
	            }  
	        });  
		 ImageButton btn_ad_item_message = (ImageButton)window.findViewById(R.id.btn_ad_item_message);
		 btn_ad_item_message.getBackground().setAlpha(20);
		 btn_ad_item_message.setOnClickListener(new View.OnClickListener() {  
	            @Override  
	            public void onClick(View arg0) {  
	            	DialogUtils.toMessage(context,itemMobile);
	            }  
	        });  
	}
	
	
	/**
	 * 退出APP
	 * @param context
	 */
	public void exitApp(Context context)
	{
//		Intent intent = new Intent();
//		intent.putExtra("FLAG", Constants.INIT_EXITAPP);
//	    intent.setClass(context,LoadingActivity.class);
//	    context.startActivity(intent);
	
	}
	
	/**
	 * 拨打电话
	 * @param strPhone
	 */
	public static void action_call(Context context,String strPhone)
	{
		if (StringUtils.isEmpty(strPhone))
			return;
		//ACTION_CALL
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+strPhone));
		// 将意图传给操作系统
		// startActivity方法专门将意图传给操作系统
		context.startActivity(intent);
	}
	
	/**
	 * 短信界面
	 * @param context
	 * @param worker
	 */
	public static void toMessage(Context context,String strPhone)
	{
		if (StringUtils.isEmpty(strPhone))
			return;
   
	  //jump mymessage
//		Intent intent = new Intent();
//		intent.putExtra("OPPOSITE", worker);
//	    intent.setClass(context,ChatActivity.class);
//	    context.startActivity(intent);
		
		//jump sms
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+strPhone));
		context.startActivity(intent);
	}
	
}
