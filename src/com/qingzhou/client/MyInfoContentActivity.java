package com.qingzhou.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 资讯内容
 * @author hihi
 *
 */
public class MyInfoContentActivity extends Activity {

	private TextView new_title;
	private TextView new_date;
	private TextView new_content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo_content);
		
		new_title = (TextView) findViewById(R.id.new_title);
		new_date = (TextView) findViewById(R.id.new_date);
		new_content = (TextView) findViewById(R.id.new_content);
		
		initContent();
	}
	
	/**
	 * 为数据赋值
	 */
	private void initContent()
	{
		new_title.setText("去南京，见孟非！");
		new_date.setText("2013-07-09");
		new_content.setText("  7月27日-28日，济宁轻舟装饰与我乐橱柜联合举办济宁消费团赴南京（免费）两日游，活动期间凡是选择轻舟装饰或预订我乐橱柜的业主均可报名，详情咨询我乐美凯龙店（0537-5151236）、济宁轻舟装饰店（0537-2230777）");
		
	}
	
	/**
	 * 关闭窗口
	 */
	public void info_back(View v)
	{
		Toast.makeText(getBaseContext(), "测试", Toast.LENGTH_LONG).show();
		//Intent intent = new Intent (MyInfoContentActivity.this,MyInfoActivity.class);			
		//startActivity(intent);
		MyInfoContentActivity.this.finish();
	}
	
	
}
