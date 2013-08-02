package com.qingzhou.client;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


/**
 * 我的家装活动
 * @author hihi
 *
 */
public class MyHomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myhome);
				
	}
	
	/**
	 * 我的合同點擊事件
	 */
	public void start_mycontract(View v)
	{
		Intent intent = new Intent();
	    intent.setClass(MyHomeActivity.this,MyContractActivity.class);
	    startActivity(intent);
	}
	
	/**
	 * 工程溝通點擊事件
	 */
	@SuppressWarnings("deprecation")
	public void start_exchange(View v)
	{
		showDialog(0);
	}
	
	CharSequence[] items = {"设计师：Mike","项目经理：Jack","客户经理：Peter"};
	
	@Override
    protected Dialog onCreateDialog(int id) {
		switch(id){
		case 0:
			Builder builder = new AlertDialog.Builder(this);
			builder.setIcon(R.drawable.cm);
			//builder.setView(new View(this));
			builder.setAdapter(new CMListAdapter(this,items),null);
//			builder.setAdapter(new CMListAdapter(this,items),new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					Intent intent = new Intent (MyHomeActivity.this,ChatActivity.class);			
//					startActivity(intent);
//				}
//			});
			
//			builder.setItems(items, new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					Intent intent = new Intent (MyHomeActivity.this,ChatActivity.class);			
//					startActivity(intent);
//				}
//			});
			builder.setTitle("联系人");
			return builder.create();
		}
		return null;
	}
	
	public void phone_onclick(View v)
	{
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:15011320143"));
		// 将意图传给操作系统
		// startActivity方法专门将意图传给操作系统
		MyHomeActivity.this.startActivity(intent);
	}
	
	public void message_onclick(View v)
	{
		Intent intent = new Intent (MyHomeActivity.this,ChatActivity.class);			
		startActivity(intent);
	}
	
}
