package com.qingzhou.client.version;

import com.qingzhou.app.utils.FileUtils;
import com.qingzhou.client.R;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import android.content.Intent;

/**
 * 卸载应用
 * @author hihi
 *
 */
public class UninstallerActivity extends Activity{

	private static final String TAG = "UninstallerActivity";
	
	String savePath = "";//下载后存放位置
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.welcome);
		savePath = this.getResources().getText(R.string.savePath).toString();
		showUnDialog(this);
   }
	
	/**
	 * 卸载提示框
	 * @param mcontext
	 */
	private void showUnDialog(Context mcontext)
	{
		AlertDialog.Builder builder = new Builder(mcontext);
		builder.setTitle("提示");
		builder.setMessage("您是否要卸载？");
		builder.setPositiveButton("是", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
				Log.i(TAG,"执行卸载！");
				//卸载
				removeApp();
				
				Log.i(TAG,"完成卸载！");
				UninstallerActivity.this.finish();
			}
		});
		builder.setNegativeButton("不", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();	
				UninstallerActivity.this.finish();
			}
		});
		 builder.create().show();
	}
	/**
     * 卸载应用后调用
     */
    private void removeApp()
    {
//		Uri packageURI = Uri.parse("package:com.qingzhou.client");      
//		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);       
//		startActivity(uninstallIntent); 
    		
    	FileUtils.deleteDir(savePath); //卸载后清理
    }
    
   

	
}
