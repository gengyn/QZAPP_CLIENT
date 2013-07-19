package com.qingzhou.client;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.qingzhou.client.MyInfoEntity;
import com.qingzhou.client.MyInfoViewAdapter;

/**
 * 资讯活动
 * 
 * @author hihi
 * 
 */
public class MyInfoActivity extends Activity {

	private ListView infoListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.myinfo);
		initInfoList();
	}

	private String[] infoArray = new String[] { "暑假太热，装修太累，橱柜太贵。去南京，见孟非！",
			"济宁轻舟装饰，济宁电视台《美丽蝶变》第三期", "家在冠亚，冠亚星城五期业主见面会", "济宁公司夏季设计师特训营开课", };

	private String[] dataArray = new String[] { "2013-07-09",
			"2013-07-09", "2013-07-09", "2013-07-09",
			"2013-07-09", "2013-07-09", "2013-07-09",
			"2013-07-09" };

	/**
	 * 初始化资讯
	 */
	private void initInfoList() {
		infoListView = (ListView) findViewById(R.id.info_list);
		List<MyInfoEntity> mInfo = new ArrayList<MyInfoEntity>();

		for (int i = 0; i < infoArray.length; i++) {
			MyInfoEntity item = new MyInfoEntity();
			item.setInfo_title(infoArray[i]);
			item.setInfo_date(dataArray[i]);
			mInfo.add(item);
		}

		// infoListView.setTextFilterEnabled(true);
		// ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,infoArray));
		infoListView.setAdapter(new MyInfoViewAdapter(this, mInfo));

		//加入单击事件
		infoListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				 Toast.makeText(getBaseContext(), "selectd:" +arg2 +" id:"+arg3, Toast.LENGTH_LONG).show();
				 Intent intent = new Intent (MyInfoActivity.this,MyInfoContentActivity.class);			
				 startActivity(intent);			
			}

		});
	}

}
