package com.qingzhou.client.version;

import java.io.File;

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
    		
    	deleteDir(savePath); //卸载后清理
    }
    
    /**
     * 递归删除整个目录，以后整理成公共方法
     * @param savePath
     * @return
     */
    private void deleteDir(String savePath) {
    	File f = new File(savePath);//定义文件路径         
    	if(f.exists() && f.isDirectory()){//判断是文件还是目录  
    		if(f.listFiles().length==0){//若目录下没有文件则直接删除  
    			f.delete();  
    		}else{//若有则把文件放进数组，并判断是否有下级目录  
    			File delFile[]=f.listFiles();  
    			int i =f.listFiles().length;  
    			for(int j=0;j<i;j++){  
    				if(delFile[j].isDirectory()){  
    					deleteDir(delFile[j].getAbsolutePath());//递归调用del方法并取得子目录路径  
    				}  
    				delFile[j].delete();//删除文件  
    			}  
    		}  
    	}
    }  

	
}
