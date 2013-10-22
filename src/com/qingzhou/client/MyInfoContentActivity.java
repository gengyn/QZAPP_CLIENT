package com.qingzhou.client;

import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.domain.Myinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 资讯内容
 * @author hihi
 *
 */
public class MyInfoContentActivity extends BaseActivity {

	private QcApp qcApp;
	private TextView content_title;
	private TextView content_type;
	private TextView content_date;
	private WebView content_webview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo_content);
		
		qcApp = (QcApp)getApplication();
		
		content_title = (TextView) findViewById(R.id.content_title);
		content_type = (TextView) findViewById(R.id.content_type);
		content_date = (TextView) findViewById(R.id.content_date);
		content_webview = (WebView) findViewById(R.id.content_webview);
//		content_webview.getSettings().setJavaScriptEnabled(true);
//		content_webview.getSettings().setSupportZoom(true);
//		content_webview.getSettings().setBuiltInZoomControls(true);
//		content_webview.getSettings().setDefaultFontSize(15);
		
		Myinfo myinfo = (Myinfo)getIntent().getSerializableExtra("MYINFO");
		initContent(myinfo);
	}
	
	/**
	 * 为数据赋值
	 */
	private void initContent(Myinfo myinfo)
	{
		if (myinfo == null)
			myinfo = new Myinfo();
		content_title.setText(myinfo.getInfo_title());
		content_date.setText(myinfo.getInfo_date());
		content_type.setText(myinfo.getInfo_type());
		content_webview.loadUrl(myinfo.getInfo_url());
		//Toast.makeText(getBaseContext(),myinfo.getInfo_url(), Toast.LENGTH_LONG).show();
		
	}
	
	/**
	 * 关闭窗口
	 */
	public void info_back(View v)
	{
		//Toast.makeText(getBaseContext(), "测试", Toast.LENGTH_LONG).show();
		MyInfoContentActivity.this.finish();
	}
	
	
}
