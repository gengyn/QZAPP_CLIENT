package com.qingzhou.client;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.qingzhou.app.utils.CustomerUtils;
import com.qingzhou.app.utils.DialogUtils;
import com.qingzhou.app.utils.FileUtils;
import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.common.QcApp;

import com.alibaba.fastjson.JSON;

/**
 * 登录验证
 * @author hihi
 *
 */
public class LoginActivity extends Activity {
	private EditText mUser;  //客户名称
	private EditText mPhone; //客户电话
	private EditText mPassword; //客户密码
	private CheckBox mIsAuto;//是否自动登录
	private QcApp qcApp;
	private String filePath = "loginUser.json";//客户信息文件位置
	private boolean isReLogin = false;//是否切换用户登录
	
	public static LoginActivity _instance = null; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        _instance = this;
        //取得启动该Activity的Intent对象
        Intent intent = getIntent();
        //取出Intent中附加的数据,是否是切换用户登录，如为空，默认为不是切换用户
        isReLogin = intent.getBooleanExtra("isReLogin", false);

        mUser = (EditText)findViewById(R.id.login_username_edit);
        mPhone = (EditText)findViewById(R.id.login_phone_edit);
        mPassword = (EditText)findViewById(R.id.login_passwd_edit);
        mIsAuto = (CheckBox) findViewById(R.id.autoLogin_box);
        qcApp = (QcApp)getApplication();
        
        filePath = Constants.CACHE_DIR + filePath;//定义客户文件位置
        //读取登录信息,如是切换用户登录则不读取
        if(!isReLogin) readLoginJSON();
        
    }
    
    /**
	 * 判断是否返回键，并且没有其他Activity时，提示退出
	 */
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
        	DialogUtils.showExitDialog(this);  
            return true;  
        }  
        return true;  
    } 
	
	/**
	 * 将登录信息写入JSON，保存客户名称、电话和密码
	 * @return
	 */
	public boolean writeLoginJSON()
	{
		Map<String,String> loginMap = new HashMap<String,String>();
		loginMap.put("userName", mUser.getText().toString().trim());
		loginMap.put("phone", mPhone.getText().toString().trim());
		loginMap.put("passwd", mPassword.getText().toString().trim());
				
		return FileUtils.writeFile(filePath, JSON.toJSONString(loginMap));
	}
	
	/**
	 * 读取客户名称、电话和密码
	 */
	public void readLoginJSON()
	{
		String fileStr = FileUtils.readFile(filePath);
		if (!StringUtils.isEmpty(fileStr))
		{
			Map<String,String> loginMap = new HashMap<String,String>();
			loginMap = (Map)JSON.parseObject(fileStr);
			mUser.setText(loginMap.get("userName"));
			mPhone.setText(loginMap.get("phone"));
			mPassword.setText(loginMap.get("passwd"));
			mIsAuto.setChecked(true);
			//登录操作
			loginSub();
		}
	}
	
	/**
	 * 删除客户信息文件
	 */
	public void deleteLoginJSON()
	{
		FileUtils.deleteFile(filePath);
	}
	
	
	
	/**
	 * 转向加载界面
	 */
	private void toLoadingActivity()
	{
		Intent intent = new Intent();
		intent.putExtra("FLAG", Constants.INIT_USERINFO);
        intent.setClass(LoginActivity.this,LoadingActivity.class);
        startActivity(intent);
        //LoginActivity.this.finish();
	}
	
	/**
	 * 登录按钮点击事件
	 * @param v
	 */
	public void login_main(View v)
	{
		loginSub();
	}
    
    /**
     * 登录验证，成功后跳转主界面，失败提醒
     * @param v
     */
    public void loginSub() {
    	if(CustomerUtils.checkPasswd(mPhone.getText().toString().trim() + mUser.getText().toString().trim(), 
    			mPassword.getText().toString().trim()))  
        {
    		//登录成功
    		//如果选择自动登录，将保存客户信息，如不是讲删除客户信息
    		if (mIsAuto.isChecked()) writeLoginJSON();
    		else deleteLoginJSON();
    		//设置客户姓名到QCAPP
			qcApp.setUserName(mUser.getText().toString().trim());
			qcApp.setUserPhone(mPhone.getText().toString().trim());
    		//成功后转向
			toLoadingActivity();
             //Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
          }
        else if(StringUtils.isEmpty(mUser.getText().toString()) 
        		|| StringUtils.isEmpty(mPassword.getText().toString()) 
        		|| StringUtils.isEmpty(mPhone.getText().toString())) 
        {
//        	new AlertDialog.Builder(LoginActivity.this)
//			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
//			.setTitle("提醒")
//			.setMessage(getResources().getText(R.string.loginInputErrorText).toString())
//			.create().show();
        	DialogUtils.showLongToask(getApplication(), getResources().getText(R.string.loginInputErrorText).toString());
         }
        else{
           
//        	new AlertDialog.Builder(LoginActivity.this)
//			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
//			.setTitle("失败")
//			.setMessage(getResources().getText(R.string.loginErrorText).toString())
//			.create().show();
        	DialogUtils.showLongToask(getApplication(), getResources().getText(R.string.loginErrorText).toString());
        }
    	
     }  
}
